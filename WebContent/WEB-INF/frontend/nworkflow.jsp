<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="java.util.Arrays,java.util.ArrayList,backend.javabean.Workform,backend.javabean.Defaultflow,java.util.HashMap,rbac.javabean.RbacRole"%>
<%@page import="java.util.Set"%>
<div class="container">
	<div id="createform">
		<h2 class="sub-header">申请单据</h2>
		<script src="../ckeditor/ckeditor.js"></script>

		<%
			Workform workform = (Workform) request.getAttribute("workform");
			ArrayList<Defaultflow> workflows = (ArrayList<Defaultflow>) request.getAttribute("workflows");			
			HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>) application.getAttribute("roles");
		%>

		<form id="cnewworkflow" action="nworkflow" method="post" class="form-horizontal" role="form">
			<div class="form-group">
				<div class="col-md-6">
					<input name="workformName" value="<%=workform.getName()%>"
						type="text" class="form-control" placeholder="表单名称">
					<input name="formid" value="<%=request.getAttribute("formid") %>" type="hidden">
				</div>
				<div class="col-md-6">
					<h5>
						<%
							String checked = "";

											if (request.getParameter("checked") != null) {
												checked = "添加成功";
											} else {
												if (request.getAttribute("checked") != null)
													checked = (String) request.getAttribute("checked");
											}
											out.print(checked);
						%>
					</h5>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-6">
					<select name="mWorkflow" class="form-control">
						<%
							for (Defaultflow flow : workflows) {

												String participate = flow.getParticipate();
												String participateID[] = participate.split(",");
												String alias[] = new String[participateID.length];

												for (int i = 0; i < participateID.length; i++) {
													alias[i] = roles.get(Integer.valueOf(participateID[i]))
															.getAlias();
												}
						%>
						<option value="<%=flow.getParticipate()%>"><%=flow.getName()%>----<%=Arrays.toString(alias)%></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="col-md-6">
					<button id="custom" type="button" class="btn btn-info">自定义</button>
				</div>	
			</div>
			
			<div class="form-group">
				<div class="col-md-6">
					<input name="kaptcha" value="" type="text" class="form-control" placeholder="验证码">
				</div>
				<div class="col-md-6">
					<img id="nkaptcha" src="<%=basePath%>kaptcha.jpg" />
				</div>	
			</div>

			<div class="form-group">
				<div class="col-md-11">
					<textarea class="ckeditor" name="editor1"><%=workform.getContent()%></textarea>
				</div>
				<div class="col-md-1">
					<button type="submit" class="btn btn-warning">提交</button>
				</div>
			</div>
		</form>
	</div>


	<div id="createflow">
		<h2 class="sub-header">新建流程</h2>

		<div class="row">
			<div class="col-md-4">

				<div class="divcss" id="roleuser">
					<ul class="list-unstyled">
						<%
							HashMap<Integer, String> users;
							Set<Integer> roleskey = roles.keySet();
							for (Integer key : roleskey) {
						%>
						<li data-roleid="<%=key%>"><%=roles.get(key).getAlias()%></li>
						<li>
							<ul>
								<%
									users = (HashMap<Integer, String>) roles.get(key).getUser();
										Set<Integer> userskey = users.keySet();
										for (Integer userkey : userskey) {
								%>
								<li data-userid="<%=userkey%>"><%=users.get(userkey)%></li>
								<%
									}
								%>

							</ul>

						</li>
						<%
							}
						%>

					</ul>
				</div>
			</div>
			<div class="col-md-2">
				<span class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-hand-right"></span>
			</div>

			<div class="col-md-4">
				<div class="divcss" id="addworkflow">
					<ol>

					</ol>

				</div>
			</div>
			<div class="col-md-2">
				<button id="createwl" type="button" class="btn btn-danger">提交</button>&nbsp;
				<button id="goback" type="button" class="btn btn-danger">返回</button>
			</div>

		</div>
	</div>


	<hr>

	<footer>
		<p>&copy; Company 2014</p>
	</footer>
</div>
<!-- /container -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="../bootstrap/js/jquery-1.11.1.js"></script>


<script>
	$("#cnewworkflow").submit(function( event ) {
		var cevent="true";
		var captcha=$("input[name='kaptcha']").val();
		$.ajaxSetup({ 
		    async : false 
		});  
		 $.post( "<%=basePath%>checkcaptcha", { kaptcha: captcha }, function( data ) {
	            if(data!="ok") {
	            	cevent="false";
	            }
	     });

		 if(cevent != "true") {
         	alert("验证码错误");
         	event.preventDefault();
		 }
	});
	
	$("#nkaptcha").on("click", function() {
	 	$(this).attr('src', '<%=basePath%>kaptcha.jpg?' + Math.floor(Math.random()*100) );
	});

	$("#createflow").hide();
	$("#custom").on("click", function() {
		$("#createform").hide();
		$("#createflow").show();
	});
	$("#goback").on("click", function() {
		$("#createform").show();
		$("#createflow").hide();
	});

	$("#roleuser > ul > li:odd").hide();
	$("#createwl").attr("disabled", "disabled");

	$("#roleuser > ul > li:even").on("click", function() {
		$("#roleuser > ul > li:odd").hide();
		$(this).next().show();

	});

	$("#roleuser li li").on("click", function() {

		$("#createwl").removeAttr("disabled");
		$("#roleuser li li").removeClass("libgcl");
		$(this).addClass("libgcl");
		$(this).clone().removeClass("libgcl").appendTo("#addworkflow ol");
	});

	$("#addworkflow").on("click", "li", function() {
		$(this).remove();
		if ($("#addworkflow li").length == 0) {
			$("#createwl").attr("disabled", "disabled");
		}
	});

	var wName = "";
	$("#flowName").keyup(function() {
		wName = $(this).val();
	});

	$("#createwl").on("click", function() {
		var mWorkflow="0,";
		$("#addworkflow li").each(function(index) {
			mWorkflow += $(this).data("userid") + ",";
		});
		mWorkflow = mWorkflow.substring(0, mWorkflow.length - 1);

		
		
		mFlowname="";
		$("#addworkflow li").each(function() {
			mFlowname=mFlowname+$(this).text()+ ",";
		});
		mFlowname= mFlowname.substring(0,mFlowname.length - 1);
		mFlowname="自定义流----["+mFlowname+"]";
		
		$("#deleteru").attr("disabled", "disabled");
		$("option").removeAttr("selected");
		var newflow="<option value=\""+mWorkflow+"\" selected=\"selected\">"+mFlowname+"</option>";
		$("select").append(newflow);
		alert("添加成功");
		
	});
</script>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
