package com.crypto.crypto_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.crypto_api.model.OutputKeysDTO;
import com.crypto.crypto_api.service.CryptoService;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

	private final CryptoService cryptoService;

	public CryptoController(CryptoService cryptoService) {
		this.cryptoService = cryptoService;
	}

	@PostMapping("/hash/bcrypt")
	public ResponseEntity<String> hashPassword(@RequestBody String password) {
		String hashedPassword = cryptoService.hashPassword(password);
		return ResponseEntity.ok(hashedPassword);
	}

	@PostMapping("/hash/sha256")
	public ResponseEntity<String> hash(@RequestBody String input) throws Exception {
		String hashedValue = cryptoService.hash(input);
		return ResponseEntity.ok(hashedValue);
	}

	@GetMapping("/generate-keys")
	public ResponseEntity<OutputKeysDTO> generateKeys(@RequestParam String algorithm) throws Exception {
		OutputKeysDTO keys = cryptoService.generateRsaKeys(algorithm);
		return ResponseEntity.ok(keys);
	}

	@PostMapping("/encrypt")
	public ResponseEntity<String> encrypt(@RequestBody String plaintext) throws Exception {
		String encryptedValue = cryptoService.encrypt(plaintext);
		return ResponseEntity.ok(encryptedValue);
	}

	@PostMapping("/decrypt")
	public ResponseEntity<String> decrypt(@RequestBody String ciphertext) throws Exception {
		String decryptedValue = cryptoService.decrypt(ciphertext);
		return ResponseEntity.ok(decryptedValue);
	}

}
