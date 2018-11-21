<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
request.setAttribute("useEasyUIGird", "useEasyUIGird");
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>登录IP管理</title>
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<style type="text/css">
			.main {
				width: 100%;
				margin: 0px auto;
				margin-top: 5px;
				height: 450px;
				font-family: Microsoft YaHei;
			}
			.area_hy {
				height: 426px;
				border: 1px solid #c3c3c3;
			}
			
			.area_hy_title {
				height: 25px;
			}
			
			.area_hy_title {
				border-bottom: 1px solid #C3C3C3;
			}
			
			#ip_manage ul,#ip_manage_oper ul,.ip_manage_content ul{
				list-style: none;
				margin: 0px;
				padding: 0px;
			}
			
			#ip_manage ul li,#ip_manage_oper ul li{
				width: 52px;
				height: 18px;
				line-height: 18px;
				text-align: center;
				float: left;
				color: #565656;
				font-size: 12px;
				cursor: pointer;
				background: url("./images/sel.png") -126px -7px no-repeat;
			}
			#ip_manage ul li,#ip_manage_oper ul li{
				background:none;
				width:80px;
				margin-left:3px;
				margin-top:3px;
				border:1px solid #c3c3c3;
				background-color:#e7e7e7;
			}
			.ip_manage_content ul li{
				display:none;
			}
			#ip_manage ul li.active,#ip_manage_oper ul li active{
				background-color:#FFFFFF;
			}
			.ip_manage_content ul li.active{
				display:block;
			}
			
			div_top_title{
				width: 618px;
				padding-left:10px;
				height: 26px;
			}
			.div_out{
				border: 1px solid #c9eafb;
				width: 750px;
				height: 20px;
				display: none;
				position:absolute;
				margin-top:26px;
				z-index: 100;
			}
			.div_top_title span{
				color: #ffffff;
				font-size: 12px;
				line-height: 18px;
			}
			
			.div_content{
				height: 50px;
				width: 800px;
				background-color: #ebf8fe;
			}
			
			.td_title{
				font-family: Microsoft YaHei;
				font-size: 13px;
				text-align: left;
				height: 30px;
				width: 80px;
			}
			.td_content{
				font-family: Microsoft YaHei;
				font-size: 13px;
				text-align: left;
				color: #c3c3c3;
				height: 30px;
			}
			.td_content input{
				font-family: Microsoft YaHei;
				font-size: 13px;
				width: 120px;
				height: 15px;
				border: 1px solid #c3c3c3;
			}
			
			.fh_date_title {
				color: #FFFFFF;
				font-size: 13px;
			}
			
			.btn_title {
				font-family: Microsoft YaHei;
				font-size: 13px;
				height:25px;
			}
		</style>
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script type="text/javascript">
			var url = basePath + 'do?invoke=com.suypower.cppsp.moudle.ipfilter.web.IPFilterAction';
			var currnamelist = 0, currname = "apply";
			
			jQuery(document).ready(function() {
				getIPNameList(currname);
				
				jQuery("#ip_manage ul li").click(function(){
			    	var n = jQuery(this);
			    	jQuery("#ip_manage ul li,.ip_manage_content ul li").removeClass("active");
			    	n.addClass("active");
			    	jQuery("#div_addLxr").hide();
			    	jQuery(".ip_manage_content ul li").each(function() {
			    		var ns = jQuery(this);
			    		if(ns.attr("name") == n.attr("name")){
			    			ns.addClass("active");
			    			getIPNameList(ns.attr("name"));
			    			currnamelist = switchIpListType(ns.attr("name"));
			    			currname = n.attr("name");
			    			
		    				jQuery("#batchdelete").show();
			    			if(n.attr("name") != "apply"){
		    					jQuery("#batchapply").hide();
			    			} else {
		    					jQuery("#batchapply").show();
			    			}
			    		}
			    	});
			    });
			    
			    jQuery("#ip_manage_oper ul li").click(function(){
			    	var n = jQuery(this);
			    	jQuery("#ip_manage_oper ul li,.ip_manage_content_oper ul li").removeClass("active");
			    	n.addClass("active");
			    	if (n.attr("name") == "addlist") {
			    		jQuery("#div_addLxr").show();
			    	} else if (n.attr("name") == "batchapply") {
			    		batchapply();
			    	} else if (n.attr("name") == "batchdelete") {
			    		batchdelete();
			    	}
			    });
			});
		    
			function getIPNameList(nl, args) {
				jQuery("#" + nl).datagrid({
					url : url + "@queryIPNameList&listtype=" + switchIpListType(nl) + "&args=" + (args ? args : ""),
					width : 979,
					height: 400,
					rownumbers : true,
					fitColumns : true,	
					pagination : true,
					singleSelect : false,
					columns : [[
							{title : '',field : 'ipid',	align : 'center', width : 5, checkbox : true}, 
					        {title : '用户名',	field : 'username',	align : 'left', width : 160}, 
							{title : 'IP',	field : 'ipaddress',	align : 'center',width : 180}, 
							{title : '操作',	field : 'operation', align : 'center',width : 200,
								formatter:function(value, data){
									if(nl == "apply") {
										return "<input type='button' class='btn_title' value='开通' onclick='apply(" + data.ipid + ");'/>  <input type='button' class='btn_title' value='拒绝' onclick='deleteMsg(" + data.ipid + ");'/>";
									} else if (nl == "whitenl") {
										return "<input type='button' class='btn_title' value='删除' onclick='deleteMsg(" + data.ipid + ");'/>";
									} else if (nl == "blacknl") {
										return "<input type='button' class='btn_title' value='删除' onclick='deleteMsg(" + data.ipid + ");'/>";
									}
								}
							}]],
			   		onLoadSuccess: function(jsonData) {
			   		}
				});
			   		
				jQuery("#" + nl).datagrid('getPager').pagination({
			        beforePageText:'第',
			        afterPageText:'页&nbsp;&nbsp;&nbsp;共 {pages} 页',
			        displayMsg:'当前显示 {from} - {to} 条记录  共 {total} 条记录'
			    });
			}
			
			function add() {
				if(!jQuery("#username").val()) {
					alert("请填写用户名!");
					return false;
				} else if (jQuery("#ipaddress").val()) {
					var ip = jQuery("#ipaddress").val().split("/");
					if(!regExIP(ip[0]) || ip[1] > 30 || ip[1] <= 0) {
						alert("请填写正确的IP地址!");
						return false;
					}
				}
				
				jQuery.ajax({
					url: url + "@addIPNameList&username=" + jQuery("#username").val() + "&ipaddress=" + jQuery("#ipaddress").val() + "&listtype=" + jQuery("#listtype").val(),
					success: function (data) {
						if(data == "nofituser"){
							alert("用户名不存在!");
						} else if (data == "sucess"){
							alert("添加完成!");
						} else if (data == "fail"){
							alert("添加失败!");
						}
					}
				});
			}
			
			function apply() {
				jQuery.ajax({
					url: url + "@updateIPNameList&id=" + arguments[0] + "&listtype=1",
					success: function (data) {
						if (data == "sucess"){
							getIPNameList(currname);
							alert("开通完成!");
						} else if (data == "fail"){
							alert("开通失败!");
						}
					}
				});
			}
			
			function deleteMsg() {
				jQuery.ajax({
					url: url + "@deleteIPNameList&id=" + arguments[0],
					success: function (data) {
						if (data == "sucess"){
							getIPNameList(currname);
							alert("删除完成!");
						} else if (data == "fail"){
							alert("删除失败!");
						}
					}
				});
			}
			
			function query() {
				getIPNameList(currnamelist, jQuery("#qyinfo").val());
			}
			
			function switchIpListType() {
				switch(arguments[0]) {
					case "blacknl": return 0; break;
					case "whitenl": return 1; break;
					case "apply": return 2; break;
				}
			}
			
			function batchapply() {
				var ids = "";
				var items = $("#" + currname).datagrid("getSelections");
	  			for (var i = 0; i < items.length; i++) {
	  				ids += items[i].ipid + (i == items.length - 1 ? "" : ",");
	  			}
	  			apply(ids);
			}
			
			function batchdelete() {
				var ids = "";
				var items = $("#" + currname).datagrid("getSelections");
	  			for (var i = 0; i < items.length; i++) {
	  				ids += items[i].ipid + (i == items.length - 1 ? "" : ",");
	  			}
	  			deleteMsg(ids);
			}
			
			function regExIP() {
				var partten = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
				return partten.test(arguments[0]);
			}
			
		</script>
	</head>
	<body style="padding: 0; margin: 0;">
		<div class="main">
			<div class="area_hy">
				<div class="area_hy_title">
					<div style="float:left" id="ip_manage">
						<ul>
							<li class="active" name="apply">
								开通IP
							</li>
							<li name="whitenl">
								白名单
							</li>
							<li name="blacknl">
								黑名单
							</li>
						</ul>
					</div>
					&nbsp;
					<div style="float:left" id="ip_manage_oper">
						<ul>
							<li name="addlist">
								添加
							</li>
							<li name="batchapply" id="batchapply">
								批量开通
							</li>
							<li name="batchdelete" id="batchdelete">
								批量删除
							</li>
						</ul>
					</div>
					<div style="float: right; padding-right: 18px;display: none;" id="searchcon">
						<label class="fh_date_title" style="color: #767d7c;">
							查询(IP或用户名)
						</label>
						<input type="text" id="qyinfo" name="qyinfo" value=""/>
						<input class="btn_title" type="button" value="查询" onclick="query();"/>
					</div>
					<div class="div_out" style="height: 50px;" id="div_addLxr">
		            	<div class="div_top_title"></div>
		            	<div class="div_content" style="height: 40px;">
		            		<table style="width: 750px;height: 40px;text-align: right;margin-left: 20px;margin-right: 20px;">
		            			<tr>
		            				<td class="td_title">用户名：</td>
		                    		<td class="td_content"><input type="text" name="username" id="username"><font color="red">*</font></td>
		                    		<td class="td_title">IP地址：</td>
		                    		<td class="td_content"><input type="text" name="ipaddress" id="ipaddress"></td>
		                    		<td class="td_title">名单类型：</td>
		                    		<td class="td_content">
										<select id="listtype" style="width: 80px;font-family: Microsoft YaHei;font-size: 13px;">
											<option value="0">黑名单</option>
											<option value="1">白名单</option>
										</select>
									</td>
		            				<td>
		            					<input class="btn_title" style="width:50px;height:25px;float: none;margin-top: 0px;" type="button" onclick="add();" value="确定">
		            					&nbsp;
		            					<input class="btn_title" style="width:50px;height:25px;float: none;margin-top: 0px;" type="button" onclick="jQuery('#div_addLxr').css('display','none');" value="取消">
		            				</td>
		            				
		            			</tr>
		            		</table>
		            	</div>
		            </div>
				</div>
				<div id="ip_manage_content" class="ip_manage_content">
					<ul>
						<li name="apply" class="active">
							<table id="apply"></table>
						</li>
						<li name="whitenl">
							<table id="whitenl"></table>
						</li>
						<li name="blacknl">
							<table id="blacknl"></table>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>

