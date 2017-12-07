/*
 * This code is based on the code from the following sources
 * 
 * - http://www.mkyong.com/tutorials/spring-security-tutorials/
 * 		(Mkyong, 2017)
 * 
 * - https://stackoverflow.com/questions/2989245/using-prepared-statements-with-jdbctemplate
 * 		(stackoverflow, 2010)
 * 
 */

package com.tradezilla.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tradezilla.model.UserAccountInfo;

public class Admin extends DefaultDAO {

	/**
	 * 
	 * @return
	 */
	public ArrayList<UserAccountInfo> readCandidateUsers() {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "SELECT * FROM users WHERE enabled = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setBoolean(1, false);
			}
		};

		return jdbcTemplate.query(sql, pss, rseUserAccountInfoArrayList);
	}

	/**
	 * 
	 */
	public void listUsersForApproval() {

	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public String approveUser(final String username) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "UPDATE users SET enabled = true WHERE username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, username);
			}
		};
		
		jdbcTemplate.update(sql, pss);
		return "success";
	}

}
