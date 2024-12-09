package dev.portfolio.oussama.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Serv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,columnDefinition = "Text")
    private String description;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String icon;


}
