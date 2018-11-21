			/*客单*/
			function changeone(){
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/customer.jsp?checkid=' + checkid +'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				//console.log('<iframe src="' + base_path +'/page/ipmspage/room_statistics/customer.jsp?checkid=' + checkid +'"')
			}
			/*账单*/
			function changetwo(){
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/roomlist_bill.jsp?checkid=' + checkid + '&pagea=' + pagea + '" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			/*房租计划*/
			function changethree(){
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/chummageplan.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			/*提示*/
			function changefour(){
				countPrompt();
				//document.getElementById("c").innerHTML='<iframe src="' + base_path +'/page/ipmspage/order/prompt.jsp?checkid='+checkid+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/loadOrderPromptData.do?checkid='+checkid+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			/*日志*/
			function changefive(){
				//document.getElementById("d").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/log.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				//document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/showLogData.do?type=check&checkid='+checkid+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/showLogData.do?checkid='+ checkid +'&type=check" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
				//console.log('<iframe src="' + base_path +'/page/ipmspage/room_statistics/log.jsp?checkid='+ checkid +'&type=check" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>')
			}
			/*财务计划*/
			function changesix(){
				document.getElementById("customer").innerHTML='<iframe src="' + base_path +'/page/ipmspage/room_statistics/finance.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			$(".right_check ul li").on("click",function(){
				$(this).addClass("tab_select");
				$(this).siblings().removeClass("tab_select");
				$("#customer").css("display","block");
				$(".roomdata").css("display","none");
			});
			$(".roomdata_five").on("click",function(){
				$(this).addClass("active");
			})