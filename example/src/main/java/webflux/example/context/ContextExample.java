package webflux.example.context;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.resources.LoopResources;

@Slf4j
public class ContextExample {
    public static void main(String[] args) throws InterruptedException {
        Mono.deferContextual(ctx -> Mono.just("Hello " + ctx.get("firstname"))
            .doOnNext(data -> log.warn("# just doOnNext : {}", data))
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .transformDeferredContextual(
                (mono, ctx2) -> mono.map(data -> data + " " + ctx2.get("lastname"))
            )
            .contextWrite(context -> context.put("firstname", "jo"))
            .contextWrite(context -> context.put("lastname", "jaeng2")))
            .subscribe(data -> log.warn("# onNext :{}", data));

        Thread.sleep(2000L);
    }
}
