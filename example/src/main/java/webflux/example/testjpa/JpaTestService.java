package webflux.example.testjpa;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaTestService {
    private final JpaTestRepository repository;

    @Transactional
    public void create() {
        JpaTestEntity entity = new JpaTestEntity("2");
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<JpaTestEntity> v1FindAll() {
        List<JpaTestEntity> result = repository.findAll();
        log.warn("v1FindAll DB I/O After");
        return result;
    }

    @Transactional(readOnly = true)
    public Mono<List<JpaTestEntity>> v2FindAll() {
        Mono<List<JpaTestEntity>> mono = Mono.fromCallable(() -> {
                log.warn("fromCallable");
                return repository.findAll();
            });
        log.warn("findAll After");
        return mono;
    }

    @Transactional(readOnly = true)
    public Mono<List<JpaTestEntity>> v3FindAll() {
        Mono<List<JpaTestEntity>> mono = Mono.fromCallable(() -> {
                log.warn("fromCallable");
                return repository.findAll();
            })
            .subscribeOn(Schedulers.parallel());
        log.warn("findAll After");
        return mono;
    }


    public void somethingParallelLogic() {
        log.warn("something ... ");
    }
}
