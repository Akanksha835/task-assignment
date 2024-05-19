package com.example.userapp.service;

import com.example.userapp.model.Manager;
import com.example.userapp.model.User;
import com.example.userapp.repository.ManagerRepository;
import com.example.userapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByMobNum(String mobNum) {
        return userRepository.findByMobNum(mobNum);
    }

    public List<User> getUsersByManagerId(UUID managerId) {
        return userRepository.findByManagerId(managerId);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void deleteUserByMobNum(String mobNum) {
        Optional<User> user = userRepository.findByMobNum(mobNum);
        user.ifPresent(userRepository::delete);
    }

    public User updateUser(UUID userId, User userData) {
        return userRepository.save(userData);
    }

    public boolean managerExists(UUID managerId) {
        return managerRepository.existsById(managerId);
    }
}
