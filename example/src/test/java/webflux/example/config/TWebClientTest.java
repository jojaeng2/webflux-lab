package webflux.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class TWebClientTest {

    @Autowired
    private WebClient client;

    @Test
    void test() {
//        Mono<String> mono = client.get()
//            .uri("/persons/{id}", "11")
//            .accept(MediaType.APPLICATION_JSON)
//            .retrieve()
//            .bodyToMono(String.class)
//                .doOnSuccess();
//        System.out.println("mono.do = " + mono.do);
        StepVerifier.create(Flux.just("1"))
            .expectNext("foo", "bar")
            .verifyError(RuntimeException.class);

    }
}
