//新增模板
function addSmsTemplateNo(){
	 //调用checkForm
	 var arr=new Array(
	     new Array('status','启用状态不可为空!'),
	     new Array('TemplateName','模板名称不可为空!'),
	     new Array('SmsCategory','模板类型不可为空!'),
	     new Array('branchId','归属门店不可为空!'),
	     new Array('varTemplate','模板参数不可为空!'),
	     new Array('templateContent','模板内容不可为空!')   
	 );
	 
	//调用checkFormInputLength
	 var arrlength=new Array(
	     new Array('TemplateName','15','模板名称不可超过15字!'),
	     new Array('varTemplate','200','模板参数不可超过200字!'),
	     new Array('templateContent','150','模板内容不可超过150字!')   
	 );
	 if(!checkFormIsNull(arr) ){
	     return false;
	 }else if(!checkFormInputLength(arrlength)){
		  return false;
	 }else(
		$.ajax({	
			url : base_path + '/saveNewTemplate.do',
			dataType : "json",
			type : "post",
			data : $("#myForm").serialize(),
			success : function(json){
				if(json.result == 1){
					window.parent.showMsg("新增成功！");
					window.parent.refreshGrid();
					window.parent.JDialog.close();
				}else if(json.result == 2){
					window.parent.showMsg("已有当前业务的模板！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}else if(json.result == 3){
					window.parent.showMsg("当前业务的模板不止一个！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}else{
					window.parent.showMsg("操作失败");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}
			},
			error:function(json){
				window.parent.showMsg("操作失败！" + json);
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		})	
	)	
	
};

//修改模板
function editSmsTemplateNo(){
	 //调用checkForm
	 var arr=new Array(
	     new Array('status','启用状态不可为空!'),
	     new Array('TemplateName','模板名称不可为空!'),
	     new Array('SmsCategory','模板类型不可为空!'),
	     new Array('branchId','归属门店不可为空!'),
	     new Array('varTemplate','模板参数不可为空!'),
	     new Array('templateContent','模板内容不可为空!')   
	 );
	 
	//调用checkFormInputLength
	 var arrlength=new Array(
	     new Array('TemplateName','15','模板名称不可超过15字!'),
	     new Array('varTemplate','200','模板参数不可超过200字!'),
	     new Array('templateContent','150','模板内容不可超过150字!')   
	 );
	 if(!checkFormIsNull(arr) ){
	     return false;
	 }else if(!checkFormInputLength(arrlength)){
		  return false;
	 }else( 
		$.ajax({
			url : base_path + '/alterSmsTemplate.do',
			dataType : "json",
			type : "post",
			data : $("#myForm").serialize(),
			success : function(json){
				if(json.result == 1){
					window.parent.showMsg("修改成功！");
					window.parent.refreshGrid();
					window.parent.JDialog.close();
				}else if(json.result == 2){
					window.parent.showMsg("已有当前业务的模板！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}else if(json.result == 3){
					window.parent.showMsg("当前业务的模板不止一个！");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}else{
					window.parent.showMsg("操作失败");
					window.setTimeout("window.parent.location.reload(true)", 1000);
				}
			},
			error:function(json){
				window.parent.showMsg("操作失败！" + json);
				window.setTimeout("window.parent.location.reload(true)", 1000);
			}
		})	
	)	
	
};

function SmsPageReset(){
	window.location.reload(true);
};

function Smsclose(){
	window.parent.JDialog.close();
};

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
		 //console.log("idlength"+$("#"+arrlength[j][0]).val().length+"ffffff"+arrlength[j][1]);
		 if($("#"+arrlength[j][0]).val().length > Number(arrlength[j][1])){
			 window.parent.showMsg(arrlength[j][2]);
			 $("#"+arrlength[j][0]).focus();
			 return false;	 
		 }
	 }
	 return true;
 };
