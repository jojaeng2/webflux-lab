package webflux.example.forwebclient;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ClientController {

    @GetMapping("/zip")
    public Mono<String> zip() {
        log.warn("#ClientController ## responseA ### Before");
        Mono<String> responseA = WebClient.create().get()
            .uri("http://localhost:8080/a/geta")
            .retrieve()
            .bodyToMono(String.class);
        log.warn("#ClientController ## responseA ### After");
        log.warn("#ClientController ## responseB ### Before");
        Mono<String> responseB = WebClient.create().get()
            .uri("http://localhost:8080/b/getb")
            .retrieve()
            .bodyToMono(String.class);
        log.warn("#ClientController ## responseB ### After");
        return Mono.zip(responseA, responseB)
            .map(tuple -> tuple.getT1() + tuple.getT2());
    }

    @GetMapping("/then")
    public Mono<String> then() {
        Mono<String> then = WebClient.create().get()
            .uri("http://localhost:8080/a/geta")
            .retrieve()
            .bodyToMono(String.class)
            .then(WebClient.create().get()
                .uri("http://localhost:8080/b/getb")
                .retrieve()
                .bodyToMono(String.class));
        return then;
    }

    @GetMapping("/zipWhen")
    public Mono<String> zipWhen() {
        Mono<Date> mono = WebClient.create().get()
            .uri("http://localhost:8080/a/time").retrieve()
            .bodyToMono(Date.class);

        return mono.zipWhen(date -> WebClient.create().get()
            .uri("http://localhost:8080/b/convert/" + date)
            .retrieve()
            .bodyToMono(String.class))
            .flatMap(tuple -> Mono.just(tuple.getT2()));
    }

    @GetMapping("/doOn")
    public void doOn() {
        Flux.range(1, 10)
            .doOnNext(System.out::println)
            .doOnError(System.out::println)
            .doOnComplete(System.out::println)
            .subscribe();
    }
}
