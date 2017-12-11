package com.tradezilla.db;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePassword {

	/**
	 * Encode the given password.
	 * SOURCE: http://www.programming-free.com/2015/09/spring-security-password-encryption.html
	 * 
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password) {
//		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
//		return encryptedPassword;
		return new BCryptPasswordEncoder().encode(password);
	}
}
