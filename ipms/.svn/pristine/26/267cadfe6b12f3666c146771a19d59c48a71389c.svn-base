<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/campaigns.css" media="all" />
		<title>公寓管家管理页面</title>
	</head>
	<style>
	#apartmentmanageredit .text_search {
    padding: 8px 5px;
    width: 438px;
    margin-left: 10px;
}
	.gray{
	    background: #f0f0f0;
	}
	td.must{
	color:red;
	}

	#apartmentmanageredit td{
	    width: 18%;
	    text-align: right;
	    vertical-align: middle;
	    border: 0 none;
	    padding: 2px;
	    white-space: nowrap;
	}
	#apartmentmanageredit .longinput{
	    width: 437px;
	}
	
	</style>
	<body>
		<form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data"  >
		<div>
		 <input type="hidden" id ="staffid" name="staffid" value="${manager.staffId}" /> 
			<table id="apartmentmanageredit">
				<tr>
					<td style="width:8%;" >
						员工姓名
					</td>
					<td ><input class="gray" id="staffname" value="${manager.staffName}" placeHolder="请输入1-10个有效字符"  maxlength="10" name="staffname" readonly></input></td>
				
					<td style="width:13%;" >
						登录名
					</td>
					<td >
						<input class="gray" id="loginname" maxlength="10" value="${manager.loginName}" placeHolder="字母、数字、下划线组成" 
							name="loginname" readonly></input>
					</td>
				</tr>
				<tr>
					<td style="width:8%;" >
						身份证
					</td>
					<td >
						<input class="gray" id="idcard" maxlength="18"
							name="idcard" value="${manager.idcard}" readonly></input>
					</td>
				
					<td style="width:8%;" class="must">
						手机号
					</td>
					<td >
						<input id="mobile" maxlength="11" 
							name="mobile" value="${manager.mobile}" ></input>
					</td>
				</tr>
				<tr>

					<td style="width:8%;" >
						生日
					</td>
					<td >
						<input class="gray" id="birthday" name="birthday" value="${birthday}" readonly></input>
					</td>
					<td style="width:8%;" >
						性别
					</td>
					<td >
					<c:if test="${manager.gendor eq '1'}"> 
						<input  class="gray" id="gender" name="gender" value="男" readonly></input>
						<input  type="hidden" id="gender_hidden" name="gender_hidden" value="1" ></input>
					</c:if> 
					<c:if test="${manager.gendor eq '0'}"> 
						<input  class="gray" id="gender" name="gender" value="女" readonly></input>
						<input  type="hidden"  id="gender_hidden" name="gender_hidden" value="0" ></input>
					</c:if> 
<!-- 						<select id="gender" class="inputStyle gray" name="gender" > -->
<!-- 							<c:if test="${manager.gendor eq '1'}"> -->
<!-- 						<option value="1">男 </option> -->
<!-- 						</c:if> -->
<!-- 						<c:if test="${manager.gendor eq '0'}"> -->
<!-- 						<option value="0">女 </option> -->
<!-- 						</c:if> -->
<!-- 								<option value="1" <c:if test="${manager.gendor eq '1'}">selected="selected"</c:if>> -->
<!-- 									男 -->
<!-- 								</option> -->
<!-- 								<option value="0" <c:if test="${manager.gendor eq '0'}">selected="selected"</c:if>> -->
<!-- 									女 -->
<!-- 								</option> -->
<!-- 			            </select> -->
					</td>
				</tr>
				<tr>
					<td style="width:8%;" >
						入职时间
					</td>
					<td >
						<input class="gray" id="entrytime" name="entrytime" value="${entrytime}" readonly></input>
					</td>
				
					<td style="width:8%;">
						邮件
					</td>
					<td >
						<input id="email"  
							name="email" value="${manager.email}"></input>
					</td>
				</tr>
				<tr>
					<td style="width:8%;">
						地址
					</td>
					<td colspan = "3">
						<input id="address" maxlength="30" 
							name="address" class="longinput" value="${manager.address}" ></input>
					</td>
				</tr>
				<tr>
					<td style="width:8%;">
						备注
					</td>
					<td colspan = "3"><textarea id="remark" name="remark" class="text_search textarea_text" rows="2" maxlength="60" >${manager.remark}</textarea>
