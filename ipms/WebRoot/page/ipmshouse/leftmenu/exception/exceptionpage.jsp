<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("exceptionType", request.getAttribute("exceptionType"));  
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>异常页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
 <body class="body_exception">
 	 <div class="search_margin">
 	 	 <h3>日结异常</h3>
 	 	 <span class="close_span"><i class="imooc imooc_ex" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe900;</i></span>
     	 <table class="ostable_exception">
				<tr>
					<td align="right" style="width:37px">
						<span>开始日期</span>
					</td>
					<td>
						<input id="exbegintime" name="exbegintime" class="osinput date"
							type="text" value="" />
					</td>
					<td align="right" style="width:37px">
						<span>结束日期</span>
					</td>
					<td>
						<input id="exendtime" name="exendtime" class="osinput date"
							type="text" value="" />
					</td>
					<td align="right" style="width:37px">
						<span>异常类型</span>
					</td>
					<td>
						<select id="exceptiontype" name="exceptiontype" class="check five"
							onchange="">
							<option value="">
								全部类型
							</option>
							<c:forEach var="et" items="${exceptionType}">	
								<option value="${et.content}">
									${et.paramdesc}
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="right" style="width:37px">
						<span>异常状态</span>
					</td>
					<td>
						<select id="exceptionstatus" name="exceptionstatus" class="check five" onchange="">
							<option value="">
								所有状态
							</option>
							<option value="0">
								未处理
							</option>
							<option value="1">
								已处理
							</option>
						</select>
					</td>
					<td>
						<input id="" name="" class="osearch" type="button" onclick="exceptiondata()" value="查询" />
					</td>
				</tr>
			</table>
		</div>
		<div style="width:100%;height:100%;">
		<iframe name="" id="exceptionIframe" frameborder="0" width="100%" height="100%" src="exceptionData.do"></iframe></div>
		<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/exception/exceptionpage.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
