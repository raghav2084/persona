/**
 * 
 */
$(function() {

	// edit table row data by clicking edit button
	$(".editPayment").click(function(event) {

		// alert(event.target.id);
		// $("tr").eq(event.target.id).css( "background-color", "red" );

		/*var rowdata = $("tr").eq(event.target.id).children("td").map(
				function() {
					return $(this).text();
				}).get();*/

		var rowdata = $(this).closest("tr").children("td").map(function() {
			return $(this).text();
		}).get();

		var len = rowdata.length;

		// alert("Your row data is: " + $.trim(rowdata[5]));
		// alert("Your row data is: " + $.trim(rowdata[0]) + " , " +
		// $.trim(rowdata[1]) + " , " + $.trim(rowdata[2]) + " len:
		// "+len);
		/*
		 * for (var i= 1;i<len-1;i++){ alert("Your row data is: " +
		 * $.trim(rowdata[i])); }
		 */

		// var s ="";
		// var arr = $.trim(rowdata[5]).split(',');
		// for (var j=0;j<arr.length-1;j++){
		// s = $.trim(arr[j]) +", "+ s;
		// }
		// alert(s);
		// alert("S "+ $.trim(arr[5]));
		// alert(arr.length);
		var payid = $.trim(rowdata[0]);
		$(".modal-body #edit-pay-id").val(payid);
		var time = $.trim(rowdata[1]);
		$(".modal-body #edit-pay-time").val(time);
		var custName = $.trim(rowdata[2]);
		$(".modal-body #edit-pay-customername").val(custName);
		var custPhone = $.trim(rowdata[3]);
		$(".modal-body #edit-pay-customerphone").val(custPhone);
		var stylist = $.trim(rowdata[4]);
		$(".modal-body #edit-pay-stylist").val(stylist);
		var services = $.trim(rowdata[5]);
		$(".modal-body #edit-pay-services").val(services);
		var finalAmount = $.trim(rowdata[6]);
		$(".modal-body #edit-pat-finalAmount").val(finalAmount);
		// setting editmpModalHiddenEmpNumID Hidden value
		// and passing to DB.In DB update works by deleting old and
		// saving updating new
		// Incase user edits phonenuber, we need the old number to
		// lookup and delete
		// $(".modal-body
		// #editmpModalHiddenEmpNumID").val($.trim(rowdata[3]));
		$("#editPay").modal('show');
	});

	// Model/alert on clicking delete button for a row
	$(".delPay").click(
			function(event) {
					
				
				//alert(event.target.id)
				/*var rowdata = $("tr").eq(event.target.id).children("td").map(
						function() {
							return $(this).text();
						}).get();*/
				//made it dynamic
				var rowdata = $(this).closest("tr").children("td").map(
						function() {
							return $(this).text();
						}).get();
				//alert(rowdata.length)
				var msg = "Are you sure you want to delete Payment ID: "
						+ $.trim(rowdata[0]) + ", Customer Name: "
						+ $.trim(rowdata[2]) + "?";
				$(".modal-body #deletePayModalMsgID").text(msg);
				$(".modal-body #deletePayModalHiddenPayID").val(
						$.trim(rowdata[0]));
				// $(".modal-body
				// #deleteEmpModalHiddenEmpNameID").val($.trim(rowdata[1]));
				$("#deletePayModal").modal('show');
			});

	// Actual delete after clicking delete on Modal
	$("#deletePayButtonID")
			.click(
					function(event) {

						//storing to update Badge of total payments 
						var totolNumPay = $('#TotalPaymentBadgeID').text();
						var totolNumPayInt = parseInt(totolNumPay);
						var arr = $('#TotalAmountID').text();
						var b = arr.split(":");
						var amt = parseInt(b[1]);
						//alert("from page "+amt)

						var $btn = $(this);
						$btn.button('loading');

						// if no validation error then submit the form
						// $("form#deletePayModalFormID").submit();
						//deleteUserAjaxCall();

						var data = {
							deletePayModalHiddenPayName : $(
									'#deletePayModalHiddenPayID').val()
						};

						$
								.ajax({
									type : 'POST',
									//contentType: "application/json; charset=utf-8",
									url : 'deletePay',
									//dataType: "json",
									data : data,
									success : function(response) {
										//alert(response);
										$("#deletePayModal").modal('hide');
										var amtToMinus = parseInt(response.finalAmount);

										//to get the row which we will delete after Successful AJAX detetion from the server
										var tableRow = $("td")
												.filter(
														function() {
															return $(this)
																	.text() == $(
																	'#deletePayModalHiddenPayID')
																	.val();
														}).closest("tr")

										tableRow.css("background-color",
												"#86BBFF").fadeOut(1500,
												function() {
													tableRow.remove();
												});

										var newAmt = amt - amtToMinus;
										var newString = "Total Amount : "
												+ newAmt;
										//alert (newString)
										var newPayNum = totolNumPayInt - 1;
										$('#TotalPaymentBadgeID').text(newPayNum);
										$('#TotalAmountID').text(newString);

									},
									error : function(e) {
										alert('Error Occurred while Deleting'
												+ e);
									}
								});

						setTimeout(function() {
							$btn.button('reset');
						}, 1000);

					});

	// Updating payment
	$('#editPayButtonID').click(function(e) {

		var arr = $('#TotalAmountID').text();
		
		var b = arr.split(":");
		var amt = parseInt(b[1]);
		//alert("from page "+amt)

		var $btn = $(this);
		$btn.button('loading');

		// if no validation error then submit the form
		//$("form#editPayFormID").submit();

		$.ajax({
			type : 'POST',
			//contentType: "application/json; charset=utf-8",
			url : 'editPay',
			//dataType: "json",
			data : $("#editPayFormID").serialize(),
			success : function(response) {
				// alert(response);
				$("#editPay").modal('hide');
				// alert($('#edit-pay-id').val())

				//to get the row which we will delete after Successful AJAX detetion from the server
				var tableRow = $("td").filter(function() {
					return $(this).text() == $('#edit-pay-id').val();
				}).closest("tr")
				//getting row data 
				/*var tdName = tableRow.find("td").eq(2).html();
				var tdPH = tableRow.find("td").eq(3).html();
				var tdStylist = tableRow.find("td").eq(4).html();
				var tdServices = tableRow.find("td").eq(5).html();
				var tdAmt = tableRow.find("td").eq(6).html();
				alert("Result: "+tdName+" "+tdPH+" "+tdStylist+" "+tdServices+" "+tdAmt)*/

				var oldAmount = parseInt(tableRow.find("td").eq(6).html());
				//updating the row data with updated values
				tableRow.find("td").eq(2).html(response.customerName);
				tableRow.find("td").eq(3).html(response.customerPhoneNumber);
				tableRow.find("td").eq(4).html(response.serviceProviderName);
				tableRow.find("td").eq(5).html(response.serviceList);
				tableRow.find("td").eq(6).text(response.finalAmount);
				var newAmount = parseInt(response.finalAmount);
								
				if (newAmount > oldAmount) {
					//alert ("New Price is more")
					var amtDiff = newAmount - oldAmount;
					var newAmt = amt + amtDiff;
					var newString = "Total Amount : " + newAmt;
					//alert (newString)
					$('#TotalAmountID').text(newString);

				} else if (newAmount < oldAmount) {
					//alert ("New Price is less")
					var amtDiff = oldAmount - newAmount;
					var newAmt = amt - amtDiff;
					var newString = "Total Amount : " + newAmt;
					//alert (newString)
					$('#TotalAmountID').text(newString);

				} else {
					//alert ("Do nothing")
				}

			},
			error : function(e) {
				alert('Error occurred while Updating Payment' + e);
			}
		});

		setTimeout(function() {
			$btn.button('reset');
		}, 1000);
	});

	var daysToAdd = 30;
	$("#txtFromDate").datepicker({
		maxDate: new Date(),
		onSelect : function(selected) {
			//var dtMax = new Date()
			var dtMax = new Date(selected);
			dtMax.setDate(dtMax.getDate() - daysToAdd);
			dtMax.setDate(dtMax.getDate());
			var dd = dtMax.getDate();
			var mm = dtMax.getMonth() + 1;
			var y = dtMax.getFullYear();
			var dtFormatted = mm + '/' + dd + '/' + y;
			//$("#txtToDate").datepicker("option", "minDate", dtFormatted);
			//$("#txtToDate").datepicker("option", "maxDate", dtFormatted);
		}
	});

	$("#txtToDate").datepicker({
		maxDate: new Date(),
		onSelect : function(selected) {
			var dtMax = new Date()
			var dtMax = new Date(selected);
			dtMax.setDate(dtMax.getDate() - daysToAdd);
			//dtMax.setDate(dtMax.getDate());
			var dd = dtMax.getDate();
			var mm = dtMax.getMonth() + 1;
			var y = dtMax.getFullYear();
			var dtFormatted = mm + '/' + dd + '/' + y;
			//$("#txtFromDate").datepicker("option", "maxDate", dtFormatted)
			//$("#txtFromDate").datepicker("option", "minDate", dtFormatted)
		}
	});
	
$('#timeFilerSearchButtonID').click(function(e) {
		
		
		if(!validate('txtFromDate'))
			{
				
				return false;
			}
		if(!validate("txtToDate"))
			{
				return false;
			}
		
		var $btn = $(this);
	    $btn.button('loading');
		//if no validation error then submit the form
		$("form#rangeDateForm").submit();
		
		 setTimeout(function () {
		        $btn.button('reset');
		    }, 1000);
	});

});// End of main function.




function validate(id) {

	if ($("#" + id).val() == null || $("#" + id).val() == "") {
		var div = $("#" + id).closest("div");
		div.addClass("has-error");
		//alert("in new");
		$('[data-toggle="'+id+'tooltip"]').tooltip('show').tooltip({
	        placement : 'top'
	    });
		//alert("Validation Error" +$("#"+id).val() );
		return false;
	} else {
		var div = $("#" + id).closest("div");
		div.removeClass("has-error");
		//alert("in new else");
		$('[data-toggle="'+id+'tooltip"]').tooltip('destroy')
		return true;
	}


}