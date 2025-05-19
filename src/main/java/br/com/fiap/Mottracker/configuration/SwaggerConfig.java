package br.com.fiap.Mottracker.configuration;

import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge Mottu FIAP 2025 - Mottracker")
                        .description("""
                            Entrega do projeto Java para o Challenge FIAP 2025 com a empresa Mottu.

                            **Autores:**
                            - Daniel Saburo Akiyama (RM 558263)
                            - Danilo Correia e Silva (RM 557540)
                            - João Pedro Rodrigues da Costa (RM 558199)
                            """)
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("-"));
    }

    @Bean
    OpenApiCustomizer customerGlocalHeaderOpenApiCustomiser () {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> {

                        ApiResponses apiResponses = operation.getResponses();

                        apiResponses.addApiResponse("200", createApiResponse("Sucesso!", "Operação realizada com sucesso."));
                        apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido", "Recurso criado com sucesso."));
                        apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído", "Recurso excluído com sucesso."));

                        apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!", "{\"campo\": \"mensagem de erro\"}"));
                        apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!", "Token JWT inválido ou ausente."));
                        apiResponses.addApiResponse("403", createApiResponse("Acesso proibido!", "Você não tem permissão para acessar este recurso."));
                        apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!", "Recurso não encontrado: id=123"));
                        apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!", "Erro interno do servidor: NullPointerException"));

                    }));
        };
    }

    private ApiResponse createApiResponse(String message, String exampleValue) {
        return new ApiResponse()
                .description(message)
                .content(new Content().addMediaType("application/json",
                        new MediaType().addExamples("default",
                                new Example().value(exampleValue))));
    }
}
