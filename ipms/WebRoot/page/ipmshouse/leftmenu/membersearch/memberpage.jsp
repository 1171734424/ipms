 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("membername", request.getAttribute("membername")); 
   request.setAttribute("memberid", request.getAttribute("memberid")); 
   request.setAttribute("rankname", request.getAttribute("rankname")); 
   request.setAttribute("gendor", request.getAttribute("gendor"));
   request.setAttribute("mobile", request.getAttribute("mobile"));
   request.setAttribute("birthday", request.getAttribute("birthday"));
   request.setAttribute("idcard", request.getAttribute("idcard"));
   request.setAttribute("email", request.getAttribute("email"));
   request.setAttribute("source", request.getAttribute("source"));
   request.setAttribute("validtime", request.getAttribute("validtime"));
   request.setAttribute("invalidtime", request.getAttribute("invalidtime"));
   request.setAttribute("address", request.getAttribute("address"));
   request.setAttribute("remark", request.getAttribute("remark"));
%>

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
    <link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.gray{
	background: #f0f0f0;
	
	}
	
	</style>
  </head>
  <body>
    <input id="gendorid" name="" class="input_ms" type="hidden" value="${gendor}" disabled="true"/>
  <div class="member_margin">
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
			  <td><input id="" name="" class="input_ms" type="text" value="${membername}" disabled="true"/>
			  </td>
			  <td align="right"><span>会员卡号</span></td>
			  <td><input id="msmemberid" name="" class="input_ms" type="text" value="${memberid}" disabled="true"/></td>
			  <td align="right"><span>会员等级</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${rankname}" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>性别</span></td>
			  <td>
			  <select id="gender" name="gender" class="input_ms gray"  disabled="true" >
										<option value="0">
											女
										</option>
										<option value="1">
											男
										</option>
								</select>
			  </td>
			   <td align="right"><span>手机号</span></td>
			  <td><input id="msmobile" name="" class="input_ms" value="${mobile}" type="text"/></td>
			  <td align="right"><span>出生日期</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${birthday}" disabled="true"/></td>
			  </tr>
			  <tr>
			  <td align="right"><span>身份证</span></td>
			  <c:if test="${idcard == ''}">
			  	<td><input id="idcard" name="idcard" class="input_ms" type="text" value="${idcard}" /></td>
			  </c:if>
			  <c:if test="${idcard != ''}">
			  	<td><input id="idcard" name="idcard" class="input_ms" type="text" value="${idcard}" disabled/></td>
			  </c:if>
			  <td align="right"><span>Email</span></td>
			  <td><input id="msemail" name="" class="input_ms" type="text" value="${email}"/></td>
			   <td align="right"><span>销售来源</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${source}" disabled="true"/></td>
			  </tr>
			  <tr>
			 
			  <td align="right"><span>生效时间</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${validtime}" disabled="true"/></td>
			  <td align="right"><span>失效时间</span></td>
			  <td><input id="" name="" class="input_ms" type="text" value="${invalidtime}" disabled="true"/></td>
			  </tr>
			  
			  <tr >
			  <td align="right" style="height:60px;"><span>地址</span></td>
			  <td colspan="5"><textarea id="msadress" name="" class="textarea_members" type="text"/>${address}</textarea></td>
			  </tr>
			  <tr>
			  <td align="right"><span>备注</span></td>
			  <td colspan="5"><textarea id="msremark" name="" class="textarea_members" type="text"/>${remark}</textarea></td>
			  </tr>
			  </table>
		   </section>   			    
          <div class="editdata_ms">
             <input type="button" class="button_margin submitbotton_membersearch two_button" onclick="editmemberdata()" value="修改资料">
          </div>
       </div>
	<script>
	
	</script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
	<script src="<%=request.getContextPath()%>/script/ipms/js/leftmenu/membersearch.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
