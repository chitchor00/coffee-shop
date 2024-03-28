package org.swaroop.coffeestore.config;

import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.CoffeeStoreException;
import org.swaroop.coffeestore.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(CoffeeStoreException.class)
	public ResponseEntity<ErrorDTO.GeneralRs> handleCoffeeStoreException(CoffeeStoreException ex) {
		log.warn("@ControllerAdvice - CoffeeStoreException: {}", ex.getMessage());
		return new ResponseEntity<>(new ErrorDTO.GeneralRs(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO.GeneralRs> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		final List<ErrorDTO.FieldDTO> errorFieldDTOS = ex
			.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> new ErrorDTO.FieldDTO(fieldError.getField(), fieldError.getCode()))
			.collect(Collectors.toList());

		ex.getBindingResult()
			.getGlobalErrors()
			.stream()
			.map(objectError -> new ErrorDTO.FieldDTO(null, objectError.getCode()))
			.collect(Collectors.toCollection(() -> errorFieldDTOS));

		return new ResponseEntity<>(
			new ErrorDTO.GeneralRs("InputValidationError").addFields(errorFieldDTOS), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDTO.GeneralRs> handleAccessDeniedException(AccessDeniedException ex) {
		log.warn("@ControllerAdvice - AccessDenied");
		return new ResponseEntity<>(new ErrorDTO.GeneralRs("AccessDenied"), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO.GeneralRs> handleException(Exception ex) {
		log.error("@ControllerAdvice - General Exception:", ex);
		return new ResponseEntity<>(new ErrorDTO.GeneralRs("Unknown"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
