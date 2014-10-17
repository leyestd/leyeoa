<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole,java.util.ArrayList,java.util.HashMap,rbac.javabean.Permission,java.util.Set" %>

<div class="container">
	<!-- Example row of columns -->


	<h2 class="sub-header">新增关系</h2>
	<div class="row">
		<div class="col-md-3">
			<h4>角色</h4>
			<div class="cdivcss" id="role">
				<ol>
				<%
					ArrayList<AccountPermissionRole> roles=(ArrayList<AccountPermissionRole>)request.getAttribute("roles");
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
		<div class="col-md-3">
			<h4>用户</h4>

			<div class="cdivcss" id="user">
				<ol>
				<%
					ArrayList<AccountPermissionRole> users=(ArrayList<AccountPermissionRole>)request.getAttribute("users");
						for(AccountPermissionRole user : users)
						{
				%>
					<li data-userid="<%=user.getId()%>" data-fullname="<%=user.getAlias()%>"> <abbr title="<%=user.getName()%>"><%=user.getAlias()%></abbr></li>
				
				<%
									}
								%>
				</ol>
			</div>
		</div>


		<div class="col-md-3">
			<h4>操作</h4>

			<div class="cdivcss" id="permission">
				
				<%
				HashMap<Permission,ArrayList<Permission>> ControllerActions=(HashMap<Permission,ArrayList<Permission>>)request.getAttribute("ControllerActions");
				Set<Permission> roleskey=ControllerActions.keySet();
				for(Permission key : roleskey) {	
				%>
					
				  <div class="panel panel-warning">
  					<div class="panel-heading"><%=key.getAlias() %></div>
					<ul class="list-group">
				<%	
					for(Permission per : ControllerActions.get(key))
					{%>
						<li class="list-group-item list-group-item-success" data-permissionid="<%=per.getId()%>"><%=per.getAlias()%></li>
					<% }%>
					</ul>
				  </div>
				<% }%>
			</div>
		</div>
		
		<div class="col-md-3">
			<h4>已新增</h4>
			<div class="cdivcss" id="crp">
				<ol>

				</ol>
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
	var roleid, userid, permissionid;
	var rolet, usert, permissiont;

	$("#role li").on("click", function() {
		$(this).siblings().removeClass("libgcl");
		$(this).addClass("libgcl");
		roleid = $(this).data("roleid");
		rolet = $(this).text();

	});

	$("#user li").on("click", function() {
		if (roleid != undefined) {
			$(this).siblings().removeClass("libgcl");
			$(this).addClass("libgcl");
			userid = $(this).data("userid");
			usert = $(this).data("fullname");
	        $.post( "crelationship", { roleid: roleid, userid: userid}, function( data ) {
	            if(data=="ok") {
	            	$("#crp ol").append("<li>" + rolet + "...." + usert + "</li>");
	            }else{
	            	alert("添加失败，您有角色关系存在");
	            }
	        });
		}

	});

	$("#permission li").on(
			"click",
			function() {

				if (roleid != undefined) {
					$(this).siblings().removeClass("libgcl");
					$(this).addClass("libgcl");
					permissionid = $(this).data("permissionid");
					permissiont = $(this).text();
			        $.post( "crelationship", { roleid: roleid, permissionid: permissionid}, function( data ) {
			            if(data=="ok") {
			            	$("#crp ol").append("<li>" + rolet + "...." + permissiont + "</li>");
			            }else{
			            	alert("添加失败，您有角色关系存在");
			            }
			        });
				}
			});
</script>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
