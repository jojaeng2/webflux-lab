package webflux.example.testjpa;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "JPA_TEST")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JpaTestEntity {

    @Id
    @Column
    private String id = UUID.randomUUID().toString();

    @Column
    private String name;

    public JpaTestEntity(String name) {
        this.name = name;
    }
}
