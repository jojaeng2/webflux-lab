package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class FilterExample {

    public static void main(String[] args) {
    }

    public Flux<Integer> emit() {
        log.warn("#FilterExample emit # before");
        Flux<Integer> filter = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(num -> num % 2 == 0);
        log.warn("#FilterExample emit # After");
        return filter;
    }
}
