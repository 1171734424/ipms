<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>民宿编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		var newCity = ${newCity};
	</script>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
.myUlInfo{
    width: 200px;
    height: 233px;
    position: absolute;
    z-index: 1;
    border: 1px solid #ccc;
    left: 88px;
    top: 56px;
    background: #fff;
    overflow:auto;
}
li{
	list-style-type:none;
	height:28px;
	margin-left:-30px;
}
.branch_table{
	width:100%;
	border-collpse:separate;
	border-spacing:10px;
}
.inputStyle{
	width: 245px;
    height: 30px;
    border-bottom-right-radius: 6px;
    border-bottom-left-radius: 6px;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    border: 1px solid #dddddd;
    background-color: white;
}
.inputStyle2{
    width: 225px;
    height: 30px;
    border-bottom-right-radius: 6px;
    border-bottom-left-radius: 6px;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    border: 1px solid #dddddd;
    background-color: white;
}
.contractbutton{
	margin-top:100px;
	float: right;
    width: 235px;
    height: 50px;
    text-align: center;
    border-radius: 5px;
    margin-top:8px;
	}
	.pdepartsubmit{
	float:left;
	width: 100px;
    height: 30px;
   background-color: #5A5D9D;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;
	}
	
	.pdepartsubmit:hover,.pdepartcancle:hover,.pdepartadd:hover{
		font-weight:bold;
	}
	.pdepartcancle{
	float:left;
	width: 100px;
    height: 30px;
    background-color: #FC8A15;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;
	}
	.pdepartadd{
	float:left;
	width: 100px;
    height: 30px;
    background-color: #5A5D9D;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;
	}
	#preLocation{
	width:245px;
	}
	/* #aftLocation{
	width:40px;
	} */
	#remarkInput{
	height:120px;
	width: 97%;
	}
	td.must{
	color:red;
	}

	.keyword_input, .roomDevice{
	width: 946px;
    height: 30px;
    border-bottom-right-radius: 6px;
    border-bottom-left-radius: 6px;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    border: 1px solid #dddddd;
    background-color: white;
	}
.deviceTable,.tipTable{
    position: absolute;
    left: 8%;
    top: 7%;
    width: 84%;
    height: 78%;
    background-color: #fff;
    border-radius: 8px;
    text-align: center;
    margin: 0 auto;
}
.deviceBody,.tipBody, .serviceBody{
	position: absolute;
	overflow: hidden;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 200;
	top: 0;
	left: 0;
}
.submitDiv{
	width:60%;
	height:40px;
	line-height:40px;
	position:relative;
	top:5%;
	margin: 0 auto;
}
button#confirm{
	height:36px;
	width:100px;
	background-color:#5A5D9D;
	color:#fff;
	border:none;
}
button#cancel{
	height:36px;
	width:100px;
	background-color: #FC8A15;
	color:#fff;
	border:none;
}
span#confirm,span#cancel{
    margin: 0 2%;
}
table.device,.tip{
	height:68%;
	width:94%;
	font-size:medium;
	border: none;
    margin: 0 auto;
    margin-left: 6%;
    margin-top: 6%
}
table.device,.service{
	height:68%;
	width:94%;
	font-size:medium;
	border: none;
    margin: 0 auto;
    margin-left: 6%;
}

div.deviceBody,.tipBody, .serviceBody{
	display:none;
}
	.topdiv{
	   overflow-y: auto;
	   height: 479px;
	}
	
	/*城市联动  */
.subway_add{
display:none;
}
.subway_title{
display:none;
}
</style>
  </head>
  
  <body onload="init()">
   <div class="topdiv">
   <input type="hidden" name="houseid" id="houseid" value="${house.houseId}" class=""  /> 
    <table class="branch_table">
    	<tr>
    	<td class="must">民宿名称</td>
    	<td><input class="inputStyle" id="housename" maxlength="15" placeholder="请输入1-15个有效字符"  value="${house.housename}"/></td>
    	<td class="must">门牌号</td>
    	<td><input class="inputStyle" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" id="houseNo" maxlength="7" placeholder="请输入1-7个数字" value="${house.houseNo}" />
    	</td>
    	<td class="must">楼层</td>
		<td><input class="inputStyle" id="floor" value="${house.floor}" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="2"/></td>
    	</tr>
    	<tr>
<!--    	<td id="no_input" class="must" >房型码</td>-->
<!--			<td ><input class="inputStyle" id="roomType"  placeHolder="请输入1-3个字母或数字" maxlength="3"-->
<!--			onkeyup="var reg = /^[\da-z]+$/i; if(!reg.test(this.value)) this.value = ''" name="roomType"></input></td>-->
    	<td class="must" >房型名称</td>
		<td >
			<input class="inputStyle" id="roomName" maxlength="8" placeHolder="请输入1-8个有效字符"	name="roomName" value="${house.houseType}"></input>
		</td>
    	<td class="must">管理员账号</td>
    	<td>
    	<input class="inputStyle" id="staffname" value="${staffname}"  readonly/>
    	<input type="hidden" id="staffname_CUSTOM_VALUE" name="staffid" value="${house.staffId}" />
    	</td>
    	<td class="must">装修风格</td>
    	<td><input class="inputStyle" id="decStyle" value="${house.decStyle}" /></td>
    	</tr>
    	<tr>
