<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/script.jsp"%>
<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>会员修改</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/mebregister.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script type="text/javascript">var mem = ${ member };</script>
  </head>
  <body>
  <div class="register_margin">
     <form action="" method="post" id="omemberadd" name="omemberadd">
	      <section class="register_one">
	        <ul class="clearfix">
	          <li><label class="label_name red">会员卡号</label><input id="memberId" name="memberId" class="text_search input_ms" readonly="readonly" type="text" value=${ member.member.memberId } /></li>
	          <li><label class="label_name red">会员等级</label><input id="memberRank" name="" class="text_search input_ms" readonly="readonly" type="text" value=${ member.rankName } /></li>
			  <li><label class="label_name">姓名</label><input id="memberName" name="memberName" class="text_search input_ms" type="text" value=${ member.member.memberName } /></li>
			  <li><label class="label_name">性别</label>
			  	<select id="gendor" name="gendor" class="text_search input_ms" value="1">
	                <option  value="0">女</option>
                    <option  value="1">男 </option>	
                </select>
              </li>
			  <li><label class="label_name red">手机号</label><input id="mobile" name="mobile" class="text_search input_ms" type="text" value='${ member.member.mobile }' /></li>
			  <li><label class="label_name red">身份证</label><input id="idcard" name="idcard" class="text_search input_ms" type="text" value='${ member.member.idcard }' /></li>
			  <li><label class="label_name red">Email</label><input id="email" name="email" class="text_search input_ms" type="text" value='${ member.member.email }' /></li>
			  <li><label class="label_name red">销售来源</label><input id="source" name="" class="text_search input_ms" disabled type="text" value='${ member.decodesource }' /></li>
			  <li><label class="label_name red">生效时间</label><input id="validTime" name="" class="text_search input_ms" readonly="readonly" type="text" value='${ member.validTime }' /></li>
			  <li><label class="label_name red">失效时间</label><input id="invalidTime" name="" class="text_search input_ms" readonly="readonly" type="text" value='${ member.invalidTime }' /></li>
			  <li><label class="label_name red">地址</label><input id="address" name="address" class="text_search input_ms" type="text" value='${ member.member.address }' /></li>
			  <li><label class="label_name red">备注</label><input id="remark" name="remark" class="text_search input_ms" type="text" value='${ member.member.remark }' /></li>
			  </ul>
		   </section> 
		 </form> 	    
       <div class="editdata_ms">
          <input type="button" class="button_margin ms_re" onclick="updateMember()" value="修改">
       </div>
   </div>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		$(function (){
			$("#gendor").val(mem.member.gendor);
		});
		
		function updateMember(){
			if(isNull($("#memberName"), "姓名：")){
				return false;
			}
			if(isNull($("#mobile"), "手机号：")){
				return false;
			}
			if(!isMobile($("#mobile").val())){
				return false;
			}
			if(!isIdcard($("#idcard").val()) && $("#idcard").val() != ''){
				return false;
			}
			if(!isEmail($("#email").val()) && $("#email").val() != ''){
				return false;
			}
			
			$.ajax({
		         url: path + "/updatemember.do",
				 type: "post",
				 data : $("#omemberadd").serialize(),
				 success: function(json) {
				 	if(json.result == 1){
				 		showMsg(json.message);
				 		setTimeout("refreshPage()", 2000);
				 	}else if(json.result == 0){
				 		window.showMsg(json.message);
				 	}
				 },
				 error: function(json) {}
			});
		}
		
		function refreshPage(){
			$(window.parent.document).find(".tab_one").click();
			window.parent.JDialog.close();
			//$(window.parent["document"]["all"]["customer"]["childNodes"][0]["contentDocument"]).find("#searchmembercondotion").val(json.data);
		}
		
	</script>
  </body>
</html>
