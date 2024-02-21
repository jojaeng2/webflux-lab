package webflux.example.boards.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
public class DescriptionResponse {
    private final String id;
    private final String title;
    private final String script;
    private final Date register;
}