<!--		<td class="must">楼层</td>-->
<!--		<td><input class="inputStyle" id="floor" value="" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}",-->
<!--   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="2"/></td>-->
		<td class="must">小区名称</td>
    	<td><input class="inputStyle" id="communityName" value="${house.communityName}" /></td>	
    	
    	
		<td>订单审核</td><td><select class="inputStyle" id="flag" >
		
			<option value="0" <c:if test="${houseflag == 0}">selected="selected"</c:if>>关</option>
			<option value="1" <c:if test="${houseflag == 1}">selected="selected"</c:if>>开</option>
			</select>
		</td>
		
		<td class="must">床位</td>
    	<td><input class="inputStyle" id="beds" maxlength="2" value="${house.beds}" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/></td>
        
		</tr>

    	<tr>
    	<td class="must">床型</td>
    	<td>
    	<select class="inputStyle" id="bedDesc" >
    	 <option value=""> 请选择</option>
    	<c:forEach items="${beddescList }" var="beddescList">
			<option value="${beddescList.paramDesc}" id="" <c:if test="${beddescList.paramDesc eq house.bedDesc}">selected="selected"</c:if> >${beddescList.content}</option>
			</c:forEach>
		</select></td>
		<td class="must">房屋朝向</td>
    	<td><select class="inputStyle" id="position" >
    	<option value="">请选择</option>
    	<c:forEach items="${positionList }" var="positionList">
			<option value="${positionList.paramDesc}" id="" <c:if test="${positionList.paramDesc eq house.position}">selected="selected"</c:if> >${positionList.content}</option>
			</c:forEach>
			</select>
		</td>
    	<td class="must">宽带</td>
    	<td>
    	<select class="inputStyle" id="broadband" >
    	<option value="">
									       请选择
								     </option>
    	<c:forEach items="${broadbandList }" var="broadbandList">
			<option value="${broadbandList.paramDesc}" id="" <c:if test="${broadbandList.paramDesc eq house.broadband}">selected="selected"</c:if> >${broadbandList.content}</option>
			</c:forEach>
		</select>
			</td>
		
			
    	</tr>
    	<tr>
    	<td class="must">建筑面积</td>
    	<td><input class="inputStyle2" id="area" value="${house.area}" maxlength="4" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
         onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>m<sup>2</sup></td>
    	<td class="must">状态</td>
    	<td>
    	<select class="inputStyle" id="status" >
    	
			    <c:forEach var="a" items="${rpstatus}">
                     <c:set var="flag" value="${a.orderno}"></c:set>
                      <c:if  test="${(flag==null)||(flag==1)||(flag==0)}">
                      <option value="<c:out value='${a.content}' />" <c:if test="${a.content eq house.status}">selected="selected"</c:if>/>
                      <c:out value="${a.paramname}"/>
                      </option>
                       </c:if>
			    </c:forEach>
		</select>
		</td>
    	<td class="must">详细地址</td>
    	<td><input class="inputStyle" id="preLocation" value="${addresshead}" maxlength="27"  /><%-- &nbsp—&nbsp
    	<input class="inputStyle" id="aftLocation" value="${addresslastInput}" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
         onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" maxlength="5"/>&nbsp号</td> --%>
        
    	
	  </tr>
	  <tr>
	  <td class="must">保洁费</td>
    	<td><input class="inputStyle2" id="cleanprice" value="${house.cleanprice}" maxlength="8"/><span>元</span></td>
    	<td class="must">经度</td>
    	<td><input class="inputStyle" id="longitude" value="${location.longitude}" placeholder="例如：160.1236512313"/></td>
    	<td class="must">纬度</td>
    	<td><input class="inputStyle" id="latitude" value="${location.latitude}" placeholder="例如：89.1236512313"/></td>
	  
	  </tr>
	  <tr>
	  <td class="must">押金</td>
    	<td><input class="inputStyle" id="cashpledge" value="${house.cashPledge}" />元</td>
		<td class="must">推荐</td>
    			<td><select class="inputStyle"  id = "recommend">
    					<option value = "0" <c:if test="${house.isRecommend eq 0}">selected="selected"</c:if>>否</option>
    						<option value = "1" <c:if test="${house.isRecommend eq 1}">selected="selected"</c:if>>是</option>
    					</select>
    			</td>
    	</td>
		    <td class="street_title">排序</td>
	    	<td><input class="inputStyle" id="orderNo" onkeyup  ="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}",
         onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${house.orderNo}" maxlength="8" placeholder="请输入1~8位正整数"/></td>
	  
	  </tr>
	  
	  
    	<tr>
	  <td class="must">城市</td>
    	<td><!--
	    	<select class="inputStyle" onchange="chooseCity()" id="cityCode">
				<c:forEach items="${cityList }" var="cityList">
				<option value="${cityList.adminiCode}" id="cityopt" <c:if test="${cityList.adminiCode eq cityAdminCode}">selected="selected"</c:if> >${cityList.adminiName}</option>
				</c:forEach>
	    						
			</select>-->
			<div>
			<input type="text" id="cityChooseName" class="inputStyle" value="${cityAdminName }"/>
			<input type="text" id="cityChooseCode" class="inputStyle" hidden="true" value="${cityAdminCode }"/>
			<ul class='myUlInfo' style='display:none;'>
			</ul>
			</div>
			 <select class="inputStyle" id="cityCode" style='display:none'>
				<option value="" id="cityopt"></option>		
			</select>
		</td>
    	<td class="must">区</td>
    	<td><select class="inputStyle" id="district" onchange="chooseDistrict()">
			 <c:if test="${fn:length(districtList) eq 0}">
			<option value="">暂无区域记录</option>
			</c:if> 
			<c:forEach items="${districtList }" var="districtList">
			<option value='${districtList["ADMINI_CODE"]}' id="districtopt" <c:if test="${districtList['ADMINI_CODE'] eq disAdminCode}">selected="selected"</c:if> >${districtList["ADMINI_NAME"]}</option>
			</c:forEach>
			</select></td>
			
		<td class="property_add">属性</td>
    	<td>
    	<select class="inputStyle property_add" id="property" onchange="chooseProperty()">
			<option value='3' <c:if test="${property eq 3}">selected="selected"</c:if>>街道</option>
			<option value='4' <c:if test="${property eq 4}">selected="selected"</c:if>>商业圈</option>
			<option value='5' <c:if test="${property eq 5}">selected="selected"</c:if>>学校</option>
			<option value='6' <c:if test="${property eq 6}">selected="selected"</c:if>>景点</option>
			<option value='7' <c:if test="${property eq 7}">selected="selected"</c:if>>地铁线路</option>
		</select>
		</td>
			
		<tr>
		<c:if test="${property eq 3}"><td class="street_title">街道</td></c:if>
		<c:if test="${property eq 4}"><td class="street_title">商业圈</td></c:if>
		<c:if test="${property eq 5}"><td class="street_title">学校</td></c:if>
		<c:if test="${property eq 6}"><td class="street_title">景点</td></c:if>
		<c:if test="${property eq 7}"><td class="street_title">地铁线路</td></c:if>
		<td>
    	<select class="inputStyle street_add" id="street" onchange="chooseStreet()">
    	<c:if test="${property eq 7}">
    		<c:if test='${fn:length(subwayLineList) eq 0}'>
    				<option value="">暂无地铁线路记录</option>
    		</c:if> 
    		<c:forEach items="${subwayLineList }" var="streetList">
			<option value='${streetList["ADMINI_CODE"]}' id="streetopt" <c:if test="${streetList['ADMINI_CODE'] eq normalCode}">selected="selected"</c:if>>${streetList["ADMINI_NAME"]}</option>
			</c:forEach>
		</c:if>
		<c:if test="${property ne 7}">
			<c:if test='${fn:length(streetList) eq 0}'>
				<c:if test="${property eq 3}"><option value="">暂无街道记录</option></c:if>
				<c:if test="${property eq 4}"><option value="">暂无商业圈记录</option></c:if>
				<c:if test="${property eq 5}"><option value="">暂无学校记录</option></c:if>
				<c:if test="${property eq 6}"><option value="">暂无景点记录</option></c:if>
				
			</c:if> 
			<c:forEach items="${streetList }" var="streetList">
			<option value='${streetList["ADMINI_CODE"]}' id="streetopt" <c:if test="${streetList['ADMINI_CODE'] eq normalCode}">selected="selected"</c:if>>${streetList["ADMINI_NAME"]}</option>
			</c:forEach>
		</c:if>
		 
		</select>
		
	    </tr>
    	<%-- <td>
    	<select class="inputStyle street_add" id="street" onclick="chooseStreet()">
		 <c:if test='${(fn:length(streetList) eq 0) or street.adminiCode eq ""}'>
		<option value="">暂无街道记录</option>
		</c:if> 
		<c:forEach items="${streetList }" var="streetList">
		<option value='${streetList["ADMINI_CODE"]}' id="streetopt" <c:if test="${streetList['ADMINI_CODE'] eq street.adminiCode}">selected="selected"</c:if>>${streetList["ADMINI_NAME"]}</option>
		</c:forEach>
		</select> 
		</td>--%>
		<tr>
		<td class="subway_title" <c:if test="${property eq 7}">style="display:block;"</c:if>>地铁站</td>
    	<td>
    	<select class="inputStyle subway_add" id="subway" <c:if test="${property eq 7}">style="display:block;"</c:if>>
		 <c:if test='${fn:length(subwayList) eq 0}'>
			<option value="">暂无地铁记录</option>
		</c:if> 
		<c:forEach items="${subwayList }" var="streetList">
		<option value='${streetList["ADMINI_CODE"]}' <c:if test="${streetList['ADMINI_CODE'] eq subAdminCode}">selected="selected"</c:if>>${streetList["ADMINI_NAME"]}</option>
		</c:forEach>
		</select>
		</td>
		</tr>
		<%-- <td>商业圈</td>
		<td>
		<select class="inputStyle" id="circle" onchick="chooseCircle()">
			 <c:if test="${fn:length(circletList) eq 0}">
				<option value="">暂无商圈记录</option>
				</c:if> 
				<c:forEach items="${circletList }" var="circletList">
				<option value='${circletList["ADMINI_CODE"]}' id="circleopt" <c:if test="${circletList['ADMINI_CODE'] eq circle.adminiCode}">selected="selected"</c:if>>${circletList["ADMINI_NAME"]}</option>
				</c:forEach>
			</select></td>
			
		
    	</tr> --%>
   <%--  	<tr>
    	
    	<td class=>学校</td>
    	<td><select class="inputStyle" id="school" onclick="chooseSchool()">
			 <c:if test="${fn:length(schoolList) eq 0}">
			<option value="">暂无学校记录</option>
			</c:if> 
			<c:forEach items="${schoolList }" var="schoolList">
			<option value='${schoolList["ADMINI_CODE"]}' id="schoolopt" <c:if test="${schoolList['ADMINI_CODE'] eq school.adminiCode}">selected="selected"</c:if> >${schoolList["ADMINI_NAME"]}</option>
			</c:forEach>
			</select></td>
			<td >景点</td>
    	<td>
    	<select class="inputStyle" id="scenic" onclick="chooseScenic()">
		 <c:if test='${(fn:length(scenicList) eq 0) or scenic.adminiCode eq ""}'>
		<option value="">暂无景点记录</option>
		</c:if> 
		<c:forEach items="${scenicList }" var="scenicList">
		<option value='${scenicList["ADMINI_CODE"]}' id="scenicopt" <c:if test="${scenicList['ADMINI_CODE'] eq scenic.adminiCode}">selected="selected"</c:if>>${scenicList["ADMINI_NAME"]}</option>
		</c:forEach>
		</select>
		</td>
		
		<td>地铁站</td>
		<td>
		<select class="inputStyle" id="subway" onchick="chooseSubway()">
			 <c:if test="${fn:length(subwwayList) eq 0}">
				<option value="">暂无地铁记录</option>
				</c:if> 
				<c:forEach items="${subwwayList }" var="subwwayList">
				<option value='${subwwayList["ADMINI_CODE"]}' id="subwayopt" <c:if test="${subwwayList['ADMINI_CODE'] eq subway.adminiCode}">selected="selected"</c:if>>${subwwayList["ADMINI_NAME"]}</option>
				</c:forEach>
			</select></td>
			
		
		</tr> --%>		
