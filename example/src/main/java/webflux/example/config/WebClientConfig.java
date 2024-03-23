package webflux.example.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    public static final String boardsWebClient = "BOARDS_WEB_CLIENT";

    @Bean(boardsWebClient)
    public WebClient boardWebApiClient(HttpClient httpClient) {
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:8080")
            .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create()
            .doOnConnected(conn -> conn
                .addHandler(new ReadTimeoutHandler(3, TimeUnit.SECONDS)))
            .responseTimeout(Duration.ofSeconds(3L));

    }
}