<!--						<input id="remark" maxlength="" -->
<!--							name="remark" class="longinput"></input>-->
					</td>
				</tr>
			</table>
	</div>
	<div class="contractbutton">
		<div class="button div_button save" style="display:inline-block;float:right;margin: 20px 48px;" onclick="submitdata()">提交</div>
	</div>
	</form>
	
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
        <script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
		var base_path ="<%=request.getContextPath()%>";	
//		 laydate({
//	        	elem: '#birthday'
	        	
// 		});
//		laydate({
//       	elem: '#entrytime'
	        	
//		});
		$(function(){
		var judgestaffid = $("#staffid").val();
		if(judgestaffid == ""){
		showMsg("未找到管家信息，无法进行操作！"); 
		}
		
		});
	
		function submitdata(){
		
		var idcardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var staffid = $("#staffid").val();
		if(staffid == ""){
		showMsg("未找到管家信息，无法进行操作！"); 
		return false;
		}
		var staffname = $("#staffname").val();
		var loginname = $("#loginname").val();
		var idcard = $("#idcard").val();
		var mobile = $("#mobile").val();
		var birthday = $("#birthday").val();
		var entrytime = $("#entrytime").val();
		var email = $("#email").val();
		var address = $("#address").val();
		var remark = $("#remark").val();
		var gender = $("#gender_hidden").val();
		
		var arr=new Array(
		     new Array('staffname','管家姓名不可为空!'),
		     new Array('loginname','登录名不可为空!'),
		     new Array('idcard','身份证不可为空!'),
		     new Array('mobile','手机号不可为空!'), 
		     new Array('birthday','出生日期不可为空!'), 
		     new Array('entrytime','入职日期不可为空!')
		    
		 );
		 
		 if(!checkFormIsNull(arr) ){
	       return false;
	     }
		
	
		
		if(!(/^[a-zA-Z][a-zA-Z0-9_]*$/.test(loginname))){
		  showMsg("登录名由字母、数字、下划线组成，开头必须为字母！");
		 return false; 
		}
		
		if (!/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[x])$)$/i.test($("#idcard").val())) {
			showMsg("请输入正确的身份证号码！");
			return false; 
		}
		if(!(/^1[34578]\d{9}$/.test(mobile))){   
	    	  showMsg("手机号格式不正确！");  
	    	  return false;  
	         } 
	         
	     if($("#email").val() != ""){
	      if (!/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/i.test($("#email").val())) {
				showMsg("请输入正确的邮箱！");
				return false; 
			}
	     }
		
		
		
		$.ajax({
		    url: base_path+"/submitApartmentManager.do",
			type: "post",
			dataType: "json",
			data: { 
			         staffid:staffid,
			         staffname :staffname,
		             loginname :loginname,
					 idcard :idcard,
					 mobile :mobile,
					 gender:gender,
					 birthday :birthday,
					 entrytime :entrytime,
					 email :email,
					 address :address,
					 remark:remark
					 },
		    success: function(json) {
				if(json.result == 1){
					showMsg("修改成功！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}else{
				showMsg(json.message);
			}
			},
			error : function(data){
				}
		
		});
		
		}	
		
		
		
			
	//批量验证表单非空
		function checkFormIsNull(arr){
			 for(var i=0;i<arr.length;i++){
				 if($("#"+arr[i][0]).val()==''){
					 showMsg(arr[i][1]);
					 return false;
				 }
			 }
			 return true; 
		};
		
		
		
		function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}	
		</script>

	</body>
</html>