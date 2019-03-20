/**
 * Used for Nav Bar to make menu activte and deactive
 */

$(function() {
	
	
	//method to make the select nav bar menu active (highlighted)
	//$(".nav a").on("click", function(){
		  // $(".nav").find(".active").removeClass("active");
		  // $(this).parent().addClass("active");
	 //alert("location.pathname: "+location.pathname);
		//alert("location.pathname: "+location.pathname.substring(location.pathname.lastIndexOf('/')+1));
	//	});
		
	var path =	location.pathname.substring(location.pathname.lastIndexOf('/')+1);
	
	$(".nav").find(".active").removeClass("active");	
	 $("ul.nav.navbar-nav").find('a[href="' + path + '"]').closest('li').addClass('active');
	 // alert("location.pathname: "+location.pathname.substring(11));
	  //alert("Context Root" + window.location.pathname.substring(0, window.location.pathname.indexOf("/",4)));

	  
});//end of main function

