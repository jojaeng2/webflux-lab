package webflux.example.basic;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinkOneExample02 {

    public static void main(String[] args) {
        // emit 된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달한다. 나머지는 Drop 됨.
        Sinks.One<String> sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        // Sink.One은 단 한개의 데이터를 emit 할 수 있기 때문에 두 번째 emit 한 데이터는 drop 된다.
        sinkOne.emitValue("Hi Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> log.info("Subscriber 01 : {}", data));
        mono.subscribe(data -> log.info("Subscriber 02 : {}", data));
    }
}
