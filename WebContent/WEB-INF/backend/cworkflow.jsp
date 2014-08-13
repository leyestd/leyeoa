<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="rbac.javabean.AccountPermissionRole,java.util.ArrayList,backend.javabean.Workform"%>
<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set"%>
<div class="container">

	<h2 class="sub-header">新建流程</h2>


	<div class="row">
		<div class="col-md-3">
			<h4>角色</h4>
			<div class="cdivcss" id="rolelist">
				<ol>
					<%
						HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)application.getAttribute("roles");
									HashMap<Integer,String> users;
									Set<Integer> roleskey=roles.keySet();
								for(Integer key : roleskey) {
					%>
					<li data-roleid="<%=key%>"><%=roles.get(key).getAlias()%></li>
					<%
						}
					%>

				</ol>
			</div>
		</div>

		<div class="col-md-3">
			<h4>工作流</h4>
			<div class="cdivcss" id="addworkflow">
				<form role="form">
					<div class="form-group">
						<input type="text" class="form-control" id="flowName"
							placeholder="工作流名称">
					</div>
				</form>
				<ol>

				</ol>

			</div>
		</div>

		<div class="col-md-1">
			<h4>&nbsp;</h4>
			<span class="glyphicon glyphicon-minus"></span> <span
				class="glyphicon glyphicon-minus"></span> <span
				class="glyphicon glyphicon-hand-right"></span>
		</div>

		<div class="col-md-3">
			<h4>表单</h4>
			<div class="cdivcss" id="formlist">
				<ol>
					<%
						ArrayList<Workform> formList=(ArrayList<Workform>)request.getAttribute("formList");
								for(Workform workform : formList) {
					%>
					<li data-formid="<%=workform.getId()%>"><%=workform.getName()%></li>
					<%
						}
					%>

				</ol>
			</div>
		</div>

		<div class="col-md-2">
			<h4>&nbsp;</h4>
			<button id="createwl" type="button" class="btn btn-danger">提交</button>
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
	$("#createwl").attr("disabled", "disabled");

	$("#rolelist li").on("click", function() {

		$("#createwl").removeAttr("disabled");
		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		$(this).clone().removeClass("libgcl").appendTo("#addworkflow ol");
	});

	var formid = "";
	$("#formlist li").on("click", function() {

		$("#createwl").removeAttr("disabled");
		$("#formlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		formid = $(this).data("formid");
	});

	$("#addworkflow").on("click", "li", function() {
		$(this).remove();
		if ($("#addworkflow li").length == 0) {
			$("#createwl").attr("disabled", "disabled");
		}
	});

	var mWorkflow = "";

	var wName = "";
	$("#flowName").keyup(function() {
		wName = $(this).val();
	});

	$("#createwl").on("click", function() {
		if (wName == "" || formid == "") {
			alert("流程名或表单ID为空");
		} else {
			mWorkflow = "";
			$("#addworkflow li").each(function(index) {
				mWorkflow += $(this).data("roleid") + ",";
			});
			mWorkflow = mWorkflow.substring(0, mWorkflow.length - 1);

			$.post("cworkflow", {
				mWorkflow : mWorkflow,
				wName : wName,
				formid : formid
			}, function(data) {
				if (data == "ok") {
					$("#createwl").attr("disabled", "disabled");
					mWorkflow = "";
					wName = "";
					$("#flowName").val("");
					$("#addworkflow li").remove();
					alert("流程添加成功");
				} else {
					alert(data);
				}
			});

			$("#deleteru").attr("disabled", "disabled");
		}
	});
</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
