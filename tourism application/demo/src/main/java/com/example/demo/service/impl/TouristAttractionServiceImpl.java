package com.example.demo.service.impl;

import com.example.demo.model.DTO.TouristAttractionDTO;
import com.example.demo.model.TouristAttraction;
import com.example.demo.repository.TouristAttractionRepository;
import com.example.demo.service.TouristAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristAttractionServiceImpl implements TouristAttractionService {

    @Autowired
    TouristAttractionRepository attractionRepository;

    @Override
    public TouristAttraction create(TouristAttraction touristAttraction) {
        return attractionRepository.save(touristAttraction);
    }

    @Override
    public TouristAttraction saveAttraction(TouristAttractionDTO touristAttractionDTO) {

        TouristAttraction touristAttraction = new TouristAttraction();
        touristAttraction.setName(touristAttractionDTO.getName());
        touristAttraction.setLocation(touristAttractionDTO.getLocation());
        touristAttraction.setDescription(touristAttractionDTO.getDescription());
        touristAttraction.setLatitude(touristAttractionDTO.getLatitude());
        touristAttraction.setLongitude(touristAttractionDTO.getLongitude());
        touristAttraction.setImage(touristAttractionDTO.getImage());

        return attractionRepository.save(touristAttraction);
    }

    @Override
    public List<TouristAttraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    @Override
    public TouristAttraction getAttractionById(Integer idAttraction) {
        return attractionRepository.findById(idAttraction).get();
    }

    @Override
    public TouristAttraction getAttractionByNameLatitudeLongitude(String name, double lattitude, double longitude) {
        return attractionRepository.findByNameOrLatitudeAndLongitude(name, lattitude, longitude);
    }

    @Override
    public TouristAttraction update(TouristAttraction attraction) {
        return attractionRepository.save(attraction);
    }


    @Override
    public Boolean deleteAttraction(Integer idAttraction) {
        if (attractionRepository.existsById(idAttraction)) {
            attractionRepository.deleteById(idAttraction);
            return true;
        }
        return false;
    }

    @Override
    public Boolean existsByNameOrCoordinates(TouristAttractionDTO touristAttractionDTO) {
        TouristAttraction touristAttraction = attractionRepository.findByNameOrLatitudeAndLongitude(touristAttractionDTO.getName(), touristAttractionDTO.getLatitude(), touristAttractionDTO.getLongitude());
        return touristAttraction != null;
    }

    @Override
    public List<TouristAttraction> getAttractionsByLocation(String location) {
        return attractionRepository.findByLocation(location);
    }


}