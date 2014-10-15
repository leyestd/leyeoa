<%@ include file="/WEB-INF/jspf/backend/head.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole,java.util.ArrayList,rbac.javabean.Department" %>   

<div class="container">
	<!-- Example row of columns -->


	<h2 class="sub-header">新建用户</h2>
  <div class="row">
   <div class="col-md-6">
	<form action="cuser" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="username" class="col-md-2 control-label">账号</label>
			<div class="col-md-8">
				<input name="username" type="text" class="form-control" id="username"
					placeholder="账号">
			</div>
			<div class="col-md-2"></div>
		</div>
		<div class="form-group">
			<label for="password" class="col-md-2 control-label">密码</label>
			<div class="col-md-8">
				<input name="password" type="password" class="form-control" id="password"
					placeholder="密码">
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="form-group">
			<label for="fullname" class="col-md-2 control-label">全名</label>
			<div class="col-md-8">
				<input name="fullname" type="text" class="form-control" id="fullname"
					placeholder="全名">
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="form-group">
			<label for="inputEmail3" class="col-md-2 control-label">邮箱</label>
			<div class="col-md-8">
				<input name="email" type="text" class="form-control" id="email"
					placeholder="邮箱">
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<div class="checkbox">
					<label> <input name="enabled" value="1" type="checkbox" checked="checked" id="checkenable">启用
					</label>
				</div>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<button id="createuser" type="button" class="btn btn-danger">添加</button>
			</div>
		</div>
	</form>
   </div>
   <div class="col-md-6">
   		<div class="row">
   			<div class="col-md-6">
				<div class="rdivcss" id="rolelist">
					<h4>默认角色</h4>
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
			<div class="col-md-6">
			  <div class="rdivcss" id="departmentlist">
				<h4>默认部门</h4>
				<ol>
					<%
						ArrayList<Department> deps=(ArrayList<Department>)request.getAttribute("departments");
								for(Department dep : deps) {
					%>
						<li data-departmentid="<%=dep.getId()%>"><%=dep.getAlias()%></li>
					<%
						}
					%>
				</ol>
			  </div>
			</div>
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
	$("#createuser").attr("disabled", "disabled");

	var default_roleid="";
	var default_depid="";
	$("#rolelist li").on("click", function() {
		default_roleid=$(this).data("roleid");
		if(default_roleid!="" && default_depid!="") {
			$("#createuser").removeAttr("disabled");
		}
		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});

	$("#departmentlist li").on("click", function() {
		default_depid=$(this).data("departmentid");
		if(default_roleid!="" && default_depid!="") {
			$("#createuser").removeAttr("disabled");
		}
		
		$("#departmentlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});
	
	$("#createuser").on("click", function() {
		var username=$("#username").val();
		var password=$("#password").val();
		var fullname=$("#fullname").val();
		var email=$("#email").val();
		var enabled=$("#checkenable").val();
		
		 $.post( "cuser", { default_roleid : default_roleid,
			 				default_depid : default_depid,
			 				username : username ,
			 				password : password ,
			 				fullname : fullname ,
			 				email : email,
			 				enabled : enabled 
		 }, function( data ) {
	            if(data=="ok") {
	            	alert("添加用户成功");
	            	$("#username").val("");
	            	$("#password").val("");
	            	$("#fullname").val("");
	            	$("#email").val("");
	            }else{
	            	alert("添加失败 "+data);
	            }
	        });
		
	});
	

</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>