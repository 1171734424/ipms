<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>新增员工</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/manage/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
    <script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/IMGUP.css" rel="stylesheet" type="text/css" media="all" />

	
	<style>
	.ptdinput2{
	 width: 180px;
	 height: 30px;
	 border-bottom-right-radius:6px;
	 border-bottom-left-radius:6px;
	 border-top-right-radius:6px;
	 border-top-left-radius:6px;
	 border: 1px solid #dddddd;
	 background-color: white;
	 margin: 4px;
      }
	.basicInfo .tdtwo2_address{
	   width: 440px;
	   height: 30px;
	   border-bottom-right-radius: 6px;
	   border-bottom-left-radius: 6px;
	   border-top-right-radius: 6px;
	   border-top-left-radius: 6px;
	   border: 1px solid #dddddd;
	   background-color: white;
	   margin: 4px;
       padding: 0 4px;
       margin-left: 11px;
	}
	.basicInfo .tdtwo2{
	   width: 180px;
	   height: 30px;
	   border-bottom-right-radius: 6px;
	   border-bottom-left-radius: 6px;
	   border-top-right-radius: 6px;
	   border-top-left-radius: 6px;
	   border: 1px solid #dddddd;
	   background-color: white;
	   margin: 4px;
       padding: 0 4px;
	}
	table#roomTypeEdit2 {
    width: 81%;
    position: relative;
    left: 5%;
    border: none;
    margin-top: -1px;
    margin-left: 33px;
	}
	.contractbutton2{
		width: 100%;
	    text-align: center;
	    margin: -11% auto;
	}
	.pdepartsubmit2 {

    float: left;
    width: 100px;
    height: 36px;
    background-color: #5A5D9D;
    border-radius: 3px;
    text-align: center;
    padding: 3px;
    margin: 3px;
    margin-left: 190px;
    line-height: 30px;
    font-size: 15px;
    font-family: Microsoft YaHei;
    color: #fff;
    font-weight: normal;
    cursor: pointer;

}
    .pdepartcancle2{
	    float: left;
	    width: 100px;
	    height: 36px;
	    background-color: #FC8A15;
	    border-radius: 3px;
	    text-align: center;
	    padding: 3px;
	    margin: 3px;
	    margin-left: 39px;
	    line-height: 30px;
	    font-size: 15px;
	    font-family: Microsoft YaHei;
	    color: #fff;
	    font-weight: normal;
	    cursor: pointer;

    }
    td.tdone22 {
    width: 47%;
    text-align: right;
    vertical-align: middle;
    border: 0 none;
    padding: 2px;
    white-space: nowrap;
}
    #remark2{
    width: 443px;
    height: 89px;
    border-bottom-right-radius: 6px;
    border-bottom-left-radius: 6px;
    border-top-right-radius: 6px;
    border-top-left-radius: 6px;
    border: 1px solid #dddddd;
    background-color: white;
    margin: 6px;
    margin-left: 9px;
    padding: 0 4px;
}
	</style>
  </head>
	<div>
		<form id="basicInfo" name="basicInfo" class="basicInfo"  method="POST" enctype="multipart/form-data"  >
		<div style="height:441px;">
			<table id="roomTypeEdit2">
				<tr>
					<td id="no_input" class="tdone2" style="color:red">
						管理员姓名
					</td>
					<td class="tdtwo"><input class="tdtwo2" id="staffname"  placeHolder="请输入1-10个有效字符" maxlength="10"  name="staffname"></input></td>
				
					<td class="tdone2" style="color:red">
						登录名
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="loginname" maxlength="10" placeHolder="字母、数字、下划线组成"
							name="loginname"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone2" style="color:red">
						归属门店
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="branchname" name="branchname"></input>
						<input class="tdtwo2" type="hidden" id="branchname_CUSTOM_VALUE" name="branchid"></input>
					</td>
				
					<td class="tdone2" >
					             部门
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="departname" name="departname"></input>
						<input class="tdtwo2" type="hidden" id="departname_CUSTOM_VALUE" name="departid"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone2" style="color:red">
						职务
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="postname" name="postname"></input>
						<input class="tdtwo2" type="hidden" id="postname_CUSTOM_VALUE" name="postid"></input>
					</td>
				
					<td class="tdone2" style="color:red">
						身份证
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="idcard" name="idcard"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone2" style="color:red">
						手机号
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="mobile" name="mobile"></input>
					</td>
				
