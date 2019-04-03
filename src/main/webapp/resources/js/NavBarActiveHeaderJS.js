/**
 * Used for Nav Bar to make menu activte and deactive
 */

$(function() {
		
	var path =	location.pathname.substring(location.pathname.lastIndexOf('/')+1);
	
	$(".nav").find(".active").removeClass("active");	
	 $("ul.nav.navbar-nav").find('a[href="' + path + '"]').closest('li').addClass('active');
	  
});//end of main function

