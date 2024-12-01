package dev.portfolio.oussama.dto.welcome;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class WelcomeRequest {
    private String title;
    private String tags;
    private String description;
    private Optional<MultipartFile> image;
    private Optional<MultipartFile> cv;
}
