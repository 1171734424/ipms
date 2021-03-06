<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>房型添加</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />
	
  </head>
	<div class = "content">
		<form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data"  >
		<div>
			<table id="roomTypeEdit">
				<tr id="houseSelect" style="display:none"><td class="tdone" style="color:red">民宿名称</td><td class="tdtwo"><select class="ptdinput"  name="houseId" id="houseId">
    																				<c:forEach items="${houseList}" var="houseList">				
    																				<option value="${houseList['HOUSE_ID']}">${houseList['HOUSENAME']}</option>
    																				</c:forEach>
    																				</select></td></tr>
				
				<tr>
					<td id="no_input" class="tdone">
						场景
					</td>
					<td class="tdtwo">
						<select id="theme" class="ptdinput" name="theme"
							 onchange="showhours()">
							<c:forEach items="${themeList}" var="themeVar">
								<option value="${themeVar.CONTENT}"
									<c:if test="${theme eq themeVar.CONTENT}">selected="selected"</c:if>>
									${themeVar.PARAM_NAME}
								</option>
							</c:forEach>
						</select>
						<input type="hidden" name="themeId" value="" id="themeId" />
					</td>
				</tr>

				<tr>
					<td id="no_input" class="tdone" style="color:red">
						门店名称
					</td>
					<td class="tdtwo"><input id="branch_select"   name="branch_select" value="${branchName}" readonly></input>
					<input type="hidden" id="branch_select_CUSTOM_VALUE"   name="branch_select_CUSTOM_VALUE" value="${loginUserBranchId}" ></input>
					</td>
					
				</tr>

				<tr>
					<td id="no_input" class="tdone" style="color:red">
						房型码
					</td>
					<td class="tdtwo"><input id="roomType"  placeHolder="请输入1-3个字母或数字" maxlength="3"
			onkeyup="var reg = /^[\da-z]+$/i; if(!reg.test(this.value)) this.value = ''" name="roomType"></input></td>
				</tr>
				<tr>
					<td class="tdone" style="color:red">
						房型名称
					</td>
					<td class="tdtwo">
						<input id="roomName" maxlength="8" placeHolder="请输入1-8个有效字符"
							name="roomName"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone" style="color:red">
						床位
					</td>
					<td class="tdtwo"><input id="roomBed" 
   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}",
   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="2" placeholder="请输入1-99的数字" name="roomBed"></input></td>
				</tr>
				<tr>
					<td class="tdone" style="color:red">
						床型
					</td>
					<td class="tdtwo">
						<select id="roomBedDesc" class="ptdinput" name="bedType">
							<c:forEach items="${roomBedDescList}" var="roomBedDesc">
								<option value="${roomBedDesc.PARAM_DESC}">
									${roomBedDesc.CONTENT}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdone" style="color:red">
						宽带
					</td>
					<td class="tdtwo">
						<select id="roomBroadband" class="ptdinput" name="broadband">
							<c:forEach items="${roomBroadbandList}" var="roomBroad">
								<option value="${roomBroad.PARAM_DESC}">
									${roomBroad.CONTENT}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdone">
						客房描述
					</td>
					<td class="tdtwo">
						<input id="roomDesc" value="${roomDesc}" name="roomDesc"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone">
						客房朝向
					</td>
					<td class="tdtwo">
						<select id="roomPosition" class="ptdinput" name="position">
							<c:forEach items="${roomPositionList}" var="roompos">
								<option value="${roompos.PARAM_DESC}">
									${roompos.CONTENT}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdone">
						房间设施
					</td>
					<td class="tdtwo"><input id="roomDevice" onclick="dev()" class="ptdinput" name="device" readonly></input></td>
				</tr>
				<tr>
					<td class="tdone">
						入住须知
					</td>
					<td class="tdtwo"><input id="roomTip"  onclick="tips()" class="ptdinput" value="${deviceString}" name="tip" readonly></input></td>
				</tr>
				<tr id = "commonPrice">
					<td class="tdone" style="color:red">
						门市价
					</td>
					<td class="tdtwo"><input id="roomTypePrice"   class="ptdinput"  name="roomTypePrice" ></input></td>
				</tr>
				<tr id="hoursPrice" >
					<td class="tdone" style="color:red">
						时租价
					</td>
					<td class="tdtwo"><input id="roomTypeHoursPrice"   class="ptdinput"  name="roomTypeHoursPrice"></input></td>
				</tr>
				<!-- <tr id="cleanPrice" >
					<td class="tdone" style="color:red">
						保洁费用
					</td>
					<td class="tdtwo"><input id="roomTypeCleanPrice"   class="ptdinput"  name="roomTypeCleanPrice"></input></td>
				</tr> -->
				<tr>
					<td class="tdone">
						备注
					</td>
					<td class="tdtwo">
						<input class="ptdinput" id="remark" name="remark"></input>
					</td>
				</tr>
				<input type="hidden" name="branchId" value="${branchId}" id="branchId" />
			</table>
	</div>

	<!--  批量上传图片 -->
	<!--图片选择对话框-->
	<!--确定上传按钮-->
	<div class="contractbutton">
		<div class="pdepartsubmit" onclick="submitRoomType()">提交</div>
		<div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
	</div>
	</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/crm/manage/IMGUP.js"></script>
	<%--  <c:if test="${theme eq '酒店'}">items="${hotelDeviceList }" </c:if>
		  <c:if test="${theme eq '公寓'}">items="${apartmentDeviceList }" </c:if>
		  <c:if test="${theme eq '民宿'}">items="${apartmentDeviceList }" </c:if> --%>

	<div class="deviceBody">
		<div class="deviceTable">
			<table class="device">
				<c:if test="${theme eq '1'}">
					<c:forEach items="${hotelDeviceList }" var="device"
						varStatus="status">
						<c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkbox" name="deviceCheckBox"
								value="${device.PARAM_NAME}">
							${device.PARAM_NAME}
						</td>
						<c:if test="${status.count eq 4 ||status.count%4 eq 0}">
							</tr>
						</c:if>
					</c:forEach>
				</c:if>

				<c:if test="${theme eq '2'}">
					<c:forEach items="${apartmentDeviceList }" var="device"
						varStatus="status">
						<c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkbox" name="deviceCheckBox"
								value="${device.PARAM_NAME}">
							${device.PARAM_NAME}
						</td>
						<c:if test="${status.count eq 4 ||status.count%4 eq 0}">
							</tr>
						</c:if>
					</c:forEach>
				</c:if>

				<c:if test="${theme eq '3'}">
					<c:forEach items="${houseDeviceList }" var="device"
						varStatus="status">
						<c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
							<tr>
						</c:if>
						<td>
							<input type="checkbox" name="deviceCheckBox"
								value="${device.PARAM_NAME}">
							${device.PARAM_NAME}
						</td>
						<c:if test="${status.count eq 4 ||status.count%4 eq 0}">
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
			</table>
			<div class="submitDiv">
				<span id="confirm"><button id="confirm"
						onclick="submitDevice()";>
						确定
					</button>
				</span>
				<span id="cancel"><button id="cancel" onclick="hiddenDevice()";>
						取消
					</button>
				</span>
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
					<td>
						<input type="checkbox" name="tipCheckBox" value="${tips.CONTENT}">
						${tips.CONTENT}
					</td>
					<c:if test="${status.count eq 4 ||status.count%4 eq 0}">
						</tr>
					</c:if>
				</c:forEach>

			</table>
			<div class="submitDiv">
				<span id="confirm"><button id="confirm" onclick="submitTip()">
						确定
					</button>
				</span>
				<span id="cancel"><button id="cancel" onclick="hiddenTip()">
						取消
					</button>
				</span>
			</div>
		</div>
	</div>
