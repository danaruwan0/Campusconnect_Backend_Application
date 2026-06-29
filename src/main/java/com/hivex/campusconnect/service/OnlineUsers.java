package com.hivex.campusconnect.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUsers {

    private final Set<Long> onlineUsers =
            ConcurrentHashMap.newKeySet();

    public void userOnline(Long userId) {
        onlineUsers.add(userId);
    }

    public void userOffline(Long userId) {
        onlineUsers.remove(userId);
    }

    public boolean isOnline(Long userId) {
        return onlineUsers.contains(userId);
    }

    public Set<Long> getAllOnlineUsers() {
        return onlineUsers;
    }
}