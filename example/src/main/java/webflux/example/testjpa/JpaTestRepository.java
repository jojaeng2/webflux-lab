package webflux.example.testjpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestRepository extends JpaRepository<JpaTestEntity, String> {
}
