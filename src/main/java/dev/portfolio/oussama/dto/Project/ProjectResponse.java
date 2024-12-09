package dev.portfolio.oussama.dto.Project;

import dev.portfolio.oussama.model.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {
    private Long id;
    private String project;
    private String title;
    private String description;
    private String image;
    private String error;
    private List data;

    public ProjectResponse(Project p){
        this.id=p.getId();
        this.project=p.getProject();
        this.title=p.getTitle();
        this.description=p.getDescription();
        this.image=p.getImage();
    }
    public ProjectResponse(String p){

        this.error=p;
    }
    public ProjectResponse(List p){

        this.data=p;
    }


}
