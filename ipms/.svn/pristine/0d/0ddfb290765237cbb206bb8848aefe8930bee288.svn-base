 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>新增公寓预约</title> 
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="member_margin ">
	  <form action="addApartmentReservedInfo.do" method="post" id="addReserved">
	      <section class="detail_one">
			  <table class="table_ms">
			  <tr>
			    <td align="right" class="first_td"><span>预约人：</span></td>
			    <td ><input id="reservedperson" name="reservedperson" class="input_ms text_search" type="text"/></td>
			    <td align="right"><span>手机号：</span></td>
			    <td><input id="mobile" name="mobile" class="input_ms text_search" type="text" /></td>
			  </tr>
			  <tr>
			    <td align="right"><span>房间类型：</span></td>
			    <td>
			    	<!-- <input id="roomType" name="roomType" class="input_ms" type="text"/> -->
			    	<select class="select_edit mySelect text_search" id="roomType" name="roomType"></select>
			    </td>
			  </tr>
			  <tr>
			    <td align="right" class="first_td"><span>备注：</span></td>
			    <td colspan="5" ><textarea id="remark" name="remark" class="textarea_ms" rows="2" ></textarea></td>
			  </tr>
		      </table>
		   </section>   			    
          <div class="editdata_ms ">
             <input  type="button" class="button_margin submitbotton_membersearch confirm_editdata" onclick="add_submit()" value="确定">
          </div>
         </form>
    </div>
	<script src="<%=request.getContextPath()%>/script/apartment/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		var path = "<%=request.getContextPath() %>";
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		function add_submit(){
			var reservedperson = $("#reservedperson").val();
			var mobile = $("#mobile").val();
			var roomType = $("#roomType").val();
			var remark = $("#remark").val();
			if("" == reservedperson){
				showMsg("请填写预约人!");
				return false;
			}else if("" == mobile || !(/^1[34578]\d{9}$/.test(mobile))){
				showMsg("请填写正确的手机号码!");
				return false;
			}else if("" == roomType){
				showMsg("请选择房间类型!");
				return false;
			}else{
				$.ajax({
					url: "addApartmentReservedInfo.do",
					dataType: "json",
					type: "post",
					data:{
						'reservedperson' : reservedperson,
						'mobile' : mobile,
						'roomType' : roomType,
						'remark' : remark
					},
					success: function(json) {
					        showMsg("处理成功!");
					        window.setTimeout("window.parent.location.reload(true)",800);
					},
					error:function(json){
							showMsg("处理失败!");
						    window.setTimeout("window.parent.location.reload(true)",1000);
					}
				});
			}
		}
		
		$(document).ready(function(){
			$.ajax({
			         url: path + "/queryApartRoomTypeInfo.do",
					 type: "post",
					 data : {},
					 success: function(json) {
						 console.log(json);
					 	var data = "<option value=''>--选择--</option>";
					 	$.each(json,function(index){
					 		data = data + "<option value='" + json[index].roomTypeKey.roomType + "'>" +json[index]["roomName"] + "</option>"
					 	});
					 	$("#roomType").html(data);
						$(".mySelect").select({
							width: "175px"
						});
					 },
					 error: function(json) {}
			});
		});
	</script>
  </body>
</html>
