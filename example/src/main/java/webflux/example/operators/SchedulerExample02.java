package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerExample02 {
    public static void main(String[] args) {
        Flux.range(1, 7)
            .doOnNext(data -> log.info("# doOnNext from range : {}", data))
            .publishOn(Schedulers.parallel())
            .filter(data -> data > 3)
            .doOnNext(data -> log.info("# doOnNext filter : {}", data))
            .map(data -> data * 10)
            .doOnNext(data -> log.info("# doOnNext map : {}", data))
            .subscribe(data -> log.info("# onNext : {}", data));
    }
}
