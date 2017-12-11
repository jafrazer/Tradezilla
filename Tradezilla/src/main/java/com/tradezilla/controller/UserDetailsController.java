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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tradezilla.dao.TradeItem;
import com.tradezilla.dao.User;
import com.tradezilla.model.TradeItemInfo;
import com.tradezilla.model.UserAccountInfo;

@Controller
@RequestMapping(value = "/userDetails")
public class UserDetailsController {

	@Autowired
	TradeItemInfo tradeItemInfo;

	/**
	 * This method redirects to the user details page because there is an extra userDatials appearing in the URL
	 * 
	 * @return Redirect string
	 */
	@RequestMapping(value = "/redirectUserDetails", method = RequestMethod.GET)
	public String redirectToUserDetails() {
		return "redirect:/userDetails/returnUserDetails";
	}	

	/**
	 * This method passes along the user details and the tradeItem list for the current user
	 * 
	 * @return
	 */
	@RequestMapping(value = "/returnUserDetails", method = RequestMethod.GET)
	public ModelAndView returnToUserDetails() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("userDetails");

		// http://www.baeldung.com/get-user-in-spring-security
		String currentUserName ="";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken))
			currentUserName = authentication.getName();

//		User user = new User();
		UserAccountInfo userAccountInfo = new User().readUserAccountInfo(currentUserName);
		mav.addObject("userAccountInfo", userAccountInfo);
		
//		TradeItem tradeItem = new TradeItem();
		ArrayList<TradeItemInfo> tradeItemList = new TradeItem().readTradeItemListForUser(currentUserName);
		mav.addObject("tradeItemList", tradeItemList);

		return mav;
	}
}