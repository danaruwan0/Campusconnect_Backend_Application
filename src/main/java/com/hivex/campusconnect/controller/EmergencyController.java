


package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.Emergency.EmergencyRequest;
import com.hivex.campusconnect.dto.Emergency.EmergencyResponse;
import com.hivex.campusconnect.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmergencyController {

    private final EmergencyService emergencyService;

    /**
     * Create Emergency Alert
     */
    @PostMapping(
            value="/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<EmergencyResponse> createEmergency(

            @PathVariable Long userId,

            @RequestPart("data")
            EmergencyRequest request,

            @RequestPart(value="image",required=false)
            MultipartFile image,

            @RequestPart(value="video",required=false)
            MultipartFile video

    ){

        EmergencyResponse response =
                emergencyService.create(
                        userId,
                        request,
                        image,
                        video
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get All Emergency Alerts
     */
    @GetMapping
    public ResponseEntity<List<EmergencyResponse>> getAllEmergencies() {

        List<EmergencyResponse> responses =
                emergencyService.getAll();

        return ResponseEntity.ok(responses);
    }

    /**
     * Get Emergency By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmergencyResponse> getEmergency(
            @PathVariable Long id
    ) {

        EmergencyResponse response =
                emergencyService.get(id);

        return ResponseEntity.ok(response);
    }

}