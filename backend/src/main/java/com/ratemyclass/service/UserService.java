package com.ratemyclass.service;

import com.ratemyclass.dto.auth.RegisterRequestDTO;
import com.ratemyclass.dto.auth.UserResponseDTO;
import com.ratemyclass.entity.User;
import com.ratemyclass.exception.UserAlreadyExistsException;
import com.ratemyclass.exception.UserNotFoundException;
import com.ratemyclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Creates a new user based on registration data
     */
    public UserResponseDTO createUser(RegisterRequestDTO registerRequest) {
        // Validate if user already exists
        validateUserDoesNotExist(registerRequest.getEmail(), registerRequest.getStudentId());
        
        // Create new user entity
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setStudentId(registerRequest.getStudentId());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setMajor(registerRequest.getMajor());
        user.setGraduationYear(registerRequest.getGraduationYear());
        user.setRole(User.Role.STUDENT); // Default role
        user.setEnabled(true);
        user.setEmailVerified(false); // Will be verified later via email
        
        // Save user
        User savedUser = userRepository.save(user);
        
        // Return DTO
        return new UserResponseDTO(savedUser);
    }
    
    /**
     * Finds user by email
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }
    
    /**
     * Finds user by student ID
     */
    @Transactional(readOnly = true)
    public Optional<User> findByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }
    
    /**
     * Gets user by ID and converts to DTO
     */
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDTO::new);
    }
    
    /**
     * Updates user's email verification status
     */
    public void verifyEmail(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        
        user.setEmailVerified(true);
        userRepository.save(user);
    }
    
    /**
     * Updates user password
     */
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Validates that user doesn't already exist by email or student ID
     */
    private void validateUserDoesNotExist(String email, String studentId) {
        if (userRepository.existsByEmail(email.toLowerCase())) {
            throw new UserAlreadyExistsException("User already exists with email: " + email);
        }
        
        if (userRepository.existsByStudentId(studentId)) {
            throw new UserAlreadyExistsException("User already exists with student ID: " + studentId);
        }
    }
}