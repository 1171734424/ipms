<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>职位与员工设置</title>
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/bwizard.min.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/initialcss/initialStaff.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/css/common/tipInfo.css" rel="stylesheet" type="text/css" media="all" />
		<link rel="stylesheet" id="style" type="text/css" href="<%=request.getContextPath()%>/css/common/datepicker.css" media="all" />	
	</head>
	<%@ include file="../common/css.jsp"%>
	<body>
		<div class="well" style="height:540px;">
			<!--<div id="step2" role="tabpanel" class="bwizard-activated" aria-hidden="false">
				<div class="tab-pane" id="tab1">
				查询数据库，展示新增职位的地方
					<div class="control-group">
						<label class="control-label" for="name">
							职位：
						</label>
						<div class="controls">
							<input type="text" id="namefield" name="namefield"
								class="required">
						</div>
					</div>
				
				员工新增的页面，中的职位直接查询数据库预制的店长的职位
					<form action="saveStaffData.do" method="post" id="myForm">
						<div class="control-group">
							<label class="control-label leftfloat">员工姓名</label>
							<div class="controls leftfloat">
								<input type="text" id="staffName" name="staffName" class="required paddingInput" placeholder="输入员工姓名">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label leftfloat">登录名</label>
							<div class="controls leftfloat">
								<input type="text" id="loginName" name="loginName" class="required paddingInput" placeholder="输入登录名">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">职位</label>
							<div class="controls">
								//<input type="text" id="post" name="post" class="required">
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">身份证</label>
							<div class="controls">
								<input type="text" id="idCard" name="idCard" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">手机号</label>
							<div class="controls">
								<input type="text" id="mobile" name="mobile" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">性别</label>
							<div class="controls">
								<input type="text" id="mobile" name="mobile" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">生日</label>
							<div class="controls">
								<input type="text" id="mobile" name="mobile" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">入职时间</label>
							<div class="controls">
								<input type="text" id="mobile" name="mobile" class="required">
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<input type="button" id="ddfield" name="ddfield"
									class="required" value="提交" onclick="kk();">
							</div>
						</div>
					</form>
				</div>
				下面是新增员工的数据展示table
				<div>
					<table>
						<tbody>
							<tr><td>姓名</td><td>职位</td></tr>
							<c:forEach items="${staffList}" var="sl">
								<tr><td>${sl.staffName}</td><td>${sl.post}</td></tr>
						 	</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		-->
		<form id="myForm" class="form-horizontal" role="form"   >
			<c:forEach items="${staffList}" var="sl">
                    <fieldset style="margin: 10px;">
                        <legend>员工修改</legend>
                        <input class="form-control" id="staffId" name="staffId" type="hidden" value="${sl.staff_id}"/>
                       <div class="form-group" style=" margin: 40px;">
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="staffName">员工姓名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="staffName" name="staffName" type="text" placeholder="输入员工姓名" value="${sl.staff_name}"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="loginName">登录名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="loginName" name="loginName" type="text" placeholder="输入登录名" value="${sl.login_name}"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >身份证</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="idcard" name="idcard" type="text" placeholder="320981198808080808" value="${sl.idcard}" onblur="checkidcard(this)"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                       	  <label class="col-sm-2 control-label" >手机号</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="mobile" name="mobile" type="text" placeholder="18209090909" value="${sl.mobile}"/>
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >生日</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="birthdays" name="birthdays"  value="${sl.birthday}" style="width:287px;height:41px;font-size: 16px;color: #555;"/>
                          </div> 
                       </div>
                       <div class="form-group formInput">
	                       <label class="col-sm-2 control-label" >入职时间</label>
                           <div class="col-sm-4">
                              <input class="form-control" id="entryTimes" name="entryTimes" value="${sl.entryTime}" style="width:287px;height:41px;font-size: 16px;color: #555;"/>
                           </div>
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">性别</label>
                           <div class="col-sm-10">
                              <select id="gendor" name="gendor" class="form-control">
                                 <option value="1" >男</option>
                                 <option value="0" >女</option>
                              </select>
                           </div>
                        </div>
                        <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">职位</label>
                           <div class="col-sm-10">
                              <select id="post" name="post" class="form-control">
                                 <c:forEach items="${postList}" var="pl">
                                  	<option value="${pl.post_Id}">${pl.post_Name}</option>
						 		</c:forEach>
                              </select>
                           </div>
                        </div>
                       <button id="buttonId" type="button" class="btn btn-default" onclick = "checkForm();" style="display:none"></button>
                    </div>
                    </fieldset>     
                
                </c:forEach>
                <c:if test="${staffList== null}" >
                    <fieldset style="margin: 10px;">
                        <legend>员工新增</legend>
                        <input class="form-control" id="staffId" name="staffId" type="hidden" />
                        <div class="form-group" style=" margin: 40px;">
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="staffName">姓名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="staffName" name="staffName" type="text" placeholder="输入员工姓名" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" for="loginName">登录名</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="loginName" name="loginName" type="text" placeholder="输入登录名" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >身份证</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="idcard" name="idcard" type="text" placeholder="320981198808080808" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                       	  <label class="col-sm-2 control-label" >手机号</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="mobile" name="mobile" type="text" placeholder="18209090909" />
                          </div>
                       </div>
                       <div class="form-group formInput">
                          <label class="col-sm-2 control-label" >生日</label>
                          <div class="col-sm-4">
                             <input class="form-control" id="birthdays" name="birthdays" style="width:287px;height:41px;font-size: 16px;color: #555;" placeholder=""/>
                          </div> 
                       </div>
                       <div class="form-group formInput">
	                       <label class="col-sm-2 control-label" >入职时间</label>
                           <div class="col-sm-4">
                              <input class="form-control" id="entryTimes" name="entryTimes" style="width:287px;height:41px;font-size: 16px;color: #555;"/>
                           </div>
                       </div>
                       <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">性别</label>
                           <div class="col-sm-10">
                              <select id="gendor" name="gendor" class="form-control">
                                 <option value="1" >男</option>
                                 <option value="0" >女</option>
                              </select>
                           </div>
                        </div>
                        <div class="form-group formInput">
                           <label for="disabledSelect"  class="col-sm-2 control-label">职位</label>
                           <div class="col-sm-10">
                              <select id="post" name="post" class="form-control">
                                 <c:forEach items="${postList}" var="pl">
                                  	<option value="${pl.post_Id}">${pl.post_Name}</option>
						 		</c:forEach>
                              </select>
                           </div>
                        </div>
                        </div><!--
                        <button id="buttonId" type="button" class="btn btn-default" onclick = "inputCheckRepeat();">提交</button>
                    --></fieldset>     
                
               </c:if>
                </form>
             <!-- <fieldset>
                        <legend>数据展示</legend>
                        <table class="table">
                         	<thead><tr><th>姓名</th><th>登录名</th><th>职位</th></tr></thead>  
                            <tbody> 
                            	<c:forEach items="${staffList}" var="sl">
                                  	<tr><td>${sl.staff_Name}</td><td>${sl.login_Name}</td><td>${sl.post_name}</td></tr>  
						 		</c:forEach> 
                            </tbody>  
                        </table> 
                </fieldset>
		--></div>
		<%@ include file="../common/script.jsp"%>
		<%-- <script src="<%=request.getContextPath()%>/script/initialData/jquery.min.js"></script> --%>
		<script src="<%=request.getContextPath()%>/script/initialData/bootstrap.min.js"></script>
		<!--<script src="<%=request.getContextPath()%>/script/initial/bwizard.min.js"></script>-->
		<script src="<%=request.getContextPath()%>/script/common/tipInfo.js"></script>
		<script src="<%=request.getContextPath()%>/script/ipms/js/moment.js" charset="utf-8"></script>
		<script src="<%=request.getContextPath()%>/script/common/keyPrevent.js"></script>
		<script src="<%=request.getContextPath()%>/script/common/datepickerCN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/script/ipms/js/workbillroom/util.js"></script>
		<script type="text/javascript">
	    	var base_path = "<%=request.getContextPath()%>";
	    	$(document).ready(function() {
	    		//当数据展示时select选中
	    		$("#gendor").val(${gendor});
	    		$("#post").val(${post});
	    		<c:if test="${staffList== null}" >
	    			$("#staffName").focus();
	    		</c:if>	
	    		
	    		$("#birthdays").datepicker({
					dateFormat: "yy-mm-dd",
					changeMonth: true, 
					changeYear: true
				});
	    		$("#entryTimes").datepicker({
					dateFormat: "yy-mm-dd",
					changeMonth: true, 
					changeYear: true
				});
	    		$("#birthdays").val(moment().format('YYYY-MM-DD'));
	    		$("#entryTimes").val(moment().format('YYYY-MM-DD'));
	    		$("#ui-datepicker-div").css('font-size','0.8em');
	    		
        	});
        	
        	function checkidcard(e){
        		var idcard = $(e).val();
        		if(idcard){
        			if(!isIdcard(idcard)){
        			}
        		}
        	}
	</script>
	</body>
</html>

