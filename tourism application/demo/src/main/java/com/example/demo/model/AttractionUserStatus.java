package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AttractionUserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private TouristAttraction touristAttraction;

    private String email;
    private String attractionName;
    private int status;
    private String locationName;

    private double latitude; // Nou câmp
    private double longitude; // Nou câmp
}