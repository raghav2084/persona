<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
</head>
<body>
	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand">Hello ${loggedInUser.userName}!</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="home">Home</a></li>
					<!-- <li><a href="#">Payments</a></li> -->
					<!-- <li><a href="#">Contact</a></li> -->
					<!--  <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li role="separator" class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li> -->
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<!-- <li class="active"><a href="./">Default <span class="sr-only">(current)</span></a></li>
              <li><a href="../navbar-static-top/">Static top</a></li> -->
              
              <li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Employees <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<!--<li role="separator" class="divider"></li>-->
							<li class="dropdown-header"><h5 class="text-danger">Reports</h5></li>
							<c:forEach var="EmpNames" items="${AllEmpNames}" varStatus="i">
									<li><a href="empreport?num=${EmpNames.empPhoneNum}">${EmpNames.empName}</li>
								</c:forEach>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header"><h5 class="text-danger">Employees</h5></li>
							<li><a href="manageemp">Manage Employees</a></li>
						</ul></li>
              
              
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Payments <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<!--<li role="separator" class="divider"></li>-->
								<li class="dropdown-header"><h5 class="text-danger">Payments</h5></li>
								<li><a href="allpayments">All Payments</a></li>
								<!--<li role="separator" class="divider"></li>
								 <li class="dropdown-header"><h5 class="text-danger">Contact
									Us</h5></li>
								<li><a href="contact">Contact</a></li>
								-->
							</ul>
						</li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">Settings <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<!--<li role="separator" class="divider"></li>-->
							<li class="dropdown-header"><h5 class="text-danger">Admin</h5></li>
							<li><a href="manageadmins">Manage Admins</a></li>
						</ul></li>
					<!-- <li><a href="contact">Contact</a></li> -->
				
					
					<li><a href="#" data-toggle="modal" data-target="#myModal">Logout</a></li>

					<!-- Modal -->
					<div id="myModal" class="modal fade" role="dialog">
						<div class="modal-dialog modal-sm">

							<!-- Modal content-->
							<div class="modal-content">
								<div class="modal-header">
									<!-- 									<button type="button" class="close" data-dismiss="modal">&times;</button> -->
									<h4 class="modal-title">Logout?</h4>
								</div>
								<div class="modal-body">
									<p>Are you sure you want logout?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger btn-block"
										data-dismiss="modal">No</button>
									<a href="logout" class="btn btn-primary btn-block">Yes</a>
								</div>
							</div>

						</div>
					</div>


				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid --> </nav>

		<!-- Main component for a primary marketing message or call to action -->
<!-- 		<div class="jumbotron" id="myJumbotron">
			<button type="button" id="myButton" data-dismiss="alert"
				class="close" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<h2>Persona Unisex Salon Offers & Savings</h2>
			<p>We are pleased to provide both new and existing clients with a
				variety of ways to save on exceptional hair and beauty services..</p>
			<p>
				<a class="btn btn-lg btn-primary" href="http://www.persona-fied.com"
					target="_blank" role="button">Visit Offers/Savings &raquo;</a>
			</p>

		</div> -->

	</div>
	<!-- /container -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!--  add the following lib in order to avoid JQuery conflict -->
		<script	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script> 
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


</body>
</html>