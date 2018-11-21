/**
 * @author ZhengXing
 * @date   2014/4/26
 */
Pager = {
	pagerId: "",
	pagerForm: "",
	total: 0,
	current: 0,
	rows: 20,
	records: 0,
	callback: null,
	setId: function (id) {
		this.pagerId = id;
	},
	setForm: function (formId) {
		if (formId)
			this.pagerForm = $("#" + formId);
		else
			this.pagerForm = $("form")[0];
	},
	setCurrent: function (pageNum) {
		this.current = pageNum;
	},
	setTotal: function (pageCnt) {
		this.total = pageCnt;
	},
	setRows: function (num) {
		this.rows = num;
	},
	setRecords: function (count) {
		this.records = count;
	},
	setCallback: function(fn) {
		this.callback = fn;
	},
	hasPreview: function () {
		return this.current != 1;
	},
	hasNext: function () {
		return this.current != this.total;
	},
	isFirst: function() {
		return this.current == 1;
	},
	isLast: function() {
		return this.current == this.total;
	},
	preview: function () {
		if (this.hasPreview()) this.doQuery(this.current - 1);
	},
	next: function () {
		if (this.hasNext()) this.doQuery(this.current + 1);
	},
	first: function () {
		if (!this.isFirst()) this.doQuery(1); 
	},
	last: function () {
		if (!this.isLast()) this.doQuery(this.total);
	},
	doQuery: function (pageNum) {
		if (!pageNum) {
			showMsg("请输入需要查询的页码!");
			return;
		} else if(!/^[0-9]*$/.test(pageNum) || pageNum > this.total) {
			showMsg("请输入正确的页码!");
			return;
		}
		this.setCurrent(pageNum);
		this.setPaginationInfo();
		if (this.callback) {
			if (typeof this.callback == "function") {
				this.callback();
			} else {
				eval(this.callback);
			}
		} else {
			this.pagerForm.submit();
		}
	},
	renderPager: function (pagination, fn, type, id, formId) {
		if (pagination) {
			this.setId(id ? id : "pager");
			this.setForm(formId);
			this.setCurrent(pagination["pageNum"]);
			this.setTotal(pagination["total"] == 0 ? 1 : pagination["total"]);
			this.setRows(pagination["rows"]);
			this.setRecords(pagination["records"]);
			this.setCallback(fn);
			if ($(".pageRegion").length == 0) {
				var cls = type ? "_scale" : "";
				var el = "<div class='pageRegion' align='center'><div class='btn'><ul class='ul'>" +
						"<li class='li'><a class='b_btn' title='首页'><span class='b_first'" + cls + " onclick='Pager.first();'></span></a></li>" +
						"<li class='li'><a class='b_btn' title='上一页'><span class='b_preview'" + cls + " onclick='Pager.preview();'></span></a></li>";
				if (!type) {
					el += "<li class='li'><input type='text' id='queryPage' value='" + pagination.pageNum + "'/></li>" +
						"<li class='li'><a class='b_btn' title='跳转到'><span class='b_looking'" + cls + " onclick='Pager.doQuery($(\"#queryPage\").val());'></span></a></li>"
				}	
				el += "<li class='li'><a class='b_btn' title='下一页'><span class='b_next'" + cls + " onclick='Pager.next();'></span></a></li>" +
						"<li class='li'><a class='b_btn' title='尾页'><span class='b_last'" + cls + " onclick='Pager.last();'></span></a></li></ul></div>" +
						"<div class='fontspan'><span><font class='font_large'>第" + pagination.pageNum + "页  共" + this.total + "页</font></span></div>";
				$("#" + this.pagerId).append(el);
				var hidden = "<input type='hidden' id='total' name='total'/><input type='hidden' id='records' name='records'/>" +
					"<input type='hidden' id='pageNum' name='pageNum'/><input type='hidden' id='rows' name='rows'/><div>";
				$(hidden).appendTo(this.pagerForm);
			}
			this.setPaginationInfo();
		}
	},
	setPaginationInfo: function() {
		$("#total").val(this.total);
		$("#pageNum").val(this.current);
		$("#rows").val(this.rows);
		$("#records").val(this.records);
	},
	renderComponent: function() {
		//$().this.hasPreview()
	},
	schedule: function() {
		
	}
};

