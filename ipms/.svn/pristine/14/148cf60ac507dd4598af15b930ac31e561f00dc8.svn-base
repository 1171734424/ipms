PageLoading = {
	pageLoading : false,
	getPageLoading : function () {
		return pageLoading;
	},
	beginPageLoading : function (msg, loading_type) {
		if (loading_type == null)
			loading_type = "all";
		var loading = document.getElementById("loading");
		var overlay = document.getElementById("overlay");
		var html;
		if (loading_type == "all" || loading_type == "loading") {
			if (loading == null) {
				html = new Array();
				html.push('<div id="background" class="fullbackground"></div>');
				html.push('<div id="loading" class="c_loading" style="display:block;">');
				html.push('<span id="loading_txt"><img src="../../images/loading.gif" algin="bottom"/>'
						+ '&nbsp;<font size="3" color="blue">'+ (msg ? msg : "loading...") +'</font></span>');
				html.push('</div>');
				this.insertHtml('beforeend', document.body, html.join(""));
				html = null;
			} else {
				loading = document.getElementById("loading_txt");
				if (loading && loading.nodeType) {
					loading.innerText = msg ? msg : "loading...";
				}
			}
		}
		if (loading_type == "all" || loading_type == "overlay") {
			if (overlay == null) {
				html = new Array();
				html.push('<div id="overlay" class="c_overLay">');
				html.push('<iframe style="height:100%;width:100%;" frameborder="0" scrolling="no"></iframe>');
				html.push('</div>');
				this.insertHtml('beforeend', document.body, html.join(""));
				html = null;
			}
		}
		loading = null;
		overlay = null;
		html = null;
		pageLoading = true;
	},
	endPageLoading : function (loading_type) {
		if (loading_type == null)
			loading_type = "all";
		if (loading_type == "all" || loading_type == "loading") {
			var background = document.getElementById("background");
			var loading = document.getElementById("loading");
			if (loading) {
				document.body.removeChild(background);
				document.body.removeChild(loading);
			}
			loading = null;
		}
		if (loading_type == "all" || loading_type == "overlay") {
			var overlay = document.getElementById("overlay");
			if (overlay) {
				document.body.removeChild(overlay);
			}
			overlay = null;
		}
		pageLoading = false;
	},
	insertHtml : function (where, el, html) {
		where = where.toLowerCase();
		switch (where) {
		case "beforebegin":
			el.insertAdjacentHTML(where, html);
			return el.previousSibling;
		case "afterbegin":
			el.insertAdjacentHTML(where, html);
			return el.firstChild;
		case "beforeend":
			el.insertAdjacentHTML(where, html);
			return el.lastChild;
		case "afterend":
			el.insertAdjacentHTML(where, html);
			return el.nextSibling;
		}
		throw 'Illegal insertion point -> "' + where + '"';
	}
};