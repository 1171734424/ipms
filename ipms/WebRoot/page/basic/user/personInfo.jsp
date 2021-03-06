<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<%	
request.setAttribute("state", request.getAttribute("state"));
%>
<!DOCTYPE>
<html style="width: 100%; height: inherit;">
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="<%=request.getContextPath()%>/css/ipms/css/personinfo/personinfo.css" rel="stylesheet" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet"  media="all"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />	
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
		<style>
			input,select,textarea {
			    line-height: 1.5;
			}
			textarea{
				margin: 4px;
				padding: 8px 5px;
	   			width: 740px;
   			}
   			.two_button{
   				float: left;
			    display: inline-block;
			    padding: 0 14px;
			    margin-left: 77.5% !important;
			    height: 32px;
			    line-height: 32px;
			    background-color: #ff9800;
			    border-radius: 3px;
			    font-size: 14px;
			    border:none;
			    cursor: pointer;
			    margin-right: 6px;
			    color:#fff;
   			}
   			.container{
   			width:50%;
   			}
   			.Tinput input,.Tinput select{
   			width:100%}
		</style>
	</head>
	<body>
		<form id="myForm">
			<div class="container" style="background-color:#fff">
				<div class="Pborder">
					<table>
						<tr class="Ttr">
							<td align="left" style='width:100px'>
								<div class="password_font"> 员工号：</div>
							</td>
							<td align="center" class="Tinput" style='width:275px'>
								<input type="text" class="text_search" readonly="readonly" id="staffId" name="staffId" value="${staffInfo.staffId}"></input>
							</td>
							<td align="left" style='width:100px' >
								<div class="password_font"> 员工姓名 ：</div>
							</td>
							<td align="center" class="Tinput" style='width:275px'>
								<div class="Tinput1"><input class="text_search" id="staffName" name="staffName" value="${ staffInfo.staffName}"/></div>
							</td>
						</tr>
						
						<tr class="Ttr">
						   <td align="left">
								<div class="password_font"> 职务 ：</div>
							</td>
							<td align="center" class="Tinput">
								<input class="text_search" type="text" id="post" name="post" readonly="readonly" value="${postName}" />
							</td>
							<td align="left" style="width:18%;">
								<div class="password_font"> 归属门店 ：</div>
							</td>
							<td align="center" class="Tinput" >
								<div class="Tinput1">
									<input class="text_search" id="branchId" name="branchId" type="text" readonly="readonly"  value="${branchName}"/>
								</div>
							</td>
						</tr>
						
						<tr class="Ttr">
						    <td align="left" >
							  <div class="password_font"> 登录名：</div>
							</td>
							<td align="center" class="Tinput">
								<input class="text_search" id="loginName" name="loginName" value="${staffInfo.loginName}"/>
							</td>
							<td align="left" >
								<div class="password_font"> 性别：</div>
							</td>
							<td align="center" class="Tinput">
								<%--<input class="text_search" id="gendor" name="gendor" value="${mygendor}" />
								--%><select class="text_search" id="gendor" name="gendor" value="${mygendor}">
									
									<option value="1" <c:if test="${'1' eq mygendor}">selected</c:if>>男</option>
									<option value="0" <c:if test="${'0' eq mygendor}">selected</c:if>>女</option>
								</select>
							</td>
						</tr>
						
						<tr class="Ttr">
						    <td align="left" >
							<div class="password_font"> 部门：</div>
							</td>
							<td align="center" class="Tinput" >
								<input class="text_search" type="text"  id="department" name="department" readonly="readonly" value="${departName}"></span>
							</td>
							<td align="left" style="width:18%;">
								<div class="password_font"> 生日 ：</div>
							</td>
							<td align="center" class="Tinput" >
								<%--<div class="Tinput1"><input class="text_search" id="birthday" name="birthday" value="${birthday}"/></div>--%>
								<div class="Tinput1"><input id="birthday1" name="birthday1" value="${birthday}" class="date_text text_search" type="text"/></div>
							</td>
						</tr>

                        <tr class="Ttr">
							<td align="left" style="width:15%;">
								<div > 手机号 ：</div>
							</td>
							<td align="center" class="Tinput" >
								<input class="text_search" id="mobile" name="mobile" value="${staffInfo.mobile}" />
							</td>
							<td align="left" style="width:18%;">
								<div> 身份证号 ： </div>
							</td>
							<td align="center" class="Tinput" >
								<input class="text_search" id="idcard" name="idcard" value="${ staffInfo.idcard}" />
							</td>
						</tr>
						
						<tr class="Ttr">
							<td align="left" style="width:15%;">
								<div> 邮箱：</div>
							</td>
							<td align="center" class="Tinput" >
								<input class="text_search" id="email" name="email" value="${ staffInfo.email}" />
							</td>
							<td align="left" style="width:18%;">
								<div class="password_font"> 入职时间 ：</div>
							</td>
							<td align="center" class="Tinput" >
								<%--<div class="Tinput1"><input class="text_search" id="birthday" name="birthday" value="${birthday}"/></div>--%>
								<div class="Tinput1"><input id="entryTime" name="entryTime" value="${entryTime}" class="date_text text_search" type="text"/></div>
							</td>
						</tr>
						
						<tr class="Ttr">
							<td align="left" style="width:15%;">
								<div> 联系地址：</div>
							</td>
							<td align="center" class="Tinput">
								<%--<input class="textarea" value="${ staffInfo.address}" />
								<textarea rows="2" cols="3" id="address" name="address">${ staffInfo.address}</textarea>
							<td colspan="3" align="center" class="Tinput">--%>
								<textarea id="address" name = "address" rows="4" cols="80%">${ staffInfo.address}</textarea>
							</td>
						</tr>
						<tr class="Ttr">
							<td align="left" style="width:15%;">
								<div>备注：&nbsp</div>
							</td>
							<td align="center" class="Tinput">
								<%--<input class="textarea" value="${ staffInfo.address}" />
								--%><textarea rows="2" cols="3" id="remark" name="remark">${ staffInfo.remark}</textarea>
							</td>
						</tr>
					</table>
				</div>
				<!-- <div><span>确定</span><span>取消</span></div> -->
				<input type="button" class="button_margin submitbotton_membersearch two_button" onclick="editstaffdata()" value="修改资料">
			</div>	
		</form>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/ui.datetimepicker.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
	</body>
	<script>
		var base_path = "<%=request.getContextPath()%>";
		function chosebranch() {
		}
		
		function editstaffdata() {
			var isMob=/^((\+?86)|(\+86))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[012356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;  
			var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
			if (!$("#staffName").val()) {
				showMsg("请填写员工姓名!");
				return false;
			}
			if (!$("#loginName").val()) {
				showMsg("请填写登录名!");
				return false;
			}
			if (!$("#gendor").val()) {
				showMsg("请选择性别!");
				return false;
			}
			if (!$("#mobile").val()) {
				showMsg("请填写手机号!");
				return false;
			}
			if(!isMob.test($("#mobile").val())){  
				showMsg("请填写正确的手机号!");
				return false;
			  } 
			if (!$("#idcard").val()) {
				showMsg("请填写身份证号!");
				return false;
			}
			if ($("#idcard").val().length != 18 && $("#idcard").val().length != 15) {
				showMsg("身份证号格式错误!");  
                return false; 
            }  
			if (!$("#entryTime").val()) {
				showMsg("请选择入职时间!");
				return false;
			}
			if ($("#email").val()) {
				if (!myReg.test($("#email").val())) {
					 showMsg("邮箱格式不正确!");
					 return false;
				}
			}
			$.ajax({
				url:base_path + "/editPersonInfo.do",
				data: $("#myForm").serialize(),
				success:function(json) {
					if (json) {
						showMsg(json.message);
						window.setTimeout("location.reload(true)", 1800);
					}
				},
				error:function (json) {
					showMsg("操作失败");
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

		$(function() {
			$(".date").datetimepicker({ dateFormat: "yy/mm/dd" });
			$("#datetimepicker_div").css('font-size','0.9em');
			
			laydate.render({
				elem: '#birthday1',
				type: 'datetime',
				format: 'yyyy/MM/dd',
				theme: '#4A5064'
			});

			laydate.render({
				elem: '#entryTime',
				type: 'datetime',
				format: 'yyyy/MM/dd',
				theme: '#4A5064'
			});
		})
	</script>
</html>
