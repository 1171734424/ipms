<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>拆分账</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/bill.css" />
  </head>
  
  	<body>
		<section class="detail_six splitmargin">
			<div class="high_header">
					<div class="margintop">
						<form id="bill_date">
							<input type="text" id="checkId" name="checkId" value="${checkid }" hidden="true"/>
							<input type="text" id="billId" name="billId" value="${strbillid }" hidden="true"/>
							<ul class="segment_ul clearfix">
								<li>
								    <label class="label_name">总金额：</label>
								    <input type="text" id="totalnum" name="totalnum" value="${totalnum }" class="text_search" disabled/>
								</li>
								<li>
									<label class="label_name">请选择拆分份数：</label>
									<select id="num" name="num" class="text_search" onchange="getNum()"> 
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</li>
							<li>
								<ul class="input_money clearfix">
									<li class="li_split clearfix">
										<label class="label_name">请填写拆分金额：</label>
									</li>
									<li id="inputnum" name="inputnum">
										<input class="text_search" name="numberone" onkeyup= "checkNum(this.value)" />
										<input class="text_search" name="numberone" onkeyup= "checkNum(this.value)"/>
									</li>
								</ul>
							</li>
					        <li>
					        	<label class="label_name">拆分原因：</label>
					        	<textarea id="remark" name="remark" rols="10" class="text_search" maxlength="50"></textarea>
					        </li>
					        <li onclick="submitsplit()" class="clearfix"><span role="button" class="button_margin mybill" >确定</span></li>
					      </ul>
		       		   </form>
	    		 </div>
			</div>
		</section>
	</body>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		var checkid = "<%=request.getParameter("checkId")%>";
		var strbillid = "<%=request.getParameter("strbillId")%>";
		var totalnum = $("#totalnum").val();
		var path = "<%=request.getContextPath()%>";
		var flag;
		$(document).ready(function() {
			//getLastNum();
		})
		
		function getNum() {
			var inputnum = $("#num").val();
			$("#inputnum").html(" ");
			for (i=0;i<inputnum;i++) {
				//var inputtext = $("<input id='numberone' name='numberone'/>")
				$("<input class='text_search' name='numberone' onkeyup='checkNum(this.value)'/>").appendTo("#inputnum");
			}
			//inputtext.appendTo("#inputnum");
		}

		function checkNum(value) {
			flag = 0;
			if(!isNaN(value)){
	            var dot = value.indexOf(".");
	            if(dot != -1){
	                var dotCnt = value.substring(dot+1,value.length);
	                if(dotCnt.length > 2){
	                	showMsg("小数位已超过2位！");
	                	flag = 1;
	                	return false;
	                }
	            }
	        } else {
	            showMsg("请输入数字");
	            flag = 1;
	            return false;
	        }
			//getLastNum();
		}
		function getLastNum() {
			$("#inputnum").find("input:last-child").attr("disabled","disabled"); 
			var inputnum = $("#num").val();
			var numtotal = 0.00;
			$(".numberone").each(function(i) {
				if (i == inputnum-1) {
					numtotal = 0.00;
				}
				numtotal = numtotal + parseFloat($(this).val().tofixed(2));
			});
			
			if (numtotal >= $("#inputnum").val()) {
				$("#inputnum").find("input:last-child").val("0.00");
			} else {
				$("#inputnum").val(totalnum-numtotal);
			}
		}
		
		function submitsplit() {
			var totalmoney = 0;
			$($("#inputnum").find("input")).each(function(i) {
				//totalmoney = totalmoney.tofixed(2)
				//thisone = $(this).val().tofixed(2);
				totalmoney = parseFloat(totalmoney) + parseFloat($(this).val());
				totalmoney = totalmoney.toFixed(2);
				checkNum($(this).val());
			})
			if (flag == 1) {
				return false;
			} else if (totalmoney != (parseFloat(totalnum)).toFixed(2)) {
				showMsg("拆分金额总数和总金额不等!");
				return false;
			} else if (!$("#remark").val()) {
				showMsg("请填写拆分原因!");
				return false;	
			} else {
				$.ajax({
					url: path + "/splitAccount.do",
					type: "post",
				 	data : $("#bill_date").serialize(),
					success: function(json) {
						showMsg(json.message);
						window.setTimeout("$(window.parent.parent.$('.tab_two')).click()",1500);
						//window.setTimeout("window.parent.JDialog.close()",1000);
					},
					error: function(json) {}
				});
			}
		}
		
	</script>
</html>
