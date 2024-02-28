package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FilterWhenExample {

    public static void main(String[] args) {
        Flux.just(-1, 0, 1)
            .filterWhen(num -> Mono.just(num > 0))
            .subscribe(result -> log.warn("#switchIfEmpty ## result : {}", result));
    }
}
