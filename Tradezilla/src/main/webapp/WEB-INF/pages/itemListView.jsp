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
		<!-- itemListView -->
		<div id='itemListView'>
			<c:choose>
				<c:when test="${empty tradeItemList}">There are no trade requests listed.</c:when>
				<c:otherwise>
					<c:forEach items="${tradeItemList}" var="item">
						<form action="tradeItemInfo" method="POST" id="tradeItemInfoForm">
							<p>
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
		<!-- END Page Heading -->
	</body>
</html>