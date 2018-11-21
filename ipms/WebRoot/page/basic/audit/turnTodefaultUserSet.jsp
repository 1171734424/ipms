<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%@ include file="../../common/css.jsp"%>
<%@ include file="../../common/script.jsp"%>
<% request.setAttribute("basePath", request.getContextPath());
   request.setAttribute("auditpost", request.getAttribute("auditpost"));
   request.setAttribute("auditgrades", request.getAttribute("auditgrades"));
   request.setAttribute("auditgradesnew", request.getAttribute("auditgradesnew"));
   request.setAttribute("rauditgradesnew", request.getAttribute("rauditgradesnew"));
   request.setAttribute("prauditgradesnew", request.getAttribute("prauditgradesnew"));
   request.setAttribute("puauditgradesnew", request.getAttribute("puauditgradesnew"));
   request.setAttribute("style", request.getAttribute("style"));
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>审核人设置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">				
	<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
	<link rel="stylesheet"  href="<%=request.getContextPath()%>/css/ipms/css/leftmenu/leftmenu.css" />
  
	<style>
	.auditer_td_hidden{
	  display:none;
	}
	/*crm里面 提交 取消 按钮样式公共类*/
.crm-confirmbt,.crm-cancelbt{
	height: 34px;
    line-height: 34px;
    text-align: center;
    width: 80px;
    border: none;
    color: #fff;
    cursor: pointer;
    border-radius: 4px;
    font-weight: normal;
    margin: 0 10px;
    display: inline-block;
    font-size: 14px;
}
.crm-confirmbt{
	background-color: #5A5D9D;
}
.crm-cancelbt{
	background-color: #FC8A15;
}
	.parents-content{
	width: 100%;
    text-align: center;
    margin: 3% auto;
}
	.fieldWrapper select {
    width: 243px;
    height: 30px;
    border-radius: 5px;
    font-size: 11pt;
    border-color: #ddd !important;
    border-width: 1px;
	}

	table.top-table{
		    margin-top: 2%;
		    float: left;
		    width: 0%;
		}
		
 .audit_group{
		position: absolute;
    top: 42%;
    right: 4%;
   
		}
	.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
