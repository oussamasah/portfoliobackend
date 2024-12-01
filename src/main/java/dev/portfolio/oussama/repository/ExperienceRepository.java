package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Experience;
import dev.portfolio.oussama.model.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

}