<!--    	<tr>-->
<!--    	 <td class="must">基准价</td>-->
<!--    	<td><input class="inputStyle2" id="originalprice" value="${house.initprice}" maxlength="8"/><span>元</span></td>-->
<!--    	<td class="must">调整价</td>-->
<!--    	<td><input class="inputStyle2" id="currentprice" value="${house.currentprice}" maxlength="8"/><span>元</span></td>-->
<!--    	-->
<!--		</tr>-->
		
	    

		<tr>  
		<td >房间设施</td>
		<td colspan="5"><input id="roomDevice" onclick="dev()" class="roomDevice" name="device" value="${deviceString}"  readonly></input></td>
		</tr>
		<tr> 
		 <td>入住须知</td>
		 <td colspan="5"><input id="roomTip"  onclick="tips()" class="roomDevice" value="${tipString}" name="tip" readonly></input></td>
		</tr>
		<tr> 
		 <td>周边服务</td>
		 <td colspan="5"><input id="roomService"  onclick="services()" class="roomDevice" value="${serviceName}" name="roomService" readonly></input></td>
		</tr>
		<tr>
		<td class="must">关键字</td><td colspan="5"><input class="keyword_input" id="keyword" value = "${keyword}" placeholder="多个关键字以逗号分割，不能超过6个关键字，每个关键字字数不能超过5个字符！"/></td>
		</tr>
		
