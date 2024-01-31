package webflux.example;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class FluxExample04 {
    public static void main(String[] args) {
        Flux.concat(
            Flux.just("Venus"),
            Flux.just("Earth"),
            Flux.just("Mars")
        )
            .collectList()
            .subscribe(pla -> log.info("S :{}", pla));
    }
}
