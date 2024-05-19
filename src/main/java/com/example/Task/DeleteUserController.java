package com.example.userapp.controller;

import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DeleteUserController {
    @Autowired
    private UserService userService;

    @PostMapping("/delete_user")
    public ResponseEntity<String> deleteUser(@RequestParam(required = false) UUID userId,
                                             @RequestParam(required = false) String mobNum) {
        if (userId != null) {
            Optional<User> userOpt = userService.getUserById(userId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            userService.deleteUser(userId);
        } else if (mobNum != null) {
            Optional<User> userOpt = userService.getUserByMobNum(mobNum);
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            userService.deleteUserByMobNum(mobNum);
        } else {
            return ResponseEntity.badRequest().body("Either userId or mobNum is required");
        }
        return ResponseEntity.ok("User deleted successfully");
    }
}
