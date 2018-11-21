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
    
    <title>房价规则新增</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script> 
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<style>
	.inputStyle {
		width: 280px;
	}
	#preLocation {
		width: 280px;
	}
	#aftLocation {
		width: 40px;
	}
	#remarkInput {
		height: 84px;
		width: 97%;
	}
	#keyword {
		height: 44px;
		width: 97%;
	}
	td.must {
		color: red;
	}
	</style>
  </head>
<body onload="init()">
	<table class="branch_table crm-table">
		<tr>
			<td class="must">规则名称</td>
			<td><input class="inputStyle" id="rulesName" maxlength="25"
				placeholder="不能超过25位字符" /></td>
			<td class="must">规则周期</td>
			<td><select class="inputStyle" id=rulesPeriod>
					<c:forEach items="${branchThemList}" var="branchThemList">
						<option value="${branchThemList.content }">${branchThemList.paramName}</option>
					</c:forEach>
			</select></td>
		</tr>

		<tr>
			<td class="must">等级</td>
			<td><select class="inputStyle" id="branchRank"
				<c:if test="${fn:length(uperBranchList) ne 0}">disabled="disabled"</c:if>>
					<c:forEach items="${branchRankList}" var="branchRankList">
						<option value="${branchRankList.paramName}">${branchRankList.content}</option>
					</c:forEach>
			</select></td>
			<td class="must">座机</td>
			<td><input class="inputStyle" id="homePhone"
				placeholder="如 0252554235" /></td>
		</tr>
		<tr>
			<td class="must">邮编</td>
			<td><input class="inputStyle" id="postCode"
				placeholder="如 210000" /></td>
			<td class="must">联系人</td>
			<td><input class="inputStyle" id="connectPerson" maxlength="5" /></td>
		</tr>
		<tr>
			<td class="must">手机</td>
			<td><input class="inputStyle" id="phone" /></td>
			<td class="must">详细地址</td>
			<td><input class="inputStyle" id="preLocation" />
			<%--&nbsp—&nbsp
    	<input class="inputStyle" id="aftLocation" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}",
   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>&nbsp号--%></td>
		</tr>
		<tr>
			<td class="must">城市</td>
			<td><select class="inputStyle" onclick="chooseCity()"
				id="cityCode">
					<c:forEach items="${cityList }" var="cityList">
						<option value="${cityList.adminiCode}" id="cityopt">${cityList.adminiName}</option>
					</c:forEach>
			</select class="must"></td>
			<td class="must">区</td>
			<td><select class="inputStyle" id="district"
				onclick="chooseDistrict()">
					<c:if test="${fn:length(districtList) eq 0}">
						<option value="">暂无区域记录</option>
					</c:if>
					<c:forEach items="${districtList }" var="districtList">
						<option value='${districtList["ADMINI_CODE"]}' id="districtopt">${districtList["ADMINI_NAME"]}</option>
					</c:forEach></td>
		</tr>
		<tr>
			<td>街道</td>
			<td><select class="inputStyle" id="street"
				onclick="chooseStreet()">
					<c:if test="${fn:length(streetList) eq 0}">
						<option value="">暂无街道记录</option>
					</c:if>
					<c:forEach items="${streetList }" var="streetList">
						<option value='${streetList["ADMINI_CODE"]}' id="streetopt">${streetList["ADMINI_NAME"]}</option>
					</c:forEach>
			</select></td>
			<td>商业圈</td>
			<td><select class="inputStyle" id="circle"
				onchick="chooseCircle()">
					<c:if test="${fn:length(circletList) eq 0}">
						<option value="">暂无商圈记录</option>
					</c:if>
					<c:forEach items="${circletList }" var="circletList">
						<option value='${circletList["ADMINI_CODE"]}' id="circleopt">${circletList["ADMINI_NAME"]}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td class="must">经度</td>
			<td><input class="inputStyle" id="longitude" /></td>
			<td class="must">纬度</td>
			<td><input class="inputStyle" id="latitude" /></td>
		</tr>
		<tr>
			<td class="must">门店ip地址</td>
			<td><input class="inputStyle" id="ip" /></td>
			<td class="must">推荐</td>
			<td><select class="inputStyle" id="recommend">
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
		</tr>
		<tr>
			<td>房价审核</td>
			<td><select class="inputStyle" id="flag">
					<option value="0">关</option>
					<option value="1">开</option>
			</select></td>
		</tr>
		<tr>
			<td>关键字</td>
			<td colspan="3"><input class="inputStyle" id="keyword"
				placeholder="多个关键字以逗号分割，不能超过6个关键字！" /></td>
		</tr>
		<tr>
			<td>备注</td>
			<td colspan="3"><input class="inputStyle" id="remarkInput" /></td>
		</tr>
	</table>
	<div class="contractbutton parents-content">
		<div class="pdepartsubmit crm-confirmbt" onclick="submitBranch()">提交</div>
		<div class="pdepartcancle crm-cancelbt"
			onclick="window.parent.JDialog.close()">取消</div>
	</div>
	<input type="hidden" name="latlng" value="" class="mid_tet" readonly />
