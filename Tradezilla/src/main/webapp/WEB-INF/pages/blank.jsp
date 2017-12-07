<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!-- This code is based on the code from the following sources
	 
	 http://www.mkyong.com/tutorials/spring-security-tutorials/
	  		(Mkyong, 2017)
	  
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
		
		<div class="msg">${msg}</div>
	</body>
</html>