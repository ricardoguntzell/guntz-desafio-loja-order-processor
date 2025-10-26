package br.com.guntz.desafio.loja.order.processor.infrastructure.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do SpringDoc OpenAPI (Swagger) para o serviço Order Processor.
 *
 * Embora seja primariamente um Consumer de fila, esta configuração documenta qualquer
 * endpoint REST que o serviço possa expor (ex: /health, /metrics, ou APIs internas).
 */
@Configuration
public class DocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Configurações principais da API
                .info(new Info()
                        .title("GUNTZ | Order Processor API")
                        .version("v1.0.0")
                        .description("API e Backend Worker responsáveis por consumir e processar " +
                                     "os pedidos da fila. A documentação cobre endpoints de status e administração.")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}