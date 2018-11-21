<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>入账</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  <style>
  	#jd_iframe{
  		width:700px !important;
  	}
  	#ontime {
	    height: 155px;
	    width: 360px;
	}
  	#remark {
  		width: 360px;
    	height: 60px;
  	}
  	#clearfix_ul {
  		margin-top: 7px;
  		margin-left: -19px;
  	}
  	.css_common {
  		width: 360px !important;
  	}
  	ul li {
  		padding-bottom: 5px;
  	}
  	#button {
  		position: absolute;
	    left: 73%;
	    top: 80%;
  	}
  </style>
  <body>
   <!--入账开始 -->
		<section class="detail_six">
			<div class="high_header">
				<!--  <i class="imooc detail_imooc" style="color:#3B3E40;"></i>	-->
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix" id="clearfix_ul">
						      <li><label class="label_name">房单号：</label><input name="checkId" type="text" value="${ checkid }" id="id" class="text_edit css_common" readonly></li>
						      <li style="position:relative;">
							      <label class="label_name">项目：</label>
							      <input id="project" name="project" type="text" value="" class="text_edit margin_text css_common" autocomplete="off" onclick="onbill()">
							      <div id="ontime" class="ontime fadeIn"></div>
						      </li>
						      <li><label class="label_name">金额：</label><input id="amount" name="amount" type="number" min=0 value="" class="text_number css_common" ></li>
						      <li><label class="label_name">备注：</label> <textarea id="remark" name="remark" col="100" rols="10"></textarea></li>
						      <li onclick="submitbill()"><span role="button" class="button confirm" id="button">确定</span></li>
			       		   </ul>
			       		   <input type="hidden" id="projectid" name="projectid">
		       		   </form>
	    		 </div>
			</div>
		</section>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/workbillroomInHouse/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipmsinhouse/workbillroomInHouse/tipInfo-lj.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
			var path = "<%=request.getContextPath() %>";
			function submitbill(){
				if($("#amount").val().length == 0){
					showMsg("金额：不能为空!");
					return false;
				}
				if(!isMoney($("#amount").val(), "金额：")){
					return false;
				}
				if($("#amount").val() >= 10000000){
					showMsg("金额太大!");
					return false;
				}
				showMsg("确认入账?", submitbilldata, closeparent);
			}
			
			function closeparent(){
			 	window.parent.JDialog.close();
			}
			
			function submitbilldata(){
				$.ajax({
			         url: path + "/addbillInhouse.do",
					 type: "post",
					 data : $("#bill_date").serialize(),
					 success: function(json) {
						 showMsg(json.message);
						 setTimeout("refreshpage()", 2000);
					 },
					 error: function(json) {}
				});	
			}
			
			$(".margin_text").on("click",function(){
				$(".ontime").css("display","block");
			})
			function refreshpage(){
				parent.location.reload();
			 	window.parent.JDialog.close();
			}
			
			function onbill(){
				document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmshouse/leftmenu/houserefund/commbill.jsp" width="360" height="155" frameborder=no  scrolling="no"></iframe>'
			}
		</script>
  </body>
</html>
