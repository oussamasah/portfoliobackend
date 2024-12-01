package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.welcome.WelcomeResponse;
import dev.portfolio.oussama.service.WelcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {
    private static final String UPLOAD_DIR = "uploads\\welcome\\";
    private final WelcomeService welcomeService;
    public WelcomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return welcomeService.get()
                .map(welcome -> ResponseEntity.ok(new WelcomeResponse(welcome)))
                .orElseGet(() -> ResponseEntity.status(401).body(new WelcomeResponse("Invalid credentials")));

    }
        @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam("title") String title,
                                  @RequestParam("tags") String tags,
                                  @RequestParam("description") String description,
                                  @RequestParam(value = "image", required = false) MultipartFile image,
                                  @RequestParam(value = "cv", required = false) MultipartFile cv) {

        // Define the relative upload directory inside your application directory
        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/welcome/"; // Relative path in the app directory
        String imgPath = "";
        String cvPath = "";

        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new WelcomeResponse("Failed to create upload directory"));
        }

        if (image != null && !image.isEmpty()) {
            try {
                // Avoid overwriting files with the same name
                String imageFilename = image.getOriginalFilename();
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    image.transferTo(uploadFile);  // Transfer file to the specified location
                    imgPath = "/uploads/welcome/" + imageFilename;  // Save the relative path for the image
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new WelcomeResponse("Image upload failed: " + e.getMessage()));
            }
        }

        if (cv != null && !cv.isEmpty()) {
            try {
                // Avoid overwriting files with the same name
                String cvFilename = cv.getOriginalFilename();
                if (cvFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, cvFilename);
                    cv.transferTo(uploadFile);  // Transfer file to the specified location
                    cvPath = "/uploads/welcome/" + cvFilename;  // Save the relative path for the CV
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new WelcomeResponse("CV upload failed: " + e.getMessage()));
            }
        }

        return welcomeService.save(title, tags, description, imgPath, cvPath)
                .map(welcome -> ResponseEntity.ok(new WelcomeResponse(welcome)))
                .orElseGet(() -> ResponseEntity.status(401).body(new WelcomeResponse("Invalid credentials")));
    }

}
