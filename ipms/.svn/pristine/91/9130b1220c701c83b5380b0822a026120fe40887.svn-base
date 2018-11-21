<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>民宿参数设置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/animate.css"/>	
	<link href="<%=request.getContextPath()%>/css/ipms/css/reset.css" rel="stylesheet" type="text/css" media="all" />
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ipms/css/roomlistfont.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/iconfonts/iconfont.css"/>
	
  </head>
  <style>
  	.div_table {
	    height: 764px;
	    padding: 10px;
	    overflow: auto;
	}
	.setparam {
	    height: 50px;
	    line-height: 40px;
	    width: 100%;
	    height: 34px;
	    line-height: 34px;
	    text-align: left;
	    width: 85px;
	    border: none;
	    color: #004E90;
	    transition: all ease-in-out 0.3s;
	}
	.div_table table {
	    width: 100%;
	    background-color: #fff;
	    border-collapse: collapse;
	    border-spacing: 0;
	    border: 1px solid #e2e2e2;
    }
    .box-transition table tr {
	    transition: all .3s;
	    -webkit-transition: all .3s;
	}
	.box-transition table thead tr {
	    background-color: #f2f2f2;
	}
	.box-transition table tr td, .box-transition table th {
	    border: 1px solid #e2e2e2;
	}
	.box-transition table td, .box-transition table th {
	    position: relative;
	    padding: 9px 15px;
	    min-height: 20px;
	    line-height: 20px;
	    font-size: 14px;
	}
	.verifierbox{
		width: 426px;
	    height: 276px;
	    display: none;
	    position: absolute;
	    z-index: 999;
	    top: 40%;
	    left: 53%;
	    margin-left: -445px;
	    margin-top: -180px;
	    transition: all 0.65s ease-in-out;
	    transition-duration: 0.65s;
	    animation-duration: 0.65s;
	    -webkit-box-shadow: 0 5px 15px rgba(0, 0, 0, .5);
	    box-shadow: 0 5px 15px rgba(0, 0, 0, .5);
	    border-radius: 6px;
	    background-color: #fff;
	}
	.verifierbox h4{
	    padding: 0;
	    margin: 0;
	    width: 100%;
	    height: 40px;
	    line-height: 40px;
	    background: rgb(74, 80, 100);
	    color: #fff;
	    border-top-left-radius: 6px;
	    border-top-right-radius: 6px;
	}
	h4 span.title{
		margin-left:10px;
	}
	.inputbox {
	    width: 93%;
	    margin: 10% auto;
	}
	.div_update ul li{
		margin-left:50px;
	}
	.color_button{
	    cursor: pointer;
	    width: 80px;
	    background-color: #f79001;
	    padding: 4px;
	    height: 34px;
	    line-height: 27px;
	    border: none;
	    position: absolute;
	    border: none;
	    color: #fff;
	    right: 41%;
	    top: 60%;
	}
	.div_update .imooc_order{
		position:absolute;
	    top: 1px;
	    font-size: 30px;
	    left: 92%;
	    transition: all 0.15s ease-in-out;
	    transition:all 0.3s ease-in-out;
	}
	.imooc_order:hover {
	    transform: rotate(90deg);
	    -ms-transform: rotate(90deg);
	    -webkit-transform: rotate(90deg);
	}
	.updatebtn{
		text-align:center;
		cursor:pointer;
		color:#FC8A15;
	}
  </style>
  <body> 
     <div class="fadeInDown box-transition">
	  	<div class="div_table">
	  		<div class="setparam">
	  			<span>参数设置</span>
	  		</div>
			<table id="showInfoTable">
				<colgroup>
			      <col width="150">
			      <col width="150">
			      <col width="200">
			      <col width="200">
	    		</colgroup>
			</table>
		</div>
		<div class="div_update verifierbox fadeInDown" style="display:none;">
			<h4><span class="title">参数修改</span><i class="imooc imooc_order" style="color:#fff;" onclick="Close()">&#xe900;</i></h4>
			<div class="inputbox">
	     		<ul class="clearfix">
	     			<li><label>请输入参数值</label><input type="text" class="text_search" id="inputData"></li>
	     			<li><label><input type="button" name=""  value="确认" class="button_margin color_button" onclick="updateParameter()"/></label></li>
	     		</ul>
     		</div>
     	</div>
	</div>	
 </body>

	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/parameter/houseparameter.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	  <script>
	 		var base_path = '<%=request.getContextPath()%>';
	 		var nn = '${map}';
	 		var json = JSON.parse(nn);
	  </script>
</html>
