package com.crypto.crypto_api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.crypto_api.service.JwtService;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

	private final JwtService jwtService;

	public JwtController(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
    @PostMapping("/create-token")
    public ResponseEntity<String> createToken(@RequestBody Map<String, Object> requestBody) {
        long expirationTime = 3600000; // 1h
        
        String jwtToken = jwtService.createJwtToken(requestBody, expirationTime);

        return ResponseEntity.ok(jwtToken);
    }

}
