package com.tradezilla.db;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword {

	// http://www.programming-free.com/2015/09/spring-security-password-encryption.html
	public String encryptPassword(String password) {
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		return encryptedPassword;
	}
	
//	public static void main(String args[]) {
//		System.out.println(encryptPassword("password"));
//	}
	
}
