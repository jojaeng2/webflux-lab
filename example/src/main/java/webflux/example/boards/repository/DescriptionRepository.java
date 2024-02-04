package webflux.example.boards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import webflux.example.boards.domain.Description;

public interface DescriptionRepository extends JpaRepository<Description, String> {
}
