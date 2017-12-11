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

	@RequestMapping(value = "/redirectUserDetails", method = RequestMethod.GET)
	public String redirectToUserDetails() {
		return "redirect:/userDetails/returnUserDetails";
	}	

	@RequestMapping(value = "/returnUserDetails", method = RequestMethod.GET)
	public ModelAndView returnToUserDetails() {

		// http://www.baeldung.com/get-user-in-spring-security
		String currentUserName ="";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			currentUserName = authentication.getName();
		}

		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		UserAccountInfo userAccountInfo = user.readUserAccountInfo(currentUserName);

		modelAndView.addObject("userAccountInfo", userAccountInfo);
		modelAndView.setViewName("userDetails");
		
		TradeItem tradeItem = new TradeItem();
		ArrayList<TradeItemInfo> tradeItemList = tradeItem.readTradeItemListForUser(currentUserName);
		modelAndView.addObject("tradeItemList", tradeItemList);

		return modelAndView;
	}
}