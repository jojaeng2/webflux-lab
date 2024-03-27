package webflux.example.members.application.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.example.boards.dto.DescriptionResponse;
import webflux.example.members.application.service.MemberService;
import webflux.example.members.domain.Member;
import webflux.example.members.dto.MemberDTO;
import webflux.example.members.dto.MemberResponse;
import webflux.example.members.dto.MemberResponseWithDescriptions;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public MemberResponse join(@RequestBody MemberDTO dto) {
        Member member = memberService.join(dto);
        return MemberResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .age(member.getAge())
            .build();
    }

    @GetMapping("/member/{id}")
    public MemberResponse getById(@PathVariable String id) {
        Member member = memberService.findById(id);
        return MemberResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .age(member.getAge())
            .build();
    }

    @GetMapping("/nonblock/member/description/{id}")
    public Mono<List<DescriptionResponse>> getMemberDescriptionsByIdNonBlocking(@PathVariable String id) {
        return memberService.findMemberDescriptionsNonBlocking(id);
    }

    @GetMapping("/block/member/description/{id}")
    public List<DescriptionResponse> getMemberDescriptionsByBlocking(@PathVariable String id) {
        return memberService.findMemberDescriptionBlocking(id);
    }

    @GetMapping("/timeout/member/description/{id}")
    public Mono<List<DescriptionResponse>> getMemberDescriptionsTimeoutTest(@PathVariable String id) throws InterruptedException {
        return memberService.findMemberDescriptionsTimeoutTest(id);
    }

    @GetMapping("/members/descriptions")
    public Mono<List<MemberResponseWithDescriptions>> getMembersDescriptionsByIds(@RequestParam List<String> memberIds) {
        return memberService.findMembersDescription(memberIds);
    }

    @GetMapping("/members")
    public List<MemberResponse> getAllMembers() {
        log.warn("members");
        List<Member> members = memberService.findAllMembers();
        return members.stream()
            .map(member -> MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .age(member.getAge())
                .build())
            .toList();
    }

    @GetMapping("/member/descriptions/{id}")
    public Mono<List<DescriptionResponse>> getMemberWithDescriptionsByMemberId(@PathVariable String id) {
        log.warn("### MemberController#getMembeWithDescriptionsByMemberId#before");
        Mono<List<DescriptionResponse>> response = memberService.findMemberDescription(id);
        log.warn("### MemberController#getMembeWithDescriptionsByMemberId#after");
        return response;
    }



    @GetMapping("/timeout1")
    public String timeoutTest1() {
        return memberService.timeoutTest()
            .block();
    }

    @GetMapping("/timeout2")
    public Mono<String> timeoutTest2() throws InterruptedException {
        log.error("timeoutTest2");
//        Thread.sleep(40000L);
        return memberService.timeoutTest();
    }

    @GetMapping("/blockTest")
    public String blockTest() {
        log.warn("# Blocking Test ## 1");
        String result = memberService.blockTest().block();
        log.warn("# Blocking Test ## 4");
        return result;
    }

    @GetMapping("/block")
    public String block() {
        Mono.delay(Duration.ofSeconds(1))
            .doOnNext(it -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            })
            .block();
        return "HEllo";
    }

    @GetMapping("/v2/member/descriptions/{id}")
    public Mono<List<DescriptionResponse>> v2GetMemberWithDescriptionsByMemberId(@PathVariable String id) throws InterruptedException {
        log.warn("# v2GetMembeWithDescriptionsByMemberId ## Before");
        Mono<List<DescriptionResponse>> response = memberService.v2FindMemberDescription(id);
        log.warn("# v2GetMembeWithDescriptionsByMemberId ## After");
        return response;
    }

    @GetMapping("/v3/member/descriptions/{id}")
    public List<DescriptionResponse> v3GetMemberWithDescriptionsByMemberId(@PathVariable String id) throws InterruptedException {
        log.warn("# v2GetMembeWithDescriptionsByMemberId ## Before");
        List<DescriptionResponse> response = memberService.v3FindMemberDescription(id);
        log.warn("# v2GetMembeWithDescriptionsByMemberId ## After");
        return response;
    }

    @GetMapping("/v1/subscribeTest")
    public List<String> v1SubscribeTest() throws InterruptedException {
        List<String> result = memberService.v1SubscribeTest();
        log.warn("v1SubscribeTest Controller Layer Result Size : {}", result.size());
        return result;
    }

    @GetMapping("/v1/timeouttest")
    public List<DescriptionResponse> v1TimeoutTest() {
        return memberService.v1TimeoutTest();
    }

    @GetMapping("/v1/for-readtimeout")
    public void v1ForReadTimeout() throws InterruptedException {
        log.warn("for-read-timeout-before");
        Thread.sleep(40000L);
        log.warn("for-read-timeout-after");
    }

    @GetMapping("/sample/suffix")
    public String getSuffix() {
        return "suffix-";
    }

    @GetMapping("/block/webflux")
    public String blockWebFlux() {
        return memberService.blockWebFlux();
    }
}
