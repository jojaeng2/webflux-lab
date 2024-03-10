package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishOnExample {
    public static void main(String[] args) throws InterruptedException {
        Flux.just(1, 3, 5, 7)
            .doOnNext(data -> log.info("# doOnNext: {}", data))
            .doOnSubscribe(subscription -> log.info("# doOnSubscribe: {}"))
            .publishOn(Schedulers.parallel())
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(5000L);
    }
}
