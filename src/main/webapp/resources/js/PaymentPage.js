$(function() {

	/*$('#detailsbutton').click(function(e) {
		e.preventDefault();
		alert("fygvgvk");
	});*/

	// function to give now time (used by now button)
	$('#myNowButton').click(
			function(e) {
				e.preventDefault();
				var currentdate = new Date();

				var tday = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
						"Sat");
				var tmonth = new Array("Jan", "Feb", "Mar", "Apr", "May",
						"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
				var nday = currentdate.getDay();
				var nmonth = currentdate.getMonth();
				var ndate = currentdate.getDate();
				var nyear = currentdate.getFullYear();
				var nhour = currentdate.getHours();
				var nmins = currentdate.getMinutes();

				if (nmins < 9)
					nmins = "0" + nmins;
				else
					nmins = nmins;

				if (nhour == 0) {
					ap = " AM";
					nhour = 12;
				} else if (nhour <= 11) {
					ap = " AM";
				} else if (nhour == 12) {
					ap = " PM";
				} else if (nhour >= 13) {
					ap = " PM";
					nhour -= 12;
				}

				/*var date = tday[nday] + ", " + tmonth[nmonth] + " " + ndate
						+ " '" + nyear + " " + nhour + ":" + nmins + "" + ap;*/

				var date = tmonth[nmonth] + " " + ndate + ", " + nyear + " "
						+ nhour + ":" + nmins + "" + ap;

				/*
				 * var datetime = currentdate.getDate() + "/" +
				 * (currentdate.getMonth()+1) + "/" + currentdate.getFullYear() + " " +
				 * currentdate.getHours() + ":" + currentdate.getMinutes();
				 */

				$('#cal').val(date);

			});

	// used to copy Initial amount to Final Amount field
	$('#initialAmount').change(function() {
		$('#finalAmount').val($(this).val());
	});

	// Disabling Discount and Coupons field on selecting radio button
	$("input[type='radio'][name=a]").click(function() {
		if ($(this).val() == 'discountradiovalue') {
			$("#DiscontSelectedID").prop("disabled", false);
			$("#couponstextID").prop("disabled", true);

		} else if ($(this).val() == 'couponsradiovalue') {

			$("#couponstextID").prop("disabled", false);
			$("#DiscontSelectedID").prop("disabled", true);

		}
	});

	// Disabling Discount and Coupons field on clicking in text area
	if ($("#DiscontSelectedID").click()) {
		$("#couponstextID").prop("disabled", true);
		$("#DiscontSelectedID").prop("disabled", true);

	}

	// reset button enabling discount & coupons fields if they are disabled.
	$("#resteButtonID").click(function() {
		$("#couponstextID").prop("disabled", true);
		$("#DiscontSelectedID").prop("disabled", true);
	});

	var currentdate = new Date();

	var tday = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
	var tmonth = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec");
	var nday = currentdate.getDay();
	var nmonth = currentdate.getMonth();
	var ndate = currentdate.getDate();
	var nyear = currentdate.getFullYear();
	var nhour = currentdate.getHours();
	var nmins = currentdate.getMinutes();

	if (nmins < 9)
		nmins = "0" + nmins;
	else
		nmins = nmins;

	if (nhour == 0) {
		ap = " AM";
		nhour = 12;
	} else if (nhour <= 11) {
		ap = " AM";
	} else if (nhour == 12) {
		ap = " PM";
	} else if (nhour >= 13) {
		ap = " PM";
		nhour -= 12;
	}

	var date = "Recent Payments Today - " + tday[nday] + ", " + tmonth[nmonth]
			+ " " + ndate;
	//$('#todaysdate').text(date);

	//employee payment submit button
	$('#empPaySubmitButtonID').click(function(e) {
		if (!validate2('customerNameID'))
		{
			return false;
		}
		if (!validate2("customerPhoneNumberID")) {
			return false;
		}
		if (!validate2("cal")) {
			return false;
		}
		if (!validate2("initialAmount")) {
			return false;
		}
		if (!validate2("serviceProviderNameID")) {
			return false;
		}
		
		//if(validateCheckbox("serviceList"))
		if($("input[name='serviceList']:checked").length<1)
		{
			//alert("if"+$("input[name='serviceList']:checked").length);
			var div = $("input[name='serviceList']").closest("div")
			//alert("Please select atleast one service");
			$('[data-toggle="SelectServicestoottip"]').tooltip('show').tooltip({
		        placement : 'right'
		    });
			$('#SelectServicestoottip').addClass("text-danger");
			return false;
			
		}
		//else{
			//alert("else "+$("input[name='serviceList']:checked").length);
			//var div = $("input[name='serviceList']").closest("div");
			//div.remove();
			//}

		var $btn = $(this);
		$btn.button('loading');
		//if no validation error then submit the form
		$("form#paymentForm").submit();

		setTimeout(function() {
			$btn.button('reset');
		}, 1000);
	});

});//end of main function

function validate(id) {

	if ($("#" + id).val() == null || $("#" + id).val() == "") {
		var div = $("#" + id).closest("div");
		$("#glypcn" + id).remove();
		div.addClass("has-error has-feedback");
		div
				.append(' <span id="glypcn'
						+ id
						+ '" class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
		//alert("Validation Error" +$("#"+id).val() );
		return false;
	} else {
		var div = $("#" + id).closest("div");
		div.removeClass("has-error");
		div.addClass("has-success has-feedback");
		$("#glypcn" + id).remove();
		/*div.append('<span id="glypcn'+id+'" class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');*/
		return true;
	}

}

function validate2(id) {

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
