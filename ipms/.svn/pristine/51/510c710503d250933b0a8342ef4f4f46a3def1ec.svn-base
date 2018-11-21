<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>同城房态</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/jquery-easyui/themes/default/easyui.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/jquery-easyui/themes/icon.css" />	
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/loginfile/box_popup.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/roomlist/room_urban.css" />
	<link rel="stylesheet" id="style" type="text/css"
		href="<%=request.getContextPath()%>/css/ipms/css/roomlist/jquery-labelauty.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	
	<style>
	ul { list-style-type: none;}
	li { display: inline-block;}
	li { margin: 10px 0;}
	input.labelauty + label { font: 14px "Microsoft Yahei"; margin-bottom: 0;}
	.room_margin input.labelauty + label > span.labelauty-unchecked, input.labelauty + label > span.labelauty-checked{
		width: auto;
	}
	.room_margin input.labelauty:checked + label > span.labelauty-checked{
		width: auto;
	}
	.left_area label{
		height: 35px;
	    width: 63px;
	    text-align: center;
	    margin-left: 18px;
	}
	.left_area label span{
		line-height:14px !important;
	}
	</style>
  </head>
  
  <body>
   <div class="room_margin"> 
  	  <div class="header_search" style="padding-left: 21px;">
  		<!-- <label class="label_names">营业日期</label>
  		<input class="date_txt"  type="text" id="ydate"  style="width:150px;margin-left:10px;"> -->
  		<i class="fa fa-calendar-check-o"></i>
		<label>分店选择</label>
		<input class="urbanone" placeholder="请选择分店" style="width: 150px;height:30px;margin-left:4px;padding-left:5px;"/>
	  </div>
		<form id="cityroom" action="cityroomstatus.do" method="post" target="logFrame">
		<!--  分店名称搜索-->
			<div class="info_show fadeInDown" style="display:none;">
				<div class="clearfix">
					<div class="left_area  fl">
						<%--<input type="text" placeholder="搜索分店名称" class="urban">
						--%><input type="checkbox" class="btn_style button_margin" name="checkbox" data-labelauty="全选" 
							id="all" value="">
						<button type="button" class="btn_style button_margin close" onclick="cancelFrom()">取消</button>
						<button type="button" class="btn_style button_margin" onclick="submitFrom()">查询</button>
						
					</div>
				</div>
				<div class="top_area">
					<ul class="dowebok" id="list">
						<c:forEach items="${roomName }" var="item">
							<li><input type="checkbox" name="checkbox" data-labelauty="${item.key }" value="${item.value }"></li>
						</c:forEach>
					</ul>
					<input id="branchId" name="branchId" type="hidden" value=""/>
				</div>
			</div>	
  		</form>
 	<section class="box-content-section fl" style="width:100%;">
		<section class="box_content_widget fl">
			<div id="integral" class="content" style="height:900px;padding:10px;">
				<iframe name="logFrame" id="logFrame" frameborder="0" width="100%" height="100%" src="cityroomstatus.do"></iframe>
			</div>
		</section>
	</section>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/room_urban/jquery-labelauty.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		/* laydate({
        	elem: '#ydate'
		}); */
		$(function(){
			$(".urbanone").focus(function(){
		 		$(".info_show").css("display","block");
			});
			$(".close").on("click",function(){
				$(".info_show").fadeOut(500);
			});
		})
		function submitFrom(){
			$(".info_show").css("display","none");
			var branchId = "";
			$("input:checkbox[name='checkbox']:checked").each(function() {
				branchId += $(this).val() + " ";
			});
			$("#branchId").val(branchId);
			$("#cityroom").submit();
		}
		</script>
		<script>
		$(function(){
			$(':input').labelauty();
		});
		$(function(){
			$("#all").click(function(){   
			    if(this.checked){
			        $("#list input:checkbox").prop("checked", true);  
			    }else{   
					$("#list :checkbox").prop("checked", false);
			    }   
			});
		})
		
		function cancelFrom() {
			$(".info_show").css("display","none");
		}
		</script>
  </body>
</html>



