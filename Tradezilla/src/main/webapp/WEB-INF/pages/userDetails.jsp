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
		<jsp:include page="pageHeader.jsp" />
		
		<h3>User Details</h3>
		
		<div class="userDetails">
			<div class="userHeaderLeft">
				<table>
	                	<tr>
	                	    <td><b>Username:</b></td>
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
	
		<div class='tradeRequests'>
			<h3>My Trade Requests</h3>
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