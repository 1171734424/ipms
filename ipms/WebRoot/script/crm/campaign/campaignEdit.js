function loadPageByBusinessIdNew(){
	
	laydate.render({
		  elem: '#effectiveTimetemp',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
	});
	
	laydate.render({
		  elem: '#startTimetemp',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
	});
	
	laydate.render({
		  elem: '#endTimetemp',
		  type: 'datetime',
		  format: 'yyyy/MM/dd HH:mm:ss',
		  theme: '#4A5064'
	});

	$("#usingPerson").bind("click",function(){
	 	JDialog.open("适用对象", base_path + "/selectMultiUsingPerson.do",450,350);
	});
	
	if ( IsMarketCenter == "IsMarketCenter" ){
		$("#usingType").bind("click",function(){
		 	JDialog.open("适用类型 ", base_path + "/selectMultiUsingType.do",450,350);
		 	if($("#businessId").val()=='8'){
				$("#branchId").val("");
				$("#branchId_CUSTOM_VALUE").val("");
				$("#roomId").val("");
				$("#roomId").attr('disabled',false);
   				$("#roomId").css("background-color","#fff");
		 	} else if($("#businessId").val()=='6'){
		 		$("#branchId").val("");
				$("#branchId_CUSTOM_VALUE").val("");
				$("#roomType").val("");
				$("#roomType").attr('disabled',false);
   				$("#roomType").css("background-color","#fff");
   				$("#labelhousechange").text("特惠房型");
		 	}
		});
	}
	
	
	if($("#businessId").val() == "1"){
		var innerDetail = "<table id='campaign1' class='alertfour'  name='campaign1'>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>推荐会员</label></td>"+
		"<td colspan='2'  style='width: 20%;'><input id='goodsService' name='goodsService' value='' class='text_edit'/></td>"+
		"<td class='state' colspan='2'><label>个</label></td></tr>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>送</label></td>"+
		"<td colspan='2'><input id='couponPrice' name='couponPrice' value=''  class='text_edit' type='text' readonly/></td>"+
		"<td class='state' colspan='2'><label>元优惠券</label></td></tr>"+
		"<tr><td class='state' colspan='2' style='text-align: right;width: 12%;'><label>组合名称</label></td>"+
		"<td colspan='2'><input id='couponGroupId' name='couponGroupId' value='' type='hidden' class='text_edit'/><input id='couponName' name='couponName' value='' class='text_edit'/></td>"+
		"<td class='state' colspan='2'><input id='showRule' name='showRule' type='text' style='width:300px' readonly/></td></tr>" +
		"</table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroup.do",450,350);
		});
	}else if($("#businessId").val() == "2"){
		var innerDetail = "<table id='campaign1' class='alerthree' name='campaign1'><tr><td class='state' colspan='2' style='width:6.5%;'><label>开卡即送"+
		"</label></td>"+
		"<td colspan='2' style='width: 24%;'><input id='couponPrice' class='text_search' name='couponPrice' value='' /><input id='couponGroupId' name='couponGroupId' type='hidden' value='' />"+
		"</td><td class='state'><label>元优惠券</label></td></tr><tr><td class='state'>"+
		"<label>面额</label></td><td class='state blingprice'><label>10元、</label><label>20元、</label><label>30元、</label><label>50元、</label><label>100元</label></td></tr><tr><td class='state'>"+
		"<label>张数</label></td><td class='blingz'><label><input id='tenCoupon' name='tenCoupon' value='' readonly/>"+
		"<input id='twentyCoupon' name='twentyCoupon' value='' readonly/>"+
		"<input id='thirtyCoupon' name='thirtyCoupon' value='' readonly/>"+
	    "<input id='fiftyCoupon' name='fiftyCoupon' value='' readonly/>"+
		"<input id='hundredCoupon' name='hundredCoupon' value='' readonly/>"+
		"</label></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroupAnother.do",450,350);
		});
	}else if($("#businessId").val() == "3"){
		var innerDetail = "<table id='campaign1' class='alertwo' name='campaign1'><tr><td class='state' style='width:5%;'><label>充值"+
		"</label></td><td><input id='operMoney' name='operMoney' value='' class='text_search' />元送<input id='couponPrice' name='couponPrice' class='text_search' value='' /><input id='couponGroupId' name='couponGroupId' type='hidden' value='' />元优惠券</td>"+
		"<tr><td class='state'>"+"<label>面额</label></td><td class='state'><label>10元、</label><label>20元、</label><label>30元、</label><label>50元、</label><label>100元</label></td>" +
		"</tr><tr><td class='state'>"+
		"<label>对应张数</label></td><td class='blingz'><label><input id='tenCoupon' name='tenCoupon' value='' readonly/>"+
		"<input id='twentyCoupon' name='twentyCoupon' value='' readonly/>"+
		"<input id='thirtyCoupon' name='thirtyCoupon' value='' readonly/>"+
		"<input id='fiftyCoupon' name='fiftyCoupon' value='' readonly/>"+
		"<input id='hundredCoupon' name='hundredCoupon' value='' readonly/>"+
		"</label></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroupAnother.do",450,350);
		});
	}else if($("#businessId").val() == "4"){ 
		var innerDetail = "<table id='campaign1' class='specbling' name='campaign1'><tr><td class='state' style='width:11%;'><label>注册首晚入住</label></td><td class='state' style='width:24%;'><input id='discountPrice' name='discountPrice'  class='text_search' value='' /></td><td class='state'><label>元</label></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
		var array =['2','3','4','5','6'];
		selectInfoSelectedTwo(array,"usingPerson");	
	}else if($("#businessId").val() == "5"){
		var innerDetail = "<div id='box'><table id='campaign1' class='spectwo' name='campaign1'>"+
 		"<tr id='oldLoad'><td class='state' style='width:10%;'><label>连续预约预订</label></td><td><input id='liveDay' name='liveDay' class='text_search' value='' /></td><td class='state'><label style='margin-left:1%;'>天</label></td></tr>"+
		"</table></div>";
		$("#campaignDetail").html(innerDetail);
		
		$("#liveDay").change(function(){
			$("#oldLoad").siblings().remove();
			for(var i=0;i<$("#liveDay").val();i++){
				$("#campaign1").append("<tr><td class='state'><label class='margin_top'>第"+(i+1)+"天&nbsp;&nbsp;&nbsp;&nbsp; 折扣</label></td><td><input id='dayCount"+(i+1)+"' class='text_search' name='dayCount"+(i+1)+"' value='' /></td><td class='state'><label style='margin-left:1% !important;' class='margin_top'>%</label></td></tr>");
			}
		});
	
	}else if($("#businessId").val() == "6"){
		var innerDetail ="<table id='campaign1' class='alertone' name='campaign1'><tr><td class='state'><label>归属门店</label></td><td><input id='branchId' class='text_search' name='branchId' value='' /><input id='branchId_CUSTOM_VALUE' name='branchId_CUSTOM_VALUE' type='hidden' value='' /></td><td class='state'><label id='labelhousechange'>特惠房型</label></td>"+
		 "<td><input id='roomType' name='roomType' class='text_search' value='' /><input id='roomType_CUSTOM_VALUE' name='roomType_CUSTOM_VALUE' type='hidden' value='' /></td><td class='state'>"+
		 "<label>特惠价格</label></td><td><input id='discountPrice' name='discountPrice' class='text_search'	 value='' /></td><td><label>元</label></td><tr>"+
		 "<td class='state' style='width:21%;'><label>特惠房间</label></td><td><input id='experienceCount' class='text_search' name='experienceCount' value='' /></td><td><label>间</label></td>"
			"</tr></table>"; 
		$("#campaignDetail").html(innerDetail);
		
		usingPersonselected();
		usingTypeselected();
		
		$("#branchId").bind("click",function(){
			var usingtype = $("#usingType_CUSTOM_VALUE").val();
			if(!usingtype){
				window.parent.showMsg("请先选择适用类型！");
			}else if(usingtype.length >= 2){
				window.parent.showMsg("民宿,酒店,公寓不可同选！");
			}else if((usingtype.indexOf("1") >= 0 || usingtype.indexOf("2") >= 0) && usingtype.indexOf("3") <= -1){
				//酒店公寓
				JDialog.open("归属门店", base_path + "/selectBranchId.do",450,350);
				$("#roomType").val("");
				$("#roomType").attr('disabled',false);
   				$("#roomType").css("background-color","#fff");
			}else{
				//民宿
				JDialog.open("民宿", base_path + "/selectHouse.do",450,350);
			}
		});
		
		$("#roomType").bind("click",function(){
			var usingtype = $("#usingType_CUSTOM_VALUE").val();
			if(!$("#branchId").val() && usingtype.indexOf("3") <= -1){
				window.parent.showMsg("请先选择归属店！");
			}else if((usingtype.indexOf("1") >= 0 || usingtype.indexOf("2") >= 0) && usingtype.indexOf("3") <= -1){
				//酒店公寓
				JDialog.open("特惠房型", base_path + "/selectRoomType.do?branchId="+$("#branchId_CUSTOM_VALUE").val(),450,350);
			}else{
				//民宿
				JDialog.open("民宿", base_path + "/selectHouse.do",450,350);
			}
		});
		
		
	}else if($("#businessId").val() == "7"){
		var innerDetail = "<table id='campaign1' class='alertzero' name='campaign1'>"+
		"<tr><td class='state' colspan='2' width='14%'><label>使用积分</label></td>"+
		"<td colspan='2'><input id='operScore' name='operScore' class='text_search' value='' /><label  style='margin-left:12px;font-size:12px;color:#333;'>分</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"<tr><td class='state' colspan='2'><label>送</label></td>"+
		"<td colspan='2'><input id='couponPrice'  class='text_search'  name='couponPrice' value='' type='text' readonly/><label style='margin-left:12px;font-size:12px;color:#333;'>元优惠券</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"<tr><td class='state' colspan='2'><label>组合名称</label></td>"+
		"<td colspan='2'><input id='couponGroupId'  class='text_search'  name='couponGroupId' type='hidden' value='' /><input id='couponName' class='text_search' name='couponName' value='' />" +
		"<input id='showRule' name='showRule' type='text' style='width:444px;border:none;margin-left: 10px;' readonly/></td>"+
		"</tr>" +
		"</table>";
		$("#campaignDetail").html(innerDetail);
		
		$("#couponPrice").bind("click",function(){
		 	JDialog.open("优惠券组合", base_path + "/selectcouponGroup.do",450,350);
		});
	}else if($("#businessId").val() == "8"){
		var innerDetail ="<table id='campaign1' class='specialone' name='campaign1'><tr><td class='state'><label id='belongTab' class='floatstyle'>归属门店</label></td><td><input id='branchId'"+
		" name='branchId' class='text_search' value='' /><input id='branchId_CUSTOM_VALUE' name='branchId_CUSTOM_VALUE' type='hidden' value='' /></td><td class='state' style='width:22%;'><label class='floatstyle'>特惠房间</label></td>"+
		"<td><input id='roomId' name='roomId'  class='text_search' value='' /></td><td class='state' style='width:22%;'>"+
		"<label class='floatstyle'>特惠价格</label></td><td><input id='discountPrice' name='discountPrice'  class='text_search' value='' /></td><td><label style='margin-left:115px;'>元</label></td><tr>"+
		"</tr><tr><td><label class='floatstyle'>显示始时</label></td><td><input id='startShowTime' name='startShowTime'  class='text_search' value='' /></td><td><label class='floatstyle'>显示末时</label></td><td><input id='endShowTime' name='endShowTime'  class='text_search' value='' /></td></tr></table>";
		$("#campaignDetail").html(innerDetail);
		$("table.specialone").css("margin-left","10px");
		$("label.yuan").css("position","relative");
		
		usingPersonselected();
		usingTypeselected();
		
		$("#branchId").bind("click",function(){
			var usingtype = $("#usingType_CUSTOM_VALUE").val();
			if(!usingtype){
				window.parent.showMsg("请先选择适用类型！");
			}else if(usingtype.length >= 2){
				window.parent.showMsg("民宿,酒店,公寓不可同选！");
			}else if((usingtype.indexOf("1") >= 0 || usingtype.indexOf("2") >= 0) && usingtype.indexOf("3") <= -1){
				//酒店公寓
				JDialog.open("归属门店", base_path + "/selectBranchId.do",450,350);
				$("#roomId").val("");
				$("#roomId").attr('disabled',false);
   				$("#roomId").css("background-color","#fff");
			}else{
				//民宿
				JDialog.open("民宿", base_path + "/selectHouse.do",450,350);
			}
		});
		
		$("#roomId").bind("click",function(){
			var usingtype = $("#usingType_CUSTOM_VALUE").val();
			if(!$("#branchId").val() && usingtype.indexOf("3") <= -1){
				window.parent.showMsg("请先选择归属店！");
			}else if((usingtype.indexOf("1") >= 0 || usingtype.indexOf("2") >= 0) && usingtype.indexOf("3") <= -1){
				//酒店公寓
				JDialog.open("归属门店", base_path + "/selectBranchRoomId.do?branchId="+$("#branchId_CUSTOM_VALUE").val(),450,350);
			}else{
				//民宿
				JDialog.open("民宿", base_path + "/selectHouse.do",450,350);
			}
		});
		
		
		laydate.render({
			  elem: '#startShowTime',
			  type: 'datetime',
			  format: 'yyyy/MM/dd HH:mm:ss',
			  theme: '#4A5064'
		});
		
		laydate.render({
			  elem: '#endShowTime',
			  type: 'datetime',
			  format: 'yyyy/MM/dd HH:mm:ss',
			  theme: '#4A5064'
		});
	} else if ($("#businessId").val() == "11"){
		var innerDetail = "<table id='campaign1' name='campaign1'>"+
		"<tr><td class='state' colspan='2' width='14%'><label>住</label></td>"+
		"<td colspan='2'><input id='roomCount' name='roomCount' class='text_search' value='' /><label  style='margin-left:12px;font-size:12px;color:#333;'>天</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"<tr><td class='state' colspan='2'><label>送</label></td>"+
		"<td colspan='2'><input id='experienceCount' name='experienceCount' class='text_search' value='' type='text' /><label style='margin-left:12px;font-size:12px;color:#333;'>天</label></td>"+
		"</tr>"+
		"</table>";
		$("#campaignDetail").html(innerDetail);
		usingPersonselected();
	} else if($("#businessId").val() == "13"){
		var innerDetail = "<table id='campaign1' name='campaign1'>"+
		"<tr><td class='state' colspan='2' width='14%'><label>住即送</label></td>"+
		"<td colspan='2'><input id='operMoney' name='operMoney' class='text_search' value='' /><label  style='margin-left:12px;font-size:12px;color:#333;'>元</label></td>"+
		"<td class='state' colspan='2'></td></tr>"+
		"</table>";
		$("#campaignDetail").html(innerDetail);
		usingPersonselected();
	}else {
		$("#campaignDetail").html("");
	}

}

