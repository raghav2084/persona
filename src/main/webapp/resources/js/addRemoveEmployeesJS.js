/**
 * To assist add new employee - perform validations
 * 
 */

$(function() {

	$('#addNewEmp').click(function(e) {

		if (!validate('emp-name')) {

			return false;
		}
		if (!validate("emp-phone")) {
			return false;
		}

		var $btn = $(this);
		$btn.button('loading');

		//if no validation error then submit the form
		$("form#addNewEmpFormID").submit();

		setTimeout(function() {
			$btn.button('reset');
		}, 1000);
	});

	//edit table row data by clicking edit button
	$(".editEmp").click(
			function(event) {

				//alert(event.target.id);
				//$("tr").eq(event.target.id).css( "background-color", "red" );

				var rowdata = $("tr").eq(event.target.id).children("td").map(
						function() {
							return $(this).text();
						}).get();

				var len = rowdata.length;

				//alert("Your row data is: " + $.trim(rowdata[0]) + " , " + $.trim(rowdata[1]) + " , " + $.trim(rowdata[2]) + " len: "+len);
				/* for (var i= 1;i<len-1;i++){
					 alert("Your row data is: " + $.trim(rowdata[i])); 
				 }*/

				var empName = $.trim(rowdata[1]);
				$(".modal-body #edit-emp-name").val(empName);
				var empNum = $.trim(rowdata[3]);
				$(".modal-body #edit-emp-phone").val(empNum);
				var empSex = $.trim(rowdata[2]);
				$(".modal-body #edit-emp-sex").val(empSex);
				var empStatus = $.trim(rowdata[4]);
				$(".modal-body #edit-emp-status").val(empStatus);
				//setting editmpModalHiddenEmpNumID Hidden value 
				//and passing to DB.In DB update works by deleting old and saving updating new
				//Incase user edits phonenuber, we need the old number to lookup and delete 
				$(".modal-body #editmpModalHiddenEmpNumID").val($.trim(rowdata[3]));
				$("#editEmp").modal('show');
			});

	//Updating employee
	$('#editEmpButtonID').click(function(e) {

		if (!validate('edit-emp-name')) {

			return false;
		}
		if (!validate('edit-emp-phone')) {

			return false;
		}
		var $btn = $(this);
		$btn.button('loading');

		//if no validation error then submit the form
		$("form#editEmpFormID").submit();

		setTimeout(function() {
			$btn.button('reset');
		}, 1000);
	});
	
	
	//Model/alert on clicking delete button for a row
	$(".delEmp").click(
			function(event) {

				var rowdata = $("tr").eq(event.target.id).children("td").map(
						function() {
							return $(this).text();
						}).get();

				var msg = "Are you sure you want to delete "+$.trim(rowdata[1])+"?";
				$(".modal-body #deleteEmpModalMsgID").text(msg);
				$(".modal-body #deleteEmpModalHiddenEmpNumID").val($.trim(rowdata[3]));
				$(".modal-body #deleteEmpModalHiddenEmpNameID").val($.trim(rowdata[1]));
				$("#deleteEmpModal").modal('show');
			});

	
	//Actual delete after clicking delete on Modal
	$("#deleteEmpButtonID").click(
			function(event) {

				var $btn = $(this);
				$btn.button('loading');

				//if no validation error then submit the form
				$("form#deleteEmpModalFormID").submit();

				setTimeout(function() {
					$btn.button('reset');
				}, 1000);
			
			});
	
	//phone number validation 
	//if digits b/w 1-9
	//add error class and tooltip
	$('#emp-phone, #edit-emp-phone').keyup(function(){
		//alert("ke");
		var number = $(this).val();
		var div = $(this).closest("div");
		if(number.length == 10)
			{
			//if 10 digits - remove error class
			div.removeClass("has-error");
			$('[data-toggle="'+$(this).attr('id')+'tooltip"]').tooltip('destroy')
			}
		//if del is clicked, bring back error and tooltip
		else {
				div.addClass("has-error");
				//alert("Validation Error" +$("#"+id).val() );
				$('[data-toggle="'+$(this).attr('id')+'tooltip"]').tooltip('show').tooltip({
			        placement : 'top'
			    });
			}
	});

	//keypress validation on name
	$('#emp-name, #edit-emp-name').keyup(function(){
		//alert("asdad "+$(this).attr('id')+" sdas");
		var div = $(this).closest("div");
		if ($(this).val() == null || $(this).val() == ""){
			
			div.addClass("has-error");
			//alert("Validation Error" +$("#"+id).val() );
			//alert('[data-toggle="'+$(this).attr('id')+'tooltip"]');
			$('[data-toggle="'+$(this).attr('id')+'tooltip"]').tooltip('show').tooltip({
		        placement : 'top'
		    });
		}else {
			div.removeClass("has-error");
			$('[data-toggle="'+$(this).attr('id')+'tooltip"]').tooltip('destroy');
			
		}
	});
	
});//end of main function

function validate(id) {
	var div = $("#" + id).closest("div");

	//alert(div.hasClass("has-error"));
	
	if ($("#" + id).val() == null || $("#" + id).val() == "" || div.hasClass("has-error"))//there is already a validation error
	 {
		div.addClass("has-error");
		//alert("Validation Error" +$("#"+id).val() );
		$('[data-toggle="'+id+'tooltip"]').tooltip('show').tooltip({
	        placement : 'top'
	    });
		return false;
	}else {
		div.removeClass("has-error");
		$('[data-toggle="'+id+'tooltip"]').tooltip('destroy')
		return true;
	}

}