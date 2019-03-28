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

	//Mobile phone verification on out of focus
	//if digits entered between 1-10 digits
	//note : if input is blank this code will check
	//that but will not trigger any error.
	$('#customerPhoneNumberID').focusout(function() {
		var number = $(this).val();
		var div = $(this).closest("div");
		 var hasErrorClass = $(div).hasClass("has-error");
		//alert(hasErrorClass);
		//less than 10 digits
		if(number.length > 0 && number.length < 10)
		{	
			
				//add error class
				div.addClass("has-error");
				//show tooltip with message
				$('[data-toggle="customerPhoneNumberIDtooltip"]').tooltip({
			        placement : 'auto',
			        
			    }).tooltip('show');
		}else{
			//if 10 digits - remove error class
			div.removeClass("has-error");
			$('[data-toggle="customerPhoneNumberIDtooltip"]').tooltip('destroy')
		}
	});
	
	//
	$('#customerPhoneNumberID').keyup(function(){
		//alert("ke");
		var number = $(this).val();
		var div = $(this).closest("div");
		if(number.length == 10)
			{
			//if 10 digits - remove error class
			div.removeClass("has-error");
			$('[data-toggle="customerPhoneNumberIDtooltip"]').tooltip('destroy')
			}
	});
	
	// used to copy Initial amount to Final Amount field
	$('#initialAmount').change(function() {
		$('#finalAmount').val($(this).val());
	});

	// Disabling Discount and Coupons field on selecting radio button
	$("input[type='radio'][name=a]").click(function() {
		//if discount radio button is clicked
		if ($(this).val() == 'discountradiovalue') {
			//discount text input is enabled
			$('#DiscontSelectedID').prop('disabled', false);
			//clearning the Coupon Number input text
			$('#couponstextID').val('');
			//alert("fygvgvk");
			//coupon text input is disabled
			$("#couponstextID").prop("disabled", true);
		} 
		//if coupon radio button is clicked
		else if ($(this).val() == 'couponsradiovalue') {
			$("#couponstextID").prop("disabled", false);
			//clearning the Discount input text
			$('#DiscontSelectedID').val('');
			$("#DiscontSelectedID").prop("disabled", true);

		}
	});

	// Disabling Discount and Coupons field on clicking in text area
	if ($("#DiscontSelectedID").click()) {
		$("#couponstextID").prop("disabled", true);
		$("#DiscontSelectedID").prop("disabled", true);

	}

	// for reset button to:
	// reset discount & coupons fields if they are disabled.
	//and remove all "has-error" class from all
	//inputs in the form
	$("#resteButtonID").click(function() {
		$("#couponstextID").prop("disabled", true);
		$("#DiscontSelectedID").prop("disabled", true);
		//alert("resteButton clicked");
		$(".input-group").removeClass("has-error");
		//remove all the tooltips associated with inputs
		$(".tooltip").tooltip('destroy');
		//hiding discout & coupon panel
		$("#DiscountCheckBox").removeClass("in");
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

	//employee & owner payment submit button
	$('#empPaySubmitButtonID, #ownPaySubmitButtonID').click(function(e) {
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

