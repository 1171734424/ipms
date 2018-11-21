<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
request.setAttribute("score", request.getAttribute("score"));
request.setAttribute("money", request.getAttribute("money")); 
request.setAttribute("currintegration", request.getAttribute("currintegration")); 
request.setAttribute("sjscore", request.getAttribute("sjscore")); 
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>积分兑换</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>	
	<script>
      var base_path = "<%=request.getContextPath()%>";
      var score = "${score}";
      var money = "${money}";
      var currintegration = "${currintegration}";
	</script>
	<style>
	.whitebg{
    background: #fff;
	}
	.divalign{
	text-align:center;
	}
	.scorefrom{
	margin:20px;
	}
	.scoretable{
	border:none;
	}
	.exchangebotton{
	font-size: 14px;
	font-family: "微软雅黑";
	height: 34px;
    line-height: 31px;
    text-align: center;
    width: 80px;
    margin-left: 4px;
    border: 1px solid #CCCCCC;
    background: #4A5064;
    border: 1px solid #CCCCCC;
    color: #fff;
    cursor: pointer;
	}
	.exfont{
	font-size: 16px;
	padding: 10px 0 10px 0;
	}
	.exfont{
	border:none;
	width:60px;
	}
	.exinfont{
	width: 80px;
    height: 34px;
    text-align: center;
	}
	.noshow_score{
	display:none;
	}
	</style>
  </head>
  
  
  <body class="whitebg" style="width:100%;height:100%;">		
    <div class="">
			<form action="" method="post" class="scorefrom">
			    <table class="scoretable">
			    <col width="30%"/>
			    <col width="20%"/>
			    <col width="35%"/>
			    <col width="15%"/>
			    <tr>
			    <td class="exfont" align="right"><span>当前积分兑换规则为：</span></td>
			    <td class="exfont"><span>${score} 积分 = ${money} 元</span></td>
			    <td class="exfont" align="right"><span>会员目前积分总额为：</span></td>
			    <td class="exfont"><span>${currintegration}</span></td>
			    </tr>
			    <tr class="noshow_score">
			    <td class="exfont" align="right"><span>所能兑换的现金额度为：</span></td>
			    <td class="exfont"><input class="exfont" id="maxExchange" name="" type="text"/></td>
			    <td class="exfont" align="right"><span>兑换所需积分为：</span></td>
			    <td class="exfont"><input class="exfont" id="scoreUser" name="" type="text"/></td>
			    </tr>
			    <tr>
			    <td colspan="4" class="exfont" align="center">
			    <span>请输入本次需兑换的积分额度：</span><input id="sjExchange" class="exinfont" name="" value="${sjscore}" type="text"/><input id="" class="exchangebotton noshow_score" name="" type="button" value="开始兑换" onclick="scoreexchange()"/>
			    </td>
			    </tr>
			    <tr class="noshow_score">
			    <td class="exfont" align="right"><span>已兑换现金额度为：</span></td>
			    <td class="exfont"><input class="exinfont" id="Exchangesj" name="" type="text"/></td>
			    <td class="exfont" align="right"><span>剩余积分为：</span></td>
			    <td class="exfont"><input class="exinfont" id="sjscoreHave" name="" type="text"/></td>
			    </tr>
			    </table><!--
			    <div class="divalign"></div>
			    
			    <div class="divalign">，</span></div>
				<div></div>-->
				<section class="detail_four_score">
					<div class="" role="button">
						<ul class="clearfix">
							<li><span class="button_margin" onclick="submitexchange()">确定</span></li>
							<!--<li><span class="button_margin" onclick="cancelexchange()">取消</span></li>
						--></ul>
					</div>
				</section>
		</div>
   		<script>
      var base_path = "<%=request.getContextPath()%>";
      var score = "${score}";
      var money = "${money}";
      var currintegration = "${currintegration}";
      var exchange =(money/score);
      var maxexchange  =parseInt(currintegration*exchange);
      var scoreuser = (maxexchange/exchange);
      var scorehave = (currintegration - scoreuser);
      document.getElementById("maxExchange").value = maxexchange;
      document.getElementById("scoreUser").value = scoreuser;
      document.getElementById("scoreHave").value = scorehave;
      $("#maxExchange").html(maxexchange);
      $("#scoreUser").html(scoreuser);
      $("#scoreHave").html(scorehave);
      
          
      $(".info_write ul li").on("click",function(){
				$(this).addClass("active");
				$(this).siblings().removeClass("active");
			});
			$(".detail_four .look_order").on("click",function(){
				window.location.href="<%=request.getContextPath()%>/page/ipmspage/order/order_check.jsp";
			});
			
			function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
			
			function scoreexchange(){
		
			var re = /^[0-9]+$/ ;
			var sjExchange = $("#sjExchange").val();
		    if(sjExchange){
			if(re.test(sjExchange)){
			if(sjExchange<=currintegration){
			
			var exchangesj  =parseInt(sjExchange*exchange);
            var scorehavesj = (currintegration - sjExchange);
             document.getElementById("Exchangesj").value = exchangesj;
		      document.getElementById("sjscoreHave").value = scorehavesj;
		      $("#Exchangesj").html(exchangesj);
		      $("#sjscoreHave").html(scorehavesj);
			}else{
		     showMsg("所能兑换的积分额度不能大于会员目前的积分余额总额！")
			}
			   
			}else{
			showMsg("请先输入正确的积分格式（正整数）！")
			}
			
			}else{
			showMsg("请先输入需要兑换的积分金额！")
			}
			
			}
			
			
			function cancelexchange(){
			
			window.parent.JDialog.close();
			
			}
			
			function submitexchange(){
			var re = /^[0-9]+$/ ;
			var scoreneed = $("#sjExchange").val();
			if(scoreneed){
		    if(re.test(scoreneed)){
			      if(scoreneed>currintegration){
					      showMsg("所能兑换的积分额度不能大于会员目前的积分余额总额！")
					
					}else{
					      window.parent.$("#score").val($("#sjExchange").val())
					      window.parent.JDialog.close();
					}
			
			}else{
			   showMsg("请先输入正确的积分格式（正整数）！")
			}
			}else{
			  showMsg("请先输入输入需要兑换的积分额度！")
			}
			
			}
	</script>
		
	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
  </body>
</html>
