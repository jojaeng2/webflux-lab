package webflux.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    public static final String boardsWebClient = "BOARDS_WEB_CLIENT";

    @Bean(boardsWebClient)
    public WebClient boardWebApiClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();
    }
}
