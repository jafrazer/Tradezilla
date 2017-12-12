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
			
			<jsp:include page="itemListView.jsp" />
		</div>
        
	</body>
</html>