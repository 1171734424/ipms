<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
  </head>
  <body>
  <div class="register_margin">
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
			  <li><label class="label_name">姓名</label><input id="memberName" name="memberName" class="input_ms" type="text"/></li>
			  <li><label class="label_name">性别</label>
			  	<select id="gendor" name="gendor" class="input_ms text_select">
	                <option  value="0">女</option>
                    <option  value="1">男 </option>
                </select>
              </li>
			  <li><label class="label_name red">手机号</label><input id="mobile" name="mobile" class="input_ms" type="text"/></li>
			  </ul>
		   </section> 
		 </form>
       <div class="editdata_ms">
          <input type="button" class="button_margin ms_re" onclick="submitMember()" value="注册会员">
       </div>
   </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		$(function (){
		})
		
		function submitMember(){
			var msg = "";
			if(isNull($("#memberName"), "姓名：")){
				return false;
			}
			if(isNull($("#mobile"), "手机号：")){
				return false;
			}
			if(!isMobile($("#mobile").val())){
				msg = "不是有效的手机号!"
				showMsg(msg);
				return false;
			}
			
			$.ajax({
		         url: path + "/addmember.do",
				 type: "post",
				 data : $("#omemberadd").serialize(),
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		$(window.parent["document"]["all"]["customer"]["childNodes"][0]["contentDocument"]).find("#searchmembercondotion").val(json.data);
				 		setTimeout("setval()", 2000);

				 	}else if(json.result == 0){
				 		window.showMsg(json.message);
				 	}
				 },
				 error: function(json) {}
			});			
		}
		
		function setval(){
	 		window.parent.JDialog.close();
		}
		
		
		
	</script>
  </body>
</html>
