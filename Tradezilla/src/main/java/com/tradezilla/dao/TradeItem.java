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
	 * 
	 * @param username
	 * @param itemName
	 * @param itemDescription
	 * @return
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
		
		jdbcTemplate.update(sql, pss);	
		
		return readByUsernameAndItemName(username, itemName);
	}

	/**
	 * 
	 * @param tradeItemInfo
	 * @return
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
		
		jdbcTemplate.update(sql, pss);	
		
		return readByUsernameAndItemName(tradeItemInfo.getUsername(), tradeItemInfo.getItemName());
	}

	/**
	 * 
	 * @param username
	 * @param itemName
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
	 * 
	 * @param tradeItemInfo
	 * @return
	 */
	public TradeItemInfo readTradeItem(final TradeItemInfo tradeItemInfo) {
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE itemId = ? AND username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tradeItemInfo.getUsername());
			}
		};
		
		tradeItemList = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);

		if (1 == tradeItemList.size()) // there should only be one record here.
			return tradeItemList.get(0);
		else // something went wrong; too many records returned.
			return null;
	}

	/**
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
