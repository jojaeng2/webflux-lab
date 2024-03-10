package webflux.example.members.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public List<DescriptionResponse> getMemberDescriptionsTimeoutTest(@PathVariable String id) {
        return memberService.findMemberDescriptionsTimeoutTest(id);
    }

    @GetMapping("/members/descriptions")
    public Mono<List<MemberResponseWithDescriptions>> getMembersDescriptionsByIds(@RequestParam List<String> memberIds) {
        return memberService.findMembersDescription(memberIds);
    }

    @GetMapping("/members")
    public List<MemberResponse> getAllMembers() {
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

        Mono<List<DescriptionResponse>> response = memberService.findMemberDescription(id);
        log.warn("### MemberController#getMembeWithDescriptionsByMemberId#after");
        return response;
    }
}
