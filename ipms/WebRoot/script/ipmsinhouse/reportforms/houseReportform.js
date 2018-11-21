$(function(){
	queryChannelAnalyse();
});
var _li=$(".submenu li");
$(".submenu li").on("click",function(){
	$(this).addClass("onColor");
	$(this).siblings().removeClass("onColor");
})
/**
 * 渠道分析报表
 */
function queryChannelAnalyse(){	
	$("#frame").attr('src',"queryChannelAnalyse.do");
}

/**
 * 跳转财务报表入账项目明细报表页面
 */
function queryIntoAccountDetil(){
	$("#frame").attr('src',"queryIntoAccountDetil.do");
}

/**
 * 跳转财务报表冲减明细表报表页面
 */
function queryWriteDownDetile(){
	$("#frame").attr('src',"queryWriteDownDetile.do");
}

/**
 * 跳转到营业报表页面
 */
function queryOperationStatement(){
	$("#frame").attr('src',"queryOperationStatement.do");
}
