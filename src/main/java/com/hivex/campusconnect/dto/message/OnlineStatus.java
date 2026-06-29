package com.hivex.campusconnect.dto.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineStatus {

    private Long userId;

    private boolean online;
}