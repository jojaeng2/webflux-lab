package webflux.example.basic;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Sinks.One 예제
 * - 한 건의 데이터만 emit 하는 예제
 */
@Slf4j
public class SinkOneExample01 {
    public static void main(String[] args) {

        // emit 된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달한다. 나머지 데이터는 DROP 됨.
        Sinks.One<String> sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> log.info("Subscriber 01 : {}", data));
        mono.subscribe(data -> log.info("Subscriber 02 : {}", data));
    }
}
