package dev.portfolio.oussama.dto.Recaptcha;

import lombok.Data;

@Data
public class Recaptcha {
    private String message;
    public  Recaptcha(String msg){
        this.message = msg;
    }
}
