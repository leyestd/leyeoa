<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole" %>
<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set" %>

<div class="container">
	<!-- Example row of columns -->
<%
AccountPermissionRole role=(AccountPermissionRole)request.getAttribute("role");
%>

	<h2 class="sub-header">修改角色</h2>


	<form action="urole" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="rolename" class="col-md-1 control-label">角色</label>
			<div class="col-md-4">
				<input name="newName" value="<%=role.getName() %>" type="text" class="form-control" id="rolename"
					placeholder="角色">
				<input type="hidden" name="roleName" value="<%=role.getName() %>">
			</div>
			<div class="col-md-1">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
			<div class="col-md-6">
			<h5>
			<% 
			String checked="";
			
			if(request.getParameter("checked")!=null) {
				checked="修改成功";
			}else {
				if(request.getAttribute("checked")!=null)
					checked=(String)request.getAttribute("checked"); 			
			}
			out.print(checked);
			%>
			</h5>
			</div>
		</div>

		<div class="form-group">
			<label for="alias" class="col-md-1 control-label">别名</label>
			<div class="col-md-4">
				<input  name="alias" value="<%=role.getAlias() %>"type="text" class="form-control" id="alias"
					placeholder="别名">
			</div>
			<div class="col-md-7"></div>
		</div>

	</form>

<hr>

	<div class="row">
		<div class="col-md-3">
			<h4>已有高层</h4>
			<%
			HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)application.getAttribute("roles");
			Integer advanced_role=(Integer)request.getAttribute("advancedrole");
			%>
			<div class="udivcss" id="ownedrole">
				<ol>	
					<%if (advanced_role != null) { %>
						<li data-roleid="<%=advanced_role%>"><%=roles.get(advanced_role).getAlias() %></li>
					<% }%>
				</ol>
				
			</div>
		</div>

		<div class="col-md-1">
			<h4>&nbsp;</h4>
			<button id="createur" type="button" class="btn btn-default">&lt;&lt;&lt;</button>
		</div>

		<div class="col-md-3">
			<h4>系统角色</h4>
			<div class="udivcss" id="rolelist">
				<ol>
					<%
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
		<div class="col-md-5">

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
	$("#createur").attr("disabled", "disabled");

	var roleid="<%=role.getId() %>";
	var advancedid;
	var clickrole;
	var deleterole;
	
	$("#rolelist li").on("click", function() {
		advancedid = $(this).data("roleid");
		$("#createur").removeAttr("disabled");

		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		clickrole=$(this).clone().removeClass("libgcl");
	});

	$("#createur").on("click", function() {
		$.post("urolehierarchy", {
			roleid : roleid,
			advancedid: advancedid,
		}, function(data) {
			if (data == "ok") {
				$("#ownedrole li").remove();
				clickrole.appendTo($("#ownedrole ol"));
				alert("高层关系修改成功");
			} else {
				alert("修改失败");
			}
		});
	});
	
	$("#ownedrole").on("click", "li", function() {
		deleterole=$(this);
		var confirm_ = confirm("您确认要删除?");
		if (confirm_) {
			$.post("urolehierarchy", {
				roleid : roleid,
			}, function(data) {
				if (data == "ok") {
					deleterole.remove();
					alert("高层关系删除成功");
				} else {
					alert("删除失败");
				}
			});
		}
	});

</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>