<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>授权记录</title>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/addDelFixedUser.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
		</head>
	
<body>
	<div class="userbox">
		<div id="addBut" class="btn-contrl" >
		 	<span class="fl  spanbold">授权详情</span><span class="btn add fr" style="background-color: #FC8A15" onclick="allDELRight();">删除授权</span>
		</div>
		 	<form id="myForm" class="form-horizontal" role="form" method = "post"  >
		 	<input type="hidden" id="idcard" value="${idcard}"/>
		 	<table id="showData" align="center">
		 		<thead>
		 			<tr id="trFirst">
		 			<td class="fontbold" id="all">
						<input type="checkbox" onchange="updateallcheckbox(this)"/><span style="margin-left: 5px;">全选</span>
					</td>
		 			<td class="fontbold">归属/楼层/房号</td><td class="fontbold">网关</td>
		 			<td class="fontbold">门锁</td><td class="fontbold">开始时间</td><td class="fontbold">结束时间</td>
		 			</tr>
		 		</thead>
		 		<tbody style="height:110%;">
				 	<c:forEach items="${DoorOpenRightList}" var="sl">
				 		<tr id="${sl.DATAID}"  class="load">
				 		<td onclick='aa(this)'><input type='checkbox'></td>
				 		<td>${sl.HOUSENAME}/${sl.FLOORID}/${sl.ROOMID}</td><td>${sl.GATEWAYCODE}</td>
				 		<td>${sl.LOCKCODE}</td><td>${fn:substring(sl.STARTTIME,0,19)}</td><td>${fn:substring(sl.ENDTIME,0,19)}</td>
						</tr>
				 	</c:forEach>
				</tbody>
		 	</table>
	</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>	
<script type="text/javascript">
	var array = new Array();
	var base_path ="<%=request.getContextPath()%>";
	$(document).ready(function() {
		
	});
	
	function updateallcheckbox(e){
		var checked = $(e).prop('checked');
		var checkedtds = $("td input[type='checkbox']");
		if(checked){
			$.each(checkedtds, function(index){
					$(checkedtds[index]).prop("checked", true);
			});
		} else {
			$.each(checkedtds, function(index){
					$(checkedtds[index]).prop("checked", false);
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
		$.each(checkedtds, function(index){
			if($(checkedtds[index]).prop("checked")){
				count++;
			} else {

			}
		});
	
		var checkbox = $(e).parent().find(':checkbox');
		var checked = checkbox.prop("checked");
		//checkbox.stopPropagation();
		 if(checked){ 	
			count--;			
		} else {
			count++;
		} 
	    checkbox.prop('checked', !checked);
	    if(count == checkedtdslen.length){
	    	$("#all input").prop('checked',true);
	    } else {
	    	$("#all input").prop('checked',false);
	    }
	}
			
	function allDELRight(){
		 var count = 0;
		 var dataidArray = new Array();
		 var checkedtds = $(".load td input[type='checkbox']"); 
		$.each(checkedtds, function(index){
			if($(checkedtds[index]).prop("checked")){
				count++;
				var dataid = $(checkedtds[index]).parent().parent().attr("id");
				dataidArray.push(dataid);
			} else {

			}
		});

		if(count == 0){
			window.parent.parent.showMsg("请先选择一条记录！");
			return false;
		}
		
		$.ajax({	
			url : base_path + '/deleteUserRightNew.do',
			dataType : "json",
			type : "post",
			data : { 
				dataidArray : JSON.stringify(dataidArray)},
			success : function(json){
				if(json.result == 1){
					window.parent.parent.showMsg("删除成功！");
					window.parent.setTimeout("window.parent.parent.location.reload(true)", 1000);
				}else{
					if(json.message != null){
						window.parent.parent.showMsg(json.message);
					} else {
						window.parent.parent.showMsg("删除失败");
						window.parent.setTimeout("window.parent.parent.location.reload(true)", 1000);
					}
				}
			},
			error:function(json){
				window.parent.parent.showMsg("操作失败！" + json);
				window.parent.setTimeout("window.parent.parent.location.reload(true)", 1000);
			}
		});	 
		
	}

</script>
</body>

</html>