<!-- 					<td class="tdone2" style="color:red"> -->
<!-- 						生日 -->
<!-- 					</td> -->
<!-- 					<td class="tdtwo" > -->
<!-- 						<input class="tdtwo2" id="birthday" name="birthday"></input> -->
<!-- 					</td> -->
                    <td id="no_input" class="tdone2">
						性别
					</td>
					<td class="tdtwo">
						<select id="gender" class="ptdinput2" name="gender">
								 <option value="1">男</option>
						         <option value="0">女</option>
						</select>
						
					</td>
				</tr>
				<tr>
					<td class="tdone2" style="color:red">
						入职时间
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="entrytime" name="entrytime"></input>
					</td>
				    <td id="no_input" class="tdone2">
						状态
					</td>
					<td class="tdtwo">
                        <select id="status" class="ptdinput2" name="status">
								<option value="1">有效</option>
								<option value="2">自营</option>
								<option value="3">加盟</option>
						</select>
					</td>
					
				</tr>
				<tr>
					
				
					<td class="tdone2" >
						邮件
					</td>
					<td class="tdtwo">
						<input class="tdtwo2" id="email" name="email"></input>
					</td>
				</tr>
				<tr>
					<td class="tdone2" >
						地址
					</td>
					<td class="tdtwo">
						<input class="tdtwo2_address" id="address" name="address" maxlength="25"></input>
					</td>
				</tr>
 				<tr> 
 					<td class="tdone2" > 
 						备注：
 					</td> 
					<td ><textarea id="remark2" name="remark" class="" rows="2" maxlength="60" ></textarea>

 					</td>
				</tr> 


			</table>
	</div>


	<div class="contractbutton2">
		<div class="pdepartsubmit2" onclick="submitstaffinfo()">提交</div>
		<div class="pdepartcancle2" onclick="window.parent.JDialog.close()">取消</div>
	</div>
	</form>
	</div>
	<script src="<%=request.getContextPath()%>/script/crm/manage/IMGUP.js"></script>


</body>
  <script type="text/javascript">
var base_path = '<%=request.getContextPath() %>';
//              laydate({
// 	        	elem: '#birthday'
	        	
	        	
//     		});
              laydate({
	        	elem: '#entrytime'
	        
    		});
    		
    		
  		$(function() {

			$("#branchname").bind("click",function(){
			 	JDialog.open("归属门店", base_path + "/addhouse2querybranch.do",450,350);
			});
			
			$("#departname").bind("click",function(){
			 	JDialog.open("部门", base_path + "/addhouse2querydepart.do",450,350);
			});
			
			$("#postname").bind("click",function(){
			 	JDialog.open("职务", base_path + "/addhouse2querypostname.do",450,350);
			});
			

		});  		
  
  function submitstaffinfo(){
  
       var arr=new Array(
		     new Array('staffname','员工姓名不可为空!'),
		     new Array('loginname','登录名不可为空!'),
		     
		     new Array('branchname','归属门店不可为空!'),
		    // new Array('departname','部门不可为空!'),
		     new Array('postname','职务不可为空!'),
		        
		     new Array('idcard','身份证不可为空!'),
		     new Array('mobile','手机号不可为空!'), 
		    // new Array('birthday','出生日期不可为空!'), 
		     new Array('entrytime','入职日期不可为空!')
		    
		 );
		 
		 if(!checkFormIsNull(arr) ){
	       return false;
	     }
	    
	    var staffname = $("#staffname").val();
		var loginname = $("#loginname").val();
		var branchname_CUSTOM_VALUE = $("#branchname_CUSTOM_VALUE").val();
		var departname_CUSTOM_VALUE = $("#departname_CUSTOM_VALUE").val();
		var postname_CUSTOM_VALUE = $("#postname_CUSTOM_VALUE").val();
		
		
		var idcard = $("#idcard").val();
		var mobile = $("#mobile").val();
		//var birthday = $("#birthday").val();
		var entrytime = $("#entrytime").val();
		var email = $("#email").val();
		var status = $("#status").val();
		var address = $("#address").val();
		var remark = $("#remark2").val();
		var gender = $("#gender").val();       
	   	if(!(/^[a-zA-Z][a-zA-Z0-9_]*$/.test(loginname))){
		  showMsg("登录名由字母、数字、下划线组成，开头必须为字母！");
		 return false; 
		}
		
		if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/i.test($("#idcard").val())) {
			showMsg("请输入正确的身份证号码！");
			return false; 
		}
		
		if(!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(mobile))){   
	    	  showMsg("手机号格式不正确！");  
	    	  return false;  
	         } 
	         
	    if($("#email").val() != ""){
	      if (!/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/i.test($("#email").val())) {
				showMsg("请输入正确的邮箱！");
				return false; 
			}
	     }  
	     
	     	$.ajax({
		    url: base_path+"/staffUpdate2addHouse.do",
			type: "post",
			dataType: "json",
			data: { 
			         
			         STAFFNAME :staffname,
		             LOGINNAME :loginname,
		             BRANCHID_CUSTOM_VALUE : branchname_CUSTOM_VALUE,
		             DEPARTID_CUSTOM_VALUE : departname_CUSTOM_VALUE,
		             POST_CUSTOM_VALUE : postname_CUSTOM_VALUE,
					 IDCARD :idcard,
					 MOBILE :mobile,
					 GENDOR:gender,
					// BIRTHDAY :birthday,
					 ENTRYTIME :entrytime,
					 STATUS :status,
					 EMAIL :email,
					 ADDRESS :address,
					 REMARK:remark
					 },
		    success: function(json) {
			       if(json.result == 1){
			       showMsg(json.message);
					window.setTimeout("window.parent.JDialog.close()", 1800);
			       }else{
			        showMsg(json.message);
			       }
					
		
			},
			error : function(data){
			showMsg("新增失败！");
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
   function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg, fn);
		}
	}
   


  </script>
</html>
