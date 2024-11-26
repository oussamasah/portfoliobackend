package dev.portfolio.oussama.dto.Auth;

import dev.portfolio.oussama.model.Welcome;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class WelcomeResponse {
    private String title;
    private String tags;
    private String description;
    private String image;
    private String cv;
    private String message;
    public WelcomeResponse(Welcome wlc) {
        this.title = wlc.getTitle();
        this.description = wlc.getDescription();
        this.tags = wlc.getTags();
        this.image = wlc.getImage();
        this.cv = wlc.getCv();
        this.message = "Welcome data saved successfuly";

    }
    public WelcomeResponse(String wlc) {
       this.message = wlc;
    }


}
