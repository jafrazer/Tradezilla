<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Trade Request</title>
		
		<!-- Stylesheets -->
		<link rel="stylesheet" type="text/css" href="css/style.css">
		
		<!--[if lt IE 9]>
		  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>

	<body>
		<jsp:include page="pageHeader.jsp" />
			
		<h3>Create Trade Request</h3>
		<div>
			<form action="createTradeRequest" method="POST" id="CreateTradeRequestForm">
				<h3>Enter trade item name: </h3><input type="text" name="itemName" id="itemName" value="${tradeItemInfo.itemName}"/>
				<h3>Enter trade item description: </h3><input type="text" name="description" id="description" value="${tradeItemInfo.description}"/>
				<input type="submit" value="Create" />
				<input type="hidden" name="username" value="${tradeItemInfo.username}" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</body>
</html>