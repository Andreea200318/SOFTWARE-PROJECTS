// TouristAttractionService.java
package com.example.demo.service;

import com.example.demo.model.DTO.TouristAttractionDTO;
import com.example.demo.model.TouristAttraction;

import java.util.List;

public interface TouristAttractionService {
    public TouristAttraction create(TouristAttraction touristAttraction);
    public List<TouristAttraction> getAllAttractions();
    public TouristAttraction getAttractionById(Integer idAttraction);
    public TouristAttraction getAttractionByNameLatitudeLongitude(String name, double latitude, double longitude);
    public TouristAttraction update(TouristAttraction attraction);
    public Boolean deleteAttraction(Integer idAttraction);
    public Boolean existsByNameOrCoordinates(TouristAttractionDTO touristAttractionDTO);

    // Metodă pentru a obține atracțiile turistice pe județ

    TouristAttraction saveAttraction(TouristAttractionDTO touristAttractionDTO);

    List<TouristAttraction> getAttractionsByLocation(String locationName);


}
