<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body>
		<!-- Page Heading -->
		<div id='pageHeader'>
			<h1 id='center'>Tradezilla</h1>
			<!-- Main Menu -->
			<div class="mainMenu" id='center'>
				<h3> | 
					<a href="home">Home</a> | 
					<a href="userDetails">User Details</a> | 
					<a href="createTradeRequest">Create Trade Request</a> | 
					<a href="login">Login</a> | 
					<a href="register">Register</a> | 
				</h3>
			</div>
			<!-- END Main Menu -->
			<%-- <c:if test="${pageContext.request.userPrincipal.name != null}">
				<div class="userDetails" id='center'>
					<p><b>Username:</b> ${userAccountInfo.username}</p>
				</div>
			</c:if> --%>
		</div>
		<!-- END Page Heading -->
	</body>
</html>