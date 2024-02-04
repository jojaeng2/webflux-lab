package webflux.example.members.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private final String name;
    private final Integer age;
}
