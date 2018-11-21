var tabUI,tabCounter, currentTabId, currentFrame, currentMenu, currentModel, modelConfigs;
var tabLimit, leftHideTabs, rightHideTabs, appMode;

$(document).ready(function() {
	modelConfigs = new Array();
	leftHideTabs =  new Array();
	rightHideTabs = new Array();
	tabCounter = 0;
	appMode = $("#systemType").val() == 1;
	tabUI = $("#tabUI");
	currentTabId = "first";
	createNewTab(currentTabId, "首页");
});

function createNewTab() {
	if (appMode && currentTabId != "first") {
		$("#" + currentTabId).remove();
		$("#contentFrame_" + currentTabId).remove();
		tabCounter -= 1;
	}
	
	var tabId = arguments[0], tabTitle = arguments[1];
	if (getTabIndex(tabId) != -1) {
		$("#" + tabId).css("tab_unselected");
		return false;
	}
	
	if (tabCounter > 12) {
		TipInfo.Msg.alert("导航栏开启数量已达到上限!");
		return false;
	}
	
	tabCounter += 1;
	
	if (isScroll()) {
		if ($(".leftArrow").is(":hidden"))
			showSrcollArrow();
		
		scrollTabs(currentTabId, tabId, "left");
	}
	
	if (tabTitle.length > 8)
		tabTitle = tabTitle.substring(0, 6) + "...";
	
	var tab = "<li id='" + tabId + "' class='tab_selected' onclick=onClickEvent('" + tabId + "')><div class='tab_title'>" 
		+ tabTitle + "</div><div id='close_" + tabId + "' class='close_img'></div></li>";
	$(tab).appendTo(tabUI).dblclick(function() {
		if (tabId != "first") {
			destoryTab(tabId);
		}
		return;
	});
	
	if (appMode || tabCounter > 1) {
		$("<img src='./images/close_scale.png'/>").appendTo($("#close_" + tabId)).hover(function() {
			var img = $(this);
			img.attr("src", "./images/close.png");
		}, function() {
			var img = $(this);
			img.attr("src", "./images/close_scale.png");
		}).click(function() {
			destoryTab(tabId);
			return;
		});
		
		$("#" + tabId).hover(function() {
			$("#close_" + tabId).show();
		}, function() {
			$("#close_" + tabId).hide();
		});
		
		adjustCSS(tabId, currentTabId);
		currentTabId = tabId;
	}
	return true;
}

function destoryTab(tabId) {
	$("#" + tabId).remove();
	$("#contentFrame_" + tabId)[0].src = "";
	$("#contentFrame_" + tabId).remove();
	tabCounter -= 1;
	
	if (!isScroll()) {
		hideScrollArrow();
	}
	
	var leng = $(".tab_ui ul").find("li").length, tarTabId,
		sibling = $(".tab_ui ul").find("li")[leng - 1];
	
	if (rightHideTabs.length > 0) {
		tarTabId = rightHideTabs[rightHideTabs.length - 1];
		rightHideTabs.splice(rightHideTabs.length - 1, 1);
	} else if (leftHideTabs.length > 0) {
		tarTabId = leftHideTabs[leftHideTabs.length - 1];
		leftHideTabs.splice(leftHideTabs.length - 1, 1);
	} else 
		tarTabId = sibling.id;
	
	$("#" + tarTabId).show();
	onClickEvent(tarTabId, tabId);
}

function showSrcollArrow() {
	$(".leftArrow").show().hover(function() {
		var bg = $(this);
		bg.css("backgroundImage", "url(./images/tabs/left_hv.png)");
	}, function() {
		var bg = $(this);
		bg.css("backgroundImage", "url(./images/tabs/left.png)");
	}).click(function() {
		var lis = $(".tab_ui ul").find("li");
		if (lis.length > 1 && lis[0].id != currentTabId)
			onClickEvent($("#" + currentTabId).prev().attr("id"));
		$(this).die("click");
		return;
	});
	$(".rightArrow").show().hover(function() {
		var bg = $(this);
		bg.css("backgroundImage", "url(./images/tabs/right_hv.png)");
	}, function() {
		var bg = $(this);
		bg.css("backgroundImage", "url(./images/tabs/right.png)");
	}).click(function() {
		var lis = $(".tab_ui ul").find("li");
		if (lis.length > 1 && lis[lis.length - 1].id != currentTabId)
			onClickEvent($("#" + currentTabId).next().attr("id"));
		$(this).die("click");
		return;
	});
}

