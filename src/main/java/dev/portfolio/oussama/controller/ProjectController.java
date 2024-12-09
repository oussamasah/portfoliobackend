package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.Project.ProjectRequest;
import dev.portfolio.oussama.dto.Project.ProjectResponse;
import dev.portfolio.oussama.dto.ServiceInfo.ServiceResponse;
import dev.portfolio.oussama.dto.SkillResponse;
import dev.portfolio.oussama.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    ProjectService service;
    public ProjectController(ProjectService service){
        this.service=service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save( @RequestParam("id") String id,
                                   @RequestParam("project") String project,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam(value = "image", required = false) MultipartFile image
) {
        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/project/"; // Relative path in the app directory
        String imagePath="";


        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new SkillResponse("Failed to create upload directory"));
        }

        if (image != null && !image.isEmpty()) {
            try {
                // Avoid overwriting files with the same name

                String extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.') + 1);
                String imageFilename = Instant.now().getEpochSecond() +"."+  extension;
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    image.transferTo(uploadFile);  // Transfer file to the specified location
                    imagePath = ("/uploads/project/" + imageFilename);  // Save the relative path for the image


                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new ServiceResponse("Image upload failed: " + e.getMessage()));
            }

        }
        System.out.println("**************************"+id.toString());
        return this.service.save(id,project,title,description,imagePath).map(proj->ResponseEntity.ok().body(new ProjectResponse(proj))).orElseGet(()->ResponseEntity.status(403).body(new ProjectResponse("Bad request")));

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return this.service.getAll().map(d->ResponseEntity.ok().body(new ProjectResponse(d))).orElseGet(()->ResponseEntity.status(403).body(new ProjectResponse("no data found")));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        this.service.delete(id);
        return  ResponseEntity.ok().body(Map.of("message","Project Deleted successefuly"));
    }
}
