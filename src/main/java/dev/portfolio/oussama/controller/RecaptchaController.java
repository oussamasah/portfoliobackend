package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.Recaptcha.Recaptcha;
import dev.portfolio.oussama.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recaptcha")
public class RecaptchaController {

    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        boolean isVerified = recaptchaService.verifyRecaptcha(token);
        System.out.println("-------------------------"+String.valueOf(isVerified));
        if (isVerified) {
            return ResponseEntity.ok(new Recaptcha("Captcha verified successfully"));
        } else {
            return ResponseEntity.status(403).body(new Recaptcha("Captcha verification failed X)"));
        }
    }
}
