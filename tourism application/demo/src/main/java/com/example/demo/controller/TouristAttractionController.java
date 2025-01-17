package com.example.demo.controller;

import com.example.demo.model.DTO.TouristAttractionDTO;
import com.example.demo.model.TouristAttraction;
import com.example.demo.service.TouristAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractions")
public class TouristAttractionController {

    @Autowired
    TouristAttractionService touristAttractionService;

    @GetMapping("")
    public List<TouristAttraction> getAllAttractions() {
        return touristAttractionService.getAllAttractions();
    }

    @GetMapping("/{idAttraction}")
    public TouristAttraction getAttraction(@PathVariable Integer idAttraction) {
        return touristAttractionService.getAttractionById(idAttraction);
    }

    @GetMapping("/county/{locationName}")
    public ResponseEntity<List<TouristAttraction>> getAttractionsByCounty(@PathVariable String locationName) {
        List<TouristAttraction> attractions = touristAttractionService.getAttractionsByLocation(locationName);
        if (attractions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attractions);
    }

    @PostMapping("/store")
    public ResponseEntity<?> saveAttraction(@RequestBody TouristAttractionDTO touristAttractionDTO) {
        TouristAttraction existingTouristAttraction = touristAttractionService.getAttractionByNameLatitudeLongitude(
                touristAttractionDTO.getName(),
                touristAttractionDTO.getLatitude(),
                touristAttractionDTO.getLongitude()
        );
        if (existingTouristAttraction != null) {
            return ResponseEntity.status(409).body("Attraction already exists!");
        }

        TouristAttraction newTouristAttraction = touristAttractionService.saveAttraction(touristAttractionDTO);
        return ResponseEntity.status(201).body(newTouristAttraction);
    }

    @PutMapping("/update")
    public TouristAttraction updateAttraction(@RequestBody TouristAttraction attraction) {
        return touristAttractionService.update(attraction);
    }

    @DeleteMapping("/{idAttraction}")
    public ResponseEntity<String> deleteAttraction(@PathVariable Integer idAttraction) {
        boolean isDeleted = touristAttractionService.deleteAttraction(idAttraction);
        if (isDeleted) {
            return ResponseEntity.ok("Tourist Attraction deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Attraction not found.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TouristAttraction> updateAttraction(@PathVariable Integer id, @RequestBody TouristAttraction attraction) {
        // Verificăm dacă atracția există
        TouristAttraction existingAttraction = touristAttractionService.getAttractionById(id);
        if (existingAttraction == null) {
            return ResponseEntity.notFound().build();  // Dacă atracția nu există, returnăm 404
        }

        // Actualizăm atracția
        attraction.setIdAttraction(id);
        TouristAttraction updatedAttraction = touristAttractionService.update(attraction);
        return ResponseEntity.ok(updatedAttraction);  // Returnăm atracția actualizată
    }

}
