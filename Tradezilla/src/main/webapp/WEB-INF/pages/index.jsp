<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Tradezilla</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>

	<body>
		<jsp:include page="pageHeader.jsp" />
	
		<!-- Links to the register and login pages -->
		<div id='center'>
			<a href="register">Create a new account</a> | <a href="login">Already a member? Login here.</a>
		</div>
		
		<!-- Blurb -->
		<div id='center'>
			<h3>Post a trade request; Receive trade offers; Connect with a trade partner.</h3>
		</div>
		
		<!-- Search bar -->
		<div  id='center'>
			<form name='searchForm' action="searchResults" method='POST'>
				<h3>Search for your trade target here</h3>
				<input type="text" name="searchString" />
				<input type="submit" value="Search" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</body>
</html>