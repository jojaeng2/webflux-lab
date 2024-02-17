package webflux.example.basic.retry;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;

@Slf4j
public class RetryExample01 {
    public static void main(String[] args) {
        Flux.just(6, 9, 13)
            .map((num) -> {
                if (num == 13) {
                    throw new RuntimeException("Exception");
                }
                return num;
            })
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
            .subscribe(
                (number) -> log.info("number : {}", number),
                (error) -> log.error("error: {}", error.getMessage()),
                () -> log.warn("success message")
            );
    }
}
