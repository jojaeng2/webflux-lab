package webflux.example.operators;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor
public class FlatMapExample {

    public Flux<Integer> emit() {
        return Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .flatMap(num ->  Flux.just(num * 2, num * 3, num * 4));
    }

    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .flatMap(num -> Mono.just(num * 2))
            .subscribe(num -> log.warn("flatMap number: {}", num));
    }
}
