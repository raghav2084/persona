<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    	<!--for adding favicon -->
	<link rel="shortcut icon" type="image/png" href="resources/images/favicon.ico"/>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="resources/css/login.css">

	<title>Persona Login</title>
	<div class="jumbotron" style="background-color: #fff;"/>
	
				
</head>

<!--Begin Boday -->
<body>	
<div class="container">
<div class="wrapper">
	<form class="form-signin" id="loginFormID" method="post">
			<h2 class="form-signin-heading">Login</h2>
			<p class="text-danger">${loginError}</p>
			<div class="from-group">
			<input type="text" class="form-control" id="lg_usernameID" name="lg_username" placeholder="Username" required >
			</div>
			<div class="form-group">
			<input type="password" class="form-control" id="lg_passwordID" name="lg_password" placeholder="Password" required >
			</div>
			<div id="radiodiv">
				<label class="radio-inline" >
						<input type="radio" id="usertypeID" name="usertype" value="iamadmin" required> I am Admin
		      	</label>	
		      	<label class="radio-inline">
		      			<input type="radio"  name="usertype" value="iamemp"> I am Employee
		      	</label>		
      	 </div>
      </br>
      <button class="btn btn-lg btn-primary btn-block" id="LoginSubmit" type="button" data-loading-text="Logging in...">Login</button>
     
    </form>	
</div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/homePage.js" />" /></script>
</body>
</html>
