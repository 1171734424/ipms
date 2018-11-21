var fuzzyObjs, selected, currentElement;

jQuery(function($) {
	fuzzyObjs = $("input[fuzzyquery=true]");
	if (fuzzyObjs) {
		$.each(fuzzyObjs, function(i) {
			fuzzyObjs.eq(i).keydown(function(e) {
				var currKey = 0, e = e || event;
				currKey = e.keyCode || e.which || e.charCode;
				switch (currKey) {
					case 13:
						if (!selected) {
							currentElement = fuzzyObjs.eq(i);
							var queryInfo = currentElement.val();
							if (queryInfo) {
								fuzzyQuery(queryInfo);
							}
						}
						break;
					default:
						break;
				}
			});
		});
	}
});

function fuzzyQuery(queryInfo) {
	var fqId = currentElement.attr("id");
	$.ajax({
		url : base_path + "/commonSearch.do",
		type : "post",
		dataType : "json",
		data : {
			queryInfo : queryInfo,
			arg : fqId
		},
		success : function(json) {
			var message = "";
			$.each(json, function(index) {
				var row = json[index];
				var callback = currentElement.attr("callback");
				message += "<li class='" + (index == 0 ? "fq_selected" : "fq_unselected") + "' onclick='"
						+ (callback ? callback + "Search" : "commonSelect(this,\"" + fqId + "\")")
						+ ";' rval='" + row["VAL"] + "'>" + row["TEXT"] + "</li>";
			});
			if (message) {
				showMessage(fqId, "<ul id='queryInfo' style='position:absolute;'>" + message + "</ul>");
			}
		},
		error : function(data) {
		}
	});
}

function showMessage(fqId, message) {
	var X, Y;
	var messageDiv = fqId + "_info";
	var height = $("#" + fqId).height();
	Y = $("#" + fqId).position().top + height;
	X = $("#" + fqId).position().left - 37;
	jQuery("#" + messageDiv).html(message).css({
		"left" : X,
		"top" : Y,
		"z-index" : 9999999
	}).show();
	$(".fq_selected").hover(function() {
		$(this).addClass("fq_selected");
	}, function() {
		$(this).addClass("fq_unselected");
	});
	selected = $("#queryInfo>li").eq(0);
}

jQuery(function($) {
	$(document).keydown(function(e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		switch (currKey) {
			case 13:
				if (selected) {
					commonSelect();
				}
				break;
			case 37:
			case 38:
				movePrev();
				break;
			case 39:
			case 40:
				moveNext();
				break;
			default:
				break;
		}
	});
});

function commonSelect() {
	currentElement.val(selected.attr("rval"));
	currentElement.next().val(selected.html());
	selected = jQuery("#" + selected.attr("rval"));
	removeQueryInfo();
}

function removeQueryInfo() {
	jQuery("#queryInfo li").remove();
	jQuery("#queryInfo").hide();
	jQuery("#" + (currentElement.attr("id")) + "_info").css("display", "none");
	if (selected) {
		selected = "";
	}
	var arg = arguments[0];
	if (arg) {
		currentElement.val("");
	}
	currentElement = null;
}

function movePrev() {
	var index = $("#queryInfo>li + .fq_selected").prevAll().length;
	selected = $("#queryInfo>li").attr("class", "fq_unselected")
		.eq(index == 0 ? $("#queryInfo>li").length - 1 : index - 1);
	selected.attr("class", "fq_selected");
}

function moveNext() {
	var index = $("#queryInfo>li + .fq_selected").prevAll().length;
	selected = $("#queryInfo>li").attr("class", "fq_unselected")
		.eq(index == $("#queryInfo>li").length - 1 ? 0 : index + 1);
	selected.attr("class", "fq_selected");
}
