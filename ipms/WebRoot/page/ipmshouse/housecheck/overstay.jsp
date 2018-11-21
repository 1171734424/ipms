<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>续住</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="<%=request.getContextPath()%>/css/initialcss/daterangepikerbootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/daterangepicker.css"/>
	<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
  </head>
  <style>
  	.label_name{
  		width: 150px;
  	}
  </style>
  <body>
   <div class="get_color">
		<form id="getcontinue_bill" class="getcontinue_bill">
			<section class="detail_one">
				<ul class="clearfix">
					<li><label class="label_name">开始日期</label><input id="cuurent_checkouttime" type="text" name="cuurent_checkouttime" value = "" class="text_edit" readonly/></li>
					<li>
						<label class="label_name">结束日期</label> <input id="getcontinuetime" name="getcontinuetime" style="cursor: auto;background: #ffffff;" type="text" value="" class="text_edit wdate"  readonly>
						<!-- <span class="spantime sone" style="" onclick="plustime('A')">中午</span>
						<span class="spantime stwo" onclick="plustime('P')">下午</span> -->
					</li>
					<!--<li>
						<label class="label_name">支付方式</label>
						<select name="projectid" id="projectid" class="text_edit select_project" style="padding: 0px; margin: 3px 0 0 4px;" onchange="selectpaytype(this)">
							<option value="">--请选支付方式--</option>
							<option value="2002">现金收取</option>
							<option value="2003">银行卡</option>
						</select>
						
						<input id="project" name="project" type="text" value="" class="text_edit margin_text" onclick="oncontinue()" onchange="hideontime()">
						 <div id="ontime" class="ontime oncontinue fadeIn"></div> 
					</li>-->
					<!--<li><label class="label_name">预计金额</label> <input id="amount" name="amount" type="number" value="" class="text_edit" ></li>-->
					<!--<li><label class="label_name">凭证号</label> <input id="voucher" name="voucher" type="text" value="" class="text_edit" ></li>-->
					<li style="margin-top: 150px;">
						<input type="button" id="submitbill" value="取消" onclick="closeWin()" class="button_margin cancel"/>
						<input type="button" id="submitbill" value="确认" onclick="submitgetcontinuebill()" class="button_margin confirm"/>
					</li>
				</ul>
			</section>
		 	<input type="hidden" id="houseId" name="houseId" value="">
		</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/moment.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/daterangepicker.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script type="text/javascript">
	  	/*laydate({
	    	elem: '#getcontinuetime',
	    	type: 'time'
		});*/
  		var path = '<%=request.getContextPath()%>';
  		$(function(){
  			var leavedate1 = moment(getUrlParam('leavetime').split(" ")[0] + " 12:00").add(0, 'days');
  			var leavedate2 = moment(getUrlParam('leavetime').split(" ")[0] + " 12:00").add(1, 'days');
  			var checkouttime = getUrlParam('leavetime').split(" ");
  			$('#getcontinuetime').daterangepicker({
  					"singleDatePicker": true,
					"timePicker": false,
					"timePickerIncrement": 1,
				    "locale": {
				        "format": "YYYY/MM/DD",
				        "firstDay": 1
				    },
					"minDate": leavedate1, //moment("20120901", "YYYYMMDD")//.add('hours', 1)
					"startDate": leavedate2.format("YYYY/MM/DD")
				}, function(start, end, label) {
				});
				$("#cuurent_checkouttime").val(leavedate1.format("YYYY/MM/DD"));
				$("#houseId").val(getUrlParam('houseorderid'));
  			//$(".wdate").datetimepicker({ datetimeFormat: "yy/mm/dd HH:mm:ss" })
  		});
  		
  		
  		function selectpaytype(e){
			if($(e).val() == "2003"){
				$(".newdatail_six ul li:nth-child(8)").show();
			}else{
				$(".newdatail_six ul li:nth-child(8)").hide();
			}
		}
		
  		function closeWin() {
		  window.parent.JDialog.close();
		}

  		function submitgetcontinuebill(){
			if(isNull($("#getcontinuetime"))){
				return false;
			}
			$.ajax({
		         url: path + "/overstay.do",
				 type: "post",
				 data : $("#getcontinue_bill").serialize(),
				 success: function(json) {
				 	if(json.result == 1){
						showMsg(json.message);
				 	}else if(json.result == 0){
				 		showMsg(json.message);
				 	}else{
				 		showMsg("错误" + JSON.stringify(json));
				 	}
				 	window.setTimeout("window.parent.JDialog.close()", 1800);
				 },	
				 error: function(json) {
				 	alert(json.responseText)
				 }
			});
  		}
  		
  		$(".margin_text").on("click",function(){
			$(".ontime").show();
			$(document).on("click", function () {//对document绑定一个影藏Div方法
				$("#ontime").hide();
			});
			event.stopPropagation();
		})
		
		//获取url中的参数
        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }
		
		$(".spantime").bind("click",function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
		})
  		/*续住-->项目*/
  		function oncontinue(){
			document.getElementById("ontime").innerHTML='<iframe src="' + path +'/page/ipmspage/common_addbill/commbill.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
		}
		
		function hideontime(){
			$("#ontime").css("display","none");
		}
		
		function refreshpage(){
			 $(window.parent.document).find(".tab_one").click();
			 window.parent.JDialog.close();
		}
		
  	</script>
  </body>
</html>
