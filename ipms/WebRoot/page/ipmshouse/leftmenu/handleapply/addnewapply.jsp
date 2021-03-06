 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>新增保洁申请</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>-->
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.member_margin .pay_div .label_name{
	   float: left;
	}
	.div_lastli{
		width:auto !important;
	}
	</style>
  </head>
  <body>
  <div class="member_margin new_margin">
	   <form action="submitnewcleanapplication.do" method="post" id="addnewapplyform">
			<section class="detail_one clean_margin">
			<input type="hidden" id="roomtype" name="roomtype"
				value="" />
			<input type="hidden" id="memberId" name="memberId"
				value="" />
			<ul class="clearfix">
				<li>
					<label class="label_name" style="color:red;">
						保洁日期：
					</label>
					<input id="cleantime" name="cleantime" type="text" value=""
						class="text_search" readonly>
				</li>
				<li>
					<label class="label_name" style="color:red;">
						时间段：
					</label>
					<select id="timeArea" name="timeArea" class="text_search">
						<option value="0">
							上午
						</option>
						<option value="1">
							下午
						</option>
					</select>
				</li>
				<li>
					<label class="label_name" style="color:red;">
						房间号：
					</label>
					<input id="roomid" name="roomid" type="text" class="text_search" onclick="checkroomid()" readonly >
					<div id="allroomid" class="class_main" style="display: none"></div>
				</li>
				<li>
					<label class="label_name" style="color:red;">
						保洁类型：
					</label>
					<select id="cleanstyle" name="cleanstyle" class="text_search">
					
						<option value="0">
							免费
						</option>
						<option value="1">
							收费
						</option>
						<option value="2">
							赠送
						</option>
						<option value="3">
							其他
						</option>
					</select>
				</li>
				<li>
					<label class="label_name">
						合同编号：
					</label>
					<input id="contrartid" name="contrartid" type="text" value="" class="text_search" readonly="readonly">
				</li>
				<li class="label_tel">
					<label class="label_name">
						手机号：
					</label>
					<input id="mobile" name="mobile" type="text" value="" class="text_search" readonly="readonly">
				</li>
				</ul>
				<div id="pay_div" style="display:none;">
				<ul>
					<li>
						<label class="label_name" style="color:red;">
							保洁费/(元)：
						</label>
						<input id="cleanprice" name="cleanprice" type="text" value="${cleanprice}" class="text_search" maxlength="5" />
					</li>
	<!--				<li>-->
	<!--					<label class="label_name">-->
	<!--						支付金额：-->
	<!--					</label>-->
	<!--					<input id="paycash" name="paycash" type="text" value="" class="text_search" >-->
	<!--				</li>-->
	<!--				<li>-->
	<!--					<label class="label_name">-->
	<!--						使用积分：-->
	<!--					</label>-->
	<!--					<input id="payscore" name="payscore" type="text" value="" class="text_search" >-->
	<!--				</li>-->
	<!--				<li>-->
	<!--					<label class="label_name">-->
	<!--						使用优惠券：-->
	<!--					</label>-->
	<!--					<input id="usecoupon" name="usecoupon" type="text" value="" class="text_search" >-->
	<!--				</li>-->
					<li class="div_lastli" style="color:red;">
						<label class="label_name">
							支付状态：
						</label>
						<input id="paystatus2" name="paystatus2" type="text" value="已支付" class="text_search" readonly="readonly"/>
						<input type="hidden" value="1" id="paystatus" name="paystatus">
