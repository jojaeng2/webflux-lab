package webflux.example;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class ColdSequenceExample {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
            .map(String::toLowerCase);

        coldFlux.subscribe(country -> log.info("S1:{}", country));
        log.info("----------------------");
        coldFlux.subscribe(country -> log.info("S2:{}", country));
    }
}
