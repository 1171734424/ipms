
jQuery(document).ready(function() {
	
});

function replyComment(rowid){
	var rowData = getRowDataById(rowid);
	var commentId = rowData["COMMENT_ID"];
	JDialog.open("商家评论回复", base_path + "/toAddCommentReply.do?commentId="+commentId,800,450);
}



//新增门店图片
function delBranch(rowid){
	var branchId = concatColumns("BRANCHID");
/*	var rowData = getRowDataById(rowid);
	var branchId=rowData["BRANCHID"];*/
	$.ajax({
		url: base_path + "/branchDelete.do",
		type: "post",
		dataType: "json",
		data: { branchId: branchId },
		success: function(result) {
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}else if(result == 3){
				showMsg("分店员工不能删除门店！");
			} else if (result == -1) {
				showMsg("总店不能被删除！");
				refreshGrid();
			} else {
				showMsg("数据异常！");
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}
//删除民宿
function delHouse(rowid){
	var branchId = concatColumns("BRANCHID");
/*	var rowData = getRowDataById(rowid);
	var branchId=rowData["BRANCHID"];*/
	$.ajax({
		url: base_path + "/houseDelete.do",
		type: "post",
		dataType: "json",
		data: { branchId: branchId },
		success: function(result) {
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}else if(result == 3){
				showMsg("分店员工不能删除门店！");
			}else{
				showMsg("数据异常！");
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}
//新增门店图片
function dealinvester(rowid){
	var rowData = getRowDataById(rowid);
	var STATUS=rowData["STATUS"];
	var remark=rowData["REMARK"];
	var INVESTERAPPLYID=rowData["INVESTERAPPLYID"];
		//JDialog.open("处理申请", base_path + "/editinvester.do?INVESTERAPPLYID="+INVESTERAPPLYID+"&remark="+remark,400,200);
	if(STATUS == '未处理'){
		$.ajax({
			url: base_path + "/updateinvester.do",
			type: "post",
			dataType: "json",
			data: { INVESTERAPPLYID: INVESTERAPPLYID },
			success: function(json) {
				if (json.result == 1) {
					showMsg("操作成功");
					window.setTimeout("location.reload(true)", 1000);
				}
			},
			error: function(json) {
				showMsg("操作失败！");
			}
		});
	}else{
		showMsg("该条数据已处理！");
	}
	
}

//新增门店图片
function addPic(rowid){
	var rowData = getRowDataById(rowid);
	var branchId=rowData["BRANCHID"];
	JDialog.open("门店图片新增", base_path + "/addBranchPic.do?branchId="+branchId+"&scence=0",850,540);
}
function editBranch(rowid){
	var rowData = getRowDataById(rowid);
	var branchId=rowData["BRANCHID"];
	var branchType=rowData["BRANCHTYPE"];
	if(!branchId&&!branchType){
		JDialog.open("门店新增", base_path + "/addBranch.do?",800,560)
	}else{
		JDialog.open("门店编辑", base_path + "/editBranch.do?branchId="+branchId,800,560);
	}
}

function editHouse(rowid){
	
	var rowData = getRowDataById(rowid);
	var houseid=rowData["BRANCHID"];

	if(!houseid){

		JDialog.open("民宿新增", base_path + "/addHouse.do",1050,500);

	}else{
		JDialog.open("民宿编辑", base_path + "/editHouse.do?houseid="+houseid,1050,500);
	}
		
}
function transportData() {
	jQuery("#progressbar").css("display", "block");
	jQuery("#progressbar").progressbar({ value : 0 });
//	jQuery("#progressbar").progressbar({ value : false });
	
	load_flag = true;
	setProcessValue();
	
	//TODO transport data call servlet
	jQuery("#progressbar").progressbar({
		complete : function(event, ui) {
			showMsg("load complete!");
			jQuery("#progressbar").css("display", "none");
			load_flag = false;
		}
	});
}

function setProcessValue() {
	var cnt = jQuery("#progressbar").progressbar("option", "value") + 1;
	jQuery("#progressbar").progressbar("option", "value", cnt);
	if (load_flag && cnt <= 100) setTimeout("setProcessValue()", 100);
}

function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

function delDepart() {
	var departIds = concatColumns("DEPARTID");

	$.ajax({
		url: base_path + "/departDelete.do",
		type: "post",
		dataType: "json",
		data: { departIds: departIds },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delMember() {
	var memberid = concatColumns("MEMBERID");
	$.ajax({
		url: base_path + "/delMember.do",
		type: "post",
		dataType: "json",
		data: { memberid: memberid },
		success: function(json) {
			var result = json.code;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delGoodsType() {
	var paramId = concatColumns("PARAMID");
	$.ajax({
		url: base_path + "/delGoodsType.do",
		type: "post",
		dataType: "json",
		data: { paramId: paramId },
		success: function(json) {
			var result = json.code;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delContrart() {
	var contractId = concatColumns("CONTRARTID");
	$.ajax({
		url: base_path + "/delContrart.do",
		type: "post",
		dataType: "json",
		data: { contractId: contractId },
		success: function(json) {
			var result = json.code;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function delUserCascade() {
	var userIds = concatColumns("USERID");

	$.ajax({
		url: base_path + "/userDelete.do",
		type: "post",
		dataType: "json",
		data: { userIds: userIds },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("操作成功");
				refreshGrid();
			} else {
				var msg = json.message;
				showMsg("用户[" + msg.substring(0, msg.length - 1) + "]在线，无法删除！");
			}
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function roleRightEdit(rowid) {
	var roleId = "";
	if (rowid) {
		var rowData = $(grid_name).jqGrid("getRowData", rowid);
		roleId = rowData["ROLEID"];
	}
	JDialog.open("角色管理", base_path + "/roleRightEdit.do?roleId=" + roleId, 920, 580, "doSubmit");
}

function roleRightDelete() {
	var roleIds = concatColumns("ROLEID");
	
	$.ajax({
		url: base_path + "/roleRightDelete.do",
		type: "post",
		dataType: "json",
		data: { roleIds: roleIds },
		success: function(json) {
			showMsg("操作成功！");
			refreshGrid();
		},
		error: function(json) {
			showMsg("操作失败！");
		}
	});
}

function addAdjustButton(v) {
	return "<a class='b_button_cell'><span class='b_minus' onclick='adjustNum(this, -1)'>-</span></a>"
		 + "<span name='val'>" + v + "</span>" 
		 + "<a class='b_button_cell'><span class='b_plus' onclick='adjustNum(this, 1)'>+</span></a>";
}

function adjustNum(obj, num) {
	var val = $(obj).parent().parent().find("span[name=val]");
	val.html(parseInt(val.html()) + num);
}

function closeDialog(){
	window.parent.JDialog.close();
}


//新增获取当前门店信息
function getBranch(e, tag){
	$.ajax({
		url: base_path + "/getBranchId.do",
		type: "post",
		dataType: "json",
		data: { tag : tag },
		success: function(json) {
			 if(json.result == 1){
				 e.val(json.data?json.data:tag);
			 }else if(json.result == 2){
				 e.val(json.data?json.data:tag);
			 }
		},
		error: function(json) {
		}
	});
}

//储值卡充值
function cardRecharge(rowid){
	var rowData = getRowDataById(rowid);
	var memberid=rowData["MEMBERID"];
	$.ajax({
		url: base_path + "/memberInfo.do",
		type: "post",
		dataType: "json",
		data: { memberid : memberid },
		success: function(json) {
			if(json.memberRank == '1'){
				showMsg("请注册会员!");
			}else{
				JDialog.open("储值卡充值", base_path + "/turnToCardRecharge.do?memberid="+memberid,600,400);
			}
		},
		error: function(json) {
		}
	});
}
//查看合同文档
function querycontract(rowid){
	var rowData = getRowDataById(rowid);
	var contract=rowData["CONTRART"];
	JDialog.open("合同查看", base_path + "/queryContract.do?contract="+contract,1000,600);
}
//房租续约
function rentRenewal(rowid){
	var rowData = getRowDataById(rowid);
	var contractId=rowData["CONTRARTID"];
	JDialog.open("房租续约", base_path + "/rentRenewal.do?contractId="+contractId,600,300);
}
//会员卡升级
function memberUpGrade(rowid){
	var rowData = getRowDataById(rowid);
	var accountid=rowData["ACCOUNTID"];
	var memberid=rowData["MEMBERID"];
	$.ajax({
		url: base_path + "/memberInfo.do",
		type: "post",
		dataType: "json",
		data: { memberid : memberid },
		success: function(json) {
			if(json.memberRank == '0'){
				showMsg("公司会员不可以升级!");
			}else if(json.memberRank == '1'){
				showMsg("请注册会员!");
			}else if(json.memberRank == '5'){
				showMsg("不满足升级黑钻会员资格!");
			}else if(json.memberRank == '6'){
				showMsg("该会员已到当前最高级别!");
			}else{
				JDialog.open("会员升级", base_path + "/memberUpGrade.do?accountid="+accountid+"&memberid="+memberid,800,450);
			}
		},
		error: function(json) {
		}
	});
}
//会员冻结
function freezeMember(rowid){
	var rowData = getRowDataById(rowid);
	var memberid=rowData["MEMBERID"];
	showMsg("是否冻结？",function(){
		$.ajax({
			url: base_path + "/freezeMember.do",
			type: "post",
			dataType: "json",
			data: { memberid : memberid },
			success: function(json) {
				if(json.result == '1'){
					showMsg(json.message);
					window.setTimeout("location.reload(true)", 1800);
				}else{
					showMsg("操作失败!");
				}
			},
			error: function(json) {
			}
		});
	});
	
}
//会员解冻
function recoverMember(rowid){
	var rowData = getRowDataById(rowid);
	var memberid=rowData["MEMBERID"];
	JDialog.open("更改手机号", base_path + "/updateMemberPhone.do?memberid="+memberid,400,200);
}
//会员卡升级充值
function payUpGradeMemberLevel(member){
	var memberrank = member.id;
	//var memberrankname = member.value;
	var accountid = $("#accountid").val();
	var memberid = $("#memberid").val();
	//JDialog.open("会员", base_path + "/payUpGradeMemberLevel.do?memberrank="+memberrank+"&accountid="+accountid+"&memberid="+memberid+"&memberrankname="+memberrankname,450,200);
	JDialog.open("会员", base_path + "/payUpGradeMemberLevel.do?memberrank="+memberrank+"&accountid="+accountid+"&memberid="+memberid,450,200);
}

//新增模板 
function addSmsTemplatePage(){
	JDialog.open("模板新增", base_path + "/turnToSmsTemplateAddPage.do", 800, 420);	
}

//模板修改编辑
function alterSmsTemplate(rowid){
	var rowData = getRowDataById(rowid);
	var templateId = rowData["TEMPLATEID"];
	JDialog.open("模板修改", base_path + "/turnToalterSmsTemplatePage.do?templateId="+templateId,800,420)
}

//审核信息页面
function auditInfoDetail(rowid){
	var rowData = getRowDataById(rowid);
	var audittype = rowData["TABLEAUDITTYPE"];
	var operid = rowData["OPERID"];
	var pagetype = "audit" ;
	if(audittype=="采购申请"){
		JDialog.open("审核信息", base_path + "/auditInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,510);
	}else if(audittype=="房价申请"){
		JDialog.open("审核信息", base_path + "/rpapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,700);
	}else if(audittype=="退房申请"){
		JDialog.open("审核信息", base_path + "/checkoutInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,300);
	}else if(audittype=="维修申请"){
		JDialog.open("审核信息", base_path + "/repairapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,340);
	}
	
}

//审核日志信息页面
function audiLogInfoDetail(rowid){
	var rowData = getRowDataById(rowid);
	var audittype = rowData["OPERTYPE"];
	var operid = rowData["OPERID"];
	var pagetype = "auditlog" ;
	if(audittype=="采购申请"){
		JDialog.open("审核信息", base_path + "/auditInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,780,510);
	}else if(audittype=="房价申请"){
		JDialog.open("审核信息", base_path + "/rpapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,500);
	}else if(audittype=="退房申请"){
		JDialog.open("审核信息", base_path + "/checkoutInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,260);
	}else if(audittype=="维修申请"){
		JDialog.open("审核信息", base_path + "/repairapplyInfoDetail.do?operid="+operid+"&pagetype="+pagetype+"&audittype="+audittype,980,300);
	}
}

//短信发送的详情（某一条数据是否发送成功，短信内容是什么）
function turnToSmsShowDetail(rowid){
	var rowData = getRowDataById(rowid);
	var templateId = rowData["TEMPLATEID"];
	JDialog.open("发送详情", base_path + "/turnToSmsShowDetailPage.do?templateId="+templateId,1000,520);
}

//积分调整
function scoreAdjustment(rowid){
	var rowData = getRowDataById(rowid);
	var accountid=rowData["ACCOUNTID"];
	JDialog.open("积分调整", base_path + "/turnToScoreAdjustment.do?accountid="+accountid,600,300);
}

//新增营销活动
//20180626修改
function addCampaignsPage(){
	JDialog.open("活动新增", base_path + "/turnToCampaignsAddPage.do", 870, 630);	
}
//新增合同
//function addContrart(rowid){
//	var rowData = getRowDataById(rowid);
//	var contractId = rowData["CONTRARTID"];
//	if(!contractId){
//		JDialog.open("合同新增", base_path + "/turnToContrartAddPage.do", 850, 500);	
//	}
//	
//}

//function updateContrart(rowid){
//	var rowData = getRowDataById(rowid);
//	var contractId = rowData["CONTRARTID"];
//	JDialog.open("合同修改", base_path + "/updateContrart.do?contractId="+contractId, 870, 550);	
//}

//活动修改编辑
function alterCampaign(rowid){
	var rowData = getRowDataById(rowid);
	var campaignId = rowData["DATAID"];
	JDialog.open("活动修改", base_path + "/turnAlterCampaignPage.do?campaignId="+campaignId,870,630)
}


//客户来源分析详细信息查看
function showDetailInformation(rowid){
	var rowData = getRowDataById(rowid);
	var time = rowData["ORDERTIME"];
	JDialog.open("客户来源详情", base_path + "/showDetailInformation.do?time=" + time ,987,410);
	
}
//订单消结一栏表
function qIntoAccountDetil(rowid){
	var rowData = getRowDataById(rowid);
	var checkId = rowData["CHECKID"];
	JDialog.open("账单详情", base_path + "/qIntoAccountDetil.do?checkId=" + checkId ,987,410);
	
}
//订单消结一栏表
function queryHosueAbnormal(rowid){
	var rowData = getRowDataById(rowid);
	var checkId = rowData["CHECKID"];
	JDialog.open("异常详情", base_path + "/queryHosueAbnormal.do?checkId=" + checkId ,987,410);
	
}
//会员层次统计详情
function showMemberDetailInfo(rowid){
	var rowData = getRowDataById(rowid);
	var time = rowData["REGISTERTIME"];
	
	JDialog.open("会员详情", base_path + "/showMemberDetailInfo.do?time=" + time ,1230,410);
}
//会员消费详情
function  showConsumeDetailInfo(rowid){
	var rowData = getRowDataById(rowid);
	var memberID = rowData["MEMBERID"];
	JDialog.open("消费详情", base_path + "/showConsumeDetailInfo.do?memberID=" + memberID,1500,410);
}
//房间销售明细订单详情
function OrderStream(rowid){
	var rowData = getRowDataById(rowid);
	var roomtype = rowData["ROOMTYPE"];
	var startdate = rowData["STARTDATE"];
	var enddate = rowData["ENDDATE"];
	JDialog.open("详情查询", base_path + "/orderRoomDetailShow.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,800,380)
}
//房间销售汇总房单详情
function RoomSummary(rowid){
	var rowData = getRowDataById(rowid);
	var roomtype = rowData["ROOMTYPE"];
	var startdate = rowData["STARTDATE"];
	var enddate = rowData["ENDDATE"];
	JDialog.open("详情查询", base_path + "/checkDetailShow.do?roomtype="+roomtype+"&startdate="+startdate+"&enddate="+enddate,1000,380)
}

//房间管理自定义逻辑删除
function delRoom(){
	var theme = concatColumns("THEME");
	var branchid = concatColumns("BRANCHID");
	var roomid = concatColumns("ROOMID");
	var roomtype = concatColumns("ROOMNAME");
	var roomstatus = concatColumns("STATUS");
	var roomarea = concatColumns("AREA");
	var roomfloor = concatColumns("FLOOR");
	var roomremark = concatColumns("REMARK");
	$.ajax({
		url: base_path + "/delRoom.do",
		type: "post",
		dataType: "json",
		data: { theme: theme,
		        branchid: branchid,
		        roomid: roomid,
		        roomtype: roomtype,
		        roomstatus: roomstatus,
		        roomarea: roomarea,
		        roomfloor: roomfloor,
		        roomremark: roomremark
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//房型管理自定义逻辑删除
function delRoomType(){	
	var roomType = concatColumns("ROOMTYPE");
	var branchId = concatColumns("BRANCHID");
	var theme = concatColumns("THEME");
	$.ajax({
		url: base_path + "/delRoomType.do",
		type: "post",
		dataType: "json",
		data: { roomType: roomType,
			branchId : branchId,
			theme : theme
		},
		success: function(json) {
			if (json == 1) {
				showMsg("删除成功");
				window.location.reload();
				 window.setTimeout("location.reload(true)", 1800);
			} else if(json == -1) {
				showMsg("总店不可以删除酒店公寓房型！");
				 window.setTimeout("location.reload(true)", 1800);
			}else{
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}


//房价申请页面
function rpApply(rowid){
	var rowData = getRowDataById(rowid);
	var themename = rowData["THEME"];
	var branchname = rowData["BRANCHID"];
	var rpkindname = rowData["RPKIND"];
	var roomtypename = rowData["ROOMTYPE"];
	var status = rowData["STATUS"];
	JDialog.open("房价管理", base_path + "/rpBasicApplyfirst.do?themename="+themename+"&branchname="+branchname+"&rpkindname="+rpkindname+"&roomtypename="+roomtypename+"&status="+status,1000,610);		
}

//房价申请new
function rpapplyprice(){
	$.ajax({
		url : base_path + "/rpBasicjudge.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			if (json.result == 1) {
				if(json.message){
			     showMsg(json.message);
				}else{
					 JDialog.open("房价管理", base_path + "/rpPageApply.do",1000,610);
				}
				
			}else{
				 showMsg("操作失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
	
}

//时租申请new
function rpapplypricehour(){
	var hourprice = "2";
	$.ajax({
		url : base_path + "/rpBasicjudge.do",
		type: "post",
		dataType: "json",
		data: { hourprice: hourprice
		},
		success: function(json) {
			if (json.result == 1) {
				if(json.message){
			     showMsg(json.message);
				}else{
					JDialog.open("房价管理", base_path + "/rpPageApply.do?hourprice="+hourprice,1000,610);
				}
				
			}else{
				 showMsg("操作失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}


//民宿房价申请页面
function houserpApply(rowid){
	var rowData = getRowDataById(rowid);
	var branchId = rowData["BRANCHID"];
	var branchname = rowData["HOUSENAME"];
	var houseType = rowData["ROOMTYPE"];
	var houseNo = rowData["HOUSENO"];
	var roomPrice = rowData["ROOMPRICE"];
	var rpType = rowData["RPTYPE"];
	if(rpType=="调整"){
		JDialog.open("房价管理", base_path + "/houserpBasicApply.do?branchId="+branchId+
				"&branchname="+branchname+
				"&houseType="+houseType+
				"&houseNo="+houseNo+
				"&roomPrice="+roomPrice+
				"&rpType="+rpType,1000,420);
	}else{
		JDialog.open("房价管理", base_path + "/houserpBasicApply.do?branchId="+branchId+
				"&branchname="+branchname+
				"&houseType="+houseType+
				"&houseNo="+houseNo+
				"&roomPrice="+roomPrice+
				"&rpType="+rpType,1000,300);
	}
			
}


//公寓房价申请页面
function apartmentpApply(rowid){
	var rowData = getRowDataById(rowid);
	var branchId = rowData["BRANCHID"];
	var branchName = rowData["BRANCHNAME"];
	var roomName = rowData["ROOMNAME"];
	var roomType = rowData["ROOMTYPE"];
	var roomPrice = rowData["ROOMPRICE"];
	var rpType = rowData["RPTYPE"];
	var theme = rowData["THEME"];
	var priceType = rowData["PRICETYPE"];
	
	if(status=="调整"){
		JDialog.open("房价管理", base_path + "/apartmentBasicApply.do?branchId="+branchId+
				"&branchName="+branchName+
				"&roomName="+roomName+
				"&roomType="+roomType+
				"&roomPrice="+roomPrice+
				"&priceType="+priceType+
				"&theme="+theme+
				"&rpType="+rpType,1000,420);
	}else{
		JDialog.open("房价管理", base_path + "/apartmentBasicApply.do?branchId="+branchId+
				"&branchName="+branchName+
				"&roomName="+roomName+
				"&roomType="+roomType+
				"&roomPrice="+roomPrice+
				"&priceType="+priceType+
				"&theme="+theme+
				"&rpType="+rpType,1000,300);
	}
			
}

//房价初始化
function rpInitialize(){
	$.ajax({
		url: base_path + "/rpInitialize.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			if (json.result == 1) {
				if(json.message){
			     showMsg(json.message);
				}else{
					showMsg("初始化成功");
					window.setTimeout("location.reload(true)", 1800);
				}
				
			} else {
				 showMsg("初始化失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
	
}



//商品自定义逻辑删除
function delGoods(){	
	var goodsid = concatColumns("GOODSID");
	$.ajax({
		url: base_path + "/delGoods.do",
		type: "post",
		dataType: "json",
		data: { goodsid: goodsid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//商品分类自定义逻辑删除
function delCategory(){	
	var categoryid = concatColumns("CATEGORYID");
	$.ajax({
		url: base_path + "/delCategory.do",
		type: "post",
		dataType: "json",
		data: { categoryid: categoryid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//房间管理自定义新增编辑
function editrpRow(rowid){
	var rowData = getRowDataById(rowid);
	var theme = rowData["THEME"];
	var branch = rowData["BRANCHID"];
	var roomtype = rowData["ROOMNAME"];
	var floor = rowData["FLOOR"];
	var roomid = rowData["ROOMID"];
	var area = rowData["AREA"];
	var status = rowData["STATUS"];
	var remark = rowData["REMARK"];
	if((!branch)&&(!roomid)){
		$.ajax({
			url: base_path + "/judgeRoomAdd.do",
			type: "post",
			dataType: "json",
			success: function(json) {
				if (json.result == 1) {
				//	if(json.message){
				//     showMsg(json.message);
				//	}else{
						JDialog.open("添加记录", base_path + "/roomAdd.do" ,500,420);
				//	}
					
				} else {
					 showMsg("操作失败");
					 window.setTimeout("location.reload(true)", 1800);
				}
			},
			error: function(json) {
				showMsg("操作失败！");
				 window.setTimeout("location.reload(true)", 1800);
			}
		});
		
	}else{
		
		JDialog.open("编辑记录", base_path + "/roomEdit.do?theme="+theme+"&branch="+branch+"&roomtype="+roomtype+"&floor="+floor+"&roomid="+roomid+"&area="+area+"&status="+status+"&remark="+remark ,500,420);
	}	
}



// 商品类别管理自定义新增编辑
function editGoodsTypeRow(rowid){
	var rowData= getRowDataById(rowid);
	var categoryId = rowData["CATEGORYID"];
	var categoryName = rowData["CATEGORYNAME"];
	var branchId = rowData["BRANCHID"];
	var chargeRoom = rowData["CHARGEROOM"];
	var superCategory = rowData["SUPERCATEGORY"];
	var status = rowData["STATUS"];
	var remark = rowData["REMARK"];
	if((!categoryId)){
		JDialog.open("添加记录", base_path + "/addCrmGoodsType.do" ,500,460);
	}else{
		JDialog.open("编辑记录", base_path + "/editCrmGoodsType.do?categoryName="+categoryName+
															"&branchId="+branchId+
															"&chargeRoom="+chargeRoom+
															"&superCategory="+superCategory+
															"&status="+status+
															"&remark="+remark+
															"&categoryId="+categoryId
															,500,460);
	}	
}

//商品资料管理自定义新增编辑
function editGoodsManage(rowid){
	
	var rowData= getRowDataById(rowid);
	var goodsId = rowData["GOODSID"];
	var goodsName = rowData["GOODSNAME"];
	var categoryId = rowData["CATEGORYID"];
	var branchId = rowData["BRANCHID"];
	var supplierId = rowData["SUPPLIERID"];
	var price = rowData["PRICE"];
	var specifications = rowData["SPECIFICATIONS"];
	var unit = rowData["UNIT"];
	var saleType = rowData["SALETYPE"];
	var status = rowData["STATUS"];
	var integral = rowData["INTEGRAL"];
	var productionDate = rowData["PRODUCTIONDATE"];
	var remark = rowData["REMARK"];
	
	if(!goodsId){
		JDialog.open("添加记录", base_path + "/addCrmGoodsManage.do" ,500,420);
	}else{
		JDialog.open("编辑记录", base_path + "/editCrmGoodsManage.do?goodsId="+goodsId+
															"&goodsName="+goodsName+
															"&categoryId="+categoryId+
															"&branchId="+branchId+
															"&supplierId="+supplierId+
															"&price="+price+
															"&specifications="+specifications+
															"&unit="+unit+
															"&saleType="+saleType+
															"&status="+status+
															"&integral="+integral+
															"&productionDate="+productionDate+
															"&remark="+remark
															,500,420);
	}	
}

/*
 * 自定义房态新增
 * <column columnId="ROOMTYPE" name="房型码" width="120" editable="true" align="center" hidden="true" primaryKey="true"/>
					<column columnId="ROOMNAME" name="房型名称" width="120" editable="true" align="center"/>
					<column columnId="THEME" name="场景" width="120" editable="true" align="center"/>
					<column columnId="ROOMBED" name="床位" width="120" editable="true" align="center" validateRule="NUMBER"/>
					<column columnId="BEDDESC" name="床型" width="120" editable="true" align="center"/>
					<column columnId="BROADBAND" name="宽带" width="120" editable="true" align="center"/>
					<column columnId="ROOMDESC" name="客房描述" width="120" editable="true" align="center"/>
					<column columnId="ROOMPOSITION" name="客房朝向" width="120" editable="true" align="center"/>
					<column columnId="STATUS" name="状态" width="120" editable="true" align="center"/>
					<column columnId="RECORDTIME" name="记录时间" width="120" editable="true" align="center" editType="date"/>
					<column columnId="REMARK" name="备注" width="120" editable="true" align="center"/>
 */
/*
 * 房型图片新增
 */
function addRoomTypePicture(rowid){
	var rowData= getRowDataById(rowid);
	var roomType = rowData["ROOMTYPE"];
	var branchId = rowData["BRANCHID"];
	var theme = rowData["THEME"];
	JDialog.open("房型图片新增", base_path + "/addRoomTypePictureTwo.do?roomType="+roomType+"&theme="+theme+"&branchId="+branchId+"&scence=0",1000,680);
}

/*
 * 房型自定义添加
 */
function editRoomType(rowid){
	var rowData= getRowDataById(rowid);
	var roomType = rowData["ROOMTYPE"];
	var roomName = rowData["ROOMNAME"];
	var theme = rowData["THEME"];
	var roomBed = rowData["ROOMBED"];
	var bedDesc = rowData["BEDDESC"];
	var broadband = rowData["BROADBAND"];
	var roomDesc = rowData["ROOMDESC"];
	var roomPosition = rowData["ROOMPOSITION"];
	var status = rowData["STATUS"];
	var recordTime = rowData["RECORDTIME"];
	var remark = rowData["REMARK"];
	var roomLabel = rowData["ROOMLABEL"];
	var tip = rowData["TIPS"];
	var branchId = rowData["BRANCHID"];
	if((!roomType)&&(!roomName)){
		JDialog.open("添加记录", base_path + "/addRoomType.do" ,650,512);
	}else{
		JDialog.open("编辑记录", base_path + "/editRoomType.do?roomType="+roomType+
															"&roomName="+roomName+
															"&theme="+theme+
															"&roomBed="+roomBed+
															"&bedDesc="+bedDesc+
															"&broadband="+broadband+
															"&roomDesc="+roomDesc+
															"&roomPosition="+roomPosition+
															"&status="+status+
															"&recordTime="+recordTime+
															"&roomLabel="+roomLabel+
															"&tip="+tip+
															"&remark="+remark+
															"&branchId="+branchId
															,650,512);
	}	
}
//房租价自定义逻辑删除
function delRoomPrice(){	
	var theme = concatColumns("THEME");
	var branchid = concatColumns("BRANCHID");
	var roomtype = concatColumns("ROOMTYPE");
	var rpid = concatColumns("RPID");
	$.ajax({
		url: base_path + "/delRoomPrice.do",
		type: "post",
		dataType: "json",
		data: { theme: theme,
		        branchid: branchid,
		        roomtype: roomtype,
		        rpid: rpid
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//金柜自定义逻辑删除
function delCash(){	
	var branchId = concatColumns("BRANCHID");
	var cashBox = concatColumns("CASHBOX");
	$.ajax({
		url: base_path + "/delCash.do",
		type: "post",
		dataType: "json",
		data: { branchId: branchId,
		        cashBox: cashBox
		},
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

/*``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````*/

//设备
function openDoor(rowid){
	showMsg("功能未开放！");
	/*var rowData= getRowDataById(rowid);
	var lockno = rowData["SERIALNO"];
	var cardno = '';
	var LockType = 1;
	$.ajax({
		url: base_path + "/gateLock.do",
		dataType: "json",
		type: "post",
		data:{
			'lockno' : lockno,
			'LockType' : LockType,
			'cardno' : cardno
		},
		success: function(json) {
			if(json.result =="1"){
				showMsg(json.message);
			}else {
				showMsg(json.message);
			}
			   
		},
	})*/
}

function closeDoor(rowid){
	showMsg("功能未开放！");
}

function inAndDecreaseUser(rowid){
	var isCheck = "0";
	var rowData= getRowDataById(rowid);
	var lockno = rowData["SERIALNO"];
	var dataid = rowData["DATAID"];
	var branchId = dataid.substring(0,6);
	var roomId = rowData["ROOMID"];
	var gatewayCode = rowData["GATEWAY_CODE"];
	var mainUser = rowData["CHECKUSER"];
	var branchIdNum = rowData["BRANCHIDNUM"];
	if(mainUser != null && mainUser.length > 0){
		isCheck = "1";
	}
	JDialog.open("添减用户", base_path + "/addAndDeleteUser.do?gatewayCode="+gatewayCode+"&branchId="+branchId+"&roomId="+roomId+"&lockno="+lockno+"&isCheck="+isCheck+"&branchIdNum="+branchIdNum,1100,589);
}

//新增门锁要绑定网关
function boundGateway(rowid){
	var rowData= getRowDataById(rowid);
	var lockno = rowData["SERIALNO"];
	var gatewayNo = rowData["GATEWAYNO"];
	var LockType = 0;
	var SUPERKIND = rowData["SUPERKIND"];
	if(SUPERKIND == "门锁"){
		$.ajax({
			url: base_path + "/perPairing.do",
			dataType: "json",
			type: "post",
			data:{
				'lockno' : lockno,
				'type' : LockType,
				'gatewayNo' : gatewayNo
			},
			success: function(json) {
				if(json.result =="1"){
					showMsg(json.message);
				}else {
					showMsg(json.message);
				}
				   
			},
		})
	} else{
		showMsg("非门锁设备无需绑定网关！");
	}
	

	
}



function openElectric(rowid){
	var rowData = getRowDataById(rowid);	
	var communicationstatus = rowData["STATUS"];
	var isOpen = rowData["ISOPEN"];
	var branchId = rowData["BRANCHIDNUM"];
	//alert(branchId);
	var roomId = rowData["ROOMID"];
	var serialNo = rowData["SERIALNO"];
	if(communicationstatus == "0"){
		showMsg("当前设备已删除！");
	} else if (communicationstatus == "2"){
		showMsg("当前设备通信异常，请稍后操作！");
	} else if (communicationstatus == "3"){
		showMsg("当前设备正维修，不可操作！");
	} else {
		operateElectric(branchId, roomId, "1", serialNo);
	}
}

function closeElectric(rowid){
	var rowData = getRowDataById(rowid);	
	var communicationstatus = rowData["STATUS"];
	var isOpen = rowData["ISOPEN"];
	var branchId = rowData["BRANCHIDNUM"];
	var roomId = rowData["ROOMID"];
	var serialNo = rowData["SERIALNO"];
	if(communicationstatus == "0"){
		showMsg("当前设备已删除！");
	} else if (communicationstatus == "2"){
		showMsg("当前设备通信异常，请稍后操作！");
	} else if (communicationstatus == "3"){
		showMsg("当前设备正维修，不可操作！");
	} else {
		operateElectric(branchId, roomId, "3", serialNo);
	}
}

function operateElectric(branchId, roomId, status, serialNo){
	$.ajax({
		url : base_path + "/putOnLight.do",
		type: "post",
		dataType: "json",
		data: { branchId : branchId,
				roomId : roomId,
				status : status,
				serialNo : serialNo
		},
		success: function(json) {
			if (json.result != -1) {
				if(json.message){
			     showMsg(json.message);
			     window.setTimeout("location.reload(true)", 1000);
				}	
			} else {
				showMsg("操作失败！");
				window.setTimeout("location.reload(true)", 1000);
			}
		},
		error: function(json) {
			 showMsg("ajax失败！");
			 window.setTimeout("location.reload(true)", 1000);
		}
	});
}

function repairDetial(rowid){
	var rowData= getRowDataById(rowid);
	var serialNo = rowData["SERIALNO"];
	JDialog.open("维修记录", base_path + "/turnToEquipDetailPage.do?serialNo="+serialNo,1000,520);
}

function repairDetialForHouse(rowid){
	var rowData= getRowDataById(rowid);
	var serialNo = rowData["SERIALNO"];
	JDialog.open("维修记录", base_path + "/turnToEquipDetailPage.do?serialNo="+serialNo,1000,520);
}

function addEquipRepairDetail(){
	JDialog.open("维修新增", base_path + "/equipmentRepairDetailAdd.do",510,290);
}

function queryWarningLog(rowid){
	var rowData= getRowDataById(rowid);
	var status = rowData["STATUS"];
	var serialNo = rowData["SERIALNO"];
	if(status == "2"){
		showMsg("已阅读，不可操作！");
	} else if(status == "0"){
		showMsg("已删除，不可操作！");
	} else{
		$.ajax({
			url : base_path + "/changeWarningStatus.do",
			type: "post",
			dataType: "json",
			data: {
				serialNo : serialNo
		},
			success: function(json) {
				if (json.result == 1) {
				     showMsg("操作成功！");
				     window.setTimeout("location.reload(true)", 1000);	
				} else {
					showMsg("操作失败！");
					window.setTimeout("location.reload(true)", 1000);
				}
			},
			error: function(json) {
				 showMsg("ajax失败！");
				 window.setTimeout("location.reload(true)", 1000);
			}
		});
	}
}

function delStaff(rowid) {
	var staffId = concatColumns("STAFFID");
	
	$.ajax({
		url: base_path + "/delStaff.do",
		type: "post",
		dataType: "json",
		data: { staffId: staffId },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				$.ajax({
					url: base_path + "/deleteStaff.do",
					type: "post",
					dataType: "json",
					data: { staffId: staffId },
					success: function(json) {
						if (result == 1) {
							showMsg("操作成功");
							refreshGrid();
						}
					},
					error: function(json) {
						showMsg("操作失败");
					}
				});
			} else if (result == 2) {
				window.JDialog.open("删除员工", base_path + "/page/ipmspage/order/secondpage.jsp?staffId=" + staffId , 200, 100);
			} else if (result == 3) {
				showMsg("自营或加盟员工不能删除");
				refreshGrid();
			}
		},
		error: function(json) {
			showMsg("操作失败");
		}
	});
}

function delCashBox(){
	var dataId = concatColumns("DATAID");
	$.ajax({
		url: base_path + "/delCashBox.do",
		type: "post",
		dataType: "json",
		data: { dataId: dataId },
		success: function(json) {
			showMsg(json.message);
			refreshGrid();
		},
		error: function(json) {
			showMsg("操作失败");
		}
	});
}


//城市管理自定义逻辑删除
function delCity(rowid){
	var cityCode = concatColumns("ADMINICODE");
	var rank = concatColumns("CITYRANK");
	console.log(rank)
	if(rank.indexOf("市,") != -1){
		showMsg("城市不允许手动删除");
		return;
	}
	$.ajax({
		url: base_path + "/delCity.do",
		type: "post",
		dataType: "json",
		data: { cityCode: cityCode },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功");
				 window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

function editCityNew(rowid){
	var rowData = getRowDataById(rowid);
	var adminiCode=rowData["ADMINICODE"];
	if(!adminiCode){
		JDialog.open("城市新增", base_path + "/addCity.do?",560,400)
	}else{
		JDialog.open("城市编辑", base_path + "/editCity.do?adminiCode="+adminiCode,560,400)
	}
}

//自定义新增城市图片
function addCityPic(rowid) {
	var rowData = getRowDataById(rowid);
	var adminiCode=rowData["ADMINICODE"];
	var CITYRANK=rowData["CITYRANK"];
	var ADMININAME=rowData["ADMININAME"];
	if(CITYRANK == '市' ){
		JDialog.open("城市图片新增", base_path + "/addCityPic.do?adminiCode="+adminiCode+"&citytype=city&ADMININAME="+ADMININAME,600,400);

	}else if(CITYRANK == '景点' ){
		JDialog.open("城市图片新增", base_path + "/addScenicSpotsPic.do?adminiCode="+adminiCode+"&citytype=jingdian&ADMININAME="+ADMININAME,600,400);

	}else{
		showMsg("仅市、景点可增加图片！");
	}
}

//自定义新增商品图片
function addGoodsPic(rowid) {
	var rowData = getRowDataById(rowid);
	var adminiCode=rowData["GOODSID"];
		JDialog.open("商品图片新增", base_path + "/addGoodsPic.do?adminiCode="+adminiCode,600,400);
}

//自定义新增商品分类图标
function addGoodsPicMark(rowid){
	var rowData = getRowDataById(rowid);
	var CATEGORYID = rowData["CATEGORYID"];
	JDialog.open("商品分类图标新增",base_path + "/addGoodsPicMark.do?CATEGORYID="+CATEGORYID,600,400);
}

function dataSynchro(){
	$.ajax({
		url : base_path + "/checkSystemExistIsCloud.do",
		dataType : "json",
		type : "post",
		async:false,
		success : function(json) {
			if(json.result =="1" && json.data == true ){
				exist = true;
			}else if (json.result =="1" && json.data == false){
				exist = false;
			}
		},
		error: function(json) {
			showMsg("查询失败");
			return false;
		}
	});

	if(exist){
		$.ajax({
			url: base_path + "/hydropowerDataAbutment.do",
			type: "post",
			dataType: "json",
			success: function(json) { 
				var result = json.result;
				showMsg(json.message);
				window.setTimeout("location.reload(true)", 1800);
			},
			error: function(json) {
				showMsg("操作失败！");
				 window.setTimeout("location.reload(true)", 1800);
			}
		});
	} else {
		showMsg("设备基础资料的同步只在云端处理！");
	}

}

function doorDataSynchro(){
	/*$.ajax({
		url : base_path + "/checkSystemExistIsCloud.do",
		dataType : "json",
		type : "post",
		async:false,
		success : function(json) {
			if(json.result =="1" && json.data == true ){
				exist = true;
			}else if (json.result =="1" && json.data == false){
				exist = false;
			}
		},
		error: function(json) {
			showMsg("查询失败");
			return false;
		}
	});

	if(exist){*/
		$.ajax({
			url: base_path + "/doorDataAbutment.do",
			type: "post",
			dataType: "json",
			success: function(json) { 
				var result = json.result;
				showMsg(json.message);
				window.setTimeout("location.reload(true)", 1800);
			},
			error: function(json) {
				showMsg("操作失败！");
				 window.setTimeout("location.reload(true)", 1800);
			}
		});
	/*} else {
		showMsg("设备基础资料的同步只在云端处理！");
	}*/

}
/*````````````````````````````````````````````````````````品宣管理``````````````````````````````````````````````````````````````````````*/
//添加及编辑品宣页面
function editorAddProbrand(rowid){
	var rowData = getRowDataById(rowid);
	var contentId = rowData["CONTENTID"];
	if(rowid){
		JDialog.open("品宣编辑",base_path + "/editProbrand.do?contentId="+contentId, 780,650);
	}
	else{
		JDialog.open("品宣新增",base_path + "/addProbrand.do", 800, 650);
	}
}

//删除活动功能
function delProbrand(){
	var contentId = concatColumns("CONTENTID");
		$.ajax({
	         url: base_path +  "/delProbrand.do",
			 type: "post",
			 dataType: "json",
			 data : { contentId: contentId },
			success: function(json) {
				var result = json.result;
				 if (result == 1) {
					 showMsg("删除成功！");
					 window.setTimeout("window.location.reload();", 500);
				}else if(result == 0){
					showMsg("删除失败！");
				}
				 
			 }
		});
}
/*````````````````````````````````````````````````````````价格规则管理``````````````````````````````````````````````````````````````````````*/
//删除活动功能
function delPriceRules(){
	var rulesId = concatColumns("RULESID");
		$.ajax({
	         url: base_path +  "/delPriceRules.do",
			 type: "post",
			 dataType: "json",
			 data : { rulesId: rulesId },
			success: function(json) {
				var result = json.result;
				 if (result == 1) {
					 showMsg("删除成功！");
					 window.setTimeout("window.location.reload();", 500);
				}else if(result == 0){
					showMsg("删除失败！");
				}
				 
			 }
		});
}

// 编辑及添加房价规则
function editorAddPriceRule(rowid){
	var rowData = getRowDataById(rowid);
	var rulesId=rowData["RULESID"];
	if(!rulesId){
		JDialog.open("房价规则新增", base_path + "/addPriceRule.do?",800,380)
	}else{
		showMsg("房价规则暂不能修改！");
	}
}

function delPrice(rowid){
	var rowData = getRowDataById(rowid);
	var id = rowData.ID;
	if(rowData.PRIORITY == '基准价'){
		showMsg("只能删除调整价!");
		return false;
	}
	showMsg("确认删除,不可更改?", 'dodelPrice(' + id + ')');

}

function dodelPrice(id){
	$.ajax({
		url: base_path +  "/delPrice.do",
		type: "post",
		dataType: "json",
		data : { id : id },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg("删除成功！");
				window.setTimeout("window.location.reload();", 500);
			}else if(result == 0){
				showMsg("删除失败！");
			}
		}
	});
}
/*````````````````````````````````````````````````````````舆情管理``````````````````````````````````````````````````````````````````````*/
//添加及编辑品宣舆情页面
function commentAddOrEdit(rowid){
	var rowData = getRowDataById(rowid);
	var commentId = rowData["COMMENT_ID"];
	if(rowid){
		JDialog.open("舆情编辑",base_path + "/editCommentCrm.do?commentId="+commentId, 800,550);
	}
	else{
		JDialog.open("舆情新增",base_path + "/addCommentCrm.do", 800, 550);
	}
}

/*````````````````````````````````````````````````````````舆情管理``````````````````````````````````````````````````````````````````````*/
//添加及编辑订单舆情页面
function commentAddOrEditOrder(rowid){
	var rowData = getRowDataById(rowid);
	var commentId = rowData["COMMENT_ID"];
	if(rowid){
		JDialog.open("舆情编辑",base_path + "/editOrderCommentCrm.do?commentId="+commentId, 800,480);
	}
	else{
		JDialog.open("舆情新增",base_path + "/addOrderCommentCrm.do", 800, 480);
	}
}

//删除订单评论
function delOrderComment(){
	var commitId = concatColumns("COMMENT_ID");
	$.ajax({
		url: base_path + "/delOrderComment.do",
		type: "post",
		dataType: "json",
		data: {
			commitId: commitId
		},
		success: function(json) {
			if (json.result == 1) {
				showMsg("删除成功");
				window.location.reload();
				 window.setTimeout("location.reload(true)", 1800);
			} else if(json.result == 0) {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}

//删除品宣评论
function delProbrandComment(){
	var commitId = concatColumns("COMMENT_ID");
	$.ajax({
		url: base_path + "/delProbrandComment.do",
		type: "post",
		dataType: "json",
		data: {
			commitId: commitId
		},
		success: function(json) {
			if (json.result == 1) {
				showMsg("删除成功");
				window.location.reload();
				 window.setTimeout("location.reload(true)", 1800);
			} else if(json.result == 0) {
				showMsg("删除失败");
				 window.setTimeout("location.reload(true)", 1800);
			}
		},
		error: function(json) {
			showMsg("操作失败！");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
}


/*````````````````````````````````````````````````````````价格规则管理``````````````````````````````````````````````````````````````````````*/
//编辑及添加民宿管家账号
function editorHouseAccount(rowid){
	var rowData = getRowDataById(rowid);
	var accountName=rowData["HOUSEACCOUNTNAME"];
	if(rowid){
		JDialog.open("民宿账号编辑", base_path + "/editHouseAccount.do?accountName="+accountName,600,430);
	}else{
		JDialog.open("民宿账号新增", base_path + "/addHouseAccount.do",600,430);
	}
	
}
//删除
function delHouseAccount(rowid) {
	var accountName = concatColumns("HOUSEACCOUNTNAME");
	$.ajax({
		url: base_path + "/delHouseAccount.do",
		type: "post",
		dataType: "json",
		data: { accountName: accountName },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				refreshGrid();
			}else{
				showMsg(json.message);
			} 
		},
		error: function(json) {
			showMsg("操作失败");
		}
	});
}
//发送短信
function sendmessage(rowid){
	var rowData = getRowDataById(rowid);
	var promotionId=rowData["PROMOTIONID"];
	$.ajax({
		url: base_path + "/sendmessage.do",
		type: "post",
		dataType: "json",
		data: { promotionId: promotionId },
		success: function(json) {
			var result = json.result;
			if (result == 1) {
				showMsg(json.message);
				refreshGrid();
			}else{
				showMsg(json.message);
			} 
		},
		error: function(json) {
			showMsg("操作失败");
		}
	});
}


function copyhouse(){
	
	var houseId=concatColumns("BRANCHID");
	if(houseId == ''  ){
		showMsg("未选择民宿！");
		return false;
	}
	$.ajax({
		url: base_path + "/copyHouse.do",
		type: "post",
		dataType: "json",
		data: { houseId: houseId },
		success: function(json) {
			if(json.result == 1){
				showMsg("操作成功");
				 window.setTimeout("location.reload(true)", 1800);
			}
			
		},
		error: function(json) {
			showMsg("操作失败");
			 window.setTimeout("location.reload(true)", 1800);
		}
	});
	
}
//门锁多锁添加人
function doorAddUser() {
	var lockno = concatColumns("SERIALNO");
	var gatewayCode = concatColumns("GATEWAY_CODE");	
	var branchIdName = concatColumns("BRANCHID");
	var branchIdNum = concatColumns("BRANCHIDNUM");
	var roomId = concatColumns("ROOMID");
	
	if($.isEmptyObject(lockno) || $.isEmptyObject(gatewayCode) || $.isEmptyObject(branchIdName) || $.isEmptyObject(branchIdNum) || $.isEmptyObject(roomId)){
		showMsg("请先选择要授权的门锁！");
	} else {
		JDialog.open("添减用户", base_path + "/doorAddUser.do?gatewayCode="+gatewayCode+"&roomId="+roomId+"&lockno="+lockno+"&branchIdNum="+branchIdNum+"&branchIdName="+branchIdName,1100,589);
	}
}

/**
 * 入住须知新增标签
 */
function addTips(rowid){
	var rowData = getRowDataById(rowid);
	var adminiCode=rowData["ADMINICODE"];
	if(!adminiCode){
		JDialog.open("新增入住须知", base_path + "/addTipsCRM.do?",560,400)
	}
}
