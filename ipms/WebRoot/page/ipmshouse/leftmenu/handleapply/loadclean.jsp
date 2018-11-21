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
  
    <title>保洁排班</title>
<!--    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />-->
<!--    <link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />-->
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css">
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
<!--	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>-->
<!--    <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css"  rel="stylesheet">	-->
<!--	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />-->
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
	</style>
  </head>
  <body>
    <div class="c" style="height:590px;width:1000px;border:1px solid;">
		<ul>
	 	  <li>
			<label class="label_name">保洁月份</label>
			<input type="text" id="cleantime" name="cleantime" class="date_text text_search" value=""  />
			<input type="button" name="" id="" class="gdsbutton" value="查询" onclick="searchbymonth()"/>
		   </li>
		</ul>
		<div class="div_table" style="height:600px;">
			<table style="">
			<tr><td style="width:50px ">日期</td><td colspan="3"></td></tr>
			<tr><td style="width:50px ">时段</td><td colspan="2" >房号</td><td >保洁人员</td></tr>
			<tr><td style="width:50px ">上午</td><td colspan="2"></td><td></td></tr>
			<tr><td style="width:50px ">下午</td><td colspan="2"></td><td></td></tr>
			</table>
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
	        	elem: '#cleantime',format: 'YYYY/MM'
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
       var arr = yearmonth.split("/");
      var maxday = getLastDay(arr[0],arr[1]);

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
    //var yearmonth = $("#cleantime").val();
   // var arr = time.split("/");
 // var maxday = getLastDay(arr[0],arr[1]);
   
  // var date = new Date()
       var yearmonth = $("#cleantime").val();
       var arr = yearmonth.split("/");
       var mounth2 = parseInt(arr[1],10)-1;
      // var cleandate = new Date(Date.parse(arr[0]+"/"+mounth2+"/01"))
      var cleandate_page = new Date(arr[0],mounth2,01);
      //  cleandate.setDate(cleandate.getDate()+1);
       var maxday = getLastDay(arr[0],arr[1]);
    		$.ajax({
		url: path + "/qwe.do",
		type:"post",
		dataType: "json",
		data: { 'time' : yearmonth
				
           		},
		success:function(json) {
		
		 $(".div_table").html(" ");
	 for(var i=0;i<maxday;i++){
	 
	 //转插件传来的日期格式
	   	var cleandate_page_year = cleandate_page.getFullYear();
	var cleandate_page_month = cleandate_page.getMonth() + 1;
	var cleandate_page_strDate = cleandate_page.getDate();
    if (cleandate_page_month >= 1 && cleandate_page_month <= 9) {
        cleandate_page_month = "0" + cleandate_page_month;
    }
    if (cleandate_page_strDate >= 0 && cleandate_page_strDate <= 9) {
        cleandate_page_strDate = "0" + cleandate_page_strDate;
    }
    var currentdate = cleandate_page_year + "/" + cleandate_page_month + "/" + cleandate_page_strDate;
    var flag = true;
    //循环json
		$.each(json,function(cleantime_back,timeareaMap){			
			if(cleantime_back == currentdate ){			
			  $.each(timeareaMap,function(timearea,roomidlist){			
			 if(timeareaMap["0"]==null){
			 timeareaMap["0"]="";
			 }
			 if(timeareaMap["1"]==null){
			 timeareaMap["1"]="";
			 }
			    $("<table ><tr><td style='width:50px '>日期</td><td colspan='3'>"+currentdate+
	               "</td></tr><tr><td style='width:50px '>时段</td><td colspan='2' >房号</td><td >保洁人员</td></tr>"+
					 "<tr><td style='width:50px '>上午</td><td colspan='2'>"+timeareaMap["0"]+"</td><td ondblclick=arrangeclean(this)'></td></tr>"+
			          "<tr><td style='width:50px '>下午</td><td colspan='2'>"+timeareaMap["1"]+"</td><td></td></tr>"+
					    "</table>").appendTo(".div_table");
					    cleandate_page.setDate(cleandate_page.getDate()+1);
			   flag=false;
			

		});
		}		
		});
		if(flag){
		 $("<table ><tr><td style='width:50px '>日期</td><td colspan='3'>"+currentdate+
	     "</td></tr><tr><td style='width:50px '>时段</td><td colspan='2' >房号</td><td >保洁人员</td></tr>"+
					"<tr><td style='width:50px '>上午</td><td colspan='2'></td><td ondblclick=arrangeclean(this)></td></tr>"+
			"<tr><td style='width:50px '>下午</td><td colspan='2'></td><td></td></tr>"+
					"</table>").appendTo(".div_table");
		 cleandate_page.setDate(cleandate_page.getDate()+1);
		
		}
}
	
		},
		error :function(json){
		}
    	
    		});
    		});
    		
    		function arrangeclean(e){
    	
    		
    		}
    </script>
  </body>
</html>
