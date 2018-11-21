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
    
    <title>城市编辑</title>
    
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/addCustom.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
  </head>
  <body>
 	<div style="height:388px;overflow:auto;overflow-x:hidden">
		<div>
			<ul class="editCity">
				<li class="addCustom_li">
					<label>级别:</label>
					<select id="rank" name="rank" class="inputStyle" disabled="disabled">
						<option value="1" <c:if test="${rank eq 1}">selected="selected"</c:if>>市</option>
    					<option value="2" <c:if test="${rank eq 2}">selected="selected"</c:if>>区</option>
    					<option value="3" <c:if test="${rank eq 3}">selected="selected"</c:if>>街道</option>
    					<option value="4" <c:if test="${rank eq 4}">selected="selected"</c:if>>商业圈</option>
    					<option value="5" <c:if test="${rank eq 5}">selected="selected"</c:if>>学校</option>
    					<option value="6" <c:if test="${rank eq 6}">selected="selected"</c:if>>景点</option>
    					<option value="7" <c:if test="${rank eq 7}">selected="selected"</c:if>>地铁线路</option>
    					<option value="7" <c:if test="${rank eq 8}">selected="selected"</c:if>>地铁站</option>
					</select>
				</li>
				<li class="addCustom_li">
					<label>城市编码:</label>
					<input type="text" id="adminiCode" name="adminiCode" value="${adminiCode }" readonly="readonly"/>
				</li>
				<li class="addCustom_li">
					<label>所属城市</label>
					<input id="city" name="city" class="inputStyle"  value="${cityName}" readonly="readonly"/>
				</li>
				<li class="addCustom_li mydistrict" <c:if test="${(rank eq 1)||(rank eq 2)||(rank eq 7)||(rank eq 8)}">style="display:none;"</c:if>>
					<label>所属区域</label>
					<input id="district" name="district" class="inputStyle"  value="${districtName }" readonly="readonly"/>
				</li>
				<li class="addCustom_li subwayLine" <c:if test="${rank ne 8}">style="display:none;"</c:if>>
					<label>所属线路</label>
					<input id="subwayLine" name="subwayLine" class="inputStyle"  value="${subwayLineName }" readonly="readonly"/>
				</li>
				<li class="addCustom_li">
					<label>名称:</label>
					<input id="adminiName" name="adminiName" type="text" value="${adminiName }" <c:if test="${rank eq 1}">readonly="readonly"</c:if>/>
				</li>

				<li class="addCustom_li" <c:if test="${rank ne 1}">style="display:none;"</c:if>>
					<label>推荐排序:</label>
					<input id="orderNumber" name="orderNumber" type="text" value="${orderNumber }"/>
				</li>
				 <li class="addCustom_li">
					<label>状态:</label>
					<select id="status" name="status" class="inputStyle"  <c:if test="${rank eq 1}">disabled="disabled"</c:if>>
					    <option value="1" <c:if test="${status eq 1}">selected="selected"</c:if>>有效</option>
						<option value="0" <c:if test="${status eq 0}">selected="selected"</c:if>>已删除</option>
    					
					</select>
				</li> 
				<li class="addCustom_li" <c:if test="${rank ne 1}">style="display:none;"</c:if>>
					<label>热门城市:</label>
					<select id="hot" name="hot" class="inputStyle" >
						<option value="0" <c:if test="${remark eq 0}">selected="selected"</c:if>>否</option>
    					<option value="1" <c:if test="${remark eq 1}">selected="selected"</c:if>>是</option>
					</select>
				</li>
				<li class="addCustom_li" <c:if test="${rank ne 1}">style="display:none;"</c:if>>
					<label>城市图标:</label>
					<input type="file" id="cityPicture" name="cityPicture" value=""/>
				</li>
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
	var district = '${districtName}';
	
	 $(function () {
		 if(district != '') {
			$(".mydistrict").show();
		}
		});
	
	
	function submit(){
		var formData  = document.getElementById("cityPicture").files[0];
		/* if(formData == undefined){
			showMsg("城市图片不可为空");
			return false;
		} */
		var adminiCode = document.getElementById("adminiCode").value;
		var adminiName = document.getElementById("adminiName").value;
		var rank = document.getElementById("rank").value;
		var orderNumber = document.getElementById("orderNumber").value;
		if(orderNumber != ""){
			var reg = /\s/;
			if(reg.test(orderNumber)){
				alert("请去掉推荐排序空格");
				return false;
			}else{
				var regnum = /^[1-9]\d{0,3}$/;
				if(!regnum.test(orderNumber)){
					alert("请输入1-4位正整数");
					return false;
				}
			}
		}
		var status = document.getElementById("status").value;
		var hot = document.getElementById("hot").value;
		var form = new FormData();
        form.append("file", formData);
        form.append("adminiCode", adminiCode);
        form.append("adminiName", adminiName);
        form.append("rank", rank);
        form.append("orderNumber", orderNumber);
        form.append("status", status);
        form.append("hot", hot);
		$.ajax({
			url: base_path+"/submitEditCity.do",
			type: "post",
			dataType: "json",
			data: form,
			contentType: false,
         	processData: false,
			success: function(json) {
				var result = json.result;
				if (result == 3) {
					showMsg(json.message);
				} else if (result == -1) {
					showMsg(json.message);
				} else if(result == 1){
					showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
					window.parent.refreshGrid();
				}else{
					showMsg("操作失败！");
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
	
	</script>
</html>
