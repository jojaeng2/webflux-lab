package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ParallelExample {

    public static void main(String[] args) throws InterruptedException {
        Flux.range(1, 10)
            .parallel(5)
            .runOn(Schedulers.parallel())
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(3000L);
    }
}
