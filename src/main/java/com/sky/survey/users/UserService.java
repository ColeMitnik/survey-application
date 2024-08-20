package com.sky.survey.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(User.UserRole.RESPONDENT);
        user.setDateCreated(LocalDateTime.now());
        user.setDateModified(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUserRole(userDetails.getUserRole());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setGender(userDetails.getGender());
        user.setDateModified(LocalDateTime.now());
        // Don't update password here, create a separate method for password changes
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + userName));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User registerUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(User.UserRole.RESPONDENT);
        user.setDateCreated(LocalDateTime.now());
        user.setDateModified(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User authenticateUser(String userName, String password) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setDateModified(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}