<!-- 						<select id="paystatus" name="paystatus" class="text_search"> -->
<!-- 							<option value="1"> -->
<!-- 								已支付 -->
<!-- 							</option> -->
<!-- 							<option value="0"> -->
<!-- 								未支付 -->
<!-- 							</option> -->
<!-- 						</select> -->
					</li>
			</ul>
					</div>
			<ul>
				<li>
					<label class="label_name">
						备注：
					</label>
					<textarea id="remark" name="remark" class="text_search textarea_text" rows="4"></textarea>
				</li>
				<li>
					<input type="button" class="button_margin button_color" onclick="submitform()" value="确定">
				</li>
			</ul>
			</section>
		  </form>
       </div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>-->
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>-->
<!--	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>-->
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<!--	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/membersearch.js"></script>-->
<script >


		
		 var base_path ="<%=request.getContextPath()%>";
			laydate({
			
		    	elem: '#cleantime',
		    	min:laydate.now(0)
			});
		 var regdouble=/^\d+(\.\d{1,2})?$/;
		function checkroomid(){
		
		if($("#cleantime").val() == null || $("#cleantime").val() == ""){
		showMsg("日期不能为空！");
		return false;
		}
		$("#allroomid").css("display","block");
		var inputdate = $("#cleantime").val();
		var timearea = $("#timeArea").val();
		//var obj = document.getElementById("roomid");
		//obj.setAttribute("readOnly", true);
		document.getElementById("allroomid").innerHTML='<iframe src="' + base_path +'/page/ipmspage/leftmenu/handleapply/checkroom.jsp?inputdate='+inputdate+'&timearea='+timearea+'" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>';
		
		}
		function submitform(){
		if($("#roomid").val() == null || $("#roomid").val() == ""){
		showMsg("房间号不能为空！");
		return false;
		}
		if($("#cleantime").val() == null || $("#cleantime").val() == ""){
		showMsg("日期不能为空！");
		return false;
		}
		var inputdate = new Date(Date.parse($("#cleantime").val()));
	
	    var today = new Date();
	    var year = today.getFullYear(); 
	    var month = today.getMonth() + 1;
	    var day = today.getDate();
	    var new_today = new Date(year,month -1,day);
	   //日期校验
	    if(inputdate < new_today){
		  showMsg("日期不能小于当天!");
		   return false;
		  }
		if(inputdate > getLastDay(year,month)){
		      showMsg("申请日期不能超过下月!");
			   return false;
		  }
		  
		if(($("#cleanstyle").val() == "1")){
		if($("#cleanprice").val() == null || $("#cleanprice").val() == ""){
			   showMsg("保洁费用不能为空!");
				return false;
			   
		   }
		  if(isNaN($("#cleanprice").val())){
			showMsg("保洁费请填写数字!");
		    return false;	
	     }else if(regdouble.test($("#cleanprice").val()) == false){
	    		showMsg("保洁费最多只能保留两位数字!");
	    		$("#cleanprice").val("");
				$("#cleanprice").focus();
		    return false;
	   }
		  }
	 
		$.ajax({
	         url: base_path + "/submitnewcleanapplication.do",
			 type: "post",
			 data: $("#addnewapplyform").serialize(),
			 success: function(json) {
			    if(json.result == 1){
				    showMsg(json.message);
				    window.setTimeout("window.parent.location.reload(true)", 1800);
			    }else{
			        showMsg(json.message);
			    }
			 },
			 error: function(json) { showMsg(json.message);}
		        
		});
		}
	//获取下月最大日期	
		function getLastDay(year,month) { 
		    var new_year = year;    //取当前的年份          
		    var new_month = ++month;//取下一个月的第一天，方便计算（最后一天不固定）       
		    if(month>11) {         
		     new_month -=12;        //月份减          
		     new_year++;            //年份增          
		    }         
		    var new_date = new Date(new_year,new_month,1); //往后推迟2个月的一号
		    // var new_date = new Date(new_year,0,1); 
		    return (new Date(new_date.getTime()-1000*60*60*24));//获取当月最后一天日期          
		}
		
		
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		
		
		document.documentElement.onclick=function(e){
			e= window.event ? window.event : e;
			var e_tar=e.srcElement ? e.srcElement : e.target;
			if(e_tar.id=="roomid"){
				return;
			}else{
				/*$("#equipment_class").css("display", "none");*/
				$("#allroomid").css("display", "none");
			}
		};


		$("#cleanstyle").change(function(){
						loadPageBycleanstyle();
					});	
		function loadPageBycleanstyle(){
		 if($("#cleanstyle").val() == "1"){
		 $("#pay_div").show();
		 //$("#paystatus").val("0");
		/* $.ajax({
		         url: base_path + "/cleanstylechange.do",
				 type: "post",
				 data: {},
				 success: function(json) {
				   $("#cleanprice").val(json.message);
				 },
				 error: function(json) { }
		        
		});
		*/
		 }else{
		  $("#pay_div").hide();
		 // $("#paystatus").val("2");
    	  //$("#cleanprice").val("");
    	 // $("#paymoney").val("");
		 }
		
		
		}
			
			
	</script>
  </body>
</html>
