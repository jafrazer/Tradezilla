/*
 * This code is based on the code from the following source
 * 
 * - http://www.mkyong.com/tutorials/spring-security-tutorials/
 * 		(Mkyong, 2017)
 * 
 * and was constructed using information from the following sources.
 * It was amended to include variable names relevant to this project.
 * 
 * - www.youtube.com 
 * 		(Shoaib Khan, 2012)
 *		(Brandon Jones, 2015)
 *		(IntoProgram, 2015)
 *		(Thamizh arasaN, 2014)
 *
 * - http://www.baeldung.com/get-user-in-spring-security
 * 		(Baeldung, 2017)
 * 
 */

package com.tradezilla.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tradezilla.dao.Admin;
import com.tradezilla.dao.Register;
import com.tradezilla.dao.TradeItem;
import com.tradezilla.dao.User;
import com.tradezilla.model.TradeItemInfo;
import com.tradezilla.model.UserAccountInfo;

@Controller
public class MainController {

	@Autowired
	User user;
	
	@Autowired
	Register register;
	
	@Autowired
	Admin admin;
	
	@Autowired
	TradeItem tradeItem;

	/***************************************************************************************
	 * 
	 * Method to return the main page
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = { "/", "/welcome**", "/home" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Spring Security Login Form - Database Authentication");
		modelAndView.addObject("message", "This is default page!");
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/***************************************************************************************
	 * 
	 * Method to link to the role protected admin page
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView modelAndView = new ModelAndView();
		ArrayList<UserAccountInfo> userList = admin.readCandidateUsers();
		
		// Include a list of all unapproved user accounts
		modelAndView.addObject("userList", userList);
		modelAndView.setViewName("admin");

		return modelAndView;

	}

	/***************************************************************************************
	 * Method to return the login page
	 * 
	 * @param error - indicates if an error has occurred during login
	 * @param logout - indicates successful logout
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 *
	 ***************************************************************************************/
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView modelAndView = new ModelAndView();
		if (error != null) {
			modelAndView.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			modelAndView.addObject("msg", "You've been logged out successfully.");
		}
		modelAndView.setViewName("login");

