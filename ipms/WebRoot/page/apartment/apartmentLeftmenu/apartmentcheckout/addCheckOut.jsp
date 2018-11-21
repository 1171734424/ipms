 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>新增退房申请</title> 
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/jquery-dialog.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
		.button_margin {
		    height: 34px;
		    line-height: 34px;
		    text-align: center;
		    width: 85px;
		    border: none;
		    color: #fff;
		    cursor: pointer;
		    border-radius: 4px;
		    font-size: 14px;
		    background: #FC8A15;
		}
		.button_div{
		    width: 100px;
   	 		margin-top: 20px;
    		margin-left: 535px;
		}
	</style>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body>
  <div class="member_margin ">
	  <form action="addReservedInfo.do" method="post" id="addReserved">
	      <section class="detail_one">
			  <table class="table_ms">
			 	<tr>
			 	    <td align="right" class="first_td"><span>房间号：</span></td>
				    <td><input id="roomId" name="roomId" class="input_ms" type="text" value ="${roomid}" readonly="readonly"/></td>
				    <td align="right" ><span>房间类型：</span></td>
				    <td><input id="roomType" name="roomType" class="input_ms" type="text" value="${roomtype}" readonly="readonly"/></td>
				    <div id="allroomid" class="class_main" style="display:none"></div>
			 	</tr>
			  	<tr>
				    <td align="right" class="first_td"><span>合同人：</span></td>
				    <td><input id="reservedperson" name="reservedperson" class="input_ms" type="text" value="${contractName}" readonly="readonly"/></td>
				    <td align="right" ><span>手机号：</span></td>
				    <td><input id="mobile" name="mobile" class="date_text text_search" type="text" value="${mphone}" readonly="readonly"/></td>
			 	</tr>
			  	<tr>
			  	    <td align="right"><span>合同号：</span></td>
				    <td><input id="contractId" name="contractId" class="input_ms" type="text"  value="${contractId}" readonly="readonly"/></td>
			  	</tr>
			  	<tr>
			  		<td align="right" ><span>退房日期：</span></td>
				    <td><input id="checkOuttime" name="checkOuttime" class="date_text text_search" type="text" /></td>
			  	</tr>
			  	<tr>
			    	<td align="right" class="first_td"><span>备注：</span></td>
			    	<td colspan="5" ><textarea id="remark" name="remark" class="textarea_ms" style="height: 110px;" rows="2" maxlength="100"></textarea></td>
			  	</tr>
		      </table>
		   </section>
          <div class="button_div">
             <input id="confirm_button" type="button" class="button_margin" onclick="add_submit()" value="确定">
          </div>
        </form>
    </div>
	<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script type="text/javascript">
		function showMsg(msg, fn) {
			if (!fn) {
				TipInfo.Msg.alert(msg);
			} else {
				TipInfo.Msg.confirm(msg, fn);
			}
		}
		
		laydate({
	    	elem: '#checkOuttime'
		});
		
		function queryContract(){
			var roomId = $("#roomId").val();
			if("" == roomId){
				showMsg("请填写退房的房间号!");
				return false;
			}else{
				$.ajax({
					url: "queryContractRoomId.do",
					dataType: "json",
					type: "post",
					data:{
						'roomId' : roomId
					},
					success: function(json) {
						if(json.length > 0){
							$("#reservedperson").val(json[0]['MEMBERID']);
							$("#mobile").val(json[0]['MOBILE']);
							$("#roomType").val(json[0]['ROOMTYPENAME']);
							$("#contractId").val(json[0]['CONTRARTID']);
						}else{
							showMsg("当前房间是空房!");
						}
					},
					error:function(json){
						showMsg("处理失败!");
					}
				});
			}
		}
		
		function add_submit(){
			var contractId = $("#contractId").val();
			var remark = $("#remark").val();
			var checkOuttime = $("#checkOuttime").val();
			if("" == contractId){
				showMsg("请选择房间!");
				return false;
			}else if("" == checkOuttime){
				showMsg("请选择退房日期!");
				return false;
			}else{
				$.ajax({
					url: "addContractInfo.do",
					dataType: "json",
					type: "post",
					data:{
						'contractId' : contractId,
						'remark' : remark,
						'checkOuttime' : checkOuttime
					},
					success: function(json) {
					        showMsg("处理成功!");
					        window.setTimeout("window.parent.location.reload(true)",1000);
					},
					error:function(json){
							showMsg("处理失败!");
						    window.setTimeout("window.parent.location.reload(true)",1000);
					}
				});
			}
		}
	</script>
  </body>
</html>
