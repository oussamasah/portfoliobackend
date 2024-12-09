package dev.portfolio.oussama.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity()
@Data
@Table(name = "service_info")
public class ServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true,columnDefinition = "Text")
    private String description;


}