package com.pharmactrl.controller;

import com.pharmactrl.security.JwtUtil;
import com.pharmactrl.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> authRequest) {
        String email = authRequest.get("email");
        String password = authRequest.get("password");
    
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    
        //  Passe l'objet userDetails ici
        String token = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
    
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("refreshToken", refreshToken);
        response.put("email", email);
        return response;
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String email = body.get("email");
        
        boolean isValid = jwtUtil.validateToken(token, email);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        return response;
    }
    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
    
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token expiré !");
        }
    
        String email = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email); //  obligatoire
    
        String newAccessToken = jwtUtil.generateAccessToken(userDetails); //  maintenant correct
    
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        return response;
    }
    
@GetMapping("/me")
public Map<String, Object> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
    // Extraire le token sans "Bearer "
    String token = authHeader.replace("Bearer ", "");
    
    // Extraire l'email (ou username)
    String email = jwtUtil.extractUsername(token);

    // Charger les infos de l'utilisateur
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    // Construire la réponse
    Map<String, Object> response = new HashMap<>();
    response.put("email", userDetails.getUsername());
    response.put("roles", userDetails.getAuthorities());

    return response;
}


}
