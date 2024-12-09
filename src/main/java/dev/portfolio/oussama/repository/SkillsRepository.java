package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Skills;
import dev.portfolio.oussama.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills,Long> {
    List<Skills> findByActive(boolean active);
    List<Skills> findByChecked(boolean checked);
    List<Skills> findByMultiword(boolean multiword);
}
