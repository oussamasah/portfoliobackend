package dev.portfolio.oussama.service;

import dev.portfolio.oussama.dto.Project.ProjectRequest;
import dev.portfolio.oussama.model.Project;
import dev.portfolio.oussama.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    ProjectRepository repo;
    public ProjectService(ProjectRepository repo){
        this.repo=repo;
    }

    public Optional<Project> save(String id,String project,String title,String description ,String img){

        Long convetedId= 0L;
        try {
                convetedId = Long.valueOf(id);
        }catch (Exception e){
            convetedId = 0L;
        }

        Project proj = new Project();
        if(convetedId!=0) {
            // Update existing record
            proj = repo.findById(convetedId)
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));
        }
        proj.setProject(project);
        proj.setTitle(title);
        proj.setDescription(description);
        if(img!=null && !img.isEmpty()){
            proj.setImage(img);
        }

        this.repo.save(proj);
        return Optional.of(proj);
    }

    public Optional<List> getAll(){
        List data= this.repo.findAll();
        return Optional.of(data);
    }
    public void delete(Long id){


        this.repo.deleteById(id);

    }

    }
