package dev.portfolio.oussama.dto.Experience;

import dev.portfolio.oussama.model.Experience;
import lombok.Data;

import java.util.List;

@Data
public class ExperienceResponse {
    private Long id;
    private String name;
    private String daterange;
    private String description;
    private String error;
    private List data;
    public ExperienceResponse(Experience e){
       this.id =e.getId();
        this.name = e.getName();
        this.daterange=e.getDaterange();
        this.description=e.getDescription();
    }
    public ExperienceResponse(String e){
        this.error =e;

    }
    public ExperienceResponse(List e){
        this.data =e;

    }
}
