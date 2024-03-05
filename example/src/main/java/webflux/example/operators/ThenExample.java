package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ThenExample {
    public static void main(String[] args) {
        Mono<String> strMono = Mono.just("TEST");
        Mono<Integer> intMono = Mono.just(1);
        Flux<String> flux = Flux.just("TEST", "ㅆㅆㅆ");

        Mono<Void> then = strMono.then();
        Mono<Integer> then2 = strMono.then(intMono);
        Mono<Void> then3 = flux.then();
        Mono<String> then4 = flux.then(strMono);


        then.subscribe(ss -> log.warn("then : {}", ss));
        then2.subscribe(ss -> log.warn("then2 : {}", ss));
        then3.subscribe(ss -> log.warn("then3 : {}", ss));
        then4.subscribe(ss -> log.warn("then4 : {}", ss));
    }
}
