<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>日志</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/integral/integral.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  <style>
	  table.myTable td {
		    vertical-align: middle;
		    font-size: 0.7rem;
		    text-overflow: ellipsis;
		    text-align: center;
		    white-space: nowrap;
		    padding: 10px;
		    overflow: hidden;
		}
  </style>
  <body>
  	 <div class="integral_margin">
    	<div class="header_roomlist">
			<ul>
		    <c:if test="${param.type == '1'}">
                <li class="header_roomlist_li active" index="0">
					<span>登录日志</span>
				</li>
				<li class="header_roomlist_li" index="1">
					<span>交接班日志</span>
				</li>
				<li class="header_roomlist_li" index="2">
					<span>销售日志</span>
				</li>	
				<li class="header_roomlist_li" index="3">
					<span>操作日志</span>
				</li>
				<li class="header_roomlist_li" index="4">
					<span>房态操作日志</span>
				</li>	
				<li class="header_roomlist_li" index="5">
					<span>历史操作日志</span>
				</li>	
			</c:if>
			 <c:if test="${param.type == '2'}">
			    <li class="header_roomlist_li active" index="0">
					<span>登录日志</span>
				</li>
				<li class="header_roomlist_li" index="3">
					<span>操作日志</span>
				</li>
				<li class="header_roomlist_li" index="5">
					<span>历史操作日志</span>
				</li>
				<li class="header_roomlist_li" index="6">
					<span>换房日志</span>
				</li>
				<li class="header_roomlist_li" index="7">
					<span>门锁日志</span>
				</li>
			</c:if>
			<c:if test="${param.type == '3'}">
				<li class="header_roomlist_li active"  index="3">
					<span>操作日志</span>
				</li>
				<li class="header_roomlist_li" index="5">
					<span>历史操作日志</span>
				</li>
				<li class="header_roomlist_li" index="7">
					<span>门锁日志</span>
				</li>
			</c:if>
				
				
			</ul>
			<div class="fr">
				<span class="highsearch">高级查询</span>
			</div>
			<div class="high_header inte-padding fadeInDown">
				<h3>高级查询</h3>
				<i class="imooc imooc_log" style="color:#fff;">&#xe900;</i>
				<form name="condition" id="condition" action="LoginLog.do" method="post" target="logFrame">
				<input id="type" name="type" type="hidden" value='<%=request.getParameter("type")%>'>
					<div class="margintop">
						<ul class="clearfix">
						  <li>
							<label class="label_name in_select">日志选择</label>
							<select id="selectAge" class="text_search" disabled='disabled'>
							    <option value="0">登录日志</option>
							    <option value="1">交接班日志</option>
						        <option value="2">销售日志</option>
						        <option value="3">操作日志</option>
					         	<option value="4">房态操作日志</option>
					         	<option value="5">历史操作日志</option>
						    </select>
						 </li>
						</ul>
						<ul id="loginfo" class="clearfix">
							<li><label class="label_name">登录人</label><input name="logname" id="logname" type="text" value="" id="id" class="text_search"></li>
					      	<li>
					      		<label class="label_name">登录端</label>
					      		<select name="source" id="source" class="text_search">
					      			<option value="">--请选择--</option>
								    <option value="1">app</option>
								    <option value="2">网站</option>
							        <option value="3">分店</option>
							        <option value="4">wap</option>
							    </select>
					      	</li>
					      	<li>
					      		<label class="label_name">登录状态</label>
					      		<select name="status" id="status" class="text_search">
					      			<option value="">--请选择--</option>
								    <option value="1">成功</option>
								    <option value="0">失败</option>
							    </select>
					      	</li>
						</ul>
					</div>
					<div class="time_div">
						<ul class="clearfix">
							<li>
								<label class="label_name">开始时间</label>
								<input id="starttime" name="starttime" type="text" value="" class="text_search check date">
							</li>
							<li>
								<label class="label_name">结束时间</label>
								<input id="endtime" name="endtime" type="text" value="" class="text_search check date">
							</li>
						</ul>
					</div>
					<div>
						<button type="button" class="btn_style button_margin reset" onclick="resetform()">重置</button>	
						<button type="button" class="btn_style button_margin" onclick="submitFrom()">查询</button>
					</div>
				</form>
			</div>
		<section class="box-content-section fl" style="width:100%;">
			<section class="box_content_widget fl">
				<div id="integral" class="content" style="height:889px;">
				 <c:if test="${param.type == '1' || param.type == '2'}">
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="LoginLog.do?type=<%=request.getParameter("type")%>"></iframe>
				 </c:if>
				 <c:if test="${param.type == '3'}">
					<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="operateLog.do?type=<%=request.getParameter("type")%>"></iframe>
				 </c:if>
				
				</div>
			</section>
		</section>
	 </div>
	</div>
    <%@ include file="../../common/script.jsp"%>
    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/integral/integral.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>

	<script>
	var base_path = "<%=request.getContextPath()%>";
	var type = "<%=request.getParameter("type")%>";
	
	</script>
  </body>
</html>
