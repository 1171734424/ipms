<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>工作帐修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/work_account/work_account_check.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
  </head>
  <body>
   <div class="get_color updatework">
		<form id="">
			<section class="work_one">
				<ul class="clearfix">
					<li>
						<label class="label_name">名称</label> <input id="getcontinuetime" name="getcontinuetime" type="text" value="" class="text_update">
					</li>
					<li>
						<label class="label_name">创建者：</label><input id="project" name="project" type="text" value="" class="text_update" onclick="oncontinue()">
						<div id="ontime" class="ontime oncontinue fadeIn"></div>
					</li>
					<li><label class="label_name">创建日期：</label> <input id="amount" name="amount" type="number" value="" class="text_update" ></li>
					<li><label class="label_name">结账日期：</label> <input name="预定人会员类型" type="text" value="" class="text_update" ></li>
					<li><label class="label_name">结账者：</label> <input name="预定人会员类型" type="text" value="" class="text_update" ></li>
					<li><label class="label_name">名称：</label> <input name="预定人会员类型" type="text" value="" class="text_update" ></li>
					<li><label class="label_name">说明：</label> <textarea name="预定人会员类型" type="text" value="" class="text_update" ></li>
					<li><label class="label_name">备注：</label> <textarea name="预定人会员类型" type="text" value="" class="text_update" ></li>
					<li>
						<input type="button" id="submitbill" value="取消" onclick="closeWin()" class="button_margin cancel"/>
						<input type="button" id="submitbill" value="确认" onclick="submitgetcontinuebill()" class="button_margin confirm"/>
					</li>	
				</ul>
			</section>
		 	<input type="hidden" name="checkId" value="${checkid }">
		</form>
	 </div>
	<script src="<%=request.getContextPath()%>/script/common/newtipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/util.js"></script>
  	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  	<script type="text/javascript">
  		
  	</script>
  </body>
</html>
