<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Admin</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>

<body>
	<!-- Page Heading -->
	<div class='pageHeader'>
		<h1>Tradezilla</h1>
		<!-- Main Menu -->
		<div class="mainMenu">
			<h4><a href="home">Home</a></h4>
		</div>
		<!-- END Main Menu -->
	</div>
	<!-- END Page Heading -->

	<!-- Links to the register and login pages -->
	<div>
		<a href="register">Create a new account</a> | <a href="login">Already a member? Login here.</a>
	</div>
	
	<!-- Blurb -->
	<div>
		<h3>Create a post for a trade target.</h3>
		<h3>Receive trade offers.</h3>
		<h3>Connect with the trader to get your item.</h3>
	</div>
	
	<!-- Search bar -->
	<div>
		<h3>Search for a trade item here</h3>
		<form name='searchForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
			<h3>Search:</h3>
			<input type="text" name="seachTerm" />
			<input type="submit" value="doSearch" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>