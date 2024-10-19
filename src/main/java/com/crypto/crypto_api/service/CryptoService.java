package com.crypto.crypto_api.service;

import static com.crypto.crypto_api.utils.Constants.VALID_ALGORITHMS;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.crypto.crypto_api.model.OutputKeysDTO;

@Service
public class CryptoService {

	private final PrivateKey privateKey;
	private final PublicKey publicKey;

	public CryptoService(PrivateKey privateKey, PublicKey publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public String hash(String input) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes());
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public String encrypt(String plainText) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encrypted = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encrypted);
	}

	public String decrypt(String encryptedText) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(decrypted);
	}

	public OutputKeysDTO generateRsaKeys(String algorithm) throws Exception {
		if (!VALID_ALGORITHMS.contains(algorithm)) {
			throw new IllegalArgumentException(
					"Invalid algorithm " + algorithm + ". Allowed algorithms: " + VALID_ALGORITHMS);
		}

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
		keyPairGen.initialize(2048);
		KeyPair pair = keyPairGen.generateKeyPair();
		String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
		String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

		return new OutputKeysDTO(publicKey, privateKey);
	}
}
