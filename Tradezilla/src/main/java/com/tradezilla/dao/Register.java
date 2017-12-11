/*
 * This code is based on the code from the following sources
 * 
 * - http://www.mkyong.com/tutorials/spring-security-tutorials/
 * 		(Mkyong, 2017)
 * 
 * - https://stackoverflow.com/questions/2989245/using-prepared-statements-with-jdbctemplate
 * 		(stackoverflow, 2010)
 * 
 * - With thanks to Carl Leslie for his help with getting the project environment running using his code.
 */

package com.tradezilla.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tradezilla.db.EncodePassword;
import com.tradezilla.model.UserAccountInfo;

public class Register extends DefaultDAO {

	/**
	 * Register the user by inserting their details into the database and creating a record in the user_role table
	 * The account is disabled until approved by an admin user.
	 * 
	 * @param userDetails User details for the new account
	 * 
	 * @return "Success" if no errors occur
	 */
	public String register(final UserAccountInfo userDetails) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		// Encrypt the password
//		EncryptPassword encryptPassword = new EncryptPassword();
		final String encryptedPassword = new EncodePassword().encryptPassword(userDetails.getPassword());	
		
		// Create the user in the user table
		String sqlCreateUser = "INSERT INTO users (username,password,enabled) VALUES (?,?,?)";
		PreparedStatementSetter pss_user = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userDetails.getUsername());
				ps.setString(2, encryptedPassword);
				ps.setBoolean(3, false);
			}
		};
		jdbcTemplate.update(sqlCreateUser, pss_user);

		// Insert the user role record for the new user
		String sqlInsertUserRole = "INSERT INTO user_roles (username, role) VALUES (?,?)";
		PreparedStatementSetter pss_userRole = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userDetails.getUsername());
				ps.setString(2, "ROLE_USER");
			}
		};
		jdbcTemplate.update(sqlInsertUserRole, pss_userRole);

		return "Success";
	}

	/**
	 * Check the user's chosen username is not taken
	 * 
	 * @param username
	 * @return Boolean indicating if the username is available
	 */
	public boolean checkForDuplicateUsername(final String username) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "SELECT * FROM users WHERE username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, username);
			}
		};

		ArrayList<UserAccountInfo> userList = jdbcTemplate.query(sql, pss, rseUserAccountInfoArrayList);

		if (userList.size() > 0) 
			return true;
		else
			return false;
	}
}
