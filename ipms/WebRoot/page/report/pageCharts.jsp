<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${pageConfig.charts != null}">
	<div class="reportform_left">
		<div class="right_chart">
			<div class="chart_title">
				<c:if test="${pageConfig.charts.Column != null}">
					<input type="radio" value="Column2D" name="chartType" checked="checked" onclick="loadChart()"/>
					<span class="font_large">柱状图</span>
				</c:if>
				<c:if test="${pageConfig.charts.Pie != null}">
					<input type="radio" value="Pie2D" name="chartType" onclick="loadChart()"/>
					<span class="font_large">占比图</span>
				</c:if>
				<c:if test="${pageConfig.charts.Line != null}">
					<input type="radio" value="Line" name="chartType" onclick="loadChart()"/>
					<span class="font_large">趋势图</span>
				</c:if>
			</div>
			<div class="chart_inner" align="center" id="chart_inner"></div>
			<c:if test="${ pageConfig.chartInfo != null }">
				<div id="chart_info" align="center">
					<iframe id="chartInfo" name="chartInfo" frameborder="0" align="top" onload="selfJustify()"
						src="<%=request.getContextPath()%>/page/chart/${ pageConfig.chartInfo }.jsp" ></iframe>
				</div>
				<script>
					function selfJustify() {
						var ifm = document.getElementById("chartInfo");
						var inner = document.frames ? document.frames["chartInfo"].document : ifm.contentDocument;
						if(ifm != null && inner != null) {
							ifm.width = inner.body.scrollWidth;
							ifm.height = inner.body.scrollHeight;
						}
					}
				</script>
			</c:if>
		</div>
		<div id="seperator" class="chart_seperator" onclick="showCharts();">
			<span class="arrow"></span>
		</div>
	</div>
</c:if>