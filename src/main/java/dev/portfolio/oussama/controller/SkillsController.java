package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.service.SkillsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/skills")
public class SkillsController {
    private final SkillsService service;
    public SkillsController(SkillsService service){
        this.service= service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam("id") Long id,@RequestParam("skill") String skill , @RequestParam("active") boolean active, @RequestParam("multiword") boolean multiword,
    @RequestParam("checked") boolean checked,@RequestParam("lang") String lang
    ){
        if(id == null){
            System.out.println("++++"+id);
            id=0L;
        }
       return this.service.save(id,skill,active,checked,multiword,lang).map(t->ResponseEntity.ok().body(Map.of("message","Skills saved"))).orElseGet(()->ResponseEntity.status(403).body(Map.of("error","Slill not saved")));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        Optional<List> list = service.get();
        return ResponseEntity.ok().body(Map.of("data",list));
    }
}
