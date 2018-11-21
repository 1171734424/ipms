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
</head>
	
<body>
	<div class="userbox" style='height:525px'>
		 	<span class="spanBold">所选门锁</span>
		 	<div id="door" class="divlock">
			 </div>
			<div id="addBut" class="" >
		 		<span class="fl  spanbold">固定用户</span>
		 		<span class="btn add fr" style="background-color: #FC8A15;margin-right:8.5%;" onclick="allAddRight();">批量授权</span>
		 		<span class="btn add fr" style="background-color: #0a58cd" onclick="getPassword();">动态密码</span>
				<span class="btn add fr" style="background-color: #d6a0da;" onclick="addUser();">增加用户</span>
				<span class="btn add fr" style="background-color: #61949e;" onclick="relogin();">刷新</span>
			</div>
		 	<form id="myForm" class="form-horizontal" role="form" method = "post"  >
		 	<table id="showData" align="center">
		 		<thead>
		 			<tr id="trFirst">
		 			<td class="fontbold" id="all">
						<input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span>
					</td>
		 			<td class="fontbold">姓名</td><td class="fontbold">身份证</td><td class="fontbold">手机</td><td id="startth" class='fontbold TimeStyle'>授权开始时间</td><td id="endth" class='fontbold TimeStyle'>授权结束时间</td><td id="oper" class="fontbold">操作</td></tr>
		 		</thead>
		 		<tbody>
				 	<c:forEach items="${FrequenterList}" var="sl">
				 		<tr id="${sl.userId}"  class="load">
				 		<td onclick='aa(this)'><input type='checkbox'></td>
				 		<td>${sl.userName}</td><td>${sl.idcard}</td><td>${sl.mobile}</td><td class="TimeStyle"><input id='startTime' class='inputSpanAnther startTimeStyle' value=''/></td><td class="TimeStyle"><input id='endTime' class='inputSpanAnther endTimeStyle ' value=''/></td></td>
					 		<td id="oper">
					 			<div id="deleteBut" class="btn btnstyle marginledelAnother" style="background-color: #52B2E3;" onclick="rightDoorRecord(this)">
									<span>授权详情</span>
								</div>
							</td>
						</tr>
				 	</c:forEach>
				</tbody>
		 	</table>
			<!-- <div id="deleteBut" class="confirm" style="background-color: #5A5D9D" onclick="forSubmit()">
				<span>确定</span>
			</div> -->
			<input type="hidden" id="serialNo" name="serialNo" value="${serialNo}"/>
			<input type="hidden" id="gatewayCode" name="gatewayCode" value="${gatewayCode}"/>
			<input type="hidden" id="roomId" name="roomId" value="${roomId}"/>
			<input type="hidden" id="branchIdNum" name="branchIdNum" value="${branchIdNum}"/>
			<input type="hidden" id="branchIdName" name="branchIdName" value="${branchIdName}"/>
	</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>	
