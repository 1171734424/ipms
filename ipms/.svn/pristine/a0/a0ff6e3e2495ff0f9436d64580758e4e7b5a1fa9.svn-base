<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/script.jsp"%>
<%
	Object sysfuncs = request.getAttribute("systemfunctions");
	Object relations = request.getAttribute("relations");
	Object subSystemNames = request.getAttribute("subSystemNames");
	Object rightconstants = request.getAttribute("rightconstants");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>员工权限设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/initialcss/roledialog.css"/>
		<style type="text/css">
			.ui-jqdialog-content {
			    height: 490px;
			}
		</style>
		<script>
			var systemfuncitons = JSON.parse('<%=sysfuncs%>');
			var relations = JSON.parse('<%=relations%>');
			var subSystemNames = JSON.parse('<%=subSystemNames%>');
			var rightconstants = JSON.parse('<%=rightconstants%>');
		</script>
	</head>
	<body>
		<div class="well" style="height:540px;">
			<fieldset>
                <legend style = "margin-bottom: 5px;">权限详情展示</legend>
                <div class="ui-jqdialog-content ui-widget-content">
			<input type="hidden" id="ROLEID" name="ROLEID" value="${ role.roleId }"/>
			<input type="hidden" id="BRANCHIDINROLE" name="BRANCHIDINROLE" value="${ (role.branchId == null || role.branchId == '' )  ? loginData.staff.branchId : role.branchId}"/>
			<form name="rightForm" id="rightForm" style="height:100%;overflow:auto;position:relative;">
				<table class="ui-corner-all SubmitTable">
					<tr class="tinfo">
						<td class="InnerTD">
							<div class="form-group">
                          		<label class="col-sm-2 control-label inputFloat paddingStyle" for="staffName">&nbsp;角色名称&nbsp;</label>
                          		<div class="col-sm-4">
                             		<input class="form-control inputFloat" name="ROLENAME" id="ROLENAME" type="text" value="${ role.roleName }" />
                          		</div>
                           </div>
                           <div class="form-group">
                          		<label class="col-sm-2 control-label inputFloat paddingStyle" for="staffName">&nbsp;备注&nbsp;</label>
                          		<div class="col-sm-4">
                             		<input class="form-control inputFloat" name="REMARK" id="REMARK" type="text" value="${ role.remark }" style="width:687px" />
                          		</div>
                           </div>
			            </td>
					</tr>
				</table>
				<table class="ui-corner-all EditTable" cellspacing="0" cellpadding="0" width="98.5%">
					<tbody>
						<tr id="formError" style="display: none;">
							<td class="ui-state-error" colspan="3"></td>
						</tr>
						<tr>
							<td class="CaptionTD" style="text-align:center;font-weight: bold;">
								<input name="chooseAll" id="chooseAll" type="checkbox"/>子系统
							</td>
							<td class="InnerCaptionTD" width="760px">
								<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
									<tr style="border-bottom: 1px solid;">
										<td class="CaptionTD" width="120px" style="text-align:center;font-weight: bold;">模块</td>
										<td class="CaptionTD" width="640px" style="text-align:center;font-weight: bold;">功能</td>
									</tr>
								</table>
							</td>
						</tr>
						<c:forEach items="${ map }" var="varmap">
							<tr class="formData">
								<td class="CaptionTD" width="120px"  style="text-align:center;">	
								<script>
									document.write(subSystemNames["${ varmap.key }"]);
								</script>
								</td>
								<td class="InnerCaptionTD" width="760px">
									<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
							   			<c:forEach items="${ modelconfigs }" var="modelconfig">
											<c:if test="${ modelconfig.value.show && modelconfig.value.subSystem == varmap.key }">
								        		<tr>
													<td class="CaptionTD" width="120px">
														<input class="FormElement" name="model" type="checkbox" 
														value="${ modelconfig.value.modelId }" onclick="selectChildren(this);"/>
														${ modelconfig.value.modelName }
													</td>
													<td class="InnerCaptionTD" width="640px">
														<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
															<c:forEach items="${ modelconfig.value.pageConfigs }" var="pageconfig" varStatus="status">
																<c:if test="${ !pageconfig.value.tab }">
																	<tr style="border-bottom: 1px solid;">
																		<td class="CaptionTD" colspan="${ pageconfig.value.tabs == null ? 2 : 1 }"
																			width="${ pageconfig.value.tabs == null ? 240 : 120 }px">
																			<input class="FormElement" name="page" type="checkbox" tabs="${ pageconfig.value.tabs != null }"
																				value="${ pageconfig.value.pageId }" onclick="selectChildren(this);"/>
																			${ pageconfig.value.name }
																		</td>
																		<c:if test="${ pageconfig.value.tabs != null }">
																			<td class="InnerTableTD" colspan="2">
																				<table class="InnerTable" id="${ pageconfig.value.pageId }" 
																					cellspacing="0" cellpadding="0" height="100%">
																				</table>
																			</td>
																		</c:if>
																		<c:if test="${ pageconfig.value.tabs == null }">
																			<td class="InnerTD" colspan="1" width="400px">
																				<c:forEach items="${ pageconfig.value.functions }" var="function" varStatus="funcstatus">
																					<input class="FormElement" name="function" type="checkbox" value="${ function.key }"/>
																					<script>
																						document.write(systemfuncitons["${ function.key }"]);
																					</script>
																				</c:forEach>
																			</td>
																		</c:if>
																	</tr>
																</c:if>
															</c:forEach>
															<c:forEach items="${ modelconfig.value.pageConfigs }" var="pageconfig" varStatus="status">
																<c:if test="${ pageconfig.value.tab }">
																	<script>
																		var subPageId = "${ pageconfig.value.pageId }";
																		var containerId = "${ pageconfig.value.containerId }";
																		var tr = "<tr><td class='CaptionTD' width='115px'><input class='FormElement' name='page' type='checkbox'" 
																				+ " value= '" + subPageId + "' sub='true' onclick='selectChildren(this);'/>"
																				+ '&nbsp;' + "${ pageconfig.value.name }</td><td class='InnerTD' id='" + subPageId + "'></td></tr>";
																		$(tr).appendTo($("#" + containerId));
																		
																		var subFunc;
																		<c:forEach items="${ pageconfig.value.functions }" var="func" varStatus="funcstatus">
																			subFunc = "<span><input class='FormElement' name='function' parent='" + subPageId + "' type='checkbox'"
																					+ " value='${ func.key }'/>" + '&nbsp;' + systemfuncitons["${ func.key }"] + '&nbsp;' + "</span>";
																			$(subFunc).appendTo($("#" + subPageId));
																		</c:forEach>	
																	</script>
																</c:if>
															</c:forEach>
														</table>
													</td>
												</tr>
											</c:if>
										</c:forEach>
									</table>
								</td>
							</tr>		
						</c:forEach>
						<!-- <c:if test="${ rightconstants != null && !empty rightconstants }">
							<tr class="formData">
								<td class="CaptionTD" width="120px">
									<input class="FormElement" name="model" type="checkbox" onclick="selectChildren(this);"/>
									业务权限
								</td>
								<td class="InnerCaptionTD" width="640px">
									<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
										<c:forEach items="${ rightconstants }" var="constant">
											<tr class="formData">
												<td class="InnerTD">
													<input class="FormElement" name="page" tabs="false" type="checkbox" onclick="selectChildren(this)" value="${ constant.key }"/>
													<script>
														document.write(rightconstants["${ constant.key }"]);
													</script>
												</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:if> -->
						<c:if test="${ rightconstants != null && !empty rightconstants }">
							<tr class="formData">
								<td class="CaptionTD" width="120px">
									<input class="FormElement" name="model" type="checkbox" onclick="selectChildren(this);"/>
									业务权限
								</td>
								<td class="InnerCaptionTD" width="760px">
									<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
										<c:forEach items="${ rightconstants }" var="rightconstant">
											<c:if test="${ !empty rightconstant.listrights }">
												<tr>
													<td class="CaptionTD" width="120px">
														<input class="FormElement" name="model" type="checkbox" 
														value="${rightconstant.model}" onclick="selectChildren(this);"/>
														${ rightconstant.model }
													</td>
													<td class="InnerCaptionTD" width="640px">
														<table class="InnerTable" cellspacing="0" cellpadding="0" height="100%">
															<c:forEach items="${ rightconstant.listrights }" var="right" varStatus="status">
																<c:if test="${ true }">
																	<tr style="border-bottom: 1px solid;">
																		<td class="CaptionTD" colspan="${ right == null ? 2 : 1 }"
																			width="${ right == null ? 240 : 120 }px">
																			<input class="FormElement" name="page" type="checkbox" tabs="${ true }"
																				value="${ right.righttype }" onclick="selectChildren(this);"/>
																			${ right.name }
																		</td>
																	</tr>
																</c:if>
															</c:forEach>
														</table>
													</td>			
												</tr>
											</c:if>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</form>
		</div> 
            </fieldset>
            <div style="display: none;">
				<c:forEach items="${staffList}" var="sl">
					<form id="userForm" class="form-horizontal"   action="">
	                    <fieldset>
	                        <legend>权限分配</legend>
	                        <input class="form-control" id="dataId" name="dataId" type="hidden" value="${userrole.dataId}"/>
	                        <input class="form-control" id="userId" name="userId" type="hidden" value="${sl.staff_id}"/>
	                        <input class="form-control" id="roleId" name="roleId" type="hidden" value="${role.roleId}"/>
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" for="staffName">员工姓名</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="staffName" name="staffName" type="text"  value="${sl.staff_name}" readonly/>
	                          </div>
	                       </div>
	                       <div class="form-group formInput">
	                          <label class="col-sm-2 control-label" >权限</label>
	                          <div class="col-sm-4">
	                             <input class="form-control" id="roleNames" name="roleNames" type="text"  value="${ role.roleName }"/>
	                          </div>
	                       </div>
	                    </fieldset>     
	                </form>
	            </c:forEach>
            </div>
         </div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<!--<script src="<%=request.getContextPath()%>/script/initial/bwizard.min.js"></script>-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	$(document).ready(function() {
	    		$("#ROLENAME").change(function(){
	    			$("#roleNames").val($("#ROLENAME").val());
	    		});
	    		
	    		$("#chooseAll").click(function(){
	        		var xz = $(this).prop("checked");
	       			 var ck = $(".well input[type='checkbox']").prop("checked",xz);
	        	});
        	});
     		
     		
			function init() {
				if ($("#ROLEID").val()) {
					var func, datarights;
					$.each(relations, function(index) {
						func = $(":checkbox[value=" + relations[index].funcrightId + "]");
						func.prop("checked", true);
						
						selectParent(func, $(func).attr("sub") == "true", true);
						
						if(relations[index].datarights) {
							datarights = relations[index].datarights.split(",");
							$.each(datarights, function(i) {
								$(func).parent().parent().find(":checkbox[name=function][value=" + datarights[i] + "]").prop("checked", true);
							});
						}
					});
				}
			}
			init();
			
			function selectChildren(obj) {
				var name = $(obj).attr("name"), checked = $(obj).prop("checked"), el = "", tabEl = "";
				if (name == "page") {
					var hasTabs = $(obj).attr("tabs") == "true",
						isSub = $(obj).attr("sub") == "true";
					if (isSub) {
						el = "[name=function]";
					} else {
						tabEl = "[tabs]";
					}
					
					var deSelect = $(obj).parent().parent().parent().find(":checkbox[name=page]" + tabEl + ":checked");
					if (checked || deSelect.length == 0) {
						selectParent(obj, isSub, checked);
					}
				}
				$(obj).parent().parent().find(":checkbox" + el).prop("checked", checked);
			}
			
			function selectParent(obj, isSub, checked) {
				var parent = $(obj).parent().parent().parent().parent().parent().prev().find(":checkbox");
				if (parent.length > 0) {
					parent.prop("checked", checked);
					if (isSub) {
						var deSelect = parent.parent().parent().parent().find(":checkbox[name=page][tabs]:checked");
						if (checked || deSelect.length == 0) {
							selectParent(parent, isSub, checked);	
						}
					}
				}
			}
			
			function concatRights() {
				var rightRelation;
				var pages = $(":checkbox[name=page]:checked"), functions, funcs;
				
				rights = new Array();
				role = new Object();
				if($("#ROLEID").val()) {
					role["ROLEID"] = $("#ROLEID").val();
				}
				role["ROLENAME"] = $("#ROLENAME").val();
				role["REMARK"] = $("#REMARK").val();
				role["BRANCHID"] = $("#BRANCHIDINROLE").val();
				
				if (pages.length > 0) {
					$.each(pages, function(index) {
					 	rightRelation = new Object();
						funcs = "";
						if(!$(pages[index]).attr("tabs") || $(pages[index]).attr("tabs") == "false") {
							functions = $(pages[index]).parent().parent().find(":checkbox[name=function]:checked");
							if (functions.length > 0) {
								$.each(functions, function(i) {
									funcs += $(functions[i]).val() + (i == functions.length - 1 ? "" : ",");
								});
							}
						}
						
						/*if($("#ROLEID").val()) {
							rightRelation["ROLEID"] = $("#ROLEID").val();
						}*/
						rightRelation["FUNCRIGHTID"] = $(pages[index]).val();
						rightRelation["DATARIGHTS"] = funcs;
						rights.push(rightRelation);
					});
				}
			}
	</script>
	</body>
</html>

