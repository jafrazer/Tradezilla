<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<div>
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
		
		<h3>Trade Item:</h3>
		${tradeItemInfo.itemName}&nbsp;&nbsp;&nbsp;&nbsp;(${tradeItemInfo.username)
		<br />
		<h3>Description:</h3>
		${tradeItemInfo.itemDescription} <br />
	</div>
</body>
</html>