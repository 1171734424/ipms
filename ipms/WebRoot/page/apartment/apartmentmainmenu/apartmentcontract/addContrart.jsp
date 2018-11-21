<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/campaigns.css" media="all" />
		<title>合同新增</title>
	</head>
	<style>
	
	.gray{
	    background: #f0f0f0;
	}
	td.must{
	color:red;
	}
	
	.addContrart .gdsbutton_clean{
		height: 32px;
	    line-height: 26px;
	    text-align: center;
	    width: 70px;
	    border:none;
	    background-color:#5A5D9C;
	    color: #fff;
	    cursor: pointer;
}
.addContrart input.registermember{
	margin-top: 0;
	margin-left: 105px;
	background-color: #FC8A15;
}

.basicInfo .contrart_table{
    border: none;
    width: 87%;
    margin-left: 61px;
    margin-top: 16px;
}

</style>
<body>
<div class="addContrart">
	<form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data">
		<input type="hidden" id="contrartid" name="contrartid" value="${contrartId}" />
			<table class="contrart_table">
			<tr>
				<td style="width:8%;">查会员信息&nbsp</td>
				<td>
					<input id="memberselect" name="memberselect" type="text" value="" placeholder="手机号" onfocus="ridof()" onblur="ridon(this)" onKeyUp="this.value = this.value.replace(/\D/g, '')"; onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			        <input type="button" name="" id="" class="gdsbutton_clean" value="查询" onclick="selectmember()" style="margin-left: 39px;"/>
				</td>
				<td>
				</td>
				<td>
				 <input type="button" name="" id="" class="gdsbutton_clean registermember" value="注册会员" onclick="addmember()"/>
				</td>
			</tr>
			<tr>
				<td style="width:8%;">订单号</td>
			    <td><input id="orderid" name="orderid" value="" readonly/></td>
				<td style="width:8%;" >合同</td>
				<td>
					<input type="file" id ="contrartFile" name="contrartFile" />
				</td>
			</tr>
			<tr>
				<td style="width:8%;" class="must">合同人</td>
				<td>
					<input id="member_id" name="member_id" value="${memberName }" class="gray" readonly/>
					<input type="hidden" id="member_id_CUSTOM_VALUE" name="member_id_CUSTOM_VALUE" value="${contrart.memberId }" />
				    <input type="hidden" id="member_rank" name="member_rank" value="" />
				</td>
				<td style="width:8%;">手机号</td>
				<td>
					<input id="mobile" name="mobile" value="${contrart.mobile }" class="gray" readonly/>
					
				</td>
				<td style="width:8%;" class="must">租赁时间</td> 
				<td >
					<select name="time" id="time" class="ui-select" onchange="timechange()" > 
					 	<option value="">--请选择--</option> 
					 	<option value="1">一个月</option> 
					 	<option value="3">三个月</option> 
				   		<option value="6">六个月</option> 
				     	<option value="12">一年</option> 
				      	<option value="24">两年</option> 
				       	<option value="36">三年</option> 
					</select> 
				</td> 
			</tr>
			<tr>
				<td style="width:8%;" class="must">租赁方式</td>
				<td >
					<select name="typeofpayment" id="typeofpayment" class="ui-select"  onchange="Money()">
						<option value="">--请选择--</option>
						<option value="1">月付</option>
						<option value="3">季付</option>
						<option value="6">半年付</option>
						<option value="12">年付</option>
					</select>
					<input type="hidden" id="usingType_CUSTOM_VALUE" name="usingType_CUSTOM_VALUE" value="" />
				</td>
				<td style="width:8%;" class="must">开始时间</td>
				<td>
					<input id="startTimeTemp" name="startTimeTemp" class="date" value="${starttime }" readonly />
				</td>
				<td style="width:8%;" >到期时间</td>
				<td>
					<input id="endTimeTemp" name="endTimeTemp"  value="${endtime }"  class="gray" readonly/>
				</td>
			</tr>
			<tr>
				<td style="width:8%;" class="must">房间号</td>
				<td>
					<input id="room_id" name="room_id" value="${contrart.roomId }" readonly/>
					<input type="hidden" id="room_type" name="room_type" value="${roomType }" />
				</td>
				<td style="width:8%;">单价</td>
				<td>
					<input  id="unitprice" name="unitprice" value="${contrart.unitPrice }" class="gray"  maxlength="6" onkeyup="changeUnitprice()"  readonly/>
					<input type="hidden" id ="oldprice" name="oldprice" value="${contrart.unitPrice }">
					<span>元</span>
				</td>
				<td style="width:8%;">已付定金</td>
				<td>
					<input id="advanceCash" name="advanceCash" value="" maxlength="6" class="gray" style="margin-left:27px;" readonly/>
					<span>元</span>
				</td>
			</tr>
			 <tr>
				
				<td style="width:8%;">应付房租</td>
				<td>
					<input  id="totalprice" name="totalprice" value="" class="gray" readonly/>
					<span>元</span>
				</td>
				<td id= "yajing" style="width:8%;" class="must" >应付押金</td>
					<td>
						<input  id="cash" name="cash" value="${contrart.cash}"  onkeyup="changeCash()" class="gray" readonly="readonly" maxlength="6"/>
						<span>元</span>
					</td>
				<td style="width:8%;" id="zonge">线下支付总额</td>
				<td>
					<input id="actualCash" name="actualCash" value="" class="gray" style="margin-left:27px "readonly/>
					<span>元</span>
				</td>
					
				</tr>
				<tr>
 					<td style="width:8%;display:none">房租到期</td>
					<td style="display:none">
						<input id="contrart_end_time" name="contrart_end_time" value="${contrartEndTime }" class="gray" readonly="readonly"/>
					</td> 
					
 					<td style="width:8%;display:none" class="must" >自动退租</td>
 					<td>
						<select name="autorefund" id="autorefund" class="ui-select" style="display:none" >
							<option value="1" selected>否</option>
							<option value="0">是</option>
						</select>
					</td>
					</tr>
			</table>
		</form>
		<div class="button_div fr" style="width:100%;margin-top: -13px;">
			<div class="button div_button save" id="adjustprice" style="display:inline-block;float:right;margin: 50px 166px;" onclick="adjustprice();">
				<span>单价调整</span>
			</div>
			<div class="button div_button save" style="display:inline-block;float:right;margin: 50px -349px;" onclick="submitform();">
				<span>保存</span>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(function (){
           laydate({
        	elem: '#startTimeTemp',
        	min:laydate.now(0),
        	choose :change
   		});
		});
	var base_path ="<%=request.getContextPath()%>";
		
		$(function() {
			$("#orderid").bind("click",function(){
				$("#typeofpayment,#time,#room_id,#cash,#startTimeTemp,#unitprice").attr("disabled","disabled").addClass("gray");
				$("#yajing").text('已付押金');
			 	JDialog.open("订单号", base_path + "/contrartaptorder.do",570,350);
			});
			
			$("#room_id").bind("click",function(){
			if($("#member_id").val() == ""){
			  showMsg("请选择合同人!");
			  return false;
			}
			var memberrank = $("#member_rank").val();
			var startTime = $("#startTimeTemp").val();
			 	JDialog.open("房间号 ", base_path + "/contrartselectroomid.do?memberrank="+memberrank+"&startTime="+startTime,450,350);
			});
			
			//初始化开始日期
			var now = new Date(); 
		    var starttime = dealLocalDate(now);
		    $("#startTimeTemp").val(starttime);
		});
		
		function hasorder(){
			$("#memberselect").attr('disabled', true);
			$("#zonge").text("支付总额");
		}
		
		function ridof(){
			$("#orderid").attr('disabled', true);
			$("#orderid").addClass('gray');
		}
		
		function ridon(e){
			if($(e).val()){
				return false;
			}
			$("#orderid").attr('disabled', false);
			$("#orderid").removeClass('gray');
		}
		

		function changeUnitprice(){
			 var regdouble=/^\d+(\.\d{0,2})?$/;
			var roomid_check = $("#room_id").val();
		    var unitpricecheck =  $("#unitprice").val();
		    
			if(roomid_check == '' ){
				showMsg("请先选择房间号！");
	        	$("#unitprice").val("");
	        	return false;
			}
			 if(isNaN(unitpricecheck)){
		        	showMsg("请输入数字！");
		        	$("#unitprice").val("");
		        	$("#cash").val("");
		        	return false;
		        }
			 
			 if(!regdouble.test(unitpricecheck)){
				 showMsg("单价最多保留两位小数！");
				 $("#unitprice").val("");
		        	$("#cash").val("");
		        	return false;
			 }
			$("#typeofpayment").val("");
			$("#totalprice").val("");
			$("#actualCash").val("");
			
			$("#cash").val(unitpricecheck);
			
		}
		function changeCash(){
			 
	        var totalprice = Number($("#totalprice").val()).toFixed(2);//已付定金
	        var yajing = Number($("#cash").val()).toFixed(2);//已付定金
	        if(isNaN(yajing)){
	        	showMsg("请输入数字！");
	        	$("#cash").val("");
	        	return false;
	        }
	        $("#actualCash").val((Number(totalprice) + Number(yajing)).toFixed(2));//线下支付总额
	       
		}
		function timechange(){
			if($("#startTimeTemp").val() == ""){
			 showMsg("开始时间不可为空!");
	            return false;
			
			}
			var timemonth = Number($("#time").val());//月份数量
	        var starttime_c1 =  $("#startTimeTemp").val();//开始时间
		    var startdate_c1 = new Date(starttime_c1);//开始时间
			startdate_c1.setMonth(startdate_c1.getMonth() + timemonth);
		    startdate_c1.setDate(startdate_c1.getDate() - 1);
			var contrart_end_time = dealLocalDate(startdate_c1);
			$("#endTimeTemp").val(contrart_end_time);
			//清空数据
			Money();
			
		}
		function findunitprice(){
		var roomid = $("#room_id").val();
		var memberrank = $("#member_rank").val();
		$.ajax({
  	  			url: base_path + "/findunitprice.do",
  	  			dataType: "json",
  	  			type: "post",
  	  			data:{
  	  				'roomid' : roomid,
  	  				'memberrank' : memberrank
  	  			},
  	  			success: function(json) {
  			        $("#unitprice").val(json);
  	  				   
  	  			},
  	  			error:function(json){
  					
  	  			}
  	  		});

		}
		
		function submitform(){
			 $("#typeofpayment,#time,#room_id,#cash,#startTimeTemp,#unitprice").removeAttr("disabled").removeClass("gray");
			var arr=new Array(
			     new Array('autorefund','请选择是否自动退租!'),
			     new Array('member_id','合同人不可为空!'),
			     new Array('startTimeTemp','开始时间不可为空!'), 
			     new Array('endTimeTemp','到期时间不可为空!'), 
			     new Array('room_id','房间号不可为空!'),
			     new Array('typeofpayment','请选择租赁方式!'), 
			     new Array('unitprice','单价不可为空!'), 
			     new Array('contrart_end_time','房租到期时间不可为空!'),
			     new Array('cash','押金不可为空!')
			 );
			var phonejudge = /^1[34578]\d{9}$/;
			var regdouble=/^\d+(\.\d{1,2})?$/;
			var moneyReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //校验是金额
			if(!checkFormIsNull(arr) ){
		     return false;
		 }
		 
		 //校验上传文件格式只能为docx
		
	    var zswb = $("#contrartFile").val();
	    if(zswb.length>1 && zswb != ''){
	        var lodt = zswb.lastIndexOf(".");
	        var type = zswb.substring(lodt+1);
	        if( type != "docx"){
	           showMsg("合同文件格式请设置为.docx!");
	            return false;
	        }
	    }

		  if(Number($("#unitprice").val()).toFixed(2) == '0.00'){
			  showMsg("单价不能为零!");
			    return false;	
		  }
		   if(isNaN($("#cash").val())){
				showMsg("押金请填写数字!");
			    return false;	
		     }else if(moneyReg.test($("#cash").val()) == false){
		    		showMsg("押金格式错误!");
		    		$("#cash").val("");
					$("#cash").focus();
			    return false;
		   }
		   var roomId = $("#room_id").val();
		   var startTime = $("#startTimeTemp").val();
			$.ajax({
		         url: base_path + "/findbespeakapart.do",
				 type: "post",
				 data : {
					 roomId : roomId ,
					 startTime : startTime
				 },
				 success: function(json) {
				 	if(json.result == 1){
				 		if(json.data == 1){
				 			showMsg("此房已有人预约看房，确认后请至公寓预约修改此房间号！", 'submitformsure("confirm")', 'submitformsure("cancel")');
				 		}else if(json.data == 0){
				 			submitformsure("confirm");
				 		}
				 	}
				 },
				 error: function(json) {console.log(json)}
			});
			
		}
		
		function submitformsure(e){
			if(e=='cancel'){
				return false;
			}
			var formData = new FormData($("#basicInfo")[0]); 
			//formData.append("oldprice",$("#oldprice").val());
			$.ajax({
				url : base_path + "/saveContrart.do",
				data: formData,
				 contentType: false,
		         processData: false,
				dataType : "json",
				type : "post",
				success : function(json) {
				if(json.result == 1){
				    showMsg(json.message);
				}else if(json.result == 2){
					showMsg(json.message);
					window.setTimeout("window.parent.document.getElementById('menu_910').click();window.parent.JDialog.close();", 2000);
					}
				},
				error : function(json) {
					showMsg("设置失败!");
				}
			});
		}
		
	//批量验证表单非空
		function checkFormIsNull(arr){
			 for(var i=0;i<arr.length;i++){
				 if($("#"+arr[i][0]).val()==''){
					 showMsg(arr[i][1]);
					 return false;
				 }
			 }
			 return true; 
		};	
		
		function Money(){
  		var paymonth = Number($("#typeofpayment").val());
  		var time = Number($("#time").val());
  		if(paymonth > time){
		   showMsg("租赁方式所选时间不得超过租赁期限!");
		   $("#typeofpayment").val("");
		   $("#totalprice").val("");
		   $("#actualCash").val("");
		   $("#contrart_end_time").val("");
		   return false;
		}
		var money = (paymonth * (Number($("#unitprice").val()).toFixed(2))).toFixed(2);//应付房租
		$("#totalprice").val(money);
        var advanceCash = Number($("#advanceCash").val()).toFixed(2);//已付定金
        var yajing = Number($("#cash").val()).toFixed(2);//已付定金
        var actualCash_value = Number((money - advanceCash)).toFixed(2);
        $("#actualCash").val((Number(actualCash_value) + Number(yajing)).toFixed(2));//线下支付总额
        var starttime_c1 =  $("#startTimeTemp").val();
	    var startdate_c1 = new Date(starttime_c1);
	    startdate_c1.setDate(startdate_c1.getDate()-1);
		startdate_c1.setMonth(startdate_c1.getMonth() + paymonth);
		var contrart_end_time = dealLocalDate(startdate_c1);
		$("#contrart_end_time").val(contrart_end_time);
  	}	
  		

  	function  change(){
		  	$("#contrart_end_time").val("");
		  //	$("#paymonth").val("");
		  	$("#totalprice").val("");
		  	$("#typeofpayment").val("");
		  	$("#time").val("");
		  	$("#endTimeTemp").val("");
		  		//初始化开始日期
  	}
  	function dealLocalDate(myT){
	var year = myT.getFullYear();
	var month = myT.getMonth() + 1;
	var strDate = myT.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + "/" + month + "/" + strDate;
    return currentdate;
}

