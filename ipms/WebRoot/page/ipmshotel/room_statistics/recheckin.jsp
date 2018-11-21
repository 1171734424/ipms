<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link href="<%=request.getContextPath()%>/css/initialcss/daterangepikerbootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/daterangepicker.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		.register_one{
			padding: 53px 24px;
		}
	</style>
  </head>
  <body>
  <div class="register_margin">
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
			  <li><label class="label_name">离店日期</label><input id="checkOuttime" name="checkOuttime" class="input_ms"/></li>
			  <li>
			   	<input type="button" style="background-color: #F79F24;color: white;" class="button_margin ms_re" onclick="resetprice()" value="确定">
			  </li>
			  </ul>
		   </section> 
		 </form>
   </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/moment.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/daterangepicker.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = "<%=request.getParameter("checkid")%>";
		$(function (){
			loadCheckData();
		})

		function loadCheckData(){
			$.ajax({
		         url: path + "/loadCheckData.do",
				 type: "post",
				 data : {checkid : checkid},
				 success: function(json) {
				 	$("#checkOuttime").val(dealDate(json[0].CHECKOUTTIME));
				 	var checkoutdate = moment(dealDate(json[0].CHECKOUTTIME).split(" ")[0] + " 12:00").add(1, 'days');
  					var checkouttime = dealDate(json[0].CHECKOUTTIME).split(" ");
					$('#checkOuttime').daterangepicker({
	  					"singleDatePicker": true,
						"timePicker": true,
						"timePickerIncrement": 1,
						"opens": "right",
					    "locale": {
					        "format": "YYYY/MM/DD HH:00",
					        "daysOfWeek": ["日","一","二","三", "四","伍","六"],
					        "monthNames": ["一月", "二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
					        "firstDay": 1
					    },
						"minDate": checkouttime[0] + " 12:00", //moment("20120901", "YYYYMMDD")//.add('hours', 1)
						"startDate": checkoutdate.format("YYYY/MM/DD HH:MI")
					}, function(start, end, label) {
					});
				 },
				 error: function(json) {}
			});
		}
		
		function resetprice(){
			var checkOuttime = $("#checkOuttime").val();
			$.ajax({
		         url: path + "/recheckin.do",
				 type: "post",
				 data : {
				 	checkid : checkid,
				 	checkOuttime : checkOuttime
				 },
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("refresh()", 2000);
					}else {
						showMsg(json.message);
						setTimeout("refresh()", 2000);
					}
				 },
				 error: function(json) {}
			});
		}
		
		function refresh(){
			if ($(window.parent.parent.parent.document).find("iframe")[1]){
				 $($(window.parent.parent.parent.document).find("iframe")[1].contentDocument.logFrame.document.forms[0]).submit();
				 window.parent.parent.parent.JDialog.close();
			}
		}
		
	</script>
  </body>
</html>
