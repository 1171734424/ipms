<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
	request.setAttribute("branchtype",
			request.getAttribute("branchtype"));
%>
<!DOCTYPE HTML>
<html>
<head>
<title>酒店/公寓/民宿</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="Shortcut Icon" href="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/deer.png" type="image/x-icon" />
<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" 	rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/index/htmleaf-demo.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/footer.css" />
<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/index/index.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/jquery-labelauty.css" />
<script src="<%=request.getContextPath()%>/script/ipms/js/room_urban/jquery-labelauty.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<script>var base_path = "<%=request.getContextPath()%>"; var flags = ${pmsFlag};</script>
</head>
<body>
	<div class="htmleaf-container">
		<div class="demo_title"></div>
		<%--<div class="img_desktop">
				<img src="<%=request.getContextPath()%>/css/ipms/img/backgroundimg/ff.png" width="1000px" height="500px" />
			</div>
			--%>
		<div class="fl loginReg-viewbox" id="cashbox">
		
			<div id="shift" class="loginReg-order">
				<!--默认班次展示 -->
				<c:if test="${branchtype == 1}">
				<div class="default_class">
				  <span class="span_default"></span>
					<!--除默认班次之外的其他班次 -->
					<ul id="shiftTime" class="order_ul clearfix">
					</ul>
				</div>
				</c:if>
				<!-- 默认班次展示结束-->
			</div>
			<!--<div id="cashbox" class="loginReg-cash">
						<ul class="clearfix">	
							<li class="loginReg-order-li">
								<input type="radio" name="radio1" value="A" class="labelauty" checked="checked"  data-labelauty="备用金A">
							</li>
							<li class="loginReg-order-li">
								<input type="radio" name="radio1" value="B" class="labelauty"  data-labelauty="备用金B">
							</li>
						</ul>
					</div>
				-->
		</div>
		<div class="demo">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="navbar">
							<span class="navbar_close" onclick=" back();">退出</span>
							<ul class="menu fa">
								<li onclick="turnToPms();"><a id="PMS"
									class="fa fa-h-square"></a></li>
								<c:forEach items="${ subSystemKind }" var="subSystem">
									<li onclick="turnToSysTem(this)"><a class="fa"
										id="${subSystem.key}"></a> <span class="subSystem_two">${ subSystem.value }</span>
									</li>
									<script>
										var subsystem = "#${subSystem.key}";
										var systemStyle = "fa-${subSystem.key}";
										$(subsystem).addClass(systemStyle);
									</script>
								</c:forEach>
								<li></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="branchtype" value="${branchtype}" />
	<%-- <div class="footer"><%@include file="../system/footer.jsp"%></div> --%>
	<script>
			var subSystemKind =${subSystemKind};
			$("	.navbar_close").on("mousedown",function(){
				$(".navbar_close").addClass("active");
			});
			$("	.navbar_close").on("mouseout",function(){
				$(".navbar_close").removeClass("active");
			});
			$(".span_default").click(function(){
				  var temp,lis;
				  temp = document.getElementById('shiftTime');
	              lis = temp.getElementsByTagName('li');
	              $(".default_class ul").css("transform", "scale(1)");
	              $(".default_class ul").css("z-index", "1");
	              /* $(".default_class ul").css("transition", "transform 0.4s 0.08s, z-index 0s 0.5s"); */
	              $(".default_class ul li:nth-child(1)").addClass("sectionone");
	              $(".default_class ul li:nth-child(1) span").addClass("sectiononespan");
	              if(lis.length==2){
	                 $(".default_class ul li:nth-child(2)").addClass("sectiontwo");
	                 $(".default_class ul li:nth-child(2) span").addClass("sectiontwospan");
	              }
	               if((lis.length=="3")||(lis.length=="4")){
	                 $(".default_class ul li:nth-child(3)").addClass("sectionthreefour_c3");
	                 $(".default_class ul li:nth-child(3) span").addClass("sectionthreefour_c3span");
	                 $(".default_class ul li:nth-child(2)").addClass("sectionthreefour_c2");
	                 $(".default_class ul li:nth-child(2) span").addClass("sectionthreefour_c2span");
	                 
	              } 
	              if((lis.length ==4 )||(lis.length == 6)){
	                 $(".default_class ul li:nth-child(4)").addClass("sectionfoursix_c4");
	                 $(".default_class ul li:nth-child(4) span").addClass("sectionfoursix_c4span");
	               }
	               if(lis.length == 5){
	                 $(".default_class ul li:nth-child(5)").addClass("sectionfive_c5");
	                 $(".default_class ul li:nth-child(5) span").addClass("sectionfive_c5span");
	                 $(".default_class ul li:nth-child(2)").addClass("sectionfive_c2");
	                 $(".default_class ul li:nth-child(2) span").addClass("sectionfive_c2span");
	                 $(".default_class ul li:nth-child(4)").addClass("sectionfive_c4");
	                 $(".default_class ul li:nth-child(4) span").addClass("sectionfive_c4span");
	                 $(".default_class ul li:nth-child(3)").addClass("sectionfive_c3");
	                 $(".default_class ul li:nth-child(3) span").addClass("sectionfive_c3span");
	              }
	              if(lis.length == 6 ){
	                $(".default_class ul li:nth-child(6)").addClass("sectionsix_c6");
	                 $(".default_class ul li:nth-child(6) span").addClass("sectionsix_c6span");
	                 $(".default_class ul li:nth-child(2)").addClass("sectionsix_c2");
	                 $(".default_class ul li:nth-child(2) span").addClass("sectionsix_c2span");
	                 $(".default_class ul li:nth-child(5)").addClass("sectionsix_c5");
	                 $(".default_class ul li:nth-child(5) span").addClass("sectionsix_c5span");
	                 $(".default_class ul li:nth-child(3)").addClass("sectionsix_c3");
	                 $(".default_class ul li:nth-child(3) span").addClass("sectionsix_c3span");
	              }
			  
			});
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			$(document).ready(function() {
				if (flags == true) {
					$(".navbar .menu li:nth-child(1):before").css("display", "block");
				} else {
					$(".navbar .menu li:nth-child(1)").css("display", "none");
				}
				$(".navbar .menu").css({
					'transform':'scale(1)',
					'z-index':'1',
					'transition':'transform 0.4s 0.08s, z-index 0s 0.5s'
					});
				
				$('.menu li a').hover(function() {
					$(this).addClass('on');
				}, function() {
					$(this).removeClass('on');
				});	
								
				$('#cashbox :input').labelauty();
				var branchtype = $("#branchtype").val();
				if(branchtype=="1"){
				$.ajax({
					url : base_path + "/shiftTime.do",
					type : "post",
					dataType : "text",
					success : function(json) {
					
						var obj = JSON.parse(json);
						console.log(obj);
						
						if(obj.result == 1){
						$("#shift span").html(obj.data.current[0]["SHIFT_NAME"]);
						var tabledata = "";
						 	$.each(obj.data.other, function(index){
						 		tabledata = tabledata +"<li class='cloginReg-order-li'><input onclick='clicktime("+obj.data.other[index]["SHIFTTIME_ID"]+",\""+obj.data.other[index]["SHIFT_NAME"]+"\")' type='radio' name='radio' value='"+obj.data.other[index]["SHIFTTIME_ID"]+"' data-labelauty='"+obj.data.other[index]["SHIFT_NAME"]+"' ></li>";
						 		
						 	});
						if(obj.data.other.length == 0){
						 		$("#shift ul").html("");
						 	}else{
						 	    
						 		$("#shift ul").html(tabledata);
						 		$('#shift :input').labelauty();
						 		$.each(obj.data.other, function(index){
							 		if(obj.message == obj.data.other[index]["SHIFTTIME_ID"]){
								 		
								 		$(":radio[name='radio'][value='" + obj.message + "']").prop("checked","checked");
								 		//$(":radio[name='radio'][value='" + obj.message + "']").css('display','none'); 
								 	}
							 	});
								 	//if(obj.data.other.length == 2){
								 	//debugger;
								 	  //var step=100;//每次旋转多少度  
	                                  //obj.css({'transform':'rotate('+(deg+step)%360+'deg)'});  
								 	  //$(".default_class:hover .order_ul li:nth-child(2)").attr("transform", "rotate(400deg)");
							 	   // }
							 	}
						}else{
						  showMsg(obj.message);
						
						}
					
					
					//debugger
					/*if(obj.result == 1){
						var tabledata = "";
					 	$.each(obj.data.other, function(index){
					 		tabledata = tabledata +"<li class='cloginReg-order-li'><input  type='radio' name='radio' value='"+obj.data.other[index]["SHIFTTIME_ID"]+"' data-labelauty='"+obj.data.other[index]["SHIFT_NAME"]+"' ></li>";
					 		
					 	});
					 	if(obj.data.length == 0){
					 		$("#shift ul").html("");
					 	}else{
					 		$("#shift ul").html(tabledata);
					 		$('#shift :input').labelauty();
					 		$.each(obj.data, function(index){
						 		if(obj.message == obj.data[index]["SHIFTTIME_ID"]){
							 		
							 		$(":radio[name='radio'][value='" + obj.message + "']").prop("checked","checked");
							 		//$(":radio[name='radio'][value='" + obj.message + "']").css('display','none'); 
							 	}else{
							 	
							 	}
						 	});

					 	  }
					 	  
                    
					
					}else{
					  showMsg(obj.message);
					
					}
   */
					},
					error : function() {
						showMsg("默认班次失败或班次未配置!");
					}
				});
				}
		    });
			
			function clicktime(x,y){
			
		//console.log("<li class='cloginReg-order-li'><input checked='checked' type='radio' name='radio' value='"+x+"' data-labelauty='"+y.replace(/\"/g,"")+"' ></li>")
		//$("#shift span").html("");
			$(".span_default").html(y);
			$("#shiftTime").css("transform", "scale(0)");
			$("#shiftTime").css("transition", "transform 0.4s 0.08s, z-index 0s 0.5s;");
			//.html("<li class='cloginReg-order-li'><input checked='checked' type='radio' name='radio' value='"+x+"' data-labelauty='"+y.replace(/\"/g,"")+"' ></li>");
			
			}
			function checkTime(i) {
				return i < 10 ? '0' + i : '' + i;
			}
			
			function turnToSysTem(e){
				var obj = $(e).find("a").attr('id');
				var href = "loadMainFrame.do?subSystem="+obj;
				$("#" + obj).attr('href',href);
				
			}
			
			function turnToPms(){
			    var shiftid = null;
				var obj_shift = document.getElementsByName("radio");
		
				for ( var i = 0; i < obj_shift.length; i++) {
					if (obj_shift[i].checked) {
						shiftid = obj_shift[i].value;
					}
				}
				
				var branchtype = $("#branchtype").val();
				if((branchtype=="1")&&(shiftid==null)){
				    showMsg("默认班次失败或班次未配置，请手动选择班次或前往crm配置班次！");
				    return;
				}
			  	$.ajax({
					url : base_path + "/pmsLoginlog.do",
					type : "post",
					dataType : "json",
					data : {
						shiftid: shiftid						
					},
					async:false,
					success : function(json) {
						if (1 == json.result) {
						  if (json.message) {
						     showMsg(json.message)
						  } else {
						  	$("#PMS").attr('href','loadMainFrame.do?subSystem=PMS');
						  }
						} else {
							window.location.href = "<%=request.getContextPath()%>/page/ipmspage/login/login.jsp";
						}
					},
					error : function() {
					  window.location.href = "<%=request.getContextPath()%>/page/ipmspage/login/login.jsp";
					}
				});
			}
			
			
			
    	function turnToLoginPage(){
				window.location.href = "<%=request.getContextPath()%>/page/ipmspage/login/login.jsp";
		}
		function back() {
			window.top.location.href = base_path + "/logout.do";
		}
	</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
</body>
</html>
