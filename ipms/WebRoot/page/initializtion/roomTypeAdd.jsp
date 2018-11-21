<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/css.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>房型录入</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<style>
		.edit-cell.ui-state-highlight select{
		margin:auto;
		width:90%;
		}
		</style>
	</head>
	<body>
		<div class="well" style="height:540px;">
		    <input type="hidden" id="branchId" name="branchId" value="${branchId}"/>
			<div style="height: 50px;">
               	<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="add" name="add" class="required btn btn-primary" value="新增" onclick="addNewRow();">
					</div>
				</div>
				<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="delete" name="delete" class="required btn btn-danger" value="删除" onclick="deleteRow();">
					</div>
				</div>
				<div class="control-group addFloatStyle">
					<div class="controls">
						<input type="button" id="save" name="save" class="required btn btn-success" value="保存" onclick="saveData();">
					</div>
				</div>
				<div class="control-group addFloatStyle" style = "margin-left: 42px;">
					<div class="controls">
						<input type="button" id="initial" name="initial" class="required btn btn-info" value="房价初始化" onclick="rpInitialByRoomType();">
					</div>
				</div>
			</div>
			<form id = "myform" name = "myform">
               	<table id="dataGrid"></table> 
            </form>
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/grid.locale-cn.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/initialPage.js"></script>
		<script type="text/javascript">
			var base_path = "<%=request.getContextPath()%>";
			var newDataFlag = '${newDataFlag}';
			var branchId = '${branchId}';
			var branchType = '${branchType}';
			var recordUser = '${recordUser}';
			var newObjString = "";
			var firstEditCell = -1, checked_row = new Array() , data_changed = new Array(),data_newAdd = new Array(),required_data = new Object(),
				delete_tag = "DELETE",save_tag = "SAVE",validate_tag = "VALIDATE";
			$(document).ready(function() {
				pageInit();
			});
		</script>
	</body>
</html>

