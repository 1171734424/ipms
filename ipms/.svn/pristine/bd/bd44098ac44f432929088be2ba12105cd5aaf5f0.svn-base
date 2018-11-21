$(document).ready(function() {
	querymyroomstatus();
});

function querymyroomstatus() {
	$.ajax({
		url:base_path + "/hotelqueryRoomList.do",
		type:"post",
		dataType: "json",
		success:function(json) {
			if(json) {
				if (json.state == 1) {
					$(".hotel").show();
					$(".room_list").html(" ");
					for (i=0;i<json.roomlist.length;i++) {
						if (json.roomlist[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none;'>" + json.roomlist[i].ROOMTYPE + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '3') {
							$("<li class='live'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						}
					}
					$(".totalcount").text(json.totalcount[0].TOTAL);
					$(".livecount").html(json.totallive[0].LIVE);
					/*鼠标点击任意浏览器区域菜单消失*/
					$(document).click(function() {
						$(".dirtyroom").hide();
						$(".cleanroom").hide();
						$(".stoproom").hide();
						$(".vzroom").hide();
						$(".housevzroom").hide();
					});
					$("li").mousedown(function(e) {
						//鼠标点击菜单选项添加事件
						$(".setclean").val($(this).find(".roomId").text());
						// 屏蔽浏览器自带的右键
						$(document).bind("contextmenu",
						    function(){
						        return false;
						    }
						);
						if (e.which == 3) {
						    var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
					        if ($(this).children("span:last-child").html() == '1' ) {
					        	$(".dirtyroom").hide();
						        $(".cleanroom").show();
						        $(".stoproom").hide();
						        $(".vzroom").hide();
						        $("#cleanroomid").val($(this).children("span:first-child").html());
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#vzroom").val($(this).children("span:first-child").html());
						        $(".cleanroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == '3') {
					        	$(".cleanroom").hide();    
						        $(".dirtyroom").show();
						        $(".vzroom").hide();
						        $(".stoproom").hide();
						       // alert($(this).children("span:first-child").html());
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#cleanroomid").val($(this).children("span:first-child").html());
								$("#vzroom").val($(this).children("span:first-child").html());
						        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".dirtyroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == 'T') {
								$(".stoproom").show();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$(".vzroom").hide();
								$("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".stoproom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == 'Z') {
					    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
					    	    	$(".setcleanlive").show();
					    	    	$(".setcleanroom").hide();
					    	    	$(".setcleanstop").hide();
					    	    	$(".setcleanlist").show();
					    	    	$(".setcleanbill").show();
					    	    	$(".setcleanplan").show();
					    	    	$(".setcleanhint").show();
					    	    } else {
					    	    	$(".setcleanlive").hide();
					    	    	$(".setcleanroom").show();
					    	    	$(".setcleanstop").show();
					    	    	$(".setcleanlist").hide();
					    	    	$(".setcleanbill").hide();
					    	    	$(".setcleanplan").hide();
					    	    	$(".setcleanhint").hide();
					    	    }
								$(".vzroom").show();
								$(".stoproom").hide();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$("#vzroom").val($(this).children("span:first-child").html());
								$("#cleanroomid").val($(this).children("span:first-child").html());
								$("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#thecheckid").val($(this).children("span:nth-child(2)").html());
								$("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".vzroom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
					       }
						}
					})
					
					$(".room_list li").dblclick(function(e) {
						if ($(this).children("span:last-child").html() == '1') {
							var roomId = $(this).children("span:first-child").html();
							var roomType = $(this).children("span:nth-child(2)").html();
							JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1179,780);
						} else if ($(this).children("span:last-child").html() == '3') {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.parent.parent.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
						} else if ($(this).children("span:last-child").html() == 'W') {
							var roomId = $(this).children("span:first-child").html();
							var logid;
							$.ajax({
								url: base_path + "/hotelgetLogIdByRoom.do",
								type: "post",
								dataType: "json",
								data: {roomId: roomId},
								success: function (json) {
									logid = json.data.logId;
									window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid, 882, 512);
								},
								error: function (json) {
									
								}
							})
							return false;
							//showMsg("此房为维修房!");
						} else if ($(this).children("span:last-child").html() == 'T') {
							var roomId = $(this).children("span:first-child").html();
							var logid;
							$.ajax({
								url: base_path + "/hotelgetLogIdByRoom.do",
								type: "post",
								dataType: "json",
								data: {roomId: roomId},
								success: function (json) {
									logid = json.data.logId;
									window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
								},
								error: function (json) {
									
								}
							})
							return false;
							//showMsg("此房为停售房!");
						} else if ($(this).children("span:last-child").html() == 'Z') {
							if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
								var checknumber = $(this).children("span:nth-child(2)").html()
								var which = 'check';
								window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
							} else {
								showMsg("此房为脏房!");
							}
						}
					}); 
				} else if (json.state == 2) {
					$(".hotel").hide();
					$(".apartment").show();
					$(".room_list").html(" ");
					for (i=0;i<json.apartment.length;i++) {
						if (json.apartment[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.apartment[i].RMID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].TYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "预定" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '3') {
							if ($.trim(json.apartment[i].CONTID).length > 0 && $.trim(json.apartment[i].CONTID).length != null) {
								$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + json.apartment[i].TYPE + "</span>" +
										"<img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + json.apartment[i].TYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.apartment[i].STATUS == 'M') {
							$("<li class='model'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "样板房" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'E') {
							$("<li class='employee'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "员工宿舍" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'W') {
							if ($.trim(json.apartment[i].CONTID).length > 0 && $.trim(json.apartment[i].CONTID).length != null) {
								$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + "维修房" + "</span>" +
										"<img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + "维修房" + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.apartment[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "其他" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "停售房" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} /*else if (json.apartment[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "脏房" + "</span>").appendTo(".room_list");
						} */
					}
					mymousedown(2);
					bindDbclick(2);
					$.ajax({
						type : "POST",
						url:base_path + "/hotelroomStatusCount.do",
						dataType : "json",
						success : function(data) {
							for(var i=0;i<data.roomCountList.length;i++){
								$("#checkin_count").html(data.roomCountList[i].CHECKINCOUNT);
								$("#reserve_count").html(data.roomCountList[i].RESERVECOUNT);
								$("#null_count").html(data.roomCountList[i].NULLCOUNT);
								$("#demoRoom_count").html(data.roomCountList[i].DEMOCOUNT);
								$("#staffRoom_count").html(data.roomCountList[i].STAFFCOUNT);
								$("#other_count").html(data.roomCountList[i].OTHERCOUNT);
								$("#stopsale_count").html(data.roomCountList[i].STOPCOUNT);
								$("#repair_count").html(data.roomCountList[i].REPAIRCOUNT);
								$("#total_count").html(data.roomCountList[i].TOTALCOUNT);
								$("#deal_count").html(data.roomCountList[i].DEALCOUNT);
								$("#checkin_rate").html((data.roomCountList[i].CHECKINRATE*100).toFixed(1)+"%");
							}
						},
						error : function() {
							showMsg("查询失败");
							return false;
							}
					 });
				} else if (json.state == 3) {
					$(".house").show();
					$(".room_list").html(" ");
					for (i=0;i<json.houselist.length;i++) {
						if (json.houselist[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == '3') {
							if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
								$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span>" +
										"<img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.houselist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == 'W') {
							if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
								$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.houselist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} //else if (json.apartment[i].STATUS == 'T') {
							//$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "停售房" + "</span>").appendTo(".room_list");
						//} else if (json.apartment[i].STATUS == 'Z') {
						//	$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "脏房" + "</span>").appendTo(".room_list");
						//} 
					}
					$(".housetotal").text(json.totalcount[0].TOTAL);
					$(".houselive").html(json.totallive[0].LIVE);
					mymousedown(3);	
					bindDbclick(3);
				}
			} else {
				$(".room_list").html(" ");
			} 
		}
	});	
	getRoomFloor();
	getRoomType();
}


				

function queryalllist() {
	$("#roomId").val("");
	//querymyroomstatus();
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	
	$.ajax({
		url:base_path + "/hotelqueryRoomList.do",
		type:"post",
		dataType: "json",
		data: { roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json) {
				if (json.state == 1) {
					$(".hotel").show();
					$(".room_list").html(" ");
					for (i=0;i<json.roomlist.length;i++) {
						if (json.roomlist[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none;'>" + json.roomlist[i].ROOMTYPE + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == '3') {
							$("<li class='live'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'W') {
							$("<li class='maintance'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
									+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.roomlist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
						}
					}
					$(".totalcount").text(json.totalcount[0].TOTAL);
					$(".livecount").html(json.totallive[0].LIVE);
					/*鼠标点击任意浏览器区域菜单消失*/
					$(document).click(function() {
						$(".dirtyroom").hide();
						$(".cleanroom").hide();
						$(".stoproom").hide();
						$(".vzroom").hide();
						$(".housevzroom").hide();
					});
					$("li").mousedown(function(e) {
						//鼠标点击菜单选项添加事件
						$(".setclean").val($(this).find(".roomId").text());
						// 屏蔽浏览器自带的右键
						$(document).bind("contextmenu",
						    function(){
						        return false;
						    }
						);
						if (e.which == 3) {
						    var mousePos = mousePosition(e);  
					        var  xOffset = 20;  
					        var  yOffset = 25; 
					        if ($(this).children("span:last-child").html() == '1' ) {
					        	$(".dirtyroom").hide();
						        $(".cleanroom").show();
						        $(".stoproom").hide();
						        $(".vzroom").hide();
						        $("#cleanroomid").val($(this).children("span:first-child").html());
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#vzroom").val($(this).children("span:first-child").html());
						        $(".cleanroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == '3') {
					        	$(".cleanroom").hide();    
						        $(".dirtyroom").show();
						        $(".vzroom").hide();
						        $(".stoproom").hide();
						       // alert($(this).children("span:first-child").html());
						        $("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#cleanroomid").val($(this).children("span:first-child").html());
								$("#vzroom").val($(this).children("span:first-child").html());
						        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".dirtyroom").css("display","block").css("position","absolute")
						        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == 'T') {
								$(".stoproom").show();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$(".vzroom").hide();
								$("#cleanroomid").val($(this).children("span:first-child").html());
						        $(".stoproom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
					       } else if ($(this).children("span:last-child").html() == 'Z') {
					    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
					    	    	$(".setcleanlive").show();
					    	    	$(".setcleanroom").hide();
					    	    	$(".setcleanstop").hide();
					    	    	$(".setcleanlist").show();
					    	    	$(".setcleanbill").show();
					    	    	$(".setcleanplan").show();
					    	    	$(".setcleanhint").show();
					    	    } else {
					    	    	$(".setcleanlive").hide();
					    	    	$(".setcleanroom").show();
					    	    	$(".setcleanstop").show();
					    	    	$(".setcleanlist").hide();
					    	    	$(".setcleanbill").hide();
					    	    	$(".setcleanplan").hide();
					    	    	$(".setcleanhint").hide();
					    	    }
								$(".vzroom").show();
								$(".stoproom").hide();
								$(".cleanroom").hide(); 
								$(".dirtyroom").hide();
								$("#vzroom").val($(this).children("span:first-child").html());
								$("#cleanroomid").val($(this).children("span:first-child").html());
								$("#dirtyroomid").val($(this).children("span:first-child").html());
								$("#thecheckid").val($(this).children("span:nth-child(2)").html());
								$("#thischeckid").val($(this).children("span:nth-child(2)").html());
						        $(".vzroom").css("display","block").css("position","absolute")
				        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
					       }
						}
					})
					
					$(".room_list li").dblclick(function(e) {
						if ($(this).children("span:last-child").html() == '1') {
							var roomId = $(this).children("span:first-child").html();
							var roomType = $(this).children("span:nth-child(2)").html();
							JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1280,780);
						} else if ($(this).children("span:last-child").html() == '3') {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
						} else if ($(this).children("span:last-child").html() == 'W') {
							var roomId = $(this).children("span:first-child").html();
							var logid;
							$.ajax({
								url: base_path + "/hotelgetLogIdByRoom.do",
								type: "post",
								dataType: "json",
								data: {roomId: roomId},
								success: function (json) {
									logid = json.data.logId;
									window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
								},
								error: function (json) {
									
								}
							})
							return false;
							//showMsg("此房为维修房!");
						} else if ($(this).children("span:last-child").html() == 'T') {
							var roomId = $(this).children("span:first-child").html();
							var logid;
							$.ajax({
								url: base_path + "/hotelgetLogIdByRoom.do",
								type: "post",
								dataType: "json",
								data: {roomId: roomId},
								success: function (json) {
									logid = json.data.logId;
									window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
								},
								error: function (json) {
									
								}
							})
							return false;
							//showMsg("此房为停售房!");
						} else if ($(this).children("span:last-child").html() == 'Z') {
							if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
								var checknumber = $(this).children("span:nth-child(2)").html()
								var which = 'check';
								window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
							} else {
								showMsg("此房为脏房!");
							}
						}
					}); 
				} else if (json.state == 2) {
					$(".hotel").hide();
					$(".apartment").show();
					$(".room_list").html(" ");
					for (i=0;i<json.apartment.length;i++) {
						if (json.apartment[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.apartment[i].RMID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].TYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "预定" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == '3') {
							if ($.trim(json.apartment[i].CONTID).length > 0 && $.trim(json.apartment[i].CONTID).length != null) {
								$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + json.apartment[i].TYPE + "</span>" +
										"<img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='live'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + json.apartment[i].TYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.apartment[i].STATUS == 'M') {
							$("<li class='model'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "样板房" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'E') {
							$("<li class='employee'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "员工宿舍" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'W') {
							if ($.trim(json.apartment[i].CONTID).length > 0 && $.trim(json.apartment[i].CONTID).length != null) {
								$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + "维修房" + "</span>" +
										"<img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='maintance'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].CONTID + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span>" + json.apartment[i].CHEUSER + "</span><span>" + "维修房" + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.apartment[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "其他" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.apartment[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span>" + "停售房" + "</span><span class='roomType' style='display:none'>" + json.apartment[i].RMTYPE + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span>").appendTo(".room_list");
						} /*else if (json.apartment[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "脏房" + "</span>").appendTo(".room_list");
						} */
					}
					mymousedown(2);
					bindDbclick(2);
					$.ajax({
						type : "POST",
						url:base_path + "/hotelroomStatusCount.do",
						dataType : "json",
						success : function(data) {
							for(var i=0;i<data.roomCountList.length;i++){
								$("#checkin_count").html(data.roomCountList[i].CHECKINCOUNT);
								$("#reserve_count").html(data.roomCountList[i].RESERVECOUNT);
								$("#null_count").html(data.roomCountList[i].NULLCOUNT);
								$("#demoRoom_count").html(data.roomCountList[i].DEMOCOUNT);
								$("#staffRoom_count").html(data.roomCountList[i].STAFFCOUNT);
								$("#other_count").html(data.roomCountList[i].OTHERCOUNT);
								$("#stopsale_count").html(data.roomCountList[i].STOPCOUNT);
								$("#repair_count").html(data.roomCountList[i].REPAIRCOUNT);
								$("#total_count").html(data.roomCountList[i].TOTALCOUNT);
								$("#deal_count").html(data.roomCountList[i].DEALCOUNT);
								$("#checkin_rate").html((data.roomCountList[i].CHECKINRATE*100).toFixed(1)+"%");
							}
						},
						error : function() {
							showMsg("查询失败");
							return false;
							}
					 });
				} else if (json.state == 3) {
					$(".house").show();
					$(".room_list").html(" ");
					for (i=0;i<json.houselist.length;i++) {
						if (json.houselist[i].STATUS == '1') {
							$("<li><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == '2') {
							$("<li class='order'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else if (json.houselist[i].STATUS == '3') {
							if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
								$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.houselist[i].STATUS == 'T') {
							$("<li class='stopsell'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} /*else if (json.houselist[i].STATUS == 'Z') {
							$("<li class='dirty'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
						}*/ else if (json.houselist[i].STATUS == 'W') {
							if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
								$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							} else {
								$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
							}
						} else if (json.houselist[i].STATUS == 'O') {
							$("<li class='other'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} //else if (json.apartment[i].STATUS == 'T') {
							//$("<li class='stopsell'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "停售房" + "</span>").appendTo(".room_list");
						//} else if (json.apartment[i].STATUS == 'Z') {
						//	$("<li class='dirty'><span class='roomId'>" + json.apartment[i].RMID + "</span><span style='display:none'>" + json.apartment[i].STATUS + "</span><span>" + "脏房" + "</span>").appendTo(".room_list");
						//} 
					}
					$(".housetotal").text(json.totalcount[0].TOTAL);
					$(".houselive").html(json.totallive[0].LIVE);
					mymousedown(3);	
					bindDbclick(3);
				}
			} else {
				$(".room_list").html(" ");
			} 
		}
	});		
}


function queryarrival(type) {
	$("#houseName").val("");
	$.ajax({
		url:base_path + "/hotelqueryArrival.do",
		type:"post",
		dataType:"json",
		data:{type: type},
		success:function(json) {
			if(json) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '1') {
						$("<li><span>"+ json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" 
								+json[i].STATUS + "</span>").appendTo(".room_list");
					}  else if (json[i].STATUS == '2') {
						$("<li class='order'><span>"+ json[i].HOUSE_ID + "</span><span style='display:none'>" + json[i].ORDID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" 
								+json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '3') {
						$("<li class='live'><span>"+ json[i].HOUSE_ID + "</span><span style='display:none'>" + json[i].CHECK_ID + "</span><span>" + json[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" 
								+json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json[i].HOUSE_ID + "</span><span style='display:none'>" + json[i].CHECK_ID + "</span><span>" + json[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>"
								+json[i].STATUS + "</span>").appendTo(".room_list");
					}
					
					/*else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
								+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span>" 
								+json[i].STATUS + "</span>").appendTo(".room_list");
					}  /*else if (json[i].STATUS == 'O') {
						$("<li class='other'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMTYPE + "</span><span>" 
							+json[i].CHECKUSER + "</span>").appendTo(".room_list");
					}*/
				}
				mymousedown(3);
				bindDbclick(3);
			} else {
				$(".room_list").html(" ");
			} 
		}
	})
}

function queryleavelive(status){
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	$.ajax({
		url:base_path + "/hotelqueryLeavelive.do",
		type:"post",
		dataType:"json",
		data: { status: status, roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '3') {
						$("<li class='live'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					}
					
				}
				$(document).click(function() {
					$(".dirtyroom").hide();
					$(".cleanroom").hide();
					$(".stoproom").hide();
					$(".vzroom").hide();
					$(".housevzroom").hide();
				});
				$("li").mousedown(function(e) {
					//鼠标点击菜单选项添加事件
					$(".setclean").val($(this).find(".roomId").text());
					// 屏蔽浏览器自带的右键
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					if (e.which == 3) {
					    var mousePos = mousePosition(e);  
				        var  xOffset = 20;  
				        var  yOffset = 25; 
				        if ($(this).children("span:last-child").html() == '3') {
				        	$(".cleanroom").hide();    
					        $(".dirtyroom").show();
					        $(".vzroom").hide();
					        $(".stoproom").hide();
					       // alert($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".dirtyroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'Z') {
				    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
				    	    	$(".setcleanlive").show();
				    	    	$(".setcleanroom").hide();
				    	    	$(".setcleanstop").hide();
				    	    	$(".setcleanlist").show();
				    	    	$(".setcleanbill").show();
				    	    	$(".setcleanplan").show();
				    	    	$(".setcleanhint").show();
				    	    } else {
				    	    	$(".setcleanlive").hide();
				    	    	$(".setcleanroom").show();
				    	    	$(".setcleanstop").show();
				    	    	$(".setcleanlist").hide();
				    	    	$(".setcleanbill").hide();
				    	    	$(".setcleanplan").hide();
				    	    	$(".setcleanhint").hide();
				    	    }
							$(".vzroom").show();
							$(".stoproom").hide();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$("#vzroom").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#thecheckid").val($(this).children("span:nth-child(2)").html());
							$("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".vzroom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
				       }
					}
				})
				
				$(".room_list li").dblclick(function() {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
				});
			} else {
				$(".room_list").html(" ");
			}  
		}
	})
}

function queryroom(status) {
	var roomtype = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	
	$.ajax({
		url:base_path + "/hotelqueryRoomByStatus.do",
		type:"post",
		dataType:"json",
		data: { status: status, roomtype:roomtype, roomfloor:roomfloor },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '1') {
						$("<li><span>" + json[i].ROOMID + "</span><span style='display:none;'>" + json[i].ROOMTYPE+ "</span><span>" + json[i].ROOMNAME+ "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" + json[i].CHECKUSER + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} 
				}
				$(document).click(function() {
					$(".dirtyroom").hide();
					$(".cleanroom").hide();
					$(".stoproom").hide();
					$(".vzroom").hide();
					$(".housevzroom").hide();
				});
				$("li").mousedown(function(e) {
					//鼠标点击菜单选项添加事件
					$(".setclean").val($(this).find(".roomId").text());
					// 屏蔽浏览器自带的右键
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					if (e.which == 3) {
					    var mousePos = mousePosition(e);  
				        var  xOffset = 20;  
				        var  yOffset = 25; 
				        if ($(this).children("span:last-child").html() == '1' ) {
				        	$(".dirtyroom").hide();
					        $(".cleanroom").show();
					        $(".stoproom").hide();
					        $(".vzroom").hide();
					        $("#cleanroomid").val($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $(".cleanroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == '3') {
				        	$(".cleanroom").hide();    
					        $(".dirtyroom").show();
					        $(".vzroom").hide();
					        $(".stoproom").hide();
					       // alert($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".dirtyroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'T') {
							$(".stoproom").show();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$(".vzroom").hide();
							$("#cleanroomid").val($(this).children("span:first-child").html());
					        $(".stoproom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'Z') {
				    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
				    	    	$(".setcleanlive").show();
				    	    	$(".setcleanroom").hide();
				    	    	$(".setcleanstop").hide();
				    	    	$(".setcleanlist").show();
				    	    	$(".setcleanbill").show();
				    	    	$(".setcleanplan").show();
				    	    	$(".setcleanhint").show();
				    	    } else {
				    	    	$(".setcleanlive").hide();
				    	    	$(".setcleanroom").show();
				    	    	$(".setcleanstop").show();
				    	    	$(".setcleanlist").hide();
				    	    	$(".setcleanbill").hide();
				    	    	$(".setcleanplan").hide();
				    	    	$(".setcleanhint").hide();
				    	    }
							$(".vzroom").show();
							$(".stoproom").hide();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$("#vzroom").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#thecheckid").val($(this).children("span:nth-child(2)").html());
							$("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".vzroom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
				       }
					}
				})
				
				$(".room_list li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '1') {
						var roomId = $(this).children("span:first-child").html();
						var roomType = $(this).children("span:nth-child(2)").html();
						JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1179,780);
					} else if ($(this).children("span:last-child").html() == 'Z') {
						if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
						} else {
							showMsg("此房为脏房!");
						}
					} else if ($(this).children("span:last-child").html() == 'T') {
						var roomId = $(this).children("span:first-child").html();
						var logid;
						$.ajax({
							url: base_path + "/hotelgetLogIdByRoom.do",
							type: "post",
							dataType: "json",
							data: {roomId: roomId},
							success: function (json) {
								logid = json.data.logId;
								window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid ,882,512);
							},
							error: function (json) {
								
							}
						})
						return false;
					}
				});
			} else {
				$(".room_list").html(" ");
			} 
		}
	})
}

$("#dropse_roomtype").change(function(){
	var roomType = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	var label = $(".hotel_roomstatus").find(".active").find("span").text();
	
	$.ajax({
		url:base_path + "/hotelqueryByRoomType.do",
		type:"post",
		dataType:"json",
		data: { roomType: roomType, roomfloor:roomfloor, label:label },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '1') {
						$("<li><span>"+ json[i].ROOMID + "</span><span style='display:none'>" + json[i].ROOMTYPE +"</span><span>" + json[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '2') {
						$("<li class='order'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == '3') {
						$("<li class='live'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" 
								+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span>" 
								+json[i].CHECKUSER + "</span>" + "<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'O') {
						$("<li class='other'><span>" + json[i].ROOMID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].ROOMNAME + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					}
				}
			
			
				$(document).click(function() {
					$(".dirtyroom").hide();
					$(".cleanroom").hide();
					$(".stoproom").hide();
					$(".vzroom").hide();
					$(".housevzroom").hide();
				});
				$("li").mousedown(function(e) {
					//鼠标点击菜单选项添加事件
					$(".setclean").val($(this).find(".roomId").text());
					// 屏蔽浏览器自带的右键
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					if (e.which == 3) {
					    var mousePos = mousePosition(e);  
				        var  xOffset = 20;  
				        var  yOffset = 25; 
				        if ($(this).children("span:last-child").html() == '1' ) {
				        	$(".dirtyroom").hide();
					        $(".cleanroom").show();
					        $(".stoproom").hide();
					        $(".vzroom").hide();
					        $("#cleanroomid").val($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $(".cleanroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == '3') {
				        	$(".cleanroom").hide();    
					        $(".dirtyroom").show();
					        $(".vzroom").hide();
					        $(".stoproom").hide();
					       // alert($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".dirtyroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'T') {
							$(".stoproom").show();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$(".vzroom").hide();
							$("#cleanroomid").val($(this).children("span:first-child").html());
					        $(".stoproom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'Z') {
				    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
				    	    	$(".setcleanlive").show();
				    	    	$(".setcleanroom").hide();
				    	    	$(".setcleanstop").hide();
				    	    	$(".setcleanlist").show();
				    	    	$(".setcleanbill").show();
				    	    	$(".setcleanplan").show();
				    	    	$(".setcleanhint").show();
				    	    } else {
				    	    	$(".setcleanlive").hide();
				    	    	$(".setcleanroom").show();
				    	    	$(".setcleanstop").show();
				    	    	$(".setcleanlist").hide();
				    	    	$(".setcleanbill").hide();
				    	    	$(".setcleanplan").hide();
				    	    	$(".setcleanhint").hide();
				    	    }
							$(".vzroom").show();
							$(".stoproom").hide();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$("#vzroom").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#thecheckid").val($(this).children("span:nth-child(2)").html());
							$("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".vzroom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
				       }
					}
				})
				
				$(".room_list li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '1') {
						var roomId = $(this).children("span:first-child").html();
						var roomType = $(this).children("span:nth-child(2)").html();
						JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1179,780);
					} else if ($(this).children("span:last-child").html() == '3') {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
					} else if ($(this).children("span:last-child").html() == 'W') {
						var roomId = $(this).children("span:first-child").html();
						var logid;
						$.ajax({
							url: base_path + "/hotelgetLogIdByRoom.do",
							type: "post",
							dataType: "json",
							data: {roomId: roomId},
							success: function (json) {
								logid = json.data.logId;
								window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
							},
							error: function (json) {
								
							}
						})
						return false;
						//showMsg("此房为维修房!");
					} else if ($(this).children("span:last-child").html() == 'T') {
						var roomId = $(this).children("span:first-child").html();
						var logid;
						$.ajax({
							url: base_path + "/hotelgetLogIdByRoom.do",
							type: "post",
							dataType: "json",
							data: {roomId: roomId},
							success: function (json) {
								logid = json.data.logId;
								window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
							},
							error: function (json) {
								
							}
						})
						return false;
						//showMsg("此房为停售房!");
					} else if ($(this).children("span:last-child").html() == 'Z') {
						if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
						} else {
							showMsg("此房为脏房!");
						}
					}
				}); 
			} else {
				$(".room_list").html(" ");
			} 
		}
	})
})

$("#dropse_floor").change(function(){
	var roomType = $("#dropse_roomtype").val();
	var roomfloor = $("#dropse_floor").val();
	var label = $(".hotel_roomstatus").find(".active").find("span").text();
	
/*	if (label != "空净房" || label != "脏房" || label != "停售房" || label != "所有" || label != "维修房") {
		label = $(".roomstatus").find(".active").find("span").text();
	}*/
	$.ajax({
		url:base_path + "/hotelqueryByRoomFloor.do",
		type:"post",
		dataType:"json",
		data: { roomFloor: roomfloor, roomtype:roomType, label:label },
		success:function(json) {
			if(json.roomlist.length) {
				$(".room_list").html(" ");
				for (i=0;i<json.roomlist.length;i++) {
					if (json.roomlist[i].STATUS == '1') {
						$("<li><span>" + json.roomlist[i].ROOMID + "</span><span style='display:none;'>" + json.roomlist[i].ROOMTYPE + "</span><span>" + json.roomlist[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == '2') {
						$("<li class='order'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == '3') {
						$("<li class='live'><span>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span><span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json.roomlist[i].ROOMID + "</span><span style='display:none'>" + json.roomlist[i].CHEID +"</span><span>" + json.roomlist[i].ROOMNAME + "</span><span>" 
								+json.roomlist[i].CHECKUSER + "</span>" + "<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'W') {
						$("<li class='maintance'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json.roomlist[i].STATUS == 'O') {
						$("<li class='other'><span>" + json.roomlist[i].ROOMID + "</span><span>" + json.roomlist[i].ROOMNAME + "</span>" +
								"<span style='display:none;'>" + json.roomlist[i].STATUS + "</span>").appendTo(".room_list");
					}
				}
				$(".nine").text(json.roomlist.length);
			
				/*鼠标点击任意浏览器区域菜单消失*/
				$(document).click(function() {
					$(".dirtyroom").hide();
					$(".cleanroom").hide();
					$(".stoproom").hide();
					$(".vzroom").hide();
					$(".housevzroom").hide();
				});
				$("li").mousedown(function(e) {
					//鼠标点击菜单选项添加事件
					$(".setclean").val($(this).find(".roomId").text());
					// 屏蔽浏览器自带的右键
					$(document).bind("contextmenu",
					    function(){
					        return false;
					    }
					);
					if (e.which == 3) {
					    var mousePos = mousePosition(e);  
				        var  xOffset = 20;  
				        var  yOffset = 25; 
				        if ($(this).children("span:last-child").html() == '1' ) {
				        	$(".dirtyroom").hide();
					        $(".cleanroom").show();
					        $(".stoproom").hide();
					        $(".vzroom").hide();
					        $("#cleanroomid").val($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $(".cleanroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == '3') {
				        	$(".cleanroom").hide();    
					        $(".dirtyroom").show();
					        $(".vzroom").hide();
					        $(".stoproom").hide();
					       // alert($(this).children("span:first-child").html());
					        $("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#vzroom").val($(this).children("span:first-child").html());
					        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".dirtyroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'T') {
							$(".stoproom").show();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$(".vzroom").hide();
							$("#cleanroomid").val($(this).children("span:first-child").html());
					        $(".stoproom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
				       } else if ($(this).children("span:last-child").html() == 'Z') {
				    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
				    	    	$(".setcleanlive").show();
				    	    	$(".setcleanroom").hide();
				    	    	$(".setcleanstop").hide();
				    	    	$(".setcleanlist").show();
				    	    	$(".setcleanbill").show();
				    	    	$(".setcleanplan").show();
				    	    	$(".setcleanhint").show();
				    	    } else {
				    	    	$(".setcleanlive").hide();
				    	    	$(".setcleanroom").show();
				    	    	$(".setcleanstop").show();
				    	    	$(".setcleanlist").hide();
				    	    	$(".setcleanbill").hide();
				    	    	$(".setcleanplan").hide();
				    	    	$(".setcleanhint").hide();
				    	    }
							$(".vzroom").show();
							$(".stoproom").hide();
							$(".cleanroom").hide(); 
							$(".dirtyroom").hide();
							$("#vzroom").val($(this).children("span:first-child").html());
							$("#cleanroomid").val($(this).children("span:first-child").html());
							$("#dirtyroomid").val($(this).children("span:first-child").html());
							$("#thecheckid").val($(this).children("span:nth-child(2)").html());
							$("#thischeckid").val($(this).children("span:nth-child(2)").html());
					        $(".vzroom").css("display","block").css("position","absolute")
			        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
				       }
					}
				})
				
				$(".room_list li").dblclick(function(e) {
					if ($(this).children("span:last-child").html() == '1') {
						var roomId = $(this).children("span:first-child").html();
						var roomType = $(this).children("span:nth-child(2)").html();
						JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1179,780);
					} else if ($(this).children("span:last-child").html() == '3') {
						var checknumber = $(this).children("span:nth-child(2)").html()
						var which = 'check';
						window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
					} else if ($(this).children("span:last-child").html() == 'W') {
						var roomId = $(this).children("span:first-child").html();
						var logid;
						$.ajax({
							url: base_path + "/hotelgetLogIdByRoom.do",
							type: "post",
							dataType: "json",
							data: {roomId: roomId},
							success: function (json) {
								logid = json.data.logId;
								window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
							},
							error: function (json) {
								
							}
						})
						return false;
						//showMsg("此房为维修房!");
					} else if ($(this).children("span:last-child").html() == 'T') {
						var roomId = $(this).children("span:first-child").html();
						var logid;
						$.ajax({
							url: base_path + "/hotelgetLogIdByRoom.do",
							type: "post",
							dataType: "json",
							data: {roomId: roomId},
							success: function (json) {
								logid = json.data.logId;
								window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
							},
							error: function (json) {
								
							}
						})
						return false;
						//showMsg("此房为停售房!");
					} else if ($(this).children("span:last-child").html() == 'Z') {
						if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
							var checknumber = $(this).children("span:nth-child(2)").html()
							var which = 'check';
							window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
						} else {
							showMsg("此房为脏房!");
						}
					}
				}); 
			} else {
				$(".room_list").html(" ");
			}  	
		}
	})
})


if($("#roomId").focus){
	$("#roomId").keydown(function(e){
		 var key = e.which;
         if (key == 13) {
        	e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        	$(".hotel_roomstatus").children("li:first-child").siblings().removeClass("active");
        	$(".hotel_roomstatus").children("li:first-child").addClass("active");
        	getRoomFloor();
        	getRoomType();
        	
			$.ajax({
				url:base_path + "/hotelqueryByRoomId.do",
				type:"post",
				dataType:"json",
				data: { roomId: $("#roomId").val() },
				success:function(json) {
					if(json.length > 0) {
						$(".room_list").html(" ");
						for (i=0;i<json.length;i++) {
							if (json[i].STATUS == '1') {
								$("<li><span>"+ json[i].ROOMID + "</span><span style='display:none;'>" + json[i].ROOMTYPE + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '2') {
								$("<li class='order'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '3') {
								$("<li class='live'><span>" + json[i].ROOMID + "</span><span style='display:none;'>" + json[i].CHEID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'T') {
								$("<li class='stopsell'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'Z') {
								$("<li class='dirty'><span>" + json[i].ROOMID + "</span><span style='display:none;'>" + json[i].CHEID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'W') {
								$("<li class='maintance'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
										+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'O') {
								$("<li class='other'><span>" + json[i].ROOMID + "</span><span>" + json[i].ROOMNAME + "</span><span>" 
									+json[i].CHECKUSER + "</span><span style='display:none;'>" + json[i].STATUS + "</span>").appendTo(".room_list");
							}
						}
						
						$("li").mousedown(function(e) {
							//鼠标点击菜单选项添加事件
							$(".setclean").val($(this).find(".roomId").text());
							// 屏蔽浏览器自带的右键
							$(document).bind("contextmenu",
							    function(){
							        return false;
							    }
							);
							if (e.which == 3) {
							    var mousePos = mousePosition(e);  
						        var  xOffset = 20;  
						        var  yOffset = 25; 
						        if ($(this).children("span:last-child").html() == '1' ) {
						        	$(".dirtyroom").hide();
							        $(".cleanroom").show();
							        $(".stoproom").hide();
							        $(".vzroom").hide();
							        $("#cleanroomid").val($(this).children("span:first-child").html());
							        $("#dirtyroomid").val($(this).children("span:first-child").html());
									$("#vzroom").val($(this).children("span:first-child").html());
							        $(".cleanroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						       } else if ($(this).children("span:last-child").html() == '3') {
						        	$(".cleanroom").hide();    
							        $(".dirtyroom").show();
							        $(".vzroom").hide();
							        $(".stoproom").hide();
							       // alert($(this).children("span:first-child").html());
							        $("#dirtyroomid").val($(this).children("span:first-child").html());
									$("#cleanroomid").val($(this).children("span:first-child").html());
									$("#vzroom").val($(this).children("span:first-child").html());
							        $("#thischeckid").val($(this).children("span:nth-child(2)").html());
							        $(".dirtyroom").css("display","block").css("position","absolute")
							        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						       } else if ($(this).children("span:last-child").html() == 'T') {
									$(".stoproom").show();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$(".vzroom").hide();
									$("#cleanroomid").val($(this).children("span:first-child").html());
							        $(".stoproom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");  
						       } else if ($(this).children("span:last-child").html() == 'Z') {
						    	    if ($(this).children("span:nth-child(2)").html() && $.trim($(this).children("span:nth-child(2)").html()).length > 0 ) {
						    	    	$(".setcleanlive").show();
						    	    	$(".setcleanroom").hide();
						    	    	$(".setcleanstop").hide();
						    	    	$(".setcleanlist").show();
						    	    	$(".setcleanbill").show();
						    	    	$(".setcleanplan").show();
						    	    	$(".setcleanhint").show();
						    	    } else {
						    	    	$(".setcleanlive").hide();
						    	    	$(".setcleanroom").show();
						    	    	$(".setcleanstop").show();
						    	    	$(".setcleanlist").hide();
						    	    	$(".setcleanbill").hide();
						    	    	$(".setcleanplan").hide();
						    	    	$(".setcleanhint").hide();
						    	    }
									$(".vzroom").show();
									$(".stoproom").hide();
									$(".cleanroom").hide(); 
									$(".dirtyroom").hide();
									$("#vzroom").val($(this).children("span:first-child").html());
									$("#cleanroomid").val($(this).children("span:first-child").html());
									$("#dirtyroomid").val($(this).children("span:first-child").html());
									$("#thecheckid").val($(this).children("span:nth-child(2)").html());
									$("#thischeckid").val($(this).children("span:nth-child(2)").html());
							        $(".vzroom").css("display","block").css("position","absolute")
					        		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
						       }
							}
						})
						
						$(".room_list li").dblclick(function(e) {
							if ($(this).children("span:last-child").html() == '1') {
								var roomId = $(this).children("span:first-child").html();
								var roomType = $(this).children("span:nth-child(2)").html();
								JDialog.openWithNoTitle("", base_path + "/hotelcheckInByOrder.do?roomId=" + roomId + "&roomType=" + roomType,1179,780);
							} else if ($(this).children("span:last-child").html() == '3') {
								var checknumber = $(this).children("span:nth-child(2)").html()
								var which = 'check';
								window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
							} else if ($(this).children("span:last-child").html() == 'W') {
								var roomId = $(this).children("span:first-child").html();
								var logid;
								$.ajax({
									url: base_path + "/hotelgetLogIdByRoom.do",
									type: "post",
									dataType: "json",
									data: {roomId: roomId},
									success: function (json) {
										logid = json.data.logId;
										window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
									},
									error: function (json) {
										
									}
								})
								return false;
								//showMsg("此房为维修房!");
							} else if ($(this).children("span:last-child").html() == 'T') {
								var roomId = $(this).children("span:first-child").html();
								var logid;
								$.ajax({
									url: base_path + "/hotelgetLogIdByRoom.do",
									type: "post",
									dataType: "json",
									data: {roomId: roomId},
									success: function (json) {
										logid = json.data.logId;
										window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882,512);
									},
									error: function (json) {
										
									}
								})
								return false;
								//showMsg("此房为停售房!");
							} else if ($(this).children("span:last-child").html() == 'Z') {
								if ($.trim($(this).children("span:nth-child(2)").html()) != null && $.trim($(this).children("span:nth-child(2)").html()).length > 0) {
									var checknumber = $(this).children("span:nth-child(2)").html()
									var which = 'check';
									window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
								} else {
									showMsg("此房为脏房!");
								}
							}
						}); 
					} else {
						$(".room_list").html(" ");
					} 
				}
			})
         }
	});
}


function mousePosition(ev){
    ev = ev || window.event;   
    if(ev.pageX || ev.pageY){   
        return {x:ev.pageX, y:ev.pageY};   
    }   
    return {   
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,   
        y:ev.clientY + document.body.scrollTop - document.body.clientTop   
    };   
}  


function setroom(roomstatus, type) {
	var roomId = $(".roomIds").val();
	//var checkId = $("#thischeckid").val();
	//if (roomstatus == '0' || roomstatus == 'Z') {
	//	roomId = $(".roomIds").val();
	//} else 
	if (type == 2) {
		if (roomstatus == 'T') {
			//roomId = $(".roomIds").val();
			window.JDialog.open("增加停售房", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_add.jsp?roomId=" + roomId + "&flag=1",832,462);
			return false;
		} else if (roomstatus == 'W') {
			var roomType  = $("#myRoomType").val();
			window.JDialog.open("添加维修记录",base_path+"/page/ipmspage/leftmenu/repair/addRepairApplication.jsp?roomId=" + roomId + "&roomType=" + roomType,605,350);
			return false;
		}
	} else if (type == 3) {
		if (roomstatus == 'T') {
			//roomId = $(".roomIds").val();
			window.JDialog.open("增加停售房", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_add.jsp?roomId=" + roomId + "&flag=1",832,462);
			return false;
		} else if (roomstatus == 'W') {
			window.JDialog.open("新增维修",base_path+"/page/ipmspage/leftmenu/repair/addRepairApplicationH.jsp?houseId=" + roomId,635,350);
			return false;
		} 
	} else if (type == 1) {
		if (roomstatus == 'T' || roomstatus == 'W') {
			//roomId = $(".roomIds").val();
			window.JDialog.open("增加停售房", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_add.jsp?roomId=" + roomId + "&flag=1",832,462);
			return false;
		}
	}
	
	$.ajax({
		url: base_path + "/hotelsetRoomStatus.do",
		type: "post",
		dataType: "json",
		data: { roomstatus: roomstatus, roomId : roomId, type: type},
		success: function(json) {
			if (json.result == 1) {
				showMsg(json.message);
				setTimeout("location.reload()",1800);
			} else if (json.result == 0) {
				showMsg(json.message);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});	
}

function turnto(which) {
	var roomId = $("#dirtyroomid").val();
	var checknumber = $("#thischeckid").val();
	if (which) {
		window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber + '&type=' + which, 1179, 733);
	} 
}

function refresh(){
	location.reload();
}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function getRoomType() {
	$.ajax({
        url: base_path + "/hotelloadsearchroomtype.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>所有房型</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["ROOMTYPE"] + "'>" +json[index]["ROOMNAME"] + "</option>"
		 	});
		 	$("#dropse_roomtype").html(data);
			$(".mySelect").select({
				width: "175px"
			});
		 },
		 error: function(json) {}
	});
}

function getRoomFloor() {
	$.ajax({
        url: base_path + "/hotelloadRoomFloor.do",
		 type: "post",
		 data : {},
		 success: function(json) {
		 	var data = "<option value=''>所有楼层</option>";
		 	$.each(json,function(index){
		 		data = data + "<option value='" +json[index]["FLOOR"] + "'>" +json[index]["FLOOR"] + "</option>"
		 	});
		 	$("#dropse_floor").html(data);
			$(".mySelect").select({
				width: "175px"
			});
		 },
		 error: function(json) {}
	});
}

function mymousedown(type) {
	/*鼠标点击任意浏览器区域菜单消失*/
	$(document).click(function() {
		$(".dirtyroom").hide();
		$(".cleanroom").hide();
		$(".stoproom").hide();
		$(".vzroom").hide();
		$(".housecleanroom").hide();
		$(".housecleanedroom").hide();
		$(".housestoproom").hide();
		$(".apartcleanroom").hide();
		$(".apartmaintroom").hide();
		$(".housemaintroom").hide();
		$(".apartstoproom").hide();
		$(".apartliveroom").hide();
		$(".houserepairroom").hide();
		$(".onlyhousestoproom").hide();
	});
	$("li").mousedown(function(e) {
		//鼠标点击菜单选项添加事件
		$(".setclean").val($(this).find(".roomId").text());
		// 屏蔽浏览器自带的右键
		$(document).bind("contextmenu",
		    function(){
		        return false;
		    }
		);
		if (e.which == 3) {
			var mousePos = mousePosition(e);  
	        var  xOffset = 20;  
	        var  yOffset = 25; 
	        if ( type == 2) {
	        	var roomType = $(this).find(".roomType").html();
	        	$("#myRoomType").val(roomType);
		        if ($(this).find("span:last-child").html() == 'W') {
		        	if ($.trim($(this).find("span:nth-child(2)").html()) != null && $.trim($(this).find("span:nth-child(2)").html()).length > 0) {
		        		$(".apartstoproom").show();
		        		$(".apartmaintroom").hide();
		        		$(".apartcleanroom").hide();
		        		$(".apartliveroom").hide();
				        $(".apartstoproom").css("display","block").css("position","absolute")
			    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
		        	} else {
		        		$(".apartmaintroom").show();
		        		$(".apartcleanroom").hide();
		        		$(".apartstoproom").hide();
		        		$(".apartliveroom").hide();
				        $(".apartmaintroom").css("display","block").css("position","absolute")
			    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");	
		        	} 
		        } else if ($(this).find("span:last-child").html() == 'T' 
	        		 || $(this).find("span:last-child").html() == '1') {
		        	$(".apartcleanroom").show();
		        	$(".apartmaintroom").hide();
		        	$(".apartliveroom").hide();
		        	$(".apartstoproom").hide();
			        $(".apartcleanroom").css("display","block").css("position","absolute")
		    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");	
		        } else if ($(this).find("span:last-child").html() == '3') {
		        	if ($.trim($(this).find("span:nth-child(2)").html()) != null && $.trim($(this).find("span:nth-child(2)").html()).length > 0) {
		        		$(".apartliveroom").show();
		        		$(".apartmaintroom").hide();
		        		$(".apartcleanroom").hide();
		        		$(".apartstoproom").hide();
				        $(".apartliveroom").css("display","block").css("position","absolute")
			    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
		        	}/* else {
		        		$(".apartmaintroom").show();
		        		$(".apartcleanroom").hide();
		        		$(".apartstoproom").hide();
				        $(".apartmaintroom").css("display","block").css("position","absolute")
			    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");	
		        	} */
		        }
	        } else if (type == 3) {
	        	if ($(this).find("span:last-child").html() == 'W') {

	        		if ($.trim($(this).find("span:nth-child(2)").html()) != null 
	        			&& $.trim($(this).find("span:nth-child(2)").html()).length > 0) {
			        	$(".housemaintroom").hide();
		        		$(".housestoproom").hide();
		        		$(".housecleanroom").hide();
		        		$(".houseliveroom").hide();
		        		$(".houserepairroom").hide();
		        		$(".housecleanedroom").hide();
				        $(".onlyhousestoproom").css("display","block").css("position","absolute")
			    			.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        		} else {
			        	$(".housestoproom").hide();
		        		$(".housemaintroom").hide();
		        		$(".housecleanroom").hide();
		        		$(".houseliveroom").hide();
		        		$(".houserepairroom").hide();
		        		$(".housecleanedroom").hide();
				        $(".onlyhousestoproom").css("display","block").css("position","absolute")
			    			.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        		}
	        	} else if ($(this).find("span:last-child").html() == '1') {
		        	$(".housecleanroom").show();
	        		$(".housemaintroom").hide();
	        		$(".housestoproom").hide();
	        		$(".houseliveroom").hide();
	        		$(".houserepairroom").hide();
	        		$(".onlyhousestoproom").hide();
	        		$(".housecleanedroom").hide();
			        $(".housecleanroom").css("display","block").css("position","absolute")
		    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        	} else if ($(this).find("span:last-child").html() == 'T') {
	        		$(".housestoproom").show();
	        		$(".housemaintroom").hide();
	        		$(".housecleanroom").hide();
	        		$(".houseliveroom").hide();
	        		$(".onlyhousestoproom").hide();
	        		$(".houserepairroom").hide();
	        		$(".housecleanedroom").hide();
		        	$(".housestoproom").css("display","block").css("position","absolute")
		    			.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        	} else if ($(this).find("span:last-child").html() == 'Z') {
	        		$(".housecleanedroom").show();
	        		$(".housestoproom").hide();
	        		$(".housemaintroom").hide();
	        		$(".housecleanroom").hide();
	        		$(".houseliveroom").hide();
	        		$(".onlyhousestoproom").hide();
	        		$(".houserepairroom").hide();
		        	$(".housecleanedroom").css("display","block").css("position","absolute")
		    			.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        	}else if (($(this).find("span:last-child").html() == '2' || $(this).find("span:last-child").html() == '3')) {
	        		//$(".houserepairroom").show();
	        		$(".housemaintroom").hide();
	        		$(".housestoproom").hide();
	        		$(".houseliveroom").hide();
	        		$(".onlyhousestoproom").hide();
	        		$(".housecleanroom").hide();
	        		$(".housecleanedroom").hide();
			        $(".houserepairroom").css("display","block").css("position","absolute")
		    		.css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px");
	        	} 
	        }
	        var roomId = $(this).find("span:first-child").html();
	        $(".roomIds").val($(this).find("span:first-child").html());
		}
	});
}

function queryhouselist() {
	$("#houseName").val("");
	/*$.ajax({
		url:base_path + "/hotelqueryRoomList.do",
		type: "post",
		dataType: "json",
		success:function(json) {
			if (json.state == 3) {
				$(".house").show();
				$(".room_list").html(" ");
				for (i=0;i<json.houselist.length;i++) {
					if (json.houselist[i].STATUS == '1') {
						$("<li><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
					} else if (json.houselist[i].STATUS == '2') {
						$("<li class='order'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
					} else if (json.houselist[i].STATUS == '3') {
						if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
							$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else {
							$("<li class='live'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
						}	
					} else if (json.houselist[i].STATUS == 'T') {
						$("<li class='stopsell'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
					} else if (json.houselist[i].STATUS == 'Z') {
						$("<li class='dirty'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
					} else if (json.houselist[i].STATUS == 'W') {
						if ($.trim(json.houselist[i].CHEID).length > 0 && $.trim(json.houselist[i].CHEID).length != null) {
							$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						} else {
							$("<li class='maintance'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].CHEID +"</span><span>" + json.houselist[i].HOUSENAME + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span>").appendTo(".room_list");
						}					
					} else if (json.houselist[i].STATUS == 'O') {
						$("<li class='other'><span class='roomId'>" + json.houselist[i].HOUSEID + "</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
					}  
				}
				$(".housetotal").text(json.totalcount[0].TOTAL);
				$(".houselive").html(json.totallive[0].LIVE);
				mymousedown(3);	
				bindDbclick(3);				
			} else {
				$(".room_list").html(" ");
			}
		},
		error: function(json) {},
	});*/
	querymyroomstatus();
}

function queryhouseroom(status) {
	$("#houseName").val("");
	$.ajax({
		url:base_path + "/queryHouseRoomByStatus.do",
		type:"post",
		dataType:"json",
		data: { status: status },
		success:function(json) {
			if(json.length > 0) {
				$(".room_list").html(" ");
				for (i=0;i<json.length;i++) {
					if (json[i].STATUS == '1') {
						$("<li><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME+ "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'Z') {
						$("<li class='dirty'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'T') {
						$("<li class='stopsell'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} else if (json[i].STATUS == 'W') {

						if ($.trim(json[i].CHEID).length > 0 && $.trim(json[i].CHEID).length != null) {
							$("<li class='maintance'><span class='roomId'>" + json[i].HOUSEID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
						} else {
							$("<li class='maintance'><span class='roomId'>" + json[i].HOUSEID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
						}
					//	$("<li class='maintance'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" + json[i].STATUS+ "</span>").appendTo(".room_list");
					} 
				}
				mymousedown(3);	
				bindDbclick(3);
			} else {
				$(".room_list").html(" ");
			} 
		}
	})
}


if($("#houseName").focus){
	$("#houseName").keydown(function(e){
		 var key = e.which;
         if (key == 13) {
        	e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        	
        	$(".roomstatus").find("li").removeClass("active");
        	$(".roomstatus").find("li:first-child").addClass("active");
			$.ajax({
				url:base_path + "/queryByHouseName.do",
				type:"post",
				dataType:"json",
				data: { houseName: $("#houseName").val() },
				success:function(json) {
					if(json.length > 0) {
						$(".room_list").html(" ");
						for (i=0;i<json.length;i++) {
							if (json[i].STATUS == '1') {
								$("<li><span>"+ json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none;'>" 
										+json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '2') {
								$("<li class='order'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none;'>" 
										+json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == '3') {
								/*if (json[i].CHEID) {*/
									$("<li class='live'><span class='roomId'>" + json[i].HOUSE_ID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span>" + json[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
								/*} else {
									$("<li class='live'><span class='roomId'>" + json[i].HOUSEID + "</span><span style='display:none'>" + json[i].CHEID +"</span><span style='display:none'>" + json.houselist[i].STATUS + "</span><span>" + json.houselist[i].HOUSENAME + "</span>").appendTo(".room_list");
								}*/
							} else if (json[i].STATUS == 'T') {
								$("<li class='stopsell'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none;'>" 
										+json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'Z') {
								$("<li class='dirty'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none;'>" 
										+json[i].STATUS + "</span>").appendTo(".room_list");
							} else if (json[i].STATUS == 'W') {
								if (json[i].CHEID) {
									$("<li class='maintance'><span class='roomId'>" + json[i].HOUSEID + "</span><span>" + json[i].HOUSENAME + "</span><img src='./css/ipms/img/backgroundimg/holding.png'><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
								} else {
									$("<li class='maintance'><span class='roomId'>" + json[i].HOUSEID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none'>" + json[i].STATUS + "</span>").appendTo(".room_list");
								}
							} else if (json[i].STATUS == 'O') {
								$("<li class='other'><span>" + json[i].HOUSE_ID + "</span><span>" + json[i].HOUSENAME + "</span><span style='display:none;'>" 
									+json[i].STATUS + "</span>").appendTo(".room_list");
							}
						}
						mymousedown(3);	
						bindDbclick(3);
					} else {
						$(".room_list").html(" ");
					} 
				}
			})
         }
	})
}

function bindDbclick(type) {
	$(".room_list li").dblclick(function(e) {
		if ($(this).children("span:last-child").html() == '1') {
			if (type == 2) {
				window.parent.JDialog.open("新增合同",base_path+"/page/crm/member/addContrart.jsp?roomId"+$(this).children("span:first-child").html(),950,500);
			} 
		} else if ($(this).children("span:last-child").html() == '3' || $(this).children("span:last-child").html() == 'W') {
			var checknumber = $(this).children("span:nth-child(2)").html();
			var which = 'check';
			if (type == 3) {
				if ($.trim(checknumber) != null && $.trim(checknumber).length > 0 ) {
						window.parent.parent.JDialog.openWithNoTitle("", base_path + "/page/ipmspage/housecheck/housecheck_details.jsp?houseorderid=" + checknumber,1179,640);
				} else {
					window.parent.JDialog.open("维修申请记录详情",base_path+"/repairInHouseData.do?houseId="+$(this).children("span:first-child").html(),1340,616);
				} 
			} else if (type == 2) {
				if ($.trim(checknumber) != null && $.trim(checknumber).length > 0) {
					window.parent.parent.JDialog.openWithNoTitle("", base_path + "/page/ipmspage/apartmentcheckin/apartment_details.jsp?contractid=" + checknumber,1179,640);
				} else {
					window.parent.JDialog.open("维修申请记录详情",base_path+"/repairData.do?roomId="+$(this).children("span:first-child").html(),950,300);
				}
			} else if (type == 1) {
				if ($.trim(checknumber) != null && $.trim(checknumber).length > 0) {
					window.parent.parent.JDialog.openWithNoTitle("", base_path + "/page/ipmspage/apartmentcheckin/apartment_details.jsp?contractid=" + checknumber,1179,640);
				} else {
					window.parent.JDialog.open("维修申请记录详情",base_path+"/repairData.do?roomId="+$(this).children("span:first-child").html(),950,300);
				}
			}
			//window.JDialog.openWithNoTitle("",base_path +'/page/ipmshotel/room_statistics/roomlist_check.jsp?checkid=' + checknumber, 1179, 733);
		} else if ($(this).children("span:last-child").html() == 'T') {
			var roomId = $(this).children("span:first-child").html();
			var logid;
			$.ajax({
				url: base_path + "/hotelgetLogIdByRoom.do",
				type: "post",
				dataType: "json",
				data: {roomId: roomId},
				success: function (json) {
					logid = json.data.logId;
					window.JDialog.open("停售房编辑", base_path + "/page/ipmshotel/hotelStopHouse/stophouse_edit.jsp?logid=" + logid , 882, 512);
				},
				error: function (json) {
					
				}
			})
			return false;
			//showMsg("此房为停售房!");
		} else if ($(this).children("span:last-child").html() == 'Z') {
			showMsg("此房为脏房!");
		} else if ($(this).children("span:last-child").html() == '2') {
        	window.JDialog.openWithNoTitle("", base_path + "/page/ipmspage/houseorder/houseorder_details.jsp?houseorderid=" + $(this).find("span:nth-child(2)").html() + "&status=0",1179,580);	
		} 
	});
}