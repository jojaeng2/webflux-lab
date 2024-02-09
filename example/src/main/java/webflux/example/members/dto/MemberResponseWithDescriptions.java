package webflux.example.members.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

import webflux.example.boards.dto.DescriptionResponse;

@Builder
@Getter
public class MemberResponseWithDescriptions {
    private MemberResponse memberResponse;
    private List<DescriptionResponse> descriptions;
}
