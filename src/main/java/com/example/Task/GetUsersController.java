package com.example.userapp.controller;

import com.example.userapp.model.User;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GetUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/get_users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) UUID userId,
                                               @RequestParam(required = false) String mobNum,
                                               @RequestParam(required = false) UUID managerId) {
        List<User> users;
        if (userId != null) {
            users = userService.getUserById(userId).map(List::of).orElse(List.of());
        } else if (mobNum != null) {
            users = userService.getUserByMobNum(mobNum).map(List::of).orElse(List.of());
        } else if (managerId != null) {
            users = userService.getUsersByManagerId(managerId);
        } else {
            users = userService.getUsers();
        }
        return ResponseEntity.ok(users);
    }
}
