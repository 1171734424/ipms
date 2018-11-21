<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<%
	request.setAttribute("basePath", request.getContextPath());
	request.setAttribute("roomtype", request.getAttribute("roomtype"));
	request.setAttribute("theme", request.getAttribute("theme"));
	request.setAttribute("roomprice", request.getAttribute("roomprice"));
	request.setAttribute("guarantee", request.getAttribute("guarantee"));
	request.setAttribute("rpsetup", request.getAttribute("rpsetup"));
	request.setAttribute("outlineroomprice", request.getAttribute("outlineroomprice"));
	request.setAttribute("discount", request.getAttribute("discount"));
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>预订页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/tipInfo.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/common/jquery-dialog.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/jquery.mCustomScrollbar.min.css" />
	 	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/select/select.css" />
	 	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<link rel="stylesheet" id="style"  href="<%=request.getContextPath()%>/css/fontawesome/css/font-awesome.min.css" />
	</head>
	<body>
		<div class="walk_margin" style="padding:0;background:#fff;border:none;">
			<input id="roomprice" name="roomprice" type="hidden" />
			<input id="memberId" name="memberId" type="hidden" />
			<input id="memberrank" name="memberRank" type="hidden" />
		    <input id="rooprice_msj" name="rooprice_msj" type="hidden" value="${roomprice}"/>
		    <input id="guarantee_msj" name="guarantee_msj" type="hidden" value="${guarantee}"/>
		    <input id="outlineroomprice" name="outlineroomprice" type="hidden" value="${outlineroomprice}"/>
		    <input id="discount" name="discount" type="hidden" value="${discount}"/>
		    <input id="newMemberId" name="newMemberId" type="hidden">
			<!--<h3></h3>
			<span class="close_span">
				<i class="imooc left_imooc_order" style="color:#3B3E40;" onclick=window.parent.JDialog.close();>&#xe900;</i>
			</span>
			-->
			<div class="walk_form" style="height:640px;">
				<form action="" method="post" class="order_form order_form_later" >
					<section class="detail_one sonepa">
					<label class="label_title">
						查会员信息
					</label>
					<input id="memberselect" name="memberselect" type="text" value=""
						class="check check_one" placeholder="手机号/公司编号" onKeyUp=this.value = this.value.replace(/\D/g, ''); onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="11">
					<span role="button" class="button_margin addmember_order orange" onclick=addmember();>注册会员</span>
					<span role="button" class="button_margin submitbutton_order"onclick=selectmember();>查询</span>
					
					
					</section>
					<section class="detail_one">
					<label class="label_title">
						预订单信息
					</label>
					<ul class="clearfix">
						<li class="payscore">
							<label class="orlabel_name">
								场景
							</label>
							<select id="theme" name="theme" class="check order_input"
								 disabled>
								<c:forEach var="th" items="${theme}">
									<option value="${th.content}">
										${th.paramname}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							<label class="orlabel_name" id="arrive">
								抵店日期
							</label>
							<input id="arrivedate" name="arrivedate" type="text" value=""
								class="check date order_input" onblur="adtimejudge()">
						</li>
						<li>
							<label class="orlabel_name" id="leave">
								离店日期
							</label>
							<input id="leavedate" name="leavedate" type="text" value=""
								class="check ldate order_input">
						</li>
						<li>
							<label class="orlabel_name">
								房间类型
							</label>
							<select id="roomtype" name="roomtype" class="check order_input"
								onchange="newrtChange()">
								<option id="choosert" value="">
									房间类型
								</option>
								<c:forEach var="rt" items="${roomtype}">
									<option name="${rt.roomtype}" id="${rt.roombed}"
										value="${rt.roomtype}">
										${rt.roomname}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							<label class="orlabel_name">
								数量
							</label>
							<span onclick=subNum(); class="count jian">-</span>
							<input id="acount" name="acount" type="text " value="1" class="number" onKeyUp=this.value = this.value.replace(/\D/g, '') onafterpaste="this.value=this.value.replace(/\D/g,'')">
							<span onclick=addNum(); class="count jia">+</span>
						</li>
						
						<li>
							<label class="orlabel_name">房价码</label>
							<select id="rpid" name="rpid" class="check order_input" onchange="rpChange()" disabled>
								<option id="" value="">
									房价码
								</option>
								<c:forEach var="rp" items="${rpsetup}">
									<option name="${rp.PARAMNAME}" id="${rp.PARAMNAME}"
										value="${rp.PARAMNAME}">
										${rp.PARAMDESC}
									</option>
								</c:forEach>
							</select>
							<!--<input id="rpid" name="rpid" type="text" value="" class="check order_input"
								disabled>
							<input id="rpidid" name="rpidid" type="hidden" value=""class="check order_input" disabled>
						--></li>
						<li>
							<label class="orlabel_name">
								房价
							</label>
							<input id="price" name="price" type="text" value="" class="check order_input"
								disabled>
						</li>
						<li>
							<label class="orlabel_name">
								预订人
							</label>
							<input id="userorder" name="userorder" type="text" value=""
								class="check order_input" maxlength="8">
						</li>
						<li>
							<label class="orlabel_name">
								预定人性别
							</label>
							<select id="ordergendor" name="check_gendor" class="check order_input">
										<option value="0">
											女
										</option>
										<option value="1">
											男
										</option>
							 </select>
						</li>
						<li>
							<label class="orlabel_name">
								预定人手机
							</label>
							<input id="ordemobile" name="ordemobile" type="text" value=""
								class="check order_input" maxlength="11" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
						</li>
						<li>
							<label class="orlabel_name">
								保留时效
							</label>
							<input id="limited" name="guarantee" type="text" value=""
								class="check order_input" disabled>
						</li>
						</ul>
				 <label class="label_title ">入住人信息</label>
		            <div id="tab" class="xx_wrap">
		              <span id="addnewtab" class="tab_select tabli addcheckperson button_margin">新增</span>
			           <ul  id="tabtitlenew" class="nav">
				          <li class="tabli active" id="tabtabtitle_0" onclick="changeTab('0')">入住人</li>
			           </ul>
			        </div>
		        <div id="tabCon">
			     <div id="tabCon_0" class="showuser style="display:block">
			        <ul id="tabcontent" class="clearfix">
					   <li><label class="orlabel_name orlabel_red">姓名</label><input id= "user" name="check_user" type="text" value="" class="check order_input" maxlength="10"></li>
					   <li><label class="orlabel_name orlabel_red">性别</label><select id= "gender" name="check_gender" class="check order_input">
										<option value="0">
											女
										</option>
										<option value="1">
											男
										</option>
								</select></li>
					   <li><label class="orlabel_name orlabel_red">手机号</label><input id= "mobile" name="check_mobile" type="text" value="" class="check order_input" maxlength="11" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></li>
					   <li><label class="orlabel_name">身份证</label><input id= "idcard" name="check_idcard" type="text" value="" class="check order_input" maxlength="18" ></li>
					</ul>
				 </div>
		        </div>
		         <label class="label_title ">备注</label>
					<ul>
						<li>
							<label class="orlabel_name">
								接待备注
							</label>
							<textarea id="receptionremark" name="receptionremark" type="text"
								value="" class="check orderta_height order_textarea"></textarea>
						</li>
						<li>
							<label class="orlabel_name">
								客房备注
							</label>
							<textarea id="roomremark" name="roomremark" type="text" value=""
								class="check order_textarea"></textarea>
						</li>
					</ul>
					</section>
					<section class="detail_one_order"
						style="background: #FFFFFF;height:142px;">
					<label class="label_title" >
						预付款
					</label>
					<div id="paymethod" class="paymethod">
						<input id="xj" name="" type="checkbox" />
						<label class="label_name_order">
							现金
						</label>
						<input id="cash" name="cash" type="text" value="" class="check"
							disabled="true"  placeholder="预付金额" onkeyup="num(this)"/>
						<input id="yhk" name="" type="checkbox"/>
						<label class="label_name_order">
							银行卡
						</label>
						<input id="card" name="card" type="text" value="" class="check"
							disabled="true" placeholder="预付金额" onkeyup="num(this)"/>
					</div>
					<div id="order_pzh" class="order_pzh paymethod">
						<label class="label_name_order">
							凭证号
						</label>
						<input id="voucher" name="" type="text" value="" class="check" placeholder="凭证号" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</div>
					<div id="exscore" class="paymethod payscore">
						<input id="jf" name="" type="checkbox" />
						<label class="label_name_order">
							积分
						</label>
						<input id="score" name="score" type="text" value="" class="check"
							disabled="true">
					</div>
					<div id="excash" style="display: none; float: left;">
						<label class="label_name_order">
							金额
						</label>
						<input id="scorecash" name="score" type="text" value=""
							class="check">
					</div>
					</section>
					<section class="detail_four order_four" >
						<span id="order-button" class="button_margin order_order" onclick="orderbegin()">预订</span>
					</section>
				</form>
			</div>
		</div>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
	    <script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.select.js"></script>
        <script src="<%=request.getContextPath()%>/script/ipms/js/selectjs/jquery.mCustomScrollbar.concat.min.js"></script> 
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipmshotel/leftmenu/order/order.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
		      var base_path = "<%=request.getContextPath()%>";
		      function adtimejudge(){
				startTime = $("#arrivedate").val();
				endTime = $("#leavedate").val();
				var starttime = new Date(startTime).getTime();
				var endtime = new Date(endTime).getTime();
				if(endtime <= starttime){
					return true;
				}
				return false;
			}
		</script>
	</body>
</html>
