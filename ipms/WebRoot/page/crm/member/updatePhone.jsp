<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    
    <title>更改手机号</title>
    
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
  <style type="text/css">
  	#updateMbuttom {
  		position: absolute;
	    left: 107px;
	    top: 58px;
  	}
  	#ul_custom {
  		position: absolute;
	    top: -30px;
	    left: 48px;
  	}
  	#addCustom_li {
  		margin: 10;
  	}
  	#memberMobile {
  		background-color: white;
    	border: 1px solid #d3dbde;
  	}
  	#oldMobile {
  		margin-left: 15px;
  	}
  </style>
  </head>
  <body>
 	<div style="width: 300px;height: 150px;margin-top: 20px;">
		<div style="height: 185px;">
			<input id="member_Id" type="hidden" value="${member.memberId }" readonly="readonly"/>
			<ul class="ul_custom" id="ul_custom">
				<li class="addCustom_li" id="addCustom_li">
					<label>原手机号:</label>
					<input type="text" id="oldMobile" value="${mobile }" readonly="readonly"/>
				</li>
				<li class="addCustom_li" id="addCustom_li">
					<label>修改手机号:</label>
					<input type="text" id="memberMobile" name="memberMobile" value="" maxlength="11" placeholder="手机号为必填项！"/>
				</li>
			</ul>
		</div>
		<div class="div_button" id="updateMbuttom">
			<input class="cancel" type="button" value="清空" onclick="empty()"/>
	    	<input class="submit" type="button" value="提交" onclick="updateReserve()"/>  
	    </div>
	</div>
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
	
		base_path = '<%=request.getContextPath()%>';
	
		function empty(){
			$("#memberMobile").val("");
		}
		
		function updateReserve(){
			var memberid = $("#member_Id").val();
			var mobile = $("#memberMobile").val();
			var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
			
			if (!myreg.test(mobile)) {
				showMsg("请输入正确的手机号！");
				return false;
			}
			
			
			$.ajax({
				url: base_path + "/recoverMember.do",
				type: "post",
				dataType: "json",
				data: { memberid : memberid, 
						nowMobile : mobile },
				success: function(json) {
					if(json.result == '1'){
						showMsg(json.message);
						window.setTimeout("window.parent.location.reload(true)", 1800);
					}else{
						showMsg(json.message);
					}
				},
				error: function(json) {
				}
			})
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