<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />

<jsp:include page="../include/OwnHeader.jsp" />
<title>Persona | All Payments</title>
</head>
<body>
	<div class="container">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>Time Filter</h4>
			</div>
			<div class="panel-body">
				<form id="rangeDateForm" method="post" action="allpayments">
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
					<button type="button" id="timeFilerSearchButtonID" class="btn btn-primary">Search</button>
					<button class="btn btn-md btn-info" id="resteButtonID" type="reset">Reset</button>
				</form>
			</div>
		</div>




		<div class="panel panel-primary">
			<!-- Recent Payment panel starts  -->
			<div class="panel-heading">
				<h4>
					Payments <span class="badge" style="font-size: 20px"id="TotalPaymentBadgeID"> ${fn:length(paymentsList)}</span>
						<small style="color: white">${msg}</small>
					<!-- <div class="pull-right">
						<a href="#" class="btn btn-lg btn-primary">Learn More</a>
						<button type="button" class="btn btn-default" data-toggle="modal"
							data-target="#addEmp">
							<span class="glyphicon glyphicon-plus"></span>
						</button>
					</div> -->
				</h4>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<!-- Table -->
					<table class="table table-striped">
						<thead>
							<tr>
								<!-- <th>#</th> -->
								<th>Payment ID</th>
								<th>Time</th>
								<th>Customer Name</th>
								<th>Customer Phone</th>
								<th>Stylist</th>
								<th>Services</th>
								<th>Amount</th>
								<th>Action</th>
							</tr>
						</thead>
				
						<c:set var="total" value="0" />
						<c:forEach var="pay" items="${paymentsList}" varStatus="i">
							<tr>
								<%-- <td>${i.count}</td>  --%>
								<td>${pay.paymentID}</td>
								<td>${pay.paymentDate}</td>
								<td>${pay.customerName}</td>
								<td>${pay.customerPhoneNumber}</td>
								<td>${pay.serviceProviderName}</td>
								<td>
									<%-- <c:forEach var="service" items="${pay.serviceList}"
										varStatus="i">
								 ${service},
								</c:forEach> --%> ${pay.serviceList}
								</td>
								<td>${pay.finalAmount}</td>
								<td>
									<div>
										<button type="button" class="editPayment btn-sm btn-primary"
											id="${i.count}">Edit</button>
										<button type="button"
											class="delPay btn-sm btn-primary btn-danger" id="${i.count}">Delete</button>
									</div>
								</td>
								<c:set var="total" value="${total+pay.finalAmount}" />
							</tr>
						</c:forEach>
					</table>
					<h4 class="text-danger">${NoResultsFound}</h4>
				</div>
			</div>

			<div class="panel-footer clearfix">
				<div class="row">
					<h4 class="col-md-8 col-lg-offset-9 text-danger" id="TotalAmountID">Total
						Amount :${total}</h4>

				</div>
			</div>


			<!-- EDIT PAYMENT MODEL -->
			<div id="editPay" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">Edit Payment</h4>
						</div>
						<div class="modal-body">
							<form role="form" id="editPayFormID" action="editPay"
								method="post">
								<div class="form-group">
									<label for="emp-name" class="control-label">Payment ID:</label>
									<input type="text" class="form-control" id="edit-pay-id"
										name="paymentID" readonly>

								</div>


								<div class="form-group">
									<label for="emp-name" class="control-label">Customer
										Name:</label> <input type="text" class="form-control"
										id="edit-pay-customername" name="CustomerName">
								</div>
								<div class="form-group">
									<label for="emp-phone" class="control-label">Customer
										Number:</label> <input type="text" class="form-control"
										id="edit-pay-customerphone" name="customerPhoneNumber">
								</div>
								<div class="form-group">
									<label for="emp-phone" class="control-label">Appointment
										Time:</label> <input type="text" class="form-control"
										id="edit-pay-time" name="paymentDate" readonly>
								</div>
								<div class="form-group">
									<!-- <label for="emp-phone" class="control-label">Stylist: </label>
									<input type="text" class="form-control" id="edit-pay-stylist"
										name="serviceProviderName"> -->
									<label for="emp-phone" class="control-label">Stylist: </label>
									<select class="form-control" name="serviceProviderName"
										id="edit-pay-stylist">
										<c:forEach var="EmpNames" items="${AllEmpNames}" varStatus="i">
											<option>${EmpNames.empName}</option>
										</c:forEach>

									</select>

								</div>
								<div class="form-group">
									<label for="emp-phone" class="control-label">Services:
									</label>
									<textarea class="form-control" rows="2" id="edit-pay-services"
										name="serviceList"></textarea>
								</div>
								<div class="form-group">
									<label for="emp-phone" class="control-label">Final
										Amount: </label> <input type="text" class="form-control"
										id="edit-pat-finalAmount" name="finalAmount">
								</div>
								<!-- Hidden value to send the EmpPhone -->
								<input type=hidden id="editmpModalHiddenEmpNumID"
									name="editEmpModalHiddenEmpNum">
							</form>
						</div>
						<div class="modal-footer">

							<button class="btn btn-lg btn-primary btn-block"
								id="editPayButtonID" type="button"
								data-loading-text="Updating...">Update</button>
							<button type="button" class="btn btn-lg btn-default btn-block"
								data-dismiss="modal">Cancel</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Delete PAYMENT MODEL -->
			<div id="deletePayModal" class="modal fade">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">Delete Payment</h4>
						</div>
						<div class="modal-body">
							<form role="form" id="deletePayModalFormID" action="deletePay"
								method="post">
								<p id="deletePayModalMsgID"></p>
								<input type=hidden id="deletePayModalHiddenPayID"
									name="deletePayModalHiddenPayName">
							</form>
						</div>
						<div class="modal-footer">

							<button class="btn btn-lg btn-primary btn-block"
								id="deletePayButtonID" type="button"
								data-loading-text="Deleting...">Delete</button>
							<button type="button" class="btn btn-lg btn-default btn-block"
								data-dismiss="modal">Cancel</button>
						</div>
					</div>
				</div>
			</div>





		</div>
		<!-- Recent Payment panel body ends  -->
	</div>
	<!-- Recent Payment panel ends  -->


	</div>
	<!--  <script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>  -->
	<!-- <script	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/jquery-ui.min.js"></script>  -->
	<!-- end of container -->
 	<script src="<c:url value="/resources/js/NavBarActiveHeaderJS.js" />" /></script>
	<script src="<c:url value="/resources/js/allpaymentJS.js" />" /></script>
<%-- <script src="<c:url value="/resources/js/DatepickerJS.js" />" /></script>	 	 --%>
</body>
</html>