package com.ratemyclass.dto.auth;

public class AuthResponseDTO {
    
    private String token;
    private String tokenType = "Bearer";
    private UserResponseDTO user;
    private Long expiresIn; // in seconds
    
    // Constructors
    public AuthResponseDTO() {}
    
    public AuthResponseDTO(String token, UserResponseDTO user, Long expiresIn) {
        this.token = token;
        this.user = user;
        this.expiresIn = expiresIn;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    
    public UserResponseDTO getUser() { return user; }
    public void setUser(UserResponseDTO user) { this.user = user; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
}