package dev.portfolio.oussama.dto.Contact;

import dev.portfolio.oussama.model.Contact;
import lombok.Data;

import java.util.List;

@Data
public class ContactResponse {
    private String name;
    private String email;
    private String number;
    private String message;
    private List<Contact> data;
    private String response_error;

    public ContactResponse(Contact c){
        this.name= c.getName();
        this.email= c.getEmail();
        this.number= c.getNumber();
        this.message= c.getMessage();

    }
    public ContactResponse(String c){
        this.response_error=c;
    }

    public ContactResponse(List c){
        this.data = c;
    }

}
