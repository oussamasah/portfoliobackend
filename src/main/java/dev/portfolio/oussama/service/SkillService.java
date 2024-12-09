package dev.portfolio.oussama.service;

import dev.portfolio.oussama.dto.SkillRequest;
import dev.portfolio.oussama.model.Skill;
import dev.portfolio.oussama.repository.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    private SkillRepository repo;
    public SkillService(SkillRepository repo){
        this.repo =repo;
    }

    public Optional<Skill> saveSkill(String id,String name,String category,String icon){
       Long convetedId;
        try {
            convetedId = Long.valueOf(id);
        }catch (Exception e){
            convetedId = 0L;
        }
        Skill skill = this.repo.findById(convetedId).orElse(null);
        if(skill == null){
            skill = new Skill();
            skill.setName(name);
            skill.setCategory(category);
            skill.setIcon(icon);
        }


        this.repo.save(skill);

        return Optional.of(skill);


    }

    public List get(){

       List<Skill> list =  this.repo.findAll();
       return list;

    }
    public void delete(Long id){

       this.repo.deleteById(id);;

    }

}
