<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>增减固定用户</title>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/addDelFixedUser.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
		<%-- <link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" /> --%>
	</head>
	
<body>
	<div class="userbox">
		<c:if test="${flag == '1'}">
		 	<span class="spanBold">在住客人</span>
		 	<table >
		 		<thead>
		 			<tr><td class="fontbold">姓名</td><td class="fontbold">身份证</td><td class="fontbold">手机号</td><td class="fontbold">授权开始时间</td><td class="fontbold">授权结束时间</td><!-- <td>操作</td> --></tr>
		 		</thead>
		 		<tbody class="innerTable">
			 	<c:forEach items="${checkUserList}" var="sl">
			 		<tr id="${sl.checkuserId}"><td>${sl.checkuserName}</td><td>${idcard}</td><td>${sl.mobile}</td><td><fmt:formatDate type="time" value="${sl['startTime']}" pattern="yyyy/MM/dd HH:mm:ss" />
			 		</td><td><fmt:formatDate type="time" value="${sl['endTime']}" pattern="yyyy/MM/dd HH:mm:ss" /></td>		 
			 	</c:forEach>
			 	</tbody>
		 	</table>
		</c:if>
			<input id="serialNo" name="serialNo" type="hidden"  value="${serialNo}"/>
			<input id="gatewayCode" name="gatewayCode" type="hidden"  value="${gatewayCode}"/>
			<input id="type" name="type" type="hidden"  value="${type}"/>
			<input id="checkIdOrContract" name="checkIdOrContract" type="hidden"  value="${checkIdOrContract}"/>
			<div id="addBut" class="btn-contrl" >
		 		<span class="fl  spanbold">固定用户</span><span class="btn add fr" style="background-color: #FC8A15" onclick="addFixedUser();">新增</span>
			</div>
		 	<form id="myForm" class="form-horizontal" role="form" method = "post"  >
		 	<table id="showData" align="center">
		 		<thead>
		 			<tr id="trFirst"><td class="fontbold">姓名</td><td class="fontbold">身份证</td><td class="fontbold">手机号</td><td class="fontbold">授权开始时间</td><td class="fontbold">授权结束时间</td><td id="oper" class="fontbold">操作</td></tr>
		 		</thead>
		 		<tbody>
				 	<c:forEach items="${FixedUserList}" var="sl">
				 		<tr id="${sl.userId}"><td>${sl.userName}</td><td>${sl.idcard}</td><td>${sl.mobile}</td><td><fmt:formatDate type="time" value="${sl['startTime']}" pattern="yyyy/MM/dd HH:mm:ss" /></td><td><fmt:formatDate type="time" value="${sl['endTime']}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
					 		<td id="oper">
					 			<div id="deleteBut" class="btn" style="background-color: #52B2E3" onclick="deleteFixedUser(this)">
									<span>删除</span>
								</div>
							</td>
						</tr>
				 	</c:forEach>
				</tbody>
		 	</table>
			<div id="deleteBut" class="confirm" style="background-color: #5A5D9D" onclick="forSubmit()">
				<span>确定</span>
			</div>
	</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>	