function removeTab() {
	$("#" + currentTabId).remove();
	$("#contentFrame_" + currentTabId).remove();
	onClickEvent("first");
}

function hideScrollArrow() {
	$(".leftArrow").hide();
	$(".rightArrow").hide();
}

function onClickEvent() {
	var tabId = arguments[0], closedTabId = arguments[1];
	if (closedTabId && tabId != currentTabId && closedTabId != currentTabId) return;

	var selectedTab;
	if (!closedTabId && $("#" + tabId).is(":hidden"))
		selectedTab = scrollTabs(currentTabId, $("#" + tabId).is(":hidden") ? tabId : null);
	selectedTab = selectedTab ? selectedTab : tabId;
	if ($("#contentFrame_" + selectedTab).css("display") == "none") {
		$("#contentFrame_" + currentTabId).css("display", "none");
		$("#contentFrame_" + selectedTab).css("display", "block");
		
		if (modelConfigs[selectedTab] && modelConfigs[closedTabId] != modelConfigs[selectedTab])
			listFunctions(modelConfigs[selectedTab]);
		adjustCSS(selectedTab, currentTabId);
		currentTabId = selectedTab;
	}
	currentFrame = "#contentFrame_" + currentTabId;
	currentMenu = "#menu_" + currentTabId;
}

function getTabIndex() {
	return $(".tab_ui ul li").index($("#" + arguments[0]));
}

function adjustCSS() {
	$("#" + arguments[0]).attr("class", "tab_selected");
	$("#menu_" + arguments[0]).attr("class", "menu_selected");
	if (arguments[1]) {
		$("#" + arguments[1]).attr("class", "tab_unselected");
		$("#menu_" + arguments[1]).attr("class", "menu_unselected");
	}
}

function doQuery() {
	var id = arguments[0], obj = $("#menu_" + id), openWin = arguments[1];
	var url = base_path + (obj.attr("url") ? "/" + obj.attr("url") + ".do" : "/queryPageConfig.do")
		+ "?modelId=" + $("#modelId").val() + "&pageId=" + id;
	
	if (openWin) {
		if (openWin == "blank") {
			window.open(url, "_blank");
		} else {
			top.window.location.href = url;
		}
	} else {
		if(createNewTab(id, obj.attr("name"))) {
			var el = "<iframe name='contentFrame_" + id + "' id='contentFrame_" + id + "' src='" + url
				+ "' frameborder='0' width='100%' height='100%' style='display:block;'></iframe>";
			$(currentFrame).css("display", "none");
		    $(el).appendTo($("#dataZone"));
		    currentFrame = "#contentFrame_" + id;

			if (currentMenu) {
				adjustCSS(currentTabId, currentMenu.substring(6));
				$(currentMenu).attr("class", "menu_unselected");
			}
			obj.attr("class", "menu_selected");
			currentMenu = "#" + obj.attr("id");
			
			if (!modelConfigs[id]) modelConfigs[id] = $("#modelId").val();
		} else
			onClickEvent(id);
	}
}

function listFunctions(modId, modName) {
	modName = modName ? modName : $("#model_" + modId).attr("name");
	if($("#menu_" + modId).attr("id")) return;
	$.ajax({
		url: base_path + "/queryModelFunctions.do",
		type: "post",
		dataType: "json",
		data: { modelId: modId },
		success: function(json) {
			if (json && json.length > 0) {
				$("#menuList li").remove();
				if (currentModel != modId) {
					$("#model_" + currentModel).attr("class", "model_unselected");
					$("#model_" + modId).attr("class", "model_selected");
				}
				$("#modelId").val(modId);
				currentModel = modId;
				var menu = $("#menuList");
				$("<li id='menu_" + modId + "' name='" + modName + "' class='menu_title'>"
						+ "<div class='li_lefticon'><span class='li_text'>" + modName + "</span></div></li>").appendTo(menu);
				$.each(json, function(index) {
					var func = json[index];
					if (!func.tab && !func.subWin && func.show) {
						$("<li id='menu_" + func.pageId + "' name='" + func.name + "' url='" + func.url + "' class='menu_unselected'"
							+ " onclick='doQuery(\"" + func.pageId + "\", \"" + func.openWin + "\")';>" 
							+ "<div class='li_selected' style='background-image: url(./images/" + (func.icon ? func.icon : "stra"+func.pageId) + ".png)'>"
							+ "<span class='li_text'>" + func.name + "</span></div></li>")
						.appendTo(menu).hover(function() {
							var menu = $(this);
							menu.attr("class", "menu_selected");
						}, function() {
							var menu = $(this);
							if (!currentMenu || currentMenu.substring(6) != func.pageId) {
								menu.attr("class", "menu_unselected");
							}
						});
					}
				});

				menu.effect("slide", { direction: "up" }, 500);
				adjustCSS(currentTabId);
			}
		},
		error: function(json) {
		}
	});
}

