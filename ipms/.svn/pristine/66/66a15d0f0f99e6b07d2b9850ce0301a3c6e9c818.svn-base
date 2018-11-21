<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/theme/default/laydate.css" />
		<title>营销活动edit</title>
		<style type="text/css">
			.check_span{
				height: 34px;
			    line-height: 34px;
			    text-align: center;
			    width: 85px;
			    margin-left: 8px;
			    border:none;
			    border-radius: 4px;
			    color: #fff;
			    cursor:pointer;
				background: #ccc;
			}
			table{
				border:none;
			}
			#campaign1{
				border: none;
			    width: 98%;
			    margin-top: 9px;
			    margin-left: 28px;
			}
			#campaign1 tr td{
				padding:4px;
			}
			/*限时特价*/
			table.alertone tr td.state{
				width: 6.5%;
			}
			table.alertone tr td:nth-child(2n){
				width: 24%;	
			}
			/*积分换优惠券*/
			table.alertzero tr td.state{
				width:6%;
			}
			/*折上折*/
			table.spectwo{
				overflow: scroll;
			    width: 95% !important;
			    margin-top: 0;
			} 
			
			.dayCount{
				width: 175px;
			    padding: 8px 5px;
			    margin-left: 10px;
			}
			
			
			table.spectwo tr td:nth-child(2n){
				width: 24%;	
			}
			table.spectwo tr td:nth-child(3) label{
			    margin-left: 1% !important;
			}
			#campaignDetail{
				height: 200px;
			    overflow-y: auto;
			    margin: 7px 1%;
			    overflow-x: hidden;
			    
			}
			/*首住优惠价*/
			.specbling{
			    margin-left:0 !important;
			}
			.specbling input.text_search{
				margin-left:0;
			}
			/*开卡送抵用券*/
			table.alerthree{
				
			}
			td.blingprice label{
				margin: 0 15px;
			}
			td.blingz input{
				border:none;
				width:30px;
			}
			td.blingz input:first-child{
				margin-left:18px;
			}
			/*充值满送*/
			table.alertwo{
				
			}
		</style>
	</head>
	<body>
	<div class="margin_div">
		<form id="basicInfo" name="basicInfo" class="basicInfo">
			<input type="hidden" id="dataId" name="dataId" value="${campaignInfo.dataId}" />
			<table class="camtable">
				<tr>
					<td>
						业务类型
					</td>
					<td>
						<select name="businessId" id="businessId" class="ui-select text_edit" disabled="disabled">
							<option value="">
								请选择
							</option>
							<c:forEach items="${businessList}" var="bl">
								<option value="${bl.content}">
									${bl.paramName}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						活动名称
					</td>
					<td>
						<input id="campaignName" class="text_edit" name="campaignName" value="${campaignInfo.campaignName}" />
					</td>
					<td>
						活动分类
					</td>
					<td>
						<select id="campaignType" class="text_edit" name="campaignType">
							<option value="">
								请选择
							</option>
							<option value="1">
								充值优惠
							</option>
							<option value="2">
								消费优惠
							</option>
							<option value="3">
								其他
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						适用对象
					</td>
					<td>
						<input id="usingPerson" class="text_edit"  name="usingPerson" value="${usingPersonAndType.USINGPERSON}" />
						<input type="hidden" id="usingPerson_CUSTOM_VALUE" name="usingPerson_CUSTOM_VALUE" value="${campaignInfo.usingPerson}" />
					</td>
					<td>
						使用范围
					</td>
					<td>
						<select id="usingRange" name="usingRange" class="text_edit">
							<option value="">
								请选择
							</option>
							<option value="1,2,3">
								所有
							</option>
							<option value="1">
								app专享
							</option>
							<option value="2">
								小程序专享
							</option>
							<option value="3">
								公众号专享
							</option>
							<option value="1,2">
								app/小程序专享
							</option>
							<option value="1,3">
								app/公众号专享
							</option>
							<option value="2,3">
								小程序/公众号专享
							</option>
						</select>
					</td>
					<td>
						适用类型
					</td>
					<td>
						<input id="usingType"  class="text_edit" name="usingType" value="${usingPersonAndType.USINGTYPE}" />
						<input type="hidden" id="usingType_CUSTOM_VALUE" name="usingType_CUSTOM_VALUE" value="${campaignInfo.usingType}" />
					</td>
				</tr>
				<tr>
					<td>
						开始时间
					</td>
					<td>
						<input id="startTimetemp"  name="startTimetemp" class="text_edit date_text text_search" type="text" value="" />
					</td>
					<td>
						结束时间
						
					</td>
					<td>
						<input id="endTimetemp"  name="endTimetemp" class="text_edit date_text text_search" type="text" value="" />
					</td>
					<td>
						优先级
					</td>
					<td>
						<select id="priority" name="priority" class="text_edit">
							<option value="">
								请选择
							</option>
							<option value="0">
								普通
							</option>
							<option value="1">
								中级
							</option>
							<option value="2">
								高级
							</option>
							<option value="3">
								超高级
							</option>
						</select>
					</td>
				</tr>
				<%--<tr>
					<td>
						优先级
					</td>
					<td>
						<select id="priority" name="priority" class="text_edit">
							<option value="">
								请选择
							</option>
							<option value="0">
								普通
							</option>
							<option value="1">
								中级
							</option>
							<option value="2">
								高级
							</option>
							<option value="3">
								超高级
							</option>
						</select>
					</td>
				</tr>
				--%><tr>
					<td style="width:12%;">
						活动描述
					</td>
					<td colspan="5">
						<textArea id="campaignDesc" class="text_edit" name="campaignDesc" style="border: 1px solid #ddd;width: 94%;margin: 6px 0 0 10px;">${campaignInfo.campaignDesc}</textArea>
					</td>
				</tr>

			</table>
			<div id="campaignDetail" name="campaignDetail">
			</div>
		</form>
		<div >
			<!--<div class="button div_button close" style="display: inline-block;float: right; margin: 8px;" onclick="campaignClose();">
				<span>关闭</span>
			</div>
			--><div class="button div_button save" style="display: inline-block;float: right; margin: 8px;" onclick="alterCampaignInfo();">
				<span>修改</span>
			</div>
			<div class="button div_button delete" style="display: inline-block;float: right; margin: 8px;" onclick="deleteCampaignInfo();">
				<span>删除</span>
			</div>
			<div class="button div_button reset" style="display: inline-block;float: right; margin: 8px;" onclick="campaignPageReset();">
				<span>重置</span>
			</div>
		</div>
	</div>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/crm/campaign/campaignEdit.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/layDate-v5.0.5/laydate/laydate.js" charset="utf-8"></script>
		<script>
		var base_path ="<%=request.getContextPath()%>";
		var IsMarketCenter = "${IsMarketCenter}";
		$(function(){
		    //选中业务类型
			selectInfoSelected(${campaignInfo.businessId},"businessId");
			//选中活动分类
			selectInfoSelected(${campaignInfo.campaignType},"campaignType");
			//选中使用范围
			//selectInfoSelected(${campaignInfo.usingRange},"usingRange");
			$("#usingRange").val('${campaignInfo.usingRange}');
			//优先级选中
			selectInfoSelected(${campaignInfo.priority},"priority");
			//状态选中
			selectInfoSelected(${campaignInfo.status},"status");
			//加载完页面
			var qq = transStartAndEndTimes('${campaignInfo.startTime}');
			var ww = transStartAndEndTimes('${campaignInfo.endTime}');
			//var ee = transStartAndEndTimes('${campaignInfo.effectiveTime}');
			//时间的处理
			$("#startTimetemp").val(qq);
			$("#endTimetemp").val(ww);
			//$("#effectiveTimetemp").val(ee);
			loadPageByBusinessIdNew();
			//动态加载的页面填入相应的数据
			loadData();
			
			 <c:if test ="${branchRank.rank != '0' && campaignInfo.businessId != '6'}">
				//将三个按钮设为不可点击
				$(".save").attr("onclick","");
		 		$(".save").addClass("check_span");
		 		$(".delete").attr("onclick","");
		 		$(".delete").addClass("check_span");
		 		$(".reset").attr("onclick","");
		 		$(".reset").addClass("check_span");
			</c:if>;	
			
			<c:if test='${IsMarketCenter == ""}'>			
				$("#usingType").attr("readOnly",true);
			</c:if>
		})
		
		//将相应的页面的select值选中
		function selectInfoSelected(obj,idName){
			for ( var j = 0; j < $("#"+idName+" option").length; j++) {
				if (obj == $("#"+idName+" option")[j].value) {
					$("#"+idName+" option")[j].selected = true;
				}
			}
		}
		
		function loadData(){
			if($("#businessId").val() == "1"){
				$("#goodsService").val(${campaignInfo.goodsService});
				$("#couponGroupId").val(${campaignInfo.couponGroupId});
				$("#couponName").val(${couponList}[0].couponName);
				var showRule ="";
				if(${couponList}[0].tenCoupon != "" && ${couponList}[0].tenCoupon != null){
					showRule = showRule + "10元劵"+${couponList}[0].tenCoupon +"张，";
				}
				if(${couponList}[0].twentyCoupon != null && ${couponList}[0].twentyCoupon != ""){
					showRule = showRule + "20元劵"+${couponList}[0].twentyCoupon +"张，";
				}
				if(${couponList}[0].thirtyCoupon != null && ${couponList}[0].thirtyCoupon != ""){
					showRule = showRule + "30元劵"+${couponList}[0].thirtyCoupon +"张，";
				}
				if(${couponList}[0].fiftyCoupon != null && ${couponList}[0].fiftyCoupon != ""){
					showRule = showRule + "50元劵"+${couponList}[0].fiftyCoupon +"张，";
				}
				if(${couponList}[0].hundredCoupon != null && ${couponList}[0].hundredCoupon != ""){
					showRule = showRule + "100元劵"+${couponList}[0].hundredCoupon +"张，";
				}
				$("#showRule").val(showRule.substring(0,showRule.length-1));
				$("#couponPrice").val(${couponList}[0].totalPrice);
			}else if($("#businessId").val() == "2"){
				$("#couponPrice").val(${campaignInfo.couponPrice});
				$("#couponGroupId").val(${campaignInfo.couponGroupId});
				$("#tenCoupon").val(${couponList}[0].tenCoupon);
				$("#twentyCoupon").val(${couponList}[0].twentyCoupon);
			    $("#thirtyCoupon").val(${couponList}[0].thirtyCoupon);
				$("#fiftyCoupon").val(${couponList}[0].fiftyCoupon);
				$("#hundredCoupon").val(${couponList}[0].hundredCoupon);
			}else if($("#businessId").val() == "3"){
				$("#operMoney").val(${campaignInfo.operMoney});
				$("#couponPrice").val(${campaignInfo.couponPrice});
				$("#couponGroupId").val(${campaignInfo.couponGroupId});
				$("#tenCoupon").val(${couponList}[0].tenCoupon);
				$("#twentyCoupon").val(${couponList}[0].twentyCoupon);
			    $("#thirtyCoupon").val(${couponList}[0].thirtyCoupon);
				$("#fiftyCoupon").val(${couponList}[0].fiftyCoupon);
				$("#hundredCoupon").val(${couponList}[0].hundredCoupon);
			}else if($("#businessId").val() == "4"){
				$("#discountPrice").val(${campaignInfo.discountPrice});
			}else if($("#businessId").val() == "5"){
				$("#liveDay").val(${campaignInfo.liveDay});
				for(var i=0;i<${campaignRuleList}.length;i++){
					$("#campaign1").append("<tr><td class='state'><label>第"+(i+1)+"天&nbsp;&nbsp;&nbsp;&nbsp; 折扣</label></td><td><input id='dayCount"+(i+1)+"' name='dayCount"+(i+1)+"' class='dayCount' value='"+${campaignRuleList}[i].dayCount+"'/></td><td class='state'><label>%</label></td></tr>");
				}
			}else if($("#businessId").val() == "6"){
			 	$("#discountPrice").val(${campaignInfo.discountPrice});
			 	$("#branchId").val(${branchLists}[0].branchName);
			 	$("#branchId_CUSTOM_VALUE").val(${branchLists}[0].branchId);
			 	$("#roomType").val(${camroomLists}[0].roomName);
			 	$("#roomType_CUSTOM_VALUE").val(${camroomLists}[0].roomTypeKey.roomType);	
			 	$("#experienceCount").val(${campaignInfo.experienceCount});	
			}else if($("#businessId").val() == "7"){
				$("#operScore").val(${campaignInfo.operScore});
				$("#couponGroupId").val(${campaignInfo.couponGroupId});
				$("#couponName").val(${couponList}[0].couponName);
				var showRule ="";
				if(${couponList}[0].tenCoupon != "" && ${couponList}[0].tenCoupon != null){
					showRule = showRule + "10元劵"+${couponList}[0].tenCoupon +"张，";
				}
				if(${couponList}[0].twentyCoupon != null && ${couponList}[0].twentyCoupon != ""){
					showRule = showRule + "20元劵"+${couponList}[0].twentyCoupon +"张，";
				}
				if(${couponList}[0].thirtyCoupon != null && ${couponList}[0].thirtyCoupon != ""){
					showRule = showRule + "30元劵"+${couponList}[0].thirtyCoupon +"张，";
				}
				if(${couponList}[0].fiftyCoupon != null && ${couponList}[0].fiftyCoupon != ""){
					showRule = showRule + "50元劵"+${couponList}[0].fiftyCoupon +"张，";
				}
				if(${couponList}[0].hundredCoupon != null && ${couponList}[0].hundredCoupon != ""){
					showRule = showRule + "100元劵"+${couponList}[0].hundredCoupon +"张，";
				}
				$("#showRule").val(showRule.substring(0,showRule.length-1));
				$("#couponPrice").val(${couponList}[0].totalPrice);
			}else if($("#businessId").val() == "8"){
				$("#discountPrice").val(${campaignInfo.discountPrice});
			 	$("#branchId").val(${branchLists}[0].BRANCHNAME);
			 	$("#branchId_CUSTOM_VALUE").val(${branchLists}[0].BRANCHID);
			 	$("#roomId").val(${campaignInfo.roomId});
			 	var aa = transStartAndEndTimes('${campaignInfo.startShowTime}');
				var bb = transStartAndEndTimes('${campaignInfo.endShowTime}');
				//时间的处理
				$("#startShowTime").val(aa);
				$("#endShowTime").val(bb);
			}else if($("#businessId").val() == "11"){
				$("#roomCount").val(${campaignInfo.roomCount});
				$("#experienceCount").val(${campaignInfo.experienceCount});
			}else if($("#businessId").val() == "13"){
				$("#operMoney").val(${campaignInfo.operMoney});
			}else{
				$("#campaignDetail").html("");
			}
		}		
		
		//取转换后的开始时间，结束时间
		function transStartAndEndTimes(obj){
			if(obj.indexOf("-") > 0){
				var tempsdate = obj.replace(/-/g,"/").substring(0,obj.replace(/-/g,"/").length-2); 
				return tempsdate;
			}else{
				return obj;
			}
			
		}
	</script>
	</body>
</html>