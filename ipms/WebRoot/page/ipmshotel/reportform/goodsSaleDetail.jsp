<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reportform/report_forms.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/css/style.css" />
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
				<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
		<title>冲减明细报表</title>
	</head>
	<body style="overflow:hidden;">   
		<div class="shop_search">
			<form action="hotelgoodsSaleDetail.do" method="post" id="myForm">
			   <div class="form_margin">
					<input type="text" id="startTime" name="startTime" class="date shop_name" value="${startTime}" placeholder="开始时间"/>
					<input type="text" id="endTime" name="endTime" class="date shop_name" value="${endTime}" placeholder="结束时间"/>
					<input type="text" name="recordUser" id="recordUser" class="shop_name" value="${recordUser}" placeholder="操作人" />
					<input type="text" name="goodsName" id="goodsName" class="shop_name" value="${goodsName}" placeholder="商品名称" />
						<select name="goodsKind" id="goodsKind" class="search_select">
						<option value="">商品类型</option>
						<c:forEach items="${goodsType}" var="rl">
							<option value="${rl.GOODSTYPEID}">
								${rl.GOODSTYPENAME}
							</option>
						</c:forEach>
					</select>
						<button type="button" class="btn_style btn_search button_margin" onclick="search();">
							查询
						</button>
			   </div>
				<section class="box-content-section fl">
				<section class="box_content_widget fl">
				<div class="content">
					<iframe name="frame" id="frame" class="myTable" frameborder="0" width="100%" height="100%" ></iframe>
				</div>
				<div id="pager"></div>
				</section>
				</section>
			</form>
		</div>
		<%@ include file="../../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/reportforms/goodsSaleDetail.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var base_path = '<%=request.getContextPath()%>';
			Pager.renderPager(<%=pagination%>);
			$(document).ready(function(){
			//alert("startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&recordUser="+$("#recordUser").val())
				$("#frame").attr('src',"hotelgoodsSaleDetailData.do?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&recordUser="+$("#recordUser").val());
		    })
	 	</script>
	</body>
</html>