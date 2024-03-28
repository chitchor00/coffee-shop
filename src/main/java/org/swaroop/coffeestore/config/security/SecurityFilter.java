package org.swaroop.coffeestore.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.IConst;
import org.swaroop.coffeestore.iservice.ISecurityService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final ISecurityService securityService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain) throws ServletException, IOException {

		final String requestURI = request.getRequestURI();
		if (requestURI.startsWith(IConst.API_URI_PREFIX)) {
			final long start = System.currentTimeMillis();
			final String method = request.getMethod();
			log.info("SecurityFilter: RQ - URI=[{} {}]", method, requestURI);

			final String token = request.getHeader(IConst.ACCESS_TOKEN_KEY);
			securityService.authenticate(token);
			chain.doFilter(request, response);

			log.info("SecurityFilter: RS - URI=[{} {}], Status=[{}], Dur=[{}]",
				method, requestURI, response.getStatus(), System.currentTimeMillis() - start);
		} else {
			chain.doFilter(request, response);
		}
	}
}
