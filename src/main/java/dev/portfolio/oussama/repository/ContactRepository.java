package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findById(Long id);
    List<Contact> findAllByOrderByIdAsc();
}