package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
public class DeferExample {

    public static void main(String[] args) {
        Mono<Integer> monoJust = Mono.just(5);
        Mono<Integer> monoDefer = Mono.defer(() -> Mono.just(5));

        monoJust.subscribe(System.out::println);
        monoDefer.subscribe(System.out::println);


    }
}
