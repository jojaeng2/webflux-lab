package webflux.example;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class BackPressureExample01 {
    public static void main(String[] args) {
        Flux.range(1, 5)
            .doOnNext(data -> log.info("doOnNext={}", data))
            .doOnRequest(data -> log.info("doOnRequest={}", data))
            .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    request(1);
                }
            });
    }
}
