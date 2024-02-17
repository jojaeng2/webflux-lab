package webflux.example.basic;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class ErrorExample02 {

    public static void main(String[] args) {
        Flux.just(6, 9, 13)
            .map(num -> {
                throw new RuntimeException("Error!!");
            })
            .onErrorReturn(999999999999L)
            .subscribe(
                (number) -> log.info("number: {}", number),
                (error) -> log.error("error: {}", error.getMessage()),
                () -> log.warn("success message")
            );
    }
}
