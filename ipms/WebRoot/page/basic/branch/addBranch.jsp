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
    
    <title>门店新增</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<script>
		var newCity = ${newCity};
	</script>
	<style>
	.myUlInfo{
        width: 280px;
    height: 188px;
    position: absolute;
    z-index: 1;
    border: 1px solid #ccc;
    left: 93px;
    top: 8px;
    background: #fff;
    overflow-x: hidden;
    overflow-y: auto;
}
.myUlInfo > li{
height:28px;
}

/*城市联动  */
.subway_add{
display:none;
}
.subway_title{
display:none;
}
	.inputStyle {
		width: 280px;
	}
	#preLocation {
		width: 218%;
	}
	#aftLocation {
		width: 40px;
	}
	#remarkInput {
		height: 84px;
		width: 95.5%;
	}
	#keyword {
		height: 44px;
		width: 95.5%;
	}
	td.must {
		color: red;
	}
/*	@media screen and (max-width:1500px){
	.body_content{
	height:500px !important;
	overflow:scroll;
	} */
}
.body_content{
	height:100%;
	
	}
	</style>
  </head>
  <body onload="init()">
  <div class='body_content'>
 
    <table class="branch_table crm-table">
    	<tr><td class="must">门店名称</td><td><input class="inputStyle" id="branchName" maxlength="15" placeholder="不能超过15位字符"/></td><td class="must">门店类型</td><td><select class="inputStyle" id="branchType">
    																				<c:forEach items="${branchThemList}" var="branchThemList">				
    																				<option value="${branchThemList.content }">${branchThemList.paramName}</option>
    																				</c:forEach>
    																				</select></td></tr>
    	
    	<tr style="display: none;"><td class="must">等级</td><td><select class="inputStyle" id="branchRank" <c:if test="${fn:length(uperBranchList) ne 0}">disabled="disabled"</c:if>>
    						<c:forEach items="${branchRankList}" var="branchRankList">
    						<option value="${branchRankList.paramName}">${branchRankList.content}</option>
    						</c:forEach>
    						</select></td></tr>
    	<tr><td class="must">邮编</td><td><input class="inputStyle" id="postCode" placeholder="如 210000"/></td><td class="must">联系人</td><td><input class="inputStyle" id="connectPerson" maxlength="5"/></td></tr>
    	<tr><td class="must">手机</td><td><input class="inputStyle" id="phone"/></td><td class="must">座机</td><td><input class="inputStyle" id="homePhone" placeholder="如 0252554235"/></td></tr>
    	
    	
    	
    	<tr><td class="must">城市</td>
    		<td><%-- <select class="inputStyle" onclick="chooseCity()" id="cityCode">
    			<c:forEach items="${cityList }" var="cityList">
    				<option value="${cityList.adminiCode}" id="cityopt">${cityList.adminiName}</option>
    			</c:forEach>
    			</select class="must"> --%>
    			
    			<input type="text" id="cityChooseName" class="inputStyle"/>
				<input type="text" id="cityChooseCode" class="inputStyle" hidden="true"/>
				<ul class='myUlInfo' style='display:none;'>
				</ul>
			 	<select class="inputStyle" id="cityCode" style='display:none'>
				<option value="" id="cityopt"></option>		
				</select>
    		</td>
    		<td class="must">区</td><td><select class="inputStyle" id="district" onclick="chooseDistrict()">
    												 <c:if test="${fn:length(districtList) eq 0}">
    												<option value="">暂无区域记录</option>
    												</c:if> 
    												<c:forEach items="${districtList }" var="districtList">
						    						<option value='${districtList["ADMINI_CODE"]}' id="districtopt">${districtList["ADMINI_NAME"]}</option>
						    						</c:forEach>
    												</td></tr>
    												
    												
    												
    												
    												
    	<tr><%-- <td >街道</td><td><select class="inputStyle" id="street" onclick="chooseStreet()">
    						 <c:if test="${fn:length(streetList) eq 0}">
    												<option value="">暂无街道记录</option>
    												</c:if> 
    												<c:forEach items="${streetList }" var="streetList">
						    						<option value='${streetList["ADMINI_CODE"]}' id="streetopt">${streetList["ADMINI_NAME"]}</option>
						    						</c:forEach>
    						</select></td> --%>
    	<td class="property_add">属性</td>
    	<td>
    	<select class="inputStyle property_add" id="property" onchange="chooseProperty()">
			<option value='3'>街道</option>
			<option value='4'>商业圈</option>
			<!-- <option value='5'>学校</option>
			<option value='6'>景点</option>
			<option value='7'>地铁线路</option> -->
		</select>
		</td>
    	<%-- 	<td>商业圈</td><td><select class="inputStyle" id="circle" onchick="chooseCircle()">
    														 <c:if test="${fn:length(circletList) eq 0}">
			    												<option value="">暂无商圈记录</option>
			    												</c:if> 
			    												<c:forEach items="${circletList }" var="circletList">
									    						<option value='${circletList["ADMINI_CODE"]}' id="circleopt">${circletList["ADMINI_NAME"]}</option>
									    						</c:forEach>
    														</select></td> --%>
    	<td class="street_title">街道</td>
    	<td>
    	<select class="inputStyle street_add" id="street" onchange="chooseStreet()">
		 <c:if test='${(fn:length(streetList) eq 0) or street.adminiCode eq ""}'>
		<option value="">暂无街道记录</option>
		</c:if> 
		<c:forEach items="${streetList }" var="streetList">
		<option value='${streetList["ADMINI_CODE"]}' id="streetopt" <c:if test="${streetList['ADMINI_CODE'] eq street.adminiCode}">selected="selected"</c:if>>${streetList["ADMINI_NAME"]}</option>
		</c:forEach>
		</select>
		</td>													
    	</tr>
    	
    	<tr>
    	<td class="subway_title">地铁站</td>
    	<td>
    	<select class="inputStyle subway_add" id="subway">
		<option value="">暂无地铁记录</option>
		</select>
		</td>
    	</tr>
    														
    														<tr><td class="must">经度</td><td><input class="inputStyle" id="longitude" placeholder="例如：160.12365"/>
    														</td>
    														<td class="must">纬度</td><td><input class="inputStyle" id="latitude" placeholder="例如：89.12365"/>
    														</td>
    														</tr>
    														<tr style="display: none;">
