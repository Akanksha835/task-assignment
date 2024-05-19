package com.example.userapp.controller;

import com.example.userapp.model.User;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UpdateUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestParam UUID userId, @RequestBody User userData) {
        Optional<User> existingUserOpt = userService.getUserById(userId);
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User existingUser = existingUserOpt.get();

        if (userData.getFullName() != null && userData.getFullName().isEmpty()) {
            return ResponseEntity.badRequest().body("Full name must not be empty");
        }

        if (userData.getMobNum() != null && !userData.getMobNum().matches("^(\\+91|0)?[6-9]\\d{9}$")) {
            return ResponseEntity.badRequest().body("Invalid mobile number");
        }

        if (userData.getPanNum() != null && !userData.getPanNum().matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
            return ResponseEntity.badRequest().body("Invalid PAN number");
        }

        if (userData.getManagerId() != null && !userService.managerExists(userData.getManagerId())) {
            return ResponseEntity.badRequest().body("Manager ID is invalid");
        }

        if (userData.getFullName() != null) {
            existingUser.setFullName(userData.getFullName());
        }
        if (userData.getMobNum() != null) {
            existingUser.setMobNum(userData.getMobNum().replaceAll("^\\+91|^0", ""));
        }
        if (userData.getPanNum() != null) {
            existingUser.setPanNum(userData.getPanNum().toUpperCase());
        }
        if (userData.getManagerId() != null) {
            existingUser.setManagerId(userData.getManagerId());
        }

        User updatedUser = userService.updateUser(userId, existingUser);
        return ResponseEntity.ok("User updated successfully with ID: " + updatedUser.getUserId());
    }
}
