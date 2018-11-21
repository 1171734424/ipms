<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="../../../common/script.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
  <head>
  
    <title>保洁排班</title>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<style>
	
	 .div_table{
	     height: 95px;
         width: 48%;
         float: left;
         margin: 7px;
         padding:3px;
       
	    }
	    .div_table .color_one{
	    	background-color:#E85B48;
	    	color:#fff;
	    }
	   .div_table  .color_two{
	    	background: #555273;
   			color: #fff;
	    }
	   .div_table  .color_thr{
	    	background: #65799B;
    		color: #fff;
	    }
	    .color_thr em{
	   		color:#fff;
	   		font-style:normal;
	   		font-size:12px;
	    	
	    }
	     .clean_margin table{
	     	background-color: #E2EFF1;
	     	width :100%;
	     	border:none;
	     	font-size: 12px;
	 		text-align: center; 
	     }
	    .clean_margin table tr td{
	    	white-space: inherit;
	    	border: 1px dotted #999;
	    	font-size: 12px;
	    	color:#333;
	    	padding: 2px;
	    }
	    .span_button{
    	    text-align: center;
		    height: 10px;
		    line-height: 10px;
		    cursor: pointer;
		    border-radius: 2px;
		    padding: 0 6px;
		    border: 1px solid #fff;
		    font-size: 12px;
		    background: #EACF79;
    		color: #fff;
	    }
	    .clean_margin  .no_data{
	        width: 100%;
		    height: 100%;
		    background-color: antiquewhite;
		    margin: 0 auto;
		    position: relative;
	    }
	  .clean_margin .no_data #no_data{
		    text-align: center;
		    position: absolute;
		    top: 47%;
		    width: 100%;
		    font-size: 30px;
	    }
	</style>
  </head>
  <body>
    <div style="height: 100%;width: 100%;overflow-y: auto;" class="clean_margin">
    <c:if test="${empty dateFromClean || fn:length(dateFromClean)==0}">
    <div class="no_data">
    <p id="no_data">暂无数据！</p>
    </div>
    </c:if>
		<c:forEach items="${dateFromClean}" var="dateFromClean">
			<c:forEach items="${maps}" var="maps">
				<c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
					<div class="div_table" style="">
						<table >
							<tr>
								<td style="width:50px " >日期</td>
								<td colspan="4" style="font-size: medium">${dateFromClean["CLEANTIME"]} (${maps.value['1'].weekday })</td>
							</tr>
							<tr>
								<td style="width:50px ">时段</td>
								<td class="color_one" >房号</td>
								<td colspan="2" class="color_two">保洁人员</td>
								<td class="color_thr">可保洁数<em></em></td>
							</tr>
							<tr>
								<td style="width:50px ">上午</td>
								<td >
									<c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
										${maps.value['0'].roomId }
									</c:if>
								</td>
								<td id="${dateFromClean['CLEANTIME']},'0',${maps.value['0'].roomId }" colspan="2">
                                    <c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
										${maps.value['0'].cleanPerson }
									</c:if>
                                </td>
                                <td id="${dateFromClean['CLEANTIME']},0" onclick="setrestamount(this.id)">
                                	<span class="span_button">
                                    <c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
										${maps.value['0'].restAmount }
									</c:if>
                                	</span>
                                </td>
							</tr>
							<tr>
								<td style="width:50px ">下午</td>
								<td >
									<c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
										${maps.value['1'].roomId }
									</c:if>
								</td>
								<td colspan="2">
								
								     <c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
										${maps.value['1'].cleanPerson }
									 </c:if>
								</td>
								<td id="${dateFromClean['CLEANTIME']},1" onclick="setrestamount(this.id)">
									<span class="span_button">
	                                     <c:if test="${dateFromClean['CLEANTIME'] == maps.key }">
											${maps.value['1'].restAmount }
										 </c:if>
                               		</span>
                                </td>
							</tr>
						</table>
					</div>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>
	
    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/clean/applydata.js"></script>	
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
  <script type="text/javascript">
   var path ="<%=request.getContextPath()%>";
  	function setrestamount(id){
  		var arr = id.split(",");
  		var time = arr[0];
  		var timearea = arr[1];
  	    var inputdate = new Date(Date.parse(time));
	    var today = new Date();
	    var year = today.getFullYear(); 
	    var month = today.getMonth() + 1;
	    var day = today.getDate();
	    var new_today = new Date(year,month -1,day);


	
	//日期校验
	if(inputdate < new_today){
		showMsg("日期不能小于当天!");
		return false;
	}

   window.parent.JDialog.open("设置可保洁数", path + "/page/ipmspage/leftmenu/handleapply/setamount.jsp?time="+time+"&timearea="+timearea, 500, 250);
  		
  	}
 
  </script>
</html>