function isScroll() {
	var tabWidth = $(".tab_ui ul").find("li:first").outerWidth();
	tabLimit = Math.floor($(".tab_ui").outerWidth() / tabWidth);
	return tabCounter > tabLimit /*|| $(".tab_ui ul").find("li:hidden").length > 0*/;
}

function getRollingCells(direction, hiddenArr, tarTab) {
	var cell = 1;
	if (hiddenArr.length > 0) {
		if (hiddenArr.indexOf(tarTab) != -1) {
			cell = hiddenArr.length - hiddenArr.indexOf(tarTab);
		} else {
			cell = hiddenArr.length + 1;
		}
	}
	return cell;
}

function scrollTabs(currTab, tarTab, direction) {
	if (currTab == tarTab) return currTab;
	var currIndex = getTabIndex(currTab), tarIndex, interval, cell;
	if (!tarTab) {
		var hiddenTabs = $(".tab_ui ul").find("li:hidden");
		if (hiddenTabs.length > 0) {
			$.each(hiddenTabs, function(index) {
				tarTab = hiddenTabs[index].id;
				if(getTabIndex(tarTab) > currIndex) {
					return false;
				}
			}); 
			tarIndex = getTabIndex(tarTab);
		} else
			return null;
	} else
		tarIndex = getTabIndex(tarTab);
	
	interval = tarIndex == -1 ? 1 : tarIndex - currIndex;
	if (!direction) 
		direction = interval > 0 ? "left" : "right";
	
	cell = getRollingCells(direction, direction == "left" ? rightHideTabs : leftHideTabs, tarTab);
	
	$(".tab_ui").Scroll({ cell: cell, speed: 100, direction: direction, tabId: tarTab });
	return tarTab;
}

(function($) {
	$.fn.extend({
		Scroll : function(opt, callback) {
			if (!opt) var opt = {};
			var _this = this.eq(0).find("ul:first");
			var width = _this.find("li:first").outerWidth(),
				cell = opt.cell ? parseInt(opt.cell, 10) : parseInt(this.width() / width, 10),
				speed = opt.speed ? parseInt(opt.speed, 10) : 500; 
			if (cell == 0)
				cell = 1;
			var scrollWidth = 0 - cell * width;
			
			var lis = _this.find("li:visible");
			if (isScroll()) {
				var index = 0, hiddenTab;
				var rolls = Math.abs(cell);

				while(index < rolls) {
					if (opt.direction == "left") {
						hiddenTab = lis.eq(index);
						leftHideTabs.push(hiddenTab.attr("id"));
						if (rightHideTabs.length > 0) {
							$("#" + rightHideTabs[rightHideTabs.length - 1]).show();
							rightHideTabs.splice(rightHideTabs.length - 1, 1);
						}
					} else {
						hiddenTab = lis.eq(lis.length - index - 1);
						rightHideTabs.push(hiddenTab.attr("id"));
						if (leftHideTabs.length > 0) {
							$("#" + leftHideTabs[leftHideTabs.length - 1]).show();
							leftHideTabs.splice(leftHideTabs.length - 1, 1);
						}
					}
					hiddenTab.hide();
					index += 1;
				}
			}
			/*_this.animate({
				"margin-left" : scrollWidth + "px"
			}, speed, function() {
				
				_this.css({
					"margin-left" : 0
				});
			});*/
		}
	})
})(jQuery);
