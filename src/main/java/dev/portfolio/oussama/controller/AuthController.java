package dev.portfolio.oussama.controller;
import dev.portfolio.oussama.dto.Auth.AuthRequest;
import dev.portfolio.oussama.dto.Auth.AuthResponse;
import dev.portfolio.oussama.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return authService.authenticateUser(request.getUsername(), request.getPassword())
                .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                .orElseGet(() -> ResponseEntity.status(401).body(new AuthResponse("Invalid credentials")));
    }


}
