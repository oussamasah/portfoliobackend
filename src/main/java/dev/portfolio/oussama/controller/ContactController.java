package dev.portfolio.oussama.controller;


import dev.portfolio.oussama.dto.Contact.ContactRequest;
import dev.portfolio.oussama.dto.Contact.ContactResponse;
import dev.portfolio.oussama.model.Contact;
import dev.portfolio.oussama.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactService service;
    public ContactController(ContactService service){
        this.service=service;
    }

    @PostMapping("/save")
    private ResponseEntity<?> save(@RequestBody ContactRequest c){
        System.out.println("+++++++++++++++"+c.getName().isEmpty());
        System.out.println("+++++++++++++++"+c.getName().isBlank());
        System.out.println("+++++++++++++++"+ c.getName() == null);
        System.out.println("+++++++++++++++"+c.getName().matches("^[a-zA-Z]+([ '-][a-zA-Z]+)*$"));
        if(c.getName().isEmpty() || c.getName().isBlank() || c.getName() == null || !c.getName().matches("^[a-zA-Z]+([ '-][a-zA-Z]+)*$")){
            return ResponseEntity.status(403).body(new ContactResponse("Name is not valid"));
        }
        if(c.getEmail().isEmpty() || c.getEmail().isBlank() || c.getEmail() == null || !c.getEmail().matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
            return ResponseEntity.status(403).body(new ContactResponse("Email is not valid"));
        }
        if(c.getNumber().isEmpty() || c.getNumber().isBlank() || c.getNumber() == null || !c.getNumber().matches("^\\+?[1-9]\\d{9,14}$")){
            return ResponseEntity.status(403).body(new ContactResponse("Whatsapp number is not valid"));
        }
        if(c.getMessage().isEmpty() || c.getMessage().isBlank() || c.getMessage() == null || c.getMessage().length() < 30 ){
            return ResponseEntity.status(403).body(new ContactResponse("Whatsapp number is not valid"));
        }

        return this.service.saveContact( c.getName(),c.getEmail(),c.getNumber(), c.getMessage()).map(x->ResponseEntity.ok().body(new ContactResponse(x)))
                .orElseGet(()->ResponseEntity.status(403).body(new ContactResponse("Forbidden")));
    }

    @GetMapping("/getcontact")
    public ResponseEntity<?> getcontact(){
       return this.service.getAll().map(c ->ResponseEntity.ok().body(new ContactResponse(c)))
                .orElseGet(()->ResponseEntity.status(403).body(new ContactResponse("Forbidden")));

    }
    @PutMapping("/seen/{id}")
    public ResponseEntity<?> seenContact(@PathVariable Long id){

        if(id==0 || id == null){
            return ResponseEntity.status(403).body(new ContactResponse("Invalid contact"));
        }




        return this.service.seen(id).map(c ->ResponseEntity.ok().body(new ContactResponse(c)))
                .orElseGet(()->ResponseEntity.status(403).body(new ContactResponse("Forbidden")));

    }
}
