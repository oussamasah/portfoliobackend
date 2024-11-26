package dev.portfolio.oussama.controller;

import dev.portfolio.oussama.dto.Auth.WelcomeResponse;
import dev.portfolio.oussama.service.ServiceService;
import dev.portfolio.oussama.service.WelcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceInfo;
    public ServiceController(ServiceService serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @GetMapping("/gettitle")
    public ResponseEntity<?> get() {
        return serviceInfo.getTitle()
                .map(title -> ResponseEntity.ok((title)))
                .orElseGet(() -> ResponseEntity.status(401).body(("Invalid credentials")));

    }
    @PostMapping("/savetitle")
    public ResponseEntity<?> save(String title) {
        return serviceInfo.saveTitle(title)
                .map(t-> ResponseEntity.ok((t)))
                .orElseGet(() -> ResponseEntity.status(401).body(("Invalid credentials")));

    }
}
