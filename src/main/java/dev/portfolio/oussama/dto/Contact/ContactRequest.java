package dev.portfolio.oussama.dto.Contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.portfolio.oussama.model.Contact;
import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String email;
    private String number;
    private String message;
    private String response_error;
    @JsonCreator
    public ContactRequest(
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("number") String number,
            @JsonProperty("message") String message
    ) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.message = message;
    }

    public ContactRequest(String c){
        this.response_error=c;
    }

}
