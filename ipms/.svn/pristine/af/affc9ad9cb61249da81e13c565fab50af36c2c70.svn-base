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
    <title>安排保洁人员</title>
   <link rel="stylesheet"   href="<%=request.getContextPath()%>/css/ipms/css/roomlist/customer.css" />
	<link rel="stylesheet" id="style" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist_check.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
  </head>
  <style>
  .clearfix  li{
       float: left;
   }
   .table_textarea{
      width: 718px;
      margin: 9px;
     /* height: 60px;*/
    }
    .clearfix .submit_button{
    	float:right;
    }
  </style>
  <body>
  <div class="get_color">
  <form id="arrangeclean" class="arrangeclean">
  <input type="hidden" id="amount" name="amount" value="<%=request.getParameter("amount")%>"/>
  <input type="hidden" id="branchId" name="branchId" value="<%=request.getParameter("branchId")%>"/>
  <input type="hidden" id="memberid" name="memberid" value="<%=request.getParameter("memberid")%>"/>
        <section class="detail_one">
            <ul class="clearfix">
				<li><label class="label_name">保洁编号：</label><input id="recordId" name="recordId" type="text" value="<%=request.getParameter("cleanApplication")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">保洁日期：</label> <input id="cleanTime" name="cleanTime" type="text" value="<%=request.getParameter("getCleanTime")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">门店名称：</label> <input id="branchname" name="branchname" type="text" value="<%=request.getParameter("branchname")%>" class="text_search" readonly="readonly"></li>
				<li><label class="label_name">房间号：</label> <input id="roomId" name="roomId" type="text" value="<%=request.getParameter("roomid")%>" class="text_search" readonly="readonly"></li>
                <li><label class="label_name">时间段	：</label><input id="timeArea" name="timeArea" type="text"  value="<%=request.getParameter("getTimeArea")%>" class="text_search" readonly="readonly"></li>
                <li><label class="label_name">保洁人：</label>
			      	<select name="cleanPerson" class="text_search" id="cleanPerson">
			      		<!-- <option value="0">0</option>
			      		<option value="1">1</option> -->
			      	</select>
				</li>
				<li><label class="label_name">备注：</label><textarea id="remark" name="remark"  class="table_textarea" rows="2"></textarea></li>
				<li class="submit_button">
<!--		       <input type="button" id="submitbill" value="取消" onclick="window.parent.JDialog.close()" class="button_margin cancel"/>-->
		       <input type="button" id="submitbill" value="确认" onclick="updatecleanrecord(this)" class="button_margin confirm"/>
     			</li>	
            </ul>
        </section>
    </form>
    </div>
  
    
    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
    <script>
    var base_path ="<%=request.getContextPath()%>";
    $(function(){
       getallstaff();
    

    });
    
    function getallstaff(){
    
    
  		$.ajax({
		         url: base_path + "/selectallstaff.do",
				 type: "post",
				 data : {},
				 success: function(json) {
				 
				 	loadstaff(json);
				 },
				 error: function(json) {}
			});
  		
  		function loadstaff(json){
  		var tabledata = "<option value=''>-请选择-</option>";
  		$.each(json,function(index){
  		tabledata = tabledata + "<option value='" +json[index]["STAFFID"] + "'>" +json[index]["STAFFNAME"] + "</option>";
  		
  		
  		});
		$("#cleanPerson").html(tabledata);			 	
	
  		}
  		}
    
    function updatecleanrecord(e){
   			
			 if(e && e.stopPropagation){
			  e.stopPropagation();
			}else{
			  window.event.cancelBubble = true;
			 }
			 var rgb = $("#submitbill").css("background-color");
			 if(rgb == "rgb(170, 170, 170)")
			 	return false;
			 
			 
			if($("#roomId").val()==""){
			showMsg("请输入房号!");
			
			return false;
			}
			if($("#cleanPerson").val()== ""){
			showMsg("请输入保洁人员!");
			return false;
			}

			$.ajax({
		         url: base_path + "/updatecleanrecord.do",
				 type: "post",
				 dataType:"json",
				 data : $("#arrangeclean").serialize(),
				 success: function(json) {
				 showMsg(json.message);
				 window.setTimeout("window.parent.location.reload(true)",1800);
				
				 
				 },	
				 error: function(json) {
				 showMsg("设置失败!");
				 window.setTimeout("window.parent.location.reload(true)",2800);
				 }
				 
			});
		   $("#submitbill").css("background-color", "#aaa");
		 
  		}

    </script>
  </body>
</html>
