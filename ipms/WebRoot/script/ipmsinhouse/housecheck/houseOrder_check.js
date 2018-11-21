
		$(function(){
			$(".order_first").click();
			$(".right_check ul li").on("click",function(){
				$(this).addClass("tab_select");
				$(this).siblings().removeClass("tab_select");
			});
			changeorder();
		});
		/*订单*/
		function changeorder(){
			document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/page/ipmshouse/housecheck/lookHousecheckDetails.jsp?houseorderid=' + houseorderid +'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		/*账单*/
		function changeorderbill(num){
			document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/page/ipmshouse/leftmenu/houserefund/house_bill.jsp?checkid='+houseorderid+'&status='+num+' " width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		/*提示
		function changepro(){
			//document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/page/ipmspage/order/prompt.jsp?checkid=' +orderId +'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/loadOrderPromptData.do?checkid='+orderId+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			countPrompt();
		}
		/*日志*/
		function changelog(){
			document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/showHouseLogData.do?checkid='+ houseorderid +'&type=check" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		//	document.getElementById("ifa").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/log.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		
		/*function countPrompt(){
			$.ajax({
		         url: path + "/countPrompt.do",
				 type: "post",
				 data : {checkid : orderId},
				 success: function(json) {
				 	if(json[0]["NUM"] != 0){
				 		$(".hot").show();
						$(".hot").html(json[0]["NUM"]);
				 	}
				 	else{
				 		$(".hot").hide();
				 	}
				 },
				 error: function(json) {}
			});
		}*/
		