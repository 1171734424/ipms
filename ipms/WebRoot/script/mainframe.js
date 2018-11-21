$(document).ready(function() {
	$(function($) {
		$(".user").on({
			mouseover:function(){
			$(this).addClass("active");
			$(".user_panel").css("display","block");
		   },
		   mouseout:function(){
			$(this).remove("active");
			$(".user_panel").css("display","none");
		 }});
		
		$(".notice").on("click",function(){
			if($(".dropdown-menu").css("display")=="none"){
				$(".dropdown-menu").css("display","block");
			}else{
				$(".dropdown-menu").css("display","none");
			}
		})
	});
	
});


