package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class DefaultIfEmptyExample {

    public static void main(String[] args) {
        Flux.just()
            .defaultIfEmpty(0)
            .subscribe(number -> log.warn("#defaultIfEmpty #number : {}", number));
    }
}
