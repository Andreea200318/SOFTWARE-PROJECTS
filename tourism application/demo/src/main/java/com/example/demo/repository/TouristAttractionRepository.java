// TouristAttractionRepository.java
package com.example.demo.repository;

import com.example.demo.model.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Integer> {
    List<TouristAttraction> findByLocation(String location);  // Căutare pe județ
    TouristAttraction findByNameOrLatitudeAndLongitude(String name, Double latitude, Double longitude);
}
