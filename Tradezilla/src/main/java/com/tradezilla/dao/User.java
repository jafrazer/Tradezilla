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
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tradezilla.model.UserAccountInfo;

public class User extends DefaultDAO {

	public User() {
		//  Empty constructor
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
