<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole,java.util.ArrayList,backend.javabean.Defaultflow"%>
<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set"%>
<div class="container">

	<h2 class="sub-header">修改流程</h2>


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

		<div class="col-md-2">
			<h4>&nbsp;</h4>
			<span class="glyphicon glyphicon-minus"></span> 
			<span class="glyphicon glyphicon-minus"></span> 
			<span class="glyphicon glyphicon-minus"></span> 
			<span class="glyphicon glyphicon-minus"></span>
			<span class="glyphicon glyphicon-hand-right"></span>
		</div>

		<div class="col-md-3">
			<%
			Defaultflow flow=(Defaultflow)request.getAttribute("flow");
			%>
			<h4>工作流</h4>
			<div class="cdivcss" id="addworkflow">
				<form role="form">
					<div class="form-group">
						<input name="myflowName" value="<%=flow.getName() %>" type="text" class="form-control" id="flowName" placeholder="工作流名称">
					</div>
				</form>
				<ol>
			<%
				String flowinfo[]=flow.getParticipate().split(",");
				for(String id : flowinfo) {
			%>
				<li data-roleid="<%=id %>"><%=roles.get(Integer.valueOf(id)).getAlias() %></li>
			<%
				}

			%>
				</ol>

			</div>
		</div>

		<div class="col-md-4">
			<h4>&nbsp;</h4>
			<button id="updatewl" type="button" class="btn btn-danger">修改</button>
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
	$("#updatewl").attr("disabled", "disabled");

	$("#rolelist li").on("click", function() {
		$("#updatewl").removeAttr("disabled");
		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		$(this).clone().removeClass("libgcl").appendTo("#addworkflow ol");
	});

	$("#addworkflow").on("click", "li", function() {
		$(this).remove();
		$("#updatewl").removeAttr("disabled");
		if ($("#addworkflow li").length == 0) {
			$("#updatewl").attr("disabled", "disabled");
		}
	});

	$("#flowName").keyup(function() {
		$("#updatewl").removeAttr("disabled");
	});
	
	var mWorkflow = "";
	var wName = "";

	$("#updatewl").on("click", function() {
		wName=$("#flowName").val();
		if (wName == "" || $("#addworkflow li").length == 0) {
			alert("流程名或流程为空");
		} else {
			
			mWorkflow = "";
			$("#addworkflow li").each(function(index) {
				mWorkflow += $(this).data("roleid") + ",";
			});
			mWorkflow = mWorkflow.substring(0, mWorkflow.length - 1);

			$.post("uworkflow", {
				mWorkflow : mWorkflow,
				wName : wName,
				mflowid : <%=flow.getId() %>
			}, function(data) {
				if (data == "ok") {
					$("#updatewl").attr("disabled", "disabled");
					alert("流程修改成功");
				} else {
					alert(data);
				}
			});
		}
	});
</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
