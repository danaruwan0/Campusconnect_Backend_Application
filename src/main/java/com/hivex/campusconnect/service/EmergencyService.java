package com.hivex.campusconnect.service;

import com.hivex.campusconnect.dto.Emergency.EmergencyRequest;
import com.hivex.campusconnect.dto.Emergency.EmergencyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface EmergencyService {

//EmergencyResponse create(
//        Long userId,
//        EmergencyRequest request
//);

    EmergencyResponse create(

            Long userId,

            EmergencyRequest request,

            MultipartFile image,

            MultipartFile video

    );




    List<EmergencyResponse> getAll();

    EmergencyResponse get(Long id);

}