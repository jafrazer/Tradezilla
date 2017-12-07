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

import com.tradezilla.db.EncryptPassword;
import com.tradezilla.model.UserAccountInfo;

public class Register extends DefaultDAO {

	/***************************************************************************************
	 * Register the user by inserting their details and creating a role for them.  The account 
	 * is initially disabled and must be approved and enabled by an admin user.
	 * 
	 * @param userDetails - User details for the new account to be set up
	 * @return - String indicating if the action was a "Success"
	 * 
	 ***************************************************************************************/
	public String register(final UserAccountInfo userDetails) {

		jdbcTemplate = new JdbcTemplate(dataSource);

		// Encrypt the password
		EncryptPassword encryptPassword = new EncryptPassword();
		final String encryptedPassword = encryptPassword.encryptPassword(userDetails.getPassword());	
		
		// Create the user in the user table
		String sqlCreateUser = "INSERT INTO users (username,password,enabled) VALUES (?,?,?)";
		PreparedStatementSetter pss_user = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, userDetails.getUsername());
				preparedStatement.setString(2, encryptedPassword);
				preparedStatement.setBoolean(3, false);
			}
		};
		jdbcTemplate.update(sqlCreateUser, pss_user);

		// Insert the user role record for the new user
		String sqlInsertUserRole = "INSERT INTO user_roles (username, role) VALUES (?,?)";
		PreparedStatementSetter pss_userRole = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, userDetails.getUsername());
				preparedStatement.setString(2, "ROLE_USER");
			}
		};
		jdbcTemplate.update(sqlInsertUserRole, pss_userRole);

		return "Success";
	}

	/***************************************************************************************
	 * Check that the user's chosen username is not already in use
	 * 
	 * @param username - Username selected by the user requesting an account
	 * @return - Boolean indicating if the username is available
	 * 
	 ***************************************************************************************/
	public boolean checkForDuplicateUsername(final String username) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sql = "SELECT * FROM users WHERE username = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, username);
			}
		};

		ArrayList<UserAccountInfo> userList = jdbcTemplate.query(sql, pss, rseUserAccountInfoArrayList);

		if (userList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
