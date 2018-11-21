//*******************************************
var roomTypeStatus=0;
function submitRoomType(){
	var houseId = $("#houseId").val();
	var theme = $("#theme").val();
	var brId;
	var th = $("#themeId").val();
	if(theme == '3'){
		brId = houseId
	}else{
		brId = loginUserBranchId;
	}
//	if(branchRank == '0' && theme != '3'){
//		showMsg("总店员工没有权限增加酒店公寓房型！");
//		window.setTimeout("window.parent.JDialog.close();",1000);
//		return false;
//	}
	var formData = new FormData($("#basicInfo")[0]);
	var branchId=$("#branchId").val();
	 var roomType=$("#roomType").val();
	 var roomBed=$("#roomBed").val();
	 var remark = $("#remark").val();
	 if(remark.length >100){
		 showMsg("备注字段最多100个字符!");
		 return false;
	 }
	 $("#theme").attr("disabled",false);
	 if( $("#roomTypeHoursPrice").is(':visible')){
		 var roomTypeHoursPrice=$("#roomTypeHoursPrice").val();
		 if(roomTypeHoursPrice == ""){
			 showMsg("价格不能为空！");
			 return false; 
		 }
		 var rthp = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
		 if(!(rthp.test(roomTypeHoursPrice))){
			   showMsg("时租价必须是正数！");
			   return false; 
		   }
	 }
	 if( $("#roomTypePrice").is(':visible')){
		 var roomTypePrice=$("#roomTypePrice").val();
		 if(roomTypePrice == ""){
			 showMsg("价格不能为空！");
			 return false; 
		 }
		 var rthp = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
		 if(!(rthp.test(roomTypePrice))){
			 showMsg("门市价必须是正数！");
			 return false; 
		 }
	 }
	 if( $("#cleanPrice").is(':visible')){
		 var cleanPrice=$("#roomTypeCleanPrice").val();
		 if(cleanPrice == ""){
			 showMsg("价格不能为空！");
			 return false; 
		 }
		 var rthp = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
		 if(!(rthp.test(cleanPrice))){
			   showMsg("保洁费用必须是正数！");
			   return false; 
		   }
	 }
	   var roomName=$("#roomName").val();
	   var g = /^[1-9]*[1-9][0-9]*$/;  
	   if(!(g.test(roomBed))){
		   showMsg("床位必须是数字！");
		   return false;
	   }
	  /* var rtp = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
	   if(!(rtp.test(roomTypePrice))){
		   showMsg("门市价必须是正数！");
		   return false; 
	   }*/
/* 	   window.location.href=base_path+"/validateRoomType.do?roomType="+roomType;*/
	   if(roomType==""){
		   showMsg("请填写房型码！");
	   }else{
		   roomTypeStatus=1;
		   $.ajax({
				url: base_path+"/validateRoomType.do",
				type: "post",
				dataType: "json",
				data: { roomType: roomType,
					brId : brId
				},
				success: function(json) {
					var result = json.result;
					if (result == 1) {
						 showMsg("房型码已存在！");
					}else{
						if(roomName!=""&&roomBed!=""&&roomType!=""&&roomTypePrice!=""&&roomTypeHoursPrice != ""){
							$.ajax({
									url: base_path+"/addRoomTypeData.do",
									type: "post",
									dataType: "json",
									data: formData,
									contentType: false,
							        processData: false,
									success: function(json) {
										showMsg("添加成功！");
										window.setTimeout("window.parent.location.reload(true)", 1000);
										window.setTimeout("window.parent.JDialog.close();",1000);
										    },
									error: function(){
										showMsg("后台数据错误！添加失败！");
									}
										});
						   }else{
							   
							   showMsg("名称，床位不可为空！"); 
						   }
					}
				},
				error: function(json) {
					showMsg("操作失败!");
					 window.setTimeout("location.reload(true)", 1800);
				}
			}); 
	   }
		
		
	/*	if(roomTypeStatus==1){
			var formData = new FormData($("#basicInfo")[0]); 
			   var roomType=$("#roomType").val();
			   var roomBed=$("#roomBed").val();
			   var roomName=$("#roomName").val();
			   console.log(formData);
			   console.log($("#basicInfo"));
			   if(status==1){
				   showMsg("房型码已存在！");
			   }else{
				   if(roomName!=""&&roomBed!=""&&roomType!=""){
					   $.ajax({
							url: base_path+"/addRoomTypeData.do",
							type: "post",
							dataType: "json",
							data: formData,
							contentType: false,
					        processData: false,
							success: function(json) {
								showMsg("添加成功！");
								window.setTimeout("window.parent.location.reload(true)", 1000);
								window.setTimeout("window.parent.JDialog.close();",1000);
								    },
							error: function(){
								showMsg("后台数据错误！添加失败！");
							}
								});
				   }else{
					   showMsg("名称，床位不可为空！"); 
				   }
			   }
		}*/
	   
		      
}

function validate(){
	   var roomType=$("#roomType").val();
/* 	   window.location.href=base_path+"/validateRoomType.do?roomType="+roomType;*/
	   if(roomType==""){
		   showMsg("请填写房型码！");
	   }else{
		   roomTypeStatus=1;
		   $.ajax({
				url: base_path+"/validateRoomType.do",
				type: "post",
				dataType: "json",
				data: { roomType: roomType
				},
				success: function(json) {
					var result = json.result;
					if (result == 1) {
						 status=1;
						 showMsg("房型码已存在！");
					}else{
						status=0;
					}
				},
				error: function(json) {
					showMsg("操作失败!");
					 window.setTimeout("location.reload(true)", 1800);
				}
			}); 
	   }
	   
}