<script type="text/javascript">
	var array = new Array();
	var base_path ="<%=request.getContextPath()%>";
	var count = 1;
	$(document).ready(function() {
		laydate.render({
		  elem: '.startTimeStyle',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
		});
		
		laydate.render({
		  elem: '.endTimeStyle',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
		});
	});
	
	function deleteFixedUser(obj){
		var thisId = obj.parentElement.parentElement.id;
		var idcard = $($(obj).parent()).siblings().eq(1).html();
		var rowIndex = obj.parentElement.parentElement.rowIndex;
    	document.getElementById("showData").deleteRow(rowIndex);
		if(thisId.indexOf("add") <= -1){
			$.ajax({	
			url : base_path + '/deleteNewFixedUser.do',
			dataType : "json",
			type : "post",
			data : { userId : thisId ,
			idcard : idcard,
			serialNo : $("#serialNo").val()},
			success : function(json){
				if(json.result == 1){
					window.parent.showMsg("删除成功！");
					window.parent.refreshGrid();
					//window.parent.JDialog.close();
				}else{
					if(json.message == "dataToDoorFlase"){
						window.parent.showMsg("门锁身份证数据删除失败！");
					} else {
						window.parent.showMsg("删除失败");
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}
				}
			},
			error:function(json){
				window.parent.showMsg("操作失败！" + json);
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		})	 
		} 
	
	 	
    	
	}
	
	function addFixedUser(){
		var addHtml = "<tr id=add"+count+"><td><input id='userName' class='inputSpan userStyle' value=''/></td><td><input id='idcard' class='inputSpan idcardStyle' value=''/></td>"
		+"<td><input id='mobile' class='inputSpan mobileStyle' value=''/><td><input id='startTime' class='inputSpan startTimeStyle' value=''/></td><td><input id='endTime' class='inputSpan endTimeStyle' value=''/></td>"
		+"<td id='oper'><div id='deleteBut' class='btn' style='background-color: #52B2E3' onclick='deleteFixedUser(this)'>"
		+"<span>删除</span></div></td>";
		var ss = $("#showData");
		ss.append(addHtml);
		count++;
		
		laydate.render({
		  elem: '.startTimeStyle',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
		});
		
		laydate.render({
		  elem: '.endTimeStyle',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
		});
	}
	
	function forSubmit(){
		array = new Array();
		var a=1,b=1,c=1,d=1,e=1,f=1,g=1,h=1,j=1,dd=1;
		var idcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证校验
		var mobReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验	
		 $("tr[id^='add'] td .userStyle").each(function(){
		  	if($(this).val().replace(/\s+/g,"") == null || $(this).val().replace(/\s+/g,"") == ""){
		  		a=0;
				return a;
		 	}
		 });

		 if(a==0){
		// alert("姓名不可为空！")
		 window.parent.showMsg("姓名不可为空!");
		 return;
		 }
		 
	
	$("tr[id^='add'] td .idcardStyle").each(function(){
	 	if($(this).val().replace(/\s+/g,"") == null || $(this).val().replace(/\s+/g,"") == ""){
	 		b=0;
			return b;
	 	}
	 });
	
	 if(b==0){
		 //alert("身份证不可为空！")
		 window.parent.showMsg("身份证不可为空!");
		 return;
		 }
	
	 $("tr[id^='add'] td .mobileStyle").each(function(){
	  	if($(this).val().replace(/\s+/g,"") == null || $(this).val().replace(/\s+/g,"") == ""){
	 		c=0;
			return c;
	 	}
	 });
	 
	 if(c==0){
		 //alert("手机号不可为空！")
		 window.parent.showMsg("手机号不可为空!");
		 return;
		 }
		 
	 $("tr[id^='add'] td .startTimeStyle").each(function(){
	  	if($(this).val().replace(/\s+/g,"") == null || $(this).val().replace(/\s+/g,"") == ""){
	 		h=0;
			return h;
	 	}
	 });
	 
	 if(h==0){
		 window.parent.showMsg("开始时间不可为空!");
		 return;
	 }
	 
	 $("tr[id^='add'] td .endTimeStyle").each(function(){
	  	if($(this).val().replace(/\s+/g,"") == null || $(this).val().replace(/\s+/g,"") == ""){
	 		j=0;
			return j;
	 	};
	 	
	 	//alert($($(this).parent().parent().children().eq(3)).children().eq(0).val()+"444"+$(this).val())
	 	if(!checkTimeIsRight($($(this).parent().parent().children().eq(3)).children().eq(0).val(), $(this).val(), "开始时间", "结束时间")){
	 		dd=0;
			return dd;
	 	}
	 });
	 
	 if(j==0){
		 window.parent.showMsg("结束时间不可为空!");
		 return;
	 }
	 
	 if(dd==0){
		 return false;
	 }
		 
	
	 $("tr[id^='add'] td .userStyle").each(function(){
	  	if($(this).val().length > 10){
			d=0;
			return d;
	 	}
	 });
	 
	  if(d==0){
		 //alert("姓名长度过长!")
		 window.parent.showMsg("姓名长度过长!");
		 return;
		 }
		 
	 $("tr[id^='add'] td .idcardStyle").each(function(){
	 	if(!idcard.test($(this).val())){
	 		e=0;
			return e;
	 	}
	 });
	 
	 if(e==0){
		// alert("身份证输入错误!")
		  window.parent.showMsg("身份证输入错误!");
		 return;
		 }
	 
	  $("tr[id^='add'] td .mobileStyle").each(function(){
	  	if(!mobReg.test($(this).val())){
			f=0;
			return f;
	 	}
	 });
	 
	if(f==0){
		// alert("手机号输入错误!")
		 window.parent.showMsg("手机号输入错误!");
		 return;
		 }
	
	var newArray = $("tr[id^='add'] td .idcardStyle");
	
	for(var i=0; i<newArray.size(); i++){
		for(var j=i+1; j<newArray.size(); j++){
			if(newArray[i].value==newArray[j].value){
				g=0;
				break;
			}
		}
	}
	 
	 if(g==0){
		// alert("身份证输入重复!")
		 window.parent.showMsg("身份证输入重复!");
		 return;
		 }
		 
		 
	
	$("#showData tr[id^=add]").each(function(){
		var longString = "{";
		 $(this).find("td:not(#oper)").each(function(){
			var a = $(this).find("input").attr("id");
			var b = $(this).find("input").val();
			var c ='\"'+ a + '\":\"' + b +'\"';
			longString += c +",";
	 	 });
	 	 longString = longString.substring(0, longString.length -1 ) + "}";	
	 	 array.push(JSON.parse(longString));
	 });

		 $.ajax({	
			url : base_path + '/saveNewFixedUser.do',
			dataType : "json",
			type : "post",
			data : {myForm : JSON.stringify(array),
			serialNo : $("#serialNo").val(),
			checkIdOrContract : $("#checkIdOrContract").val(),
			type :  $("#type").val(),
			gatewayCode : $("#gatewayCode").val()
			},
			success : function(json){
				if(json.result == 1){
					if(json.message == "again"){
						window.parent.showMsg("身份证重复！");
					} else {
						window.parent.showMsg("新增成功！");
						window.parent.refreshGrid();
						window.parent.JDialog.close();
					}
				}else{
					if(json.message == "dataToDoorFlase"){
						window.parent.showMsg("身份证数据写入门锁失败！");
					} else {
						window.parent.showMsg("新增失败！");	
					}
					window.setTimeout("window.parent.location.reload(true)", 1000);
					
				}
			},
			error:function(json){
				window.parent.showMsg("操作失败！" + json);
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		});	   
	}
	
	
	//检验时间的对错
function checkTimeIsRight(time1, time2,timedesc1, timedesc2){
	errorMsg = "";
	var d1 = new Date(Date.parse(time1));  
	var d2 = new Date(Date.parse(time2)); 
	if(d1 >= d2){  
		errorMsg = timedesc1 + "不能晚于 \n" + timedesc2; 
		window.parent.showMsg(errorMsg);
		return false;	
	} else {
		return true;
	}
}
</script>
</body>

</html>