.close_img:hover{
display: block;
background:url("./images/close.png") left no-repeat;
}
.auditadd{
background-color:#FC8A15;
display:inline-block;
padding:4px;
border:none;
color:#fff;
border-radius:4px;
cursor: pointer;

}
	</style>
	<script>
      var base_path = "<%=request.getContextPath()%>";
	</script>
  </head>
  <body> 
  <input id="defaultposts" name="defaultposts" type="hidden" value="${auditgrades}"/>
  <input id="style" name="style" type="hidden" value="${style}"/>
  	<form id="passform">
			<div class="fieldWrapper" align="center">
				<div class="clearfix">
				<table id="checkouttable" class="top-table">
					<tbody>
						<tr>
							<td class="kv-label" name="addtd">
								退房审核：
							    <span class="auditadd">增加</span>
							</td>
							 <c:forEach var="an" items="${auditgradesnew}">
							<td class="kv-content">
							<select id=""  name="checkouttd" class="check order_input">
				                <c:forEach items="${auditpost}" var="th" varStatus="status">
				                    <c:choose>
				                       <c:when test="${an.postid==th.post_id}">
				                            <option value="${th.post_id}"  selected>${th.post_name}</option>
				                       </c:when>
				                       <c:otherwise>
				                            <option value="${th.post_id}">${th.post_name}</option>
				                       </c:otherwise>
				                    </c:choose> 
				                </c:forEach>
				            </select>
							<img class='deletetd' src="./images/close_scale.png"/>
							</td>
							</c:forEach>
					
						</tr>
					
					</tbody>
				</table>
				</div>
				<div class="clearfix">
				<table id="repairtable" class="top-table">
					<tbody>
						<tr>
							<td class="kv-label" name="addrtd">
							维修审核：
							 <span class="auditadd">增加</span>
							</td>
							 <c:forEach var="an" items="${rauditgradesnew}">
							<td class="kv-content" >
							<select id=""  name="repairtd" class="check order_input">
				                <c:forEach items="${auditpost}" var="th" varStatus="status">
				                    <c:choose>
				                       <c:when test="${an.postid==th.post_id}">
				                            <option value="${th.post_id}"  selected>${th.post_name}</option>
				                       </c:when>
				                       <c:otherwise>
				                            <option value="${th.post_id}">${th.post_name}</option>
				                       </c:otherwise>
				                    </c:choose> 
				                </c:forEach>
				            </select>
							<img class='deletetd' src="./images/close_scale.png"/>
							</td>
							</c:forEach>
						</tr>
					
					</tbody>
				</table>
				</div>
				
				<div class="clearfix">
				<table id="roompricetable" class="top-table">
					<tbody>
						<tr>
							<td class="kv-label" name="addprtd">
							房价审核：
							 <span class="auditadd">增加</span>
							</td>
							 <c:forEach var="an" items="${prauditgradesnew}">
							<td class="kv-content" >
							<select id=""  name="roompricetd" class="check order_input">
				                <c:forEach items="${auditpost}" var="th" varStatus="status">
				                    <c:choose>
				                       <c:when test="${an.postid==th.post_id}">
				                            <option value="${th.post_id}"  selected>${th.post_name}</option>
				                       </c:when>
				                       <c:otherwise>
				                            <option value="${th.post_id}">${th.post_name}</option>
				                       </c:otherwise>
				                    </c:choose> 
				                </c:forEach>
				            </select>
							<img class='deletetd' src="./images/close_scale.png"/>
							</td>
							</c:forEach>
						</tr>
					
					</tbody>
				</table>
				</div>
				
				<div class="clearfix auditer_td_hidden">
				<table id="purchasetable" class="top-table">
					<tbody>
						<tr>
							<td class="kv-label" name="addputd">
							采购审核：
							 <span class="auditadd">增加</span>
							</td>
							 <c:forEach var="an" items="${puauditgradesnew}">
							<td class="kv-content" >
							<select id=""  name="purchasetd" class="check order_input">
				                <c:forEach items="${auditpost}" var="th" varStatus="status">
				                    <c:choose>
				                       <c:when test="${an.postid==th.post_id}">
				                            <option value="${th.post_id}"  selected>${th.post_name}</option>
				                       </c:when>
				                       <c:otherwise>
				                            <option value="${th.post_id}">${th.post_name}</option>
				                       </c:otherwise>
				                    </c:choose> 
				                </c:forEach>
				            </select>
							<img class='deletetd' src="./images/close_scale.png"/>
							</td>
							</c:forEach>
						</tr>
					
					</tbody>
				</table>
				</div>
				<div class="audit_group">
					<div class="button_div">
					   <input type="button" class="pdepartsubmit crm-confirmbt" onclick="auditPostnew()" value="确定" />
      				   <input type="button" class="pdepartcancle crm-cancelbt" onclick="defaultReset()" value="重置" />
					</div>
				</div>
			</div>
		</form>
 </body>
	<script src="<%=request.getContextPath()%>/script/common/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery-ui.min.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.dialog.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/jquery.jqGrid.src.js"></script>
	<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
	<script src="<%=request.getContextPath()%>/script/crm/audit/defaultaudit.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
	<script>
	 $(document).ready(function(){
	        var style = $("#style").val();
	        if(style=="0"){ 
	          $("#roompricetable").show();
	          $("#checkouttable").hide();
	          $("#repairtable").show();
	          
	        }else if(style=="1"){
	        
	          $("#roompricetable").hide();
	          $("#checkouttable").show();
	          $("#repairtable").show();
	        
	        }
	        var defaultposts =  $("#defaultposts").val();
			var first_posts = defaultposts.replace(/=/g,":'");
			var second_posts = first_posts.replace(/,/g,"',");
			var third_posts = second_posts.replace(/}/g,"'}");
		    var fouth_posts = third_posts.replace(/}',/g,"},");
			var array_posts = eval(fouth_posts);
			for(var i = 0, l = array_posts.length; i < l; i++){ 
				var one = array_posts[i].GRADEONE;
				var two = array_posts[i].GRADESECOND;
				var three = array_posts[i].GRADETHIRD;
				var four = array_posts[i].GRADEFORTH;
				
				}
				  $("#checkone option[value='"+one+"']").attr("selected",true);
				  $("#checksecond option[value='"+two+"']").attr("selected",true);
				  $("#checkthird option[value='"+three+"']").attr("selected",true);
				  $("#checkforth option[value='"+four+"']").attr("selected",true);
				});
				
	function showMsg(msg, fn) {
	if (!fn) {
	TipInfo.Msg.alert(msg);
	   } else {
	TipInfo.Msg.confirm(msg, fn);
	  }
	}
	
	function auditPost(){
	var checkone = $("#checkone").val();
	var checksecond = $("#checksecond").val();
	var checkthird = $("#checkthird").val();
	var checkforth = $("#checkforth").val();
	if(checkforth){
	  if((checkone==null)||(checksecond==null)||(checkthird==null)){
	        showMsg("请按级别顺序完整填好审核职务");
	         return;
	  }
	 
	}
	
	 if(checkthird){
	    if(((checkone==null)||(checkone==""))||((checksecond==null)||(checksecond==""))){
	         showMsg("请按级别顺序完整填好审核职务");
	         return;
	    }
	}
	
	 if(checksecond){
	    if((checkone==null)){
	         showMsg("请按级别顺序完整填好审核职务");
	         return;
	    }
	}
	

	
	$.ajax({
	
		url : base_path + "/auditPostSure.do",
		type : "post",
		dataType : "json",
		data : {
			'checkone' : checkone,
			'checksecond' : checksecond,
			'checkthird' : checkthird,
			'checkforth' : checkforth
		},
		success : function(json) {
			if (1 == json.result) {
				showMsg("设置成功!");
				window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("设置失败！");
				window.setTimeout("location.reload(true)", 1800);
			}
		},
		error : function() {
			showMsg("操作失败，请重新操作！");
			window.setTimeout("location.reload(true)", 1800);
		}
	  });
	

	
	}
	
	 $("input[name='auditpost']").bind("click",function(){
	
	 JDialog.open("查询", base_path + "/selectAuditer.do?", 450, 350);
	 });
	var MaxInputs  = 4;
	 $("td[name='showtd']").click(function(){
	
	     $(this).parent().children("td:eq(2)").css("display","block");
	     
	     // var x = $(this).parent().children("td").size();
	      //if(x <= MaxInputs){  
	     // $(this).parent().parent().children("td:eq(1)").append("<input  type='text'  name=' class='auditreset'  /><span name='removeinput'>x</span>");
	      //x++;
	      //}
	       // return false; 
	   });
	   

							
							
	   
	    $("td[name='addtd']").click(function(){
	    var x = $(this).parent().children("td").size();
	    if(x <= MaxInputs){  
	      $(this).parent().append("<td  class='kv-content' ><select id='' name='checkouttd' class='check order_input'>"
							   +"<option value=''>请选择审核职务 </option>"
								+"<c:forEach var='th' items='${auditpost}'>"
								+"<option value='${th.post_id}'>${th.post_name}</option>"
								+"</c:forEach></select> <img class='deletetd' src='./images/close_scale.png'/></td>");
								x++;
								}
	     
	   return false; 

	   });
	   
	   $("td[name='addrtd']").click(function(){
	    var x = $(this).parent().children("td").size();
	    if(x <= MaxInputs){  
	      $(this).parent().append("<td  class='kv-content' ><select id='' name='repairtd' class='check order_input'>"
							   +"<option value=''>请选择审核职务 </option>"
								+"<c:forEach var='th' items='${auditpost}'>"
								+"<option value='${th.post_id}'>${th.post_name}</option>"
								+"</c:forEach></select> <img class='deletetd' src='./images/close_scale.png'/></td>");
								x++;
								}
	     
	   return false; 

	   });
	   
	    $("td[name='addprtd']").click(function(){
	    var x = $(this).parent().children("td").size();
	    if(x <= MaxInputs){  
	      $(this).parent().append("<td  class='kv-content' ><select id='' name='roompricetd' class='check order_input'>"
							   +"<option value=''>请选择审核职务 </option>"
								+"<c:forEach var='th' items='${auditpost}'>"
								+"<option value='${th.post_id}'>${th.post_name}</option>"
								+"</c:forEach></select> <img class='deletetd' src='./images/close_scale.png'/></td>");
								x++;
								}
	     
	   return false; 

	   });
	   
	    $("td[name='addputd']").click(function(){
	    var x = $(this).parent().children("td").size();
	    if(x <= MaxInputs){  
	      $(this).parent().append("<td  class='kv-content' ><select id='' name='purchasetd' class='check order_input'>"
							   +"<option value=''>请选择审核职务 </option>"
								+"<c:forEach var='th' items='${auditpost}'>"
								+"<option value='${th.post_id}'>${th.post_name}</option>"
								+"</c:forEach></select> <img class='deletetd' src='./images/close_scale.png'/></td>");
								x++;
								}
	     
	   return false; 

	   });
	   
	   
	   
	  $("body").on("click",".deletetd", function(e){ 
	      $(this).parent().remove();
	   });
	   
	    $(".deletetd").hover(function(){
		    $(this).attr("src","<%=request.getContextPath()%>/images/close.png");
		},function(){
			$(this).attr("src","<%=request.getContextPath()%>/images/close_scale.png");
		});
	   
	   // var users = new Array();
