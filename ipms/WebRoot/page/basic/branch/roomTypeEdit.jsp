<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'romTypeEdit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<style type="text/css">
		.parents-content-change{
			width: 100%;
    		text-align: center;
		}
	</style>
  </head>
  <body>
  <form name="hiddenForm">
  <input name="branchId" type="hidden" value="${branchId}"/>
  <input name="loginuserBranchId" type="hidden" value="${loginuserBranchId}"/>
  </form>
  <div class = "content">
   <table id="roomTypeEdit"> 
  
   <tr><td  id="no_input"  class="tdone" style="color:red">场景</td><td class="tdtwo">
   													<select id="theme"  class="ptdinput" disabled="disabled">
   														<c:forEach items="${themeList}" var="themeVar">
   														<option value="${themeVar.CONTENT}" <c:if test="${theme eq themeVar.PARAM_NAME}">selected="selected"</c:if>>${themeVar.PARAM_NAME}</option>
   														</c:forEach>
   													</select>
   													<%-- <input id="theme" readonly="readonly" value="${theme}"></input> --%>
   													</td></tr>
  	
											
   <tr>
					<td id="no_input" class="tdone" style="color:red">
						门店名称
					</td>
					<td class="tdtwo"><input id="branch_noselect"   name="branch_noselect" value="${branchName}" readonly></input>
					</td>
					
 
				</tr>													
   <tr><td  id="no_input" class="tdone" style="color:red">房型码</td><td class="tdtwo"><input id="roomType" readonly="readonly" value="${roomType}"></input></td></tr>
   <tr><td  class="tdone" style="color:red">房型名称</td><td class="tdtwo"><input id="roomName"  value="${roomName}" maxlength="8" placeHolder="请输入1-8个有效字符"></input></td></tr>
   
   <tr><td  class="tdone" style="color:red">床位</td><td class="tdtwo"><input id="roomBed"  value="${roomBed}" 
      onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="2" placeholder="请输入1-99的数字"></input></td></tr>
   <tr><td  class="tdone"  style="color:red">床型</td><td class="tdtwo"><select id="roomBedDesc"  class="ptdinput">
   														<c:forEach items="${roomBedDescList}" var="roomBedDesc">
   														<option value="${roomBedDesc.PARAM_DESC}" <c:if test="${roomBedDesc.CONTENT eq bedDesc}">selected="selected"</c:if>>${roomBedDesc.CONTENT}</option>
   														</c:forEach>
   													</select></td></tr>
   <tr><td  class="tdone" style="color:red">宽带</td><td class="tdtwo">
									<select id="roomBroadband"  class="ptdinput">
   														<c:forEach items="${roomBroadbandList}" var="roomBroad">
   														<option value="${roomBroad.PARAM_DESC}" <c:if test="${roomBroad.CONTENT eq broadband}">selected="selected"</c:if>>${roomBroad.CONTENT}</option>
   														</c:forEach>
   													</select>
									</td></tr>
   <tr><td  class="tdone">客房描述</td><td class="tdtwo"><input id="roomDesc"  value="${roomDesc}"></input></td></tr>
   <tr><td  class="tdone">客房朝向</td><td class="tdtwo">
   												<select id="roomPosition"  class="ptdinput">
   														<c:forEach items="${roomPositionList}" var="roompos">
   														<option value="${roompos.PARAM_DESC}" <c:if test="${roompos.CONTENT eq roomPosition}">selected="selected"</c:if>>${roompos.CONTENT}</option>
   														</c:forEach>
   													</select></td>
										</td></tr>
   <tr><td  class="tdone">房间设施</td><td class="tdtwo"><input id="roomDevice" onclick="device()" class="ptdinput" value="${deviceString}" readonly></input></td></tr>
   <tr><td  class="tdone">入住须知</td><td class="tdtwo"><input id="roomTip"  onclick="tip()" class="ptdinput" value="${tipString}" readonly></input></td></tr>
   <tr><td  class="tdone">备注</td><td class="tdtwo"><input  class="ptdinput" id="remark"  ></input></td></tr>
   </table>
   
   <div class="contractbutton">
	 <div class="pdepartsubmit" onclick="submitRoomType()">提交</div>
	 <div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
   </div>
   
	 
	<%--  <c:if test="${theme eq '酒店'}">items="${hotelDeviceList }" </c:if>
	 			<c:if test="${theme eq '公寓'}">items="${apartmentDeviceList }" </c:if>
	 			<c:if test="${theme eq '民宿'}">items="${apartmentDeviceList }" </c:if> --%>
	 			
	 <div class="deviceBody">
		 <div class="deviceTable">
		 <table class="device">
		 <c:if test="${theme eq '酒店'}"> 
		 <c:forEach items="${hotelDeviceList }" var="device" varStatus="status">
		 <c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
		 <tr>
		 </c:if>
		 			<td><input type="checkbox" name="deviceCheckBox" value="${device.PARAM_NAME}">${device.PARAM_NAME}</td>
		 <c:if test="${status.count eq 4 ||status.count%4 eq 0}">
		 </tr>
		 </c:if>		
		 </c:forEach>
		 </c:if>
		 
		 <c:if test="${theme eq '公寓'}"> 
		 <c:forEach items="${apartmentDeviceList }" var="device" varStatus="status">
		 <c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
		 <tr>
		 </c:if>
		 			<td><input type="checkbox" name="deviceCheckBox" value="${device.PARAM_NAME}">${device.PARAM_NAME}</td>
		 <c:if test="${status.count eq 4 ||status.count%4 eq 0}">
		 </tr>
		 </c:if>		
		 </c:forEach>
		 </c:if>
		 
		 <c:if test="${theme eq '民宿'}"> 
		 <c:forEach items="${hotelDeviceList }" var="device" varStatus="status">
		 <c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
		 <tr>
		 </c:if>
		 			<td><input type="checkbox" name="deviceCheckBox" value="${device.PARAM_NAME}">${device.PARAM_NAME}</td>
		 <c:if test="${status.count eq 4 ||status.count%4 eq 0}">
		 </tr>
		 </c:if>		
		 </c:forEach>
		 </c:if>
		 </table>
		 <div class="submitDiv">
		 <span id="confirm"><button id="confirm" onclick="submitDevice()">确定</button></span>
		 <span id="cancel"><button id="cancel" onclick="hiddenDevice()">取消</button></span>
		 </div>
		 </div>
	 </div>
	 
	 <div class="tipBody">
		 <div class="tipTable">
		 <table class="tip">
		 
		 <c:forEach items="${tips }" var="tips" varStatus="status">
		 <c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
		 <tr>
		 </c:if>
		 			<td><input type="checkbox" name="tipCheckBox" value="${tips.CONTENT}">${tips.CONTENT}</td>
		 <c:if test="${status.count eq 4 ||status.count%4 eq 0}">
		 </tr>
		 </c:if>		
		 </c:forEach>
		 
		 </table>
		 <div class="submitDiv parents-content-change">
		 <span id="confirm" class="crm-confirmbt"><button id="confirm" onclick="submitTip()">确定</button></span>
		 <span id="cancel" class="crm-cancelbt"><button id="cancel" onclick="hiddenTip()">取消</button></span>
		 </div>
		 </div>
	 </div>
  </body>
  <script type="text/javascript">
  var base_path = '<%=request.getContextPath()%>';
   function device(){
	  $(".deviceBody").toggle();
   }
   function hiddenDevice(){
	   $(".deviceBody").toggle();
   }
   function submitDevice(){
	   var obj=document.getElementsByName('deviceCheckBox');
	   var s='';
	   for(var i=0;i<obj.length;i++){
		   if(obj[i].checked){
			   s+=obj[i].value+",";
		   }
	   }
	   $("#roomDevice").val(s);
	   $(".deviceBody").toggle();
   }
   function tip(){
		  $(".tipBody").toggle();
	   }
   function hiddenTip(){
	   $(".tipBody").toggle();
   }
   function submitTip(){
	   var obj=document.getElementsByName('tipCheckBox');
	   var s='';
	   for(var i=0;i<obj.length;i++){
		   if(obj[i].checked){
			   s+=obj[i].value+",";
		   }
	   }
	   $("#roomTip").val(s);
	   $(".tipBody").toggle();
   }
   
   function submitRoomType(){
	   var branchId = document.hiddenForm.branchId.value;
	   var loginuserBranchId = document.hiddenForm.loginuserBranchId.value;
	   /*  if('${branchRank}' == '0'){
		   showMsg("总店员工不能修改房型！");
		   return false;
	   }  */
	   var roomType=$("#roomType").val();
	   var roomName=$("#roomName").val();
	   var theme=$("#theme").val();
	   var roomBed=$("#roomBed").val();
	   var bedType=$("#roomBedDesc").val();
	   var broadband=$("#roomBroadband").val();
	   var roomDesc=$("#roomDesc").val();
	   var position=$("#roomPosition").val();
	   var device=$("#roomDevice").val();
	   var tip=$("#roomTip").val();
	   var remark=$("#remark").val();
	   
	   var g = /^[1-9]*[1-9][0-9]*$/;  
	   if(!(g.test(roomBed))){
		   showMsg("床位必须是数字！");
		   return false;
	   }else if(remark.length >100){
		   showMsg("备注最多100个字符!");
		   return false;
	   }
	    $.ajax({
			url: base_path+"/editRoomTypeData.do",
			type: "post",
			dataType: "json",
			data: { roomType: roomType,
					roomName: roomName,
					theme: theme,
					roomBed: roomBed,
					bedType: bedType,
					broadband: broadband,
					roomDesc: roomDesc,
					position: position,
					device: device,
					tip: tip,
					remark: remark,
					branchId : branchId
			},
			success: function(json) {
				var result = json.result;
				if (result == 1) {
					showMsg("保存成功!");
					window.setTimeout("window.parent.location.reload(true)", 1000);
					window.setTimeout("window.parent.JDialog.close();",1000);
					 
				} else if(result == 0){
					showMsg("总店不能编辑酒店公寓房型!");
					 window.setTimeout("location.reload(true)", 1800);
					 window.setTimeout("window.parent.JDialog.close();",1000);
				}else {
					showMsg("保存失败!");
					 window.setTimeout("location.reload(true)", 1800);
					 window.setTimeout("window.parent.JDialog.close();",1000);
				}
			},
			error: function(json) {
				showMsg("操作失败！");
				 window.setTimeout("location.reload(true)", 1800);
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
   /* $("#roomDevice").focus(function(){
	  // alert("dff");
	   $("#deviceBody").toggle();
   }) */
  </script>
</html>
