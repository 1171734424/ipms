
	$(function($) {
		
		$("#contentFrame_first").click();
		
		/*点击*/
			var branchrank =  $("#branchrank").val();
			var branchtype =  $("#branchtype").val();
			var housemanager = $("#housemanager").val();
			if(branchtype=="3" && housemanager == 'housemanager'){
				$("#contentFrame_houseone").click();	
			}
			
			if(branchrank=="1"){
				$("#staffwebstatus").show();
			}
			if(branchtype=="1"){
				$("#hotelshift").show();
			}
			$(".mark").on("click",function(){
				$(this).css("display","none");
			});
			var _status=$("#statusId").html();
			if(_status == '在线'){
				$(".online").css("color","rgb(66, 242, 66)");
			}else{
				$(".online").css("color","red");
			}

			/*左侧侧边栏*/
			$(".box_left_sliderbar").on({
				mouseover:function(){
					$(".nav_span").css("display","block");
				},
				mouseout:function(){
					$(".nav_span").css("display","none");
				 }
			});
			/*主页面头部点击图片更换事件
			var _li=$(".header-toggle ul li");
			var img=$(".header-toggle ul li img");
			var image_url_origin = ["room_status.png","count.png","city.png","order.png", "icon_room.png", "workaccout.png", 
							"stop_sell.png","log.png","report.png", "statistic.png"];
			var image_url = ["room_status_two.png","count_two.png","city_two.png","order_two.png",
							"icon_room_two.png", "workaccout_two.png", "stop_sell_two.png","log_two.png",
							"report_two.png", "statistic_two.png"];
			var tempLi;
			/*$(".header-toggle ul li").on("click",function(e){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
				
				if(tempLi) {
					tempLi.find("img").attr("src", img_url + image_url_origin[tempLi.attr("index")])
				}
				$(this).find("img").attr("src", img_url + image_url[$(this).attr("index")]);
				tempLi = $(this);
			});*/
			
			
		});
		var _li=$(".header-toggle ul li");
		var img=$(".header-toggle ul li img");
		var image_url_origin = ["room_status.png","count.png","city.png","order.png", "icon_room.png", "workaccout.png", 
		                        "stop_sell.png","log.png","report.png", "statistic.png","apartinfo.png"];
		var image_url = ["room_status_two.png","count_two.png","city_two.png","order_two.png",
		                 "icon_room_two.png", "workaccout_two.png", "stop_sell_two.png","log_two.png",
		                 "report_two.png", "statistic_two.png","apartinfo_two.png"];
		var tempLi;
		function changeimage(e){
			$(e).addClass("active");
			$(e).siblings().removeClass("active");
			
			if(tempLi) {
				tempLi.find("img").attr("src", img_url + image_url_origin[tempLi.attr("index")])
			}
			$(e).find("img").attr("src", img_url + image_url[$(e).attr("index")]);
			tempLi = $(e);
		}
		/*侧边栏滑动隐藏*/
		function switchSysBar(){
			var switchPoint=$("#switchPoint");
			var left_menu=$("#left_menu");
			var icon=$(".picBox span");
			if(1 == window.status){
				window.status = 0;
				left_menu.hide();
				if(icon.attr('class')=="fa fa-chevron-left"){
					icon.removeClass("fa-chevron-left");
					icon.addClass("fa-chevron-right");
				}else{
					icon.addClass("fa-chevron-left");
					icon.removeClass("fa-chevron-right");
				}
				 left_menu.css({
					 "transition-duration": "0.3s",
					 "transition":"all 0.3s ease-in-out",
					 "transform":"translate3d(-100,0,0)",
					 "left":"-100px"
				});
				 $(".header-toggle").addClass("one");
				 $(".header_toggle_ul").addClass("two");
			}
			else{
				window.status = 1;
		        left_menu.show(500);
		        left_menu.css({
					 "transition-duration": "0.3s",
					 "transition":"all 0.3s ease-in-out",
					 "left":"0"	
				});
		        $(".header-toggle").removeClass("one");
		        $(".header_toggle_ul").removeClass("two");
			}
		}
		function houseChangeUrl(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/room_statusinhouse.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			//console.log('<iframe src="' + base_path +'/page/ipmspage/room_status/room_status.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>')
		}

		function apartmentchangeUrl(e,type){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartRoomStatus.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		function hotelchangeUrl(e,type){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelstatus.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		

		function changeUrlone(e,type){
			changeimage(e);
			if(type == '1') {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelStatistics.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			} else {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/house_forward.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			
		}
		function changeUrltwo(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/queryRoom.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function apartmentchangeUrlthree(e,house){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartmentorder.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeUrlthree(e,house){
			changeimage(e);
			if (house == '3') {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/houseorder.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			} else {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelOrder.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		}
		function changeUrlfour(e,house){
			changeimage(e);
			if (house == '3') {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/housecheck.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			} else {
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelRoomList.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		}
		function changeUrlfive(e,type){
			changeimage(e);
			if(type == "1"){
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelwork_account.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'	
			}else if(type == "2"){
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartwork_account.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'	
			}
		}	
		function changeUrlsix(e,type){
			changeimage(e);
			if(type == '1'){
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelStopHouse.do?type='+type+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}else if(type == '2'){
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartStopSellHouse.do?type='+type+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}else{
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/stopsellhouse.do?type='+type+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		}
		function changeUrlhydropower(e,type){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartHydropowerLogin.do?type='+type+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		/*
		 * 房态统计
		 */
		function changeUrleight(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/houseStatistics.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function reportForms(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelReportForms.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		/* 
		 * 民宿报表页
		 */
		function houseReportForms(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmshouse/topMenu/reportform/houseReportForms.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function apartmentchangeIntegral(e,house){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartmentintegral.do?type='+house+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function changeIntegral(e,house){
			changeimage(e);
			if(house == '1'){
				document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/hotelIntegral.do?type='+house+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
		}
		function changeIntegral_house(e,house){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/integralhouse.do?type='+house+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		function apartmentCheckIn(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/apartmentcheckin.do" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		function quertHouseOrder(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/houseorder/houseorder.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		function quertHouseCheck(e){
			changeimage(e);
			document.getElementById("st-scroll").innerHTML='<iframe src="' + base_path +'/page/ipmspage/housecheck/housecheck.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}

		/*预定入住(合并)*/
		function orderNew(){
			JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do",1179,720);
		}
	
	    function orderSerach(){
			 JDialog.openWithNoTitle("", base_path + "/hotelorderSerach.do",1280,550);
		}
	    /*直接入住*/
	   /* function checkInDirect(){
	    	JDialog.openWithNoTitle("", base_path + "/checkInDirect.do",1179,700);
	    	//JDialog.openWithNoTitle("", base_path, "/checkInDirect.do", 940,704);
	    }*/
		/*日结异常*/
	    function exceptionSearch(){
			 JDialog.openWithNoTitle("", base_path + "/exceptionSerach.do",1280,640);
		}
	    
		function goods(){
			 JDialog.openWithNoTitle("", base_path + "/goods.do",1280,780);
		}
		
		function hotelgoods(){
			 JDialog.openWithNoTitle("", base_path + "/hotelgoods.do",1280,640);
		}
		
		function apartmentGoods(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentGoods.do",1280,780);
		}
		
		function memberSearch(){
			 JDialog.openWithNoTitle("", base_path + "/hotelmemberSearch.do",1000,577);
		}
		/*备用金*/
		function pettyCash(){
			//JDialog.openWithNoTitle("", base_path + "/pettyCash.do",1088,776);
			$.ajax({
				url : base_path + "/cashCount.do",
				type : "get",
				success : function(cashmap) {
				var cashcountin = cashmap.cashin;
				var cashcountout = cashmap.cashout;
				var cardcount = cashmap.card;
				var cards = cashmap.cards;
				/*var boxname = cashmap.boxname;*/
				var boxcount = cashmap.boxcount;
				var shift = cashmap.shift;
				var shifterid = cashmap.shifterid;
				var shiftername = cashmap.shiftername;
				var branchname = cashmap.branchname;
				var shiftname = cashmap.shiftname;
				var lastshiftvalue = cashmap.lastshiftvalue;
				var logintime = cashmap.logintime;
				var shifttype = "normal"
				    JDialog.openWithNoTitle("", base_path + "/cashShift.do?cashcountin="+cashcountin+"&cashcountout="+cashcountout+"&cardcount="+cardcount+"&cards="+cards+"&boxcount="+boxcount+"&shift="+shift+"&shifterid="+shifterid+"&shiftername="+shiftername+"&branchname="+branchname+"&shiftname="+shiftname+"&lastshiftvalue="+lastshiftvalue+"&shifttype="+shifttype+"&logintime="+logintime,1288,640);
					//JDialog.openWithNoTitle("", base_path + "/page/ipmspage/leftmenu/pettycash/cashregister.jsp?cashcount="+cashcount+"&cardcount="+cardcount+"&boxname="+boxname+"&boxcount="+boxcount+"&shift="+shift+"&shifterid="+shifterid+"&shiftername="+shiftername+"&branchname="+branchname+"&shiftname="+shiftname+"&lastshiftvalue="+lastshiftvalue,1088,776);
				},
				error : function() {
				}
				
			});
			
		}
		/*申请处理*/
		function handleApply(){
			JDialog.openWithNoTitle("",base_path + "/handleApply.do",1280,780);
		}
		function apartmentHandleApply(){
			JDialog.openWithNoTitle("",base_path + "/apartmentHandleApply.do",1280,780);
		}
		function apartmentReserved(){
			 //JDialog.openWithNoTitle("", base_path + "/apartmentReserved.do",1280,780);
			JDialog.openWithNoTitle("", base_path + "/apartmentNewReserved.do",1280,825);
		}
		function apartmentCheckOut(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentCheckOut.do",1280,780);
		}
		function apartmentRent(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentRent.do",1280,780);
		}
		function apartmentReFund(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentRefund.do",1280,780);
		}
		function apartmentRoomStatus(){
			 JDialog.openWithNoTitle("", base_path + "/apartmentRoomStatus.do",1280,780);
		}
		
//		function editHouseManager(){
			
//			JDialog.open("管家信息管理", base_path + "/editHouseManager.do",630,450);
//		}

		function clickhousestaff(){
			JDialog.openWithNoTitle("", base_path + "/editHouseManagerList.do",1280,780);
		}
		function apartmentclicktaff(){
			JDialog.openWithNoTitle("", base_path + "/apartmentclicktaff.do",1280,780);
		}
		
		function repair(){
			JDialog.openWithNoTitle("",base_path+"/repair.do",1280,780);
		}
		/*处理公寓维修*/
		function apartmentRepair(){
			JDialog.openWithNoTitle("",base_path+"/apartmentRepair.do",1380,780);
		}
		
		//会员购卡升级
        function memberAddLevel(){
        	JDialog.openWithNoTitle("", base_path + "/leftmenupayUpGradeMemberLevelInPms.do",700,650);
        }
		

function updatebyhand() {
	$.ajax({
		url:base_path + "/updateBySelf.do",
		type:"post",
		dataType: "json",
		success:function(json) {
			if (json.result == 1) {
				show("上传成功!");
			}
		},
	});
}	
/*
 * @author jiangsm
 * 民宿商品售卖
 */
function goodsInHouse(){
	 JDialog.openWithNoTitle("", base_path + "/goodsInHouse.do",1280,780);
}

/*@author jiangsm
 * 民宿会员查询 
 */
function memberManage(){
	JDialog.openWithNoTitle("", base_path + "/toMemberPage.do",1280,780);
}

/*@author jiangsm
 * 民宿结账提醒
 */
function houseRefund(){
	 JDialog.openWithNoTitle("", base_path + "/houseRefund.do",1280,780);
}

/*@author jiangsm
 * 处理民宿维修
 */
function repairInHouse(){
	JDialog.openWithNoTitle("",base_path+"/repairInHouse.do",1380,780);
}

/*@author jiangsm
 * 处理民宿保洁
 */
function dealHouseClean(){
	JDialog.openWithNoTitle("",base_path+"/dealHouseClean.do",1280,780);
}

/*@author jiangsm
 * 管理员信息
 */
function housestaff(){
	JDialog.open("管理员信息", base_path + "/editHouseStaff.do",570,400);
	
}

/*@author jiangsm
 * 民宿预定
 */
function houseOrder(){
	JDialog.open("预定", base_path + "/houseOrder.do",1179,580);
}

/*@author jiangsm
 * 打卡
 */
function clockIn(){
	JDialog.open("打卡", base_path + "/clockIn.do",350,250);
}