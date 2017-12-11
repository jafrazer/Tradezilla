<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Code References:
	 http://www.mkyong.com/tutorials/spring-security-tutorials/
	  		(Mkyong, 2017)
	  
	 https://www.tutorialspoint.com/jsp/jstl_core_url_tag.htm
	 		(tutorialspoint, 2017)
	  
	 https://stackoverflow.com/questions/4286466/use-a-normal-link-to-submit-a-form
	 		(stackoverflow)
	  
	 www.youtube.com 
	  		(Shoaib Khan, 2012)
	 		(Brandon Jones, 2015)
	 		(IntoProgram, 2015)
	 		(Thamizh arasaN, 2014)
	 		(Codebun, 2017)
	 		(ArtiVisi Intermedia, 2013)
 --> 
 
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