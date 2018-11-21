<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>账户管理</title>
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
            		<c:if test="${ cashCount == null}">
            			<c:forEach items="${cashBoxList}" var="sl">
	                    	<legend>数据展示</legend>
	                    	<input type="hidden" id="branchSubType" name="branchSubType" value="${branchSubType}"/>
	                    	<input type="hidden" id="dataId" name="dataId" class="required paddingInput" value="${sl.dataId}">
	                    	<div class="control-group">
							<label class="control-label leftfloat">当前门店：</label>
							<div class="controls leftfloat">
								<input type="text" id="branchName" name="branchName" class="required paddingInput" value="${sl.branchName}" readonly>
							</div>
							</div>
							<div class="control-group">
								<label class="control-label leftfloat">备用金额：</label>
								<div class="controls leftfloat">
									<input type="text" id="cashCount" name="cashCount" class="required paddingInput" value="${sl.cashCount}">
								</div>
							</div>
						</c:forEach> 	
                	</c:if>
                	<c:if test="${ cashCount != null}">
                    	<legend>账户设置</legend>
                    	<div class="control-group">
                    	<input type="hidden" id="branchSubType" name="branchSubType" value="${branchSubType}"/>
						<label class="control-label leftfloat">当前门店：</label>
						<div class="controls leftfloat">
							<input type="text" id="branchName" name="branchName" class="required paddingInput" value="${branchName}" readonly>
						</div>
						</div>
						<div class="control-group">
							<label class="control-label leftfloat">备用金额：</label>
							<div class="controls leftfloat">
								<input type="text" id="cashCount" name="cashCount" class="required paddingInput" value="${cashCount}">
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
		<!--<script src="<%=request.getContextPath()%>/script/initial/bwizard.min.js"></script>-->
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	$(document).ready(function() {
	    		
	    		
        	});
        	
        	/*
 * =============================================== 双击事件，为其添加文本框，用来输入
 * ===============================================
 */
function TdOnClick() { 
	// 首先判断是不是该方格已经存在文本框，如果存在，则返回，不重复添加文本框。如果不存在，则添 加
	var tdcag = document.getElementById("tdcag");
	var tdid = null;
	var tdtxt = null;
	var curtd = window.event.srcElement;
	if (tdcag != null) {
		return;
	}
	tdid = window.event.srcElement;
	tdtxt = tdid.innerText;
	tdtxt = Trim(tdtxt);
	var str = "<div id='tdcag' style='position:absolute;height:4%;width:10%;margin-top:-0.5%;margin-left:-1%;'>"
	        +"<input type='text' style='height:100%;width:100%;' onblur='ChangeTdText();' id='txtId' value='"
			+ tdtxt + "'>";
	str += "<input type='hidden' id='tdInitValue' value='" + tdtxt + "'>";
	str += "</div>";
	tdid.innerHTML = str;
	// 使文本框获得焦点
	var obj = document.getElementById("txtId").focus();
	var ctr = document.getElementById("txtId").setSelectionRange(0,tdtxt.length);

}

/*
 * =============================================== 去除字符串前后的空格
 * ===============================================
 */
function Trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

/*
 * =============================================== 按ESC键，则取消更改，恢复为更改前的值
 * ===============================================
 */
document.onkeypress = function EscKeyPress() {
	if (event.keyCode == 27) {
		CancelTdChanged();
		return;
	}
}

/*
 * =============================================== 取消更改内容
 * ===============================================
 */
function CancelTdChanged() {
	var tdInitValue = document.getElementById("tdInitValue");
	var tdtxt = tdInitValue.value;
	var tdid = document.getElementById("tdcag").parentNode;
	tdid.innerText = Trim(tdtxt);
}

/*
 * =============================================== 确定更改内容
 * ===============================================
 */
function ChangeTdText() {
	var txtId = document.getElementById("txtId");
	if (txtId == null) {
		return;
	}
	var tdid = document.getElementById("tdcag").parentNode;
	tdid.innerText = Trim(txtId.value);
	return;
}
        	
        	
	</script>
	</body>
</html>