		return modelAndView;

	}
	
	/***************************************************************************************
	 * Method to return the user details on successful login
	 * 
	 * @param error - indicates if an error has occurred during login
	 * @param logout - indicates successful logout
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/userDetails", method = RequestMethod.GET)
	public ModelAndView loginSuccess(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		// Get the currently logged in user
		String currentUserName ="";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		ModelAndView modelAndView = new ModelAndView();

		// Read the logged in user's details
		UserAccountInfo userAccountInfo = user.readUserAccountInfo(currentUserName);
		modelAndView.addObject("userAccountInfo", userAccountInfo);
		modelAndView.setViewName("userDetails");
		
		// read the user's trade requests
		ArrayList<TradeItemInfo> tradeItemList = tradeItem.readTradeItemListForUser(currentUserName);
		modelAndView.addObject("tradeItemList", tradeItemList);

		return modelAndView;
	}

	/***************************************************************************************
	 * 
	 * Method to return the blank registration page
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegisterPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userDetails", new UserAccountInfo());
		modelAndView.setViewName("register");
		return modelAndView;
	}
	
	/***************************************************************************************
	 * Method to register the user with the details entered
	 * 
	 * @param userDetails - registration details entered by the user
	 * @param confirmPassword - password confirmation entered by the user
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerMember(@ModelAttribute("memberDetails") UserAccountInfo userDetails,
		@ModelAttribute("confirmPassword") String confirmPassword) {
		
		ModelAndView modelAndView = new ModelAndView();
		String message = "";
		String result = "";
		
		// Check if the user's chosen username is available
		boolean usernameExists = register.checkForDuplicateUsername(userDetails.getUsername());
		
		// If the username is not available, return the user a message asking them to select a different username
		if (usernameExists) {
			message = "This username is not available.";

			modelAndView.addObject("userDetails", userDetails);
			modelAndView.addObject("message", message);
			modelAndView.setViewName("register");
			return modelAndView;
			
		} else {
			
			// Ensure the password entered matches the confirmed password entered
			if (!(userDetails.getPassword().equals(confirmPassword))) {
				message = "Passwords do not match.  Please re-enter and confirm your password";

				modelAndView.addObject("userDetails", userDetails);
				modelAndView.addObject("message", message);
				modelAndView.setViewName("register");
				return modelAndView;

			} else {
				// Add the user's details to the database (account will be disabled until approved)
				result = register.register(userDetails);
			}
		}
		
		// Display a message telling the user that registration was successful and is now pending approval
		if (result.equals("Success")) {
			message = "You have successfully registered for an account.  You will be notified when your account has been approved for use.";
			
			modelAndView.addObject("msg", message);
			modelAndView.setViewName("blank");
		}
	
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/createTradeRequest", method = RequestMethod.POST)
	public ModelAndView createTradeRequest(
			@ModelAttribute("itemName") String itemName,
			@ModelAttribute("description") String description,
			@ModelAttribute("username") String username) {

		ModelAndView modelAndView = new ModelAndView();
		TradeItemInfo tradeItemInfo = new TradeItemInfo();
		modelAndView.setViewName("tradeItemInfo");

		tradeItemInfo.setItemName(itemName);
		tradeItemInfo.setDescription(description);
		tradeItemInfo.setUsername(username);
		tradeItemInfo = tradeItem.createTradeItem(tradeItemInfo);

		modelAndView.addObject("tradeItemInfo", tradeItemInfo);

		return modelAndView;

	}

//	@RequestMapping(value = "/viewTradeItem", method = RequestMethod.POST)
//	public ModelAndView viewTradeItem(
//			@ModelAttribute("itemName") String itemName,
//			@ModelAttribute("id") String itemId,
//			@ModelAttribute("username") String username) {
//
//		ModelAndView modelAndView = new ModelAndView();
//		TradeItemInfo tradeItemInfo = new TradeItemInfo();
//		modelAndView.setViewName("tradeItemHome");
//
//		TradeItem tradeItem = new TradeItem();
//		tradeItemInfo.setItemName(itemName);
//		tradeItemInfo.setUsername(username);
//		tradeItemInfo = tradeItem.readTradeItem(tradeItemInfo);
//
//		modelAndView.addObject("tradeItemInfo", tradeItemInfo);
//
//		return modelAndView;
//
//	}
	
	/***************************************************************************************
	 * Method to approve a user and activate their account
	 * 
	 * @param username - registered username of the user being approved
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/confirmMember", method = RequestMethod.POST)
	public ModelAndView approverMember(@ModelAttribute("username") String username) {

		ModelAndView modelAndView = new ModelAndView();

		// Enable the user's account
		String result = admin.approveUser(username);
		String msg = "";
		
		if (result.equals("Success")) {
			msg = "User approved and successfully registered.";
			
			modelAndView.addObject("msg", msg);
		}

		// Get the list of users awaiting approval
		ArrayList<UserAccountInfo> userList = admin.readCandidateUsers();
		
		modelAndView.addObject("userList", userList);
		modelAndView.setViewName("admin");

		return modelAndView;
	}
	

	/***************************************************************************************
	 * 
	 * Method to return the access denied page
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView modelAndView = new ModelAndView();
		
		// Get the currently logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
		
			modelAndView.addObject("username", userDetail.getUsername());
			
		}
		modelAndView.setViewName("403");
		
		return modelAndView;
	}
	
	/***************************************************************************************
	 * 
	 * Method to return the access denied page
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 * 
	 ***************************************************************************************/
	@RequestMapping(value = "/403", method = RequestMethod.POST)
	public ModelAndView accesssDeniedPost() {

		ModelAndView modelAndView = new ModelAndView();
		
		// Get the currently logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
		
			modelAndView.addObject("username", userDetail.getUsername());
			
		}
		modelAndView.setViewName("403");
		
		return modelAndView;
	}

}