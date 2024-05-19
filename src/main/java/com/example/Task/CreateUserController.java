package com.example.userapp.controller;

import com.example.userapp.model.User;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CreateUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Validate and process the input data
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            return ResponseEntity.badRequest().body("Full name must not be empty");
        }

        if (!user.getMobNum().matches("^(\\+91|0)?[6-9]\\d{9}$")) {
            return ResponseEntity.badRequest().body("Invalid mobile number");
        }

        user.setMobNum(user.getMobNum().replaceAll("^\\+91|^0", ""));

        if (!user.getPanNum().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
            return ResponseEntity.badRequest().body("Invalid PAN number");
        }

        user.setPanNum(user.getPanNum().toUpperCase());

        if (user.getManagerId() != null && !userService.managerExists(user.getManagerId())) {
            return ResponseEntity.badRequest().body("Manager ID is invalid");
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok("User created successfully with ID: " + createdUser.getUserId());
    }
}
