package dev.portfolio.oussama.service;


import dev.portfolio.oussama.model.Contact;
import dev.portfolio.oussama.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepo;

    public ContactService(ContactRepository repo){
        this.contactRepo = repo;
    }

    public Optional<Contact> saveContact(String name, String email , String number, String message){
        Contact c = new Contact();
        c.setName(name);
        c.setEmail(email);
        c.setNumber(number);
        c.setMessage(message);
        this.contactRepo.save(c);
        return Optional.of(c);

    }
    public Optional<List> getAll(){

        List<Contact> contacts = this.contactRepo.findAllByOrderByIdAsc();
        return Optional.of(contacts);

    }
    public Optional<Contact> seen(Long id){

        Optional<Contact> contact = this.contactRepo.findById(id);
        Contact entity;
        if(contact.isPresent()){
            entity = contact.get();
            entity.setSeen(true);
            this.contactRepo.save(entity);

        }
        return contact;

    }
}
