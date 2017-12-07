<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
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
		<jsp:include page="pageHeader.jsp" />
	
		<h3>Admin</h3>
	
		<div id='center'>
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<script>
				function formSubmit() {
					document.getElementById("logoutForm").submit();
				}
			</script>
	
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<p>Logged in as: ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">Not you? Logout.</a></p>
			</c:if>
		</div>
	
		<div class='usersToApprove'>
			<h3>Users pending approval</h3>
			<c:choose>
				<c:when test="${empty userList }">There are no users pending approval.</c:when>
				<c:otherwise>
					<c:forEach items="${userList}" var="user">
						<form action="confirmMember" method="POST" id="ConfirmMemberForm">${user.username} - 
							<input type="hidden" name="username" value="${user.username}" /> 
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input class="memDetsButton" type="submit" value="Approve" />
						</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>