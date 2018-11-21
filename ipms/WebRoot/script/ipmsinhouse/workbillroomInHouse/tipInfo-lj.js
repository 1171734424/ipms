TipInfo = {
	isStrict: document.compatMode == "CSS1Compat",
	IsIE: !!document.all,
	IEVersion: function() {
		if (!this.IsIE)
			return -1;
		try {
			return parseFloat(/msie ([\d\.]+)/i.exec(navigator.userAgent)[1]);
		} catch (e) {
			return -1;
		}
	},
	elem: function(Id, isFrame) {
		var o;
		if ("string" == typeof (Id))
			o = document.getElementById(Id);
		else if ("object" == typeof (Id))
			o = Id;
		else
			return null;
		return isFrame ? (this.IsIE ? frames[Id] : o.contentWindow) : o;
	},
	BodyScale: {
		x : 0,
		y : 0,	
		tx : 0,
		ty : 0
	},
	getClientHeight: function() {
		return this.isStrict ? document.documentElement.clientHeight
				: document.body.clientHeight;
	},
	getScrollHeight: function() {
		var h = !this.isStrict ? document.body.scrollHeight
				: document.documentElement.scrollHeight;
		return Math.max(h, this.getClientHeight());
	},
	getHeight: function(full) {
		return full ? this.getScrollHeight() : this.getClientHeight();
	},
	getClientWidth: function() {
		return this.isStrict ? document.documentElement.clientWidth
				: document.body.clientWidth;
	},
	getScrollWidth: function() {
		var w = !this.isStrict ? document.body.scrollWidth
				: document.documentElement.scrollWidth;
		return Math.max(w, this.getClientWidth());
	},
	getWidth: function(full) {
		return full ? this.getScrollWidth() : this.getClientWidth();
	},
	initBodyScale: function() {
		this.BodyScale.x = this.getWidth(false);
		this.BodyScale.y = this.getHeight(false);
		this.BodyScale.tx = this.getWidth(true);
		this.BodyScale.ty = this.getHeight(true);
	},
	Msg: {
		INFO : 'info',
		ERROR : 'error',
		WARNING : 'warning',
		IsInit : false,
		timer : null,
		dvTitle : null,
		dvCT : null,
		dvBottom : null,
		dvBtns : null,
		lightBox : null,
		dvMsgBox : null,
		defaultWidth : 300,
		moveProcessbar : function() {
			var o = $('dvProcessbar'), w = o.style.width;
			if (w == '')
				w = 20;
			else {
				w = parseInt(w) + 20;
				if (w > 100)
					w = 0;
			}
			o.style.width = w + '%';
		},
		InitMsg : function(width) {
			var ifStr = '<iframe src="javascript:false" mce_src="javascript:false"' 
					+ 'style="position:absolute; visibility:inherit; top:0px;left:0px;width:100%;'
					+ 'height:100%; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';"></iframe>', 
					html = '<div class="top"><div class="right"><div class="div_padding"><span class="close-tip"  onclick="TipInfo.Msg.hide();"/></span></div><div class="title" id="dvMsgTitle"></div>'
						+ '</div></div><div class="body"><div class="right"><span class="img-tip"/></span><div class="ct" id="dvMsgCT"></div></div></div>'
						+ '<div class="bottom" id="dvMsgBottom"><div class="right"><div class="infobtn" id="dvMsgBtns"></div></div></div>';
			this.dvMsgBox = document.createElement("div");
			this.dvMsgBox.id = "dvMsgBox";
			this.dvMsgBox.innerHTML += html;
			document.body.appendChild(this.dvMsgBox);
			this.lightBox = document.createElement("div");
			this.lightBox.id = "tipInfolightBox";
			document.body.appendChild(this.lightBox);
			
			if (this.IsIE && this.IEVersion < 7) {
				this.lightBox.innerHTML += ifStr;
				this.dvMsgBox.innerHTML += ifStr;
			}
			
			this.dvBottom = TipInfo.elem('dvMsgBottom');
			this.dvBtns = TipInfo.elem('dvMsgBtns');
			this.dvCT = TipInfo.elem('dvMsgCT');
			this.dvTitle = TipInfo.elem('dvMsgTitle');
			this.IsInit = true;
			
			document.getElementById("tipInfolightBox").onclick = function(e) {
				var btns = document.getElementsByName("button");
				if (btns.length == 1) {
					TipInfo.Msg.hide();
				}
			};
			
			document.onkeyup = function(e) {
				var currKey = 0, e = e || event;
				currKey = e.keyCode || e.which || e.charCode;
				switch(currKey) {
					case 13:
						var btns = document.getElementsByName("button");
						if (document.getElementById("editmoddataGrid")
								|| document.getElementById("jd_dialog")
								|| window.parent.document.getElementById("jd_dialog")) {
							if (this.ondefault == undefined) {
								this.ondefault = true;
							}
							this.ondefault = !this.ondefault;
						} else {
							this.ondefault = true;
						}
						
						if (this.ondefault) {
							btns[0].click();
						}
						break;
					case 27:
						TipInfo.Msg.hide();
						break;
					default:
						break;
				}
			};
		},
		checkDOMLast : function() {	
			if (document.body.lastChild != this.lightBox) {
				document.body.appendChild(this.dvMsgBox);
				document.body.appendChild(this.lightBox);
			}
		},
		createBtn : function(p, v, fn) {
			var btn = document.createElement("input");
			btn.type = "button";
			btn.className = 'infobtn';
			btn.value = v;
			btn.name = "button";
			btn.onmouseover = function() {
				this.className = 'infobtnfocus';
			}
			btn.onmouseout = function() {
				this.className = 'infobtn';
			}
			btn.onclick = function() {
				TipInfo.Msg.hide();
				if (fn)
					TipInfo.Msg.returnInfo(p, fn);
			}
			return btn;
		},
		alert : function(msg) {
			this.show( {
				buttons : {
					yes : '确认'
				},
				msg : msg,
				title : '消息'
			});
		},
		confirm : function(msg, fn, fn1) {
			this.show( {
				buttons : {
					yes : '确认',
					no : '取消'
				},
				msg : msg,
				title : '提示',
				fn : fn,
				fn1 : fn1
			});
		},
		returnInfo : function(btnInfo, fn) {
			if (btnInfo == "yes") {
				if (typeof fn == "function") {
					fn();
				} else {
					eval(fn);
				}
			}
			else if(btnInfo == "no") {
				if (typeof fn == "function") {
					fn();
				} else {
					eval(fn);
				}
			}
		},
		prompt : function(labelWord, defaultValue, txtId, fn) {
			if (!labelWord)
				labelWord = '请输入：';
			if (!defaultValue)
				defaultValue = "";
			if (!txtId)
				txtId = "msg_txtInput";
			this.show( {
				title : '输入提示',
				msg : labelWord + '<input type="text" id="' + txtId
						+ '" style="width:200px" value="' + defaultValue + '"/>',
				buttons : {
					yes : '确认',
					no : '取消'
				},
				fn : fn
			});
		},
		wait : function(msg, title) {
			if (!msg)
				msg = '正在处理..';
			this.show( {
				title : title,
				msg : msg,
				wait : true
			});
		},
		show : function(cfg) {
			if (!cfg)
				throw ("没有指定配置文件！");
			
			if (this.IsIE)
				window.attachEvent("onresize", this.onResize);
			else
				window.addEventListener("resize", this.onResize, false);
	
			if (!this.IsInit)
				this.InitMsg();
			else
				this.checkDOMLast();
	
			if (cfg.width)
				this.defaultWidth = cfg.width;
			this.dvMsgBox.style.width = this.defaultWidth + 'px';
			
			if (this.timer) {
				clearInterval(this.timer);
				this.timer = null;
			}
			this.dvTitle.innerHTML = '';
			if (cfg.title)
				this.dvTitle.innerHTML = cfg.title;
			this.dvCT.innerHTML = '';
			if (cfg.wait) {
				if (cfg.msg)
					this.dvCT.innerHTML = cfg.msg;
				this.dvCT.innerHTML += '<div class="pro"><div class="bg" id="dvProcessbar"></div></div>';
				this.dvBtns.innerHTML = '';
				this.dvBottom.style.height = '10px';
				this.timer = setInterval(function() {
					this.moveProcessbar();
				}, 1000);
			} else {
				if (!cfg.buttons || (!cfg.buttons.yes && !cfg.buttons.no)) {
					cfg.buttons = {
						yes : '确定'
					};
				}
				if (cfg.icon)
					this.dvCT.innerHTML = '<div class="icon ' + cfg.icon + '"></div>';
				if (cfg.msg)
					this.dvCT.innerHTML += cfg.msg + '<div class="clear"></div>';
				this.dvBottom.style.height = '40px';
				this.dvBtns.innerHTML = '<div class="height"></div>';
				if (cfg.buttons.yes) {
					this.dvBtns.appendChild(this.createBtn('yes', cfg.buttons.yes, cfg.fn));
					if (cfg.buttons.no)
						this.dvBtns.appendChild(document.createTextNode('　'));
				}
				if (cfg.buttons.no)
					this.dvBtns.appendChild(this.createBtn('no', cfg.buttons.no, cfg.fn1));
			}
			TipInfo.initBodyScale();
			this.dvMsgBox.style.display = 'block';
			this.lightBox.style.display = 'block';
			this.onResize(false);
		},
		hide : function() {
			
			this.dvMsgBox.style.display = 'none';
			this.lightBox.style.display = 'none';
			if (this.timer) {
				clearInterval(this.timer);
				this.timer = null;
			}
			if (this.IsIE)
				window.detachEvent('onresize', this.onResize);
			else
				window.removeEventListener('resize', this.onResize, false);
		},
		onResize : function(isResize) {
			if (isResize)
				TipInfo.initBodyScale();
			if (this.dvMsgBox) {
				this.dvMsgBox.style.top = Math.floor((TipInfo.BodyScale.y - this.dvMsgBox.offsetHeight) / 2) + 'px';
				this.dvMsgBox.style.left = Math.floor((TipInfo.BodyScale.x - this.dvMsgBox.offsetWidth) / 2) + 'px';
			}
		}

	}
}