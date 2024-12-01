package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.Experience.ExperienceRequest;
import dev.portfolio.oussama.dto.Experience.ExperienceResponse;
import dev.portfolio.oussama.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {
    ExperienceService service;
    public ExperienceController(ExperienceService service){
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ExperienceRequest e){
        if (e.getName() == null || e.getDaterange() == null || e.getDescription() == null) {
            return ResponseEntity.badRequest().body(new ExperienceResponse("Invalid input data"));
        }
        System.out.println("data passed succesfult ***************************************");
       return this.service.saveExperice(e).map(exp->ResponseEntity.ok().body(new ExperienceResponse(exp))).orElseGet(()->ResponseEntity.status(403).body(new ExperienceResponse("Error saving data")));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> get(){
        return this.service.get().map(exp->ResponseEntity.ok().body(new ExperienceResponse(exp))).orElseGet(()->ResponseEntity.status(403).body(new ExperienceResponse("Error fetching data")));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Long idLong;
        try {
            idLong = Long.valueOf(id);
            this.service.delete(idLong);
            return ResponseEntity.ok().body(Map.of("messgae","Experience deleted"));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error","Experience not deleted"));

        }

    }
}
