package dev.portfolio.oussama.dto.ServiceInfo;

import dev.portfolio.oussama.model.Serv;
import lombok.Data;

import java.util.List;

@Data
public class ServiceResponse {
    private String title;
    private String desc;
    private String icon;
    private String message;
    private String error;
    private List data;
    public ServiceResponse(Serv s){

        this.title=s.getTitle();
        this.desc=s.getDescription();
        this.icon=s.getIcon();
        this.message="Service operation done";

    }
    public ServiceResponse(String s){

        this.error=s;

    }
    public ServiceResponse(List<Serv> servList) {
        this.message = "Services fetched successfully";
        this.data = servList;
    }
}
