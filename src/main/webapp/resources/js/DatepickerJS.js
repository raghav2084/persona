/**
 * 
 */
$(function() {

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