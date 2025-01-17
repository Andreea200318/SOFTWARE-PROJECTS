package com.example.demo.service.impl;

import com.example.demo.model.AttractionUserStatus;
import com.example.demo.model.DTO.AttractionUserStatusDTO;
import com.example.demo.repository.AttractionUserStatusRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.AttractionUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionUserStatusServiceImpl implements AttractionUserStatusService {

    @Autowired
    private AttractionUserStatusRepository attractionUserStatusRepository;

    @Override
    public AttractionUserStatus addVisAttraction(AttractionUserStatusDTO attractionUserStatusDTO) {
        AttractionUserStatus attractionUserStatus = new AttractionUserStatus();
        attractionUserStatus.setEmail(attractionUserStatusDTO.getEmail());
        attractionUserStatus.setAttractionName(attractionUserStatusDTO.getAttractionName());
        attractionUserStatus.setStatus(attractionUserStatusDTO.getStatus());
        attractionUserStatus.setLocationName(attractionUserStatusDTO.getLocationName());
        attractionUserStatus.setLatitude(attractionUserStatusDTO.getLatitude()); // Salvăm latitudinea
        attractionUserStatus.setLongitude(attractionUserStatusDTO.getLongitude()); // Salvăm longitudinea

        return attractionUserStatusRepository.save(attractionUserStatus);
    }

    @Override
    public List<AttractionUserStatus> getAllUserVisitedAtrByLocation(String email, String locationName) {
        return attractionUserStatusRepository.findByEmailAndLocationNameIgnoreCase(email, locationName);
    }

    //@Override
    //public AttractionUserStatus getAttractionUserStatusByEmailAndStatus(String email, int status) {
    //    return attractionUserStatusRepository.findByEmailAndStatus(email, status);
    //}

    @Override
    public AttractionUserStatus getAttractionUserStatusByEmailAndAttraction(String email, String attractionName) {
        return attractionUserStatusRepository.findByEmailAndAttractionName(email, attractionName);
    }

    @Override
    public void updateAttractionUserStatus(AttractionUserStatus attractionUserStatus) {
        attractionUserStatusRepository.save(attractionUserStatus);
    }

    @Override
    public List<AttractionUserStatus> getAllVisitedAttractions(String email) {
        return attractionUserStatusRepository.findByEmailAndStatus(email, 2); // Status 2 = vizitat
    }

    @Override
    public void deleteAttractionUserStatus(String email, String locationName, String attractionName) {
        AttractionUserStatus attractionUserStatus = attractionUserStatusRepository
                .findByEmailAndAttractionName(email, attractionName);

        if (attractionUserStatus != null && attractionUserStatus.getLocationName().equals(locationName)) {
            attractionUserStatusRepository.delete(attractionUserStatus);
        }
    }

}

