<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("rulesPeriod", request.getParameter("rulesPeriod"));%>
<% request.setAttribute("rulesPeriodDetails", request.getParameter("rulesPeriodDetails"));%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>周期详情选择</title>
	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	  <meta http-equiv="description" content="This is my page">
	  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
	  <link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	  <link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
	  <style>
	  	.selectStyle {
    		background: #0082d2;
    		color: #f2f2f2;
 		}
 		
 		input[type="checkbox"] {
		    position: absolute;
		    z-index: -99999;
		    clip: rect(10 15 15 10);
		}
		
		tr,td{ 
			border:1px solid #eee;
			text-align: center;
		} 
		
		.tipTable_tr {
			height: 35px;
		}
		
		td {
			width: 80px;
		}
		
		.tip {
			width: 80%;
		    margin-left: 21px;
		    margin-top: 15px;
		}
		
		.menu_td {
			background: #f2f2f2;
    		color: #27292a;
			width:30px;
		}
		.changeMenu_td {
			background: #fc8a15;
    		color: #f2f2f2;
		}
		.tip_menu {
			width:40%;
		    margin-left: 21px;
		    margin-top: 15px;
		}
		.submitButton {
		    height: 34px;
		    line-height: 34px;
		    text-align: center;
		    width: 80px;
		    border: none;
		    background-color: #fc8a15;
		    color: #fff;
		    cursor: pointer;
		    border-radius: 4px;
		    font-size: 14px;
		}
		.submitButton_cancel {
			position: absolute;
		    margin-left: 85px;
		    margin-top: -34px;
		}
		
		.submitButton_div {
			margin-top: 60px;
   			margin-left: -200px;
		}
		.sureSubmitButton {
			background-color: #5A5D9D;
		}
	  </style>
  </head>
  <body>
    <form action="" name="form_rpapply" id="form_rpapply">
	  <div class="rpappply_main">
	  <input type="text" id="rulesPeriod" value="<%=request.getParameter("rulesPeriod")%>" hidden="true"/>
	  <input type="text" id="rulesPeriodDetails" value="<%=request.getParameter("rulesPeriodDetails")%>" hidden="true"/>
	    <c:if test="${rulesPeriod eq '1' }">
			<div class="tipTable">
				<table class="tip_menu">
					<tr class="tipTable_tr">
						<td class="menu_td" id="selectTimeAll" onclick="selectTimeAll(this)"><span>全选/反选</span></td>
					</tr>
				</table>
				<table class="tip">
					<c:forEach var="item" begin="1" end="24" step="1" >
						<c:if test="${item%10 == 1 }"><tr class="tipTable_tr"></c:if>
							<td onclick="selectTable(this)"><input type="checkbox" name="timeCheckBox"  value="${item }"><span>${item }点</span></td>
						<c:if test="${item%10 == 0 }"></tr></c:if>
					</c:forEach>
				</table>
			</div>
	    </c:if>		
	    <c:if test="${rulesPeriod eq '2' }">
			<div class="tipTable">
				<table class="tip_menu">
					<tr class="tipTable_tr">
						<td class="menu_td" id="selectDayAll" onclick="selectDayAll(this)"><span>全选/反选</span></td>
					</tr>
				</table>
				<table class="tip">
				<c:forEach var="item" begin="1" end="31" step="1" >
					<c:if test="${item%10 == 1 }"><tr class="tipTable_tr"></c:if>
					<td onclick="selectTable(this)"><input type="checkbox" name="dayCheckBox"  value="${item }"><span>${item }日</span></td>
					<c:if test="${item%10 == 0 }"></tr></c:if>
				</c:forEach>
				</table>
			</div>
	    </c:if>		
	    <c:if test="${rulesPeriod eq '3' }">
			<div class="tipTable">
				<table class="tip_menu">
					<tr class="tipTable_tr">
						<td class="menu_td" id="selectWeekAll" onclick="selectWeekAll(this)"><span>全选/反选</span></td>
						<td class="menu_td" id="normalday" onclick="normalday(this)"><span>平时</span></td>
						<td class="menu_td" id="weekendday" onclick="weekendday(this)"><span>周末</span></td>
					</tr>
				</table>
				<table class="tip">
					<tr class="tipTable_tr">
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="0"><span>周日</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="1"><span>周一</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="2"><span>周二</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="3"><span>周三</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="4"><span>周四</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="5"><span>周五</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="weekCheckBox"  value="6"><span>周六</span></td>
					</tr>
				</table>
			</div>
	    </c:if>		
	    <c:if test="${rulesPeriod eq '4' }">
			<div class="tipTable">
				<table class="tip_menu">
					<tr class="tipTable_tr">
						<td class="menu_td" id="selectMonthAll" onclick="selectMonthAll(this)"><span>全选/反选</span></td>
					</tr>
				</table>
				<table class="tip">
					<tr class="tipTable_tr">
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="01"><span>一月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="02"><span>二月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="03"><span>三月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="04"><span>四月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="05"><span>五月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="06"><span>六月</span></td>
					</tr>
					<tr class="tipTable_tr">
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="07"><span>七月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="08"><span>八月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="09"><span>九月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="10"><span>十月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="11"><span>十一月</span></td>
						<td onclick="selectTable(this)"><input type="checkbox" name="monthCheckBox"  value="12"><span>十二月</span></td>
					</tr>
				</table>
			</div>
	    </c:if>																
	</div>
		<div  class="submitButton_div">
			<div class="submitButton sureSubmitButton" role="button" onclick="submitOK();">
				确定
			</div>
			<div class="submitButton submitButton_cancel" role="button" onclick="window.parent.JDialog.close();">
				取消
			</div>
		</div>
	</form>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
 	var base_path = '<%=request.getContextPath()%>';
    </script>
    <script>
    /*
     * 初始化赋值
     */
    $(function(){
    	var rulesPeriod = $("#rulesPeriod").val();
    	/* 当周期类型为1时代表选择的是时 */
    	if (rulesPeriod == '1') {
    		// 初始化修改样式
    		$("td").css("width","56px");
    		$(".tip_menu ").css("width","12%");
    		$(".submitButton_div").css({"margin-top":"25px","margin-left":"195px"});
    		// 调用回调方法
			var rPDetails = "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24";
    		initSelect("timeCheckBox",rPDetails,"selectTimeAll");
    	}
    	
    	/* 当周期类型为2时代表选择的是天 */
    	if (rulesPeriod == '2') {
    		// 初始化修改样式
    		$("td").css("width","56px");
    		$(".tip_menu ").css("width","12%");
    		$(".submitButton_div").css({"margin-top":"-8px","margin-left":"195px"});
    		// 调用回调方法
    		var rPDetails = "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31";
    		initSelect("dayCheckBox",rPDetails,"selectDayAll");
    	}
    	/* 当周期类型为3时代表选择的是周 */
    	if (rulesPeriod == '3') {
    		// 初始化修改样式
    		$(".submitButton_div").css({"margin-top":"98px","margin-left":"195px"});
    		// 获取参数
        	var rulesPeriodDetails = $("#rulesPeriodDetails").val();
        	var weekCheckBox = $("input[name='weekCheckBox']");
        	// 如果全选就将全选按钮添加样式
        	if (rulesPeriodDetails == "0,1,2,3,4,5,6") {
				$("#selectWeekAll").addClass("changeMenu_td");
			}
        	// 如果选择平时就将平时按钮添加样式
        	if (rulesPeriodDetails == "0,1,2,3,4") {
        		$("#normalday").addClass("changeMenu_td");
			}
        	// 如果选择周末就将周末按钮添加样式
        	if (rulesPeriodDetails == "5,6") {
        		$("#weekendday").addClass("changeMenu_td");
			}
        	// 当将父页面获取的字符串不为空的时候，将以","分割为数组
        	if (rulesPeriodDetails != "") {
        		rulesPeriodDetails = rulesPeriodDetails.split(",");
			}
        	// 循环将上一个页面的值赋值到当前页面中，并设置为选中状态及添加样式
    		for (var i = 0; i < rulesPeriodDetails.length; i++) {
    			$(weekCheckBox[rulesPeriodDetails[i]]).prop("checked","true");
    			$(weekCheckBox[rulesPeriodDetails[i]]).parent().addClass("selectStyle");
			}
		}
    	/* 当周期类型为4时代表选择的是月 */
    	if (rulesPeriod == '4') {
    		// 初始化修改样式
    		$(".tip_menu ").css("width","12%");
    		$(".submitButton_div").css({"margin-top":"60px","margin-left":"195px"});
    		// 调用回调方法
    		var rPDetails = "01,02,03,04,05,06,07,08,09,10,11,12";
    		initSelect("monthCheckBox",rPDetails,"selectMonthAll");
		}
    });
    
    /*
     * 全选/反选时
     */
    function selectTimeAll(e) {
    	allSelectParame(e,"timeCheckBox");
    }
    
    /*
     * 全选/反选天
     */
    function selectDayAll(e) {
    	allSelectParame(e,"dayCheckBox");
    }
    
    /*
     * 单选按键
     */
    function selectTable(e) {
		if ($(e).children().is(":checked")) {
    		$(e).children().removeAttr("checked");
			$(e).removeClass("selectStyle");
		} else {
			$(e).children().prop("checked","true");
			$(e).addClass("selectStyle");
		}
    }
    
    /*
     * 全选/反选周
     */
    function selectWeekAll(e) {
    	allSelectParame(e,"weekCheckBox");
    }
    
    /*
     * 选择平时
     */
    function normalday(e) {
    	$(e).siblings().removeClass("changeMenu_td");
    	$(e).addClass("changeMenu_td");
    	$("input[name='weekCheckBox']").each(function(i){
    		if(i<5){
    			$(this).prop("checked","true");
				$(this).parent().addClass("selectStyle");
    		} else {
    			$(this).removeAttr("checked");
    			$(this).parent().removeClass("selectStyle");
    		}
    	});
    }
    
    /*
     * 选择周末
     */
    function weekendday(e) {
    	$(e).siblings().removeClass("changeMenu_td");
    	$(e).addClass("changeMenu_td");
    	$("input[name='weekCheckBox']").each(function(i){
    		if(i>4){
    			$(this).prop("checked","true");
				$(this).parent().addClass("selectStyle");
    		} else {
    			$(this).removeAttr("checked");
    			$(this).parent().removeClass("selectStyle");
    		}
    	});
    }
    
    /*
     * 全选/反选月
     */
    function selectMonthAll(e) {
    	allSelectParame(e,"monthCheckBox");
    }
   
    /*
     * 提交方法
     */ 
	 function submitOK(){
    	var parameValue="";
    	var rulesPeriod = $("#rulesPeriod").val();
    	if (rulesPeriod == "1") {
    		parameValue = commontParame("timeCheckBox");
		}
    	if (rulesPeriod == "2") {
    		parameValue = commontParame("dayCheckBox");
		}
    	if (rulesPeriod == "3") {
    		parameValue = commontParame("weekCheckBox");
		}
    	if (rulesPeriod == "4") {
    		parameValue = commontParame("monthCheckBox");
		}
    	window.parent.$("#rulesPeriodDetails").val(parameValue);
		window.setTimeout("window.parent.JDialog.close();",500);
	 }
    
	 /*
     * 根据传入的参数名称，全选不同的checkbox
     */
    function allSelectParame(e,selectParame) {
    	$(e).siblings().removeClass("changeMenu_td");
    	$(e).toggleClass("changeMenu_td");
    	$("input[name="+ selectParame +"]").each(function(){
			if ($(this).is(":checked")) {
    			$(this).removeAttr("checked");
    			$(this).parent().removeClass("selectStyle");
			} else {
				$(this).prop("checked","true");
				$(this).parent().addClass("selectStyle");
			}
    	});
    }
    
	 /*
     * 根据checkbox的名称 获取选中的复选框
     */ 
    function commontParame(parame){
	   var parameValue="";
	    	$("input[name="+ parame +"]").each(function(){
		   		if ($(this).is(":checked")) {
		   			parameValue += $(this).val() + ",";
				}
	   		});
	   return parameValue.substring(0, parameValue.length-1);
    }
	 
    /*
	 * 将父页面数据传入子页面中，并根据内容显示不同的样式
	 */
	function initSelect(inputName,rPDetails,selectFunc) {
		// 获取参数
    	var rulesPeriodDetails = $("#rulesPeriodDetails").val();
    	var monthCheckBox = $("input[name="+ inputName +"]");
    	// 如果全选就将全选按钮添加样式
    	if (rulesPeriodDetails == rPDetails) {
			$("#"+ selectFunc +"").addClass("changeMenu_td");
		}
    	
    	// 当将父页面获取的字符串不为空的时候，将以","分割为数组
    	if (rulesPeriodDetails != "") {
    		rulesPeriodDetails = rulesPeriodDetails.split(",");
		}
    	// 循环将上一个页面的值赋值到当前页面中，并设置为选中状态及添加样式
		for (var i = 0; i < rulesPeriodDetails.length; i++) {
			$(monthCheckBox[(rulesPeriodDetails[i]-1)]).prop("checked","true");
			$(monthCheckBox[(rulesPeriodDetails[i]-1)]).parent().addClass("selectStyle");
		}
	}
	 </script>
	</body>
</html>
