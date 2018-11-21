/**
 * @author ZhengXing
 * @date   2016/4/21
 */
(function ($) {
	$.zlist = $.zlist || {};
	$.extend($.zlist,{
		version: "0.1b",
		getAccessor : function(obj, expr) {
			var ret, p, prm = [], i;
			if(typeof expr === 'function') {
				return expr(obj); 
			}
			ret = obj[expr];
			if(ret === undefined) {
				try {
					if (typeof expr === 'string') {
						prm = expr.split('.');
					}
					i = prm.length;
					if(i) {
						ret = obj;
						while (ret && i--) {
							p = prm.shift();
							ret = ret[p];
						}
					}
				} catch (e) {}
			}
			return ret;
		},
		getMethod: function (name) {
	        return this.getAccessor($.fn.zList, name);
		},
		extend : function(methods) {
			$.extend($.fn.zList, methods);
			if (!this.no_legacy_api) {
				$.fn.extend(methods);
			}
		}
	});
	
	$.fn.zList = function(func) {
		if (typeof func === 'string') {
			var fn = $.zlist.getMethod(func);
			if (!fn) {
				throw ("No such method: " + func);
			}
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this, args);
		}
		
		return this.each(function() {
			if(this.list) {
				return;
			}
			
			var p = $.extend(true, {
				url: "",
				lineHeight: 100,
				width: 400,
				height: 400,
				page: 1,
				rowNum: 20,
				pager: "",
				attrModel: []
			}, $.zlist.defaults, func || {});
			
			var zl = this, list = {
				initList: function() {
					$(zl).css("width", p.width);
					$(zl).css("height", p.height + 40);
				}
			};
			
			this.p = p;
			this.list = list;
			list.initList();
		});
	};

	$.zlist.extend({
		getListParam: function(pName) {
			var $t = this[0];
			if (!$t || !$t.p ) { 
				return; 
			}
			if (!pName) { 
				return $t.p; 
			}
			return $t.p[pName] !== undefined ? $t.p[pName] : null;
		}, 
		getAttrModel: function(aName) {
			var $t = this[0];
			if (!$t || !$t.p ) { 
				return; 
			}
			if (!aName) { 
				return $t.p.attrModel; 
			}
			var am = $t.p.attrModel, mod = null;
			$.each(am, function(i) {
				if (am[i].name == aName || am[i].index == aName || am[i].text == aName) {
					mod = am[i];
					return false;
				}
			});
			return mod;
		}
	});
})(jQuery);

(function($) {
	"use strict";
	$.zlist.extend({
		loadList: function(url) {
			var t = this[0], am = $(t).zList("getListParam", "attrModel");
			$(t).empty();
			$.ajax({
				url: url + "&rows=" + Pager.rows + "&page=1",
				type: "post",
				dataType: "json",
				success: function(json) {
					if (json.result) {
						var result = JSON.parse(json.result).rows, cnt, lines, v, 
							el = "<tr><td valign='top'><div class='list_main'>";
						$.each(result, function(index) {
							el += "<div class='list_innderDiv' vid=" + result[index]["id"] + "><table class='list_innerTable'><tr>"
							cnt = 0;
							lines = 1;
							$.each(am, function(i) {
								if($(t).zList("isDataVisible", am[i])) {
									if (cnt != 0 && cnt % 3 == 0) {
										el += "</tr><tr>"
										lines += 1;
									}
									v = result[index][am[i].name];
									el += "<td>" + am[i].text + ":" + (v ? v : "") + "</td>"
									cnt += 1;
								}
							});
							el += "</tr></table></div>";
						});
						el += "</div></td></tr>";
						$(t).append(el);
						$(".list_innderDiv").css("height", lines * 40);
						$(".list_main").css("height", $(t).zList("getListParam", "height") + 30);

						if (editFlag) {
							$(".list_innderDiv").bind("click", function() {
								if ($(this).find(".list_select").length == 0) {
									var ck = "<div class='list_select'></div>";
									$(ck).appendTo($(this));
								} else {
									$(this).find(".list_select").remove();
								}
								onChecked($(this).attr("vid"));
								adjustSelectorCSS($(".list_select").length == $(".list_innderDiv").length);
		   					});
						}
					}
					if (json.pagination) {
						Pager.renderPager(json.pagination, false, false, null, loadList);
					}
				},
				error: function(json) {
					
				}
			});
		},
		isDataVisible: function (am) {
			return !/^(select|textarea|date|datetime)$/i.test(am.editType) 
				&& !/^(id|RN|CHECK)$/i.test(am.name) 
				&& !am.hidden;
		}
	});
})(jQuery);


