<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>房价规则新增</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
	<style>
	.inputStyle {
		width: 280px;
	}
	#preLocation {
		width: 280px;
	}
	#aftLocation {
		width: 40px;
	}
	#remarkInput {
		height: 84px;
		width: 97%;
	}
	#keyword {
		height: 44px;
		width: 97%;
	}
	td.must {
		color: red;
	}
	#rulesDesc {
		width: 680px;
    	height: 58px;
	}
	.parents-content {
	    /* width: 100%;
	    text-align: center;
	    margin: 210px auto; */
	    
	    position: absolute;
	    width: 100%;
	    text-align: center;
	    margin: 160px auto;
	}
	</style>
  </head>
<body>
	<table class="branch_table crm-table">
		<tr>
			<td class="must">规则名称</td>
			<td><input class="inputStyle" id="rulesName" maxlength="25" placeholder="不能超过25位字符" /></td>
			<td class="must">周期类型</td>
			<td>
				<select class="inputStyle" id="rulesPeriod" onchange="selectRulesPeriod()">
					<option value="">--请选择--</option>
					<!-- <option value="1">时</option> -->
					<option value="2">日</option>
					<option value="3" selected="selected">周</option>
					<option value="4">月</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="must">周期详情</td>
			<td><input class="inputStyle" id="rulesPeriodDetails" placeholder="周期详情" onclick="forWord()"/></td>
			<td class="must">过滤规则</td>
			<td>
				<select class="inputStyle" id="rulesFilters">
					<option value="">--请选择--</option>
					<option value="1">生效</option>
					<option value="2">排除</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="must">规则描述</td>
			<td colspan="3"><input class="inputStyle rulesDesc" id="rulesDesc" placeholder="规则描述不能大于150位字符！" /></td>
		</tr>
	</table>
	<div class="contractbutton parents-content">
		<div class="pdepartsubmit crm-confirmbt" onclick="submitPriceRule()">提交</div>
		<div class="pdepartcancle crm-cancelbt"
			onclick="window.parent.JDialog.close()">取消</div>
	</div>
	<input type="hidden" name="latlng" value="" class="mid_tet" readonly />
	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
</body>
<script>
 	// 获取项目路径
	var base_path = '<%=request.getContextPath()%>';

	// 条用时间插件
	$(function() {
		
		laydate.render({
			elem: '#startTime',
			type: 'datetime',
			format: 'yyyy/MM/dd HH:mm:ss',
			theme: '#4A5064'
		});

		laydate.render({
			elem: '#endTime',
			type: 'datetime',
			format: 'yyyy/MM/dd HH:mm:ss',
			theme: '#4A5064'
		});
	});
	
	/*
	 * 选择周期类型后绑定周期详情事件
	 */ 
	function selectRulesPeriod() {
		// 获取参数
		var rulesPeriod = $("#rulesPeriod").val();
		// 清空周期详情数据
		$("#rulesPeriodDetails").val("");
		// 清空周期详情事件
		$("#rulesPeriodDetails").attr("onclick","");
		// 当周期类型不为空时 绑定事件
		if (rulesPeriod != "") {
			$("#rulesPeriodDetails").attr("onclick","forWord()");
		} 
	}
	
	/*
	 * 跳转周期详情事件页面
	 */ 
	function forWord(){
		// 获取参数
		var rulesPeriod = $("#rulesPeriod").val();
		var rulesPeriodDetails = $("#rulesPeriodDetails").val();
		// 跳转周期详情页面并将参数传到子页面
		window.JDialog.open("选择周期详情", base_path + "/page/basic/branch/selectWeekRule.jsp?rulesPeriod=" + rulesPeriod + "&rulesPeriodDetails=" + rulesPeriodDetails , 600,250);
	}
	
	/*
	 * 提交房价规则数据
	 */ 
	function submitPriceRule() {
		// 获取参数
		var rulesName = $("#rulesName").val();
		var rulesPeriod = $("#rulesPeriod").val();
		var rulesPeriodDetails = $("#rulesPeriodDetails").val();
		var rulesFilters = $("#rulesFilters").val();
		var rulesDesc = $("#rulesDesc").val();
		// 验证参数的有效性
		if (rulesName.length > 25) {
			showMsg("规则名称不能大于25位字符！");
			return false;
		}
		if (rulesDesc.length > 150) {
			showMsg("规则描述不能大于150位字符！");
			return false;
		}
		// 定义参数数据用于循环验证数据格式
		var validate = [rulesName,rulesPeriod,rulesPeriodDetails,rulesFilters,rulesDesc];
		var descs = ["规则名称为必填项！","周期类型为必填项！","周期详情为必填项！","过滤规则为必填项！","规则描述为必填项！"];
		var flag = true;
		$(validate).each(function(i){
			var a = isNull(validate[i],descs[i]);
			if (!a) {
				flag = a;
			}
		});
		if (!flag) {
			return flag;
		}
		
		// 使用异步提交
		$.ajax({
		   type: "POST",
		   url: base_path + "/savePriceRule.do",
		   dataType : "json",
		   data: {rulesName:rulesName,
			   	  rulesPeriod:rulesPeriod,
			   	  rulesPeriodDetails:rulesPeriodDetails,
			   	  rulesFilters:rulesFilters,
			   	  rulesDesc:rulesDesc},
		   success: function(json){
		   		if (json.result == 1) {
		   			showMsg("添加成功！");
					window.setTimeout("window.parent.location.reload(true)",1000);
					window.setTimeout("window.parent.JDialog.close();", 1000);
				} else if (json.result == -1) {
		   			showMsg("添加失败！");
				}
		   }
		});
	}
	
	/*
	 * 为空验证公共方法
	 */
	function isNull(parame,desc) {
		if (parame == "") {
			showMsg(desc);
			return false;
		}
		return true;
	}
	
	/*
	 * showMsg弹出框事件
	 */
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
</script>
</html>