// var user0 = $("#usercheckin").val();
// users.push(user0);
// for(i=1;i<=InputCount;i++){
// var orname = $("#user_"+i).val();
// var ormobile = $("#mobile_"+i).val();
// if((!orname)&&(orname=="")){
// }else{
// users.push(orname);
// }
// }
   
	   function auditPostnew(){
	         var checkposts = new Array();
	         var repairposts = new Array();
	         var roompriceposts = new Array();
	         var purchaseposts = new Array();
	         var n=document.getElementsByName("checkouttd");
	         var m=document.getElementsByName("repairtd");
	         var o=document.getElementsByName("roompricetd");
	         var p=document.getElementsByName("purchasetd");
			   for(var i=0; i<n.length; i++ ){
			       var nurmalid =n[i].id;
				   var nurmalvalue =n[i].value;
				   checkposts.push(nurmalvalue);
					}
					
					  var checkpostsres=[checkposts[0]];  
					    for(var j=1;j<checkposts.length;j++){  
					        var repeat= false;  
					        for(var i=0;i<checkpostsres.length;i++){  
					           if(checkposts[j]==checkpostsres[i]){  
					               repeat=true;  
					               showMsg("退房审核的审核职务设置重复，请重新选择");
					               return; 
					           }  
					        }  
					    }  
					//var narycheckposts = checkposts.sort(); 

						//for(var i=0;i<checkposts.length;i++){ 
						
						//if (narycheckposts[i]==narycheckposts[i+1]){ 
						
						 // showMsg("退房审核的审核职务设置重复，请重新选择"); 
						  //return;
						
						  //} 
						
						//} 
					//unique(checkposts);
					
			    for(var q=0; q<m.length; q++ ){
			       var nurmalid =m[q].id;
				   var nurmalvalue =m[q].value;
				   repairposts.push(nurmalvalue);
					}
					    var repairpostsres=[repairposts[0]];  
					    for(var j=1;j<repairposts.length;j++){  
					        var repeat= false;  
					        for(var i=0;i<repairpostsres.length;i++){  
					           if(repairposts[j]==repairpostsres[i]){  
					               repeat=true;  
					               showMsg("维修审核的审核职务设置重复，请重新选择");
					               return; 
					           }  
					        }  
					    }  
						//var newnaryrepairposts = repairposts;
						//var naryrepairposts = newnaryrepairposts.sort(); 

						//for(var i=0;i<repairposts.length;i++){ 
						
						//if (naryrepairposts[i]==naryrepairposts[i+1]){ 
						
						  //showMsg("维修审核的审核职务设置重复，请重新选择"); 
						  //return;
						
						  //} 
						
						//} 
					
					//unique(repairposts);
					
					 for(var x=0; x<o.length; x++ ){
			          var nurmalid =o[x].id;
				      var nurmalvalue =o[x].value;
				      roompriceposts.push(nurmalvalue);
					}
					
					  var roompricepostsres=[roompriceposts[0]];  
					    for(var j=1;j<roompriceposts.length;j++){  
					        var repeat= false;  
					        for(var i=0;i<roompricepostsres.length;i++){  
					           if(roompriceposts[j]==roompricepostsres[i]){  
					               repeat=true;  
					               showMsg("房价审核的审核职务设置重复，请重新选择");
					               return; 
					           }  
					        }  
					    }  
					//var naryroompriceposts =  roompriceposts.sort(); 

						//for(var i=0;i<roompriceposts.length;i++){ 
						
						//if (naryroompriceposts[i]==naryroompriceposts[i+1]){ 
						
						  //showMsg("房价审核的审核职务设置重复，请重新选择"); 
						 // return;
						
						  //} 
						
						//} 
					
					//unique(roompriceposts);
					
					for(var k=0; k<p.length; k++ ){
			          var nurmalid =p[k].id;
				      var nurmalvalue =p[k].value;
				      purchaseposts.push(nurmalvalue);
					}
					var purchasepostsres=[purchaseposts[0]];  
					    for(var j=1;j<purchaseposts.length;j++){  
					        var repeat= false;  
					        for(var i=0;i<purchasepostsres.length;i++){  
					           if(purchaseposts[j]==purchasepostsres[i]){  
					               repeat=true;  
					               showMsg("采购审核的审核职务设置重复，请重新选择");
					               return; 
					           }  
					        }  
					    }  
					//var narypurchaseposts =  purchaseposts.sort(); 

						//for(var i=0;i<purchaseposts.length;i++){ 
						
						////if (narypurchaseposts[i]==narypurchaseposts[i+1]){ 
						
						//showMsg("采购审核的审核职务设置重复，请重新选择"); 
						  //return;
						
						  //} 
						
						//} 
					
					//unique(purchaseposts);
					
					for(j=checkposts.length;j<4;j++){
					  checkposts.push("*");
					}
					
					for(r=repairposts.length;r<4;r++){
					   repairposts.push("*");
					}
					for(s=roompriceposts.length;s<4;s++){
					  roompriceposts.push("*");
					}
					
					for(g=purchaseposts.length;g<4;g++){
					   purchaseposts.push("*");
					}
					
					var checkpostsarray = JSON.stringify(checkposts);
					var repairpostsarray = JSON.stringify(repairposts);
					var roompricepostsarray = JSON.stringify(roompriceposts);
					var purchasepostsarray = JSON.stringify(purchaseposts);
					
					$.ajax({
	
		url : base_path + "/auditPostSurenew.do",
		type : "post",
		dataType : "json",
		data : {
			'checkpostsarray' : checkpostsarray,
			'repairpostsarray':repairpostsarray,
			'roompricepostsarray':roompricepostsarray,
			'purchasepostsarray':purchasepostsarray
		},
		success : function(json) {
			if (1 == json.result) {
				showMsg("设置成功!");
				window.setTimeout("location.reload(true)", 1800);
			} else {
				showMsg("设置失败！");
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
</html>
