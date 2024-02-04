package webflux.example.members.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {
    private final String id;
    private final String name;
    private final Integer age;
}
