package dev.portfolio.oussama.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String project;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;


    @Column(nullable = true)
    private String image;


}
