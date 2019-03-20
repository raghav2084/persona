<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page buffer="16kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../include/OwnHeader.jsp" />

<title>Persona | Errors</title>
</head>
<body>

<div class="container">
			
			<div class="jumbotron" id="myJumbotron">
			<button type="button" id="myButton" data-dismiss="alert"
				class="close" aria-label="Close">
		
			</button>
			<h2>${ErrorType}</h2>
			<p class="text-danger">${ErrorMsg}</p>
			
			

		</div>
	</div>
	<!-- end of container -->
	<script src="<c:url value="/resources/js/NavBarActiveHeaderJS.js" />" /></script>
	
</body>
</html>