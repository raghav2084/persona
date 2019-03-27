<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page buffer="16kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--for adding favicon -->
	<link rel="shortcut icon" type="image/png" href="resources/images/favicon.ico"/>

<jsp:include page="../include/OwnHeader.jsp" />
<title>Persona | Home</title>
</head>

<body>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>Accept Payments</h4>
			</div>
			<div class="panel-body">
				<form id="paymentForm" method="post" action="savepayment">
					</br>
					<div class="row">
						<!-- row 1 start -->
						<div class="col-sm-4">
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-user"></span></span> <input type="text"
									class="form-control" placeholder="Customer Name" required
									name="customerName" id="customerNameID" data-toggle="customerNameIDtooltip" data-original-title="Name cannot be blank">
							</div>
						</div>
						<div class="col-sm-4">
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-phone-alt"></span></span> 
									<input
									type="number" class="form-control"
									oninput="javascript: if (this.value.length  > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
									maxlength="10"
									id="customerPhoneNumberID"
									placeholder="Mobile Number (xxx-xxx-xxxx)" required
									name="customerPhoneNumber"
									data-toggle="customerPhoneNumberIDtooltip"
									data-original-title="Number cannot left blank or be less than 10 digits">
							</div>
						</div>
					</div>
					<!-- row 1 end -->
					</br>
					<div class="row">
						<!-- row 2 start -->
						<div>
							<div class="col-sm-4">
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span></span> <input
										type="text" id="cal" class="form-control" required
										name="paymentDate" placeholder="Appointment Time" data-toggle="caltooltip" data-original-title="Appointment Time cannot be blank"> 
										<span
										class="input-group-btn"><button type="button"
											class="btn btn-default" id="myNowButton">Now</button> </span>
								</div>
							</div>
						</div>
					</div>
					<!-- row 2 end -->
					</br>
					<div class="row">
						<!-- row 3 start -->
						<!-- 	<div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-user"></span></span> <input type="text"
									class="form-control" placeholder="Username">
							</div>
						</div> -->
						<div class="col-sm-4">
							<div class="input-group">
								<span class="input-group-addon">&#x20B9;</span> <input
									type="number" class="form-control" placeholder="Amount"
									id="initialAmount" name="initialAmount" data-toggle="initialAmounttooltip" data-original-title="Amount cannot be blank">
									<span
									class="input-group-addon">.00</span>
							</div>
						</div>
						<!-- <div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon">$</span> <input type="text"
									class="form-control" placeholder="US Dollar"> <span
									class="input-group-addon">.00</span>
							</div>
						</div> -->
					</div>
					<!-- row 3 end -->
					</br>
					<div class="row">
						<!-- row 4 start -->
						<div class="col-sm-4">
							<label> <input type="checkbox" data-toggle="collapse"
								data-target="#DiscountCheckBox"> Do you want to offer
								any Discount/Coupons?
							</label>
						</div>
						<!-- col end -->

						<div class="collapse" id="DiscountCheckBox">
							<div class="col-sm-3">
								<div class="input-group">
									<span class="input-group-addon"> <input name="a"
										value="discountradiovalue" type="radio">
									</span> <input type="number" class="form-control"
										placeholder="Discount" id="DiscontSelectedID"
										oninput="javascript: if (this.value.length  > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
										maxlength="2"
										name="discountAmount"> <span class="input-group-addon">%</span>

								</div>
							</div>
							<div class="col-sm-3">
								<div class="input-group">
									<span class="input-group-addon"> <input name="a"
										type="radio" value="couponsradiovalue">
									</span> <input type="text" id="couponstextID" class="form-control"
										placeholder="Coupon Number">
								</div>
							</div>
						</div>
						<!-- Collapse end -->
					</div>
					<!-- row 4 end -->
					</br>
					<div class="row">
						<!-- row 5 starts -->
						<div class="col-sm-4">
							<select class="form-control" name="serviceProviderName" id="serviceProviderNameID" data-toggle="serviceProviderNameIDtooltip" data-original-title="Amount cannot be blank">
								<option value="">Select Stylist/Service Provider</option>
								<c:forEach var="EmpNames" items="${AllEmpNames}" varStatus="i">
									<option>${EmpNames.empName}</option>
								</c:forEach>

							</select>
						</div>
					</div>
					<!-- row 5 ends -->
					</br>
						<label data-toggle="SelectServicestoottip" data-original-title="Choose atleast one Service">Select Services: </label>
					<div class="row">
						<!-- row 6 starts -->

						<!-- Including the service menu from the servicemenu.jsp-->
						<jsp:include page="../employee/servicemenu.jsp" />

					</div>
					<!-- row ends -->
					</br>
					<div class="row">
						<!-- row starts -->

						<div class="col-sm-4">
							<div class="input-group">
								<span class="input-group-addon">&#x20B9;</span> <input
									type="text" class="form-control" placeholder="Final Amount"
									id="finalAmount" name="finalAmount" readonly> <span
									class="input-group-addon">.00</span>
							</div>
						</div>

					</div>
					<!-- row end -->
					</br>
					<button class="btn btn-md btn-primary" type="submit" data-loading-text="Saving..." id="ownPaySubmitButtonID">Submit</button>
					<button class="btn btn-md btn-info" id="resteButtonID" type="reset">Reset</button>
				</form>
			</div>
			<!-- panel body end  -->
		</div>
		<!-- Payment Panel Ends here -->

		<div class="panel panel-primary">
			<!-- Recent Payment panel starts  -->
			<div class="panel-heading">
				<h4 id="todaysdate">
					 Recent Payments <span class="badge" style="font-size: 20px">${fn:length(paymentsList)}</span>
					<div class="pull-right">
						<!-- <a href="#" class="btn btn-lg btn-primary">Learn More</a>
						 <a href="#" class="btn btn-lg btn-default"><span class="glyphicon glyphicon-new-window"></span></a> -->
						<a href="allpayments" class="btn btn-xs btn-default" id="detailsbutton">ALL</a>

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
<!-- 								<th>Customer Phone</th> -->
								<th>Stylist</th>
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
<%-- 								<td>${pay.customerPhoneNumber}</td> --%>
								<td>${pay.serviceProviderName}</td>
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
				</div>
			</div>
			<!-- Recent Payment panel body ends  -->
			<div class="panel-footer clearfix">
				<div class="row">						
						<h4 class="col-md-8 col-lg-offset-9 text-danger">Total Amount : ${total}</h4>
				
			</div>
		</div>
		<!-- Recent Payment panel ends  -->




	</div>
	<!-- end of container -->

	<script src="<c:url value="/resources/js/PaymentPage.js" />" /></script>
</body>
</html>