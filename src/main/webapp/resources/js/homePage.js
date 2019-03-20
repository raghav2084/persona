/**
 * Used for Login form Validations
 */

$(function() {
	$('#LoginSubmit').click(function(e) {
		
	
		if(!validate('lg_usernameID'))
			{
				
				return false;
			}
		if(!validate("lg_passwordID"))
			{
				return false;
			}
		if(!validate("usertypeID"))
			{
			return false;
			}
		
		
		if(!validateRadio2())
			{
			return false;
			}
		
		var $btn = $(this);
	    $btn.button('loading');
		//if no validation error then submit the form
		$("form#loginFormID").submit();
		
		 setTimeout(function () {
		        $btn.button('reset');
		    }, 1000);
	});

});//end of main function

function validate(id)
{
	
	if($("#"+id).val()==null || $("#"+id).val()=="")
		{
		var div = $("#"+id).closest("div");
		div.addClass("has-error");	
		//alert("Validation Error" +$("#"+id).val() );
		return false;
		}
	else
		{
		var div = $("#"+id).closest("div");
		div.removeClass("has-error");	
		return true;
		}
		
}

//if radio button is not clicked then return false (do not proceed)
function validateRadio2() { 
	//if (!$("#" + id).is(':checked'))
	if (!$("input[type='radio']").is(':checked'))
	{
		var div = $("input[type='radio']").closest("div");
		//var div = $("input[value='"+id+"']").closest("div");
		div.addClass("has-error");
		//alert("it's  ssdsd not checked");
		return false
	}else {
		var div = $("input[type='radio']").closest("div");
		div.removeClass("has-error");
		return true;
	} 
	
}
