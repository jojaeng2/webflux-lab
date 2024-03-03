package webflux.example.operators;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
public class ZipWhenExample {

    public static void main(String[] args) {
        Mono.just("id1")
            .zipWhen(id -> Mono.just(id + " name"))
            .flatMap(tuple -> {
                String id = tuple.getT1();
                String name = tuple.getT2();
                return Mono.just(id + " " + name);
            })
            .subscribe(result -> log.warn(result));
    }

    public void test(String memberId) {
        Mono<String> member = WebClient.create().get()
            .uri("http://localhost:8080/member/" + memberId)
            .retrieve()
            .bodyToMono(String.class);

        member.zipWhen(info -> WebClient.create().get()
            .uri("http://localhost:8080/comment" + info)
            .retrieve()
            .bodyToMono(String.class)
        ).flatMap(tuple -> {
            String memberInfo = tuple.getT1();
            String commentInfo = tuple.getT2();
            return Mono.just(memberInfo + "??" + commentInfo);
        });
    }
}
