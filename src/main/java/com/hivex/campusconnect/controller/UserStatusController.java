package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.service.OnlineUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserStatusController {

    private final OnlineUsers onlineUsers;

    @GetMapping("/{userId}")
    public boolean isOnline(
            @PathVariable Long userId) {

        return onlineUsers.isOnline(
                userId);
    }

    @GetMapping("/online-users")
    public Set<Long> getOnlineUsers() {

        return onlineUsers.getAllOnlineUsers();
    }
}