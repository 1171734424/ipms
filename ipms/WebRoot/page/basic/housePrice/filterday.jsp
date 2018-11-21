<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("validstart", request.getAttribute("validstart")); 
   request.setAttribute("validend", request.getAttribute("validend")); 
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>过滤日期</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/pmsmanage/roomprice.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.filterday_title{
	margin: 2% 0% 2% 1%;
    font-size: 14px;
    color:gray;
	}
	.filterday_table{
	border:none;
	height: 16%;
	margin-top:5%;
	}
	
   .rpapplysubmit_button{
        float:left;
        margin-left:35%;
        
        }
        
    .rpapplyclose_button{
        float:left;
        margin-left:11%;
        }
     .filter_button{
        margin-top:5%;
     }
     .filterinput{
        height:100%;
     }
	</style>

  </head>
  <body>
  <input id="validstart" name="validstart" type="hidden" value="${validstart}"/>
  <input id="validend" name="validend" type="hidden" value="${validend}"/>
  
  <div><table class="filterday_table">
					    <col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
						<tr>
						<td align="right"><label >开始时间:</label></td>
						<td><input id="filterbegindate" name="begindate" type="text" class="filterinput"/></td>
						<td align="right"><label >结束时间:</label></td>
						<td><input id="filterenddate" name="enddate" type="text" class="filterinput"/></td>
						</tr></table>
						<div class="filterday_title">  <span>&nbsp</span><span id="" name="">${validstart}</span><span>&nbsp至</span><span>&nbsp</span><span id="" name="">${validend}</span><span>&nbsp期间的过滤周期</span></div>
						</div>	
						    <div class="filter_button" style="color:gray">
									<div class="div_button button rpapplysubmit_button" role="button"
										onclick="auditFilterday();">
										确定
										<!-- <a id="sData">确定</a> -->&nbsp;
									</div>
										<div class="div_button button rpapplyclose_button" role="button"
										onclick="window.parent.JDialog.close();">
										取消
										<!-- <a id="sData">取消</a> -->
									</div>
									
							</div>
								
	    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
		 laydate({
		elem: '#filterbegindate',
		format: 'YYYY/MM/DD hh:00:00', 
		type: 'datetime',
		istime:true,
		min:laydate.now(0) 
	   })
	
	   laydate({
		elem: '#filterenddate',
		format: 'YYYY/MM/DD hh:00:00', 
		type: 'datetime',
		istime:true,
		min:laydate.now(0) 
	   })
	   
	    function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
	   }
	   
	   function auditFilterday(){
	   var filterbegindate = $("#filterbegindate").val();
	   var filterenddate = $("#filterenddate").val();
	   var validstart = $("#validstart").val();
	   var validend = $("#validend").val();
	   if(!filterbegindate){
	      showMsg("过滤起始时间不能为空");
	   }else if(!filterenddate){
	      showMsg("过滤结束时间不能为空");
	   }else if((new Date(filterbegindate.replace(/\-/g, "\/"))) > (new Date(
						filterenddate.replace(/\-/g, "\/")))){
		 showMsg("过滤结束时间不能早于过滤起始时间");			
	   } else if(((new Date(filterbegindate.replace(/\-/g, "\/"))) < (new Date(
						validstart.replace(/\-/g, "\/"))))||((new Date(filterbegindate.replace(/\-/g, "\/"))) > (new Date(
						validend.replace(/\-/g, "\/"))))){
	       showMsg("过滤起始时间不能早于调整起始时间或者晚于调整结束时间");
	   }else if(((new Date(filterenddate.replace(/\-/g, "\/"))) < (new Date(
						validstart.replace(/\-/g, "\/"))))||((new Date(filterenddate.replace(/\-/g, "\/"))) > (new Date(
						validend.replace(/\-/g, "\/"))))){
	       showMsg("过滤结束时间不能早于调整起始时间或者晚于调整结束时间");
	   }else{
	      var filterdate = filterbegindate +"至"+filterenddate;
	       window.parent.$("#filterday").val(filterdate);
               window.parent.JDialog.close();
	   
	   }
	   
	   }
		</script>
	
	</body>
</html>
