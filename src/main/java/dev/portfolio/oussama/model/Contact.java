package dev.portfolio.oussama.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String message;

    // Add createdDate with default value (current timestamp)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();  // default to current time

    // Add seen as a boolean field with a default value of false
    @Column(nullable = false)
    private boolean seen = false;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
        if (!seen) {
            seen = false;
        }
    }
}
