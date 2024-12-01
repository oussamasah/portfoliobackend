package dev.portfolio.oussama.dto.Experience;

import lombok.Data;

@Data
public class ExperienceRequest {

    private Long id;
    private String name;
    private String daterange;
    private String description;
    public ExperienceRequest(String id ,String name,String daterange,String description){
        try{
           this.id = Long.valueOf(id);
        }catch (Exception e){

        }
        this.name = name;
        this.daterange=daterange;
        this.description=description;
    }


}
