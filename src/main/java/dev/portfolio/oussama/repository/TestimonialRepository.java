package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Testimonial;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {
    List<Testimonial> findByActive(boolean active);
}