</body>
<script>
  var base_path = '<%=request.getContextPath()%>';
	$(function() {

	});

	/*  $("#sp_butt").click(function(){ 
	    
	       	  var optionsCity=$("#cityCode option:selected");
	       	  var optionsCityText=optionsCity.text();
	       	  var optionsDistrict=$("#district option:selected");
	       	  var optionsDistrictText=optionsDistrict.text();
	       	  var optionsStreet=$("#street option:selected");
	       	  var optionsStreetText=optionsStreet.text();
	       	  var optionsCircle=$("#circle option:selected");
	       	  var optionsCircleText=optionsCircle.text();
	       	  var homeId=$("#aftLocation").val();
	       	  if(optionsDistrictText=='暂无区域记录'){
	       		  optionsDistrictText='';
	       	  }
	       	  if(optionsStreetText=='暂无街道记录'){
	       		  optionsStreetText='';
	       	  }
	       	  if(optionsCircleText=='暂无商圈记录'){
	       		  optionsCircleText='';
	       	  }
	       	  var location=optionsCityText+optionsDistrictText+optionsStreetText+optionsCircleText+homeId;
	       	 geocoder.getLocation(location); 

	     });  */
	//QQ map. 
	var init = function() {
		var callbacks = {
			//若服务请求成功，则运行以下函数，并将结果传入 
			complete : function(result) {
				var latlng = result.detail.location;

				$("input[name='latlng']").val(latlng);

				/* /^0\d{2,3}-[1-9]\d{6,7}$/ */
				/*  var homePhoneEx="/^[0-9]{5}$/";
				 var result=  homePhoneEx.test(homePhone); */

				/* $.ajax({
					url: base_path+"/saveBranch.do",
					type: "post",
					dataType: "json",
					data: {
						branchName : branchName,
						branchType : branchType,
						branchRank : branchRank,
						homePhone : homePhone,
						postCode : postCode,
						connectPerson : connectPerson,
						phone : phone,
						preLocation : preLocation,
						aftLocation : aftLocation,
						cityCode : cityCode,
						district : district,
						street : street,
						circle : circle,
						remarkInput : remarkInput,
						latlng : latlng,
					},
					success: function(data) {
						
					},
					error : function(data){
						}
					}); */

			},
			//若服务请求失败，则运行以下函数 
			error : function() {
				showMsg("出错了。请先填写地址！");
			}
		}
		//创建类实例 
		geocoder = new qq.maps.Geocoder(callbacks);
		/* getLatlng(); */
	}

	function getLatlng() {
		var optionsCity = $("#cityCode option:selected");
		var optionsCityText = optionsCity.text();
		var optionsDistrict = $("#district option:selected");
		var optionsDistrictText = optionsDistrict.text();
		var optionsStreet = $("#street option:selected");
		var optionsStreetText = optionsStreet.text();
		var optionsCircle = $("#circle option:selected");
		var optionsCircleText = optionsCircle.text();
		/* var homeId=$("#aftLocation").val(); */
		if (optionsDistrictText == '暂无区域记录') {
			optionsDistrictText = '';
		}
		if (optionsStreetText == '暂无街道记录') {
			optionsStreetText = '';
		}
		if (optionsCircleText == '暂无商圈记录') {
			optionsCircleText = '';
		}
		var location = optionsCityText + optionsDistrictText
				+ optionsStreetText + optionsCircleText;
		geocoder.getLocation(location);
	}
	function submitBranch() {
		var latlng = $("input[name='latlng']").val();
		var branchName = $("#branchName").val();
		var branchType = $("#branchType").val();
		var branchRank = $("#branchRank").val();
		var homePhone = $("#homePhone").val();
		var postCode = $("#postCode").val();
		var connectPerson = $("#connectPerson").val();
		var phone = $("#phone").val();
		var preLocation = $("#preLocation").val();
		/* var aftLocation =$("#aftLocation").val(); */
		var cityCode = $("#cityCode").val();
		var district = $("#district").val();
		var street = $("#street").val();
		var circle = $("#circle").val();
		var remarkInput = $("#remarkInput").val();
		var flag = $("#flag").val();
		var originalkeyword = $("#keyword").val();
		var longitude = $("#longitude").val();
		var latitude = $("#latitude").val();
		var ip = $("#ip").val();
		var recommend = $("#recommend").val();
		if (cityCode == "") {
			showMsg("请选择城市！");
			return false;
		}
		if (district == "") {
			showMsg("请选择区域！");
			return false;
		}
		var keyword = originalkeyword.replace(/，/ig, ',');
		if (keyword.endsWith(",")) {
			keyword = keyword.substring(0, keyword.length - 1);
		}
		var keywordString = keyword.split(",");
		if (keywordString.length > 6) {
			showMsg("关键字不能超过6位！");
			return false;
		}
		var flag = 1;
		for (x in keywordString) {
			if (keywordString[x].length > 5) {
				flag = 0;
				break;
			}
		}

		if (flag == 0) {
			showMsg("关键字字符长度不能超过5位！");
			return false;
		}
		if (longitude == "") {
			showMsg("经度不能为空！");
			return false;
		} else {
			var longitudeRex = /^[\-\+]?(0?\d{1,2}\.\d{1,10}|1[0-7]?\d{1}\.\d{1,10}|180\.0{1,10})$/;
			if (!longitudeRex.test(longitude)) {
				showMsg("经度-180.0～+180.0，必须输入1到10位小数！");
				return false;
			}
		}
		if (latitude == "") {
			showMsg("纬度不能为空！");
			return false;
		} else {
			var latitudeRex = /^[\-\+]?([0-8]?\d{1}\.\d{1,10}|90\.0{1,10})$/;
			if (!latitudeRex.test(latitude)) {
				showMsg("纬度-90.0～+90.0，必须输入1到10位小数！");
				return false;
			}
		}

		if (branchName == "") {
			showMsg("门店名称不可为空！");
			return false;
		}
		if (homePhone == "") {
			showMsg("座机不可为空！");
			return false;
		}
		if (postCode == "") {
			showMsg("邮编不可为空！");
			return false;
		}
		if (connectPerson == "") {
			showMsg("联系人不可为空！");
			return false;
		}
		if (phone == "") {
			showMsg("手机号不可为空！");
			return false;
		}
		if (preLocation == "") {
			showMsg("地址不可为空！");
			return false;
		}
		if (ip == "") {
			showMsg("ip地址不可为空！");
			return false;
		}

		var ipRex = /^(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))$/;
		if (!(ipRex.test(ip))) {
			showMsg("ip格式不正确！");
			return false;
		}
		var postCodeRegexp = /^[0-9]{6}$/;
		if (!(postCodeRegexp.test(postCode))) {
			showMsg("邮编格式不正确！");
			return false;
		}
		//(/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/)
		var homePhoneRex = /^((0\d{9,11})|(1[3584]\d{9}))$/;
		if (!(homePhoneRex.test(homePhone))) {
			showMsg("座机格式不正确！");
			return false;
		}

		if (!(/^1[34578]\d{9}$/.test(phone))) {
			showMsg("手机号格式不正确！");
			return false;
		}

		$.ajax({
			url : base_path + "/saveBranch.do",
			type : "post",
			dataType : "json",
			data : {
				branchName : branchName,
				branchType : branchType,
				branchRank : branchRank,
				homePhone : homePhone,
				postCode : postCode,
				connectPerson : connectPerson,
				phone : phone,
				preLocation : preLocation,
				cityCode : cityCode,
				district : district,
				street : street,
				circle : circle,
				remarkInput : remarkInput,
				latlng : latlng,
				flag : flag,
				keyword : keyword,
				longitude : longitude,
				latitude : latitude,
				ip : ip,
				recommend : recommend
			},
			success : function(data) {
				if (data == 1) {
					showMsg("添加成功！");
					window.setTimeout("window.parent.location.reload(true)",
							1000);
					window.setTimeout("window.parent.JDialog.close();", 1000);
				} else if (data == -1) {
					showMsg("无权操作！");
					window.setTimeout("window.parent.location.reload(true)",
							1000);
					window.setTimeout("window.parent.JDialog.close();", 1000);
				}
			},
			error : function(data) {
			}
		});
	}
	//区域选择联动
	function chooseDistrict() {
		var districtAdminiCode = $("#district").val();
		$.ajax({
			url : base_path + "/queryPlaceByDistrict.do",
			type : "post",
			dataType : "json",
			data : {
				districtAdminiCode : districtAdminiCode
			},
			success : function(data) {
				var streetList = data.data.streetList;
				var circletList = data.data.circletList;
				var streetHtmlString = "";
				var circleHtmlString = "";

				if (streetList.length == 0) {
					streetHtmlString += "<option value=''>暂无街道记录</option>";
				} else {
					jQuery.each(streetList, function(i, item) {
						var code = item["ADMINI_CODE"];
						var name = item["ADMINI_NAME"];
						streetHtmlString += "<option value='"+code+"'>" + name
								+ "</option>"
					});
				}
				$("#street").html('').append(streetHtmlString);

				if (circletList.length == 0) {
					circleHtmlString += "<option value=''>暂无商圈记录</option>";
				} else {
					jQuery.each(circletList, function(i, item) {
						var code = item["ADMINI_CODE"];
						var name = item["ADMINI_NAME"];
						circleHtmlString += "<option value='"+code+"'>" + name
								+ "</option>"
					});
				}
				$("#circle").html('').append(circleHtmlString);
				/*  getLatlng(); */
			},
			error : function() {
			}
		});
	}
	//城市选择联动
	function chooseCity() {
		var cityAdminiCode = $("#cityCode").val();
		$.ajax({
			url : base_path + "/queryPlaceByCity.do",
			type : "post",
			dataType : "json",
			data : {
				cityAdminiCode : cityAdminiCode
			},
			success : function(data) {
				var districtList = data.data.districtList;
				var streetList = data.data.streetList;
				var circletList = data.data.circletList;
				var districtHtmlString = "";
				var streetHtmlString = "";
				var circleHtmlString = "";

				if (districtList.length == 0) {
					districtHtmlString += "<option value=''>暂无区域记录</option>";
				} else {
					jQuery.each(districtList, function(i, item) {
						var code = item["ADMINI_CODE"];
						var name = item["ADMINI_NAME"];
						districtHtmlString += "<option value='"+code+"'>"
								+ name + "</option>"
					});
				}
				$("#district").html('').append(districtHtmlString);

				if (streetList.length == 0) {
					streetHtmlString += "<option value=''>暂无街道记录</option>";
				} else {
					jQuery.each(streetList, function(i, item) {
						var code = item["ADMINI_CODE"];
						var name = item["ADMINI_NAME"];
						streetHtmlString += "<option value='"+code+"'>" + name
								+ "</option>"
					});
				}
				$("#street").html('').append(streetHtmlString);

				if (circletList.length == 0) {
					circleHtmlString += "<option value=''>暂无商圈记录</option>";
				} else {
					jQuery.each(circletList, function(i, item) {
						var code = item["ADMINI_CODE"];
						var name = item["ADMINI_NAME"];
						circleHtmlString += "<option value='"+code+"'>" + name
								+ "</option>"
					});
				}
				$("#circle").html('').append(circleHtmlString);

				/* getLatlng(); */
			},
			error : function() {
			}
		});
	}
	function chooseStreet() {
		/* getLatlng(); */
	}
	function chooseCircle() {
		/* getLatlng(); */
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
