package webflux.example.operators;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class OperatorsMain {

    private static final FlatMapExample flatMapExample = new FlatMapExample();
    private static final FilterExample filterExample = new FilterExample();


    public static void main(String[] args) {
//        flatMapExample.emit()
//            .subscribe(result -> log.warn("#flatMapExample#num:{}", result));
        filterExample.emit()
            .subscribe(result -> log.warn("#filterMapExample#num:{}", result));
    }
}
