 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>会员查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
    <link rel="stylesheet"  id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datetimepicker.css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
      var data = '<%=request.getParameter("data")%>';
      var maps = JSON.parse(data);
   <%--    <% 
      String data = request.getParameter("data")
      JSONObject obj = new JSONObject(data);
      %> --%>
	</script>
	<style type="text/css">
		.nbinput{
			    padding: 8px 5px;
   				 width: 158px;
    /* margin-left: 10px; */
		}
	</style>
  </head>
  <body>
  <div class="member_margin update_margin">
	  <form action="" method="post">
	      <section class="detail_one">
			  <table class="table_ms">
			  <col width="10%"/>
			  <col width="23%"/>
			  <col width="10%"/>
			  <col width="23%"/>
			  <col width="10%"/>
			  <col width="23%"/>
			  <tr>
			  <td align="right"><span>姓名</span></td>
			  <td><input id="name" name="" class=" nbinput" type="text" value="" disabled="true"/>
			  </td>
			  <td align="right"><span>会员卡号</span></td>
			  <td><input id="msmemberid" name="" class=" nbinput" type="text" value="" disabled="true"/></td>
			  <td align="right"><span>会员等级</span></td>
			  <td><input id="rankname" name="" class=" nbinput" type="text" value="" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>性别</span></td>
			  <td><input id="gender" name="" class=" nbinput" type="text" value="" disabled="true"/>
			  	<%-- <select id="gender" name="gender" class="nbinput" disabled="disabled">
			  		<c:if test="${gender == 0}">
						<option value="0">女</option>
			  		</c:if>
			  		<c:if test="${gender == 1}">
						<option value="1">男</option>
			  		</c:if>
				</select> --%>                    
			  </td>
			   <td align="right"><span>手机号</span></td>
			  <td><input id="msmobile" name="" class=" nbinput" value="" type="text"/></td>
			  <td align="right"><span>身份证</span></td>
			  <td><input id="idcard" name="" class=" nbinput" type="text" value=""/></td>
			  </tr>		
			  <tr>
			  <td align="right"><span>状态</span></td>
			  <td>
			  	<select id="status" name="status" class="nbinput">
			  		<%-- <c:if test="${status == 0}">
						<option value="0" selected>无效</option>
						<option value="1">有效</option>
			  		</c:if>
			  		<c:if test="${status == 1}">
			  			<option value="0">无效</option>
						<option value="1" selected>有效</option>
			  		</c:if> --%>
			  		<option value="0" selected>无效</option>
					<option value="1">有效</option>
				</select>
			  </td>
			  </tr>  
			  <tr>
			  <td align="right"><span>备注</span></td>
			  <td colspan="5"><textarea id="msremark" name="" class="textarea_members text_search" type="text"/></textarea></td>
			  </tr>
			  </table>
		   </section>   			    
          <div class="editdata_ms">
             <input type="button" class="button_margin update_data" onclick="updatememberdata()" value="修改资料">
          </div>
       </div>
	<script>
	 $("#name").val(maps.name);
	 $("#msmemberid").val(maps.memberid);
	 $("#rankname").val(maps.rankname);
	 if(maps.gender == "0"){
		 $("#gender").val("女");
	 }
	 if(maps.gender == "1"){
		 $("#gender").val("男");
	 }
	 $("#msmobile").val(maps.mobile);
	 $("#idcard").val(maps.idcard);
	 if(maps.status == "0"){
		 $("#status option[value='0']").attr("selected","selected");
	 }
	 if(maps.status == "1"){
		 $("#status option[value='1']").attr("selected","selected");
	 }
	 $("#msremark").val(maps.remark);
	var mobile = '${mobile}';
	function updatememberdata(){
			var memberId = $("#msmemberid").val();
			var newmobile = $("#msmobile").val();
			var idcard = $("#idcard").val();
			var status = $("#status").val();
			var memberName = $("#name").val();
			if(idcard == ""){
				showMsg("请补全会员身份信息");
				return;
			}
			$.ajax({//修改云端会员身份证
				url: base_path+"/insertMemberIdCard.do",
				type: "post",
				data: {"memberId" : memberId,
						"newmobile" : newmobile,
						"idcard" : idcard,
						"status" : status,
						"memberName" : memberName
			},
			success: function(json){
				if(json.result == 1){
					showMsg(json.message);
						setTimeout(" window.parent.JDialog.close();",1500);
						return;
					}
				showMsg(json.message);
				
			},
			error: function(){}
			});
		}
	
	function showMsg(msg, fn) {
		if (!fn) {
			TipInfo.Msg.alert(msg);
		} else {
			TipInfo.Msg.confirm(msg,fn);
		}
	}
	</script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
