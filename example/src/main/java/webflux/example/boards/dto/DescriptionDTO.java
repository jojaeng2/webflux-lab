package webflux.example.boards.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DescriptionDTO {
    private final String title;
    private final String script;
    private final String memberId;
}
