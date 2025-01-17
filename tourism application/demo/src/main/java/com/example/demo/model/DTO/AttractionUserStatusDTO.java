package com.example.demo.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttractionUserStatusDTO {
    private String email;
    private String attractionName;
    private int status;
    private String locationName;
    private double latitude; // Nou câmp
    private double longitude; // Nou câmp
}