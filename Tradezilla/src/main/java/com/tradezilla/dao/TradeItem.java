package com.tradezilla.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

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
		createTradeItem(username, itemName, itemDescription);
	}
	
	/**
	 * Method to insert a record into the trade request table
	 * 
	 * @param username
	 * @param itemName
	 * @param itemDescription
	 * 
	 * @return The created object, including an object id
	 */
	public TradeItemInfo createTradeItem(final String username, final String itemName, final String itemDescription) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "INSERT INTO trade_item (itemName, username, description) VALUES (?,?,?)";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, itemName);
				ps.setString(2, username);
				ps.setString(2, itemDescription);
			}
		};
		
		// TODO Ensure that there is no record with this name already created for this user
		
		jdbcTemplate.update(sql, pss);	
		
		return readByUsernameAndItemName(username, itemName);
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
		
		String sql = "INSERT INTO trade_item (itemName, username, description) VALUES (?,?,?)";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tradeItemInfo.getItemName());
				ps.setString(2, tradeItemInfo.getUsername());
				ps.setString(2, tradeItemInfo.getDescription());
			}
		};
		
		// TODO Ensure that there is no record with this name already created for this user
		
		jdbcTemplate.update(sql, pss);	
		
		return readByUsernameAndItemName(tradeItemInfo.getUsername(), tradeItemInfo.getItemName());
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

		if (1 == tradeItemList.size()) // This might not work if a user creates multiple listings with the exact same title... // TODO
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
}
