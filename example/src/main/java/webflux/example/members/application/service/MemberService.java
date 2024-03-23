package webflux.example.members.application.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.RetryBackoffSpec;
import reactor.util.retry.RetrySpec;
import webflux.example.boards.dto.DescriptionResponse;
import webflux.example.config.WebClientConfig;
import webflux.example.members.domain.Member;
import webflux.example.members.dto.MemberDTO;
import webflux.example.members.dto.MemberResponse;
import webflux.example.members.dto.MemberResponseWithDescriptions;
import webflux.example.members.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    @Qualifier(WebClientConfig.boardsWebClient)
    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final RetryBackoffSpec retrySpec = RetrySpec
        .fixedDelay(2, Duration.ofSeconds(1))
        .doBeforeRetry(signal -> log.warn("attempted retry"));

    @Transactional
    public Member join(MemberDTO dto) {
        Member member = Member.create(dto);
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findById(String id) {
        return memberRepository.findById(id)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mono<List<DescriptionResponse>> findMemberDescriptionsNonBlocking(String id) {
        log.warn("#### NonBlocking Before");
        Mono<List<DescriptionResponse>> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .exchangeToMono(clientResponse -> clientResponse.bodyToFlux(DescriptionResponse.class)
                .map(descriptionResponse -> {
                    if (clientResponse.statusCode() != HttpStatus.OK) {
                        return new DescriptionResponse(null, null, null, null);
                    }
                    return descriptionResponse;
                })
                .collectList()
            );
        log.warn("#### NonBlocking After");
        return response;
    }

    @Transactional(readOnly = true)
    public List<DescriptionResponse> findMemberDescriptionBlocking(String id) {
        log.warn("#### Blocking Before");
        List<DescriptionResponse> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList()
            .block(Duration.ofSeconds(1));
        log.warn("#### Blocking After");
        return response;
    }

    public List<DescriptionResponse> v1TimeoutTest() {
        return webClient.get()
            .uri("/timeout/descriptions/member/8ae03ee1-57e2-48c7-b8a3-af3ca7d824d8")
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList()
            .block();
    }

    @Transactional(readOnly = true)
    public Mono<List<MemberResponseWithDescriptions>> findMembersDescription(List<String> ids) {


        return Flux.fromIterable(ids)
            .flatMap(id -> {
                    log.warn("MemberService ### webClient ### Before");

                    Mono<List<DescriptionResponse>> response = webClient.get()
                        .uri("/descriptions/member/" + id)
                        .retrieve()
                        .bodyToFlux(DescriptionResponse.class)
                        .collectList()
                        .retryWhen(retrySpec);

                    Mono<Member> member = Mono.fromCallable(() -> memberRepository.findById(id).orElse(null));

                    return Mono.zip(response, member)
                        .map(tuple -> MemberResponseWithDescriptions.builder()
                            .memberResponse(MemberResponse.builder()
                                .id(tuple.getT2().getId())
                                .name(tuple.getT2().getName())
                                .age(tuple.getT2().getAge())
                                .build())
                            .descriptions(tuple.getT1())
                            .build());
                }
                )
            .collectList();
    }

    @Transactional(readOnly = true)
    public Mono<List<DescriptionResponse>> findMemberDescription(String id) {
        Mono<List<DescriptionResponse>> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList()
            .retryWhen(retrySpec);



        log.warn("### MemberService#findMemberDescription#memberRepository#findById Before");
        Member member = memberRepository.findById(id).orElse(null);
        log.warn("### MemberService#findMemberDescription#memberRepository#findById After : {}", member.getId());
        return response;
    }

    @Transactional(readOnly = true)
    public Mono<List<DescriptionResponse>> findMemberDescriptionsTimeoutTest(String id) throws InterruptedException {
        log.warn("#### NonBlocking Before");

        Mono<List<DescriptionResponse>> mono = webClient.get()
            .uri("/timeout/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList();

        log.warn("#### NonBlocking After");
        return mono;
    }

    public Mono<String> timeoutTest() {
        return Mono.just("value")
            .subscribeOn(Schedulers.parallel())
            .map(data -> {
                try {
                    log.error("new thread start");
                    Thread.sleep(40000L);
                    log.error("new thread end");
                } catch (InterruptedException e) {
                    log.error("interruptedException: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
                return data;
            });
    }

    public Mono<String> blockTest() {
        return Mono.just("value")
            .subscribeOn(Schedulers.parallel())
            .map(data -> {
                try {
                    log.warn("# Blocking Test ## 2");
                    Thread.sleep(10000L);
                    log.warn("# Blocking Test ## 3");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return data;
            });
    }

    public List<Integer> subscribeTest() throws InterruptedException {
        log.warn("subscribeTest service Before");
        ArrayList<Integer> list = new ArrayList<>();
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .subscribe(data -> {
                log.warn("subscribe data :{}", data);
                list.add(data);
            });
        log.warn("subscribeTest service After");
        System.out.println("list.size() = " + list.size());
        return list;
    }

    public Mono<List<DescriptionResponse>> v2FindMemberDescription(String id) throws InterruptedException {
        log.warn("# v2FindMemberDescription ## Before");
        Thread.sleep(1000L);
        Mono<List<DescriptionResponse>> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList();

        log.warn("# v2FindMemberDescription ## After");
        return response;
    }

    public List<DescriptionResponse> v3FindMemberDescription(String id) throws InterruptedException {
        log.warn("# v3FindMemberDescription ## Before");
        List<DescriptionResponse> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .map(data -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return data;
            })
            .collectList()
                .block();

        log.warn("# v3FindMemberDescription ## After");
        return response;
    }

    public List<String> v1SubscribeTest() {
        List<String> result = new ArrayList<>();

        webClient.get()
            .uri("http://localhost:8080/test-for-member")
            .retrieve()
            .bodyToFlux(String.class)
                .subscribe(data -> {
                    log.warn("subscribe :{}", data);
                    result.add(data);
                });
//
//        flux
//            .collectList();
////            .subscribe(data -> {
////            log.warn("data : {}", data);
////            result.add(data);
////
////        });
        log.warn("v1SubscribeTest Service Layer Result Size: {}", result.size());

        return result;
    }
}
