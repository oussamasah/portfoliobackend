package dev.portfolio.oussama.repository;

import dev.portfolio.oussama.model.Service;

import dev.portfolio.oussama.model.ServiceInfo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {
    Optional<ServiceInfo> findFirstByOrderByIdAsc();
}
