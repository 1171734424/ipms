<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/script.jsp"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会员会级</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/ipms/memberUpGrade.css"/>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css"/>
	<script src="<%=request.getContextPath()%>/script/pageFunctions.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>

  </head>
  
  <body>
  	<div style="width: 100%;height: 400px;">
		<ul style="height: 100%;">
		<c:forEach items="${memberRankDiscount}" var="list">
			<c:if test="${list['MEMBER_RANK'] == '3' }">
				<c:if test="${memberrank >= '2' && memberrank < '3'}">
				<li class="member_li">
					<div class="member_li_img">
						<img src="images/银卡.png" />
					</div>
					<div class="member_li_span">
						<span>${list["DISCOUNT"] }</span>
					</div>
					<div class="member_li_button">
						<button id="3" onclick="payUpGradeMemberLevel(this)">
							购卡
						</button>
					</div>
				</li>
			</c:if>
			</c:if>
			<c:if test="${list['MEMBER_RANK'] == '4' }">
				<c:if test="${memberrank >= '2' && memberrank < '4'}">
				<li class="member_li">
					<div class="member_li_img">
						<img src="images/金卡.png" />
					</div>
					<div class="member_li_span">
						<span>${list["DISCOUNT"] }</span>
					</div>
					<div class="member_li_button">
						<button id="4" onclick="payUpGradeMemberLevel(this)">
							购卡
						</button>
					</div>
				</li>
			</c:if>
			</c:if>
			<c:if test="${list['MEMBER_RANK'] == '5' }">
				<c:if test="${memberrank >= '2' && memberrank < '5'}">
				<li class="member_li">
					<div class="member_li_img">
						<img src="images/白金.png" />
					</div>
					<div class="member_li_span">
						<span>${list["DISCOUNT"] }</span>
					</div>
					<div class="member_li_button">
						<button id="5" onclick="payUpGradeMemberLevel(this)">
							购卡
						</button>
					</div>
				</li>
			</c:if>
			</c:if>
			<c:if test="${list['MEMBER_RANK'] == '6' }">
				<c:if test="${memberblack == '6'}">
				<li class="member_li">
					<div class="member_li_img">
						<img src="images/黑钻.png" />
					</div>
					<div class="member_li_span">
						<span>${list["DISCOUNT"] }</span>
					</div>
					<div class="member_li_button">
						<button id="6" onclick="payUpGradeMemberLevel(this)">
							购卡
						</button>
					</div>
				</li>
			</c:if>
			</c:if>
		</c:forEach>
		</ul>
		<input id="accountid" type="hidden" value="${accountid }" />
		<input id="memberid" type="hidden" value="${memberid }" />
	</div>
	<!-- <div>
		<input class="cancel" type="button" value="取消" onclick="window.parent.location.reload(true);"/>
	</div> -->
  </body>
</html>
