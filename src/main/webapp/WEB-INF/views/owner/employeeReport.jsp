<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page buffer="16kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />

<jsp:include page="../include/OwnHeader.jsp" />
<title>Reports | Employee</title>
</head>
<body>
	<div class="container">
	
	<!-- Time filter -->
	<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>Time Filter</h4>
			</div>
			<div class="panel-body">
				<form id="rangeDateForm" method="post" action="empreport">
					<div class="form-group">
						<label class="col-xs-1"> </label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="txtFromDate"
								placeholder="From" name=fromDate data-toggle="txtFromDatetooltip" data-original-title="Cannot be blank">
						</div>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="txtToDate"
								placeholder="To" name=toDate data-toggle="txtToDatetooltip" data-original-title="Cannot be blank">
						</div>

					</div>
					<button type="button" class="btn btn-primary" id="timeFilerSearchButtonID">Search</button>
					<button class="btn btn-md btn-info" id="resteButtonID" type="reset">Reset</button>
					<!-- Hidden value to send the EmpPhone -->
									<input type=hidden name="EmpPhoneNumHidden" value="${Emp.empPhoneNum}">
				</form>
			</div>
		</div>
	

		<div class="panel panel-primary">
			<!-- Recent Payment panel starts  -->
			<div class="panel-heading">
				<h4>
					${Emp.empName} <span class="badge" style="font-size: 20px">${fn:length(paymentsList)}</span>
					<small style="color: white">${msg}</small>
					<div class="pull-right">
						<!-- <a href="#" class="btn btn-lg btn-primary">Learn More</a> -->
						<!--  <a href="#" class="btn btn-lg btn-default"><span class="glyphicon glyphicon-new-window"></span></a> -->
						<!-- <a href="" class="btn btn-xs btn-default" id="detailsbutton">Details</a> -->
						<%-- <div class="btn-group">
							<button type="button" data-toggle="dropdown"
								class="btn btn-default dropdown-toggle">
								Time Filter <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="empreporttoday?num=${Emp.empPhoneNum}">Today</a></li>
								<li><a href="empreportweek?num=${Emp.empPhoneNum}">Last Week</a></li>
								 <li><a href="empreporttwweek?num=${Emp.empPhoneNum}">Last Two weeks</a></li>
								<li><a href="#">Last Three weeks</a></li>
								<li><a href="#">Past Month</a></li>
							</ul>
						</div>  --%>
					</div>
				</h4>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<!-- Table -->
					<table class="table table-hover">
						<thead>
							<tr>
								<!-- <th>#</th> -->
								<th>Payment ID</th>
								<th>Time</th>
								<th>Customer Name</th>
								<th>Customer Phone</th>
								<th>Services</th>
								<th>Amount</th>
							</tr>
						</thead>
						<c:set var="total" value="0" />
						<c:forEach var="pay" items="${paymentsList}" varStatus="i">
							<tr>
								<%-- <td>${i.count}</td> --%>
								<td>${pay.paymentID}</td>
								<td>${pay.paymentDate}</td>
								<td>${pay.customerName}</td>
								<td>${pay.customerPhoneNumber}</td>
								<td>
								<%-- <c:forEach var="service" items="${pay.serviceList}"
										varStatus="i">
								 ${service},
								</c:forEach> --%>
								${pay.serviceList}
								</td>
								<td>${pay.finalAmount}</td>
								<c:set var="total" value="${total+pay.finalAmount}" />
							</tr>
						</c:forEach>
					</table>
					<h4 class="text-danger">${NoResultsFound}</h4>
				</div>
			</div>
			<!-- Recent Payment panel body ends  -->
			<div class="panel-footer clearfix">
				<div class="row">

					<h4 class="col-md-8 col-lg-offset-9 text-danger">Total Amount
						: ${total}</h4>

				</div>
			</div>
		</div>
		<!-- Recent Payment panel ends  -->


	</div>
	<!-- end od container -->
	<script src="<c:url value="/resources/js/NavBarActiveHeaderJS.js" />" /></script>
	<script src="<c:url value="/resources/js/DatepickerJS.js" />" /></script>
</body>
</html>