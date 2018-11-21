/*
* @Author: 李燕南
* @Date:   2017-07-12 18:05:36
* @Last Modified by:   李燕南
* @Last Modified time: 2017-07-12 18:06:41
*/

(function(c) {
    var i = {
        wrap: null,
        width: -1,
        height: -1,
        auto: false,
        fileVal: "file",
        method: "POST",
        sendAsBlob: false,
        viewImgHorizontal: true,
        btns: {
            uploadBtn: null,
            retryBtn: null,
            chooseBtn: null,
            chooseBtnText: "点击选择图片"
        },
        pictureOnly: true,
        multiple: true,
        swf: "http://127.0.0.1:8080/ipms/Uploader.swf",
        url: "http://127.0.0.1:8080/ipms/upload",
        datas: null,
        resize: false,
        duplicate: false,
        maxFileNum: 20,
        maxFileTotalSize: 10485760,
        maxFileSize: 2097152,
        showToolBtn: true,
        onFileAdd: null,
        onDelete: null,
        uploadFailTip: null,
        onError: null,
        notSupport: null,
        onSuccess: null
    },
    h = (function() {
        var j = document.createElement("p").style,
        k = "transition" in j || "WebkitTransition" in j || "MozTransition" in j || "msTransition" in j || "OTransition" in j;
        j = null;
        return k
    })();
    function g(j) {
        if (j.nodeName && j.nodeType) {
            return true
        }
        return false
    }
    function a(l, p, o, k) {
        var r = c('<li id="' + l.id + '">' + '<p class="title">' + l.name + "</p>" + '<p class="imgWrap"></p>' + '<p class="progress"><span></span></p>' + "</li>"),
        m = i.showToolBtn ? c('<div class="file-panel">' + '<span class="cancel">删除</span>' + '</div>').appendTo(r) : null,
        q = r.find("p.progress span"),
        s = r.find("p.imgWrap"),
        j = c('<p class="error"></p>'),
        n = function(t) {
            switch (t) {
            case "exceed_size":
                text = "文件大小超出";
                break;
            case "interrupt":
                text = "上传暂停";
                break;
            default:
                text = '上传失败，<a href="javascript: void(0);" class="retry-this">重试</a>&nbsp;|&nbsp;<a href="javascript: void(0);" class="del-this">删除</a>';
                break
            }
            j.html(text).appendTo(r);
            j.find(".retry-this").on("click",
            function() {
                p.retry(l)
            });
            j.find(".del-this").on("click",
            function() {
                b(l, k);
                p.removeFile(l, true)
            })
        };
        if (l.getStatus() === "invalid") {
            n(l.statusText)
        } else {
            s.text("预览中");
            p.makeThumb(l,
            function(v, w) {
                if (v) {
                    var t = c("<div>您上传的<b> " + l.name + " </b>文件不支持预览！</div>");
                    t.css({
                        "width": "100%",
                        "position": "absolute",
                        "top": "50%",
                        "left": 0,
                        "cursor": "default"
                    });
                    s.html("").append(t);
                    setTimeout(function() {
                        t.css("margin-top", -t.outerHeight() / 2);
                        clearTimeout(arguments.callee)
                    },
                    10);
                    return
                }
                var u = c('<img src="' + w + '">');
                if (i.viewImgHorizontal) {
                    u.one("load",
                    function() {
                        var x = this.width,
                        y = this.height;
                        u.css({
                            "position": "absolute",
                            "top": "50%",
                            "left": "50%",
                            "margin-top": -y / 2,
                            "margin-left": -x / 2
                        })
                    })
                }
                s.html("").append(u)
            },
            i.width, i.height, s);
            k[l.id] = [l.size, 0];
            l.rotation = 0
        }
        l.on("statuschange",
        function(u, t) {
            if (t === "progress") {
                q.hide().width(0)
            } else {
                if (t === "queued") {
                    r.off("mouseenter mouseleave");
                    i.showToolBtn && m.remove()
                }
            }
            if (u === "error" || u === "invalid") {
                n(l.statusText);
                i.uploadFailTip && c.isFunction(i.uploadFailTip) && i.uploadFailTip.call(this, r);
                k[l.id][1] = 1
            } else {
                if (u === "interrupt") {
                    n("interrupt")
                } else {
                    if (u === "queued") {
                        k[l.id][1] = 0
                    } else {
                        if (u === "progress") {
                            j.remove();
                            q.css("display", "block")
                        } else {
                            if (u === "complete") {
                                r.append('<span class="success"></span>')
                            }
                        }
                    }
                }
            }
            r.removeClass("state-" + t).addClass("state-" + u)
        });
        if (i.showToolBtn) {
            d(s, r, m, l, p)
        }
        i.onFileAdd && c.isFunction(i.onFileAdd) && i.onFileAdd();
        r.appendTo(o)
    }
    function d(j, n, k, l, m) {
        n.on("mouseenter",
        function() {
            k.stop().animate({
                height: 30
            })
        }).on("mouseleave",
        function() {
            k.stop().animate({
                height: 0
            })
        });
        k.on("click", "span",
        function() {
            var o = c(this).index(),
            p;
            switch (o) {
            case 0:
                m.removeFile(l);
                return;
            case 1:
                l.rotation += 90;
                break;
            case 2:
                l.rotation -= 90;
                break
            }
            if (h) {
                p = "rotate(" + l.rotation + "deg)";
                j.css({
                    "-webkit-transform": p,
                    "-mos-transform": p,
                    "-o-transform": p,
                    "transform": p
                })
            } else {
                j.css("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + (~~ ((l.rotation / 90) % 4 + 4) % 4) + ")")
            }
        })
    }
    function b(k, j) {
        var l = c("#" + k.id);
        delete j[k.id];
        l.off().find(".file-panel").off().end().remove();
        i.onDelete && c.isFunction(i.onDelete) && i.onDelete()
    }
    function f(p, l, o, n, m) {
        var k, j;
        if (p === m.state) {
            return
        }
        l.removeClass("state-" + m.state);
        l.addClass("state-" + p);
        m.state = p;
        switch (m.state) {
        case "pedding":
            o.parent().removeClass("filled");
            o.hide();
            n.refresh();
            break;
        case "ready":
            o.parent().addClass("filled");
            o.show();
            n.refresh();
            break;
        case "uploading":
            l.text("暂停上传");
            c(i.btns.chooseBtn).hide();
            break;
        case "paused":
            l.text("继续上传");
            c(i.btns.chooseBtn).hide();
            break;
        case "confirm":
            l.text("开始上传").addClass("disabled");
            c(i.btns.chooseBtn).hide();
            j = n.getStats();
            if (j.successNum && !j.uploadFailNum) {
                f("finish", l, o, n, m);
                return
            }
            break;
        case "finish":
            j = n.getStats();
            if (j.successNum) {} else {
                m.state = "done";
                location.reload()
            }
            if (!i.duplicate) {
                c(i.btns.chooseBtn).show()
            }
            break
        }
    }
    function e(q) {
        if (!WebUploader.Uploader.support()) {
            if (i.notSupport && c.isFunction(i.notSupport)) {
                i.notSupport.call(this);
                return
            } else {
                throw new Error("WebUploader does not support the browser you are using.")
            }
        }
        if (!q || !c.isPlainObject(q)) {
            throw new Error("必须传递一个包含上传文件必要参数的对象！")
        }
        c.extend(true, i, q);
        i.btns.uploadBtn = c(i.btns.uploadBtn);
        i.wrap = c(i.wrap);
        var u = i.wrap,
        p = c('<ul class="_filelist"></ul>').appendTo(u),
        s = (i.btns.uploadBtn.size() > 0) ? i.btns.uploadBtn: u.find(".uploadBtn"),
        l = 0,
        o = 0,
        t = window.devicePixelRatio || 1,
        k = {
            "state": "pedding"
        },
        j = {},
        r,
        n = i.btns.chooseBtn,
        m = {
            title: "Images",
            extensions: "gif,jpg,jpeg,bmp,png",
            mimeTypes: "image/png,image/gif,image/jpeg,image/jpg,image/bmp"
        };
        if (!i.pictureOnly) {
            m = null
        }
        if (typeof n !== "string" && !g(n) && n[0] && g(n[0])) {
            n = n[0]
        }
        r = WebUploader.create({
            pick: {
                id: n,
                label: i.btns.chooseBtnText || "",
                multiple: i.multiple 
            },
            accept: m,
            auto: i.auto,
            swf: i.swf,
            chunked: true,
            method: i.method.toUpperCase() || "POST",
            server: i.url,
            resize: i.resize,
            sendAsBlob: i.sendAsBlob,
            fileNumLimit: i.maxFileNum,
            fileSizeLimit: i.maxFileTotalSize,
            fileSingleSizeLimit: i.maxFileSize
        });
       
        if (i.datas && c.isPlainObject(i.datas)) {
            r.option("formData", i.datas)
        }
        i.width = !c.isNumeric(parseFloat(i.width)) ? ( - 1 * t) : (parseFloat(i.width) * t);
        i.height = !c.isNumeric(parseFloat(i.height)) ? ( - 1 * t) : (parseFloat(i.height) * t);
        r.on("uploadAccept",
        function(w, v) {
            if (i.onSuccess && c.isFunction(i.onSuccess)) {
                i.onSuccess.apply(this, arguments)
            }
        });
        r.onUploadProgress = function(x, v) {
            var y = c("#" + x.id),
            w = y.find(".progress span");
            w.css("width", v * 100 + "%");
            j[x.id][1] = v
        };
        r.onFileQueued = function(v) {
            l++;
            o += v.size;
            a(v, r, p, j);
            f("ready", s, p, r, k)
        };
        r.onFileDequeued = function(v) {
            l--;
            o -= v.size;
            if (!l) {
                f("pedding", s, p, r, k)
            }
            b(v, j, r)
        };
        r.on("uploadBeforeSend",
        function(w, v) {
            v.rotation = w.file.rotation
        });
        r.on("all",
        function(w) {
            var v;
            switch (w) {
            case "uploadFinished":
                f("confirm", s, p, r, k);            
                window.location.href = base_path + "/addRoomTypePictureTwo.do?branchId="+$("#branchIdTwo").val()+"&scence="+$("#scence").val()+"&roomType="+$("#roomType").val()+"&theme="+$("#theme").val();
                break;
            case "startUpload":
                f("uploading", s, p, r, k);
                break;
            case "stopUpload":
                f("paused", s, p, r, k);
                break;
            }
        });
        
        
      /*  r.on('uploadSuccess', function (file, response) {
        	window.location.href = base_path + "/addBranchPic.do?branchId="+$("#branchId").val();
       });*/
        r.onError = function(v) {
            var w = {
                msg: "",
                code: v
            };
            if (v == "Q_EXCEED_SIZE_LIMIT") {
                w.msg = "单个文件大小超出限制!";
            } else {
                if (v == "Q_EXCEED_NUM_LIMIT") {
                    w.msg = "上传文件数量超出总数量!";
                } else {
                    if (v == "Q_TYPE_DENIED") {
                        w.msg = "上传的文件类型不满足要求!";
                    } else if(v == "F_EXCEED_SIZE"){
                    	w.msg = "文件大小不能超过5M!";
                    } else if (v == "F_DUPLICATE"){
                    	w.msg = "重复图片不可再次上传!";
                    }
                    
                }
            }
            if (i.onError && c.isFunction(i.onError)) {
                i.onError.call(this, w)
            } else {
            	showMsg("上传错误，" + w.msg)
            }
        };
        s.on("click",
        function() {
            if (c(this).hasClass("disabled")) {
                return false
            }
            if (k.state === "ready" || k.state === "paused") {
                r.upload()
            } else {
                if (k.state === "uploading") {
                    r.stop()
                }
            }
        });
        if (i.btns.retryBtn) {
            retryBtn = c(i.btns.retryBtn);
            retryBtn.on("click",
            function() {
                r.retry()
            })
        }
        s.addClass("state-" + k.state);
        return r
    }
    window.uploadImage = e
})(jQuery);


function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}