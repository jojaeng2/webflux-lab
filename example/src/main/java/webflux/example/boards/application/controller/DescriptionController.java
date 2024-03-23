package webflux.example.boards.application.controller;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import reactor.core.publisher.Mono;
import webflux.example.boards.application.service.DescriptionService;
import webflux.example.boards.domain.Description;
import webflux.example.boards.dto.DescriptionDTO;
import webflux.example.boards.dto.DescriptionResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DescriptionController {
    private final DescriptionService descriptionService;

    @PostMapping("/description")
    public DescriptionResponse create(@RequestBody DescriptionDTO dto) {
        Description description = descriptionService.create(dto);
        return DescriptionResponse.builder()
            .id(description.getId())
            .title(description.getTitle())
            .script(description.getScript())
            .register(description.getRegister())
            .build();
    }

    @GetMapping("/description")
    public ResponseEntity<Description> getDescription() {
        return new ResponseEntity<>(Description.create(DescriptionDTO.builder()
            .title("T").script("script").memberId("memberId").build()) , null, HttpStatusCode.valueOf(500));
    }

    @GetMapping("/description/{id}")
    public DescriptionResponse getById(@PathVariable String id) {
        Description description = descriptionService.findById(id);
        return DescriptionResponse.builder()
            .id(description.getId())
            .title(description.getTitle())
            .script(description.getScript())
            .register(description.getRegister())
            .build();
    }

    @GetMapping("/descriptions/member/{id}")
    public List<DescriptionResponse> getByMemberId(@PathVariable String id) {
        log.warn("### DescriptionController#getByMemberId");
        return descriptionService.findByMemberId(id)
            .stream()
            .map(description -> DescriptionResponse.builder()
                    .id(description.getId())
                    .title(description.getTitle())
                    .script(description.getScript())
                    .register(description.getRegister())
                    .build()
            )
            .toList();
    }

    @GetMapping("/timeout/descriptions/member/{id}")
    public List<DescriptionResponse> timeoutTest(@PathVariable String id) throws InterruptedException {
        log.error("#timeout1: {}", id);
        Thread.sleep(40000L);
        log.error("#timeout2: {}", id);
        return descriptionService.findByMemberId(id)
            .stream()
            .map(description -> DescriptionResponse.builder()
                .id(description.getId())
                .title(description.getTitle())
                .script(description.getScript())
                .register(description.getRegister())
                .build()
            )
            .toList();
    }
}
