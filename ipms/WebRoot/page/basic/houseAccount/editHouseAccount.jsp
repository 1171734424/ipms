<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>民宿账号编辑</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
  </head>
  <style>
  	.editCity{
  		
  	}
  </style>
  <body>
  
 	<div>
		<div>
			<ul class="editCity">
				<li class="addCustom_li">
					<label>账号名称:</label>
					<input type="text" id="accountName" name="accountName" value="${account.HOUSEACCOUNTNAME}" readonly/>
				</li>
				<li class="addCustom_li">
					<label>账号管理员:</label>
					<input class="inputStyle" id="staffname" value="${account.HOUSEMANAGER}"  readonly/>
    				<input type="hidden" id="staffname_CUSTOM_VALUE" name="staffid" value="${account.HOUSEMANAGERID}" />
				</li>
				<%--  <li class="addCustom_li">
					<label>状态:</label>
					<select id="status" name="status" class="inputStyle">
					    <option value="1" <c:if test="${account.STATUS eq 1}">selected="selected"</c:if>>有效</option>
						<option value="0" <c:if test="${account.STATUS eq 0}">selected="selected"</c:if>>无效</option>
    					
					</select>
				</li>  --%>
			</ul>
			
		</div>
		<div class="div_button">
			<input class="cancel" type="button" value="关闭" onclick="window.parent.JDialog.close()"/>
	    	<input class="submit" type="button" value="提交" onclick="submit()"/>  
	    </div>
	</div>
	
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
	var base_path = '<%=request.getContextPath()%>';
	jQuery(document).ready(function() {
		
	});
	 $("#staffname").bind("click",function(){
		 	JDialog.open("管理员", base_path + "/selectHouseStaff.do",450,350);
		});
	function submit(){
		var staffId = document.getElementById("staffname_CUSTOM_VALUE").value;
		/* var status = document.getElementById("status").value; */
		var accountName = document.getElementById("accountName").value;
		if(staffId.length == 0){
			showMsg("请选择至少一个管理员");
			return false;
		}
		 $.ajax({
			url: base_path+"/updateHouseAccount.do",
			type: "post",
			dataType: "json",
			data:{
				staffId:staffId,
				accountName:accountName
				
			},
			success: function(json) {
				var result = json.result;
				if(result == 1){
					showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
					window.parent.refreshGrid();
				}
			},
			error: function(json) {
				showMsg("操作失败！");
			}
		});
		 
		
	}
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
	
	</script>
</html>
