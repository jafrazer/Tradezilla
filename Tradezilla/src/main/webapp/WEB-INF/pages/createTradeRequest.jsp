<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	
	<div>
		<h4><a href="userHome">My Details</a></h4>
	</div>
	<div>
		<form action="createTradeRequest" method="POST" id="CreateTradeRequestForm">
			<h3>Enter trade item name</h3><input type="text" name="itemName" id="itemName" />
			<h3>Enter trade item description</h3><input type="text" name="itemDescription" id="itemDescription" />
			<input type="submit" value="Create" />
			<input type="hidden" name="username" value="${username}" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
		<h3>${message}</h3>
	</div>
</body>
</html>