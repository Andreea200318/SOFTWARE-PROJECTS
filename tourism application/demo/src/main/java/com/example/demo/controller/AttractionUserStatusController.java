package com.example.demo.controller;

import com.example.demo.model.AttractionUserStatus;
import com.example.demo.model.DTO.AttractionUserStatusDTO;
import com.example.demo.service.AttractionUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
@RequestMapping("/attraction-user-status")
@CrossOrigin(origins = "http://localhost:3000")
public class AttractionUserStatusController {

    @Autowired
    AttractionUserStatusService attractionUserStatusService;

    @PostMapping("/add-visited")
    ResponseEntity<?> addVisitedAttractions(@RequestBody List<AttractionUserStatusDTO> attractionUserStatusDTOs) {
        try {
            for (AttractionUserStatusDTO dto : attractionUserStatusDTOs) {
                AttractionUserStatus existingStatus = attractionUserStatusService.getAttractionUserStatusByEmailAndAttraction(
                        dto.getEmail(), dto.getAttractionName()
                );

                if (existingStatus != null) {
                    existingStatus.setStatus(dto.getStatus());
                    existingStatus.setLocationName(dto.getLocationName());
                    existingStatus.setLatitude(dto.getLatitude()); // Actualizăm latitudinea
                    existingStatus.setLongitude(dto.getLongitude()); // Actualizăm longitudinea
                    attractionUserStatusService.updateAttractionUserStatus(existingStatus);
                } else {
                    attractionUserStatusService.addVisAttraction(dto);
                }
            }
            return ResponseEntity.status(201).body("Selecțiile au fost salvate cu succes!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la salvarea selecțiilor.");
        }
    }

    @GetMapping("/get-all-user-visited-attractions/{email}/{locationName}")
    public ResponseEntity<?> getAllUserAttractionsByCounty(
            @PathVariable String email,
            @PathVariable String locationName) {
        try {
            // Obține toate atracțiile pentru email și locație specificată
            List<AttractionUserStatus> attractions =
                    attractionUserStatusService.getAllUserVisitedAtrByLocation(email, locationName);

            Map<String, List<String>> groupedAttractions = new HashMap<>();
            groupedAttractions.put("visited", new ArrayList<>());
            groupedAttractions.put("toVisit", new ArrayList<>());
            groupedAttractions.put("notInterested", new ArrayList<>());

            // Procesează doar atracțiile care se potrivesc cu locația
            for (AttractionUserStatus attraction : attractions) {
                switch (attraction.getStatus()) {
                    case 2:
                        groupedAttractions.get("visited").add(attraction.getAttractionName());
                        break;
                    case 1:
                        groupedAttractions.get("toVisit").add(attraction.getAttractionName());
                        break;
                    case 3:
                        groupedAttractions.get("notInterested").add(attraction.getAttractionName());
                        break;
                }
            }

            return ResponseEntity.ok(groupedAttractions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la încărcarea atracțiilor.");
        }
    }

    /*
    @PostMapping("/get-attraction-status")
    ResponseEntity<?> getAttractionUserStatus(@RequestBody AttractionUserStatusDTO attractionUserStatusDTO) {
        AttractionUserStatus attractionUserStatus = attractionUserStatusService.getAttractionUserStatusByEmailAndStatus(
                attractionUserStatusDTO.getEmail(),
                attractionUserStatusDTO.getStatus()
        );

        if (attractionUserStatus == null) {
            return ResponseEntity.status(404).body("No such attraction user status found!");
        }

        return ResponseEntity.ok(attractionUserStatus);
    }

     */

    @GetMapping("/get-all-user-visited-locations/{email}")
    public ResponseEntity<?> getAllUserVisitedLocations(@PathVariable String email) {
        try {
            // Obține toate atracțiile vizitate pentru utilizator
            List<AttractionUserStatus> visitedAttractions =
                    attractionUserStatusService.getAllVisitedAttractions(email);

            // Creează o listă de obiecte cu coordonate și numele atracției
            List<Map<String, Object>> locations = new ArrayList<>();
            for (AttractionUserStatus attraction : visitedAttractions) {
                Map<String, Object> locationData = new HashMap<>();
                locationData.put("name", attraction.getAttractionName());
                locationData.put("latitude", attraction.getLatitude());
                locationData.put("longitude", attraction.getLongitude());
                locations.add(locationData);
            }

            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la încărcarea locațiilor vizitate.");
        }
    }


    @DeleteMapping("/delete/{email}/{locationName}/{attractionName}")
    public ResponseEntity<?> deleteAttraction(
            @PathVariable String email,
            @PathVariable String locationName,
            @PathVariable String attractionName) {
        try {
            attractionUserStatusService.deleteAttractionUserStatus(email, locationName, attractionName);
            return ResponseEntity.status(200).body("Atracția a fost ștearsă cu succes.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Eroare la ștergerea atracției.");
        }
    }


}
