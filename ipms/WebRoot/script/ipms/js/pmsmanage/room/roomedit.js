 $(document).ready(function(){
	        var themeid = $("#themeid").val();
	        var rptheme = themeid;
	             if(rptheme=="1"){
	                        $("#rpstatus1").show();
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("");
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("");
	                      
                        }else if(rptheme=="2"){
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("");
	                        $("#rpstatus2").show();
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("");
                        }else if(rptheme=="3"){
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("");
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("");
	                        $("#rpstatus3").show();
                        }
	        var roomtypeid = $("#roomtypeid").val();
	        var newstatus = "rpstatus"+themeid;
            $("#roomtype option[value='"+roomtypeid+"']").attr("selected",true);
            var statusedit = $("#statusedit").val();
             $("#"+newstatus+" option[value='"+statusedit+"']").attr("selected",true);
	       
	    });
	    
	    
	      function showMsg(msg, fn) {
				if (!fn) {
					TipInfo.Msg.alert(msg);
				} else {
					TipInfo.Msg.confirm(msg, fn);
				}
			}
	  		   function thChange(){
	            var rptheme = $("#rptheme").val();
	             if(rptheme=="1"){
	                        $("#rpstatus1").show();
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("");
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("");
	                      
                        }else if(rptheme=="2"){
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("");
	                        $("#rpstatus2").show();
	                        $("#rpstatus3").hide();
	                        $("#rpstatus3").val("");
                        }else if(rptheme=="3"){
	                        $("#rpstatus1").hide();
                        	$("#rpstatus1").val("");
	                        $("#rpstatus2").hide();
	                        $("#rpstatus2").val("");
	                        $("#rpstatus3").show();
                        }
           
			}
			
			function roomedit(){
			var oldstatus = $("#statusedit").val();
			var theme = $("#rptheme").val();
			var rpstatus1 = $("#rpstatus1").val();
			var rpstatus2 = $("#rpstatus2").val();
			var rpstatus3 = $("#rpstatus3").val();
			var rproomtype =  $("#roomtype").val();
			var branchid = $("#branchid").val();
			var rpstatus;
			    if(theme=="1"){
			      rpstatus = rpstatus1;
			   }else if(theme=="2"){
			      rpstatus = rpstatus2;
			   }else if(theme=="3"){
			      rpstatus = rpstatus3;
			   }
		   var newstatus = rpstatus;
		   var floor = $("#rpfloor").val();
		   var roomid = $("#rproomid").val();
		   var area = $("#rparea").val();
		   var remark = $("#rpremark").val();
		   var oldroomtype = $("#roomtypeid").val();
		   if(!theme){
		   showMsg("场景不能为空");
		   
			}else if(!branchid){
			 showMsg("门店不能为空");
			}else if(!rproomtype){
			 showMsg("房型不能为空");
			}else if(!floor){
			 showMsg("楼层不能为空");
			}else if(!roomid){
			 showMsg("房号不能为空");
			}else if(!newstatus){
			 showMsg("状态不能为空");
			}else if(!/^\d+$/.test(floor)){
			  showMsg("楼层必须为数字，请重新输入");
			}else if(!/^\d+$/.test(roomid)){
			  showMsg("房号必须为数字，请重新输入");
			} else if (remark.length >= 100) {
				showMsg("备注内容不能超过100个字符，请重新输入");
			}else{
				if (area) {
					if (!/^\d+$/.test(area)) {
						showMsg("面积必须为数字，请重新输入");
						$("#opostcode").val("");
						return;
					  }
				}
			$.ajax({
				url : base_path + "/editdataRoom.do",
				type : "post",
				dataType : "json",
				data : {
					'theme' : theme,
					'branchid' : branchid,
					'rproomtype' : rproomtype,
					'oldroomtype':oldroomtype,
					'floor' : floor,
					'roomid' : roomid,
					'area' : area,
					'status' : newstatus,
					'oldstatus' : oldstatus,
					'remark' : remark
				},
				success : function(json) {
					if (1 == json.result) {
					    if(json.message){
					      showMsg(json.message);
					       window.setTimeout("window.parent.location.reload(true)", 2500);
						   window.setTimeout("window.parent.JDialog.close();",2500);
					    }else{
					    showMsg("编辑成功！");
					    window.setTimeout("window.parent.location.reload(true)", 2500);
						window.setTimeout("window.parent.JDialog.close();",2500);
					    }
						window.setTimeout("window.parent.location.reload(true)", 2500);
		
					} else {
						showMsg("编辑失败！");
						window.setTimeout("window.parent.location.reload(true)", 2500);
						window.setTimeout("window.parent.JDialog.close();",2500);
					}
				},
				error : function() {
					showMsg("操作失败，请重新操作！");
					window.setTimeout("window.parent.location.reload(true)", 2500);
				    window.setTimeout("window.parent.JDialog.close();",2500);
				}
			});
			}
		  }