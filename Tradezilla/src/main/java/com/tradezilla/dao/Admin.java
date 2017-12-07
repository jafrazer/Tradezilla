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

public class Admin {

	/*
	 * This code is based on the code from the following sources
	 * 
	 *** http://www.mkyong.com/tutorials/spring-security-tutorials/
	 * 		(Mkyong, 2017)
	 * 
	 *** https://stackoverflow.com/questions/2989245/using-prepared-statements-with-jdbctemplate
	 * 		(stackoverflow, 2010)
	 * 
	 */
	@Autowired
	DataSource dataSource;
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

	/***************************************************************************************
	 * 
	 * This method returns a list of users whose accounts are currently disabled
	 * 
	 * @return - List of users awaiting account approval
	 * 
	 ***************************************************************************************/
	public ArrayList<UserAccountInfo> readCandidateUsers() {
	
		jdbcTemplate = new JdbcTemplate(dataSource);

		ArrayList<UserAccountInfo> userList = 
			jdbcTemplate.query("SELECT * FROM users WHERE enabled = ?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setBoolean(1, false);
					}
				},
				
				new ResultSetExtractor<ArrayList<UserAccountInfo>>() {
					public ArrayList<UserAccountInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

						ArrayList<UserAccountInfo> userList = new ArrayList<UserAccountInfo>();
						
						while (rs.next()) {
							UserAccountInfo userAccountInfo = new UserAccountInfo();
							userAccountInfo.setUsername(rs.getString("username"));
							userList.add(userAccountInfo);
						}
						return userList;
					}
				});

        return userList;

	}
	
	/***************************************************************************************
	 * Method to approve a user account
	 * 
	 * @param username - Username of the user to be approved
	 * @return - A string stating if the update was a "success" or not
	 * 
	 ***************************************************************************************/
	public String approveUser(final String username) {
		// Update the candidate record to approved
		jdbcTemplate.update("UPDATE users SET enabled = true WHERE username = ?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1, username);
					}
				});
		return "success";
	}

}