<!--     														<td class="must">门店ip地址</td> -->
<!--     														<td><input class="inputStyle" id="ip"/> -->
<!--     														</td> -->
    														<td class="must">推荐</td>
    														<td><select class="inputStyle"  id = "recommend">
    															 <option value = "0">否</option>
    															 <option value = "1">是</option>
    															</select>
    														</td>
    														<td style="display:none;">房价审核</td><td style="display:none;"><select class="inputStyle" id="flag" >
    																				<option value="0" selected = "selected">关</option>
    																				<option value="1">开</option>
    																				</select>
    														</td>
    														</tr>
    														
    														
    														
    														
    	<tr><td class="must">详细地址</td><td><input class="inputStyle" id="preLocation"/></td></tr>
    														
    														
    	<tr><td>关键字</td><td colspan="3"><input class="inputStyle" id="keyword" placeholder="多个关键字以逗号分割，不能超过6个关键字！"/></td></tr>
    	
    	
    	<tr><td>备注</td><td colspan="3"><textarea class="inputStyle" id="remarkInput"></textarea></td></tr>
    </table>
    <div class="contractbutton parents-content">
	 <div class="pdepartsubmit crm-confirmbt" onclick="submitBranch()">提交</div>
	 <div class="pdepartcancle crm-cancelbt" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
                <input type="hidden" name="latlng" value="" class="mid_tet" readonly /> 
                 </div>
  </body>
  <script>
  var base_path = '<%=request.getContextPath()%>';

  
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
var callbacks={ 
  //若服务请求成功，则运行以下函数，并将结果传入 
  complete:function(result){ 
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
  error:function(){ 
	  showMsg("出错了。请先填写地址！"); 
  } 
} 
//创建类实例 
/* geocoder = new qq.maps.Geocoder(callbacks);  */
/* getLatlng(); */
} 
  
      function getLatlng(){
    	  var optionsCity=$("#cityCode option:selected");
    	  var optionsCityText=optionsCity.text();
    	  var optionsDistrict=$("#district option:selected");
    	  var optionsDistrictText=optionsDistrict.text();
    	  var optionsStreet=$("#street option:selected");
    	  var optionsStreetText=optionsStreet.text();
    	  var optionsCircle=$("#circle option:selected");
    	  var optionsCircleText=optionsCircle.text();
    	  /* var homeId=$("#aftLocation").val(); */
    	  if(optionsDistrictText=='暂无区域记录'){
    		  optionsDistrictText='';
    	  }
    	  if(optionsStreetText=='暂无街道记录'){
    		  optionsStreetText='';
    	  }
    	  if(optionsCircleText=='暂无商圈记录'){
    		  optionsCircleText='';
    	  }
    	  var location=optionsCityText+optionsDistrictText+optionsStreetText+optionsCircleText;
    	 geocoder.getLocation(location); 
      }
  function submitBranch(){
	  var latlng=$("input[name='latlng']").val();
	  var branchName =$("#branchName").val();
	  var branchType =$("#branchType").val();
	  var branchRank =$("#branchRank").val();
	  var homePhone =$("#homePhone").val();
	  var postCode =$("#postCode").val();
	  var connectPerson =$("#connectPerson").val();
	  var phone =$("#phone").val();
	  var preLocation =$("#preLocation").val();
	  /* var aftLocation =$("#aftLocation").val(); */
	  
	   var cityCode =$("#cityChooseCode").val();
	  var district =$("#district").val();
	  var property = $("#property").val();
	  var street =$("#street").val();
	  var subway = $("#subway").val();
	  
	  var remarkInput =$("#remarkInput").val();
	  var flag =$("#flag").val();
	  var originalkeyword =$("#keyword").val();
	  var longitude =$("#longitude").val();
	  var latitude =$("#latitude").val();
	 // var ip = $("#ip").val();
	  var recommend = $("#recommend").val();
	   if(cityCode==""){
		  showMsg("请选择城市！");  
		  return false;
	  }
	  if(district==""){
		  showMsg("请选择区域！");  
		  return false;
	  }
	  if(street==""){
		   if(property=="3"){
				 showMsg("街道不可为空！");  
				 return false;
			}else if(property=="4"){
				 showMsg("商业圈不可为空！");  
				 return false;
			}else if(property=="5"){
				 showMsg("学校不可为空！");  
				 return false;
			}else if(property=="6"){
				 showMsg("景点不可为空！");  
				 return false;
			}else if(property=="7"){
				 showMsg("地铁线路不可为空！");  
				 return false;
			}
		  
	  } 
	   if(property=="7"){
		   if(subway == ""){
			   showMsg("地铁站不可为空！");  
				 return false;
		   } 
		}
	  var keyword =  originalkeyword.replace(/，/ig,',');
	  if(keyword.endsWith(",")){
		  keyword = keyword.substring(0,keyword.length-1);
	  }
	  var keywordString = keyword.split(",");
	   if(keywordString.length>6){
		  showMsg("关键字不能超过6位！");
		  return false;
	  }
	   var flag=1;
	   for (x in keywordString){
	   if(keywordString[x].length>5){
		   flag = 0;
		   break;
	   }
	   }
	 
	 
	  if(flag == 0){
		showMsg("关键字字符长度不能超过5位！");
	  	return false;
	  }
	  if(longitude==""){
		  showMsg("经度不能为空！");
		  return false;
	  }else{
		  var longitudeRex = /^[\-\+]?(0?\d{1,2}\.\d{1,10}|1[0-7]?\d{1}\.\d{1,10}|180\.0{1,10})$/;
		  if(!longitudeRex.test(longitude)){
			  showMsg("经度-180.0～+180.0，必须输入1到10位小数！");
			  return false;
		  }
	  }
	  if(latitude == ""){
		  showMsg("纬度不能为空！");
		  return false;
	  }else{
		  var latitudeRex = /^[\-\+]?([0-8]?\d{1}\.\d{1,10}|90\.0{1,10})$/;
		  if(!latitudeRex.test(latitude)){
			  showMsg("纬度-90.0～+90.0，必须输入1到10位小数！");
			  return false;
		  }
	  }
	  
	   if(branchName==""){
		  showMsg("门店名称不可为空！");  
		  return false;
	  }
	  if(homePhone==""){
		  showMsg("座机不可为空！");  
		  return false;
	  }
	  if(postCode==""){
		  showMsg("邮编不可为空！");  
		  return false;
	  }
	  if(connectPerson==""){
		  showMsg("联系人不可为空！");  
		  return false;
	  }
	  if(phone==""){
		  showMsg("手机号不可为空！");  
		  return false;
	  }
	  if(preLocation==""){
		  showMsg("地址不可为空！");  
		  return false;
	  }
/*	  if(ip==""){
		  showMsg("ip地址不可为空！");
		  return false;
	  }
	  
	  
	  var ipRex = /^(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))$/;
	  if(!(ipRex.test(ip))){
		  showMsg("ip格式不正确！");  
    	  return false;
	  }*/
	  var postCodeRegexp=/^[0-9]{6}$/;
	  if(!(postCodeRegexp.test(postCode))){
		  showMsg("邮编格式不正确！");  
    	  return false;
	  }
	  //(/^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/)
	  var homePhoneRex=/^((0\d{9,11})|(1[3584]\d{9}))$/;
	  if(!(homePhoneRex.test(homePhone))){
		  showMsg("座机格式不正确！");  
    	  return false;
	  }
	  
	  
      if(!(/^1[34578]\d{9}$/.test(phone))){   
    	  showMsg("手机号格式不正确！");  
    	  return false;  
      } 
	   
	  $.ajax({
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
				cityCode :cityCode,
				district :district,
				street : street,
				property : property,
				subway : subway,
				remarkInput : remarkInput,
				latlng : latlng,
				flag : flag,
				keyword : keyword,
				longitude : longitude,
				latitude : latitude,
				//ip : ip,
				recommend:recommend
			},
			success: function(data) {
				if(data==1){
					showMsg("添加成功！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
					window.setTimeout("window.parent.JDialog.close();",1000);
				}else if(data==-1){
					showMsg("无权操作！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
					window.setTimeout("window.parent.JDialog.close();",1000);
				}
			},
			error : function(data){
				}
			}); 
  }
/*   //区域选择联动旧
  function chooseDistrict(){
	  var districtAdminiCode=$("#district").val();
	   $.ajax({
			url: base_path+"/queryPlaceByDistrict.do",
			type: "post",
			dataType: "json",
			data: {
				districtAdminiCode:districtAdminiCode
			},
			success: function(data) {
				var streetList=data.data.streetList;
				var circletList=data.data.circletList;
				var streetHtmlString="";
				var circleHtmlString="";
				
				 
				 if(streetList.length==0){
					 streetHtmlString+="<option value=''>暂无街道记录</option>";
				 }else{
				 jQuery.each(streetList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 streetHtmlString+="<option value='"+code+"'>"+name+"</option>"
				      }); 
				 }
				 $("#street").html('').append(streetHtmlString); 
				 
				 if(circletList.length==0){
					 circleHtmlString+="<option value=''>暂无商圈记录</option>";
				 }else{
				 jQuery.each(circletList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 circleHtmlString+="<option value='"+code+"'>"+name+"</option>"
				      }); 
				 }
				 $("#circle").html('').append(circleHtmlString); 
			},
			error: function() {
			}
		});
  } */
  //城市选择联动新
  function chooseCity(e){
	  var cityAdminiCode = $(e).find(".adminCodes").val();
	   var cityAdminiName = $(e).find(".adminNames").val();
	   $("#cityCode").val(cityAdminiCode);
	   $("#cityChooseCode").val(cityAdminiCode);
	   $("#cityChooseName").val(cityAdminiName);
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
  function chooseStreet(){
	  /* getLatlng(); */
  }
  function chooseCircle(){
	  /* getLatlng(); */
  }
  function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
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
	  
  
  $('#cityChooseName').bind('input propertychange', function() {
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
		   			str += '<li onclick="chooseCity(this)"><input value="' + city.name+ '" type="text" style="width:100%;height:28px;border: 0px;" class="adminNames" readonly/><input class="adminCodes" type="hidden" value="' + city.code +'"/></li>'
		   		}
		   	})
		   	$(".myUlInfo").html(str);
		   	$(".myUlInfo").show();
	   	} else {
	   		$(".myUlInfo").hide();
	   	}
	});
  
  
	function chooseProperty(){
		  var districtAdminiCode=$("#district").val();
		  var rank = $("#property").val();
		  if(rank == 3){
			  $(".street_title").last().html('').append("街道");  
		  }else if(rank == 4){
			  $(".street_title").last().html('').append("商业圈");  
		  }else if(rank == 5){
			  $(".street_title").last().html('').append("学校");  
		  }else if(rank == 6){
			  $(".street_title").last().html('').append("景点");  
		  }else if(rank == 7){
			  $(".street_title").last().html('').append("地铁线路");  
		  }
		  if(rank == 7){
			  $(".subway_title").show();  
			  $(".subway_add").show();  
		  }else{
			  $(".subway_title").hide();  
			  $(".subway_add").hide();  
		  }
		    $.ajax({
				url: base_path+"/queryPlaceByProperty.do",
				type: "post",
				dataType: "json",
				data: {
					districtAdminiCode:districtAdminiCode,
					rank:rank
				},
				success: function(data) {
					var streetList = "";
					var subwayList = "";
					if(rank != 7){
						streetList=data.data.list;
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
					}else{
						subwayLineList=data.data.subwayLineList;
						subwayList=data.data.subwayList;
						
						var streetHtmlString="";
						var subwayHtmlString="";
						if(subwayLineList.length==0){
							streetHtmlString+="<option value=''>暂无地铁记录</option>";
							subwayHtmlString+="<option value=''>暂无地铁记录</option>";
						}else{
							jQuery.each(subwayLineList, function(i,item){
							 	var code=item["ADMINI_CODE"];
								var name=item["ADMINI_NAME"];
							 streetHtmlString+="<option value='"+code+"'>"+name+"</option>";
						      }); 
							if(subwayList.length==0){
								subwayHtmlString+="<option value=''>暂无地铁记录</option>";
							}else{
								jQuery.each(subwayList, function(i,item){
								 	var code=item["ADMINI_CODE"];
									var name=item["ADMINI_NAME"];
									subwayHtmlString+="<option value='"+code+"'>"+name+"</option>";
							      }); 
							}
							
						}
						 $("#street").html('').append(streetHtmlString); 
						 $("#subway").html('').append(subwayHtmlString); 
						
					}
			
				},
				error: function() {
				}
			}); 
	  }
  </script>
</html>
