package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class DoOnNextExample {
    public static void main(String[] args) {
        Flux.just(1, 2, 3)
            .doOnNext(number -> log.warn("#flatMap ##Before ###number : {}", number))
            .flatMap(number -> Mono.just(number * 2))
            .doOnNext(number -> log.warn("#flatMap ##After ###number : {}", number))
            .subscribe(number -> log.warn("#subscribe ##number : {}", number));
    }
}
