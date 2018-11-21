<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<title>房态数据展示</title>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </head>
  <style>
  	table.myTable{
  		width: 100%;
        border-collapse:collapse; 
        border-spacing:0; 
        border:none;
  	}
  	.city_border table.myTable thead tr .header{
  		border-bottom:none;
  	}
  	
  	.fixedThead{
  		 display: block;
         width: 100%;
  	}
  	 .scrollTbody{
        display: block;
        height: 804px;
        overflow: auto;
        width: 100%;
   }
    .myTable td,.myTable th {
          border-bottom: none;
          border-left: none;
          padding: 2px 3px 3px 4px;
          width:182px;
    }
     thead.fixedThead tr th:last-child {
          color:#FF0000;
    }
  </style>
  <body>
  	<form id="pagerForm" name="pagerForm" action="cityroomstatus.do" target="_self" method="post">
	    <div class="city_border" style="height:846px;overflow:auto   ;">
	    	<table id="myTable" class="myTable" border="0" width="100">
				<thead class="fixedThead">
					<tr>
						<th class="header">分店</th>
						<th class="header">房间类型</th>
						<th class="header">总房数</th>
						<th class="header">门市价</th>
						<th class="header">可售房数</th>
						<th class="header">过夜房数</th>
						<th class="header">有效预定</th>
						<th class="header">所有预定</th>
						<th class="header">停售房数</th>
					</tr>
				</thead>
				<tbody id="len" class="scrollTbody">
					<c:forEach items="${list}" var="list">
						<tr>
							<td style="display:none;">${list.branchId}</td>
						    <td class="rowspan_hover">${list.branchName }</td>
							<td>${list.roomType }</td>
							<td>${list.count }</td>
							<td>${list.roomPrice }</td>
							<td>${list.sellnum }</td>
							<td>${list.nightnum }</td>
							<td>${list.validnum }</td>
							<td>${list.invalidnum }</td>
							<td>${list.stopnum }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
  </body>
  <script type="text/javascript">
  	$(function(){
  		var map = ${map};
  		var leng = $("#len tr").length; 
  		for(var i=0; i<=leng; i++){
  			var one = $("#len tr").eq(i).find("td:first").html();
  			var three = $("#len tr").eq(i).prev().find("td:first").html();
  			var num = map[one];
  			if(i == 0){
  				$("#len tr").eq(i).find("td").eq(1).attr("rowspan", num);
  			}else if(one == three){
  				$("#len tr").eq(i).find("td").eq(1).hide();
  			}else{
  				$("#len tr").eq(i).find("td").eq(1).attr("rowspan", num);
  			}
	  	}
  	});
  </script>
  
</html>
