<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>设置可保洁数</title>
    <link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
    <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style>
		.register_margin .register_one .label_name {
		    float: left;
		    width: 151px;
		    text-align: right;
		    font-weight: normal;
		    font-size: 0.85rem;
		}
		
		.editdata_ms .ms_re {
	    float: right;
	    margin: 29px 190px;
	    color: #fff;
	    background: #FC8A15;
	    border: none;
	    }
	</style>
  </head>
  <body>
     <div class="register_margin keep_clean">
       <input type="hidden" id="time" name="time" value="<%=request.getParameter("time")%>"/>
        <input type="hidden" id="timearea" name="timearea" value="<%=request.getParameter("timearea")%>"/>
       <section class="register_one ">
           <ul class="clearfix">
<!--				<li><label class="label_name">保洁日期：</label><input id="date" name="date" type="text" class="input_ms" value="" /></li>-->
<!--				<li><label class="label_name">时段：</label>-->
<!--					<select id="timeArea" name="timeArea" class="input_ms">-->
<!--				        <option  value="0">上午</option> -->
<!--		                <option  value="1">下午</option>  -->
<!--		            </select>-->
<!--      	   		 </li>-->
				<li style="margin: 28px 61px;"><label class="label_name" >设置当前时间可保洁数：</label><input id="restamount" name="restamount" type="text" class="input_ms" value="" >
				</li>
	       </ul>
        </section> 
      	<div class="editdata_ms">
	      <input class="button_margin ms_re" type="button" value="确定" onclick="setcleanrestAmount()"/>  
<!--	      <input class="button_margin ms_re" type="button" value="取消" onclick="window.parent.JDialog.close()"/>-->
	  	</div>
    </div>

    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/clean/applydata.js"></script>	
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
    <script>
    var path ="<%=request.getContextPath()%>";
			laydate({
	        	elem: '#date'
    		});
    </script>
  </body>
</html>
