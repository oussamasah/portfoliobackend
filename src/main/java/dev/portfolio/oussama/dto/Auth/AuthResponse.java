package dev.portfolio.oussama.dto.Auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    // Constructor with String argument
    public AuthResponse(String token) {
        this.token = token;
    }

}
