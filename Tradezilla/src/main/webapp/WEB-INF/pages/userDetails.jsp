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
		<title>User Home</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body>
		<div class="userDetails">
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
			
			<h3>User Home</h3>
			<div class="userHeaderLeft">
				<table>
	                	<tr>
	                	    <td><b>Username:</b> </td>
	                    	<td>${userAccountInfo.username}</td>
		            </tr>
	    	        </table>
            </div>
            
            <div class="userHeaderRight">
				<sec:authorize access="hasRole('ROLE_USER')">
					<!-- For login user -->
					<c:url value="/j_spring_security_logout" var="logoutUrl" />
					<form action="${logoutUrl}" method="post" id="logoutForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<script>
						function formSubmit() {
							document.getElementById("logoutForm").submit();
						}
					</script>

					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<a href="javascript:formSubmit()"> Logout</a>
					</c:if>
				</sec:authorize>
			</div>
        </div>
        
	</body>
</html>