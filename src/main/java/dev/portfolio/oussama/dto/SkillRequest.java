package dev.portfolio.oussama.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SkillRequest {
    Long id = null;
    String name;
    String category;
    MultipartFile icon;
    String iconPath;
    public SkillRequest(String id ,String n ,String c,MultipartFile i ){
        this.id = Long.valueOf(id);
        this.name=n;
        this.category=c;
        this.icon=i;
    }



}
