package webflux.example.basic.sinks;

import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;

@Slf4j
public class SinksExample01 {
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
            /*
                stream이 성공했을 경우에만 수행. exception은 처리 안함.
             */
            .handle((num, sinks) -> {
                log.warn(num);
                sinks.next(num);
                sinks.next(num);
            })
            .subscribe(
                (number) -> log.info("number: {}", number),
                (error) -> log.error("error: {}", error.getMessage()),
                () -> log.warn("success message")
            );
    }
}
