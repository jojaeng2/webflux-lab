package webflux.example.members.application.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.netty.channel.ConnectTimeoutException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
        Mono<List<DescriptionResponse>> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList();
        log.warn("findMemberDescriptionsNonBlocking");
        return response;
    }

    @Transactional(readOnly = true)
    public List<DescriptionResponse> findMemberDescriptionBlocking(String id) {
        List<DescriptionResponse> response = webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList()
            .block(Duration.ofSeconds(3));
        log.warn("findMemberDescriptionBlocking");
        return response;
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
}
