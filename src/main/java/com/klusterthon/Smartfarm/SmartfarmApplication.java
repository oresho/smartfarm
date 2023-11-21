package com.klusterthon.Smartfarm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info =
		@Info(
				description = "This app predicts the best time to plant and harvest crops, taking into account local weather conditions and soil quality",
				title = "Smartfarm App",
				version = "1.0"
		),
		servers = {
				@Server(
						url = "http://localhost:9090",
						description = "DEV Server"
				),
				@Server(
						url = "https://smartfarm-production-b9d7.up.railway.app",
						description = "PROD server"
				)
		},
		security = {
				@io.swagger.v3.oas.annotations.security.SecurityRequirement(
						name = "Bearer Authentication")
		})
@SecurityScheme(
		name = "Bearer Authentication",
		description = "JWT Authentication",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class SmartfarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartfarmApplication.class, args);
	}

}
