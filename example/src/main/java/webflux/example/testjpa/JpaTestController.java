package webflux.example.testjpa;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JpaTestController {

    private final JpaTestService service;

    @GetMapping("/v1/jpa/create")
    public String create() {
        service.create();
        return "ok";
    }

    @GetMapping("/v1/jpa/findall")
    public List<JpaTestEntity> v1FindAll() {
        log.warn("v1FindAll controller receive request");
        List<JpaTestEntity> result = service.v1FindAll();
        service.somethingParallelLogic();
        return result;
    }

    @GetMapping("/v2/jpa/findall")
    public Mono<List<JpaTestEntity>> v2FindAll() {
        log.warn("v2FindAll controller receive request");
        Mono<List<JpaTestEntity>> result = service.v2FindAll();
        service.somethingParallelLogic();
        return result;
    }

    @GetMapping("/v3/jpa/findall")
    public Mono<List<JpaTestEntity>> v3FindAll() {
        log.warn("v3FindAll controller receive request");
        Mono<List<JpaTestEntity>> result = service.v3FindAll();
        service.somethingParallelLogic();
        return result;
    }
}
