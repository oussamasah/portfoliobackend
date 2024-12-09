package dev.portfolio.oussama.service;

import dev.portfolio.oussama.model.Skills;
import dev.portfolio.oussama.repository.SkillsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillsService {
    private SkillsRepository repo;
    public SkillsService(SkillsRepository repo){
        this.repo=repo;
    }

    public Optional<Skills> save(Long id , String skill,boolean active,boolean checked,boolean multiword ,String lang){
        Optional<Skills> s = Optional.of(new Skills());
        if(id != null && id != 0){
            s = repo.findById(id);
        }
        s.get().setSkill(skill);
        s.get().setActive(active);
        s.get().setLang(lang);
        s.get().setMultiword(multiword);
        s.get().setChecked(checked);
        repo.save(s.get());
        return s;
    }

    public Optional<List> get(){
       List l = repo.findAll();
       return Optional.of(l);
    }
}
