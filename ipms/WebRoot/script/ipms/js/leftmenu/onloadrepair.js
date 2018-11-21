window.onload = function(){
		  	var applyId = $(document.getElementsByName("detailbtn"));
		  	var newId="";
		  	for ( var int = 0; int < applyId.length; int++) {
				newId += $(applyId[int]).parent().next().text()+",";
			}
		  	//var a = $(applyId)[0].text();
	  		 $.ajax({
	  	         url: path + "/checkMainLogNum.do",
	  			 type: "post",
	  			 data : {"applyId" : newId},
	  			 success: function(json) {
	  				for ( var int = 0; int < applyId.length; int++) {
	  					var id = $(applyId[int]).parent().next().text();
	  					for(var key in json){
	  						if(id== key && json[key]==1){
	  							$(applyId[int]).attr("disabled",true);
	  							$(applyId[int]).removeClass("active_log");
	  							$(applyId[int]).addClass("not_active_log");
	  						}else if(id== key && json[key]==2){
	  							$(applyId[int]).removeAttr("disabled");
	  							$(applyId[int]).removeClass("not_active_log");
	  							$(applyId[int]).addClass("active_log");
	  						}
	  					}
	  				}
	  			 },
	  			 error: function() {}
	  		});

		  	};