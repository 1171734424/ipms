<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath()); 
   request.setAttribute("themeid", request.getAttribute("themeid"));
   request.setAttribute("branchid", request.getAttribute("branchid"));
   request.setAttribute("roomtypeid", request.getAttribute("roomtypeid"));
   request.setAttribute("floor", request.getAttribute("floor"));
   request.setAttribute("roomid", request.getAttribute("roomid"));
   request.setAttribute("area", request.getAttribute("area"));
   request.setAttribute("roomid", request.getAttribute("roomid"));
   request.setAttribute("remark", request.getAttribute("remark"));
   request.setAttribute("rpbranchid", request.getAttribute("rpbranchid"));
   request.setAttribute("rptheme", request.getAttribute("rptheme"));
   request.setAttribute("rproomtype", request.getAttribute("rproomtype"));
   request.setAttribute("rpstatus", request.getAttribute("rpstatus"));
   request.setAttribute("statusedit", request.getAttribute("statusedit"));
   request.setAttribute("theme", request.getAttribute("theme"));
   request.setAttribute("branch", request.getAttribute("branch"));
  %>
<!DOCTYPE html">
<html>
  <head>
    <title>编辑记录</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/datepicker.css"/>	
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/pmsmanage/pmsmanage.css" />
	<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" media="all" />
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	
	<script> 
	var base_path = '<%=request.getContextPath()%>';
	</script>
  </head>  
  <body class="room-body">
     <input id="themeid" name="themeid" type="hidden" value="${themeid}"/>
	 <input id="branchid" name="branchid" type="hidden" value="${branchid}"/>
	 <input id="roomtypeid" name="roomtypeid" type="hidden" value="${roomtypeid}"/>
	 <input id="rpstatus" name="rpstatus" type="hidden" value="${rpstatus}"/>
	 <input id="statusedit" name="statusedit" type="hidden" value="${statusedit}"/>
     <form  name="myFrom" id="myForm" class="ctdform">
		 <table class="ptable" border="0" cellspacing="0" cellpadding="0">
		    <tbody>
		    <col width="13%">
		     <col width="80%">
		     <col width="2%">
		     <col width="2%">
		     <tr>
				   <td style="height:5px;"></td>
				   <td></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr id="trprojectid">
				   <td class="ctdtitle notnull" align="center;">场景</td>
				   <td align="left" class="pinputtd">
				   <input id="rptheme" name="rptheme" class="ptdinput" type="hidden" value="${themeid}"/>
				   <input id="rpthemename" name="rpthemename" class="ptdinput" type="tetx" value="${theme}" disabled="true"/></td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">门店</td>
				   <td align="left" class="pinputtd">
				       <input id="branchid" name="rptheme" class="ptdinput" type="hidden" value="${branchid}"/>
				       <input id="branch" name="branch" class="ptdinput" type="tetx" value="${branch}" disabled="true"/>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">房型</td>
				   <td align="left" class="pinputtd">
					  <select id="roomtype" name="" class="ptdinput">
							      <c:forEach var="a" items="${rproomtype}">								 
		                            <option value="${a.roomtype}">
		                               ${a.roomname}
		                               </option>
							        </c:forEach>
					 </select>
				   </td>
				  <td class="ctdtitle"></td>
				  <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center">楼层</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rpfloor" class="ptdinput" value="${floor}"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="2"/>
					   </select>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle notnull" align="center;">房号</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rproomid" class="ptdinput" value="${roomid}" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6" disabled="true"/>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr>
				   <td class="ctdtitle" align="center;">面积</td>
				   <td align="left" class="pinputtd">
					   <input type="text" id="rparea" class="ptdinput" value="${area}" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="3"/>
				   </td>
				  <td class="ctdtitle"></td>
				  <td class="ctdtitle"></td>
				</tr>
			    <tr>
				   <td class="ctdtitle notnull" align="center;">状态</td>
				   <td align="left" class="pinputtd">
					   <select id="rpstatus1" name="" class="ptdinput">
						          <option value="">
									       请选择状态
								     </option>
							      <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==1)||(flag==0)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="rpstatus2" name="" class="ptdinput" style="display:none">
						         <option value="">
									       请选择状态
								     </option>
							       <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==2)||(flag==0)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
						         <select id="rpstatus3" name="" class="ptdinput" style="display:none">
						         <option value="">
									       请选择状态
								     </option>
							      <c:forEach var="a" items="${rpstatus}">
								    <c:set var="flag" value="${a.orderno}"></c:set>
		                            <c:if  test="${(flag==null)||(flag==3)||(flag==0)}">
		                            <option value="<c:out value='${a.content}' />"/>
		                            <c:out value="${a.paramname}"/>
		                             </c:if>
                                    </option>
							      </c:forEach>
						         </select>
				   </td>
				   <td class="ctdtitle"></td>
				   <td class="ctdtitle"></td>
				</tr>
				<tr style="height:72px">
				   <td class="ctdtitle" align="center;" style="height:72px">备注</td>
				   <td align="left" class="pinputtd" style="height:72px">
					   <input type="text" id="rpremark" value="${remark}" class="ptdinput" style="height:-webkit-fill-available" />
				   </td>
				  <td class="ctdtitle"></td>
				  <td class="ctdtitle"></td>
				</tr>									
			 </tbody>
		 </table>			
	 </form>
	  <hr class="ctdhr"/>
	 <div class="contractbutton parents-content">
	 <div class="pdepartsubmit crm-confirmbt" onclick="roomedit()">提交</div>
	 <div class="pdepartcancle crm-cancelbt" onclick="window.parent.JDialog.close()">取消</div>
	 </div>
	   <script src="<%=request.getContextPath()%>/script/common/ui.datepicker.js"></script>
	   <script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
	   <script src="<%=request.getContextPath()%>/script/ipms/js/pmsmanage/room/roomedit.js"></script>	
	   <script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>