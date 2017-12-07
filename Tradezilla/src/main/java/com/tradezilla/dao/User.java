/*
 * This code is based on the code from the following sources
 * 
 * - http://www.mkyong.com/tutorials/spring-security-tutorials/
 * 		(Mkyong, 2017)
 * - https://stackoverflow.com/questions/2989245/using-prepared-statements-with-jdbctemplate
 * 		(stackoverflow, 2010)
 * 
 */

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

import com.tradezilla.model.UserAccountInfo;

public class User {

	public User() {
		//  Empty constructor
	}
	
	@Autowired
	DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/***************************************************************************************
	 * Setter method for the DataSource
	 * 
	 * @param dataSource - Database connection bean
	 * 
	 ***************************************************************************************/
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 */
	private ResultSetExtractor<ArrayList<UserAccountInfo>> rseUserAccountInfoArrayList = new ResultSetExtractor<ArrayList<UserAccountInfo>>() {
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

	/***************************************************************************************
	 * This method returns the user details associated with the username
	 * 
	 * @param username - Username of the member whose details are requested
	 * @return - User details
	 * 
	 ***************************************************************************************/
	public UserAccountInfo readUserAccountInfo(final String username) {
	
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT username FROM users WHERE username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, username);
			}
		};

		ArrayList<UserAccountInfo> userList = jdbcTemplate.query(sql, pss, rseUserAccountInfoArrayList);
		
        return userList.get(0);
	}
}