<!--    	<tr><td>备注</td><td colspan="3"><input class="inputStyle" id="remarkInput" value=""/></td></tr>-->
       
		<tr>
			<td >
				客房描述
			</td>
			<td colspan="5">
				<textarea  style="width : 946px;height: 80px;font: 13.3333px Arial;" id="housedesc" name="housedesc"   class="inputStyle" rows="2"  maxlength="1000" placeholder="请输入1000字以内描述！">${house.houseDesc}</textarea>
			</td>
		</tr>
		<tr>
        <td>备注</td><td colspan="5"><textarea  style="width : 946px;height: 80px;font: 13.3333px Arial;" id="remarkInput" name="remarkInput"  class="inputStyle" rows="4"  maxlength="100" placeholder="备注内容格式：距离xx(景点)xx(米/公里)">${house.remark}</textarea></td>
		</tr>
    </table>
    <div class="contractbutton"><%--
    <div class="pdepartadd" onclick="addstaff()">新增管理员</div>
	 --%><div class="pdepartsubmit" onclick="addhousecrm()">提交</div>
	 <div class="pdepartcancle" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
	 </div>
     <input type="hidden" name="latlng" value="" class="mid_tet" readonly /> 
     <input type="hidden" name="branchId" value="" class="mid_tet" readonly /> 
               
