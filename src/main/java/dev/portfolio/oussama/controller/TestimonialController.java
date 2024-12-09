package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.ServiceInfo.ServiceResponse;
import dev.portfolio.oussama.dto.SkillResponse;
import dev.portfolio.oussama.service.TestimonialService;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/testimonial")
public class TestimonialController {
    private final  TestimonialService service;
    public TestimonialController(TestimonialService service){
        this.service=service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("description") String description,
                                  @RequestParam(value = "photo", required = false) MultipartFile photo
                                  ){
        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/testimonial/"; // Relative path in the app directory
        String imagePath="";


        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new SkillResponse("Failed to create upload directory"));
        }

        if (photo != null && !photo.isEmpty()) {
            try {
                String extension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf('.') + 1);
                // Avoid overwriting files with the same name
                String imageFilename = Instant.now().getEpochSecond() +"."+  extension;
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    photo.transferTo(uploadFile);  // Transfer file to the specified location
                    imagePath = ("/uploads/testimonial/" + imageFilename);  // Save the relative path for the image


                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new ServiceResponse("Image upload failed: " + e.getMessage()));
            }

        }
        System.out.println("-------------------name"+name);
    return this.service.save(name ,email,description,imagePath).map(t->ResponseEntity.ok().body(Map.of("message","Testimonial added successfuly it will appear after verification"))).orElseGet(()->ResponseEntity.status(403).body(Map.of("message","error testimonial not saved")));


    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        Optional<List> list= this.service.get();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/getActive")
    public ResponseEntity<?> getactive(){
        Optional<List> list= this.service.getActive();
        return ResponseEntity.ok().body(list);
    }
    @PutMapping("/changestate/{id}")
    public ResponseEntity<?> changeState(@PathVariable Long id, @RequestParam("active") String active){
        System.out.println("-----------------------"+active);
        if(id==null){
            return ResponseEntity.status(403).body(Map.of("error","Testimonial not exist"));
        }
        System.out.println("!active.equals(true)"+!active.equals("true"));
        System.out.println("!active.equals(false)"+!active.equals("false"));
        if( !active.equals("true") && !active.equals("false")){
            return ResponseEntity.status(403).body(Map.of("error","Bad request ----------"));

        }
        Boolean activebolean = Boolean.valueOf(active);
        System.out.println("activebolean"+activebolean);
        this.service.changeState(id,activebolean);
        return ResponseEntity.ok().body(Map.of("message","State changed successfuly"));
    }
}
