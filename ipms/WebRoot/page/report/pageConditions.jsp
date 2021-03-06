﻿﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${pageConfig.conditions != null}">
	<div class="reportform_condition">
		<!-- 查询条件 -->
		<div class="table_cond">
			<ul class="ul">
				<!--<span>${pageConfig.seperators.startdatespan}</span>-->
				<c:if test="${pageConfig.conditions.STARTDATE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_STARTDATE"  type="text" placeholder="起始时间"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.STARTLASTMDATE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_STARTLASTMDATE" type="text" placeholder="起始时间"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.STARTMONTH != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_STARTMONTH"  type="text" placeholder="开始月份"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ENDMONTH != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_ENDMONTH"  type="text" placeholder="截止月份"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ENDDATE != null}">
					<li class="li"> 
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_ENDDATE"  type="text" placeholder="截止时间"/>
							</div>
						</div>
					</li>
				</c:if>				
				<c:if test="${pageConfig.conditions.ENDTIME!= null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_ENDTIME" type="text" placeholder="截止日期"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MODELID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_MODELID" type="text" placeholder="模块编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PAGEID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_PAGEID" type="text" placeholder="页面编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRICEBRANCHNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_PRICEBRANCHNAME" type="text" <c:if test="${ branch.rank == 0 }"> placeholder="${ branch.branchName }" value=""</c:if> <c:if test="${ branch.rank == 1 }">value="${ branch.branchName }"</c:if> />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.STAFFNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_STAFFNAME" type="text" placeholder="员工姓名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSESTAFFNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_HOUSESTAFFNAME" type="text" placeholder="员工姓名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSESNAMENEW != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_HOUSESNAMENEW" type="text" placeholder="民宿名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.STAFFUSERNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_STAFFUSERNAME" type="text" placeholder="编号/姓名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.LOGINNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_LOGINNAME" type="text" placeholder="登录名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBERNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MEMBERNAME" type="text" placeholder="会员名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODBRANCHID != null}">
					<li class="li conditionNoshow">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODBRANCHID" type="text" placeholder="门店名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.IDCARD != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_IDCARD" type="text" placeholder="身份证"/>
							</div>
						</div>
					</li>
				</c:if>	
				<c:if test="${pageConfig.conditions.INVERSTNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_INVERSTNAME" type="text" placeholder="申请人"/>
							</div>
						</div>
					</li>
				</c:if>	
				<c:if test="${pageConfig.conditions.INVERSTMOBILE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_INVERSTMOBILE" type="text" placeholder="手机号"/>
							</div>
						</div>
					</li>
				</c:if>	
			<c:if test="${pageConfig.conditions.INVERSTSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_INVERSTSTATUS">
								    <option value="1">未处理</option>
								    <option value="2">已处理</option>
							    </select>
							</div>
						</div>
					</li>		
				</c:if>
				<c:if test="${pageConfig.conditions.IDCARDCOMPANY != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_IDCARDCOMPANY" type="text" placeholder="营业执照"/>
							</div>
						</div>
					</li>
				</c:if>	
				<c:if test="${pageConfig.conditions.ROLENAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROLENAME" type="text" placeholder="角色名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.DEPARTNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_DEPARTNAME" type="text" placeholder="部门名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBER_RANK != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MEMBER_RANK" type="text" placeholder="会员等级"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBER_RANK_NAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MEMBER_RANK_NAME" type="text" placeholder="等级/名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBER_ORDER_ID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MEMBER_ORDER_ID" type="text" placeholder="订单编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBER_CHECK_NAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MEMBER_CHECK_NAME" type="text" placeholder="预订人姓名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MOBILE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_MOBILE" type="text" placeholder="手机"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SMSTEMPLATETYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SMSTEMPLATETYPE" type="text" placeholder="业务类型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSENAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_HOUSENAME" type="text" placeholder="民宿名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSENO != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_HOUSENO" type="text" placeholder="门牌号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.MEMBERRANK != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_MEMBERRANK">
								    <option value="">会员等级</option>