function campaignPageReset(){
	window.location.reload(true);
};

function campaignClose(){
	window.parent.JDialog.close();
};

function alterCampaignInfo(){
	 //调用checkForm
	 var arr=new Array(
	     new Array('businessId','业务类型不可为空!'),
	     new Array('campaignName','活动名称不可为空!'),
	     new Array('campaignType','活动分类不可为空!'),
	     new Array('usingPerson','适用对象不可为空!'),
	     new Array('usingRange','使用范围不可为空!'), 
	     new Array('usingType','适用类型不可为空!'), 
	     new Array('startTimetemp','开始时间不可为空!'), 
	     new Array('endTimetemp','结束时间不可为空!'),
	     new Array('effectiveTimetemp','生效时间不可为空!'), 
	     new Array('priority','优先级不可为空!'), 
	     new Array('campaignDesc','活动描述不可为空!')
	 );
	 
	//调用checkFormInputLength
	 var arrlength=new Array(
	     new Array('campaignName','15','活动名称不可超过15字!'),
	     new Array('usingPerson_CUSTOM_VALUE','14','适用对象不可超过7种!'),
	     new Array('usingType_CUSTOM_VALUE','6','适用类型不可超过3种!'),
	     new Array('campaignDesc','60','活动描述不可超过60字!')
	 );
	
	
	 if(!checkFormIsNull(arr) ){
	     return false;
	 }else if(!checkFormInputLength(arrlength)){
		  return false;
	 }else if(!checkTimeIsRight("startTimetemp", "endTimetemp", null, "开始时间", "结束时间", null)){
		  return false;
	 }else{
		 if($("#businessId").val()=="1"){
			 var arrCompaignNull = new Array(new Array('goodsService','推荐会员个数不可为空!'),
					                     new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('goodsService','2','推荐会员的个数不可超过2位数!'),
                    							new Array('couponGroupId','8','优惠券组合只能选一个!'));
			//校验数字
			 var regNum = /^\+?[1-9][0-9]*$/;
             var memberCount = $('#goodsService').val();
             if (!regNum.test(memberCount)){
            	 window.parent.showMsg("输入会员个数格式不正确！");
            	 $("#goodsService").val("");
            	 return false;
             }
			 if(!checkFormIsNull(arrCompaignNull) ){
			     return false;
			 }else if(!checkFormInputLength(arrCompaignLength)){
				  return false;
			 }
		 }else if($("#businessId").val()=="2"){
			 var arrCompaignNull = new Array(new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('couponGroupId','8','优惠券组合只能选一个!'));
			if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }else if($("#businessId").val()=="3"){
			 var arrCompaignNull = new Array(new Array('operMoney','充值金额不可为空!'),
                    						 new Array('couponGroupId','优惠券不可为空!'));
			 var arrCompaignLength = new Array(new Array('couponGroupId','8','优惠券组合只能选一个!'));
			 //金额校验
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var operMoney = $("#operMoney").val();
            if (!reg.test(operMoney)){
           	 window.parent.showMsg("操作金额输入格式不正确！");
           	 $("#operMoney").val("");
           	 return false;
            }
                
			if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }else if($("#businessId").val()=="4"){
			 var arrCompaignNull = new Array(new Array('discountPrice','金额不可为空!'));
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var discountPrice = $('#discountPrice').val();
            if (!reg.test(discountPrice)){
            	window.parent.showMsg("输入金额格式不正确！");
	           	$("#discountPrice").val("");
	           	$("#discountPrice").focus();
	           	return false;
            }
			 if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			 }
		 }else if($("#businessId").val()=="5"){
			 var arrCompaignNull = new Array(new Array('liveDay','天数不可为空!'));
			 var reg = /^\+?[1-9][0-9]*$/;
            var liveDay = $('#liveDay').val();
            if (!reg.test(liveDay)){
           	 window.parent.showMsg("输入天数格式不正确！");
           	 $("#liveDay").val("");
             $("#liveDay").focus();
           	 return false;
            }
             var datas = $("input[id^=dayCount]");
             for(var k=0;k<datas.length;k++){
           	  var content = $(datas[k]).val();
           	  if (content == null || content ==""){
           		 window.parent.showMsg("折扣不可为空！");
           		 $(datas[k]).val("");
           		 $(datas[k]).focus();
           	  }
           	  if (!reg.test(content)){
                	 window.parent.showMsg("输入折扣的格式不正确！");
                	 $(datas[k]).val("");
                	 $(datas[k]).focus();
                	 return false;
                 } 
             }
			 if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			 }
		 }else if($("#businessId").val()=="6"){
			 var arrCompaignNull = new Array(new Array('roomType_CUSTOM_VALUE','房型不可为空!'),
					 							new Array('experienceCount','房间数不可为空!'),
					 			   new Array('discountPrice','特惠价格不可为空!'));
			 var arrCompaignLength = new Array(new Array('roomType_CUSTOM_VALUE','4','房型只能选一个!'));
			 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            var discountPrice = $('#discountPrice').val();
            if (!reg.test(discountPrice)){
           	 window.parent.showMsg("输入金额格式不正确！");
           	 setTimeOut(window.parent.location.reload(),1000);
           	 $("#discountPrice").val("");
           	 return false;
            }
            //校验非零正整数
            var regNum = /^\+?[1-9][0-9]*$/;
            var roomCount = $('#experienceCount').val();
            if (!regNum.test(roomCount)){
           	 window.parent.showMsg("输入房间数量格式不正确！");
           	 $("#experienceCount").val("");
           	 return false;
            }
            if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}else if(!checkFormInputLength(arrCompaignLength)){
				return false;
			}
		 }else if ($("#businessId").val()=="8"){
			 var arrCompaignNull = new Array(new Array('branchId','归属店不可为空!'),
					 new Array('roomId','特惠房间不可为空!'),
					 new Array('discountPrice','特惠价格不可为空!'),
					 new Array('startShowTime','显示始时不可为空!'),
					 new Array('endShowTime','显示末时不可为空!'));
			
			if(!checkFormIsNull(arrCompaignNull) ){
			return false;
			}
			
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			var discountPrice = $('#discountPrice').val();
			if (!reg.test(discountPrice)){
			window.parent.showMsg("输入金额格式不正确！");
			$("#discountPrice").val("");
			return false;
			}
			
			if(!checkTimeIsRight("startShowTime", "endShowTime", null, "显示始时", "显示末时", null)){
			return false;
			}
		} else if ($("#businessId").val()=="11"){
			 var arrCompaignNull = new Array(new Array('roomCount','居住天数不可为空!'),new Array('experienceCount','赠送天数不可为空!'));
			 var reg = /^\+?[1-9][0-9]*$/;
             var roomCount = $('#roomCount').val();
             var experienceCount = $('#experienceCount').val();
	         if (!reg.test(roomCount)){
		       	 window.parent.showMsg("输入天数格式不正确！");
		       	 $("#roomCount").val("");
		       	 $("#roomCount").focus();
		       	 return false;
	         }
            
             if (!reg.test(experienceCount)){
              	 window.parent.showMsg("输入天数格式不正确！");
              	 $("#experienceCount").val("");
              	 $("#experienceCount").focus();
              	 return false;
             }
            
			if(!checkFormIsNull(arrCompaignNull) ){
				return false;
			}
		} else if($("#businessId").val()=="13"){
			 var arrCompaignNull = new Array(new Array('operMoney','金额不可为空!'));
	          
				if(!checkFormIsNull(arrCompaignNull) ){
					return false;
				}
				
				var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
				var operMoney = $('#operMoney').val();
				if (!reg.test(operMoney)){
					window.parent.showMsg("输入金额格式不正确！");
					$("#operMoney").val("");
					return false;
				}
				
				
			}
		 
		 //在开始时间，结束时间保存到后台时将表单里的startTime,endTime清空，将相关内容在js里生成想要的string模式，再到后台进行转换
		// alert("开始时间"+$("#startTimeTemp").val()+"结束shiajina"+$("#endTimeTemp").val())
		 	var realStartTime = transStartAndEndTime($("#startTimetemp").val()); 
		 	var realEndTime = transStartAndEndTime($("#endTimetemp").val());
		 	var realEffectTime = null;//transStartAndEndTime($("#effectiveTimetemp").val());
		 	//alert("sssss"+realStartTime+"ffff"+realEndTime)
		 	$("#businessId").removeAttr("disabled");
		 //ajax提交数据
			$.ajax({
				url:base_path + '/campaignsAlter.do?realStartTime='+realStartTime+'&realEndTime='+realEndTime+'&realEffectTime='+realEffectTime,
				type:"post",
				data : $("#basicInfo").serialize(),
				success : function(json){
					if(json.result == 1){
						window.parent.showMsg("修改成功！");
						window.parent.refreshGrid();
						window.parent.JDialog.close();
					}else if(json.result == 2){
						window.parent.showMsg("已有类似活动，请确认！");
						//重置当前页面
						//window.setTimeout("window.location.reload(true)", 1500);
					}else if(json.result == 3){
						var message = json.message;
						window.parent.showMsg("该时段已有相似活动,是否覆盖?",continueAlter);
					}else{
						window.parent.showMsg("操作失败！");
						window.setTimeout("window.parent.location.reload(true)", 1000);
					}
				},
				error:function(json){
					window.parent.showMsg("操作异常！"+json);
					window.setTimeout("window.parent.location.reload(true)", 1000);
				},
			})		
   }
}

