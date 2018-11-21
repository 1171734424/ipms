<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("themename", request.getAttribute("themename"));
   request.setAttribute("branchname", request.getAttribute("branchname"));
   request.setAttribute("rpkindname", request.getAttribute("rpkindname"));
   request.setAttribute("rproomtype", request.getAttribute("rproomtype"));
   request.setAttribute("rppack", request.getAttribute("rppack"));
   request.setAttribute("rpstatus", request.getAttribute("rpstatus"));
   request.setAttribute("status", request.getAttribute("status"));
   request.setAttribute("rpsetup", request.getAttribute("rpsetup"));
   request.setAttribute("themeid", request.getAttribute("themeid"));
   request.setAttribute("branchid", request.getAttribute("branchid"));
   request.setAttribute("rpking", request.getAttribute("rpking"));
   request.setAttribute("statusid", request.getAttribute("statusid")); 
%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>房租价申请</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common/dialog.css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/reset.css" rel="stylesheet" type="text/css" media="all" />
		<link href="<%=request.getContextPath()%>/css/pmsmanage/roomprice.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/need/laydate.css"/>
		<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/skins/molv/laydate.css"/>
		<style>
	
		
        
        .up_price{
         background:url(../images/rpapply.png) no-repeat center;
        }  
       
       
        .rpapplystatus_select{
         border: 1px solid #4A5064;
         font-size: 15px;
        }
        
        .rpappply_main{
         height: 98%; 
         overflow: hidden;
         padding-right: 10px;
        
        }
        
        .rpapplyoperate_select{
        border: 1px solid #4A5064;
        font-size: 14px;
        }
        
        #rpapplytable{
          display:none;
        }
        .rp_noshow{
         display:none;
        }
        .rptextarea{
         vertical-align: middle;
        } 
		</style>
	</head>
	<body>
	    <form action="" name="form_rpapply" id="form_rpapply">
	    <input id="ruleid" name="ruleid" type="hidden" value=""/>
	    <input id="rproomtype" name="rproomtype" type="hidden" value="${rproomtype}"/>
	    <input id="rpsetup" name="rpsetup" type="hidden" value="${rpsetup}"/>
	    <input id="rpstatusid" name="rpstatusid" type="hidden" value="${statusid}"/>
	    <input id="themeid" name="themeid" type="hidden" value="${themeid}"/>
	    <input id="branchid" name="branchid" type="hidden" value="${branchid}"/>
	    <input id="rpking" name="rpking" type="hidden" value="${rpking}"/>
	    <div class="rp_right_title" style="display:none;">
            <span id="themename" name="themename">${themename}</span>
			<span>|</span>
			<span id="branchname" name="branchname">${branchname}</span>
			<span>|</span>
			<span id="rpkindname" name="rpkindname">${rpkindname}</span>
			<span>|</span>
			<select id="rpstatus" name="rpstatus" class="check rpapplystatus_select" onchange="changestatus()">
		      <c:forEach var="ra" items="${rpstatus}">
			    <option value="${ra.content}">
				   ${ra.paramname}
			     </option>
		      </c:forEach>
		    </select>
		</div>
		<div class="rpappply_main">
				<div class="apply_one">
					<table class="table" id="rpapplytable">
					    <col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
						<tr>
						<td align="right"><label >开始时间：</label></td>
						<td><input id="begindate" name="begindate" type="text" class="rpapply_time_input"/></td>
						<td align="right"><label >结束时间：</label></td>
						<td><input id="enddate" name="enddate" type="text" class="rpapply_time_input"/></td>
						</tr>
