package webflux.example.basic;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class ErrorExample03 {
    public static void main(String[] args) {
        Flux.just("200", "401")
            .map(num -> {
                if (num == "200") {
                    return num;
                }
                throw new RuntimeException(num);
            })
            .onErrorMap(throwable -> {
                switch (throwable.getMessage()) {
                    case "301":
                        return new Exception("redirect");
                    case "401":
                        return new Exception("인증 에러");
                    case "409":
                        return new Exception("내부 conflict");
                    case "500":
                        return new Exception("내부 서버 에러");
                }
                return throwable;
            })
            .subscribe(
                (number) -> log.info("number: {}", number),
                (error) -> log.error("error: {}", error.getMessage()),
                () -> log.warn("success message")
            );
    }
}
