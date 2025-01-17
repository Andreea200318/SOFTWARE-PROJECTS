package com.example.demo.service;

import com.example.demo.model.AttractionUserStatus;
import com.example.demo.model.DTO.AttractionUserStatusDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttractionUserStatusService {


    AttractionUserStatus addVisAttraction(AttractionUserStatusDTO attractionUserStatusDTO);

    //List<AttractionUserStatus> getAllUserVisitedAtr(String email);

    //AttractionUserStatus getAttractionUserStatusByEmailAndStatus(String email, int status);

    AttractionUserStatus getAttractionUserStatusByEmailAndAttraction(String email, String attractionName);
    void updateAttractionUserStatus(AttractionUserStatus attractionUserStatus);

    List<AttractionUserStatus> getAllUserVisitedAtrByLocation(String email, String locationName);

    List<AttractionUserStatus> getAllVisitedAttractions(String email);

    void deleteAttractionUserStatus(String email, String locationName, String attractionName);


}