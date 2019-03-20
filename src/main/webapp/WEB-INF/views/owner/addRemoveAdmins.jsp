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

<title>Persona | Admin</title>
</head>
<body>

<div class="container">
			<div class="panel panel-primary">
			<!-- Recent Payment panel starts  -->
			<div class="panel-heading">
				<h4>
					Manage Admins
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
								<th>UserName</th>
								<th>Password</th>
								<th>LastLoginDateTime</th>
								<th>Email</th>
								<th>Is Owner</th>
								<th>Action</th>
							</tr>
						</thead>

						<c:forEach var="alladm" items="${AllAdm}" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${alladm.userName}</td>
								<td>${alladm.userPassword}</td>
								<td>${alladm.lastLoginDateTime}</td>
								<td>${alladm.email}</td>
								<td>${alladm.isOwner}</td>
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

				<!-- Modal Add New Admin-->
				<div id="addEmp" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Add New Admin</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="addNewEmpFormID" action="addNewEmp"
									method="post">
									<div class="form-group">
										<label for="emp-name" class="control-label">Employee
											Name:</label> <input type="text" class="form-control" id="emp-name"
											name="empName">
									</div>
									<div class="form-group">
										<label for="emp-phone" class="control-label">Phone
											Number:</label> <input type="text" class="form-control"
											id="emp-phone" name="empPhoneNum">
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


				<!-- EDIT ADMIN MODEL -->
				<div id="editEmp" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Edit Admin</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="editAdmFormID" action="editAdmin"
									method="post">
									<div class="form-group">
										<label for="emp-name" class="control-label">User
											Name:</label> <input type="text" class="form-control"
											id="edit-adm-name" name="userName">
									</div>
									<div class="form-group">
										<label for="emp-phone" class="control-label">Password
											</label> <input type="text" class="form-control"
											id="edit-adm-pass" name="userPassword">
									</div>
									<div class="form-group">
										<label for="emp-phone" class="control-label">Email
										</label> <input type="text" class="form-control"
											id="edit-adm-email" name="email">
									</div>
									<div class="form-group">
										<label for="emp-sex" class="control-label">Is
											Owner:</label> <select class="form-control" name="isOwner"
											id="edit-adm-isOwner">
											<option>y</option>
											<option>n</option>
										</select>
									</div>
									<!-- Hidden value to send the EmpPhone -->
									<input type=hidden id="	" name="editAdmModalHiddenAdmUname">
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
				<div id="deleteAdmModal" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Delete Admin</h4>
							</div>
							<div class="modal-body">
								<form role="form" id="deleteAdmModalFormID" action="deleteAdmin"
									method="post">
									<p id="deleteAdmModalMsgID"></p>
									<input type=hidden id="deleteAdmModalHiddenAdmNameID" name="deleteAdmModalHiddenAdmName">
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
	<script src="<c:url value="/resources/js/addRemoveAdminJS.js" />" /></script>
</body>
</html>