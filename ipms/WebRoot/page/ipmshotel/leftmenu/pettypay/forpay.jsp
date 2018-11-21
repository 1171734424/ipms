 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("listpetty", request.getAttribute("listpetty"));
%>

<!DOCTYPE HTML>
<html class="whitebg">
  <head>
    <title>投缴页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/shopsell/shopsell.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
	<style>
	.imooc_order{
		top:-10px;
		left:96%;
	}
	.yfk{
	    color: red;
	    border: none;
	    font-size: 15px;
	    margin-left: 5px;
	    width: 35px;
	}
	.yfkspan{
		margin-left: 10px;
	}
	.acount{
		width:50px;
		text-align:center;
	}
	.totaltable{
		border:none;
		margin-top: 4%;
	}
	.totalinput{
		color:red;	
		border:none;
	}
	.pettydata{
	    display:none;
	}
	.pettypayinput{
	margin: 3% 2% 3% 2%;
    height: 30px;
    width: 80%;
	
	}
	.pettypaydata{
	 margin: 3% 2% 3% 2%;
    height: 30px;
    width: 80%;
    border:none;
	}
	.pettypaycount{
	margin: 1% 1% 1% 17%;
	}
	
</style>
  </head>
  <body>
     <div id="" class="">
     	<div class="pettydata">
	        <table id="myTable" class="myTable" border="0" width="100">
				<thead id="log">
					<tr>
						<th class="header">数据号</th>
						<th class="header">投缴金额</th>
					</tr>	
				</thead>
				<tbody id="info"> 
			        <c:forEach items="${listpetty}" var="listpetty" varStatus="aaa">
					<tr>
						<td align="center" id="${aaa.index}logid" name="logid">${listpetty.logId}</td>
						<td align="center" name="payvalue" id="${aaa.index}payvalue">${listpetty.paymentValue}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div><!--
		
			    <div class="pettypaycount"><span>应缴金额：</span><input class="totalinput" id="totalprice" value="0" disable="true"/></div>
			    --><table>
			    <col width="31%"/>
			     <col width="69%"/>
			    <tr><td align="right"><span>应缴金额：</span></td><td><input class="pettypaydata" id="totalprice" value="0" disable="true" disabled="true"/></td></tr>
			    <tr><td align="right"><span>本次缴纳：</span></td><td><input class="pettypayinput" id="currpay"  onkeyup="num(this)" /></td></tr>
			    <tr><td align="right"><span>缴纳凭证：</span></td><td><input class="pettypayinput" id="voucherpay"  onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="50"/></td></tr>
			    </table>			
           <div class="sbotton_gdspage">
             <input id="nowpay" name="" type="button" class="submitbotton_gdspage clean enter" style="float:right;" value="投缴" onclick="pay()"/>
             <input id="" name="" type="button" class="submitbotton_gdspage clean" style="float:right;" value="重置" onclick="location.reload(true)"/>
           </div>
        </div>
		<script>
		$(document).ready(function(){
		$("#currpay").focus();
		 var alltext= document.getElementsByName("payvalue"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					
				  $("#totalprice").val(totalprice);
            
          })
           
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
	     
	     
	     function pay(){
	         var currpay = $("#currpay").val();
	         var shouldpay = $("#totalprice").val();
	         var voucherpay = $("#voucherpay").val();
	          var logids = document.getElementsByName("logid"); 
	          if(!currpay){
	            showMsg("投缴金额不能为空");
	          }else if(!voucherpay){
	             showMsg("凭证号不能为空");
	          }else if(!/^\d+|\d+\.\d{1,2}$/gi.test(currpay)){
	             showMsg("投缴金额必须为数字或者保留俩位小数");
	          
	          }else if(!/^\d+$/.test(voucherpay)){
	             showMsg("凭证号必须为数字");
	          
	          }else if(currpay!=shouldpay){
	             showMsg("输入金额必须与应缴金额一致");
	          
	          }else{
	          
	            for(var i =0;i<logids.length;i++)
					{
					          var logid = logids[i].innerHTML;
							  $.ajax({
								url : base_path + "/payData.do",
								type : "post",
								dataType : "json",
								data : {
									'logid' : logid,
									'currpay' : currpay,
									'voucherpay' : voucherpay
								},
								success : function(json) {
									if (1 == json.result) {
									    showMsg("投缴成功！");
									    window.setTimeout("location.reload(true)", 1800);
									    window.setTimeout("window.parent.JDialog.close();", 1800);
										
									} else {
										showMsg("投缴失败！");
										window.setTimeout("location.reload(true)", 1800);
										 window.setTimeout("window.parent.JDialog.close();", 1800);
									}
								},
								error : function() {
									showMsg("操作失败，请重新操作！");
									window.setTimeout("location.reload(true)", 1800);
									 window.setTimeout("window.parent.JDialog.close();", 1800);
								}
							    });
					     
					}
	          
	          
	          }
	     
	     }
          
			function sub(data){
			       var spanid = data.id;
			       var acountid = spanid.replace('sub', 'acount');
			       var acountidid = "#"+acountid;
			       var gdspriceid = spanid.replace('sub', 'gdsprice');
			       var gdspriceidid = "#"+gdspriceid;
			       var gdsacountid = spanid.replace('sub', 'gdsacount');
			       var gdsacountidid = "#"+gdsacountid;
			       var c = $(acountidid).val();
			    if(c>=2){
				  c = c-1;
				  $(acountidid).val(c);
				  var gdsprice = $(gdspriceidid).html()
				  var allpricr = parseInt(c*gdsprice);
				  $(gdsacountidid).html(allpricr);
				  var alltext= document.getElementsByName("gdsacount"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					 var allacounttext= document.getElementsByName("acount"); 
					var totalnumber = 0;
					for(var i =0;i<allacounttext.length;i++)
					 
					{
					          totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					         
					     
					}
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				  
				}else{
				  showMsg("数量不可为小于1的数字！");
				  }
				}
				
			function add(data){
			       var spanid = data.id;
			       var acountid = spanid.replace('sub', 'acount');
			       var acountidid = "#"+acountid;
			       var gdspriceid = spanid.replace('sub', 'gdsprice');
			       var gdspriceidid = "#"+gdspriceid;
			       var gdsacountid = spanid.replace('sub', 'gdsacount');
			       var gdsacountidid = "#"+gdsacountid;
			       var c = $(acountidid).val();
                   c = parseInt(parseInt(c)+1);
				   $(acountidid).val(c);
				   var gdsprice = $(gdspriceidid).html()
				  var allpricr = parseInt(c*gdsprice);
				  $(gdsacountidid).html(allpricr);
				  var alltext= document.getElementsByName("gdsacount"); 
				  var totalprice = 0;
					for(var i =0;i<alltext.length;i++)
					{
					          totalprice = parseInt(totalprice)+parseInt(alltext[i].innerHTML);
					     
					}
					var allacounttext= document.getElementsByName("acount"); 
					var totalnumber = 0;
					for(var i =0;i<allacounttext.length;i++)
					{
					          totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					         
					     
					}
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				 }
				 
			function lacount(){
			
			    var allacounttext= document.getElementsByName("acount"); 
			    var allpricetext= document.getElementsByName("gdsinprice"); 
			    var totalnumber = 0;
			    var totalprice = 0;
			    var re = /^[0-9]+$/ ;
		
			    for(var i =0;i<allacounttext.length;i++) 
					{    if(re.test(allacounttext[i].value)){
					     totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					     totalprice = parseInt(totalprice)+parseInt(allacounttext[i].value*allpricetext[i].innerHTML);		
					    } else{ 
					
					     showMsg("数量必须为正整数，请重新输入");
				          window.setTimeout("location.reload(true)", 1500);
					    }
					    			     
					}
					
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
				 
			    
			  
			}
			
			document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==13){ // enter 键
                  var allacounttext= document.getElementsByName("acount"); 
			    var allpricetext= document.getElementsByName("gdsinprice"); 
			    var totalnumber = 0;
			    var totalprice = 0;
			    var re = /^[0-9]+$/ ;
		
			    for(var i =0;i<allacounttext.length;i++) 
					{    if(re.test(allacounttext[i].value)){
					     totalnumber = parseInt(totalnumber)+parseInt(allacounttext[i].value);
					     totalprice = parseInt(totalprice)+parseInt(allacounttext[i].value*allpricetext[i].innerHTML);		
					    } else{ 
					
					     showMsg("数量必须为正整数，请重新输入");
				          window.setTimeout("location.reload(true)", 1500);
					    }
					    			     
					}
					
				  $("#totalprice").val(totalprice);
				  $("#totalnumber").val(totalnumber);
			    
            }
        }; 
        
        
        function gdspaysubmit(){
            var gdscheckid = $("#gdscheckid").val();
            var gdsproject = $("#gdsproject").val();
            var gdsprojectname = $("#gdsproject").find("option:selected").text();
            var gdsroomid = $("#gdsroomid").val();
             var cashpayvalue = $("#cashpayvalue").val();
            var gdsprojectpay = $("#gdsprojectpay").val();
            var gdsprojectpayname = $("#gdsprojectpay").find("option:selected").text();
            var gdsworkbill = $("#gdsworkbill").val();
            var gdsworkbillname = $("#gdsworkbill").find("option:selected").text();
            var cardpayvalue = $("#cardpayvalue").val();
            var alltext= document.getElementsByName("gdsacount"); 
					for(var i =0;i<alltext.length;i++)
					{
		               var gd = "#"+i+"gdsid";${aaa.index}acount
		               var goodsid = $(gd).text();
		               var gr = "#"+i+"gdsprice";
		               var goodsprice = $(gr).text();
		               var ga = "#"+i+"gdsacount";
		               var gdsacount = $(ga).text();
		                var ac = "#"+i+"acount";
		               var acount = $(ac).val();
		              if($("#roompay").is(':checked')){
           if(gdsroomid){
               $.ajax({
					url : base_path + "/hotelgdsRoompay.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsproject' : gdsproject,
						'gdsprojectname' : gdsprojectname,
						'totalprice' : gdsacount,
						'gdsroomid' : gdsroomid,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("挂账成功！");
						    window.setTimeout("location.reload(true)", 1800);
						    window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("挂账失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
           }else{
              showMsg("请先选择挂账房间");
           }
           
        }else if($("#cashpay").is(':checked')){
           if(cashpayvalue){
           var totalprice = $("#totalprice").val();
           if(cashpayvalue==totalprice){
           
            $.ajax({
					url : base_path + "/hotelgdsRoompaycash.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsprojectpay' : gdsprojectpay,
						'gdsprojectpayname' : gdsprojectpayname,
						'gdsworkbill' : gdsworkbill,
						'gdsworkbillname' : gdsworkbillname,
						'totalprice' : gdsacount,
						'cashpayvalue' : cashpayvalue,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("售卖成功！");
						    window.setTimeout("location.reload(true)", 1800);
						     window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("售卖失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
           
           }else{
             showMsg("需支付金额与输入金额不等，请重新输入");
           
           }
             
             
           }else{
              showMsg("请输入支付金额");
           }
        }else if($("#cardpay").is(':checked')){
             if(cardpayvalue){
                 if(cardpayvalue.length>6){
                    showMsg("凭证号长度不能超过6位");
                 }else{
                  $.ajax({
					url : base_path + "/hotelgdsRoompaycard.do",
					type : "post",
					dataType : "json",
					data : {
						'gdscheckid' : gdscheckid,
						'gdsprojectpay' : gdsprojectpay,
						'gdsprojectpayname' : gdsprojectpayname,
						'gdsworkbill' : gdsworkbill,
						'gdsworkbillname' : gdsworkbillname,
						'totalprice' : gdsacount,
						'cardpayvalue' : cardpayvalue,
						'gdsid' : goodsid,
						'totalnumber' : acount,
						'gdsprice' : goodsprice
					},
					success : function(json) {
						if (1 == json.result) {
						    showMsg("售卖成功！");
						    window.setTimeout("location.reload(true)", 1800);
						     window.setTimeout("window.parent.JDialog.close();", 1800);
							
						} else {
							showMsg("售卖失败！");
							window.setTimeout("location.reload(true)", 1800);
							 window.setTimeout("window.parent.JDialog.close();", 1800);
						}
					},
					error : function() {
						showMsg("操作失败，请重新操作！");
						window.setTimeout("location.reload(true)", 1800);
						 window.setTimeout("window.parent.JDialog.close();", 1800);
					}
				    });
				    }
             }else{
                showMsg("请先输入凭证号");
             }
           
           }else{
           showMsg("请先选择付款方式");
        }
       
					     
					} 
            

           
        
        
        }
        
        
	</script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
