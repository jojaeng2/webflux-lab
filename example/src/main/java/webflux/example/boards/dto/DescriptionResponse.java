package webflux.example.boards.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DescriptionResponse {
    private final String id;
    private final String title;
    private final String script;
    private final Date register;
}
