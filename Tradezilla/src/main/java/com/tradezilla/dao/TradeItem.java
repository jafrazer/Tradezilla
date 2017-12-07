package com.tradezilla.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tradezilla.model.TradeItemInfo;

public class TradeItem extends DefaultDAO {


	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private ResultSetExtractor<ArrayList<TradeItemInfo>> rseTradeItemInfoArrayList = new ResultSetExtractor<ArrayList<TradeItemInfo>>() {
		public ArrayList<TradeItemInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
			return readTradeItemInfoArrayList(rs);
		}
	};
	
	public TradeItem() {
		// Empty Constructor
	}
	
	public TradeItem(String username, String itemName, String itemDescription) {
		createTradeItem(username, itemName, itemDescription);
	}
	
	public TradeItemInfo createTradeItem(final String username, final String itemName, final String itemDescription) {
		
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

	public TradeItemInfo readByUsernameAndItemName(final String username, final String itemName) {
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

	public TradeItemInfo readTradeItem(final TradeItemInfo tradeItemInfo) {
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();
		
		String sql = "SELECT * FROM trade_items WHERE itemId = ? AND username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tradeItemInfo.getItemId());
				ps.setString(2, tradeItemInfo.getUsername());
			}
		};
		
		tradeItemList = jdbcTemplate.query(sql, pss, rseTradeItemInfoArrayList);

		if (1 == tradeItemList.size()) // there should only be one record here.
			return tradeItemList.get(0);
		else // something went wrong; too many records returned.
			return null;
	}

	public ArrayList<TradeItemInfo> readTradeItemListForUser(final String currentUserName) {
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
	
	private ArrayList<TradeItemInfo> readTradeItemInfoArrayList(ResultSet rs) throws SQLException {
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();

		while (rs.next()) {
			TradeItemInfo tradeItemInfo = new TradeItemInfo();
			tradeItemInfo.setItemId(rs.getString("itemId"));
			tradeItemInfo.setItemName(rs.getString("itemName"));
			tradeItemInfo.setUsername(rs.getString("username"));
			tradeItemInfo.setItemDescription(rs.getString("itemDescription"));

			tradeItemList.add(tradeItemInfo);
		}

		return tradeItemList;
	}
}
