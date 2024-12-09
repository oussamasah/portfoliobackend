package dev.portfolio.oussama.service;

import dev.portfolio.oussama.dto.Experience.ExperienceRequest;
import dev.portfolio.oussama.model.Experience;
import dev.portfolio.oussama.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {
    ExperienceRepository repo;
    public ExperienceService(ExperienceRepository rep){
        this.repo=rep;
    }

    public Optional<Experience> saveExperice(ExperienceRequest e){
        Experience exp = new Experience();

        if (e.getId() != null) {
            // Update existing record
            exp = repo.findById(e.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Experience not found with id: " + e.getId()));
        }


        exp.setName(e.getName());
        exp.setDaterange(e.getDaterange());
        exp.setDescription(e.getDescription());
        this.repo.save(exp);
        return Optional.of(exp);
    }

    public Optional<List> get(){


       List exps =  this.repo.findAll();
        return Optional.of(exps);
    }


    public void delete(Long id){


        this.repo.deleteById(id);

    }
}