<!--标签div-->
      <div class="deviceBody">
		<div class="deviceTable">
			<table class="device" style="margin-top: 6%;">
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
				<c:forEach items="${tips}" var="tips" varStatus="status">
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
	
	<div class="serviceBody">
		<div class="tipTable">
		<div style="width: 100%;height: 305px;overflow: auto;margin-top: 2%;">
			<table class="service">
				<c:forEach items="${specialSerivce}" var="specialSerivce" varStatus="status">
					<c:if test="${status.count eq 1 ||(status.count-1)%4 eq 0}">
						<tr>
					</c:if>
					<td>
						<input type="checkbox" name="serviceCheckBox" value="${specialSerivce.goodsId}">
						${specialSerivce.goodsName}
					</td>
					<c:if test="${status.count eq 4 ||status.count%4 eq 0}">
						</tr>
					</c:if>
				</c:forEach>

			</table>
		</div>
		<div class="submitDiv" style="top: 11px;">
			<span id="confirm"><button id="confirm" onclick="submitService()">
					确定
				</button>
			</span>
			<span id="cancel"><button id="cancel" onclick="hiddenServices()">
					取消
				</button>
			</span>
		</div>
		</div>
		<input type="hidden" id = "serviceId" name="serviceId" value="${serviceId}">
	</div>
	
	
  </body>
  <script>
  var base_path = '<%=request.getContextPath()%>';
  $(function(){
	  $("#staffname").bind("click",function(){
			 	JDialog.open("管理员", base_path + "/selectHouseAccount.do",450,350);
			//JDialog.open("管理员", base_path + "/selectHouseStaff.do",450,350);
			});
  });
 
