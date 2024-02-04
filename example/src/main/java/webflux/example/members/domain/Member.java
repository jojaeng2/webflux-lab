package webflux.example.members.domain;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import webflux.example.members.dto.MemberDTO;

@Table(name = "MBR")
@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @Builder.Default
    @Column(name = "MBR_ID")
    private String id = UUID.randomUUID().toString();

    @Column(name = "MBR_NM")
    private String name;

    @Column(name = "MBR_AGE")
    private Integer age;

    public static Member create(MemberDTO dto) {
        return Member.builder()
            .name(dto.getName())
            .age(dto.getAge())
            .build();
    }
}
