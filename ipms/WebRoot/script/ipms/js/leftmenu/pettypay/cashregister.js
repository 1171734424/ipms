   var base_path = "<%=request.getContextPath()%>"; 

Date.prototype.Format = function (fmt) { //author: meizz 
		   var o = {
				"M+": this.getMonth() + 1, //月份 
				"d+": this.getDate(), //日 
				"h+": this.getHours(), //小时 
				"m+": this.getMinutes(), //分 
				"s+": this.getSeconds(), //秒 
				"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
				"S": this.getMilliseconds() //毫秒 
			};
			if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
			}
			
		  $(document).ready(function(){
		   var pagetype = $("#shifttype").val();
		   if(pagetype=="innormal"){
		      	$(".tab_two").css("display", "none");
		      	$(".tab_two").css("font-family","Microsoft YaHei");
		      	$(".close_span").css("display", "none");
		        $(".check_one ul li").css("margin", "25px 0px 25px 20px");
		        $(".label_name").css("width", "115px");
		        $(".label_name").css("font_size", "0.89rem");
		        $(".check").css("width", "229px");
		        $(".textarea").css("width", "169px");
		        $(".left_div").css("color", "red","font-size","20px");
		        $(".tab_one").css("font-size","20px");
		        $(".tab_one").css("font-family","Microsoft YaHei");
		        $(".left_div").css("font-size","20px");
		        $(".left_div").css("font-family","Microsoft YaHei");
		        $(".left_check").css("margin","0px 94px");
		        $(".card ul li").css("margin", "15px 0px 13px 17px");
		        $(".check_one").css("padding-left", "52px");
		        $(".content_color  textarea").css("width", "81.6%");
		        $(".editdata_mss").css("margin-top", "10%");
		         $(".editdata_mss").css("height", "0");
		        $("#remark").css("height", "11%");
		         $("#invoiceno").css("margin", "10px 0px 0px 10px");
		   }
		  
		  
		  
		    var now = new Date();
            var time = now.Format("yyyy/MM/dd");
            var b =document.getElementById ("nowdate");
            b.innerHTML = "营业日 :"+time;
		    var boxname = $("#boxname").val();
	        $("#cashbox option[value='"+boxname+"']").attr("selected",true);
	        $("#payday").val(time);
	        var cashcountout = ($("#cashcountout").val()).replace('-', '');
	        $("#cashout").val(cashcountout);
	        var boxcount = $("#boxcount").val();
	        var cashin = $("#cashin").val();
	        var cashout = $("#cashout").val();
	        var lastshiftvalue = $("#lastshiftvalue").val();
	        var givepay = "0"
	        if((parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue))>0){
	           givepay = (parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue)).toFixed(2);
	        }
	        $("#givecash").val(givepay);
	         var boxhave = boxcount;
	        if(givepay<0){
	           boxhave =  (parseFloat(boxcount)+parseFloat(cashin) - parseFloat(cashout)- parseFloat(lastshiftvalue)).toFixed(2);	
	        }
	           
	        $("#boxnow").val(boxhave);
	         var needcash = "0";
	        if((parseFloat(boxcount) - parseFloat(boxhave))>0){
	         needcash = (parseFloat(boxcount) - parseFloat(boxhave)).toFixed(2);
	        }
	        $("#fixcash").val(needcash);
		  });
		 
		  function showMsg(msg, fn) {
				if (!fn) {
				TipInfo.Msg.alert(msg);
				   } else {
				TipInfo.Msg.confirm(msg, fn);
				  }
		   }
		  
		  function num(obj) {
				 obj.value = obj.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
				 obj.value = obj.value.replace(/^\./g, ""); // 验证第一个字符是数字
				 obj.value = obj.value.replace(/\.{2,}/g, "."); // 只保留第一个, 清除多余的
				 obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$",
							".");
				 obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
				}
				
			/*备用金修改··*/
			function changetwo() {
		        $("#goodsIframe").attr("src","cashUpdate.do");
				//document.getElementById("a").innerHTML = '<iframe src="cashupdate.jsp" width="100%" height="100%" frameborder=no  scrolling="no"></iframe>'
			}
			
			$(".left_check ul li").on("click", function() {
				$(this).addClass("tab_select");
				$(this).siblings().removeClass("tab_select");
				$(".ifa").css("display", "block");
				$("#a").css("display", "none");
			});
			
			$(".tab_two").on("click", function() {
				$("#a").css("display", "block");
				$(".ifa").css("display", "none");
			});
			
			
			function cashshift(){
			  var boxnow =  $("#boxnow").val();
			  var submitstaff = $("#submitstaff").val();
			  var branchid = $("#branchid").val();
			  var shift = $("#shift").val();
			  var boxname = $("#boxname").val();
			  var shifterid = $("#shifterid").val();
			  var cashin = $("#cashin").val();
			  var cashout = $("#cashout").val();
			  var givecash = $("#givecash").val();
			  var lastshiftvalue = $("#lastshiftvalue").val();
			  var fixcash = $("#fixcash").val();
			  var cardin = $("#cardin").val();
			  var cards = $("#cards").val();
			  var payday = $("#payday").val();
			  var depositno = $("#depositno").val();
			  var invoiceno = $("#invoiceno").val();
			  var remark = $("#remark").val();
			  if(!submitstaff){
			     showMsg("确认人不能为空")
			  }else if(!/^\d+|\d+\.\d{1,2}$/gi.test(boxnow)){
			     showMsg("备用金应为数字或保留俩位小数，为空请输入零！");
			     $("#boxnow").val("0");
			  }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashin)) {
			     showMsg("现金收入应为数字或保留俩位小数，为空请输入零！");
			     $("#cashin").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cashout)) {
			     showMsg("现金支出应为数字或保留俩位小数，为空请输入零！");
			     $("#cashout").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(givecash)) {
			     showMsg("投缴金额应为数字或保留俩位小数，为空请输入零！");
			     $("#givecash").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(lastshiftvalue)) {
			     showMsg("补上班次备用金额应为数字或保留俩位小数，为空请输入零！");
			     $("#lastshiftvalue").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(fixcash)) {
			     showMsg("本次需补备用金额应为数字或保留俩位小数，为空请输入零！");
			     $("#fixcash").val("0");
		      }else if (!/^\d+|\d+\.\d{1,2}$/gi.test(cardin)) {
			     showMsg("银行卡收入应为数字或保留俩位小数，为空请输入零！");
			     $("#cardin").val("0");
		      }else if (!/^\d+$/.test(cards)) {
				showMsg("银行卡笔数必须为数字，为空请输入零！");
				$("#cards").val("0");
			  } else{
			  
			     if (depositno) {
					if (!/^\d+$/.test(depositno)) {
						showMsg("押金收据编号必须为数字");
						$("#depositno").val("");
						return;
					} else {
						 depositno = $("#depositno").val();
					}
			    } 
			    if (invoiceno) {
					if (!/^\d+$/.test(invoiceno)) {
						showMsg("发票编号必须为数字");
						$("#invoiceno").val("");
						return;
					} else {
						 invoiceno = $("#invoiceno").val();
					}
			    }
				 
			  
			  } 

			}