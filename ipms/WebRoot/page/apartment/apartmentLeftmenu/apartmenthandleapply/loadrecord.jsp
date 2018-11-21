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
  
    <title>保洁记录</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />

	<style>
		.register_margin .label_name {
		    float: left;
		    width: 90px;
		    text-align: right;
		    font-weight: normal;
		    font-size: 0.85rem;
		}
		.div_table{
		    height: 547px;
		    overflow-y: scroll;
	    }
	   .load_margin .gdsbutton_record{
		   	font-size: 12px;
		    font-family: "微软雅黑";
		    height: 32px !important;
	        line-height: 26px !important;
	        text-align: center;
	        width: 70px !important;
		    margin-left: 4px;
		    border:none;
		    background: #5A5D9C !important;
		    color: #fff;
		    cursor: pointer;
	   }
	</style>
  </head>
  <body>
  	<h2>保洁记录</h2>
    <div class="check_wrap_margin load_margin" style="height:50px;width:1000px;">
		<ul>
			<li>
				<label class="label_name">选择月份</label>
				<input type="text" id="cleantime" name="cleantime" class="date_text text_search" value=""  />
				<input type="button" name="" id="" class="gdsbutton_record button_margin" value="查询" onclick="searchbymonth()"/>
		    </li>
		</ul>
	</div>
	<div style="width:100%;height:80%;padding-right:2%;">
		<iframe name="cleanIframe" id="cleanIframe" frameborder="0" width="100%" height="100%" src=""></iframe>
	</div>
    <script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentclean/applydata.js"></script>	
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/apartment/apartmentLeftmenu/apartmentrefund/util.js"></script>
    <script src="<%=request.getContextPath()%>/script/apartment/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
    <script>
    var path ="<%=request.getContextPath()%>";
    laydate.render({
		  elem: '#cleantime'
		  ,type: 'month',
		  format: 'yyyy/MM',
		  theme: '#4A5064'
	});
    		
//获得某月的最后一天  
function getLastDay(year,month) {         
    var new_year = year;    //取当前的年份          
    var new_month = ++month;//取下一个月的第一天，方便计算（最后一天不固定）       
    if(month>11) {         
     new_month -=12;        //月份减          
     new_year++;            //年份增          
    }         
    var new_date = new Date(new_year,new_month-1,1); 
    // var new_date = new Date(new_year,0,1); 
    return (new Date(new_date.getTime()-1000*60*60*24).getDate());//获取当月最后一天日期          
} 

	function searchbymonth(){
	     var yearmonth = $("#cleantime").val();
	     $("#cleanIframe").attr("src",path+"/querycleanApartment.do?time="+yearmonth);	
	}
	
   $(function(){
    var now = new Date();    
    var year = now.getFullYear();       //年   
    var month = now.getMonth() + 1; 
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }    
    var day = now.getDate();
    var time = year+"/"+month;
    $("#cleantime").val(time);
    searchbymonth();
 });
    		
    		
    </script>
  </body>
</html>
