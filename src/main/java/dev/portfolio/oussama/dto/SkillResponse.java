package dev.portfolio.oussama.dto;

import dev.portfolio.oussama.model.Skill;
import lombok.Data;

@Data
public class SkillResponse {

    String name;
    String category;
    String icon;
    String message;
    public SkillResponse(Skill s ){

        this.name=s.getName();
        this.category=s.getCategory();
        this.icon=s.getIcon();
    }
    public SkillResponse(String s ){

        this.message=s;

    }

}
