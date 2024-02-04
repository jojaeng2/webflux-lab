package webflux.example.boards.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import webflux.example.boards.domain.Description;
import webflux.example.boards.dto.DescriptionDTO;
import webflux.example.boards.repository.DescriptionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    @Transactional
    public Description create(DescriptionDTO dto) {
        Description description = Description.create(dto);
        return descriptionRepository.save(description);
    }

    @Transactional(readOnly = true)
    public Description findById(String id) {
        return descriptionRepository.findById(id)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Description> findByMemberId(String memberId) {
        return descriptionRepository.findByMemberId(memberId);
    }

}
