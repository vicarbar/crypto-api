package com.crypto.crypto_api.service;

import java.security.PrivateKey;
import java.sql.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private final PrivateKey privateKey;

	public JwtService(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public String createJwtToken(Map<String, Object> claims, long expirationTime) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(privateKey, SignatureAlgorithm.RS256).compact();
	}

}
