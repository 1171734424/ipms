<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>新增提示记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	
  </head>
  
  <body>
		<section class="detail_six add_pro promptdetail">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<ul class="clearfix">
						      <li><label class="label_name">订单号：</label><input id="orderId" name="orderId" style="width: 20.6em;" value="${result['CHECKID']}" class="text_number" readonly></li>
						      <li><label class="label_name">编号：</label><input id="logId" name="logId" style="width: 20.6em;" value="${result['LOGID']}" class="text_number" readonly></li>
						      <li><label class="label_name">内容：</label><textarea id="prompt_content" name="content" readonly>${result["CONTENT"]}</textarea></li>
			       		   </ul>
		       		   </form>
	    		 </div>
			</div>
			<input name="promptcount" id="promptcount" hidden="true"/>
			<div><li onclick="promptquery();"><span role="button" style="margin-right: 6px;"class="button confirm" >确定</span></li></div>
		</section>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipmshotel/order/rightclick.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
	var path = "<%=request.getContextPath()%>";
	/**function refresh(){
		var checkid = $("#prompt_checkid").val();
		$.ajax({
	         url: path + "/countPrompt.do",
			 type: "post",
			 data : {checkid : checkid},
			 success: function(json) {
			 	if(json[0]["NUM"] != 0){
				 	$($(window.parent.document).find(".tab_four").find("span").hasClass("hot")).show();
					$($(window.parent.document).find(".tab_four").find("span").hasClass("hot")).html(json[0]["NUM"]);
			 	}
			 },
			 error: function(json) {}
		});
	    $(window.parent.document).find(".tab_four").click();
		window.parent.JDialog.close();
	}**/

	function promptquery () {
		countOrderPrompt();
		//refresh();
	}

	function refresh(){
	    $(window.parent.document).find(".tab_four").click();
	    $($(window.parent.document).find(".tab_four")).find(".hot").show();
	    $($(window.parent.document).find(".tab_four")).find(".hot").html($("#promptcount").val());
		window.parent.JDialog.close();
	}

	function otherrefresh() {
		$($(window.parent.document).find(".tab_four")).find(".hot").hide();
 		$(window.parent.document).find(".tab_four").click();
 		window.parent.JDialog.close();
 	}
 	
	function countOrderPrompt() {
		$.ajax({
	         url: path + "/ordercountPrompt.do",
			 type: "post",
			 data : { checkid : $("#orderId").val() },
			 success: function(json) {
			 	if (json[0]["NUM"] != 0) {
				 	$("#promptcount").val(json[0]["NUM"]);
				 	 setTimeout("refresh()", 1500);	
			 		//$($(window.parent.document).find(".tab_four")).find(".hot").html(json[0]["NUM"]);
			 	}  else {
			 		$("#promptcount").val('0');
			 		setTimeout("otherrefresh()", 1500);	
				}
			 },
			 error: function(json) {}
		});
	}
	</script>
  </body>
</html>
