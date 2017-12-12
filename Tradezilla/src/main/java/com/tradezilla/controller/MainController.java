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
 * - With thanks to Carl Leslie for his help with getting the project environment running using his code.
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

	/**
	 * Method to direct to the main page
	 * 
	 * @return A ModelAndView object containing the view name and all details to be displayed
	 */
	@RequestMapping(value = { "/", "/welcome**", "/home" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		mav.addObject("title", "Title for the home page. This is not used in the live application.");
		mav.addObject("message", "Message for the home page. This is not used in the live application.");
		
		return mav;
	}

	/**
	 * This method directs to the admin page, which is accessible only to users with the role of admin.
	 * 
	 * @return A ModelAndView containing the view name and a list of all user accounts pending approval.
	 */
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin");
				
		// Display a list of user accounts pending approval
		mav.addObject("userList", admin.listUsersForApproval());

		return mav;
	}

	/**
	 * This method directs a user to the login page, and optionally displays a message based on whether the user has just logged out, or if there was an error with the user credentials.
	 * 
	 * @param error indicates if an error has occurred during login
	 * @param logout indicates successful logout
	 * 
	 * @return A ModelAndView containing the view name and the return message.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		
		if (error != null)
			mav.addObject("error", "Invalid username and password combination!");

		if (logout != null) 
			mav.addObject("msg", "You've been logged out successfully.");
		
		return mav;

	}
	
	/**
	 * Returns user's details after successful login attempt
	 * 
	 * @param error indicates if an error has occurred during login
	 * @param logout indicates successful logout
	 * 
	 * @return - Returns a ModelAndView containing the view name and all details to be displayed
	 */
	@RequestMapping(value = "/userDetails", method = RequestMethod.GET)
	public ModelAndView loginSuccess(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		String currentUserName = getCurrentUser();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userDetails");

		// Read the details for the current user
		UserAccountInfo userAccountInfo = user.readUserAccountInfo(currentUserName);
		mav.addObject("userAccountInfo", userAccountInfo);
		
		// read the user's trade requests
		ArrayList<TradeItemInfo> tradeItemList = tradeItem.readTradeItemListForUser(currentUserName);
		mav.addObject("tradeItemList", tradeItemList);

		return mav;
	}

	private String getCurrentUser() {
		// Get the currently logged in user
		String currentUserName ="";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		    currentUserName = auth.getName();
		}
		return currentUserName;
	}

	/**
	 * This method returns the blank registration page
	 * 
	 * @return A ModelAndView containing the view name and the user details
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegisterPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("register");
		
		mav.addObject("userDetails", new UserAccountInfo());
		
		return mav;
	}
	
	/**
	 * Method to register the user with the details entered
	 * 
	 * @param userDetails Registration details entered by the user
	 * @param confirmPassword Password confirmation entered by the user
	 * 
	 * @return A ModelAndView containing the view name, a result, and a message
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerMember(
			@ModelAttribute("memberDetails") UserAccountInfo userDetails,
			@ModelAttribute("confirmPassword") String confirmPassword) {
		
		ModelAndView modelAndView = new ModelAndView();
		String result = "";
		
		// Check if the entered username is available
		if (register.checkForDuplicateUsername(userDetails.getUsername())) {
			// If the username is not available, return an info message
			modelAndView.setViewName("register");
			
			modelAndView.addObject("userDetails", userDetails);
			modelAndView.addObject("message", "This username is not available.");
			
			return modelAndView;
			
		} else {
			// Check that the passwords match
			if (!(userDetails.getPassword().equals(confirmPassword))) {
				modelAndView.setViewName("register");

				modelAndView.addObject("userDetails", userDetails);
				modelAndView.addObject("message", "Passwords do not match.  Please re-enter and confirm your password");
				
				return modelAndView;

			} else {
				// Insert the user details in the database.
				// This creates an account that needs to be approved by an admin.
				result = register.register(userDetails);
			}
		}
		
		// Display a message telling the user that registration was successful and is now pending approval
		if (result.equals("Success")) {
			modelAndView.setViewName("blank");
			modelAndView.addObject("msg", "Account created. Admin approval pending.");
		}
	
		return modelAndView;
	}
	
	/**
	 * This method creates a record in the item table and returns the item that was created.
	 * 
	 * @param itemName
	 * @param description
	 * @param username
	 * 
	 * @return A ModelAndView containing the view name and the tradeItemInfo
	 */
	@RequestMapping(value = "/createTradeRequest", method = RequestMethod.POST)
	public ModelAndView createTradeRequest(
			@ModelAttribute("itemName") String itemName,
			@ModelAttribute("description") String description,
			@ModelAttribute("username") String username) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeItemInfo");
		
		TradeItemInfo tradeItemInfo = new TradeItemInfo();
		tradeItemInfo.setItemName(itemName);
		tradeItemInfo.setDescription(description);
		tradeItemInfo.setUsername(this.getCurrentUser());

		// Check that the entered data is valid
		tradeItem.validateTradeRequest(tradeItemInfo);
		
		// Create the trade request in the database
		tradeItemInfo = tradeItem.createTradeItem(tradeItemInfo);
		
		// TODO Add some error handling here for if there is an issue with the database insert

		mav.addObject("tradeItemInfo", tradeItemInfo);

		return mav;
	}
	
	/**
	 * This method creates a record in the item table and returns the item that was created.
	 * 
	 * @param itemName
	 * @param description
	 * @param username
	 * 
	 * @return A ModelAndView containing the view name and the tradeItemInfo
	 */
	@RequestMapping(value = "/createTradeRequest", method = RequestMethod.GET)
	public ModelAndView createTradeRequest() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("createTradeRequest");

		return mav;
	}
	
	/**
	 * This method searches for a the given search string in the trade item names.
	 * 
	 * @param searchString
	 * 
	 * @return A ModelAndView containing the view name and the tradeItemInfo
	 */
	@RequestMapping(value = "/searchTradeRequests", method = RequestMethod.POST)
	public ModelAndView searchTradeRequests(
			@ModelAttribute("searchString") String searchString) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("searchResults");

		ArrayList<TradeItemInfo> tradeItemList = new TradeItem().searchForTradeRequest(searchString);
		mav.addObject("tradeItemList", tradeItemList);

		return mav;
	}

	/**
	 * 
	 * @param itemName
	 * @param itemId
	 * @param username
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tradeItemInfo", method = RequestMethod.GET)
	public ModelAndView viewTradeItem() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("tradeItemInfo");

		return mav;
	}

	/**
	 * 
	 * @param itemName
	 * @param itemId
	 * @param username
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tradeItemInfo", method = RequestMethod.POST)
	public ModelAndView viewTradeItem(
			@ModelAttribute("itemName") String itemName,
			@ModelAttribute("itemId") String itemId,
			@ModelAttribute("username") String username) {

		ModelAndView mav = new ModelAndView();
		TradeItemInfo tradeItemInfo = new TradeItemInfo();
		mav.setViewName("tradeItemInfo");

		TradeItem tradeItem = new TradeItem();
		tradeItemInfo.setItemId(itemId);
		tradeItemInfo.setItemName(itemName);
		tradeItemInfo.setUsername(this.getCurrentUser());
		
//		tradeItemInfo = tradeItem.readTradeItemById(tradeItemInfo);
		tradeItemInfo = tradeItem.readByUsernameAndItemName(this.getCurrentUser(), itemName);

		mav.addObject("tradeItemInfo", tradeItemInfo);

		return mav;

	}
	
	/**
	 * This method approves and activates a user's account
	 * 
	 * @param username - registered username of the user being approved
	 * @return A ModelAndView for the admin page
	 */
	@RequestMapping(value = "/confirmMember", method = RequestMethod.POST)
	public ModelAndView approverMember(
			@ModelAttribute("username") String username) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin");

		// Enable the user's account		
		if (admin.approveUser(username).equals("Success")) 
			mav.addObject("msg", "User approved and activated.");

		// Get the updated list of users awaiting approval		
		mav.addObject("userList", admin.listUsersForApproval());

		return mav;
	}
	

	/**
	 * Method for an access denied page
	 * 
	 * @return A ModelAndView for the 403 page
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("403");
		
		// Get the currently logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("username", userDetail.getUsername());
		}
		
		return mav;
	}
	
	/**
	 * Method for an access denied page
	 * 
	 * @return A ModelAndView for the 403 page
	 */
	@RequestMapping(value = "/403", method = RequestMethod.POST)
	public ModelAndView accesssDeniedPost() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("403");
		
		// Get the currently logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			mav.addObject("username", userDetail.getUsername());
		}
		
		return mav;
	}
	
}