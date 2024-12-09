package dev.portfolio.oussama.dto.Project;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectRequest {
    private Long id;
    private String project;
    private String title;
    private String description;
    private MultipartFile image; // Change from String to MultipartFile
    private String imgPath; // Change from String to MultipartFile

    public ProjectRequest(String id, String project, String title, String description) {
        try {
            this.id = Long.valueOf(id);
        } catch (Exception e) {
            // Handle invalid id conversion
        }
        this.project = project;
        this.title = title;
        this.description = description;
    }
    public ProjectRequest( String project, String title, String description) {

        this.project = project;
        this.title = title;
        this.description = description;
    }
}
