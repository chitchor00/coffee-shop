package org.swaroop.coffeestore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.swaroop.coffeestore.IConst;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "Coffee Store API",
		version = "1.0",
		license = @License(
			name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
		)
	),
	security = {@SecurityRequirement(name = "AccessToken")}
)
@SecurityScheme(
	name = "AccessToken",
	type = SecuritySchemeType.APIKEY,
	in = SecuritySchemeIn.HEADER,
	paramName = IConst.ACCESS_TOKEN_KEY
)
public class OpenAPIConfiguration {
}
