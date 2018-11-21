var li, el, url, currentTab;

$(document).ready(function() {
	url = new Object();
	listTabs();
	$("#gridTabs").tabs();
	$(".ui-widget").css('font-size','0.7em');
	
});

function listTabs() {
	var firstLoad = true;
	$.each(page_config.tabs, function(index) {
		li = "<li onclick='loadDataFrame(\"" + index + "\")'><a href='#" + index + "'>" + page_config.tabs[index] + "</a></li>";
		$(li).appendTo($("#gridul"));
		url[index] = page_config.tabUrls && page_config.tabUrls[index] ? base_path + "/" + page_config.tabUrls[index] + ".do"
			: base_path + "/queryPageConfig.do?modelId=" + $("#modelId").val() + "&pageId=" + index;
		loadTabContent(index, firstLoad);
		if (firstLoad) {
			firstLoad = false;
		}
	});
}

function loadTabContent(tabId, isLoad) {
	if ($("#" + tabId).length == 0) {
		$("<div id='" + tabId + "' class='subtab'></div>").appendTo($("#gridTabs"));
		if (isLoad) {
			loadDataFrame(tabId);
		}
	}
}

function loadDataFrame(tabId) {
	if ($("#gridFrame_" + tabId).length == 0) {
		$("<iframe name='gridFrame_" + tabId + "' id='gridFrame_" + tabId + "' src='" + url[tabId]
		   + "' frameborder='0' width='100%' height='100%' style='margin:0;'></iframe>").appendTo($("#" + tabId));
	}
}
