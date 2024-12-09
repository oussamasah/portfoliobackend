package dev.portfolio.oussama.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String skill;

    @Column(nullable = false)
    private String lang ;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean checked;
    @Column(nullable = false)
    private boolean multiword;


}
