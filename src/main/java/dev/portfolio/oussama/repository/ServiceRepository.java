package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Serv;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ServiceRepository extends JpaRepository<Serv, Long> {
    Optional<Serv> findById(Long id);
    List<Serv> findAllByOrderByIdAsc();
}
