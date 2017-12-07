package com.tradezilla.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

//	@RequestMapping(value = "/viewEvent", method = RequestMethod.POST)
//	public ModelAndView viewExistingEvent(@ModelAttribute("groupName") String groupName,
//			@ModelAttribute("username") String username,
//			@ModelAttribute("eventName") String eventName) {
//
//		ModelAndView modelAndView = new ModelAndView();
//		EventInfo eventInfo = new EventInfo();
//		modelAndView.setViewName("eventHome");
//
//		Event event = new Event();
//		eventInfo.setEventName(eventName);
//		eventInfo.setGroupName(groupName);
//		eventInfo = event.readEvent(eventInfo);
//
//		modelAndView.addObject("eventInfo", eventInfo);
//
//		return modelAndView;
//
//	}
}