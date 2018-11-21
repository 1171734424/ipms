
/*showMsg*/
function showMsg(msg, fn) {
	if (!fn) {
		TipInfo.Msg.alert(msg);
	} else {
		TipInfo.Msg.confirm(msg, fn);
	}
}

	$("#info input[type='checkbox']").click(function(e){
		
		//console.log($(this).parent().parent().children("td").eq(1).html());
		
		let trid =  $(this).parent().parent().children("td").eq(1).html();
				/* if(document.getElementById(trid).checked==true){
			 $("#"+trid).prop("checked",false);
		 }else{
			 $("#"+trid).prop("checked",true);
		 }*/
		let gdsid = window.parent.$("#gdscontent").val();
		if ( document.getElementById(trid).checked==true) {
			gdsid = gdsid+ trid+ ",";
		window.parent.$("#gdscontent").val(gdsid);
		
		} else {
			let deletegds =trid+ ",";
		gdsid = gdsid.replace(deletegds, "");
		window.parent.$("#gdscontent").val(gdsid);
	  }
		 e.stopPropagation();
	    
	});

/*单品售卖*/
$("input[name='gdsale']").bind("click",function() {
	var saletype = "singlesale";
	var gdsid = $(this).parent().parent().children("td").eq(1)
			.html();
	var gdsname = $(this).parent().parent().children("td").eq(2).html();
	var gdsprice = $(this).parent().parent().children("td").eq(4).html();
	window.parent.JDialog.open("商品售卖", base_path + "/gdsApartPage.do?gdsid="
			+ gdsid + "&gdsname=" + gdsname + "&gdsprice="
			+ gdsprice + "&saletype=" + saletype, 750, 420);
})
				




/*checkbox点击事件*/				
var content = new Array();

function gdscheck(e) {
	/*
	 * 第一版版批量提交（数组方法）CM 
	 * var gdsid = $(this).parent().parent().children("td").eq(1).html();
	 * if($(this).is(':checked')){ content.push(gdsid);
	 *  } else{ for (var i = 0;i < content.length; i++) { var s =
	 * content[i]; if (s == gdsid) { content.splice(i,1); i--; } } }
	 */
	
	/* 第二版版批量提交（数组方法）CM */
	var trid =  $(e.children[1]).html();
	 if(document.getElementById(trid).checked==true){
		 $("#"+trid).prop("checked",false);
	 }else{
		 $("#"+trid).prop("checked",true);
	 }
	var gdsid = window.parent.$("#gdscontent").val();
	if ( document.getElementById(trid).checked==true) {
		gdsid = gdsid+ trid+ ",";
	window.parent.$("#gdscontent").val(gdsid);
	
	} else {
		var deletegds =trid+ ","
	gdsid = gdsid.replace(deletegds, "");
	window.parent.$("#gdscontent").val(gdsid);
  }
};


/* 第一版版批量提交 */
function gdsmanyearch() {
	if (!content.length) {
		showMsg("请至少选择一个商品！")
	} else {

		var saletype = "multiplesale";
		var newarr = new Array();
		var arrstr = '[';
		$.each(content, function(index) {
			arrstr = arrstr + JSON.stringify(content[index]) + ',';
		})
		var newstr = arrstr.substring(0, arrstr.length - 1);
		newstr = newstr + ']';
		JDialog.open("商品售卖", base_path + "/gdsmulApartPage.do?newstr=" + newstr
				+ "&saletype=" + saletype, 750, 420);
	}
}

/* 第二版版批量提交 */
function gdsmanyearch_se() {
	var saletype = "multiplesale";
	var gdscontentorgin = $("#gdscontent").val();
	var gdscontent = gdscontentorgin.substr(0, gdscontentorgin.length - 1);
	var content = new Array();
	content = gdscontent.split(",");
    var ret = [];
    for (var i = 0, j = content.length; i < j; i++) {
      if (ret.indexOf(content[i]) === -1) {
            ret.push(content[i]);
		         }
		     }
	if (gdscontentorgin=="") {
		showMsg("请至少选择一个商品！")
	} else {
		var newarr = new Array();
		var arrstr = '[';
		$.each(ret, function(index) {
			arrstr = arrstr + JSON.stringify(ret[index]) + ',';
		})
		var newstr = arrstr.substring(0, arrstr.length - 1);
		newstr = newstr + ']';
		JDialog.open("商品售卖", base_path + "/gdsmulApartPage.do?newstr=" + newstr
				+ "&saletype=" + saletype, 750, 420);
	}
	

}