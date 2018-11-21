<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglib.jsp"%>
<%@ include file="../../../common/css.jsp"%>
<%@ include file="../../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("roomId", request.getAttribute("roomId")); 
   request.setAttribute("roomtypename", request.getAttribute("roomtypename")); 
   request.setAttribute("roomacount", request.getAttribute("roomacount")); 
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>房号选择</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/order/order_details.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/reset.css" />
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/fonticon.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/tipInfo.css"/>
	<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/ipms/css/roomlist/roomstate.css" />	
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<script src="<%=request.getContextPath()%>/script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>	
	<script>
      var base_path = "<%=request.getContextPath()%>";
      var roomacount = "${roomacount}"
	</script>
	<style>
	.imooc_order{
	top:-10px;
	left:96%;
	}
	.whitebg{
    background: #fff;
	}
	.divalign{
	text-align:center;
	}
  .footer_content	.all{
	background-color:#d6f6fd;
	}
	.kjf{
	background-color:#d6f6fd;
	}
	.zf{
	background-color:#f3cfb5;
	}
	.room_content ul li{
	float: left;
    width: 10%;
    height: 68%;
    line-height: 40px;
    margin: 9px 2px;
    border-radius: 2px;
    text-align: center;
    font-weight: bold;
    color: #333;
	}
	.roomsubmitbotton{
	float: right;
    margin: 11px;
	font-size: 14px;
	font-family: "微软雅黑";
	height: 40px;
    line-height: 31px;
    text-align: center;
    width: 80px;
    border: 1px solid #CCCCCC;
    background: #4A5064;
    border: 1px solid #CCCCCC;
    color: #fff;
    cursor: pointer;
	}
	</style>
  </head>
  <body>
		<div class="main_wrapper">
		<i class="imooc imooc_order" style="color:#3B3E40;" onclick="window.parent.JDialog.close();">&#xe902;</i>
			<div class="main_content">
			<div >房型：${roomtypename}（已选）</div>
			<div id="myrt">
				<ul id="mylabel">
				 <c:forEach var="rs" items="${roomId}" varStatus="aaa">
				    <li>
				       <input type="hidden" id="${rs.status}" name = "rtype" value = "${rs.status}">
						<span class="roomid">${rs.roomid}</span> 
						</li>  
				 </c:forEach>
				</ul>
				</div>
			</div>
			<div class="footer_content">
				<form action="" method="">
					<ul id="">
					<li id = "" class="all" style="width:62px;">
						<span onclick="alltype();">所有</span>
						</li>
				    <li id = "" style="width:62px;background-color:#d6f6fd">
						<span onclick="kjf();">空净房</span>
						</li>
						 <li id = "" style="width:62px;background-color:#f3cfb5">
						<span onclick="zf();">脏房</span>
						</li> 
						<input class="button_margin roomsubmitbotton" onclick="selectroom()" value="确定"/>						
				</ul>	
				</form>
			</div>
		</div>
	<script src="script/ipms/js/jquery-1.8.2.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
		$(".footer_content ul .footer_li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			
		});
		
		function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
		
		Array.prototype.indexOf = function(val) {            
			for (var i = 0; i < this.length; i++) {
				if (this[i] == val) return i;
			}
			return -1;
		};
		
		Array.prototype.remove = function(val) {
			var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
		};
		
		 var rooms = new Array();
		 $(function(){
		 
                $("#myrt ul li").toggle(function(){
                    $(this).css("backgroundColor","#f1c2d5");
                    var roomid = $(this).find("span").html();
                    rooms.push(roomid);
                },function(){
                    $(this).css("backgroundColor","#d6f6fd");
                    var roomid = $(this).find("span").html();
                    rooms.remove(roomid);
                });
                
                var x=document.getElementsByName("rtype");
                for(var i=0; i<x.length; i++ ){
                var test = x[i].value;
                var s = "#"+test;
                if(test.indexOf("0")){
                $(s).parent().css("backgroundColor","#f3cfb5")
                }
                }
                
            })
            
            function kjf(){
            
            var x=document.getElementsByName("rtype");
                for(var i=0; i<x.length; i++ ){
                var test = x[i].value;
                var s = "#"+test;
                if(test.indexOf("0")){
                $(s).parent().css("display","none")
                }else{
                   $(s).parent().css("display","block")
                  }
                }
            
            }
            
             function zf(){
            
            var x=document.getElementsByName("rtype");
                for(var i=0; i<x.length; i++ ){
                var test = x[i].value;
                var s = "#"+test;
                if(test.indexOf("Z")){
                $(s).parent().css("display","none")
                  }else{
                   $(s).parent().css("display","block")
                  }
                }
            
            }
            
            function alltype(){
            var x=document.getElementsByName("rtype");
                for(var i=0; i<x.length; i++ ){
                var test = x[i].value;
                var s = "#"+test;
               if(test.indexOf("0")){
                $(s).parent().css("display","block")
                }else if(test.indexOf("Z")){
                $(s).parent().css("display","block")
                }
               
               
                }
            
            }
            
            function selectroom(){
              var roomlen =rooms.length;
              var ac = parseInt(roomacount)
              if(ac == (rooms.length)){
               window.parent.$("#roomid").val(rooms);
               window.parent.JDialog.close();
              
              }else{
                window.parent.$("#roomid").val("");
                showMsg("已选的房间数与预定页面的“数量”不一致，请重新选择！");
                
               
              }
             
              //var x=document.getElementsByName("rtype");
                //for(var i=0; i<x.length; i++ ){
                //var test = x[i].value;
                //var s = "#"+test;
                //if($(s).parent().attr("style").indexOf("background-color:#f1c2d5")){
                  //alert("123")
                  //}else{
                  
                  //}
                //}
            
            }
                
		 //$("ul li").click(function() {
        //$(this).css("background","#f00");//给点击的a标签加红色
        //被点击之外的a标签颜色为黑色
 
			//var a = document.getElementsByName("rtype"); 
			//for(var i=0;i<a.length;i++){
			//var test = a[i].value;
			
			//if(test.indexOf("Z")){
			//}else{
			//}
			//}
			//})
		 
       
		
		
         //$("ul li").each(function(){
          //if($("#typecolor").val() == 'Z'){
		  //document.getElementById('licolor').style.background='#f3cfb5';
		//}
         //});
		
		//if($("#typecolor").val() == 'Z'){
		//document.getElementById('licolor').style.background='#f3cfb5';
		//}
		
	
		
	
	</script>
	</body>
</html>
