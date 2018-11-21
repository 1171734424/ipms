<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>参数设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/ui.jqgrid.css"/>
	</head>
	<body>
		<div class="well" style="height:540px;">
			<form id="myForm" class="form-horizontal" role="form" method = "post">
            	<fieldset>
            		<c:if test="${ CleanCount == null}">
            			<c:forEach items="${cleanData}" var="sl">
	                    	<legend>数据展示</legend>
	                    	<input type="hidden" id="paramId" name="paramId" class="required paddingInput" value="${sl.paramId}">
	                    	<input type="hidden" id="goodId" name="goodId" class="required paddingInput" value="${sl.goodId}">
	                    	<input type="hidden" id="branchSubType" name="branchSubType" value="${branchSubType}"/>
	                    	<div class="control-group">
							<label class="control-label leftfloat">当前门店：</label>
							<div class="controls leftfloat">
								<input type="text" id="branchName" name="branchName" class="required paddingInput" value="${sl.branchName}" readonly>
							</div>
							</div>
							<div class="control-group">
								<label class="control-label leftfloat">半日保洁上限：</label>
								<div class="controls leftfloat">
									<input type="text" id="content" name="content" maxlength="2" class="required paddingInput" value="${sl.oldcontent}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label leftfloat">统一保洁费用：</label>
								<div class="controls leftfloat">
									<input type="text" id="cleanMoney" name="cleanMoney" class="required paddingInput" value="${sl.cleanMoney}">
								</div>
							</div>
						</c:forEach> 	
                	</c:if>
                	<c:if test="${ CleanCount != null}">
                    	<legend>参数设置</legend>
                    	<input type="hidden" id="branchSubType" name="branchSubType" value="${branchSubType}"/>
                    	<div class="control-group">
						<label class="control-label leftfloat">当前门店：</label>
						<div class="controls leftfloat">
							<input type="text" id="paramDesc" name="paramDesc" class="required paddingInput"  value="${branchName}" readonly>
						</div>
						</div>
						<div class="control-group">
							<label class="control-label leftfloat">半日保洁上限：</label>
							<div class="controls leftfloat">
								<input type="text" id="content" name="content" maxlength="2" class="required paddingInput" value="${CleanCount}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label leftfloat">统一保洁费用：</label>
							<div class="controls leftfloat">
								<input type="text" id="cleanMoney" name="cleanMoney"  class="required paddingInput" value="${CleanMoney}">
							</div>
						</div>
	                </c:if>
               </fieldset>        
            </form> 
		</div>
		<script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.fmatter.js"></script>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	$(document).ready(function() {
	    		
        	});
        	    	
	</script>
	</body>
</html>

