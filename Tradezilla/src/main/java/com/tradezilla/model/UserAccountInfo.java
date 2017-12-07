package com.tradezilla.model;

import org.springframework.stereotype.Component;

@Component
public class UserAccountInfo {
	
	private String username;
	private String password;
	private Boolean enabled;

	public UserAccountInfo() {
		super();
	}

	public UserAccountInfo(String username, String password, Boolean enabled) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.setEnabled(enabled);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
