package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.User;
import dev.portfolio.oussama.model.Welcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WelcomeRepository extends JpaRepository<Welcome, Long> {
    Optional<Welcome> findFirstByOrderByIdAsc();

}