<!-- 								     <option value="0">公司会员 </option>
 -->								    <option value="1">非会员</option>
								    <option value="2">注册会员</option>
								    <option value="3">银卡会员</option>
								    <option value="4">金卡会员</option>
								    <option value="5">铂金会员</option>
								    <option value="6">黑钻会员</option>
							    </select>
							</div>
						</div>
					</li>		
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSESTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_HOUSESTATUS">
								    <option value="">状态</option>
									<option value="0">关闭房间 </option>
								    <option value="1">空房</option>
								    <option value="2">预定</option>
								    <option value="3">已入住</option>
								    <option value="T">停售</option>
								    <option value="Z">脏房</option>
								    <option value="W">维修</option>
								    <option value="O">其他 </option>
							    </select>
							</div>
						</div>
					</li>		
				</c:if>
				<c:if test="${pageConfig.conditions.SMSTEMPLATESTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_SMSTEMPLATESTATUS">
								    <option value="">启用状态</option>
								    <option value="0">未启用</option>
								    <option value="1">启用</option>
							    </select>
							</div>
						</div>
					</li>		
				</c:if>
				<c:if test="${pageConfig.conditions.SMSSENDSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SMSSENDSTATUS" type="text" placeholder="发送状态"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.AUDITRECORDUSER != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_AUDITRECORDUSER" type="text" placeholder="申请人"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.CATEBRANCHID != null}">
					<li class="li conditionNoshow">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_CATEBRANCHID" type="text" placeholder="归属门店"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.CATEGORYNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_CATEGORYNAME" type="text" placeholder="商品种类"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODSID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODSID" type="text" placeholder="商品编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODSNAMES != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODSNAMES" type="text" placeholder="商品编号/名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PAYTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_PAYTYPE">
								    <option value="">支付方式</option>
								    <option value="1">挂房账</option>
								    <option value="2">现金</option>
								    <option value="3">银行卡</option>
								    <option value="4">微信</option>
								    <option value="5">支付宝</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ROOMPAYTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_ROOMPAYTYPE">
								    <option value="">结账方式</option>
								    <option value="1">现金</option>
								    <option value="2">银行卡</option>
								    <option value="3">预授权</option>
								    <option value="4">支付宝</option>
								    <option value="5">微信</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ROOMID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROOMID" type="text" placeholder="房间编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.TYPENAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_TYPENAME" type="text" placeholder="房间类型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODSNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODSNAME" type="text" placeholder="商品名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SUPERCATEGORY != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SUPERCATEGORY" type="text" placeholder="商品大类"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODCATEGORYID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODCATEGORYID" type="text" placeholder="商品类型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.GOODCATEGORYID_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_GOODCATEGORYID" type="text" placeholder="商品类型"/>
								<input id="cond_GOODCATEGORYID_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.POSTNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_POSTNAME" type="text" placeholder="职务名"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SALETYPE_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SALETYPE" type="text" placeholder="售卖类型"/>
								<input id="cond_SALETYPE_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				 <c:if test="${pageConfig.conditions.CHARGEROOM != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CHARGEROOM">
								    <option value="">是否自营</option>
								    <option value="1">自营</option>
								    <option value="0">非自营</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.STATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_STATUS">
								    <option value="1">有效/上架</option>
								    <option value="2">有效/下架</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.MEMBERSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_MEMBERSTATUS">
								    <option value="1">有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.ROOMSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_ROOMSTATUS">
								    <option value="1">在住</option>
								    <option value="2">离店</option>
								    <option value="3">已退未结</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.AUDITBRANCHID != null}">
					<li class="li conditionNoshow">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_AUDITBRANCHID" type="text" placeholder="归属门店"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.TABLEAUDITTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_TABLEAUDITTYPE">
									<option value="">所有类型</option>
									<option value="维修申请">维修申请</option>
									<option value="退房申请">退房申请</option>
									<option value="房租价申请">房租价申请</option>
									<!-- <option value="采购申请">采购申请</option> -->
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.TABLEAUDITLOGTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_TABLEAUDITLOGTYPE">
									<option value="">所有类型</option>
									<option value="4">维修申请</option>
									<option value="3">退房申请</option>
									<option value="2">房租价申请</option>
									<!-- <option value="1">采购申请</option> -->
								</select>
							</div>
						</div>
					</li>
				</c:if>
				 <c:if test="${pageConfig.conditions.AUDITSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_AUDITSTATUS">
								    <option value="">审核状态</option>
								    <option value="0">已删除</option>
								    <option value="1">待审核</option>
								    <option value="2">审核通过</option>
								    <option value="3">修改</option>
								    <option value="4">作废</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.CAMPAIGNTIME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_CAMPAIGNTIME"  type="text" placeholder="活动时间"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PROMOTIONTIME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_PROMOTIONTIME"  type="text" placeholder="促销时间"/>
							</div>
						</div>
					</li>
				</c:if>
				
				<c:if test="${pageConfig.conditions.BUSINESSTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_BUSINESSTYPE">
									<option value="">业务类型</option>
									<option value="1">推荐有礼</option>
									<option value="2">开卡送抵用劵</option>
									<option value="3">充值满送</option>
									<option value="4">首住优惠价</option>
									<option value="5">折上折</option>
									<option value="6">限时特价</option>
									<option value="7">积分送优惠券</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRIORITY != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_PRIORITY">
									<option value="">优先级</option>
									<option value="0">普通</option>
									<option value="1">中级</option>
									<option value="2">高级</option>
									<option value="3">超高级</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.USINGRANGE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_USINGRANGE">
									<option value="">适用范围</option>
									<option value="0">所有</option>
									<option value="1">线上</option>
									<option value="2">线下</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.USINGPERSON != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_USINGPERSON" type="text" placeholder="适用对象"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.USINGTYPE_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_USINGTYPE" type="text" placeholder="适用类型"/>
								<input id="cond_USINGTYPE_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.TOTALPRICE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_TOTALPRICE" type="text" placeholder="面额"/>
							</div>
						</div>
					</li>
				</c:if>
				 <c:if test="${pageConfig.conditions.COUPONSTATUS!= null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_COUPONSTATUS">
								    <option value="1" selected>有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.PROSTATUS!= null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_PROSTATUS">
								    <option value="1" selected>有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.ROOMTHEME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_ROOMTHEME">
								    <option value="">场景</option>
								    <option value="1">酒店</option>
								    <option value="2">公寓</option>
								    <option value="3">民宿</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
			  <c:if test="${pageConfig.conditions.ROOMBRANCH != null}">
					<li class="li conditionNoshow">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROOMBRANCH" type="text" placeholder="门店"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRICEROOMTHEME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_PRICEROOMTHEME">
								    <option value="">场景</option>
								    <option value="1">酒店</option>
								    <option value="2">公寓</option>
								    <!--<option value="3">民宿</option>-->
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRICETYPEH != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_PRICETYPEH">
								    <option value="">价格类型</option>
								    <option value="1">日租</option>
								    <option value="2">时租</option>
								    <option value="3">长租</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRICEROOMTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_PRICEROOMTYPE" type="text" placeholder="房型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ROOMTYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROOMTYPE" type="text" placeholder="房型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSETYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_HOUSETYPE" type="text" placeholder="房型"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PRICETYPE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_PRICETYPE">
								    <option value="">所有状态</option>
								    <option value="基准">基准</option>
								    <option value="调整">调整</option>
								    <!--<option value="3">待审核</option>
								    <option value="4">打回</option>
								    <option value="5">驳回</option>
								    <option value="0">失效</option>
								    
							    --></select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RMSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_RMSTATUS" type="text" placeholder="状态"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RMID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_RMID" type="text" placeholder="房号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RMFLOOR != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_RMFLOOR" type="text" placeholder="楼层"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SUPPLIERNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SUPPLIERNAME" type="text" placeholder="供应商"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.IP != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_IP" type="text" placeholder="IP"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.CONTRACT != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_CONTRACT" type="text" placeholder="联系人"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RPID != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_RPID" type="text" placeholder="房价码"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.CITY != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_CITY" type="text" placeholder="城市"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.DISTRICT != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_DISTRICT" type="text" placeholder="区"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.STREET != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_STREET" type="text" placeholder="街道"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.CIRCLE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_CIRCLE" type="text" placeholder="商业圈"/>
							</div>
						</div>
					</li>
				</c:if>
				
				<c:if test="${pageConfig.conditions.THEMEAREA != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_THEMEAREA">
								    <option value="">场景</option>
								    <option value="1">酒店</option>
								    <option value="2">公寓</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
				
				<c:if test="${pageConfig.conditions.ROOMTYPEAREA != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROOMTYPEAREA" type="text" placeholder="房型码"/>
							</div>
						</div>
					</li>
				</c:if>
				
				<c:if test="${pageConfig.conditions.ROOMNAMEAREA != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ROOMNAMEAREA" type="text" placeholder="房型名称"/>
							</div>
						</div>
					</li>
				</c:if>
				
				
				<c:if test="${pageConfig.conditions.RPKIND != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_RPKIND">
								    <option value="">所有房价</option>
								    <option value="1">房租</option>
								    <option value="2">时租</option>
								    <option value="3">月租</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RPIDSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_RPIDSTATUS">
								    <option value="">房价码类型</option>
								    <option value="1">基准</option>
								    <option value="2">折扣</option>
								    <option value="3">固定</option>
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.OPERMODULE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_OPERMODULE">
<!--								    <option value="">操作类型</option>-->
<!--								    <option value="1">添加</option>-->
<!--								    <option value="2">删除</option>-->
<!--								    <option value="3">修改</option>-->
<!--								    <option value="4">充值</option>-->
<!--								    <option value="5">入住操作</option>-->
<!--								    <option value="7">保洁处理</option>-->
							    </select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.RPSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select  id="cond_RPSTATUS">
								    <option value="">所有状态</option>
								    <option value="1">基准</option>
								    <option value="2">调整</option>
								    <!--<option value="3">待审核</option>
								    <option value="4">打回</option>
								    <option value="5">驳回</option>
								    <option value="0">失效</option>
								    
							    --></select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ZT != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_ZT">
									<option value="">所有</option>
								 	<option value="1">正常员工</option>
									<option value="2">自营</option>
									<option value="3">加盟</option>
							    </select>
							</div>
						</div>
					</li>
			  </c:if>
			  <c:if test="${pageConfig.conditions.SUPPILERSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_SUPPILERSTATUS">
								    <option value="1">有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.CATEGORYSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CATEGORYSTATUS">
								    <option value="1">有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.CBSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CBSTATUS">
								    <option value="1">有效</option>
								    <option value="0">无效</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			     <c:if test="${pageConfig.conditions.CASHBOX != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CASHBOX">
								    <option value="">请选择钱柜</option>
								    <option value="A">钱柜A</option>
								    <option value="B">钱柜B</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			     <c:if test="${pageConfig.conditions.CASHSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CASHSTATUS">
								    <option value="">请选择状态</option>
								    <option value="0">待用</option>
								    <option value="1">在用</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
	<!-- ********ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd**************查询************************ -->  
			    <c:if test="${pageConfig.conditions.ECATEGORYNAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ECATEGORYNAME" type="text" placeholder="设备类型"/>
							</div>
						</div>
					</li>
				</c:if>
			    <c:if test="${pageConfig.conditions.SUPERKIND_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_SUPERKIND" type="text" placeholder="父类"/>
								<input id="cond_SUPERKIND_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
			    <c:if test="${pageConfig.conditions.EKINDSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_EKINDSTATUS">
								    <option value="1">有效</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.ESERIALNO != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ESERIALNO" type="text" placeholder="序列号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EQUIPCATEGORY_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EQUIPCATEGORY" type="text" placeholder="类型"/>
								<input id="cond_EQUIPCATEGORY_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EFLOOR != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EFLOOR" type="text" placeholder="楼层"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EROOM != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EROOM" type="text" placeholder="房间"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ECONCENTRATOR_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ECONCENTRATOR" type="text" placeholder="采集器"/>
								<input id="cond_ECONCENTRATOR_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EROUTE_CUSTOM_VALUE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EROUTE" type="text" placeholder="路由器"/>
								<input id="cond_EROUTE_CUSTOM_VALUE" type="hidden" />
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EQUIPIPNUM != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EQUIPIPNUM" type="text" placeholder="连接IP"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.EMAINTPERSON != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_EMAINTPERSON" type="text" placeholder="运维人"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ESTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_ESTATUS">
								    <option value="">状态</option>
								    <option value="0">已删除</option>
								    <option value="1">工作正常</option>
								    <option value="2">工作异常</option>
								    <option value="3">无连接</option>
								    <option value="4">维修</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.EISOPEN != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_EISOPEN">
									<option value="">开关</option>
								    <option value="1">开</option>
								    <option value="0">关</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.ERSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_ERSTATUS">
								    <option value="3">未修复</option>
								    <option value="4">已修复</option>
								    <option value="0">已删除</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.EEQIPMENT != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_EEQIPMENT">
								    <option value="">设备类型</option>
								    <option value="9">门锁</option>
								    <option value="7">水表</option>
								    <option value="8">电表</option>    
								    <option value="10">采集器</option>
								    <option value="11">路由器</option>
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			    <c:if test="${pageConfig.conditions.EWARNLOGSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_EWARNLOGSTATUS">
								    <option value="1">未阅读</option>
								    <option value="2">已阅读</option>    
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			   <c:if test="${pageConfig.conditions.ADMINICODE != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ADMINICODE" type="text" placeholder="编码"/>
							</div>
						</div>
					</li>
				</c:if>
				 <c:if test="${pageConfig.conditions.ADMININAME != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ADMININAME" type="text" placeholder="名称"/>
							</div>
						</div>
					</li>
				</c:if>
			    <c:if test="${pageConfig.conditions.ORDERNUMBER != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<input id="cond_ORDERNUMBER" type="text" placeholder="推荐排序"/>
							</div>
						</div>
					</li>
				</c:if>
			    <c:if test="${pageConfig.conditions.CITYRANK != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CITYRANK">
								    <option value="">级别</option>
								    <option value="1">市</option>
								    <option value="2">区</option>
								    <option value="3">街道</option>    
								    <option value="4">商业圈</option>
								    <option value="5">学校</option>
								    <option value="6">景点</option>
								    <option value="7">地铁线路</option>
								    <option value="8">地铁站</option>
							
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			     <c:if test="${pageConfig.conditions.CITYSTATUS != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CITYSTATUS">
								    <option value="1">有效</option>
								    <option value="0">已删除</option>
							
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
			     <c:if test="${pageConfig.conditions.CITYREMARK != null}">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea" >
								<select id="cond_CITYREMARK">
								    <option value="">热门城市</option>
								    <option value="1">是</option>
								    <option value="0">否</option>
							
							    </select>
							</div>
						</div>
					</li>
			    </c:if>
