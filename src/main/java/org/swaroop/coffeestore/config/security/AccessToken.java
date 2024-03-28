package org.swaroop.coffeestore.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class AccessToken extends AbstractAuthenticationToken {
	private final String user;

	public AccessToken(ETokenType tokenType, String token) {
		super(Collections.singleton(new SimpleGrantedAuthority(tokenType.name())));
		this.user = token;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}
}
