package com.hcc.controllers;

import com.hcc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private JWTUtils jwtUtils;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JWTUtils.generateToken(userDetails, jwtSecret, jwtExpirationMs);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("{\"error\": \"Invalid username or password\"}");
        }
    }
    // Validate token endpoint
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Get token from header
            try {
                if (jwtUtils.validateToken(token, null)) { // You can replace 'null' with actual UserDetails if needed
                    return ResponseEntity.ok("Token is valid");
                } else {
                    return ResponseEntity.status(401).body("{\"error\": \"Invalid token\"}");
                }
            } catch (Exception e) {
                return ResponseEntity.status(401).body("{\"error\": \"Invalid token\"}");
            }
        } else {
            return ResponseEntity.status(400).body("{\"error\": \"Authorization header missing or malformed\"}");
        }
    }
}
