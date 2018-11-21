<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>导入</title>
		<%@ include file="../common/css.jsp"%>
	</head>
	<body>
		<form method="post" enctype="multipart/form-data" target="_self">
			<div class="file">
				<span>点击选择文件:</span>
				<input type="text" id="fileName" disabled="disabled"/>
				<input type="file" name="importFile" id="importFile"/>
			</div>
			<div style="margin-left: 88px; height: 40px; width:360px;">
				<ul class="ul">
					<li class="li">
						<div class="dialogButton"   onclick="importData();">
							<span>导入</span>
						</div>
					</li>
					<li class="li">
						<div class="dialogButton"  onclick="reset();">
							<span>重置</span>
						</div>
					</li>
					<li class="li">
						<div class="dialogButton" onclick="window.parent.JDialog.close();">
							<span>关闭</span>
						</div>
					</li>
				</ul>
			</div>
		</form>
		<%@ include file="../common/script.jsp"%>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/ajaxFileUpload.js"></script>
		<script type="text/JavaScript">
			
			$(document).ready(function() {
				$("#importFile").bind("change", function() {
					if($(this).val()) {
						var fArr = $(this).val().split("\\");
						$("#fileName").val(fArr[fArr.length - 1]);
					} else {
						$("#fileName").val("");
					}
				});
			});
			
			var base_path = '<%=request.getContextPath()%>';
			var mode = "${mode}";
			var reg = mode && mode == "image" ? /^(png|gif|jpeg|jpg)$/i
				 : mode && mode == "file" ? /^(docx|txt)$/i : /^(xlsx|txt)$/i;
		
	        function importData() {
				var importFile = $("#importFile").val();
				
	            if (!importFile) {
	                $("#importFile").focus();
	                return false;
	            }
	            
	            var fileType = importFile.substring(importFile.lastIndexOf(".") + 1);
		        if(!reg.test(fileType)) {
	               	window.parent.TipInfo.Msg.alert("文件类型错误！");
	                location.reload();
	                return false;
		        }
		        
		        if (mode && /^(image|file)$/i.test(mode)) {
		        	var fileName = "${fileName}" ?  "${fileName}." + fileType : "";
					$.ajaxFileUpload({
				        url: base_path + "/uploadFile.do",
				        secureuri: false,
				        fileElementId: "importFile",
				        dataType: 'json',
				        data: { key: "importFile", fileName: fileName, dir: "${dir}" },
				        success: function (data, status) {
				        	window.parent.showMsg("上传文件成功！");
							window.parent.JDialog.close();
				        },
				        error: function (data, status, ex) {
				        	window.parent.showMsg("上传文件失败！");
				        }
				    });  
				} else {
					$.ajaxFileUpload({
						url: base_path + "/importData.do",
				        secureuri: false,
				        fileElementId: "importFile",
						dataType: "json",
						data: { modelId: window.parent.model_id, pageId: window.parent.page_id, key: "importFile" },
						success: function(json) {
							if (!json.sucess) {
								var errorInfo, infos = "";
								$.each(json, function(index) {
									if(index == 2) {
										infos += "<br/>...";
										return false;
									}
									
									errorInfo = json[index];
									infos += "<br/>" + errorInfo.errorLocation + "的第" + errorInfo.postion
										+ "格,《" + errorInfo.title + "》的数据《" + errorInfo.errorData
										+ "》," + errorInfo.errorMsg;
								});
								window.parent.TipInfo.Msg.alert("导入数据失败！" + infos);
								location.reload();
							} else {
								window.parent.TipInfo.Msg.alert("导入数据成功！");
								window.parent.refreshGrid();
								window.parent.JDialog.close();
							}
						},
						error: function(json) {
							window.parent.TipInfo.Msg.alert("导入数据失败！");
							location.reload();
						}
					});
				}
			}
	        
	        function reset() {
	        	$("#importFile").val("");
				$("#fileName").val("");
	        }
	    </script>
	</body>
</html>
