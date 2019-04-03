<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>

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
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="contact">Contact</a></li>
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
									<a href="logout" class="btn btn-primary btn-block" data-loading-text="Logging out...">Logout</a>
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
		<div class="jumbotron" id="myJumbotron">
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

		</div>

	</div>
	<!-- /container -->

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


</body>
</html>