//QQ map. 

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
}; 
//创建类实例 
/* geocoder = new qq.maps.Geocoder(callbacks);  */
/* getLatlng(); */
}; 
  
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
  function addhousecrm(){
	  var regdouble=/^\d+(\.\d{1,2})?$/;
	  var latlng=$("input[name='latlng']").val();
	  var houseid = $("#houseid").val();
	  var housename=$("#housename").val();
	 // var roomtype=$("#roomType").val();
	 // var theme = $("#theme").val();
	  var roomName=$("#roomName").val();
	  var houseNo =$("#houseNo").val();
	  var floor =$("#floor").val();
	  var beds =$("#beds").val();
	  var bedDesc =$("#bedDesc").val();
	  var position =$("#position").val();
	  var broadband =$("#broadband").val();
	 // var originalprice =$("#originalprice").val();
	  var cleanprice =$("#cleanprice").val();
	  var cashpledge =$("#cashpledge").val();
	  var decStyle = $ ("#decStyle").val();
	  var communityName = $("#communityName").val();
	  var preLocation =$("#preLocation").val();
	  /* var aftLocation =$("#aftLocation").val(); */
	  var cityCode =$("#cityChooseCode").val();
	  var district =$("#district").val();
	  var property = $("#property").val();
	  var street =$("#street").val();
	  var subway = $("#subway").val();
	 /*  var circle =$("#circle").val(); */
	  var remarkInput =$("#remarkInput").val();
	  var area =$("#area").val();
	  var flag =$("#flag").val();
	  var status =$("#status").val();
      var originalkeyword =$("#keyword").val(); 
      var longitude =$("#longitude").val();
	  var latitude =$("#latitude").val();
	  var staffid = $("#staffname").val();
	  //var staffid = $("#staffname_CUSTOM_VALUE").val();
	  var device =$("#roomDevice").val();
	  var tip = $("#roomTip").val();
	  var services = $("#serviceId").val();
	  var housedesc = $("#housedesc").val();
	  var recommend = $("#recommend").val();
	  var orderNo = $("#orderNo").val();
	 //  var currentprice = $("#currentprice").val();
	 
	 if(cityCode==""){
		  showMsg("请选择城市！");  
		  return false;
	  }
	  
	  if(district==""){
		  showMsg("请选择区域！");  
		  return false;
	  }
	  
	  if(housename==""){
		  showMsg("民宿名称不可为空！");  
		  return false;
	  }
	   if(houseNo==""){
		  showMsg("门牌号不可为空！");  
		  return false;
	  }
	  
	  if(floor==""){
		  showMsg("楼层不可为空！");  
		  return false;
	  }
	   if(isNaN(floor)){
			window.parent.showMsg("楼层请填写数字!");
		    return false;	
	     }
	//   if(roomtype==""){
	//	  showMsg("房型码不可为空！");  
	//	  return false;
	//  }
	   if(roomName==""){
		  showMsg("房型名称不可为空！");  
		  return false;
	  }
	  
	  if(beds==""){
		  showMsg("床位不可为空！");  
		  return false;
	  }
	   if(isNaN(beds)){
			window.parent.showMsg("床位请填写数字!");
		    return false;	
	     }
	    
	   if(bedDesc==""){
		  showMsg("床型不可为空！");  
		  return false;
	  }
	  	   if(position==""){
		  showMsg("房屋朝向不可为空！");  
		  return false;
	  }
	  	   if(broadband==""){
		  showMsg("宽带不可为空！");  
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
/*	  if(originalprice==""){
		  showMsg("民宿基准价不可为空！");  
		  return false;
	  }
	   if(isNaN($("#originalprice").val())){
			window.parent.showMsg("民宿基准价请填写数字!");
		    return false;	
	     }else if(regdouble.test($("#originalprice").val()) == false){
	    		window.parent.showMsg("请输入正确民宿基准价!");
	    		$("#originalprice").val("");
				$("#originalprice").focus();
		    return false;
	   }
	   if(currentprice==""){
		  showMsg("民宿调整价不可为空！");  
		  return false;
	  }
	   if(isNaN($("#currentprice").val())){
			window.parent.showMsg("民宿调整价请填写数字!");
		    return false;	
	     }else if(regdouble.test($("#currentprice").val()) == false){
	    		window.parent.showMsg("请输入正确民宿调整价!");
	    		$("#currentprice").val("");
				$("#currentprice").focus();
		    return false;
	   }
 */
	  if(cleanprice==""){
		  showMsg("保洁费不可为空！");  
		  return false;
	  }
	   if(isNaN($("#cleanprice").val())){
			window.parent.showMsg("保洁费请填写数字!");
		    return false;	
	     }else if(regdouble.test($("#cleanprice").val()) == false){
	    		window.parent.showMsg("请输入正确保洁费格式!");
	    		$("#cleanprice").val("");
				$("#cleanprice").focus();
		    return false;
	   }
	  if(area==""){
		  showMsg("建筑面积不可为空！");  
		  return false;
	  }
	   if(isNaN($("#area").val())){
			window.parent.showMsg("建筑面积请填写数字!");
		    return false;	
	     }else if(regdouble.test($("#area").val()) == false){
	    		window.parent.showMsg("请输入正确建筑面积格式!");
	    		$("#area").val("");
				$("#area").focus();
		    return false;
	   }
	   if(status==""){
		  showMsg("请选择状态！");  
		  return false;
	  }
	  if(preLocation==""){
		  showMsg("地址不可为空！");  
		  return false;
	  }
      /* if(isNaN(aftLocation)){
			window.parent.showMsg("地址门号请填写数字!");
		    return false;	
	     } */
	  if(longitude==""){
		  showMsg("经度不可为空！");  
		  return false;
	  }
	  if(longitude.length>0){
		  var longitudeRex = /^[\-\+]?(0?\d{1,2}\.\d{1,10}|1[0-7]?\d{1}\.\d{1,10}|180\.0{1,10})$/;
		  if(!longitudeRex.test(longitude)){
			  showMsg("经度-180.0～+180.0，必须输入1到10位小数！");
			  return false;
		  }
	  }
	  if(latitude==""){
		  showMsg("纬度不可为空！");  
		  return false;
	  }
	  if(latitude.length>0){
		  var latitudeRex = /^[\-\+]?([0-8]?\d{1}\.\d{1,10}|90\.0{1,10})$/;
		  if(!latitudeRex.test(latitude)){
			  showMsg("纬度-90.0～+90.0，必须输入1到10位小数！");
			  return false;
		  }
	  }
	  if(staffid == "" ){
	   showMsg("管理员不可为空！"); 
	   return false;
	  }
	  
	  
	  if(originalkeyword == ""){
		   showMsg("关键字不可为空！"); 
		   return false;
		  }
	 var keyword =  originalkeyword.replace(/，/ig,','); 
	 
	   if(keyword.endsWith(",")){
		  keyword = keyword.substring(0,keyword.length-1);
	  }
	   var keywordString = keyword.split(",");
	   if(keywordString.length>6){
	   
		  showMsg("关键字个数不能超过6个！");
		  return false;
	  }
	  
	  for(var i=0; i< keywordString.length;i++){
	    if(keywordString[i].length>5){
	      showMsg("每个关键字字数不能超过5个字符！");
	      return false;
	    }
	  
	  }
	  $.ajax({
			url: base_path+"/updateHouse.do",
			type: "post",
			dataType: "json",
			data: {
			      staffid:staffid,
			    //  roomtype :roomtype,
	             // theme : theme,
	              roomName : roomName,
			      houseid:houseid, 
				  housename : housename,
				  houseNo: houseNo,
				  floor :floor,
				  beds :beds,
				  bedDesc :bedDesc,
				  position :position,
				  broadband :broadband,
				 // originalprice :originalprice,
				  cleanprice :cleanprice,
				  cashpledge: cashpledge,
				  communityName: communityName,
				  decStyle: decStyle,
				  preLocation :preLocation,
				 /*  aftLocation :aftLocation, */
				  cityCode :cityCode,
				  district :district,
				  street : street,
				  property : property,
				  subway : subway,
				 /*  circle: circle, */
				  remarkInput :remarkInput,
				  area :area,
				  status :status,
				  latlng : latlng,
				  longitude : longitude,
				  latitude : latitude,
				  flag : flag,
				 keyword : keyword,
				  device :device,
	              tip :tip,
	              services : services,
                  housedesc :housedesc,
                  recommend :recommend,
                  orderNo :orderNo
                //  currentprice :currentprice
			},
			success: function(json) {
			if(json.result == 0){
			    showMsg(json.message);
			}else{
			        showMsg(json.message);
					window.setTimeout("window.parent.location.reload(true)", 2000);
			
			}
					
				
			},
			error : function(data){
				}
			}); 
  }
  //区域选择联动
/*   function chooseDistrict(){
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
				var schoolList=data.data.schoolList;
				var scenicList=data.data.scenicList;
				var subwayList=data.data.subwayList;

				var streetHtmlString="";
				var circleHtmlString="";
				var schoolHtmlString="";
				var scenicHtmlString="";
				var subwayHtmlString="";
				
				 
				 if(streetList.length==0){
					 streetHtmlString+="<option value=''>暂无街道记录</option>";
				 }else{
				 jQuery.each(streetList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 streetHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#street").html('').append(streetHtmlString); 
				 
				 if(circletList.length==0){
					 circleHtmlString+="<option value=''>暂无商圈记录</option>";
				 }else{
				 jQuery.each(circletList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 circleHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#circle").html('').append(circleHtmlString); 
				 
				 //校区
				 if(schoolList.length==0){
					 schoolHtmlString+="<option value=''>暂无校区记录</option>";
				 }else{
				 jQuery.each(schoolList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 schoolHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#school").html('').append(schoolHtmlString); 

				 //景点
				 if(scenicList.length==0){
					 scenicHtmlString+="<option value=''>暂无景点记录</option>";
				 }else{
				 jQuery.each(scenicList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 scenicHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#scenic").html('').append(scenicHtmlString); 
				 //地铁
				 if(subwayList.length==0){
					 subwayHtmlString+="<option value=''>暂无地铁记录</option>";
				 }else{
				 jQuery.each(subwayList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 subwayHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#subway").html('').append(subwayHtmlString); 
			},
			error: function() {
			}
		});
  } */
  //城市选择联动
/*   function chooseCity(){
	  var cityAdminiCode=$("#cityCode").val();
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
				var schoolList=data.data.schoolList;
				var scenicList=data.data.scenicList;
				var subwayList=data.data.subwayList;
				var districtHtmlString="";
				var streetHtmlString="";
				var circleHtmlString="";
				var schoolHtmlString="";
				var scenicHtmlString="";
				var subwayHtmlString="";
				
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
				 
				 if(streetList.length==0){
					 streetHtmlString+="<option value=''>暂无街道记录</option>";
				 }else{
				 jQuery.each(streetList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 streetHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#street").html('').append(streetHtmlString); 
				 
				 if(circletList.length==0){
					 circleHtmlString+="<option value=''>暂无商圈记录</option>";
				 }else{
				 jQuery.each(circletList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 circleHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#circle").html('').append(circleHtmlString); 
				//校区
				 if(schoolList.length==0){
					 schoolHtmlString+="<option value=''>暂无校区记录</option>";
				 }else{
				 jQuery.each(schoolList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 schoolHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#school").html('').append(schoolHtmlString); 

				 //景点
				 if(scenicList.length==0){
					 scenicHtmlString+="<option value=''>暂无景点记录</option>";
				 }else{
				 jQuery.each(scenicList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 scenicHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#scenic").html('').append(scenicHtmlString); 
				 //地铁
				 if(subwayList.length==0){
					 subwayHtmlString+="<option value=''>暂无地铁记录</option>";
				 }else{
				 jQuery.each(subwayList, function(i,item){
					 var code=item["ADMINI_CODE"];
					 var name=item["ADMINI_NAME"];
					 subwayHtmlString+="<option value='"+code+"'>"+name+"</option>";
				      }); 
				 }
				 $("#subway").html('').append(subwayHtmlString); 
				 
				
			},
			error: function() {
			}
		});
  } */
 // function chooseStreet(){
	  /* getLatlng(); */
 // }
 // function chooseCircle(){
	  /* getLatlng(); */
 // }
 
 
    //城市选择联动新
   function chooseCity(e){
	   //var cityAdminiCode=$("#cityCode").val();
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
	
	function chooseStreet(){
		var rank = $("#property").val();
		if(rank != 7){
			return;
		}
		var subwayLineCode=$("#street").val();
		$.ajax({
			url: base_path+"/queryPlaceBySubwayLine.do",
			type: "post",
			dataType: "json",
			data: {
				subwayLineCode:subwayLineCode
			},
			success: function(data) {
				var subwayList = "";
				subwayList=data.data.subwayList;
				var subwayHtmlString="";
				if(subwayList.length==0){
					 subwayHtmlString+="<option value=''>暂无地铁记录</option>";
				 }else{
					 jQuery.each(subwayList, function(i,item){
						 	var code=item["ADMINI_CODE"];
							var name=item["ADMINI_NAME"];
							subwayHtmlString+="<option value='"+code+"'>"+name+"</option>";
					      }); 
				 }
				 $("#subway").html('').append(subwayHtmlString); 
				 
			},
			error: function() {
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
	
   function dev(){
	 
	  String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,'')};
		var tipsString = $("#roomDevice").val();
		var tip = tipsString.split(",");
		 $(".deviceBody").toggle();
		var checkbox = $('input:checkbox:visible');
		for (var i = 0; i < checkbox.length; i++) {
			for (var j = 0; j < tip.length; j++) {
				if($(checkbox[i]).parent().text().trim() == tip[j].trim()){
					$(checkbox[i]).attr("checked", true);	
				}
				
			}
		}
	 /*  $('input:checkbox').each(function(k){
		  if($(this).is(":visible")){
			  $(this).attr("checked",true);
		  }
		}) */
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
	   $("#roomDevice").val(s.substring(0,s.length -1));
	   $(".deviceBody").toggle();
   }
   
   function tips(){
		 
		  String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,'')};
			var tipsString = $("#roomTip").val();
			var tip = tipsString.split(",");
			 $(".tipBody").toggle();
			var checkbox = $('input:checkbox:visible');
			for (var i = 0; i < checkbox.length; i++) {
				for (var j = 0; j < tip.length; j++) {
					if($(checkbox[i]).parent().text().trim() == tip[j].trim()){
						$(checkbox[i]).attr("checked", true);	
					}
					
				}
			}
		/*   $('input:checkbox').each(function(k){
			  if($(this).is(":visible")){
				  $(this).attr("checked",true);
			  }
			}) */
	   }
   function hiddenTip(){
	   $(".tipBody").toggle();
   }
   function submitTip(){
	   var obj=document.getElementsByName('tipCheckBox');
	   var s='';
	   for(var i=0;i<obj.length-1;i++){
		   if(obj[i].checked){
			   s+=obj[i].value+",";
		   }
	   }
	    
	   $("#roomTip").val(s.substring(0,s.length -1));
	   $(".tipBody").toggle();
   }
   
      function addstaff(){
   
         JDialog.open("新增员工", base_path + "/addHouse2staff.do" ,640,430);
   
   }
   function services(){
	   String.prototype.trim=function(){return this.replace(/(^\s+)|(\s+$)/g,'')};
		var tipsString = $("#roomService").val();
		var tip = tipsString.split(",");
		$(".serviceBody").toggle();
		var checkbox = $('input:checkbox:visible');
		for (var i = 0; i < checkbox.length; i++) {
			for (var j = 0; j < tip.length; j++) {
				if($(checkbox[i]).parent().text().trim() == tip[j].trim()){
					$(checkbox[i]).attr("checked", true);	
				}
				
			}
		}
   }
   function hiddenServices(){
	   $(".serviceBody").toggle();
   }
   
   function submitService() {
   		var serviceBox = $("input[name='serviceCheckBox']");
   		var s = '';
   		var sname = '';
   		$.each(serviceBox, function(index){
   			if($(serviceBox[index]).is(":checked")){
	   			$(serviceBox[index]).parent().text();
	   			s = s + $(serviceBox[index]).val() + ",";
	   			sname = sname + $(serviceBox[index]).parent().text().trim() + ",";
   			}
   		})
   		s = s.substring(0, s.length - 1);
   		sname = sname.substring(0, sname.length - 1);
   		$("#roomService").val(sname);
   		$("#serviceId").val(s);
   		$(".serviceBody").toggle();
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
		   			str += '<li onclick="chooseCity(this)"><input value="' + city.name+ '" type="text" style="width:100%;height:90%;border: 0px;" class="adminNames" readonly/><input class="adminCodes" type="hidden" value="' + city.code +'"/></li>'
		   		}
		   	})
		   	$(".myUlInfo").html(str);
		   	$(".myUlInfo").show();
	   	} else {
	   		$(".myUlInfo").hide();
	   	}
	});
  </script>
</html>
