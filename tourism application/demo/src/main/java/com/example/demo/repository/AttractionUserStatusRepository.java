package com.example.demo.repository;

import com.example.demo.model.AttractionUserStatus;
import com.example.demo.model.Client;
import com.example.demo.model.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionUserStatusRepository extends JpaRepository<AttractionUserStatus, Integer> {
    AttractionUserStatus findByEmailAndAttractionName(String email, String attractionName);

    List<AttractionUserStatus> findByEmailAndStatus(String email, int status);
    List<AttractionUserStatus> findByEmailAndLocationNameIgnoreCase(String email, String locationName);

}