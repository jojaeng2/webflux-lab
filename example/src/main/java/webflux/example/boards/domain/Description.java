package webflux.example.boards.domain;

import java.util.Date;
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
import webflux.example.boards.dto.DescriptionDTO;

@Table(name = "DCP")
@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Description {

    @Id
    @Builder.Default
    @Column(name = "DCP_ID")
    private String id = UUID.randomUUID().toString();

    @Column(name = "DCP_TITLE")
    private String title;

    @Column(name = "DCP_SCRIPT")
    private String script;

    @Builder.Default
    @Column(name = "DCP_REG_DATE")
    private Date register = new Date();

    public static Description create(DescriptionDTO dto) {
        return Description.builder()
            .title(dto.getTitle())
            .script(dto.getScript())
            .build();
    }
}