<script type="text/javascript">
	var array = new Array();
	var base_path ="<%=request.getContextPath()%>";
	var count = 1;
	$(document).ready(function() {
		doorData();
		lay('.inputSpanAnther').each(function(){
		  laydate.render({
		    elem: this,
		     type: 'datetime',
			  format: 'yyyy/MM/dd HH:mm',
			  theme: '#4A5064'
		  });
		});
		
		$(".laydate-theme-molv").each(function(){
			this.addClass("dateNewStyle");
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
	
/* 	function forSubmit(){
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
	 */
	
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

function updateallcheckbox(e){
				var checked = $(e).prop('checked');
				var checkedtds = $("td input[type='checkbox']");
				if(checked){
					$.each(checkedtds, function(index){
							$(checkedtds[index]).prop("checked", true);
							$(".btnstyle").removeClass("marginledelAnother");
							$(".btnstyle").addClass("marginledel");
							$(checkedtds[index]).parent().parent().children().eq(4).removeClass("TimeStyle");
							$(checkedtds[index]).parent().parent().children().eq(5).removeClass("TimeStyle");
							$(checkedtds[index]).parent().parent().children().eq(4).find('input').removeClass("TimeStyles");
						    $(checkedtds[index]).parent().parent().children().eq(5).find('input').removeClass("TimeStyles");
					});
				} else {
					$.each(checkedtds, function(index){
							$(checkedtds[index]).prop("checked", false);
							$(".btnstyle").addClass("marginledelAnother");
							$(".btnstyle").removeClass("marginledel");
							$(checkedtds[index]).parent().parent().children().eq(4).addClass("TimeStyle");
							$(checkedtds[index]).parent().parent().children().eq(5).addClass("TimeStyle");
					});
				}
			}
			
			function aa(e) {
				 checkedbytruns(e);
			}
			
			function checkedbytruns(e){
			 //判断是否已经全选了
			    var count = 0;
			    var checkedtds = $(".load td input[type='checkbox']");
			    var checkedtdslen = $(".load td input[type='checkbox']");
			   	var staTimeArray = new Array();
				var endTimeArray = new Array();
				var staTime = null;
				var endTime = null;
				$.each(checkedtds, function(index){
					if($(checkedtds[index]).prop("checked")){
						count++;
						staTime = $(checkedtds[index]).parent().parent().children().eq(4);
						endTime = $(checkedtds[index]).parent().parent().children().eq(5);
						staTimeArray.push(staTime);
						endTimeArray.push(endTime);
					} else {

					}
				});
			
			
				var checkbox = $(e).parent().find(':checkbox');
				var checked = checkbox.prop("checked");
				//checkbox.stopPropagation();
				 if(checked){
				    if(count == 1){
				    $("#endth").addClass("TimeStyle");
					$("#startth").addClass("TimeStyle");
					$(".btnstyle").addClass("marginledelAnother");
					$(".btnstyle").removeClass("marginledel");
				    //所有的都删掉
				    $.each(checkedtds, function(index){
						 $(checkedtds[index]).parent().parent().children().eq(4).addClass("TimeStyle");
						 $(checkedtds[index]).parent().parent().children().eq(5).addClass("TimeStyle");
				    });
				    	
				    }else {
					    $("#endth").removeClass("TimeStyle");
						$("#startth").removeClass("TimeStyle");
						checkbox.parent().parent().children().eq(4).removeClass("TimeStyle");
						checkbox.parent().parent().children().eq(5).removeClass("TimeStyle");
						checkbox.parent().parent().children().eq(4).find('input').addClass("TimeStyles");
						checkbox.parent().parent().children().eq(5).find('input').addClass("TimeStyles");
					}	
					count--;			
				} else {
					if(count == 0){
						$("#endth").removeClass("TimeStyle");
						$("#startth").removeClass("TimeStyle");
						$(".btnstyle").removeClass("marginledelAnother");
						$(".btnstyle").addClass("marginledel");
						 $.each(checkedtds, function(index){
						  $(checkedtds[index]).parent().parent().children().eq(4).removeClass("TimeStyle");
						 $(checkedtds[index]).parent().parent().children().eq(5).removeClass("TimeStyle");
						  $(checkedtds[index]).parent().parent().children().eq(4).find('input').addClass("TimeStyles");
						 $(checkedtds[index]).parent().parent().children().eq(5).find('input').addClass("TimeStyles");
				    });
					} 
					
						
						checkbox.parent().parent().children().eq(4).removeClass("TimeStyle");
						checkbox.parent().parent().children().eq(5).removeClass("TimeStyle");
						checkbox.parent().parent().children().eq(4).find('input').removeClass("TimeStyles");
						checkbox.parent().parent().children().eq(5).find('input').removeClass("TimeStyles");	
					count++;
				} 
			    checkbox.prop('checked', !checked);
			    if(count == checkedtdslen.length){
			    	$("#all input").prop('checked',true);
			    } else {
			    	$("#all input").prop('checked',false);
			    }
			}
			
			function doorData(){
				var doorData = ${jsonArrayList};
				var width = 100*(doorData.length/5);
				var html ="<div style='width:"+width+"%;overflow:hidden;'>";
				$.each(doorData, function(index){
					html +="<div class='widthdoor'><input class='inputsty' value='"+doorData[index]["branchIdName"]+"/"+doorData[index]["roomId"]+"号'</div>"
					         +"<input style='display:none;' class='inputsty' value='"+doorData[index]["branchIdNum"]+"'/>"
					         +"<input class='inputsty' value='"+doorData[index]["lockno"]+"'/>"
					         +"<input style='display:none;' class='inputsty' value='"+doorData[index]["gatewayCode"]+"' /></div>";	
				});	
				html += "</div>";
				$("#door").append(html);
			}
			
			function allAddRight(){	
				var a=1,b=1,c=1,d=1,e=1,f=1,g=1,h=1,jj=1,dd=1;			
				var useridarray = new Array();
				var startTimeList = new Array();
				var endTimeList = new Array();
				var startTime = null;
				var endTime = null;
				var checkedtds = $(".load td input[type='checkbox']");
				$.each(checkedtds, function(index){
					if($(checkedtds[index]).prop("checked")){
						var checkid = $(checkedtds[index]).parent().parent().attr("id");
						startTime = $(checkedtds[index]).parent().parent().children().eq(4).find('input').val();
						endTime = $(checkedtds[index]).parent().parent().children().eq(5).find('input').val();
						startTimeList.push(startTime);
						endTimeList.push(endTime);
						useridarray.push(checkid);		
					} 
				});
				
				if(useridarray.length <= 0){
					window.parent.showMsg("请至少选择一个用户！");
					return false;
				} else{
					for(var i=0; i <startTimeList.length; i++){
						if(startTimeList[i].replace(/\s+/g,"") == null || startTimeList[i].replace(/\s+/g,"") == ""){
					 		h=0;
					 		break;
					 	}
					}
					  	
					 if(h==0){
						 window.parent.showMsg("开始时间不可为空!");
						 return;
					 }
					 
					 for(var j=0; j <endTimeList.length; j++){
						if(endTimeList[j].replace(/\s+/g,"") == null || endTimeList[j].replace(/\s+/g,"") == ""){
					 		jj=0;
							break;
					 	}
					 	
					 	if(!checkTimeIsRight(startTimeList[j], endTimeList[j], "开始时间", "结束时间")){
					 		dd=0;
					 		break;
					 	}
					 	
					}

				 	 if(jj==0){
						 window.parent.showMsg("结束时间不可为空!");
						 return;
					 }
					 
					 if(dd==0){
						 return false;
					 }
					 addUsersSubmit();
				} 	
			}
			
			function addUsersSubmit(){
				var doorDatanew = ${jsonArrayList};
				var newarray = new Array();
				
			 	var checkedtds = $(".load td input");
				$.each(checkedtds, function(index){
					if($(checkedtds[index]).prop("checked")){
						var username = $(checkedtds[index]).parent().parent().children().eq(1).text();
						var useridcard = $(checkedtds[index]).parent().parent().children().eq(2).text();
						var startTime = $(checkedtds[index]).parent().parent().children().eq(4).find('input').val();
						var endTime = $(checkedtds[index]).parent().parent().children().eq(5).find('input').val();
						var item ={name:username,cardNumb:useridcard,startTime:startTime,endTime:endTime};
						newarray.push(item);
					}
				});
				
				$.ajax({	
					url : base_path + '/mutilDoorAddUser.do',
					dataType : "json",
					type : "post",
					data : { doorData : JSON.stringify(doorDatanew),
							 newarray : JSON.stringify(newarray)},
					success : function(json){
						if(json.result == 1){
							window.parent.showMsg("授权成功！");
							window.parent.refreshGrid();
							
							checkedtds = $("td input");
							$.each(checkedtds, function(index){
								$(checkedtds[index]).prop("checked", false);
								$(".btnstyle").addClass("marginledelAnother");
								$(".btnstyle").removeClass("marginledel");
								$(checkedtds[index]).parent().parent().children().eq(4).addClass("TimeStyle");
								$(checkedtds[index]).parent().parent().children().eq(5).addClass("TimeStyle");
								$(checkedtds[index]).parent().parent().children().eq(4).find("input").val("");
								$(checkedtds[index]).parent().parent().children().eq(5).find("input").val("");
							});
						}else{
							window.parent.showMsg("授权失败！");							
						}
					},
					error:function(json){
						window.parent.showMsg("操作失败！" + json);
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}
				});	
				
			}
			
			function rightDoorRecord(obj){
				var idcard = $($(obj).parent()).siblings().eq(2).html();
				JDialog.open("详情", base_path + "/turnToDoorRightRecord.do?idcard="+idcard,1100,589);
			}
			
			function getPassword(){
				var a=1,b=1,c=1,d=1,e=1,f=1,g=1,h=1,jj=1,dd=1;			
				var useridarray = new Array();
				var startTimeList = new Array();
				var endTimeList = new Array();
				var startTime = null;
				var endTime = null;
				var checkedtds = $(".load td input[type='checkbox']");
				$.each(checkedtds, function(index){
					if($(checkedtds[index]).prop("checked")){
						var checkid = $(checkedtds[index]).parent().parent().attr("id");
						useridarray.push(checkid);		
					} 
				});
				
				if(useridarray.length <= 0){
					window.parent.showMsg("请至少选择一个用户！");
					return false;
				} else{
					var doorDatanew = ${jsonArrayList};
					var newarray = new Array();
				
			 		var checkedtds = $(".load td input");
					$.each(checkedtds, function(index){
						if($(checkedtds[index]).prop("checked")){
							var username = $(checkedtds[index]).parent().parent().children().eq(1).text();
							var useridcard = $(checkedtds[index]).parent().parent().children().eq(2).text();
							var phone = $(checkedtds[index]).parent().parent().children().eq(3).text();
							var item ={name:username,cardNumb:useridcard,phone:phone};
							newarray.push(item);
						}
					});
				
					$.ajax({	
						url : base_path + '/mutilUserGetPwd.do',
						dataType : "json",
						type : "post",
						data : { doorData : JSON.stringify(doorDatanew),
								 newarray : JSON.stringify(newarray)},
						success : function(json){
							if(json.result == 1){
								window.parent.showMsg(json.message);
								window.parent.refreshGrid();	   
							}else{
								window.parent.showMsg("获得动态密码失败！");							
							}
						},
						error:function(json){
							window.parent.showMsg("操作失败！" + json);
							window.setTimeout("window.parent.location.reload(true)", 1000);
						}
					});	
					
					$("#endth").addClass("TimeStyle");
				    $("#startth").addClass("TimeStyle");
					$.each(checkedtds, function(index){
						$(checkedtds[index]).prop("checked", false);
					    $(".btnstyle").addClass("marginledelAnother");
						$(".btnstyle").removeClass("marginledel");
						$(checkedtds[index]).parent().parent().children().eq(4).addClass("TimeStyle");
						$(checkedtds[index]).parent().parent().children().eq(5).addClass("TimeStyle");
						$(checkedtds[index]).parent().parent().children().eq(4).find("input").val("");
						$(checkedtds[index]).parent().parent().children().eq(5).find("input").val("");
					});
		
					} 
			}
			
			function addUser(){
				var gatewayCode = $("#gatewayCode").val();
				var roomId = $("#roomId").val();
				var lockno = $("#serialNo").val();
				var branchIdNum = $("#branchIdNum").val();
				var branchIdName = $("#branchIdName").val();
				window.parent.parent.JDialog.open("添加用户", base_path + "/turnToAddFreUser.do?gatewayCode="+gatewayCode+"&roomId="+roomId+"&lockno="+lockno+"&branchIdNum="+branchIdNum+"&branchIdName="+branchIdName,495,418);
			}
			
			function relogin(){
				window.location.reload();
			}
</script>
</body>

</html>