package webflux.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class TWebClient{

    public void get() {
        WebClient client = WebClient.create();

        Mono<String> mono = client.get()
            .uri("/persons/{id}", "11")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);

    }

    @Bean("WEBCLIENT")
    public WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }
}
