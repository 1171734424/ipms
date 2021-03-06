<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
  %>
<html>
  <head>
    <title>添加用户</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datepicker.css"/>	
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/pmsmanage/pmsmanage.css" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<style type="text/css">
		.myUlInfo{
		        width: 170px;
			    height: 200px;
			    position: absolute;
			    z-index: 1;
			    border: 1px solid #ccc;
			    left: 235px;
			    top: 58px;
			    background: #fff;
			    overflow: hidden;
		}
		li{
			list-style-type:none;
			height:28px;			
		}
	</style>
	<script> 
	var base_path = '<%=request.getContextPath()%>';
	</script>
</head> 
  <body class="room-body">
     <form  name="myFrom" id="myForm" class="ctdform">
		 <table class="ptable crm-table" border="0" cellspacing="0" cellpadding="0">
		    <tbody>
		     <col width="13%">
		     <col width="80%">
		     <col width="2%">
		     <col width="2%">
		     <tr>
				   <td class="rm_tabletop"></td>
				   <td></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
		     </tr>

			 <tr>
				   <td class="ctdtitle notnull" align="center">姓名</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="userName" class="ptdinput"  maxlength="20"/> </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
			 </tr>
			 <tr>
				   <td class="ctdtitle notnull" align="center;">身份证</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="idcard" class="ptdinput" maxlength="18"/></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
			 </tr>
			 <tr>
				   <td class="ctdtitle notnull" align="center;">性别</td>
				   <td align="left" class="pinputtd">
				   	<select id="gender" name="gender" class="ptdinput" >
						<option value="">请选择性别</option>
						<option value="1" <c:if test="${gender == '1'}">selected="selected"</c:if>>男</option>
						<option value="0" <c:if test="${gender == '0'}">selected="selected"</c:if>>女</option>	  
				   	</select>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
			 </tr>
			 <tr>
				   <td class="ctdtitle notnull" align="center;">手机</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="mobile" class="ptdinput" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="11"/></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
			 </tr>
			 <tr>
				   <td class="ctdtitle notnull" align="center;">职务</td>
				   <td align="left" class="pinputtd">
				      <input type="text" id="post" class="ptdinput" />
				      <input type="hidden" id="post_CUSTOM_VALUE" class="ptdinput" />
				      </td>
				    <td class="ctdtitle"></td>
				    <td class="ctdtitle"></td>
			</tr>
			<tr>
				    <td class="ctdtitle" align="center;" >市</td>
				    <td align="left" class="pinputtd" >
					   <input type="text" id="cityname" class="ptdinput" />
					   <input type="hidden" id="city" class="ptdinput" />
					   <ul class="myUlInfo" style="display:none;">
						</ul>
						<select class="inputStyle ptdinput" id="cityCode" style='display:none'>
							<option value="" id="cityopt"></option>		
						</select>
					   </td>
				    <td class="ctdtitle"></td>	
				    <td class="ctdtitle"></td>
			</tr>
			
			<tr>
				    <td class="ctdtitle" align="center;" >区</td>
				    <td align="left" class="pinputtd" >
					   <select class="ptdinput inputStyle district_add" id="district" onchange="chooseDistrict()">
							 <c:if test="${fn:length(districtList) eq 0}">
								<option value="">暂无区域记录</option>
							</c:if> 
							<c:forEach items="${districtList }" var="districtList">
								<option value='${districtList["ADMINI_CODE"]}' id="districtopt" <c:if test="${districtList['ADMINI_CODE'] eq district.adminiCode}">selected="selected"</c:if> >${districtList["ADMINI_NAME"]}</option>
							</c:forEach>
						</select>
				    <td class="ctdtitle"></td>	
				    <td class="ctdtitle"></td>
			</tr>
			<tr style="height:72px">
				    <td class="ctdtitle" align="center;" style="height:72px">地址</td>
				    <td align="left" class="pinputtd" style="height:72px">
					   <input type="text" id="address" class="ptdinput" style="height:-webkit-fill-available" maxlength="40"/></td>
				    <td class="ctdtitle"></td>	
				    <td class="ctdtitle"></td>
			</tr>
			<tr style="height:72px">
				    <td class="ctdtitle" align="center;" style="height:72px">备注</td>
				    <td align="left" class="pinputtd" style="height:72px">
					   <input type="text" id="remark" class="ptdinput" style="height:-webkit-fill-available" maxlength="100"/></td>
				    <td class="ctdtitle"></td>	
				    <td class="ctdtitle"></td>
			</tr>									
		</tbody>
	</table>
		<input type="hidden" id="serialNo" name="serialNo" value="${serialNo}"/>
		<input type="hidden" id="gatewayCode" name="gatewayCode" value="${gatewayCode}"/>
		<input type="hidden" id="roomId" name="roomId" value="${roomId}"/>
		<input type="hidden" id="branchIdNum" name="branchIdNum" value="${branchIdNum}"/>
		<input type="hidden" id="branchIdName" name="branchIdName" value="${branchIdName}"/>			
	 </form>
	  <hr class="ctdhr"/>
	 <div class="contractbutton parents-content">
	 <div class="pdepartsubmit crm-confirmbt" onclick="addFreOneUser()">提交</div>
	 <div class="pdepartcancle crm-cancelbt" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
	 <script>
	 var newCity = ${newCity};
	 
	 $(function(){
		 $("#post").bind("click", function() {
				var javaName = "Post";
				var colsName = "postId,postName";
				var queryConditions = "status=1";
				var inputId = "post";
				var url = base_path + "/pageConditionOpenJDialog.do?inputId=" + inputId + "&javaBeanName=" + javaName + "&colsName="+colsName+"&queryConditions="+queryConditions;
				JDialog.open("查询", url, 450, 350);
			});
		 
		 $("#cityname").bind("input propertychange", function() {
			   	var value = $(this).val();
			   	if (value) {

			   		var re = /[\u4e00-\u9fa5]/g;
					var flg = false;
					if (re.test(value)) 
						flg = true;

			   		var values;
			   		var ln;
				   	if (flg == false) {
				   		values = value.toUpperCase();
				   		ln = values.length;
				   	} else {
				   		values = value;
				   		ln = value.length;
				   	}
				   	
				   	var str = "";
				   	$.each(newCity, function(index) {
				   		var city = newCity[index];
				   		var pin = city.py.substring(0, ln);
				   		var name = city.name.substring(0, ln);
				   		if (values == pin || values == name) {
				   			str += '<li onclick="chooseCity(this)"><input value="' + city.name+ '" type="text" style="width:100%;height:90%;border: 0px;" class="adminNames" readonly/><input class="adminCodes" type="hidden" value="' + city.code +'"/></li>'
				   		}
				   	})
				   	$(".myUlInfo").html(str);
				   	$(".myUlInfo").show();
			   	} else {
			   		$(".myUlInfo").hide();
			   	}
			});
	 });
	 
	 function addFreOneUser() {
		 	var reg = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
		 	var userName = $("#userName").val();
			var idcard = $("#idcard").val();
			var gender = $("#gender").val();
			var mobile = $("#mobile").val();
			var post = $("#post_CUSTOM_VALUE").val();
			var city = $("#city").val();
			var district = $("#district").val();
			var address = $("#address").val();
			var remark = $("#remark").val();
			
			if (!userName) {
				showMsg("姓名不能为空");
			} else if (!idcard) {
				showMsg("身份证不能为空");
			} else if (!gender) {
				showMsg("性别不能为空");
			} else if (!mobile) {
				showMsg("手机不能为空");
			} else if (!post) {
				showMsg("职位不能为空");
			} else if (!reg.test(idcard)) {
				showMsg("身份证不合法，请重新输入");
			} else if (!/^((13|15|17|18)+\d{9})$/i.test(mobile)) {
				showMsg("手机号不合法，请重新输入");
			} else {
				
				$.ajax({
					url : base_path + "/addUserFrequetter.do",
					type : "post",
					dataType : "json",
					data : {
						'userName' : userName,
						'idcard' : idcard,
						'gender' : gender,
						'mobile' : mobile,
						'post' : post,
						'city' : city,
						'district' : district,
						'address' : address,
						'remark' : remark
					},
					success : function(json) {
						if (1 == json.result) {
							if (json.message) {
								showMsg(json.message);
							} else {
								showMsg("新增成功！");
								
								window.setTimeout("window.parent.JDialog.close();",1000);
								
								/* var gatewayCode = $("#gatewayCode").val();
								var roomId = $("#roomId").val();
								var lockno = $("#serialNo").val();
								var branchIdNum = $("#branchIdNum").val();
								var branchIdName = $("#branchIdName").val();
								 */
								//window.parent.JDialog.open("添减用户", base_path + "/doorAddUser.do?gatewayCode="+gatewayCode+"&roomId="+roomId+"&lockno="+lockno+"&branchIdNum="+branchIdNum+"&branchIdName="+branchIdName,1100,589);
							
								
							}

						} else {
							showMsg("新增失败！");
							window.setTimeout("window.parent.location.reload(true)",
									1000);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("window.parent.location.reload(true)", 1000);
						window.setTimeout("window.parent.JDialog.close();", 1000);
					}
				});
			}   
		}

	 	function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
	 	
	 	 //城市选择联动新
	    function chooseCity(e){
	 	   var cityAdminiCode = $(e).find(".adminCodes").val();
	 	   var cityAdminiName = $(e).find(".adminNames").val();
	 	   $("#cityCode").val(cityAdminiCode);
	 	   $("#city").val(cityAdminiCode);
	 	   $("#cityname").val(cityAdminiName);
	 	   $(".myUlInfo").hide();
	 	   $.ajax({
	 		    url: base_path+"/queryPlaceByCity.do",
	 			type: "post",
	 			dataType: "json",
	 			data: {
	 				cityAdminiCode:cityAdminiCode
	 			},
	 			success: function(data) {
	 			
	 				var districtList=data.data.districtList;
	 				var streetList=data.data.streetList;
	 				var districtHtmlString="";
	 				var streetHtmlString = "";
	 				if(districtList.length==0){
	 					districtHtmlString+="<option value=''>暂无区记录</option>";
	 				}else{
	 					jQuery.each(districtList, function(i,item){
	 					 	var code=item["ADMINI_CODE"];
	 						var name=item["ADMINI_NAME"];
	 						districtHtmlString+="<option value='"+code+"'>"+name+"</option>";
	 				      }); 
	 				}
	 				if(streetList.length==0){
	 					streetHtmlString+="<option value=''>暂无街道记录</option>";
	 				}else{
	 					jQuery.each(streetList, function(i,item){
	 					 	var code=item["ADMINI_CODE"];
	 						var name=item["ADMINI_NAME"];
	 						streetHtmlString+="<option value='"+code+"'>"+name+"</option>";
	 				      }); 
	 				}
	 				$("#district").html('').append(districtHtmlString); 
	 				$("#street").html('').append(streetHtmlString); 
	 				$("#property").val(3);
	 				$(".street_title").last().html('').append("街道"); 
	 				 $(".subway_title").hide();  
	 				  $(".subway_add").hide();  
	 			},
	 			error: function() {
	 			}
	 		});
	    }
	 	 
	  //区域联动新
	    function chooseDistrict(){
	 		  var districtAdminiCode=$("#district").val();
	 		  var rank = $("#property").val();
	 		  if(rank == 7){
	 			  return ;
	 		  }
	 		   $.ajax({
	 				url: base_path+"/queryPlaceByDistrict.do",
	 				type: "post",
	 				dataType: "json",
	 				data: {
	 					districtAdminiCode:districtAdminiCode,
	 					rank:rank
	 				},
	 				success: function(data) {
	 					var streetList=data.data.list;
	 					var streetHtmlString="";
	 					 if(streetList.length==0){
	 						 if(rank == 3){
	 							 streetHtmlString+="<option value=''>暂无街道记录</option>";
	 						 }else if(rank == 4){
	 							 streetHtmlString+="<option value=''>暂无商业圈记录</option>";
	 						 }else if(rank == 5){
	 							 streetHtmlString+="<option value=''>暂无学校记录</option>";
	 						 }else if(rank == 6){
	 							 streetHtmlString+="<option value=''>暂无景点记录</option>";
	 						 }
	 						
	 					 }else{
	 						 jQuery.each(streetList, function(i,item){
	 						 	var code=item["ADMINI_CODE"];
	 							var name=item["ADMINI_NAME"];
	 						 streetHtmlString+="<option value='"+code+"'>"+name+"</option>";
	 					      }); 
	 					 }
	 					 $("#street").html('').append(streetHtmlString); 

	 				},
	 				error: function() {
	 				}
	 			});
	 	  }
	  
	  function reloadParentPage(){
		  	var gatewayCode = $("#gatewayCode").val();
			var roomId = $("#roomId").val();
			var lockno = $("#serialNo").val();
			var branchIdNum = $("#branchIdNum").val();
			var branchIdName = $("#branchIdName").val();
			
			window.parent.JDialog.open("添减用户", base_path + "/doorAddUser.do?gatewayCode="+gatewayCode+"&roomId="+roomId+"&lockno="+lockno+"&branchIdNum="+branchIdNum+"&branchIdName="+branchIdName,1100,589);
			
	  }
	 </script>
</body>
</html>