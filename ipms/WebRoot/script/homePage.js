
$(document).ready(function() {
	$(function($) {
		$(document).keydown(function(e) {
			var currKey = 0, e = e || event;
			currKey = e.keyCode || e.which || e.charCode;
			if (currKey == 116) {
				return false;
			}
		});
	});
	
	loadCalender();
	

	MessageHints();
	if (message_num) {
		addMessageNum(message_num);
	}
	
	setInterval("getNowTime()", 1000);
	//setInterval("MessageHints()", 30000);
});

function MessageHints() {
	$.ajax({
		url: base_path + "/messageHints.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			if (json) {
				addMessageNum(json.result);
			}
		},
		error: function(json) {
		}
	});
}

function showMessageDetails() {
	//JDialog.open("信息", base_path + "/showMessageDetails.do", 800, 500);
	addMessageNum(0);
}

function addMessageNum(num) {
	var numberbox = $(".numberbox");
	if (num == 0 && numberbox.length > 0) {
		numberbox.remove();
	} else {
		num = num < 10 ? num : "..";
		if (numberbox.html()) {
			numberbox.html(num);
		} else {
			$("<div class='numberbox' align='center'>" + num + "</div>").appendTo($(".messagebox"));
		}
	}
}

function doBeforeLogOut() {
	/*if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
			window.top.opener = null;
			window.top.close();
		} else {
			window.top.open('', '_top');
			window.top.close();
		}
	} else if (navigator.userAgent.indexOf("Firefox") > 0
			|| navigator.userAgent.indexOf("Chrome") > 0) {
		window.top.location.href = "about:blank";
		window.top.close();
	} else {
		window.top.opener = null;
		window.top.open('', '_self');
		window.top.close();
	}*/
	window.top.location.href = base_path + "/logout.do";
}

function doReloadSystem() {
	$.ajax({
		url: base_path + "/doReloadSystem.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			top.window.location.reload(true);
		}
	});
}

function doReloadConfig() {
	$.ajax({
		url: base_path + "/doReloadConfig.do",
		type: "post",
		dataType: "json",
		success: function(json) {
			top.window.location.reload(true);
		}
	});
}


