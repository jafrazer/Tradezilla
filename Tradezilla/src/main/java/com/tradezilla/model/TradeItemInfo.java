package com.tradezilla.model;

import org.springframework.stereotype.Component;

@Component
public class TradeItemInfo {

	private String itemId;
	private String itemName;
	private String username;
	private String description;

	public TradeItemInfo() {
		super();
	}

	public TradeItemInfo(String itemId, String itemName, String username, String itemDescription) {
		super();
		this.setItemId(itemId);
		this.setItemName(itemName);
		this.setUsername(username);
		this.setDescription(itemDescription);
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String itemDescription) {
		this.description = itemDescription;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
