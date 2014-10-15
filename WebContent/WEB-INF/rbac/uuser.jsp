<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.Account"%>
<%@ page import="rbac.javabean.AccountPermissionRole,java.util.ArrayList,rbac.javabean.Department"%>

<div class="container">
	<!-- Example row of columns -->

	<%
		Account user = (Account) request.getAttribute("user");
	%>
	<h2 class="sub-header">修改用户</h2>
	<form action="uuser" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="newUsername" class="col-md-1 control-label">账号</label>
			<div class="col-md-4">
				<input name="newUsername" value="<%=user.getUsername()%>"
					type="text" class="form-control" id="newuser" placeholder="账号">
				<input id="olduser" type="hidden" name="username"
					value="<%=user.getUsername()%>"> <input id="userid"
					type="hidden" name="accoutid" value="<%=user.getId()%>">
			</div>
			<div class="col-md-1">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
			<div class="col-md-6">
				<h5>
					<%
						String checked = "";

						if (request.getParameter("checked") != null) {
							checked = "修改成功";
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
			<label for="password" class="col-md-1 control-label">密码</label>
			<div class="col-md-4">
				<input name="password" type="password" class="form-control"
					id="password" placeholder="密码">
			</div>
			<div class="col-md-7"></div>
		</div>

		<div class="form-group">
			<label for="fullname" class="col-md-1 control-label">全名</label>
			<div class="col-md-4">
				<input name="fullname" value="<%=user.getFullname()%>" type="text"
					class="form-control" id="fullname" placeholder="全名">
			</div>
			<div class="col-md-7"></div>
		</div>

		<div class="form-group">
			<label for="email" class="col-md-1 control-label">邮箱</label>
			<div class="col-md-4">
				<input name="email" type="text" value="<%=user.getEmail()%>"
					class="form-control" id="email" placeholder="邮箱">
			</div>
			<div class="col-md-7"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-1 col-md-11">
				<div class="checkbox">
					<label> <input name="enabled" value="1" type="checkbox"
						<%=user.getEnable() == 1 ? " checked=\"checked\"" : ""%>>启用
					</label>
				</div>
			</div>
		</div>


	</form>

<hr>

	<div class="row">
		<div class="col-md-3">
			<h4>已有角色</h4>
			<%
			ArrayList<AccountPermissionRole> ownedRole=(ArrayList<AccountPermissionRole>)request.getAttribute("ownedRole");
			%>
			<div class="udivcss" id="ownedrole">
				<ol>
				<%
						for(AccountPermissionRole role : ownedRole)
						{
				%>
							<li data-roleid="<%=role.getId()%>"><%=role.getAlias()%></li>
				<%
						}
				%>

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
						ArrayList<AccountPermissionRole> roles=(ArrayList<AccountPermissionRole>)request.getAttribute("dbroles");
								for(AccountPermissionRole role : roles)
								{
					%>
					<li data-roleid="<%=role.getId()%>"><%=role.getAlias()%></li>
					<%
						}
					%>

				</ol>
			</div>
		</div>
		<div class="col-md-1">
			<h4>&nbsp;</h4>
			
			<button id="defaultr" type="button" class="btn btn-danger">默认</button>
		</div>
		
		<div class="col-md-3">
			  <h4>默认部门</h4>
			  <div class="udivcss" id="departmentlist">
				<ol>
					<%
						ArrayList<Department> deps=(ArrayList<Department>)request.getAttribute("departments");
								for(Department dep : deps) {
					%>
						<li data-departmentid="<%=dep.getId()%>"<%=user.getDepid()==dep.getId()?" class=\"libgcl\"":"" %>><%=dep.getAlias()%></li>
					<%
						}
					%>
				</ol>
			  </div>
		</div>
		
		<div class="col-md-1">
			<h4>&nbsp;</h4>
			
			<button id="defaultDep" type="button" class="btn btn-danger">默认</button>
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
	$("#defaultr").attr("disabled", "disabled");
	$("#defaultDep").attr("disabled", "disabled");
	$("#rolelist li").d
	
	var roleid;
	var userid = $("#userid").val();
	var clickrole;
	var deleterole;
	$("#rolelist li").on("click", function() {
		roleid = $(this).data("roleid");
		$("#createur").removeAttr("disabled");
		$("#defaultr").removeAttr("disabled");

		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		clickrole=$(this).clone().removeClass("libgcl");
	});

	$("#createur").on("click", function() {
		$.post("crelationship", {
			roleid : roleid,
			userid : userid
		}, function(data) {
			if (data == "ok") {
				clickrole.appendTo($("#ownedrole ol"));
				alert("添加角色成功");
			} else {
				alert("添加失败，您有角色关系存在");
			}
		});

	});
	
	$("#ownedrole").on("click", "li", function() {
		roleid = $(this).data("roleid");
		deleterole=$(this);
		var confirm_ = confirm("您确认要删除?");
		if (confirm_) {
			$.post("drelationship", {
				roleid : roleid,
				userid : userid
			}, function(data) {
				if (data == "ok") {
					deleterole.remove();
					alert("关系删除成功");
				} else {
					alert("默认角色关系不能删除");
				}
			});
		}
	});

	$("#defaultr").on("click", function() {
		$.post("sdefaultRole", {
			roleid : roleid,
			userid : userid
		}, function(data) {
			if (data == "1") {
				alert("角色添加成功，默认角色设置失败");
				clickrole.appendTo($("#ownedrole ol"));
			} else if (data == "2") {
				alert("默认角色设置成功");
			} else if (data == "3") {
				alert("角色添加成功，默认角色设置成功");
				clickrole.appendTo($("#ownedrole ol"));
			} else {
				alert("操作失败");
			}
		});
	});
	
	var pid=0;
	$("#departmentlist").on("click", "li", function() {
		depid=$(this).data("departmentid");
		$("#defaultDep").removeAttr("disabled");
		$("#departmentlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});
	
	$("#defaultDep").on("click", function() {
		 $.post( "sdefaultdepartment", { depid : depid,
			 							 userid : userid
		 }, function( data ) {
	            if(data=="ok") {
	            	alert("部门修改成功");
	            }else{
	            	alert("修改失败 "+data);
	            }
	        });
		
	});
	
</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
