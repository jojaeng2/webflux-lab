package webflux.example.members.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import webflux.example.boards.dto.DescriptionResponse;
import webflux.example.config.WebClientConfig;
import webflux.example.members.domain.Member;
import webflux.example.members.dto.MemberDTO;
import webflux.example.members.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    @Qualifier(WebClientConfig.boardsWebClient)
    private final WebClient webClient;
    private final MemberRepository memberRepository;

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
    public Mono<List<DescriptionResponse>> findMemberDescriptions(String id) {
        return webClient.get()
            .uri("/descriptions/member/" + id)
            .retrieve()
            .bodyToFlux(DescriptionResponse.class)
            .collectList();

//        mono.subscribe(
//            dataList -> {
//                for (DescriptionResponse data : dataList) {
//                    log.warn(String.valueOf(data));
//                }
//            }
//        );
//
    }
}
