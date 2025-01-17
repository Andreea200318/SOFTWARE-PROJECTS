package com.example.demo.model.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristAttractionDTO {
    private String name;
    private String location;
    private String description;
    private Double latitude; // Adăugăm latitudine
    private Double longitude; // Adăugăm longitudine
    private String image;
}