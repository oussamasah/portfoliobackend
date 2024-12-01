package dev.portfolio.oussama.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String daterange;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;
}
