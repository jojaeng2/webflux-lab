package webflux.example.members.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import webflux.example.boards.dto.DescriptionResponse;
import webflux.example.members.application.service.MemberService;
import webflux.example.members.domain.Member;
import webflux.example.members.dto.MemberDTO;
import webflux.example.members.dto.MemberResponse;

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

    @GetMapping("/member/description/{id}")
    public Mono<List<DescriptionResponse>> getMemberDescriptionsById(@PathVariable String id) {
        return memberService.findMemberDescriptions(id);
    }

}
