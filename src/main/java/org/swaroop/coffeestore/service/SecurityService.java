package org.swaroop.coffeestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.swaroop.coffeestore.config.CoffeeStoreProperties;
import org.swaroop.coffeestore.config.security.AccessToken;
import org.swaroop.coffeestore.config.security.ETokenType;
import org.swaroop.coffeestore.iservice.ISecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService implements ISecurityService {
	private final CoffeeStoreProperties properties;

	@Override
	public void authenticate(String token) {
		//TODO: a very simple authentication

		if (token != null) {
			final ETokenType tokenType = properties.getAdminToken().equals(token) ? ETokenType.Admin : ETokenType.Customer;

			//TODO: token should be masked partially
			log.info("User Authenticated: type=[{}] token[{}]", tokenType, token);
			SecurityContextHolder.getContext().setAuthentication(new AccessToken(tokenType, token));
		} else {
			log.info("Invalid User Authentication: null token");
		}
	}
}
