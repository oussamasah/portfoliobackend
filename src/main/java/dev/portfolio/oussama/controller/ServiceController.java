package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.ServiceInfo.ServiceInfoResponse;
import dev.portfolio.oussama.dto.ServiceInfo.ServiceResponse;
import dev.portfolio.oussama.model.Serv;

import dev.portfolio.oussama.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceInfo;

    public ServiceController(ServiceService serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @GetMapping("/gettitle")
    public ResponseEntity<?> gettitle() {
        return serviceInfo.getTitle()
                .map(title -> ResponseEntity.ok(new ServiceInfoResponse(title)))
                .orElseGet(() -> ResponseEntity.status(401).body(new ServiceInfoResponse("Invalid credentials")));

    }
    @PostMapping("/savetitle")
    public ResponseEntity<?> save(String title) {
        return serviceInfo.saveTitle(title)
                .map(t-> ResponseEntity.ok(new ServiceInfoResponse(t,true)))
                .orElseGet(() -> ResponseEntity.status(401).body(new ServiceInfoResponse("Title not saved",false)));

    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        List<Serv> ServList = serviceInfo.get(); // Get the list of Welcome records
        System.out.println("***************"+ServList.toString());

        // Wrap the list in a custom response if needed
        return ResponseEntity.ok(new ServiceResponse(ServList)); // Return the list of Welcome records
    }


    // Method to update a service by id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {
        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/service/"; // Relative path in the app directory
        String iconPath = "";


        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ServiceResponse("Failed to create upload directory"));
        }

        if (icon != null && !icon.isEmpty()) {
            try {
                // Avoid overwriting files with the same name
                String imageFilename = icon.getOriginalFilename();
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    icon.transferTo(uploadFile);  // Transfer file to the specified location
                    iconPath = "/uploads/service/" + imageFilename;  // Save the relative path for the image
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new ServiceResponse("Image upload failed: " + e.getMessage()));
            }
        }

        Optional<Serv> updatedServ = serviceInfo.update(id, title, description, iconPath);

        if (updatedServ.isPresent()) {
            return ResponseEntity.ok(new ServiceResponse(updatedServ.get()));  // Return the updated service
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ServiceResponse("Service not found"));
        }
    }


    @PostMapping("/addservice")
    public ResponseEntity<?> addservice(@RequestParam("title") String title,

                                        @RequestParam("description") String description,
                                        @RequestParam(value = "icon", required = false) MultipartFile icon) {

        System.out.println("im here");

        String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/service/"; // Relative path in the app directory
        String iconPath = "";


        // Ensure the upload directory exists
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR)); // Create the directory if it doesn't exist
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ServiceResponse("Failed to create upload directory"));
        }

        if (icon != null && !icon.isEmpty()) {
            try {
                // Avoid overwriting files with the same name
                String imageFilename = icon.getOriginalFilename();
                if (imageFilename != null) {
                    File uploadFile = new File(UPLOAD_DIR, imageFilename);
                    icon.transferTo(uploadFile);  // Transfer file to the specified location
                    iconPath = "/uploads/service/" + imageFilename;  // Save the relative path for the image
                }
            } catch (IOException e) {
                return ResponseEntity.status(500).body(new ServiceResponse("Image upload failed: " + e.getMessage()));
            }
        }


        return serviceInfo.addservice(title,description,iconPath)
                .map(serv-> ResponseEntity.ok(new ServiceResponse(serv)))
                .orElseGet(() -> ResponseEntity.status(401).body(new ServiceResponse("Title not saved")));



    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        boolean isDeleted = serviceInfo.delete(id);

        if (isDeleted) {
            return ResponseEntity.ok(new ServiceResponse("Service deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ServiceResponse("Service not found"));
        }
    }
}
