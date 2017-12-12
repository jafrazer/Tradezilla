<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- This code is based on the code from the following sources
	 
	  http://www.mkyong.com/tutorials/spring-security-tutorials/
	  		(Mkyong, 2017)
	  		
	  https://stackoverflow.com/questions/5935892/if-else-within-jsp-or-jstl
	  		(stackoverflow, 2011)
	  
	  www.youtube.com 
	  		(Shoaib Khan, 2012)
	 		(Brandon Jones, 2015)
	 		(IntoProgram, 2015)
	 		(Thamizh arasaN, 2014)
 -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Search Results</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body>
		<jsp:include page="pageHeader.jsp" />
		
		<div class='searchResults'>
			<h3>Search Results</h3>
			<c:choose>
				<c:when test="${empty tradeItemList}">There are no trade requests listed.</c:when>
				<c:otherwise>
					<c:forEach items="${tradeItemList}" var="item">
						<form action="tradeItemInfo" method="POST" id="tradeItemInfoForm">
							<p>
								<%-- <input type="hidden" name="tradeItemInfo" value="${item}" /> --%>
								<input type="hidden" name="id" value="${item.itemId}" />
								<input type="hidden" name="itemName" value="${item.itemName}" />
								<input type="hidden" name="username" value="${item.username}" />
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input class="tradeItemInfoButton" type="submit" value="View" />
								${item.itemName} - ${item.description}
							</p> 
						</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
        
	</body>
</html>