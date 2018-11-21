 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>水电支付页面</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body style="background:transparent !important;">
     <div id="multiplesale" class="multiplesale" style="padding-top: 20px;">
     	<div class="multiplesale_div">
	        <table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">电费金额</th>
						<th class="header">水费金额</th>
						<th class="header">费用合计</th>
					</tr>	
				</thead>
				<tbody id="info">
					<tr>
						<td align="center">${ammeterCost }</td>
						<td align="center">${watermemterCost }</td>
						<td align="center">${amount }</td>
					</tr>
				</tbody>
			</table>
		</div>
		<table class="totaltable">
            <tr> 
	            <td align="right">
	            	<span>异常天数：</span>
				    <input class="totalinput" id="abnormalDays" value="${we.abnormalDays }" disabled="disabled"/>
	            </td>
	            <td align="right">
	            	<span>异常金额：</span>
	            	<c:if test="${we.abnormalDays eq 0 }">
				    	<input class="totalinput" style="border:1px solid" id="abnormalMoney" disabled="disabled" value="0" />
	            	</c:if>
	            	<c:if test="${we.abnormalDays gt 0}">
				    	<input class="totalinput" style="border:1px solid" id="abnormalMoney" value="${abnormalCost }" />
	            	</c:if>
	            </td>
			    <td align="right">
				    <span>总计金额：</span>
				    <input class="totalinput" id="totalprice" value="0" disable="true"/>
			    </td>			
		   </tr>
           </table>
           <div class="paymethod_gdspage">
              <div id="paycheckbox" class="footertwo_gdspage"> 
	                <span>付款方式：</span>
	               <label class="mo-checkbox" id="cashid">
		               <input id="cashpay" type="checkbox" name="mo-checkbox">
		               <i class="icon"></i>
		               <span class="spanmar_gdspage">现金</span>
	               </label>
	               <label class="mo-checkbox" id="cardid">
		               <input id="cardpay" type="checkbox" name="mo-checkbox">
		               <i class="icon"></i>
		               <span class="spanmar_gdspage">银行卡</span>
	               </label>
              </div>
           </div>
        <div id="" class="footerthree_gdspage_mul"> 
             <input id="cashpayvalue" type="text" class="check payinput_gdspage_cash" disabled="disabled" onkeyup="this.value=this.value.replace(/^\D./g,'')" maxlength="8">
             <input id="cardpayvalue" type="text" class="check payinput_gdspage_card_mul" placeholder="凭证号" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6">
        </div>
         
          <div id="payproject">
          	<div class="footerzero_gdspage">
	            <span>工作账：</span>
	            <select id="payworkbill" name="" class="check projectselect_gdspage">  
	           	<c:forEach var="th" items="${workbill}">  
	             <option  value="${th.workbillid}"> ${th.name} </option>  
	           	</c:forEach>  
	            </select> 
            </div>
          </div> 
          <div class="sbotton_gdspage">
             <input id="workbillbutton" name="" type="button" class="submitbotton_gdspage workbutton" style="float: right;" value="新建工作账" onclick="payworkbill()"/>
             <input id="" name="" type="button" class="submitbotton_gdspage clean" style="float:right;" value="重置" onclick="location.reload(true)"/>
             <input id="" name="" type="button" class="submitbotton_gdspage clean enter" style="float:right;" value="入账" onclick="payhysubmit()"/>
           </div>
        </div>
        <%@ include file="../../../common/script.jsp"%>
		<script>
		 var path = "<%=request.getContextPath()%>";
		 // 初始化显示总金额
		$(function(){
			 $("#totalprice").val(${sumCost });
		});
		// 设置键盘触发事件
		 $("#abnormalMoney").blur(function(){
			var abnormalM = $("#abnormalMoney").val();
			var amount = ${we.amount };
			var sumabCost = ((+amount)+(+abnormalM)).toFixed(2);
			$("#totalprice").val(sumabCost);
		}); 
		
		/*showMsg*/
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		// 选中CheckBox事件(支付方式级联)
		$("#paycheckbox").children("label").find("input[type='checkbox']").click(function(){
			if ($(this).is(":checked")) {
				// 如果选中checkbox时还有其他的有被选中，那么将其他的去除选中状态
				$(this).attr("checked",true).parent().siblings().children("input[type='checkbox']").attr("checked",false);
				// 显示当前id内容，如果结果为"现金"那么就调用现金该出现的内容，反之如此
				if ($(this).attr("id") == "cashpay") {
					//$("#cashpayvalue").val("${ammount}");
					$("#cardpayvalue").val("");
					$("#cardpayvalue").hide();
					$("#payproject").show();
					$("#cashpayvalue").hide();
				}else if($(this).attr("id") == "cardpay"){
					$("#cardpayvalue").show();
					$("#payproject").show();
					$("#cashpayvalue").hide();
					$("#cashpayvalue").val("");
				}
			}else {
				$("#cardpayvalue").hide();
				$("#payproject").hide();
				$("#cashpayvalue").hide();
				$("#cashpayvalue").val("");
				$("#cardpayvalue").val("");
			}
		});
		
	 // 跳创建工作账页面
	 function payworkbill() {
	    var pagetype = "gdssale";
	    window.parent.parent.JDialog.open("创建工作账", path + "/page/apartment/apartmentmainmenu/apartmentcontract/creatework_account.jsp?pagetype="+pagetype ,620,289);
	}
	
	 // 提交支付事件
	 var wea;
	 function payhysubmit() {
		 wea = ${jo };
		 wae = JSON.stringify(wea);
		var contractId = "${contractId }";
		var workbillId = $("#payworkbill").val();
		var cardpayvalue = $("#cardpayvalue").val();
		var abnormalCost = $("#abnormalMoney").val();
		var payment = null;
		if ($("#cashpay").is(":checked")) {
			if (!workbillId) {
				showMsg("请选择工作账（无工作账请先创建工作账）");
				return false;
			}
			payment = 1; 
		}else if($("#cardpay").is(":checked")){
			if (!workbillId) {
				showMsg("请选择工作账（无工作账请先创建工作账）");
			}
			if (!cardpayvalue) {
				showMsg("请先输入凭证号!");
				return false;
			}
			if (cardpayvalue.length < 6) {
				showMsg("凭证号长度小于6位!");
				return false;
			}
			payment = 2;
		}else{
			showMsg("请选择支付方式！");
			return false;
		}
		// 异步提交获取的值至controller后台，并待后台处理返回值
		$.ajax({
			url:path + "/payMoney.do",
			type:"POST",
			data:{
				'contractId' : contractId,
				'wea' : wae,
				'workbillId' : workbillId,
				'payment' : payment,
				'abnormalCost' : abnormalCost
			},
			dataType:"json",
			success:function(json){
				if (json.result == 1) {
					showMsg(json.message);
					window.setTimeout("location.reload(true)", 800);
					window.setTimeout("window.parent.JDialog.close();",800);
				}else if(json.result == -1){
					showMsg(json.message);
					window.setTimeout("location.reload(true)", 800);
					window.setTimeout("window.parent.JDialog.close();",800);
				}
			}
		});
	 } 

	</script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
  </body>
</html>
