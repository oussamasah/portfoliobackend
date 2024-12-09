package dev.portfolio.oussama.service;

import dev.portfolio.oussama.model.Serv;
import dev.portfolio.oussama.model.ServiceInfo;
import dev.portfolio.oussama.model.Welcome;
import dev.portfolio.oussama.repository.ServiceInfoRepository;
import dev.portfolio.oussama.repository.ServiceRepository;
import dev.portfolio.oussama.repository.WelcomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<Serv> addservice(String title,String desc,String icon) {

        Serv entity = new Serv();
       entity.setIcon(icon);
       entity.setTitle(title);
       entity.setDescription(desc);
        serviceRepo.save(entity);
        System.out.println("-------------++++++++++++++"+entity.getTitle());
        return Optional.of(entity);



    }


    public List<Serv> get(){
        List<Serv> servs = serviceRepo.findAllByOrderByIdAsc();
        return servs;

    }

    public Optional<Serv> update(Long id, String title, String description, String iconPath) {
        // Check if the service exists
        Optional<Serv> servOpt = serviceRepo.findById(id);
        if (servOpt.isPresent()) {
            Serv serv = servOpt.get();
            serv.setTitle(title);
            serv.setDescription(description);
            System.out.println("**************************"+iconPath);
            if(!iconPath.isEmpty()){
                serv.setIcon(iconPath);  // If you're updating the icon

            }

            // Save the updated object
            serviceRepo.save(serv);
            return Optional.of(serv);
        } else {
            return Optional.empty();  // Return empty if the service is not found
        }
    }
    public boolean delete(Long id) {
        Optional<Serv> servOpt = serviceRepo.findById(id);
        if (servOpt.isPresent()) {
            serviceRepo.deleteById(id);  // Delete the record by its ID
            return true;
        }
        return false;  // Return false if the record was not found
    }
}
