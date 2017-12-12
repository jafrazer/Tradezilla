package com.tradezilla.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tradezilla.db.DataSanitization;
import com.tradezilla.model.TradeItemInfo;

public class TradeItem extends DefaultDAO {
	
	public TradeItem() {
		// Empty Constructor
	}
	
	/**
	 * Overloaded constructor which will create a trade request based on the information provided.
	 * 
	 * @param username
	 * @param itemName
	 * @param itemDescription
	 */
	public TradeItem(String username, String itemName, String itemDescription) {
		this.createTradeItem(username, itemName, itemDescription);
	}
	
	/**
	 * Method to insert a record into the trade request table
	 * 
	 * @param username
	 * @param itemName
	 * @param description
	 * 
	 * @return The created object, including an object id
	 */
	public TradeItemInfo createTradeItem(final String username, final String itemName, final String description) {
		return this.createTradeItem(new TradeItemInfo(username, itemName, description));
	}

	/**
	 * Method to insert a record into the trade request table
	 * 
	 * @param tradeItemInfo
	 * 
	 * @return The created object, including an object id
	 */
	public TradeItemInfo createTradeItem(final TradeItemInfo tradeItemInfo) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "INSERT INTO trade_items (itemName, username, description) VALUES (?,?,?)";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tradeItemInfo.getItemName());
				ps.setString(2, tradeItemInfo.getUsername());
				ps.setString(3, tradeItemInfo.getDescription());
			}
		};
		
		validateTradeRequest(tradeItemInfo);
		
		jdbcTemplate.update(sql, pss);	
		
		return readByUsernameAndItemName(tradeItemInfo.getUsername(), tradeItemInfo.getItemName());
	}

	/**
	 * Read all trade requests with the searchString in the name.
	 * 
	 * @param searchString
	 * @return
	 */
	public ArrayList<TradeItemInfo> searchForTradeRequest(final String searchString) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		ArrayList<TradeItemInfo> searchResults = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE itemName CONTAINS ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, searchString);
			}
		};
		
		searchResults = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);
		
		return searchResults;
	}

	/**
	 * Read a trade request by the username and item name
	 * 
	 * @param username
	 * @param itemName
	 * 
	 * @return
	 */
	public TradeItemInfo readByUsernameAndItemName(final String username, final String itemName) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE username = ? AND itemName = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
				ps.setString(2, itemName);
			}
		};
		
		tradeItemList = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);

		if (1 == tradeItemList.size())
			return tradeItemList.get(0);
		else
			return null;
	}

	/**
	 * Read the trade request by the id
	 * 
	 * @param tradeItemInfo
	 * @return
	 */
	public TradeItemInfo readTradeItemById(final TradeItemInfo tradeItemInfo) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE itemId = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tradeItemInfo.getItemId());;
			}
		};
		
		tradeItemList = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);

		if (1 == tradeItemList.size()) // there should only be one record here.
			return tradeItemList.get(0);
		else // something went wrong; too many records returned.
			return null;
	}

	/**
	 * Read all trade requests posted by this user.
	 * 
	 * @param currentUserName
	 * @return
	 */
	public ArrayList<TradeItemInfo> readTradeItemListForUser(final String currentUserName) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		ArrayList<TradeItemInfo> usersTradeItems = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, currentUserName);
			}
		};
		
		usersTradeItems = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);
		
		return usersTradeItems;
	}
	
	public void validateTradeRequest(TradeItemInfo tradeItemInfo) {
		
		// TODO Write the validation code
		
		// TODO Ensure that there is no record with this name already created for this user
		
		// Ensure that the entered data is sanitized to remove any potentially executable code
		tradeItemInfo.setItemName(new DataSanitization().sanitizeString(tradeItemInfo.getItemName()));
		tradeItemInfo.setDescription(new DataSanitization().sanitizeString(tradeItemInfo.getDescription()));
		tradeItemInfo.setUsername(new DataSanitization().sanitizeString(tradeItemInfo.getUsername()));
	}
}
