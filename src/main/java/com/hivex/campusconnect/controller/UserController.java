package com.hivex.campusconnect.controller;

import com.hivex.campusconnect.dto.search_user.UserSearchResponse;
import com.hivex.campusconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;


//    http://localhost:8081/api/users/search?keyword=03250
    @GetMapping("/search")
    public List<UserSearchResponse> searchUsers(
            @RequestParam String keyword) {

        return userService.searchUsers(keyword);
    }
}