function selectmember(){
	var sm = $("#memberselect").prop("disabled");
	if(sm)
		return false;

	var idcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;//身份证校验
	var mobReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; //手机校验
	var Mnumber = $("#memberselect").val();
	if(Mnumber == ""){
	       showMsg("请填写查询条件!");
			    return false;	
	}
	if(Mnumber.length > 11){
		if(!idcard.test(Mnumber)){
			showMsg("手机号或身份证输入错误!");
			return false;
		}
	}else{
		if(!mobReg.test(Mnumber)){
			showMsg("手机号或身份证输入错误!");
			return false;
		}
	}

$("#typeofpayment,#time,#room_id,#cash,#startTimeTemp").removeAttr("disabled").removeClass("gray");
	if (Mnumber) {
	
		$.ajax({
			url : base_path + "/memberSelectForContrart.do",
			type : "post",
			dataType : "json",
			data : {
				'Mnumber' : Mnumber
			},
			success : function(json) {
			
			if(json.result == 0){
			
			showMsg(json.message);
			}else{
			
			    $("#member_id").val(json.data[1]);
		   		$("#mobile").val(json.data[3]);
		   		$("#member_rank").val(json.data[2]);
		   		$("#member_id_CUSTOM_VALUE").val(json.data[0]);
		   		$("#room_id").val("");
		   		$("#unitprice").val("");
		   		$("#cash").val("");
		   		$("#orderid").val("");
		   		$("#typeofpayment").val("");
		   		$("#totalprice").val("");
		   		$("#contrart_end_time").val("");
		   		$("#actualCash").val("");
		   		$("#advanceCash").val("");
		   		$("#payment").val("");
		   		$("#endTimeTemp").val("");
		   		//$("#autorefund").val("");
		   		$("#contrartFile").val("");
			}
				
			},
			error : function() {
				
			} 
		});
	} else {
		showMsg("请先输入会员手机号进行查询！");
		window.setTimeout("location.reload(true)", 1800);
	}
	

}

function adjustprice(){
	var me = $("#orderid").prop("disabled");
	if(!me){
		showMsg("当前不可操作!");
		return false;
	}
	JDialog.open("单价调整", base_path + "/adjustcontractprice.do?price=" + $("#unitprice").val(), 460, 120);
}



	/* 会员注册 */	
function addmember() {
	JDialog.open("会员注册", base_path + "/apartmentMemberAdd.do", 850, 410);
}
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}
	</script>
</body>
</html>