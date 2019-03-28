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

<title>Add Remove Employees</title>
</head>
<body>

<div class="container">
			<div class="panel panel-primary">
			<!-- Recent Payment panel starts  -->
			<div class="panel-heading">
				<h4>
					Manage Employees
					<div class="pull-right">
						<!-- <a href="#" class="btn btn-lg btn-primary">Learn More</a> -->
						<button type="button" class="btn btn-default" data-toggle="modal"
							data-target="#addEmp">
							<span class="glyphicon glyphicon-plus"></span>
						</button>
					</div>
				</h4>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<!-- Table -->
					<table class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Emp Name</th>
								<th>Sex</th>
								<th>Phone Number</th>
								<th>Emp Status</th>
								<th>Action</th>
							</tr>
						</thead>

						<c:forEach var="allemp" items="${AllEmp}" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${allemp.empName}</td>
								<td>${allemp.empSex}</td>
								<td>${allemp.empPhoneNum}</td>
								<td>${allemp.empStatus}</td>
								<td>
								<div >
										<button type="button" class="editEmp btn btn-primary" id="${i.count}">Edit</button>
										<button type="button" class="delEmp btn btn-primary btn-danger" id="${i.count}">Delete</button>
									</div>
										
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>

				<!-- Modal Add new content-->
				<div id="addEmp" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Add New Employee</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="addNewEmpFormID" action="addNewEmp"
									method="post">
									<div class="form-group">
										<label for="emp-name" class="control-label">Employee
											Name:</label> <input type="text" class="form-control" id="emp-name"
											name="empName"
											data-toggle="emp-nametooltip" 
											data-original-title="Name cannot be blank">
									</div>
									<div class="form-group">
										<label for="emp-phone" class="control-label">Phone
											Number:</label> <input type="number" class="form-control"
											id="emp-phone" name="empPhoneNum"
											oninput="javascript: if (this.value.length  > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
											maxlength="10"
											data-toggle="emp-phonetooltip"
											data-original-title="Number cannot left blank or be less than 10 digits">
									</div>
									<div class="form-group">
										<label for="emp-sex" class="control-label">Sex:</label> <select
											class="form-control" name="empSex">
											<option>Male</option>
											<option>Female</option>
										</select>
									</div>
								</form>
							</div>
							<div class="modal-footer">

								<button class="btn btn-lg btn-primary btn-block" id="addNewEmp"
									type="button" data-loading-text="Adding...">Add</button>
								<button type="button" class="btn btn-lg btn-default btn-block"
									data-dismiss="modal">Cancel</button>
							</div>
						</div>
					</div>
				</div>


				<!-- EDIT EMPLOYEE MODEL -->
				<div id="editEmp" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Edit Employee</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="editEmpFormID" action="editEmp"
									method="post">
									<div class="form-group">
										<label for="emp-name" class="control-label">Employee
											Name:</label> <input type="text" class="form-control"
											id="edit-emp-name" name="empName"
											data-toggle="edit-emp-nametooltip" 
											data-original-title="Name cannot be blank">
									</div>
									<div class="form-group">
										<label for="emp-phone" class="control-label">Phone
											Number:</label> <input type="text" class="form-control"
											id="edit-emp-phone" name="empPhoneNum"
											oninput="javascript: if (this.value.length  > this.maxLength) this.value = this.value.slice(0, this.maxLength);"
											maxlength="10"
											data-toggle="edit-emp-phonetooltip"
											data-original-title="Number cannot left blank or be less than 10 digits">
									</div>
									<div class="form-group">
										<label for="emp-sex" class="control-label">Sex:</label> <select
											class="form-control" name="empSex" id="edit-emp-sex">
											<option>Male</option>
											<option>Female</option>
										</select>
									</div>
									<div class="form-group">
										<label for="emp-sex" class="control-label">Employee
											Status:</label> <select class="form-control" name="empStatus"
											id="edit-emp-status">
											<option>Active</option>
											<option>Inactive</option>
										</select>
									</div>
									<!-- Hidden value to send the EmpPhone -->
									<input type=hidden id="editmpModalHiddenEmpNumID" name="editEmpModalHiddenEmpNum">
								</form>
							</div>
							<div class="modal-footer">

								<button class="btn btn-lg btn-primary btn-block" id="editEmpButtonID"
									type="button" data-loading-text="Updating...">Update</button>
								<button type="button" class="btn btn-lg btn-default btn-block"
									data-dismiss="modal">Cancel</button>
							</div>
						</div>
					</div>
				</div>

				<!-- Delete EMPLOYEE MODEL -->
				<div id="deleteEmpModal" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Delete Employee</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="deleteEmpModalFormID" action="deleteEmp"
									method="post">
									<p id="deleteEmpModalMsgID"></p>
									<input type=hidden id="deleteEmpModalHiddenEmpNumID" name="deleteEmpModalHiddenEmpNum">
									<input type=hidden id="deleteEmpModalHiddenEmpNameID" name="deleteEmpModalHiddenEmpName">
								</form>
							</div>
							<div class="modal-footer">

								<button class="btn btn-lg btn-primary btn-block" id="deleteEmpButtonID"
									type="button" data-loading-text="Deleting">Delete</button>
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
	<!-- end of container -->
	<script src="<c:url value="/resources/js/NavBarActiveHeaderJS.js" />" /></script>
	<script src="<c:url value="/resources/js/addRemoveEmployeesJS.js" />" /></script>
</body>
</html>