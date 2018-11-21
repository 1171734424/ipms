 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员注册</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
    <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="register_margin"><!--
     <i class="imooc imooc_order_gdspage" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe902;</i>-->
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
			  <li><label class="label_name red">姓名</label><input id="omembername" name="omembername" class="text_check" type="text" maxlength="10"/></li>
			  <li><label class="label_name red">登录名</label><input id="ologinname" name="ologinname" class="text_check" type="text" maxlength="10"/></li>
			  <li><label class="label_name red">性别</label>
			  	<select id="ogendor" name="ogendor" class="select_omadd">
	                <option  value="0">女</option>  
                    <option  value="1">男 </option> 
                </select>	
              </li>
              <li><label class="label_name red">身份证</label><input id="oidcard" name="oidcard" class="text_check" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="18"/></li>
			  <li><label class="label_name red">手机号</label><input id="omobile" name="omobile" class="text_check" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="11"/></li>
			  <li><label class="label_name">出生日期</label>
			 	<input id="obirthday" name="obirthday" class="text_check date" type="text"/>
			 	<i class="fa fa-calendar-check-o"></i>
			  </li>
			  <li><label class="label_name">Email</label><input id="oemail" name="oemail" class="text_check" type="text" maxlength="20"/></li>
		      <li><label class="label_name">邮编</label><input id="opostcode" name="opostcode" class="text_check" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6"/></li>
			  <li><label class="label_name">地址</label><textarea id="oaddress" name="oaddress" class="text_check text_address" type="text" maxlength="30"/></textarea></li>
			  <li><label class="label_name">备注</label><textarea id="oremark" name="oremark" class="text_check text_mark" type="text" maxlength="100"/></textarea></li>
			  <li><input type="button" class="button_margin button_submit" onclick="ordernewmember()" value="注册会员"></li>
			  </ul>
		   </section> 
		   <span class="warnning_om">注：红色字体为必填项</span>  
	 </form> 	    
          
   </div>
   	<script>
   		
   	</script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/apartment/apartmentmainmenu/apartmentcontract/ordermemberadd.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
