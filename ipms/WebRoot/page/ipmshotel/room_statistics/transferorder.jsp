<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>转单</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<style>
   .imooc_order{
	top:-10px;
	left:96%;
	}
	.ostable{
	height: 10%;
    border: none;
    margin-top: 1%;
    margin-bottom: 1%;
	}
	.osinput{
	width:90%;
	padding: 7px 5px;
	}
	.ostr{
	border-bottom: 1px solid #e9e9e9;
	}
	.ostbcontent{
	height:70%;
	overflow-y: scroll;
	}
	.ostdh{
	height:45px;
	}
   .osearch{  
   font-size: 14px;
    font-family: "微软雅黑";
    height: 26px;
    line-height: 25px;
    text-align: center;
    width: 80px;
    margin-left: 4px;
    border: 1px solid #CCCCCC;
    background: #4A5064;
    border: 1px solid #CCCCCC;
    color: #fff;
    cursor: pointer;
   }
   table.myTable td{
   	padding:7px;
   }
   </style>
    <script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
		<div  class="transf_margin" style="width:100%;height:100%;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header" style='display: none'>订单号</th>
						<th class="header">预定时间</th>
						<th class="header">房型</th>
						<th class="header">房价</th>
						<th class="header">担保类型</th>
						<th class="header">保留时间</th>
						<th class="header">预订人</th>
						<th class="header">入住日期</th>
						<th class="header">退房日期</th>
						<th class="header">入住人</th>
						<th class="header">预定渠道</th>
						<th class="header">备注</th>
					</tr>
				</thead>
				<tbody id="transferorder_data">
				</tbody>
			</table>
		</div>
	<%@ include file="../../common/script.jsp"%>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath()%>";
		var checkid = "<%=request.getParameter("checkid")%>";
		$(function(){
			$.ajax({
		         url: path + "/loadtransferorderData.do",
				 type: "post",
				 data : {checkid : checkid},
				 success: function(json) {
				 	loadtransferorderdata(json);
				 },
				 error: function(json) {}
			});
		});
		
		function loadtransferorderdata(json){
			 var tabledata;
		 	$.each(json, function(index){
		 		tabledata = tabledata + "<tr class='odd' ondblclick='committransferorder(this)'>" +
		 		"<td style='display: none'>" + json[index]["ORDERID"] + "</td>" +
		 		"<td>" + json[index]["ORDERTIME"] + "</td>" +
		 		"<td>" + json[index]["ROOMTYPE"] + "</td>" +
		 		"<td>" + json[index]["ROOMPRICE"] + "</td>" +
		 		"<td>" + json[index]["GUARANTEE"] +"</td>" +
		 		"<td>" + json[index]["LIMITED"] + "</td>" +
		 		"<td>" + json[index]["ORDERUSER"] + "</td>" +
		 		"<td>" + json[index]["ARRIVALTIME"] + "</td>"+
		 		"<td>" + json[index]["LEAVETIME"] + "</td>" +
		 		"<td>" + json[index]["USERCHECKIN"] + "</td>" +
		 		"<td>" + json[index]["SOURCE"] + "</td>" +
		 		"<td>" + json[index]["REMARK"] + "</td>" +
		 		"</tr>";
		 	});
		 	if(json.length == 0){
		 		$("#transferorder_data").html("");
		 	}else{
		 		$("#transferorder_data").html(tabledata);		 	
		 	}
		}
		
		function committransferorder(e){
			var orderid = $(e.children[0]).html();
			$.ajax({
		         url: path + "/committransferorder.do",
				 type: "post",
				 data : {
				 	checkid : checkid,
				 	orderid : orderid
				 	},
				 success: function(json) {
				 	if(json.result == 1){
					 	showMsg(json.message);
						setTimeout("refresh()", 2000);
				 	}
				 },
				 error: function(json) {}
			});
		}
		
		function refresh(){
		 	$(window.parent.document).find(".tab_one").click();
		 	//$(window.parent.parent.document.all[160].contentDocument.logFrame.document.forms[0]).submit();
		 	window.parent.loadCheckData();
		 	window.parent.JDialog.close();
		}
		</script>
  </body>
</html>