</body>
  <script type="text/javascript">
  var loginUserBranchId = '${loginUserBranchId}';
  var systemType = '${systemType}';
  var branchRank = '${branchRank}';
  $(function(){
	  var theme = '${theme}';
	  if(theme == 2 || theme == 3 ){
		  $("#hoursPrice").hide();
	  }
	  if(theme == 1){
		  $("#cleanPrice").hide();
	  }
	  var theme = $("#theme").val();
	   $("#themeId").val(theme);
  })
  
   $("#branch_select").bind("click",function(){
			 	JDialog.open("门店", base_path + "/select4Branch2.do?branchTheme="+ $("#themeId").val(),450,350);
			 	 
			});
  
  var status=0;
  var base_path = '<%=request.getContextPath()%>';
   function dev(){
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
   function tips(){
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
   
   function showhours(){
	   var theme = $("#theme").val();
	   $("#themeId").val(theme);
	   $("#branch_select").val("");
	   $("#branch_select_CUSTOM_VALUE").val("");
	   if(theme == '2' || theme == '3'){
		   $("#hoursPrice").hide();
		   $("#cleanPrice").show();
		   if(theme == '3'){
			   $("#commonPrice").hide(); 
			   $("#houseSelect").show();
		   }else{
			   $("#commonPrice").show();
			   $("#houseSelect").hide();
		   }
	   }else{
		   $("#hoursPrice").show();
		   $("#houseSelect").hide();
		   $("#cleanPrice").hide();
	   }
   }
   function insert(){
	   var theme = $("#theme").val();
	   $("#themeId").val(theme);
	   var th = $("#themeId").val();
	   //alert(th);
   }
   /* function submitRoomType(){
	   var formData = new FormData($("#basicInfo")[0]); 
	
	   if(status==0){
		   showMsg("房型码已存在！");
	   }else{
		   if(roomName!=""&&roomBed!=""&&roomType!=""){
			   $.ajax({
					url: base_path+"/addRoomTypeData.do",
					type: "post",
					dataType: "json",
					data: formData,
					contentType: false,
			        processData: false,
					success: function(json) {
						var result = json.result;
						if (result == 1) {
							showMsg("添加成功！");
							window.setTimeout("window.parent.location.reload(true)", 1000);
							window.setTimeout("window.parent.JDialog.close();",1000);
							 
						} else {
							showMsg("添加失败!");
							 window.setTimeout("location.reload(true)", 1800);
						}
					},
					error: function(json) {
						showMsg("操作失败!");
						 window.setTimeout("location.reload(true)", 1800);
					}
				});
		   }else{
			   showMsg("名称，床位，房型码不可为空！"); 
		   }
	   }
		      
   } */
   
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
