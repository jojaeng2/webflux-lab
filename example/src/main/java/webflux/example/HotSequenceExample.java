package webflux.example;

import java.time.Duration;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class HotSequenceExample {
    public static void main(String[] args) {
        Flux<String> concertFlux =
            Flux.fromStream(Stream.of("Singer A", "Singer B","Singer C","Singer D","Singer E"))
                .delayElements(Duration.ofSeconds(1)).share();

        concertFlux.subscribe(singer -> log.info("S1:{}", singer));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        concertFlux.subscribe(singer -> log.info("S2:{}", singer));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
