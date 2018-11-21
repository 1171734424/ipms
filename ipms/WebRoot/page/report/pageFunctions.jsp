﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${pageConfig.functions != null}">
	<div class="reportform_function" >
		<!-- 功能模块 -->
		<div class="buttons">
			<ul class="ul">
				<c:if test="${sessionScope.LOGIN_USER.systemType == 1}">
					<li class="li">
						<a class="b_button"><span class="b_delete" onclick="window.top.removeTab();">关  闭</span></a>
					</li>
				</c:if>
				<li class="li">
					<a class="b_button"><span class="b_refresh" onclick="location.reload();" title="Alt+R">刷  新</span></a>
				</li>
				<c:if test="${pageConfig.functions.EDIT != null || pageConfig.functions.DELETE != null || pageConfig.delFunc != null || pageConfig.functions.SELECT != null }">
					<li class="li">
						<a class="b_button"><span class="b_select" onclick="multiSelect();" title="Alt+A">全  选</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.EDIT != null || pageConfig.functions.ADD != null}">
					<li class="li">
						<a class="b_button"><span class="b_add" onclick="addNewRow();" title="Alt+N/Q">新  增</span></a>
					</li>
				</c:if>
				<c:if test="${(pageConfig.functions.EDIT != null || pageConfig.functions.UPDATE != null || pageConfig.functions.RPSAVE != null) && pageConfig.editing != null && pageConfig.editing == 'line'}">
					<li class="li">
						<a class="b_button"><span class="b_save" onclick="callSaveFunc();" title="Alt+S">保  存</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.EDIT != null || pageConfig.functions.DELETE != null}">
					<li class="li">
						<a class="b_button"><span class="b_delete" onclick="deleteRow();" title="Alt+D/E">删  除</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.PRINT != null}">
					<li class="li">
						<a class="b_button"><span class="b_print" onclick="printTemplate();">打  印</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.EXCEL != null}">
					<li class="li">
						<a class="b_button"><span class="b_excel" onclick="exportFile();">EXCEL</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.IMPORT != null}">
					<li class="li">
						<a class="b_button"><span class="b_import" onclick="importFile();">导入</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.charts != null}">
					<c:forEach items="${ pageConfig.charts }" var="chart">
						<c:if test="${ chart.key == 'Column' }">
							<li class="li">
								<span class="btn_column" onclick="loadChart('column2d');" title="柱状图"></span>
							</li>
						</c:if>
						<c:if test="${ chart.key == 'Pie' }">
							<li class="li">
								<span class="btn_pie" onclick="loadChart('pie2d');" title="占比图"></span>
							</li>
						</c:if>
						<c:if test="${ chart.key == 'Line' }">
							<li class="li">
								<span class="btn_line" onclick="loadChart('line');" title="趋势图"></span>
							</li>
						</c:if>
					</c:forEach>
				</c:if>
				
				<!-- **************************************************自定义按钮********************************************************* -->
				<c:if test="${pageConfig.functions.TEMPLATEADD != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="addSmsTemplatePage();">模板新增</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.CAMPAIGHSADD != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="addCampaignsPage();">活动新增</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.RPAPPLY != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="rpApply();">房租申请</span></a>
					</li>
				</c:if>

				<%-- <c:if test="${pageConfig.functions.RPAPPLYPRICE != null}">
					<li class="li">
						<a class="b_button" style="width:88px;"><span class="b_add" onclick="rpapplyprice();">房价调整</span></a>
					</li>
				</c:if>  
				<c:if test="${pageConfig.functions.RPAPPLYPRICEHOUR != null}">
					<li class="li">
						<a class="b_button" style="width:88px;"><span class="b_add" onclick="rpapplypricehour();">时租调整</span></a>
					</li>
				</c:if> --%>  
				<c:if test="${pageConfig.functions.ADDCONTRART != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="addContrart();">合同新增</span></a>
					</li>
				</c:if>
				<!-- **************************************************设备********************************************************* -->
				<c:if test="${pageConfig.functions.ADDREPAIRRECORD != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="addEquipRepairDetail();">维修新增</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.EQUIPSYNCHRO != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="dataSynchro();">数据同步</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.DOOREQUIPSYNCHRO != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="doorDataSynchro();">门锁同步</span></a>
					</li>
				</c:if>	
				<c:if test="${pageConfig.functions.ADDDOORRIGHT != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="doorAddUser();">添减授权</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.COPYHOUSE != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_add" onclick="copyhouse();">复制房源</span></a>
					</li>
				</c:if>
				<c:if test="${pageConfig.functions.IMPORTDOOR != null}">
					<li class="li">
						<a class="b_button" style="width:78px;"><span class="b_import" onclick="importDoorFile();">门锁导入</span></a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</c:if>

