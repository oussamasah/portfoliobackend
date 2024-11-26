package dev.portfolio.oussama.service;

import dev.portfolio.oussama.model.ServiceInfo;
import dev.portfolio.oussama.model.Welcome;
import dev.portfolio.oussama.repository.ServiceInfoRepository;
import dev.portfolio.oussama.repository.ServiceRepository;
import dev.portfolio.oussama.repository.WelcomeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepo;
    private final ServiceInfoRepository serviceInfoRepo;
    public ServiceService(ServiceRepository serviceRep,ServiceInfoRepository serviceinfoRep){
        this.serviceInfoRepo = serviceinfoRep;
        this.serviceRepo = serviceRep;
    }

    public Optional<String> getTitle(){
        Optional<ServiceInfo> serviceInfo = serviceInfoRepo.findFirstByOrderByIdAsc();
        return Optional.of(serviceInfo.get().getTitle());

    }
    public Optional<String> saveTitle(String title) {
        // Retrieve the first record or create a new one if not present
        Optional<ServiceInfo> serviceInfo = serviceInfoRepo.findFirstByOrderByIdAsc();
        ServiceInfo entity = serviceInfo.orElseGet(ServiceInfo::new);
        entity.setTitle(title);
        serviceInfoRepo.save(entity);
        return Optional.of(entity.getTitle());

    }
}
