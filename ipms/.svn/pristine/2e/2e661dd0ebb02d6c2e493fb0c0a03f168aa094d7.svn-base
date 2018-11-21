<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
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
    
    <title>城市新增</title>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
  </head>
  <style>
  	.editCity{
  		
  		
  	}
  </style>
  <body>
 	<div style="height:394px;overflow:auto;overflow-x:hidden">
		<div>
			<ul class="editCity">
				<li class="addCustom_li">
					<label>级别</label>
					<select id="rank" name="rank" class="inputStyle">
						<!--<option value="1">市</option>
    					--><option value="2" selected="selected">区</option>
    					<option value="3" >街道</option>
    					<option value="4" >商业圈</option>
    					<option value="5" >学校</option>
    					<option value="6" >景点</option>
    					<option value="7" >地铁线路</option>
    					<option value="8" >地铁站点</option>
					</select>
				</li>
				<li class="addCustom_li">
					<label style="color:red">所属城市</label>
					<input id="city" name="city" class="inputStyle"  value="" onclick="queryCity();"/>
					<input type="hidden" id="city_CUSTOM_VALUE" name="city_CUSTOM_VALUE" value="" />
				</li>
				<li class="addCustom_li mydistrict" style="display:none;">
					<label style="color:red">所属区域</label>
					<input id="district" name="district" class="inputStyle"  value="" onclick="queryDistrict();"/>
					<input type="hidden" id="district_CUSTOM_VALUE" name="city_CUSTOM_VALUE" value="" />
				</li>
				<li class="addCustom_li subwayLine" style="display:none;">
					<label style="color:red">所属线路</label>
					<input id="subwayLine" name="subwayLine" class="inputStyle"  value="" onclick="querySubwayLine();"/>
					<input type="hidden" id="subwayLine_CUSTOM_VALUE" name="city_CUSTOM_VALUE" value="" />
				</li>
				<li class="addCustom_li">
					<label style="color:red">名称:</label>
					<input id="adminiName" naem="adminiName" type="text" value=""/>
				</li>

				<!-- <li class="addCustom_li">
					<label >推荐排序:</label>
					<input id="orderNumber" name="orderNumber" type="text" value=""/>
				</li>
				<li class="addCustom_li">
					<label>状态:</label>
					<select id="status" name="status" class="inputStyle">
    					<option value="1"selected="selected">有效</option>
    					<option value="0">已删除</option>
					</select>
				</li>
				<li class="addCustom_li">
					<label>热门城市:</label>
					<select id="hot" name="hot" class="inputStyle" >
						<option value="0" selected="selected">否</option>
    					<option value="1">是</option>
					</select>
				</li>
				<li class="addCustom_li">
					<label>城市图标:</label>
					<input type="file" id="cityPicture" name="cityPicture" value=""/>
				</li> -->
			</ul>
		</div>
		<div class="contractbutton parents-content">
			  <input class="pdepartsubmit crm-confirmbt" type="button" value="提交" onclick="submit()"/>
		
			  <input class="pdepartcancle crm-cancelbt" type="button" value="关闭" onclick="window.parent.JDialog.close()"/>
	    </div>
	</div>
	
  </body>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
	var base_path = '<%=request.getContextPath()%>';
	
	
	function submit(){
		/* var formData  = document.getElementById("cityPicture").files[0];
		if(formData == undefined){
			showMsg("城市图片不可为空");
			return false;
		} */
		var reg = /\s/;
		var city = document.getElementById("city").value;
		if(city == ""){
			showMsg("所属城市不可为空");
			return false;
		}
		/**if(reg.test(adminiCode)){
			showMsg("城市编码存在空格");
			return false;
		}
		if(adminiCode.length != 12){
			showMsg("城市编码长度为12位");
			return false;
		}**/
		var district = document.getElementById("district").value;
		if( $(".mydistrict").css("display") != 'none' ) {
			if(district == ""){
				showMsg("所属区域不可为空");
				return false;
			}
		}
		if(reg.test(city)){
			showMsg("城市名称存在空格");
			return false;
		}
		var adminiName = $("#adminiName").val();
		if(adminiName == ""){
			showMsg("名称不可为空");
			return false;
		}
		var rank = document.getElementById("rank").value;
		/* var orderNumber = document.getElementById("orderNumber").value;
		if(orderNumber != ""){
			var reg = /\s/;
			if(reg.test(orderNumber)){
				showMsg("推荐排序存在空格");
				return false;
			}else{
				var regnum = /^[1-9]\d{0,3}$/;
				if(!regnum.test(orderNumber)){
					showMsg("请输入1-4位正整数");
					return false;
				}
			}
		} 
		var status = document.getElementById("status").value;
		var hot = document.getElementById("hot").value;*/
		var form = new FormData();
       // form.append("file", formData);
        form.append("city", $("#city_CUSTOM_VALUE").val());
        form.append("adminiName", adminiName);
        form.append("district", $("#district_CUSTOM_VALUE").val());
        form.append("subwayLine", $("#subwayLine_CUSTOM_VALUE").val());
        form.append("rank", rank);
       // form.append("orderNumber", orderNumber);
       // form.append("status", status);
       // form.append("hot", hot);
		$.ajax({
			url: base_path+"/submitAddCity.do",
			type: "post",
			dataType: "json",
			data: form,
			contentType: false,
         	processData: false,
			success: function(json) {
				var result = json.result;
				if(result == 1){
					showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
					window.parent.refreshGrid();
				}else{
					showMsg(json.message);
				}
			},
			error: function(json) {
				showMsg("操作失败！");
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
	
	function queryCity() {
		JDialog.open("所属城市", base_path + "/cityDialog.do",450,350);
	}
	
	function queryDistrict() {
		if ($("#city_CUSTOM_VALUE").val() == null || $("#city_CUSTOM_VALUE").val() == '') {
			showMsg("请先选择城市!");
			return false;
		}
		JDialog.open("所属城市", base_path + "/districtDialog.do?event=" + $("#city_CUSTOM_VALUE").val(),450,350);
	}
	function querySubwayLine() {
		if ($("#city_CUSTOM_VALUE").val() == null || $("#city_CUSTOM_VALUE").val() == '') {
			showMsg("请先选择城市!");
			return false;
		}
		JDialog.open("所属地铁线路", base_path + "/subwayLineDialog.do?event=" + $("#city_CUSTOM_VALUE").val(),450,350);
	}
	
	
	
	
	$("#rank").change(function() {
		if ($("#rank").val() == 2) {
			$(".mydistrict").hide();
		}else if($("#rank").val() == 7) {
			$(".mydistrict").hide();
			$(".subwayLine").hide();
		}else if($("#rank").val() == 8) {
			$(".mydistrict").hide();
			$(".subwayLine").show();
		}else {
			$(".mydistrict").show();
			$(".subwayLine").hide();
		}
	})
	</script>
</html>
