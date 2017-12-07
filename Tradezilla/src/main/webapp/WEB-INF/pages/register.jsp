<%@taglib uri="http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Register</title>
		
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
 			<form action="register" method="POST" id="RegisterForm">	
 				<h3>Registration Details</h3>	
 				<p>Enter your preferred username and create a password.</p>
				<table>
                    <tr>
                        <td>Username: </td>
                        <td><input type="text" name="username" id="username" required value="${userDetails.username}"/><br/></td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td><input type="password" required name="password" id="password"/><br/></td>
                    </tr>
                    <tr>
                        <td>Confirm password: </td>
                        <td><input type="password"  name="confirmPassword" id="confirmPassword" required/><br/></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Submit"/>
                            <input type="reset" value="Clear"/>
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                
			</form>
			<h3>${message}</h3>
		</div>
	</body>
</html>