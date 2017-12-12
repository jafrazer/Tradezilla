<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Trade Item Info</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>

	<body>
	<jsp:include page="pageHeader.jsp" />
	
	<div>
		<h3>${tradeItemInfo.itemName}</h3>
		<p>created by: ${tradeItemInfo.username}</p>
		<h3>Description:</h3>
		<p>${tradeItemInfo.description}</p>
	</div>
</body>
</html>