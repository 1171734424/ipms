<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
   request.setAttribute("shiftcontent", request.getAttribute("shiftcontent"));
   request.setAttribute("systype", request.getAttribute("systype"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'defaultaudit.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<link rel="stylesheet" id="style" type="text/css"
			href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />			
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
	<link href="<%=request.getContextPath()%>/css/pmsmanage/common/zui.min.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>	
		.top-table tr td input{
			padding: 4px 10px;
		    font-size: 14px;
		}
	</style>
  </head>
  <body> 
  <c:if test="${systype == 1}">
  	<form id="passform"  method="post">
			<div class="fieldWrapper" align="center">
			<div class="button-group">
					<div class="button_div">
					   <div>
					   	<input type="button"  class="button-label button" onclick="addshift()" value="增加班次" />
					    <input type="button" class="button-label button" onclick="confirm()" value="确认" />
					   </div>
		<!--<input type="button" class="button-label button" onclick="" value="重置" />-->
					</div>
			</div>
				<table class="top-table" id="tableid">
					<tbody id="InputsWrapper">
					  	<c:forEach  items="${shiftcontent}" var="sc" varStatus="status">
						<tr>
							<td class="kv-content" style="padding: 10px;">
							    填写班次
							    <input  id="paramname${status.index}" class=""  name="paramname" value="${sc.shift_name}" />
							    &nbsp;&nbsp;开始时间从&nbsp;&nbsp;
								<input id="starttime${status.index}" class="ldate" name="starttime"  value="${sc.starttime}" />
								&nbsp;&nbsp;至&nbsp;&nbsp;
								<input id="endtime${status.index}" class="ldate"  name="endtime"  value="${sc.endtime}" />
								<input type ="hidden" id="shiftid${status.index}" class=""  name="shiftid" value="${sc.SHIFTTIME_ID}" />
								<a href="javascript:void(0);" class="removeclass" >×</a>
							</td>
						</tr>
						</c:forEach >
					</tbody>
				</table>
				 <div>
					   	<p style="color :red">为保证能正确提交，请按照每个班次的开始时间为上一班次的结束时间，时间总和为24小时的规则设置班次！例如：早班：06:00:00至11:00:00  中班：11:00:00至16:00:00  夜班：16:00:00至06:00:00 </p>
					   </div>
			</div>
		</form>
		</c:if>
 </body>
<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/pmsmanage/common/datetimepicker.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/pmsmanage/common/zui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/audit/defaultaudit.js"></script>
	 <script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
	<script>
	var base_path = "<%=request.getContextPath()%>";
	lay('.ldate').each(function(){
	  laydate.render({
	    elem: this
	    ,trigger: 'click'
	    ,type: 'time'
	    ,btns: ['clear', 'confirm']
	  });
	}); 
	
	var staff = ${staff};
    var adminid = ${adminid};
	
     var FieldCount=0; 
     var MaxInputs  = 5;//设置input数量增加的最大值
     var x = $("#InputsWrapper tr").size();
     var y = $("#InputsWrapper tr").size();
     //var FieldCount=x; 
     //动态增加
     function addshift(){
     	
     	if(adminid == staff.staffId){
     		showMsg("ADMIN不可操作!");
     		return false;
     	}
     	
	    var tab=document.getElementById('tableid');
	    var trs=tab.getElementsByTagName('tr');
	    var trnum = trs.length;
	    FieldCount =x;
           if(trnum <= MaxInputs){
            $("#InputsWrapper").append("<tr ><td class='kv-content' style='padding: 10px;'>"+
                              "填写班次&nbsp;<input  id='paramname"+FieldCount+"' class=''  name='paramname' value='' />&nbsp;&nbsp;&nbsp;开始时间从&nbsp;&nbsp;&nbsp;"+
                              "<input id='starttime"+FieldCount+"' class='ldate' name='starttime' value='' />&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;"+
							   "<input id='endtime"+FieldCount+"' class='ldate'  name='endtime'  value='' />"+
							   "<input type ='hidden'  value='' name='shiftid' />&nbsp;<a href='javascript:void(0);' class='removeclass_add' >×</a></td></tr>");
            
             x++;   
             y++;
            // FieldCount++;
			lay('.ldate').each(function(){
			  laydate.render({
			    elem: this
			    ,trigger: 'click'
			    ,type: 'time'
			    ,btns: ['clear', 'confirm']
			  });
			}); 
        }  
       return false; 
     }
     //数据库里刷来的数据删除
     $(".removeclass").on("click",function(){
			 if( y > 2 ) {  
			var shiftid = $(this).siblings().eq(3).val();
		
			$(this).parent().parent().remove();
			y--;
			$.ajax({
		    url : base_path + "/deleteshift.do",
			dataType : "json",
			type : "post",
			data : {shiftid : shiftid
			      
			},
			success : function(json) {
			   showMsg("该班次删除成功!");
			   window.setTimeout("location.reload(true)", 1800);
			},
			error : function(json) {
			
		}
		   });
			 }	
			});
			
			
     //动态删除
      $("body").on("click",".removeclass_add", function(e){ 
        if( y > 2 ) {  
                $(this).parent().parent().remove(); 
                y--; 
        }  
		return false;  
		});   
    //确认
	function confirm(){
	
		if(adminid == staff.staffId){
     		showMsg("ADMIN不可操作!");
     		return false;
     	}
	
		var nameArray = new Array(
             new Array('paramname','班次名不可为空!','5','班次名不可超过5个字!'),
		     new Array('starttime','开始时间不可为空!','20'),
		     new Array('endtime','截止时间不可为空!','20')
		    
		     );
		
		//先校验非空和长度
		var paramnamearr = document.getElementsByName("paramname"); 
		var endtimearr = $('input[name="endtime"]');
		var shiftidarr = $('input[name="shiftid"]');
		var starttimearr = $('input[name="starttime"]');
		var timearea=0; 
		
		//for(var i=0;i<FieldCount;i++){
		for(var i=0;i< endtimearr.length;i++){
		 for(var j=0; j< nameArray.length;j++){
		 if($("#"+nameArray[j][0]+i).length > 0){
		   if($("#"+nameArray[j][0]+i).val()==''){
		       showMsg(nameArray[j][1]);
		        $("#"+nameArray[j][0]+i).focus();
			  return false;
		   }
		   if(($("#"+nameArray[j][0]+i).val()).length > Number(nameArray[j][2])){
		       showMsg(nameArray[0][3]);
		         $("#"+nameArray[j][0]+i).focus();
			  return false;
		   }
           }
		 }
	}
	    //时间的校验
	    //var paramnamearr = document.getElementsByName("paramname"); 
		//var starttimearr = document.getElementsByName("starttime"); 
		//var endtimearr = $('input[name="endtime"]');
		//var shiftidarr = $('input[name="shiftid"]');
		//var starttimearr = $('input[name="starttime"]');
		//var timearea=0; 
		for(var i=0;i<paramnamearr.length;i++){
		if($(starttimearr[i]).val() < $(endtimearr[i]).val() ){
		 timearea += time_to_sec($(endtimearr[i]).val()) - time_to_sec($(starttimearr[i]).val());
		
		}else{
		 timearea +=time_to_sec("24:00:00") - time_to_sec($(starttimearr[i]).val()) + time_to_sec($(endtimearr[i]).val());
		}
	}
		if(timearea == 86400){
		  var length = paramnamearr.length;
		  for(var j =0;j<length-1;j++ ){
		     if($(endtimearr[j]).val() != $(starttimearr[j+1]).val() && $(starttimearr[0]).val() != $(endtimearr[length-1]).val()){
		     showMsg("请检查时间是否正确!");
		     return false;
		     }
		  }
	
		}else{
		 showMsg("时间分段不合理!");
		     return false;
		}
	//开始ajax
		$.ajax({
		    url : base_path + "/addorupdateshift.do",
			dataType : "json",
			type : "post",
			data : {
			     shiftarr : parseToJson($("#passform").serialize(),"shiftid")
			},
			success : function(json) {
			showMsg(json.message);
			window.setTimeout("location.reload(true)", 1800);
			},
			error : function(json) {
			showMsg(json.message);
		}
		   });
		}

    function parseToJson(msg,end){
		var json = "[{";
		var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
		var t = false;
		for(var i = 0; i<msg2.length; i++){
			var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
			  for(var j = 0; j<msg3.length; j++){
			    json+="\""+msg3[j]+"\"";
			    if(j+1 != msg3.length){
			      json+=":";
			    }
			    if(t){
			      json+="}";
			      if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
			        json+=",{";
			      }
			      t=false;
			    }
			    if(msg3[j] == end){  //这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
			      t = true;
			    }
			  }
			  if(!msg2[i].match(end)){  //同上
			    json+=";";
			  }
			      
			}
			json+="]";
			return json;
}
	 function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
	
	//时分秒转时间戳
	var time_to_sec = function (time){
        var s = '';
        var hour_s = time.split(':')[0];
        var hour = parseInt(hour_s,10);
        var min_s = time.split(':')[1];
        var min = parseInt(min_s,10);
        var sec_s = time.split(':')[2];
        var sec = parseInt(sec_s,10);
        s = Number(hour*3600) + Number(min*60) + Number(sec);
        return s;
    };
	</script>
	
</html>
