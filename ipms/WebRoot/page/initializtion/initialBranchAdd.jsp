<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>门店新增设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script> 
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<style>
		.myUlInfo{
       		width: 287px;
    		height: 230px;
    		position: absolute;
    		z-index: 1;
    		border: 1px solid #ccc;
    		left: 215px;
    		top: 71px;
        	background-color: #fff;
    		overflow: auto;
		}

	 	.pinLi{
	     	height: 28px;
	 	}
		</style>
		<script>
			var newCity = ${newCity};
		</script>
	</head>
	<body onload="init();">
		<div class="well" style="height:540px;">
		<form id="myForm" class="form-horizontal" role="form" method = "post"  >
		<c:if test ="${branchList != null}">
			<c:forEach items="${branchList}" var="sl">
                    <fieldset style="margin: 10px;">
                        <legend>当前门店</legend>
                        <input class="form-control" id="branchId" name="branchId" type="hidden" value="${sl.branch_id}"/>
                        <div class="form-group" style=" margin: 40px; margin-top: 1px;">
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="staffName">门店名称</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="branchName" name="branchName" type="text" placeholder="输入门店名称" value="${sl.branch_name}"/>
                          </div>
                       </div>
                        <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >座机</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="phone" name="phone" type="text" placeholder="051588888888" 
                             value="${sl.phone}" onblur="checkTel(this.id)" maxlength="13"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">门店等级</label>
                           <div class="col-sm-10">
                              <select id="rank" name="rank" class="form-control" disabled="disabled">
                                  <option value="0">总店</option>
                                  <option value="1" selected = "selected">分店</option>
                              </select>
                           </div>
                        </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">门店类型</label>
                           <div class="col-sm-10">
                              <select id="branchType" name="branchType" class="form-control">
                                  <option value="1" >酒店</option>
                                  <option value="2" >公寓</option>
                              </select>
                           </div>
                        </div>
                       <div class="form-group formInput">
                       	  <label class="col-sm-2 control-label" >联系人</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="contacts" name="contacts" type="text" placeholder="请输入联系人" value="${sl.contacts}"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >手机</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="mobile" name="mobile" type="text" placeholder="18888888888" value="${sl.mobile}" maxlength="11"/>
                          </div> 
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">城市</label>
                           <div class="col-sm-10">
                            <input type="text" id="cityChooseName" value="${cityAdminCodeName}" class="form-control" style=" margin-bottom: 24px;" onchange="cleanDistrict()" />
							<input type="text" id="cityChooseCode" value="${cityAdminCode}" class="form-control" style='display:none;'/>
							<ul class='myUlInfo' style='display:none;'></ul>
							<select id="city" name="city" class="form-control" style='display:none;'>
								<option value="${cityAdminCode}" id="cityopt" selected = "selected"></option>
							</select> 
                           </div>  
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">区</label>
                           <div class="col-sm-10">
                              <select id="district" name="district" class="form-control" >
								<c:if test="${fn:length(districtList) eq 0}">
									<option value="">暂无区域记录</option>
								</c:if> 
								<c:forEach items="${districtList }" var="districtList">
									<option value='${districtList["ADMINI_CODE"]}' id="districtopt" <c:if test="${districtList['ADMINI_CODE'] == cityAdminSubCode}"></c:if>>${districtList["ADMINI_NAME"]}</option>
								</c:forEach>
                              </select>
                           </div>
                        </div>
                        <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="loginName">门店地址</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="address" name="address" type="text" placeholder="输入门店地址" value="${sl.address}"/>
                          </div>
                       </div>  
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >邮编</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="postcode" name="postcode" type="text" placeholder="000000" value="${sl.postcode}"/>
                          </div>
                       </div>
                       <c:if test ="${branchLocation != null}">
                       	<c:forEach items="${branchLocation}" var="el">
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >经度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="longitude" name="longitude" type="text" placeholder="例如：160.12365" value="${el.longitude}"/>
	                          </div>
	                       </div>
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >纬度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="latitude" name="latitude" type="text" placeholder="例如：89.12365" value="${el.latitude}"/>
	                          </div>
	                       </div>
	                     </c:forEach>
	                   </c:if>
	                   <c:if test ="${branchLocation == null}">
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >经度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="longitude" name="longitude" type="text" placeholder="例如：160.12365"/>
	                          </div>
	                       </div>
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >纬度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="latitude" name="latitude" type="text" placeholder="例如：89.12365"/>
	                          </div>
	                       </div>
	                   </c:if>
	                   <c:if test ="${loginUser.status == '2'}">
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >门店Ip</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="branchIp" name="branchIp" type="text" placeholder="192.168.1.1" value="${sl.branch_ip}"/>
	                          </div>
	                       </div>
                       </c:if>
                       </div>
                    </fieldset>     
                </c:forEach>
                </c:if>
                <c:if test="${branchList== null}" >
                    <fieldset style="margin: 10px;">
                        <legend>当前门店</legend>
                        <input class="form-control" id="branchId" name="branchId" type="hidden" />
                        <div class="form-group" style=" margin: 40px; margin-top: 1px;">
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="staffName">门店名称</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="branchName" name="branchName" type="text" placeholder="输入门店名称" />
                          </div>
                       </div>
                        <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >座机</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="phone" name="phone" type="text" placeholder="051588888888" 
                             	onblur="checkTel(this.id)" maxlength="13"
                             />
                          </div>
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">门店等级</label>
                           <div class="col-sm-10">
                              <select id="rank" name="rank" class="form-control" disabled="disabled">
                                  <option value="0">总店</option>
                                  <option value="1" selected = "selected">分店</option>
                              </select>
                           </div>
                        </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">门店类型</label>
                           <div class="col-sm-10">
                              <select id="branchType" name="branchType" class="form-control">
                                  <option value="1" >酒店</option>
                                  <option value="2" >公寓</option>
                              </select>
                           </div>
                        </div>
                      
                       <div class="form-group formInput">
                       	  <label class="col-sm-2 control-label" >联系人</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="contacts" name="contacts" type="text" placeholder="请输入联系人" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >手机</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="mobile" name="mobile" type="text" placeholder="18888888888" maxlength="11"/>
                          </div> 
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">城市</label>
                           <div class="col-sm-10">
							<input type="text" id="cityChooseName" class="form-control" style=" margin-bottom: 24px;" onchange="cleanDistrict()"/>
							<input type="text" id="cityChooseCode" class="form-control" style='display:none;'/>
							<ul class='myUlInfo' style='display:none;'></ul>
							<select id="city" name="city" class="form-control" style='display:none;'>
								<option value="" id="cityopt" selected = "selected"></option>
							</select> 
                           </div>  
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">区</label>
                           <div class="col-sm-10">
                              <select id="district" name="district" class="form-control" >
								<c:if test="${fn:length(districtList) eq 0}">
									<option value="">暂无区域记录</option>
								</c:if> 
								<c:forEach items="${districtList }" var="districtList">
									<option value='${districtList["ADMINI_CODE"]}' id="districtopt">${districtList["ADMINI_NAME"]}</option>
								</c:forEach>
                              </select>
                           </div>
                        </div>
                        <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="loginName">门店地址</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="address" name="address" type="text" placeholder="输入门店地址" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >邮编</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="postcode" name="postcode" type="text" placeholder="000000" />
                          </div>
                       </div>
                        <c:if test ="${branchLocation == null}">
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >经度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="longitude" name="longitude" type="text" placeholder="例如：160.12365"/>
	                          </div>
	                       </div>
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >纬度</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="latitude" name="latitude" type="text" placeholder="例如：89.12365"/>
	                          </div>
	                       </div>
	                   </c:if>	                   
	                   <c:if test ="${loginUser.status == '2'}">
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >门店Ip</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="branchIp" name="branchIp" type="text" placeholder="192.168.1.1" />
	                          </div>
	                       </div>
                       </c:if>
                       </div> 
                    </fieldset>     
               </c:if>
               <c:if test="${customerPhone != null}" >
	               <div class="form-group formInput cust" style=" margin-left: 45.4%;margin-top: -7.5%;">
	                  <label class="col-sm-2 control-label" >客服电话</label>
	                  <c:forEach items="${customerPhone}" var="cp">
		                  <div class="col-sm-4">
		                     <input class="form-control" id="customerPhone" name="customerPhone" type="text" 
		                     placeholder="025-88888888" value="${cp.content}" maxlength="13"/>
		                  </div>
		               </c:forEach>   
	               </div>
               </c:if>
               <c:if test="${customerPhone == null}" >
	               <div class="form-group formInput cust" style="margin-left: 45.4%; margin-top: -7.5%;">
	                  <label class="col-sm-2 control-label" >客服电话</label>
	                  <div class="col-sm-4">
	                     <input class="form-control" id="customerPhone" name="customerPhone" type="text" 
	                     placeholder="025-88888888" onblur="checkTel(this.id)"  maxlength="13"/>
	                  </div>             
	               </div>
               </c:if>
               <input type="hidden" name="latlng" value="" class="mid_tet" readonly /> 
                </form>
            </div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	$(document).ready(function() {
	    		//当数据展示时select选中
	    		<c:if test="${branchList != null}" >
	    			$("#branchType").val(parseInt(${branchList["0"]["BRANCH_TYPE"]}));
	    			$("#rank").val(parseInt(${branchList["0"]["RANK"]}));
		    		$("#city").val(parseInt(${branchList["0"]["CITY"]}));
		    		$("#district").val(parseInt(${branchList["0"]["DISTRICT"]}));
	    		</c:if>	
	    		<c:if test="${branchList== null}" >
	    			$("#branchName").focus();
	    		</c:if>	
	    		 <c:if test ="${loginUser.status != '2' && loginUser.status != '1'}">
    				$(".cust").css("margin-left","4.4%");
    				$(".cust").css("margin-top","-0.7%");
    			 </c:if>		
        	});
	    	 //区域选择联动
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
	    	  }
	    	  //城市选择联动
	    	  function chooseCity(){
	    		  var cityAdminiCode=$("#city").val();
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
	    					var circletList=data.data.circletList;
	    					var districtHtmlString="";
	    					var streetHtmlString="";
	    					var circleHtmlString="";
	    					
	    					if(districtList.length==0){
	    						districtHtmlString+="<option value=''>暂无区域记录</option>";
	    					 }else{
	    					 jQuery.each(districtList, function(i,item){
	    						 var code=item["ADMINI_CODE"];
	    						 var name=item["ADMINI_NAME"];
	    						 districtHtmlString+="<option value='"+code+"'>"+name+"</option>"
	    					      }); 
	    					 }
	    					 $("#district").html('').append(districtHtmlString); 
	    				},
	    				error: function() {
	    				}
	    			});
	    	  }

	    	  var init = function() { 
	    		  var callbacks={ 
	    		    //若服务请求成功，则运行以下函数，并将结果传入 
	    		    complete:function(result){ 
	    		        var latlng = result.detail.location; 
	    		            $("input[name='latlng']").val(latlng); 
	    		    }, 
	    		    //若服务请求失败，则运行以下函数 
	    		    error:function(){ 
	    		  	  showMsg("出错了。请先填写地址！"); 
	    		    } 
	    		  } 
	    		  //创建类实例 
	    		  geocoder = new qq.maps.Geocoder(callbacks); 
	    		  }  
	    	  
	    	  function getLatlng(){
	        	  var optionsCity=$("#city option:selected");
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
	          }
	</script>
	<script type="text/javascript">
	 function checkTel(id){
		 var phone = document.getElementById(id).value;
		if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phone)){
		window.parent.showMsg('号码有误,请重新输入!');
		return false;
		}
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
			   			str += '<li class="pinLi" onclick="chooseCityNew(this)"><input value="' + city.name+ '" type="text" style="border: 0px;margin: 0;height: 20px;    background-color: #fff;" class="adminNames" readonly/><input class="adminCodes" type="hidden" value="' + city.code +'"/></li>'
			   		}
			   	})
			   	$(".myUlInfo").html(str);
			   	$(".myUlInfo").show();
		   	} else {
		   		$(".myUlInfo").hide();
		   	}
		});
	
	 //城市选择联动新
	  function chooseCityNew(e){
		  var cityAdminiCode = $(e).find(".adminCodes").val();
		   var cityAdminiName = $(e).find(".adminNames").val();
		   $("#cityChooseCode").val(cityAdminiCode);
		   $("#cityChooseName").val(cityAdminiName);
		   $("#cityopt").val(cityAdminiCode);
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
					var circletList=data.data.circletList;
					var districtHtmlString="";
					var streetHtmlString="";
					var circleHtmlString="";
					
					if(districtList.length==0){
						districtHtmlString+="<option value=''>暂无区域记录</option>";
					 }else{
					 jQuery.each(districtList, function(i,item){
						 var code=item["ADMINI_CODE"];
						 var name=item["ADMINI_NAME"];
						 districtHtmlString+="<option value='"+code+"'>"+name+"</option>"
					      }); 
					 }
					 $("#district").html('').append(districtHtmlString); 
					
				},
				error: function() {
				}
			}); 
	  }

	 function cleanDistrict(){
		 var districtHtmlString="<option value=''>暂无区域记录</option>";
		 $("#district").html('').append(districtHtmlString); 
	 }
	 
	</script>
	</body>
</html>

