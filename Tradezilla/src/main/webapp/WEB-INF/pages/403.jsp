<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>403 Error</title>
		
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
	
	<h1>HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
			<h2>
				Username : ${username} <br /> You do not have permission to access this page!
			</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>