//批量验证表单非空
function checkFormIsNull(arr){
	 for(var i=0;i<arr.length;i++){
		 if($("#"+arr[i][0]).val()==''){
			 window.parent.showMsg(arr[i][1]);
			 $("#"+arr[i][0]).focus();
			 return false;
		 }
	 }
	 return true; 
};

//批量验证字段长度
function checkFormInputLength(arrlength){
	 for(var j=0; j<arrlength.length;j++){
		 if($("#"+arrlength[j][0]).val().length > Number(arrlength[j][1])){
			 window.parent.showMsg(arrlength[j][2]);
			 $("#"+arrlength[j][0]).focus();
			 return false;	 
		 }
	 }
	 return true;
};

//检验时间的对错
function checkTimeIsRight(time1, time2, time3, timedesc1, timedesc2, timedesc3){
	errorMsg = "";
	startDate = $("#"+time1).val().replace("-","/");
	endDate = $("#"+time2).val().replace("-","/");
	var d1 = new Date(Date.parse(startDate));  
	var d2 = new Date(Date.parse(endDate)); 
	if(time3 != null){
		effectiveDate = $("#"+time3).val().replace("-","/");
		var d3 = new Date(Date.parse(effectiveDate));  
	}
	
	if(d1 >= d2){  
		errorMsg = timedesc1 + "不能晚于 \n" + timedesc2; 
		window.parent.showMsg(errorMsg);
		return false;	
	} else if (time3 != null && d3 > d1){
		errorMsg = timedesc3 + "不能晚于 \n" + timedesc1;
		window.parent.showMsg(errorMsg);
		return false;	
	} else {
		return true;
	}
}
//删除当前记录
function deleteCampaignInfo(){
	$("#businessId").removeAttr("disabled");
	var realStartTime = transStartAndEndTime($("#startTimetemp").val()); 
 	var realEndTime = transStartAndEndTime($("#endTimetemp").val());
 	var realEffectTime ="";
	$.ajax({
		url:base_path + '/campaignsDelete.do?realStartTime='+realStartTime+'&realEndTime='+realEndTime+'&realEffectTime='+realEffectTime,
		type:"post",
		data : $("#basicInfo").serialize(),
		success : function(json){
			if(json.result == 1){
				window.parent.showMsg("删除成功！");
				window.parent.refreshGrid();
				window.parent.JDialog.close();
			}else{
				window.parent.showMsg("删除失败！");
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		},
		error:function(json){
			window.parent.showMsg("操作异常！"+json);
			window.setTimeout("window.parent.location.reload(true)", 1000);
		},
	})
}

//取转换后的开始时间，结束时间
function transStartAndEndTime(obj){
	if(obj.indexOf("-") > 0){
		var tempsdate = obj.replace(/-/g,"/").substring(0,obj.replace(/-/g,"/").length-1); 
		return tempsdate;
	}else{
		return obj;
	}
	
}

function selectInfoSelectedTwo(obj,idName){
	$("#"+idName).val("所有会员");
	$("#"+idName+"_CUSTOM_VALUE").val(obj);
	/*alert("对象："+obj)
	alert("对象的长度："+obj.length)
	for ( var i = 0; i < obj.length; i++) {
		alert("数组中的某一层："+obj[i])
		for ( var j = 0; j < $("#"+idName+" option").length; j++) {
			alert(obj[i]+"<-------------->"+$("#"+idName+" option")[j].value)
			if (obj[i] == $("#"+idName+" option")[j].value) {
				$("#"+idName+" option")[j].selected = true;
			}
		}
	}*/
}

function usingPersonselected(){
	var array =['2','3','4','5','6'];
	selectInfoSelectedTwo(array,"usingPerson");	
	$("#usingPerson").unbind("click");
	$("#usingPerson").attr("disabled","disabled");
	$("#usingPerson").css("background-color","#EAEDF1");
}

function usingPersonnotselected(){	
	$("#usingPerson").attr("disabled",false);
	$("#usingPerson").val("");
	$("#usingPerson_CUSTOM_VALUE").val("");
	$("#usingPerson").bind("click",function(){
	 	JDialog.open("适用对象", base_path + "/selectMultiUsingPerson.do",450,350);
	});
	
	$("#usingPerson").css("background-color","#fff");
}

function usingPersonselected(){
	var array =['2','3','4','5','6'];
	selectInfoSelectedTwo(array,"usingPerson");	
	$("#usingPerson").unbind("click");
	$("#usingPerson").attr("disabled","disabled");
	$("#usingPerson").css("background-color","#EAEDF1");
}

function usingTypeselected(){
	$("#usingType").unbind("click");
	$("#usingType").attr("disabled","disabled");
	$("#usingType").css("background-color","#EAEDF1");
}

function continueAlter(){
	var realStartTime = transStartAndEndTime($("#startTimetemp").val()); 
 	var realEndTime = transStartAndEndTime($("#endTimetemp").val());
 	var realEffectTime = "";
	$.ajax({
		url:base_path + '/campaignsAlter.do?checkflag="nocheck"'+'&realStartTime='+realStartTime+'&realEndTime='+realEndTime+'&realEffectTime='+realEffectTime,
		type:"post",
		data : $("#basicInfo").serialize(),
		success : function(json){
			if(json.result == 1){
				window.parent.showMsg("保存成功！");
				window.parent.refreshGrid();
				window.parent.JDialog.close();
			}else if(json.result == 2){
				window.parent.showMsg("已有类似活动，请确认！");
				//重置当前页面
				//window.setTimeout("window.location.reload(true)", 1500);
			}else if(json.result == 3){
				var message = json.message;
				window.parent.showMsg("该时段已有相似活动,是否覆盖?",continueAlter);
			}else{
				window.parent.showMsg("操作失败！");
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		},
		error:function(json){
			window.parent.showMsg("操作异常！"+json);
			window.setTimeout("window.parent.location.reload(true)", 1000);
		}
	})	
}