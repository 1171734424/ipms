<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.ideassoft.core.page.Pagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../../common/script.jsp"%>
<%
	JSONObject pagination = JSONObject.fromObject(request.getAttribute("pagination"));	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/commom_table.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/pagination.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/pagenation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomlist.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/left_order.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" media="all" />
	<style type="text/css">
		.dealandcancle_button {
		    padding: 3px;
		    font-size: 0.75rem;
		    background: #FC8A15;
		    color: #fff;
		    height: 30px;
		    width: 55px;
	    }
	    .applybutton {
		    font-size: 12px;
		    font-family: "微软雅黑";
		    height: 30px !important;
		    line-height: 26px !important;
		    text-align: center;
		    width: 55px !important;
		    margin-left: 4px;
		    border: 1px solid #f1f1f1;
		    background: #898880 !important;
		    color: #fff;
		    cursor: pointer;
		}
		.hidden_div{
		position: absolute;
	    overflow: hidden;
	    width: 100%;
	    height: 100%;
	    background-color: rgba(0, 0, 0, 0.5);
	    z-index: 200;
	    top: 0;
	    left: 0;
	    display:none;
		}
		.show_div{
		position: absolute;
	    left: 8%;
	    top: 18%;
	    width: 84%;
	    height: 60%;
	    background-color: #fff;
	    border-radius: 8px;
	    text-align: center;
	    margin: 0 auto;
		}
		.memberAcount{
		border-collapse:separate;
		border-spacing: 30px;
		}
		.inputStyle{
		width: 270px;
		height: 36px;
		border-bottom-right-radius:6px;
		border-bottom-left-radius:6px;
		border-top-right-radius:6px;
		border-top-left-radius:6px;
		border: 1px solid #dddddd;
		background-color: white;
		margin: 4px;
		}
		td.tdone{
		width:100px;
		}
		td.tdtwo{
		width:300px;
		}
		.submitDiv{
		width: 60%;
	    height: 40px;
	    line-height: 40px;
	    position: relative;
	    top: 64%;
	    margin: 0 auto;
	    left: 18%;
	    padding-top: 25px;
		}
		button#confirm{
			height:36px;
			width:100px;
			background-color:#5A5D9C;
			color:#fff;
			border:none;
		}
		button#cancel{
			height:36px;
			width:100px;
			background-color: #F79F24;
			color:#fff;
			border:none;
		}
		span#confirm,span#cancel{
		    margin: 0 2%;
		}
		table {
		    border: 0px solid #e9e9e9;
		}
		.companyAndDiscount{
			display: none;
		}
	</style>
  </head>
  <body>
   <div class="condition_div">
  	   <form id="pagerForm" name="pagerForm" action="memberManage.do" target="_self" method="post">
	    <div class="div_part_one">
	    	<table id="myTable" class="myTable" border="0" width="100%" style="margin-top: 10px;">
				<thead id="log">
					<tr style="height: 44px;">
						<th class="header">会员名称</th>
						<th class="header">会员等级</th>
						<th class="header">手机号</th>
						<th class="header">当前余额</th>
						<th class="header">当前积分</th>
						<th class="header">公司编号</th>
						<th class="header">公司折扣</th>
						<th class="header">操作</th>
					</tr>
				</thead>
				<tbody id="info">
					<c:forEach items="${memberList}" var="list">
						<tr>
							<td>${list["MEMBER_NAME"]}</td>
							<td>${list["RANKNAME"]}</td>
							<td>${list["MOBILE"]}</td>
							<td>${list["CURR_BALANCE"] }</td>
							<td>${list["CURR_INTEGRATION"] }</td>
							<td>${list["CORPORATIONID"] }</td>
							<td>${list["DISCOUNT"] }</td>
							<td>
								<input class="dealandcancle_button" type="button" id='${list["MEMBER_ID"]}' value="编辑" onclick="edit(this.id)"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pager" style="margin-left: -8px;"></div>
		<div>
			<input type="hidden" id="memberId" name="memberId" value="${memberId }"/>
			<input type="hidden" id="mobile" name="mobile" value="${mobile }"/>
			<input type="hidden" id="memberName" name="memberName" value="${memberName }"/>
			<input type="hidden" id="memberRank" name="memberRank" value="${memberRank }"/>
		</div> 
	</form>
  </div>
  <div class="hidden_div">
			<div class="show_div">
			<table class="memberAcount">
			<tr><td class="tdone">会员号：</td><td class="tdtwo"><input class = "inputStyle" id="memberId_div" value="" style="background: #f0f0f0;" readonly/></td><td class="tdone">会员名称：</td><td class="tdtwo"><input id="memberName_div" class = "inputStyle" value="" style="background: #f0f0f0;" readonly/></td></tr>
			<tr><td class="tdone">会员等级：</td><td class="tdtwo"><input class = "inputStyle" id="memberRank_div" value="" style="background: #f0f0f0;" readonly/></td><td class="tdone">手机号：</td><td class="tdtwo"><input id="mobile_div" class = "inputStyle" value=""  style="background: #f0f0f0;" readonly/></td></tr>
			<tr><td class="tdone">当前余额：</td><td class="tdtwo"><input class = "inputStyle" id="memberBalance" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td><td class="tdone">当前积分：</td><td class="tdtwo"><input id="curryScore" class = "inputStyle" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td></tr>
			<tr class="companyAndDiscount"><td class="tdone">公司编号：</td><td class="tdtwo"><input class = "inputStyle" id="companyMember" value="" style="background: #f0f0f0;" readonly/></td><td class="tdone">公司折扣：</td><td class="tdtwo"><input id="companyDiscount" class = "inputStyle" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td></tr>
			</table>
			</div>
			<div class="submitDiv">
				<span id="confirm"><button id="confirm"
						onclick="save()";>
						保存
					</button>
				</span>
				<span id="cancel"><button id="cancel" onclick="hid()";>
						取消
					</button>
				</span>
			</div>
	</div>
	
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/pager.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		Pager.renderPager(<%=pagination%>);
		var base_path = '<%=request.getContextPath()%>';
	</script>
	<script type="text/javascript">
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function edit(id){
			$.ajax({
				url : base_path + "/editMemberAccount.do",
				dataType : "json",
				type : "post",
				data : {
					id : id
				},
				success : function(json) {
					if(json["0"]["RANKNAME"]=="公司会员"){
						$(".hidden_div").show();
						$(".companyAndDiscount").show();
						$("#memberId_div").val(json["0"]["MEMBER_ID"]);
						$("#memberRank_div").val(json["0"]["RANKNAME"]);
						$("#memberBalance").val(json["0"]["CURR_BALANCE"]);
						$("#memberName_div").val(json["0"]["MEMBER_NAME"]);
						$("#mobile_div").val(json["0"]["MOBILE"]);
						$("#curryScore").val(json["0"]["CURR_INTEGRATION"]);
						$("#companyMember").val(json["0"]["CORPORATIONID"]);
						$("#companyDiscount").val(json["0"]["DISCOUNT"]);	
					}
					$(".hidden_div").show();
					$("#memberId_div").val(json["0"]["MEMBER_ID"]);
					$("#memberRank_div").val(json["0"]["RANKNAME"]);
					$("#memberBalance").val(json["0"]["CURR_BALANCE"]);
					$("#memberName_div").val(json["0"]["MEMBER_NAME"]);
					$("#mobile_div").val(json["0"]["MOBILE"]);
					$("#curryScore").val(json["0"]["CURR_INTEGRATION"]);
				},
				error : function(json){
					showMsg("您没有权限进行该项操作！");
				}
			});
			
		};
		
		function hid(){
			$(".hidden_div").hide();
		}
		function save(){
			var memberId = $("#memberId_div").val();
			var memberBalance = $("#memberBalance").val();
			var curryScore = $("#curryScore").val();
			var companyDiscount = $("#companyDiscount").val();
			if (memberBalance == "") {
				showMsg("余额不能为空！");
				return false;
			}
			if (memberBalance > 99999999) {
				showMsg("余额不能超过八位数！");
				return false;
			}
			
			if (curryScore == "") {
				showMsg("积分不能为空！");
				return false;
			}
			if (curryScore > 99999999) {
				showMsg("积分不能超过八位数！");
				return false;
			}
			
			if($("#memberRank_div").val() == "公司会员"){
				if (companyDiscount == "") {
					showMsg("公司折扣不能为空！");
					return false;
				}
				if (companyDiscount > 100) {
					showMsg("公司折扣不能大于100");
					return false;
				}
			}
			
			$.ajax({
				url : base_path + "/saveMemberAccount.do",
				dataType : "json",
				type : "post",
				data : {
					memberId : memberId,
					memberBalance : memberBalance,
					curryScore : curryScore,
					companyDiscount : companyDiscount
				},
				success : function(json) {
					showMsg("修改成功！");
					$(".hidden_div").hide();
					window.setTimeout("window.location.reload(true)", 1000);
				},
				error : function(){
					showMsg("后台数据错误！");
				}
			});
		}
	</script>
  </body>
</html>
