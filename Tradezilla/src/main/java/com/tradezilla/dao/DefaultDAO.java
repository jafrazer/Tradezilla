package com.tradezilla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.tradezilla.model.TradeItemInfo;
import com.tradezilla.model.UserAccountInfo;

public class DefaultDAO {

	@Autowired
	DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 */
	protected ResultSetExtractor<ArrayList<UserAccountInfo>> rseUserAccountInfoArrayList = new ResultSetExtractor<ArrayList<UserAccountInfo>>() {
		public ArrayList<UserAccountInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
			return readUserAccountInfoArrayList(rs);
		}
	};
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<UserAccountInfo> readUserAccountInfoArrayList(ResultSet rs) throws SQLException {
		ArrayList<UserAccountInfo> userList = new ArrayList<UserAccountInfo>();

		while (rs.next()) {
			UserAccountInfo userAccountInfo = new UserAccountInfo();
			userAccountInfo.setUsername(rs.getString("username"));
			userList.add(userAccountInfo);
		}

		return userList;
	}
	
	
	
	

	public ResultSetExtractor<ArrayList<TradeItemInfo>> rseTradeItemInfoArrayList = new ResultSetExtractor<ArrayList<TradeItemInfo>>() {
		public ArrayList<TradeItemInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
			return readTradeItemInfoArrayList(rs);
		}
	};
	
	private ArrayList<TradeItemInfo> readTradeItemInfoArrayList(ResultSet rs) throws SQLException {
		ArrayList<TradeItemInfo> tradeItemList = new ArrayList<TradeItemInfo>();

		while (rs.next()) {
			TradeItemInfo tradeItemInfo = new TradeItemInfo();
			tradeItemInfo.setItemId(rs.getString("id"));
			tradeItemInfo.setItemName(rs.getString("itemName"));
			tradeItemInfo.setUsername(rs.getString("username"));
			tradeItemInfo.setDescription(rs.getString("description"));

			tradeItemList.add(tradeItemInfo);
		}

		return tradeItemList;
	}
}
