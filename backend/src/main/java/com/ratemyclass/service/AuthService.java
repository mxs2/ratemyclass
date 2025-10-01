package com.ratemyclass.service;

import com.ratemyclass.dto.auth.AuthResponseDTO;
import com.ratemyclass.dto.auth.LoginRequestDTO;
import com.ratemyclass.dto.auth.RegisterRequestDTO;
import com.ratemyclass.dto.auth.UserResponseDTO;
import com.ratemyclass.entity.User;
import com.ratemyclass.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    /**
     * Registers a new user
     */
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        // Create user
        UserResponseDTO userResponse = userService.createUser(registerRequest);
        
        // Find the created user entity for JWT generation
        User user = userService.findByEmail(registerRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Failed to retrieve created user"));
        
        // Generate JWT token
        String token = jwtService.generateToken(user);
        
        // Return authentication response
        return new AuthResponseDTO(token, userResponse, jwtService.getExpirationTime());
    }
    
    /**
     * Authenticates user and returns JWT token
     */
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            // Get authenticated user
            User user = (User) authentication.getPrincipal();
            
            // Generate JWT token
            String token = jwtService.generateToken(user);
            
            // Create user response DTO
            UserResponseDTO userResponse = new UserResponseDTO(user);
            
            // Return authentication response
            return new AuthResponseDTO(token, userResponse, jwtService.getExpirationTime());
            
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
    
    /**
     * Gets current user information
     */
    public UserResponseDTO getCurrentUser(String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        return new UserResponseDTO(user);
    }
}