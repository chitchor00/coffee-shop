package org.swaroop.coffeestore.config.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
	private final SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers(HttpMethod.GET,
					"/error",
					"/actuator/**",
					"/swagger-ui.html",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/favicon.ico")
				.permitAll()
				.anyRequest()
				.authenticated()
			)
			.exceptionHandling()
			.authenticationEntryPoint((rq, rs, e) -> {
				final String token = rq.getHeader(AUTHORIZATION);
				log.warn("UnauthorizedAccess: user=[anonymous], token=[{}], uri=[{}]",
					token != null ? token : "", rq.getRequestURI());

				rs.setContentType("application/json");
				rs.setCharacterEncoding("UTF-8");
				rs.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				final PrintWriter writer = rs.getWriter();
				writer.write("{\"code\": \"Unauthorized\"}");
				writer.flush();
			})
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}
}
