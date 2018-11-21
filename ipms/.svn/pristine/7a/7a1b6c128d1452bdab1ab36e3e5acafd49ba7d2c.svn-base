<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));
	request.setAttribute("categoryCondition", request.getAttribute("categoryCondition")); 		
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>保洁-新增申请</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<style>	
 .search_select2{
    border: 1px solid #e0e0e0;
    border-radius: 4px;
    width: 89px;
    height: 30px;
    margin-left: 10px;
 
 }
	</style>
  </head>
  <body style="overflow:hidden;">
    <div class="check_wrap_margin load_margin">
		<div class="shop_content  margin_gdspage clean_record">
			<h2>保洁申请</h2>
			<div class="shop_search" style="height:625px;">
				<ul>
			 	  <li>
					<label class="label_name">申请日期：</label>
					<input type="text" id="applystarttime" name="applystarttime" class="date_text text_search" value="" onblur="" />
				  </li>
				  <li>
					<label class="label_name label_name_y" >--</label>
					<input type="text" id="applyendtime" name="applyendtime" class="date_text text_search" value=""  />
				  </li>
				  <li>
					<label class="label_name">保洁日期：</label>
					<input type="text" id="cleanstarttime" name="cleanstarttime" class="date_text text_search" value="" onblur="" />
					</li>
					<li>
					<label class="label_name label_name_y">--</label>
					<input type="text" id="cleanendtime" name="cleanendtime" class="date_text text_search" value=""  />
					</li>
					<li>
					<select id="timeArea" name="timeArea" class="search_select2">
					        <option  value="">时段</option> 
					        <option  value="0">上午</option> 
                            <option  value="1">下午</option>  
					</select>
					</li>
					<li>
					<select id="status" name="status" class="search_select2">
					        <option  value="">处理状态</option> 
					        <option  value="1">未处理</option> 
                            <option  value="2">已处理</option>  
                            <option  value="0">已撤销</option>  
					</select>
					</li>
					<li>
					<input type="button" name="" id="" class="gdsbutton_clean" value="查询" onclick="search()"/>
					</li>
					<li>				
				 <input type="button" name="" id="" class="gdsbutton_clean  button_add" value="新增申请" onclick="addnewapply()"/>
				</li>
				<!--<input type="button" name="" id="" class="setbutton manysale" value="设置可预约数量" onclick="setAmount()"/>-->
				  
				</ul>
				<div class="div_iframe" style="height: 589px; width: 1049px;">
					<iframe name="applicationIframe" id="applicationIframe" frameborder="0" width="100%" height="100%" src="queryApartCleanapply.do"></iframe>
				</div>
			</div>
		</div>
	</div>
<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
		<script src="<%=request.getContextPath()%>/script/apartment/js/index.js" type="text/javascript" charset="utf-8"></script>
<!--	    <script src="<%=request.getContextPath()%>/script/ipms/js/room_urban/jquery-labelauty.js"></script>-->
	    <script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
	    <script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentclean/applydata.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		 var base_path ="<%=request.getContextPath()%>";
		 	laydate({
	        	elem: '#cleanstarttime'
	        	
    		});
    		laydate({
	        	elem: '#cleanendtime'
	        	
    		});
    		laydate({
	        	elem: '#applystarttime'
	        	
    		});
    		laydate({
	        	elem: '#applyendtime'
	        	
    		});
	
		function search(){
			var cleanstarttime = $("#cleanstarttime").val();
			var cleanendtime = $("#cleanendtime").val();
			var applystarttime = $("#applystarttime").val();
			var applyendtime = $("#applyendtime").val();
			var timeArea = $("#timeArea").val();
			var status = $("#status").val();
			$("#applicationIframe").attr("src",base_path+"/querycleanapplyApart.do?cleanstarttime="+cleanstarttime+"&cleanendtime="+cleanendtime+"&timeArea="+timeArea+"&status="+status+"&applystarttime="+applystarttime+"&applyendtime="+applyendtime);
		}
		
		function addnewapply(){     
			JDialog.open("添加保洁申请",base_path +"/addApartNewApply.do",690,370);
		}
		
		</script>
  </body>
</html>
