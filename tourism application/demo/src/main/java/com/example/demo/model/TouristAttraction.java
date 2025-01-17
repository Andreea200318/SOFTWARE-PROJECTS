// TouristAttraction.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TouristAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAttraction;

    private String name;
    private String location;  // Optional, poate fi folosit pentru a adăuga locații suplimentare

    @Column(length = 1000)
    private String description;

    private Double latitude;
    private Double longitude;

    private String image;

}
