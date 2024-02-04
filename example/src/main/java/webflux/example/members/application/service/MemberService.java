package webflux.example.members.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import webflux.example.members.domain.Member;
import webflux.example.members.dto.MemberDTO;
import webflux.example.members.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
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
}