<!-- 						<tr> -->
<!-- 						<td align="right"><label >选择规则：</label></td> -->
<!-- 						<td> -->
<!-- 						<select id="adjustRule" name="adjustRule" onchange="chengeSelect()" class="inputStyle rpapply_time_input"> -->
<!-- 						<option value="">-未选择调价规则-</option> -->
<!-- 						</select> -->
<!-- 						</td> -->
<!-- 						 <td align="right"><label >规则描述：</label></td> -->
<!-- 						<td><textarea id="rpremark" name="rpremark" class="rptextarea"  style="margin-left: 2px;width: 95%;height: 30px; padding: 0 4px;" readonly></textarea></td> -->
<!-- 						</tr> -->
						<tr>
					
						   <td align="right"><label >生效周期：</label></td>
						   	<td>
							<div id="normalday">
								<input id="normal_day" name="" type="checkbox" />
						        <label class="label_name_order">平日</label>
						        <label>(</label>
						        <input id="sun_day" name="normal" value="0" type="checkbox" />
						        <label class="label_name_order">周日</label>
						        <input id="mon_day" name="normal" value="1" type="checkbox" />
						        <label class="label_name_order">周一</label>
						        <input id="tue_day" name="normal" value="2" type="checkbox" />
						        <label class="label_name_order">周二</label>
						        <input id="wed_day" name="normal" value="3" type="checkbox" />
						        <label class="label_name_order">周三</label>
						        <input id="thur_day" name="normal" value="4" type="checkbox" />
						        <label class="label_name_order">周四</label>
						        <label>)</label>
						    </div>
						    <div id="weekendday">
								<input id="weekend_day" name="" type="checkbox" />
						        <label class="label_name_order">周末</label>
						         <label>(</label>
						        <input id="fri_day" name="weekend" value="5" type="checkbox" />
						        <label class="label_name_order">周五</label>
						        <input id="satur_day" name="weekend" value="6" type="checkbox" />
						        <label class="label_name_order">周六</label>
						        <label>)</label>
						    </div>										
                        </td>
                        <td align="right"><label >过滤时间：</label></td>
						<td><input id="filterday" name="filterday" type="text" class="rpapply_time_input"/></td>
						
						</tr>
						<tr>
								<td align="right"><label >生效时间：</label></td>
								<td>
								    <select id="operate" name="rpstatus" class="check rpapplyoperate_select">
										    <option value="W">
											   非立即
										     </option>
										     <option value="N">
											   立即
										     </option>
								         </select>	
								</td>
						<td></td>
						<td></td>
						</tr>			
						</table>
						</div>
						<div class="apply_two">
						<table id="rp_apply_data"class="rpapply_table" id="">
					    <col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
					    <thead id="">
						<tr>
							<th class="rpapply_th">房间类型</th>
							<th class="rpapply_th">房型码</th>
							<th class="rpapply_th">门市价</th>
							<th class="rpapply_th">企业价</th>
							<th class="rpapply_th">普卡价</th>
							<th class="rpapply_th">银卡价</th>
							<th class="rpapply_th">金卡价</th>
							<th class="rpapply_th">铂金价</th>
							<th class="rpapply_th">黑钻价</th>		
						</tr>
					     </thead>
						     <c:forEach var="y" items="${rproomtype}">
						       <c:set var="flagy" value="${y.theme}"></c:set>
						        
						         <tr>
						       <td align="right" class="rpapply_table_td"> 
								${y.roomname}</td>
								<td align="center" class="rpapply_table_td">${y.roomtype}</td>
                               <td align="center" class="rpapply_table_td">
							   		<div class="apply_table_edit_input">
								         <input id="${y.roomtype}" name="${y.roomtype}_price" class="apply_table_input" type="text" value="${y.roomprice}" onkeyup="num(this)" onblur="rplaout(this)" maxlength="9"/>
								         <div class="img_div">
								             <img name="imgup" class="rp_img upcolor" src="<%=request.getContextPath()%>/images/rpapplyup.png"/>
								             <img name="imgdown" class="rp_img downcolor" src="<%=request.getContextPath()%>/images/rpapplydown.png"/>
								         </div>
								    </div>
							    </td>
							    <td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_CMJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td" style="display:none;"> <input id="${y.roomtype}_MSJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_PKJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_YKJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_JKJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_BJJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<td align="center" class="rpapply_table_td"> <input id="${y.roomtype}_HZJ" name="${y.roomtype}" class="apply_table_input" type="text" readonly="true"/>
								</td>
								<!--<td align="center" class="rpapply_table_td rp_noshow"><select id="${y.roomtype}_packid"  name="${y.roomtype}_packid" class="check">
								    <option value="">
									       请选择包价
								     </option>
							      <c:forEach var="rpa" items="${rppack}">
								    <option value="${rpa.packid}">
									   ${rpa.packname}
								     </option>
							      </c:forEach>
						         </select>
							     </td>
								--></tr>
							
							</c:forEach>	
						</table>
				</div>
				<div id="tableInfoTitle" class="apply_three">
					<div class="text_margin">
						<label>备注</label>
						<textarea id="rpremark" name="rpremark" class="rptextarea"></textarea>
					</div>
					<div class="div_button button rpapplysubmit_button" role="button"
						onclick="auditSubmitOK();">
						申请
						<!-- <a id="sData">申请</a> -->&nbsp;
					</div>
					<div class="div_button button rpapplyclose_button" role="button"
						onclick="window.parent.parent.JDialog.close();">
						取消
						<!-- <a id="sData">取消</a> -->
					</div>
				
				</div>
		</div>
		</form>
		<script src="<%=request.getContextPath()%>/script/ipms/js/laydate.dev.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script>
	 	var base_path = '<%=request.getContextPath()%>';
	    </script>
	    <script>
	    /*页面加载初始化 加载数据  */
	    $(document).ready(function(){
	        var statusid = $("#rpstatusid").val();
	        $("#rpstatus option[value='"+statusid+"']").attr("selected",true);
	        if(statusid=="2"){
	          $("#rpapplytable").show();
	          $(".apply_one ").css("height", "26%");
	          $(".apply_two ").css("height", "40%");
	        }
	        document.getElementById("normal_day").checked=true;
	        document.getElementById("weekend_day").checked=true;
	        checkedobj("normal");
	        checkedobj("weekend");
	        var rptype =  $("#rproomtype").val();
			var first_rptype = rptype.replace(/=/g,":'");
			var second_rptype = first_rptype.replace(/,/g,"',");
			var third_rptype = second_rptype.replace(/}/g,"'}");
		    var fouth_rptype = third_rptype.replace(/}',/g,"},");
			var array_rptype = eval(fouth_rptype);
			for(var i = 0, l = array_rptype.length; i < l; i++){ 
				var rptypename = array_rptype[i].ROOMTYPE;
				var rptypename_array = document.getElementsByName(rptypename);
				var discount =  $("#rpsetup").val();
				var first_discount = discount.replace(/=/g,":'");
				var second_discount = first_discount.replace(/,/g,"',");
				var third_discount = second_discount.replace(/}/g,"'}");
				var fouth_discount = third_discount.replace(/}',/g,"},");
				var array_discount = eval(fouth_discount);
				    for(var j=0; j<rptypename_array.length; j++ ){
				         var rptypenameid = rptypename_array[j].id;
				         if(rptypenameid==(rptypename+"_"+array_discount[j].PARAMNAME)){
				          var basic_msjid = "#"+rptypename;
				          var basic_msj = $(basic_msjid).val();
				          var discount = array_discount[j].DISCOUNT;
				          var discount_price = (basic_msj*discount)/100;
				          var discount_priceid = "#"+rptypenameid;
				          $(discount_priceid).val(discount_price.toFixed(2));
				         }
				  
				  }
			
				}
	     
				
				
				
			    
			/*	$.ajax({
					url: base_path+"/queryAdjustRules.do",
					type: "post",
					dataType: "json",
					data: {},
					success: function(json) {
						loadAdjustRules(json);
					},
					error: function(json) {
						
					}
				});	*/
				
	    });	
	 
	 /*   function loadAdjustRules(json){
	    	
			var tabledata = "<option value=''>-未选择调价规则-</option>";
	  		$.each(json,function(index){
	  	 
	  	
	  		tabledata = tabledata + "<option value='" +json[index]['RULES_ID']
	  		+"_"+json[index]['RULES_PERIODDETAILS']
	  		+"S"+json[index]['STARTTIME']
	  		+"E"+json[index]['ENDTIME']
	  		+"D"+json[index]['RULES_DESC']+
	  		"'>" +json[index]['RULES_NAME'] + "</option>"
	  		;
	  		
	  		
	  		});
			$("#adjustRule").html(tabledata);	
	    }
	    
	    
	    function chengeSelect(){
	    	
	    	var checkboxAll =  $("input[type=checkbox]");
	    	for (var jj = 0;jj< checkboxAll.length;jj++){
	    		if(checkboxAll[jj].checked) { 
	    			checkboxAll[jj].checked = false; 
		    		}  
	    	}
	    	 
	    	var orignalStr = $("#adjustRule").val();
	    	var ruleid = orignalStr.substring(0,orignalStr.indexOf("_"));
	    	var needStr = orignalStr.substring(orignalStr.indexOf("_")+1,orignalStr.indexOf("S"));
	    	var starttime = orignalStr.substring(orignalStr.indexOf("S")+1,orignalStr.indexOf("E"));
	    	var endtime = orignalStr.substring(orignalStr.indexOf("E")+1,orignalStr.indexOf("D"));
	    	var disc = orignalStr.substring(orignalStr.indexOf("D")+1,orignalStr.length);
	    	var needStrArr = needStr.split(",");
	    	for (var ii = 0;ii<needStrArr.length; ii++){
	    		switch (needStrArr[ii])
	    		{
	    		case '0':
	    			document.getElementById("sun_day").checked = true;
	    			break;
	    		case '1':
	    			document.getElementById("mon_day").checked = true;
	    			break;
	    		case '2':
	    			 document.getElementById("tue_day").checked = true;
	    			 break;
	    		case '3':
	    			 document.getElementById("wed_day").checked = true;
	    			 break;
	    		case '4':
	    			 document.getElementById("thur_day").checked = true;
	    			 break;
	    		case '5':
	    			 document.getElementById("fri_day").checked = true;
	    			 break;
	    		case '6':
	    			 document.getElementById("satur_day").checked = true;
	    			 break;
	    		}
	    		
 
	    	}
	    	$("#begindate").val(starttime);
	    	$("#enddate").val(endtime);
	    	$("#rpremark").val(disc);
	    	$("#ruleid").val(ruleid);
	    	
	    	
	    	if($("#adjustRule").val() != ''){
	    		$("#begindate").attr("disabled",true);
	    		$("#enddate").attr("disabled",true);
	    	}else{
	    		$("#begindate").removeAttr("disabled"); 
	    		$("#enddate").removeAttr("disabled"); 
	    	}
	    }
	    
	    */
	    /*计算价格通用方法  */ 
	    function countprice(obj){
	        var rptype =  $("#rproomtype").val();
			var first_rptype = rptype.replace(/=/g,":'");
			var second_rptype = first_rptype.replace(/,/g,"',");
			var third_rptype = second_rptype.replace(/}/g,"'}");
		    var fouth_rptype = third_rptype.replace(/}',/g,"},");
			var array_rptype = eval(fouth_rptype);
			for(var i = 0, l = array_rptype.length; i < l; i++){ 
				var rptypename = array_rptype[i].ROOMTYPE;
				var rptypename_array = document.getElementsByName(obj);
				var discount =  $("#rpsetup").val();
				var first_discount = discount.replace(/=/g,":'");
				var second_discount = first_discount.replace(/,/g,"',");
				var third_discount = second_discount.replace(/}/g,"'}");
				var fouth_discount = third_discount.replace(/}',/g,"},");
				var array_discount = eval(fouth_discount);
				    for(var j=0; j<rptypename_array.length; j++ ){
				         var rptypenameid = rptypename_array[j].id;
				         if(rptypenameid==(rptypename+"_"+array_discount[j].PARAMNAME)){
				          var basic_msjid = "#"+rptypename;
				          var basic_msj = $(basic_msjid).val();
				          var discount = array_discount[j].DISCOUNT;
				          var discount_price = (basic_msj*discount)/100;
				          var discount_priceid = "#"+rptypenameid;
				          $(discount_priceid).val(discount_price.toFixed(2));
				         }
				  
				  }
			
				}
	    
	    }
	    
	     /*生效周期选中通用方法  */ 
	    function checkedobj(obj){
	        var x=document.getElementsByName(obj);
			for(var i=0; i<x.length; i++ ){
			 var id = x[i].id;
			var chk = document.getElementById(id);
				chk.checked = true;
						                }
	     } 
	       
       
       /*开始时间 */ 
	   laydate({
		elem: '#begindate',
		format: 'YYYY/MM/DD hh:00:00', 
		type: 'datetime',
		istime:true,
		min:laydate.now(1) 
	   })
	   
	   
	    /*结束时间 */ 
	   laydate({
		elem: '#enddate',
		format: 'YYYY/MM/DD hh:00:00', 
		type: 'datetime',
		istime:true,
		min:laydate.now(1) 
	   })
	   
	  	 $(".img_div .upcolor").hover(function(){
		    $(this).attr("src","<%=request.getContextPath()%>/images/upcolor.png");
		},function(){
			$(this).attr("src","<%=request.getContextPath()%>/images/rpapplyup.png");
		});
	   $(".img_div .downcolor").hover(function(){
			$(this).attr("src","<%=request.getContextPath()%>/images/downcolor.png");
	   },function(){
		    $(this).attr("src","<%=request.getContextPath()%>/images/rpapplydown.png");
		});
	  
	/*加按钮   */
	 $("img[name='imgdown']").click(function(){
	      var roomtype = $(this).parent().parent().parent().parent().children("td").eq(1).html();
	      var roompriceid = "#"+roomtype;
	      var roomprice = $(roompriceid).val();
	       if(roomprice>0){
				  roomprice = roomprice-1;
				  $(roompriceid).val(roomprice.toFixed(2));
				  countprice(roomtype);
	     
				  }else{
				    showMsg("房价不能小于0");
				  }
	   });
	   
	   /*减按钮   */
	   $("img[name='imgup']").click(function(){
	      var roomtype = $(this).parent().parent().parent().parent().children("td").eq(1).html();
	      var roompriceid = "#"+roomtype;
	      var roomprice = $(roompriceid).val();
				  roomprice = parseInt(parseInt(roomprice)+1)
				  $(roompriceid).val(roomprice.toFixed(2));
				  countprice(roomtype);
	     
	   });
	   
	    /*失去焦点   */
	   function rplaout(data){
	      var roomprice_blur = data.value;
	      var roompriceid_blur = "#"+data.id;
	       if(!/^\d+|\d+\.\d{1,2}$/gi.test(roomprice_blur)){
	           showMsg("数量必须为正整数，请重新输入");
               $(roompriceid_blur).val("0") ;
                countprice(data.id);
               
	       
	       } else{
	          countprice(data.id);
		     }
	   }
	    
	    /*showMsg   */
	   function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
	   }
		
	    /*input框输入保留俩位小数   */	
	   function num(obj){
			obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
			obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字
			obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个, 清除多余的
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
       }
       
         /*键盘回车事件   */	
         document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];          
             if(e && e.keyCode==13){ // enter 键
			    var s =  $("#rpsetup").val();
				var first = s.replace(/=/g,":'");
				var second = first.replace(/,/g,"',");
				var third = second.replace(/}/g,"'}");
				var fouth = third.replace(/}',/g,"},");
				var array = eval(fouth);
				for(var i = 0, l = array.length; i < l; i++){ 
				var discount = array[i].DISCOUNT
				var paramname = array[i].PARAMNAME
				var beforediscount = $("#MSJ").val();
				 if ( !/^\d+|\d+\.\d{1,2}$/gi.test(beforediscount) ){
                      showMsg("请输入数字（小数点后俩位）")
                       window.setTimeout("location.reload(true)", 1500);
                    }else{
                      var afterdiscount = (beforediscount*discount)/100;
					  var disid = "#"+paramname;
					  $(disid).val(afterdiscount.toFixed(2));
                    }
				
			  }
            }
        }; 
     /*form  Json  */	   
    function parseToJson(msg,end){
			var json = "[{";
			var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
			var t = false;
			for(var i = 0; i<msg2.length; i++){
			  var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
			  for(var j = 0; j<msg3.length; j++){
			    json+="\""+msg3[j]+"\"";
			    if(j+1 != msg3.length){
			      json+=":";
			    }
			    if(t){
			      json+="}";
			      if(i+1 != msg2.length){  //表示是否到了当前行的最后一列
			        json+=",{";
			      }
			      t=false;
			    }
			    if(msg3[j] == end){  //这里的“canshu5”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
			      t = true;
			    }
			  }
			  if(!msg2[i].match(end)){  //同上
			    json+=";";
			  }
			           
			}
			json+="]";

			return json;
			}
			
			 /*平日checkbox  */
			$("#normalday").children('input[type=checkbox]').click(
					function() {
						if ($(this).is(':checked')) {
							if ($(this).attr('id') == "normal_day") {
								       var x=document.getElementsByName("normal");
						                for(var i=0; i<x.length; i++ ){
						                var nurmalid = x[i].id;
						                var chk = document.getElementById(nurmalid);
						                chk.checked = true;
						                }
							} else if($(this).attr('id') != "normal_day"){
							    var z=document.getElementsByName("normal");
							    var a = "1";
								for(var i=0; i<z.length; i++ ){
								var nurmalid =z[i].id;
								var chk = document.getElementById(nurmalid);
								if(chk.checked == false){
								     a = "0"
								   }
								}
								if(a == "0"){
								 var normalchk = document.getElementById("normal_day");
								    normalchk.checked = false;
								    }else if(a == "1"){
								     var normalchk = document.getElementById("normal_day");
								      normalchk.checked = true;
								    }
							
							} 
						}else {
						   if ($(this).attr('id') == "normal_day") {
							 var x=document.getElementsByName("normal");
						                for(var i=0; i<x.length; i++ ){
						                var nurmalid =x[i].id;
						                var chk = document.getElementById(nurmalid);
						                chk.checked = false;
						                }
						          }else if ($(this).attr('id') != "normal_day"){
						                var normalchk = document.getElementById("normal_day");
								         normalchk.checked = false;
						          }
						}
					})
					
					 /*周末checkbox  */
					$("#weekendday").children('input[type=checkbox]').click(
					function() {
						if ($(this).is(':checked')) {
							if ($(this).attr('id') == "weekend_day") {
								       var x=document.getElementsByName("weekend");
						                for(var i=0; i<x.length; i++ ){
						                var weekendid =x[i].id;
						                var chk = document.getElementById(weekendid);
						                chk.checked = true;
						                }
							} else if($(this).attr('id') != "weekend_day"){
							    var z=document.getElementsByName("weekend");
							    var a = "1";
								for(var i=0; i<z.length; i++ ){
								var weekendid =z[i].id;
								var chk = document.getElementById(weekendid);
								if(chk.checked == false){
								     a = "0"
								   }
								} 
								if(a == "0"){
								 var weekendchk = document.getElementById("weekend_day");
								    weekendchk.checked = false;
								    }else if(a == "1"){
								     var weekendchk = document.getElementById("weekend_day");
								      weekendchk.checked = true;
								    }
							
							} 
						} else {
							if ($(this).attr('id') == "weekend_day") {
							 var x=document.getElementsByName("weekend");
						                for(var i=0; i<x.length; i++ ){
						                var weekendid =x[i].id;
						                var chk = document.getElementById(weekendid);
						                chk.checked = false;
						                }
						          }else if ($(this).attr('id') != "weekend_day"){
						                var weekendchk = document.getElementById("weekend_day");
								         weekendchk.checked = false;
						          }
						}
					})
	
					
            /*过滤日期  */
           $("#filterday").bind("click",
				function() {
				    var validstart = $("#begindate").val();
				    var validend = $("#enddate").val();
					if(!validstart){
					      showMsg("价格开始日期不能为空")
					   }else if(!validend){
					     showMsg("价格结束日期不能为空")
					   }else if((new Date(validstart.replace(/\-/g, "\/"))) > (new Date(
								validend.replace(/\-/g, "\/")))){
					     showMsg("价格结束日期不能晚于价格开始日期")
					   }else{var validend = $("#enddate").val();
		           
					
						JDialog.open("", base_path + "/filterDay.do?validstart="+validstart+"&validend="+validend, 800, 430);
						}
					
				})      
        
        /*提交方法  */      
		 function auditSubmitOK(){
		   var ruleid = $("#ruleid").val();
		   var theme = $("#themeid").val();
		   var branchid = $("#branchid").val();
		   var rpkind = $("#rpking").val();
		   var validstart = $("#begindate").val();
		   var validend = $("#enddate").val();
		   var validday = "";
		   var n=document.getElementsByName("normal");
			   for(var i=0; i<n.length; i++ ){
			       var nurmalid =n[i].id;
				   var nurmalvalue =n[i].value;
				   if(document.getElementById(nurmalid).checked==true){
				     validday = validday+nurmalvalue
				     }
					}
		   var w=document.getElementsByName("weekend");
			   for(var j=0; j<w.length; j++ ){
			       var weekendid =w[j].id;
				   var weekendvalue =w[j].value;
				   if(document.getElementById(weekendid).checked==true){
				     validday = validday+weekendvalue
				     }
					}
			var filterday = $("#filterday").val();
			var strs= new Array(); 
            strs=filterday.split("至"); 
            var filterdaystart,filterdayend;
            for (i=0;i<strs.length ;i++ ) 
             { 
              if(i==0){
                filterdaystart = strs[i];
              }else{
                filterdayend = strs[i]
              }

             } 
			var operate = $("#operate").val();
			var status = $("#rpstatus").val(); 
			var remark = $("#rpremark").val();
			if(status=="2"){
			  if(!validstart){
			      showMsg("价格开始日期不能为空");
			      return;
			   }
			    if(!validend){
			     showMsg("价格结束日期不能为空");
			      return;
			   } 
			   if((new Date(validstart.replace(/\-/g, "\/"))) > (new Date(
						validend.replace(/\-/g, "\/")))){
			     showMsg("价格结束日期不能早于价格开始日期");
			      return;
			   } 
			   
			   if(filterday){
			   
					     if(((new Date(filterdaystart.replace(/\-/g, "\/"))) < (new Date(
								validstart.replace(/\-/g, "\/"))))||((new Date(filterdaystart.replace(/\-/g, "\/"))) > (new Date(
								validend.replace(/\-/g, "\/"))))){
							       showMsg("过滤起始时间不能早于调整起始时间或者晚于调整结束时间");
							         return;
							   }else if(((new Date(filterdayend.replace(/\-/g, "\/"))) < (new Date(
												validstart.replace(/\-/g, "\/"))))||((new Date(filterdayend.replace(/\-/g, "\/"))) > (new Date(
												validend.replace(/\-/g, "\/"))))){
							       showMsg("过滤结束时间不能早于调整起始时间或者晚于调整结束时间");
							         return;
							   }
							  
			   }
			}else{
			    operate = "N";
			}
			     $.ajax({
							url : base_path + "/rpOperate.do",
							type : "post",
							data : {
								'theme':theme,
								'branchid':branchid,
								'rpkind':rpkind,
								'validstart':validstart,
								'validend':validend,
								'validday':validday,
								'filterday':filterday,
								'operate':operate,
								'status':status,
								'remark':remark
							},
							success : function(json) {
								if (1 == json.result) {
								  if(isNaN(json.message)){
								      showMsg(json.message);
								  }else{
								     var applyid = json.message;
								      var rptype =  $("#rproomtype").val();
									  var first_rptype = rptype.replace(/=/g,":'");
									  var second_rptype = first_rptype.replace(/,/g,"',");
									  var third_rptype = second_rptype.replace(/}/g,"'}");
								      var fouth_rptype = third_rptype.replace(/}',/g,"},");
									  var array_rptype = eval(fouth_rptype);
									  for(var i = 0, l = array_rptype.length; i < l; i++){
										  var rptypename = array_rptype[i].ROOMTYPE;
										  var rptypename_array = document.getElementsByName(rptypename);
										  var rprice = $("#"+rptypename).val();
										  var packid = $("#"+rptypename+"_packid").val();
										   $.ajax({
												url : base_path + "/rpOperatedetail.do",
												type : "post",
												dataType : "json",
												data : {
												    'theme':theme,//
													'branchid':branchid,//
													'rpkind':rpkind,//
													
													'validstart':validstart,//
													'validend':validend,//
													'validday':validday,//
													'ruleid':ruleid,//
													
													
													'operate':operate,
													'status':status,
													'applyid' : applyid,
													'rptypename' : rptypename,//
													'rprice' : rprice,//
													'packid' : packid
												},
												success : function(json) {
													if (1 == json.result) {
													    showMsg("申请成功！");
													    window.parent.setTimeout("window.parent.JDialog.close();", 1800);
														
													} else {
														showMsg("申请失败！");
														window.setTimeout("window.parent.JDialog.close();", 1800);
													}
												},
												error : function() {
													showMsg("操作失败，请重新操作！");
													 window.setTimeout("window.parent.JDialog.close();", 1800);
												}
											    });
											   }
								  }
								} else {
									showMsg("申请失败！");
									window.setTimeout("location.reload(true)", 1800);
								}
							},
							error : function() {
								showMsg("操作失败，请重新操作！");
								window.setTimeout("location.reload(true)", 1800);
							}
						    });
		  
		   
		 
		 }
	 </script>
	</body>
</html>
