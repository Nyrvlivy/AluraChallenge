package br.com.alura.challenge.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Alura Challenge API",
                version = "1.0.0",
                description = "Alura Challenge Project for managing courses, enrollments, and evaluations.",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(
                        name = "Bruna Massi",
                        email = "dev.brunamassi@example.com"
                )
        ),
        servers = @Server(url = "/", description = "Principal Server"),
        externalDocs = @ExternalDocumentation(
                description = "Alura Challenge API - GitHub repository",
                url = "https://github.com/Nyrvlivy/AluraChallenge"
        )
)
public class SwaggerConfig {
}
