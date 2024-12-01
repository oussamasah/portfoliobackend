package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.ServiceInfo.ServiceResponse;
import dev.portfolio.oussama.dto.SkillRequest;
import dev.portfolio.oussama.dto.SkillResponse;
import dev.portfolio.oussama.model.Skill;
import dev.portfolio.oussama.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
    private SkillService service;
    public SkillController(SkillService service){
        this.service=service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveSkill(   @RequestParam("id") String id,
                                          @RequestParam("name") String name,
                                          @RequestParam("category") String category,
                                          @RequestParam(value = "icon", required = false) MultipartFile icon){

        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/skill/"; // Relative path in the app directory
String iconPath="";


        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new SkillResponse("Failed to create upload directory"));
        }

        if (icon != null && !icon.isEmpty()) {
            try {
                // Avoid overwriting files with the same name
                String imageFilename = icon.getOriginalFilename();
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    icon.transferTo(uploadFile);  // Transfer file to the specified location
                    iconPath = ("/uploads/skill/" + imageFilename);  // Save the relative path for the image
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new ServiceResponse("Image upload failed: " + e.getMessage()));
            }
        }
        return this.service.saveSkill(id,name,category,iconPath).map(sk-> ResponseEntity.ok().body(new SkillResponse(sk))).orElseGet(()->ResponseEntity.status(403).body(new SkillResponse("Bad request")));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
       List l =  this.service.get();
       return ResponseEntity.ok().body(l);


    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
         this.service.delete(id);
        return ResponseEntity.ok().body(Map.of("message","Deleted successfuly"));


    }
}
