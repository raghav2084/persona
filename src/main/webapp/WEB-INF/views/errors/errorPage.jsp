<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page buffer="16kb"%>
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

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="${Header}" />

<title>Persona | Errors</title>
</head>
<body>

<div class="container">
			
			<div class="jumbotron" id="myJumbotron">
			<h2>${ErrorType}</h2>
			<h2>${UserType}</h2>
			<a href="${RedirectURL}"><p class="text-danger">${RedirectMsg}</p></a>
			
			
		</div>
	</div>

	
</body>
</html>