<!--******************************************评选页面查询框及下拉**********************************************  -->
			    <c:if test="${pageConfig.conditions.PROBRANDTYPEID != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_PROBRANDTYPEID" type="text" placeholder="品宣名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PROBRANDTYPESTATUS != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_PROBRANDTYPESTATUS">
									<option value="1">有效</option>
									<option value="0">删除</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PROBRANDNAME != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_PROBRANDNAME" type="text" placeholder="品宣名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.PROBRANDSTATUSID != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_PROBRANDSTATUSID">
									<option value="2">已审核</option>
									<option value="1">待审核</option>
									<option value="0">删除</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ORDERID != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_ORDERID" type="text" placeholder="订单编号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SHORTCUTNAME != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_SHORTCUTNAME" type="text" placeholder="标签名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.SHORTCUTSTATUS != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_SHORTCUTSTATUS">
									<option value="1">有效</option>
									<option value="0">删除</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSEACCOUNTNAME != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_HOUSEACCOUNTNAME" type="text" placeholder="账号"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.HOUSEMANAGER != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_HOUSEMANAGER" type="text" placeholder="管理员"/>
							</div>
						</div>
					</li>
				</c:if>
<!--******************************************基础资料中的订单来源**********************************************  -->				
				<c:if test="${pageConfig.conditions.ORDERSOURCENAME != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<input id="cond_ORDERSOURCENAME" type="text" placeholder="来源名称"/>
							</div>
						</div>
					</li>
				</c:if>
				<c:if test="${pageConfig.conditions.ORDERSOURCESTATUS != null }">
					<li class="li">
						<div class="wrapper">
							<div class="inputArea">
								<select id="cond_ORDERSOURCESTATUS">
									<option value="1">有效</option>
									<option value="0">删除</option>
								</select>
							</div>
						</div>
					</li>
				</c:if>
<!--******************************************查询**********************************************  -->
				<li class="li">
					<div class="wrapper">
						<a class="b_button_org"><span class="b_query" onclick="queryPageData();">查 询</span></a>
					</div>
				</li>
		</div>
	</div>
</c:if>