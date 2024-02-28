package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SwitchIfEmptyExample {

    public static void main(String[] args) {
        Flux.just(-1, -2, -3)
            .filter(num -> num >= 0)
            .switchIfEmpty(Flux.just(1, 2, 3))
            .subscribe(result -> log.warn("#switchIfEmpty ## result : {}", result));
    }
}
