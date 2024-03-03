package webflux.example.operators;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ZipExample {
    public static void main(String[] args) {
        Flux<Integer> flux1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> flux2 = Flux.just(-1, -2, -3, -4);

        Flux.zip(flux1, flux2)
            .map(tuple -> tuple.getT1() * tuple.getT2())
            .subscribe(result -> log.warn("result : {}", result));

    }

    public void test(String memberId, String commentId) {
        Mono<String> member = WebClient.create().get()
            .uri("http://localhost:8080/member/" + memberId)
            .retrieve()
            .bodyToMono(String.class);

        Mono<String> comment = WebClient.create().get()
            .uri("http://localhost:8080/comment" + commentId)
            .retrieve()
            .bodyToMono(String.class);

        Mono.zip(member, comment)
            .map(tuple -> tuple.getT1() + tuple.getT2())
            .subscribe(result -> log.warn("result : {}", result));
    }
}
