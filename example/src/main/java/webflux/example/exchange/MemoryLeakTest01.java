package webflux.example.exchange;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import webflux.example.config.WebClientConfig;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemoryLeakTest01 {

    @Qualifier(WebClientConfig.boardsWebClient)
    private final WebClient webClient;

//    public Mono<ResponseEntity<String>> execute() {
//        return webClient.get()
//            .uri("/description")
//            .exchangeToMono(clientResponse -> {
//                if (clientResponse.statusCode() == HttpStatus.OK) {
//                    clientResponse.bodyToMono(String.class);
//                }
//                else {
//                    return Mono.error(clientResponse.createException());
//                }
//            });
//
//    }
}
