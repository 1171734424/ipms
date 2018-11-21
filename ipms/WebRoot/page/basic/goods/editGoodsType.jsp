<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>添加商品分类</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<style type="">
.crm-table tr td:nth-child(2n-1) {
	width: 15% !important;
}
</style>
<link href="<%=request.getContextPath()%>/css/common/tipInfo.css"
	rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/common/datepicker.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/pmsmanage/pmsmanage.css" />
<link href="<%=request.getContextPath()%>/css/reset.css"
	rel="stylesheet" media="all" />
<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<script> 
	var base_path = '<%=request.getContextPath()%>';
	</script>
</head>
<body class="room-body">
	<form name="myFrom" id="myForm" class="ctdform">
		<table class="ptable crm-table" border="0" cellspacing="0"
			cellpadding="0">
			<tbody>
				<tr>
					<td><input id="categoryId" name="categoryId" type="hidden" value="${categoryId}"/></td>
				</tr>
				
				<!--  <col width="13%">
		     <col width="80%">
		     <col width="2%">
		     <col width="2%"> -->
				<tr>
					<td class="ctdtitle notnull" align="center">商品种类</td>
					<td align="left" class="pinputtd"><input type="text"
						id="categoryName" class="ptdinput" maxlength="5" value="${categoryName }"/></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
				<tr>
					<td class="ctdtitle notnull" align="center;">门店</td>
					<td align="left" class="pinputtd"><input
						id="branch_select_CUSTOM_VALUE" name="branch_select_CUSTOM_VALUE"
						class="ptdinput" type="hidden" value="${gc.branchId }" /> <input
						id="branch_select" name="branch_select" class="ptdinput"
						type="text" value="${branchId }" disabled="disabled" /></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
				<tr>
					<td class="ctdtitle notnull" align="center;">是否自营</td>
					<td align="left" class="pinputtd"><select id="chargeRoom"
						name="chargeRoom" class="ptdinput" disabled="disabled">
						<c:if test="${gc.chargeRoom eq '1' }">
							<option value="1" selected="selected">自营</option>
							<option value="0">非自营</option>
						</c:if>
						<c:if test="${gc.chargeRoom eq '0' }">
							<option value="1">自营</option>
							<option value="0" selected="selected">非自营</option>
						</c:if>
					</select></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
				<tr>
					<td class="ctdtitle notnull" align="center;">商品大类</td>
					<td align="left" class="pinputtd"><input
						id="superCategory_select_CUSTOM_VALUE"
						name="superCategory_select_CUSTOM_VALUE" class="ptdinput"
						type="hidden" value="${gc.superCategory }" /> <input id="superCategory_select"
						name="superCategory_select" class="ptdinput" type="text" value="${superCategory }"
						readonly="true" /></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
				<tr>
					<td class="ctdtitle notnull" align="center;">状态</td>
					<td align="left" class="pinputtd"><select id="status"
						name="status" class="ptdinput">
							<c:if test="${gc.status eq '1' }">
								<option value="1" selected="selected">有效</option>
								<option value="0">删除</option>
							</c:if>
							<c:if test="${gc.status eq '0' }">
								<option value="1">有效</option>
								<option value="0" selected="selected">删除</option>
							</c:if>
					</select></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
				<tr>
					<td class="ctdtitle" align="center;">备注</td>
					<td align="left" class="pinputtd"><input type="text"
						id="rpremark" class="ptdinput" maxlength="100" value="${gc.remark }"/></td>
					<td class="ctdtitle"></td>
					<td class="ctdtitle"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<hr class="ctdhr" />
	<div class="contractbutton parents-content">
		<div class="pdepartsubmit crm-confirmbt" onclick="goodsTypeAdd()">提交</div>
		<div class="pdepartcancle crm-cancelbt"
			onclick="window.parent.JDialog.close()">取消</div>
	</div>
</body>
<script type="text/javascript">
	

	// 初始化加载页面
	$(document).ready(function() {
		
		// 初始化加载门店弹出框选择
		$("#branch_select").bind("click",function(){
		 	JDialog.open("门店", base_path + "/selectGoodsBranch.do?",450,350);
		 	 
		});
		
		// 初始化加载商品大类弹出框选择
		$("#superCategory_select").bind("click",function(){
		 	JDialog.open("商品大类", base_path + "/selectGoodsType.do?",450,350);
		 	 
		});
	});
	
	/*
	 * 提交方法 
	 */
	function goodsTypeAdd() {
		// 获取所有参数
		var categoryName = $("#categoryName").val();
		var branchId = $("#branch_select_CUSTOM_VALUE").val();
		var chargeRoom = $("#chargeRoom").val();
		var superCategory = $("#superCategory_select_CUSTOM_VALUE").val();
		var status = $("#status").val();
		var remark = $("#rpremark").val();
		var categoryId = $("#categoryId").val();
		// 验证所有数据
		if (categoryName.length < 2) {
			showMsg("商品种类字符长度不能小于2个字符！");
			return false;
		}
		if (branchId.length < 0) {
			showMsg("门店不能为空!");
			return false;
		}
		if (superCategory.length < 0) {
			showMsg("商品大类不能为空!");
			return false;
		}
		
		$.ajax({
			url : base_path + "/editSaveCrmGoodsType.do",
			type : "post",
			dataType : "json",
			data : {
				'categoryName' : categoryName,
				'branchId' : branchId,
				'chargeRoom' : chargeRoom,
				'superCategory' : superCategory,
				'status' : status,
				'remark' : remark,
				'categoryId' : categoryId
			},
			success : function(json) {
				if (1 == json.result) {
						showMsg("修改成功！");
						window.setTimeout("window.parent.location.reload(true)", 2000);
						window.setTimeout("window.parent.JDialog.close();",2000);
				} else {
					showMsg("修改失败！");
					window.setTimeout("window.parent.location.reload(true)",2000);
					window.setTimeout("window.parent.JDialog.close();",2000);
				}
			}
		});
	}
	// 提示框
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
</script>
</html>
