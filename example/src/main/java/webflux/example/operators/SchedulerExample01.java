package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class SchedulerExample01 {
    public static void main(String[] args) {
        Flux.range(1, 7)
            .doOnNext(data -> log.info("# doOnNext from range : {}", data))
            .filter(data -> data > 3)
            .doOnNext(data -> log.info("# doOnNext filter : {}", data))
            .map(data -> data * 10)
            .doOnNext(data -> log.info("# doOnNext map : {}", data))
            .subscribe(data -> log.info("# onNext : {}", data));
    }
}
