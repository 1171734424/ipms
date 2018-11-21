<%@ page language="java" pageEncoding="utf-8"%>
<c:if test="${ pageConfig.paramUrl != null }">
	<iframe id="pageParam" name="pageParam" class="reportform_param" frameborder="0" align="top"
		src="<%=request.getContextPath()%>/page/param/${ pageConfig.paramUrl }.jsp" onload="selfJustify()"></iframe>
	<script>
		function selfJustify() {
			var ifm = document.getElementById("pageParam");
			var inner = document.frames ? document.frames["pageParam"].document : ifm.contentDocument;
			if(ifm != null && inner != null) {
				ifm.height = inner.body.scrollHeight + 30;
			}
		}
	</script>
</c:if>
