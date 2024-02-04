package webflux.example.boards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import webflux.example.boards.domain.Description;

public interface DescriptionRepository extends JpaRepository<Description, String> {
    List<Description> findByMemberId(String memberId);
}
