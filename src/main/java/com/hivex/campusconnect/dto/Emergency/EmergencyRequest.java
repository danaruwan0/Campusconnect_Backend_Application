package com.hivex.campusconnect.dto.Emergency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyRequest {

    private String title;

    private String description;

    private Double latitude;

    private Double longitude;

    private String locationName;

}
