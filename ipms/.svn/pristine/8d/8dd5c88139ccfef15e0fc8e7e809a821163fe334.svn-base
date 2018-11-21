<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("REGISTERDATE", request.getAttribute("time"));
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>会员详情</title>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<link rel="stylesheet" type="text/css" href="${basePath}/css/common/ui.jqgrid.css"/>
<script src="${basePath}/script/common/jquery.jqGrid.src.js"></script>
<script src="${basePath}/script/common/grid.locale-cn.js"></script>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
<body>
	<table id="querymemberinfo"></table>
   	<div id="pager"></div>
   	<div class="dialogButton" style="margin:8px auto" onclick="closed()">关 闭</div>
	<script>
		
		var registerDate = "${REGISTERDATE}";
		jQuery(document).ready(function() {

			jQuery("#querymemberinfo").jqGrid({
				url: "memberinfo2.do?registerDate="+registerDate,
				datatype: "json",
			   	colNames: setGridCols(),
			   	colModel: setGridModels(),
			   	width: 1225,
			   	height: 295,
			   	rowNum: 20,
			   	rowList: [20,50,100],
			   	pager: "#pager",
			   	forceFit: true,
			   	cellsubmit: "clientArray",
			   	sortname: "id",
			    viewrecords: true,
			    sortorder: "desc",
			    altRows: true,
				altclass: "ui-state-focus"
			});
		});
		
	function setGridCols() {

		return ['注册时间','会员姓名','登录名','会员等级','身份证','性别','手机号','邮箱','地址'];
		
	}
	
	function setGridModels() {
	
		return [{name: 'REGISTERTIME', index: 'REGISTERTIME', width:120, align:"center" },
		        {name: 'MEMBERNAME', index: 'MEMBERNAME', width:100, align:"center" },
		        {name: 'LOGINNAME', index:'LOGINNAME' ,width:80, align:"center" },
		        {name: 'RANKNAME', index:'RANKNAME' ,width:80, align:"center" },
		        {name: 'IDCARD', index:'IDCARD' ,width:160, align:"center" },
		        {name: 'GENDOR', index:'GENDOR' ,width:60, align:"center" },
		        {name: 'MOBILE', index:'MOBILE' ,width:120, align:"center" },
		        {name: 'EMAIL', index:'EMAIL' ,width:140, align:"left" },
		        {name: 'ADDRESS', index:'ADDRESS' ,width:200, align:"center" }]
		   
		  
	}
	
	function closed(){

		window.parent.JDialog.close();
	}
	</script>
</body>
</html>