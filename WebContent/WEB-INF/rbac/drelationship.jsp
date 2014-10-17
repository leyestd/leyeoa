<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole,java.util.ArrayList" %>
<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set,rbac.javabean.RbacPermission,rbac.javabean.Permission" %>

	<div class="container">
		<h2 class="sub-header">删除关系</h2>
		<div class="row">
			<div class="col-md-4">

				<div class="divcss" id="droleuser">
					<ul class="list-unstyled">
					<%
						HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)application.getAttribute("roles");
								HashMap<Integer,String> users;
								Set<Integer> roleskey=roles.keySet();
								for(Integer key : roleskey) {
					%>
						<li data-roleid="<%=key%>"><%=roles.get(key).getAlias()%></li>
						<li>
							<ul>
							<%
								users=(HashMap<Integer,String>)roles.get(key).getUser();
									              Set<Integer> userskey=users.keySet();
									              for(Integer userkey : userskey) {
							%>
				            	  <li class="clicked" data-userid="<%=userkey%>"><%=users.get(userkey)%></li>
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
				<h4>角色&amp;用户</h4>
				<button id="deleteru" type="button" class="btn btn-danger">删除</button>
			</div>

			<div class="col-md-4">

				<div class="divcss">
					<ul class="list-unstyled" id="drolepermission">
						<%
						HashMap<Integer,RbacPermission> rolePermission=(HashMap<Integer,RbacPermission>)request.getAttribute("rolesPermission");
									HashMap<Integer,String> permissions;
									roleskey=rolePermission.keySet();
						for(Integer key : roleskey) {
						%>
						<li class="rolestyle" data-roleid="<%=key %>"><%=rolePermission.get(key).getAlias() %>
						
							<ul>
							<%
							HashMap<String ,ArrayList<Permission>> ControllerAction=rolePermission.get(key).getPermission();
							Set<String> controllerKey=ControllerAction.keySet();
							
							  
					         for(String ckey : controllerKey) {				               
					        %>
					         <li><%=ckey %>
			
					         	<ul>
					        <%	  
				              for(Permission action : ControllerAction.get(ckey)) {
				            %>
				            	  <li class="clicked" data-permissionid="<%=action.getId() %>"><%=action.getAlias() %></li>
				           <%
				              }
					        
							%>
								</ul>
							  
   
							<% }%>
							</ul>

						</li>
						
					<% } %>
						
					</ul>
				</div>
			</div>
			<div class="col-md-2">
				<h4>角色&amp;操作</h4>
				<button id="deleterp" type="button" class="btn btn-danger">删除</button>
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
		$("#deleteru").attr("disabled", "disabled");
		$("#droleuser > ul > li:odd").hide();

		$("#droleuser > ul > li:even").on("click", function() {
			$("#droleuser > ul > li:odd").hide();
			$(this).next().show();

		});

		var userid, roleid;

		$("#droleuser li li").on("click", function() {
			$("#deleteru").removeAttr("disabled");
			userid = $(this).data("userid");
			$("#droleuser li li").removeClass("libgcl");
			$(this).addClass("libgcl");

			roleid = $(this).parent().parent().prev().data("roleid");
		});

		$("#deleteru").on("click", function() {
			var confirm_ = confirm("您确认要删除?");
			if (confirm_) {
				$.post("drelationship", {
					userid : userid,
					roleid : roleid
				}, function(data) {
				 	if(data=="ok") {
				 		$("#droleuser  .libgcl").remove();
					 }else {
						 alert("默认角色关系不可删");
				 	}
				});
				$("#deleteru").attr("disabled", "disabled");
			}
		});

		
		$("#drolepermission > li > ul").hide();
		$("#deleterp").attr("disabled", "disabled");

		$("#drolepermission > li").on("click", function() {
			$(this).children().show();
			$(this).siblings().children().hide();
		});

		var permissionid, proleid;

		$(".clicked").on("click", function() {
			$("#deleterp").removeAttr("disabled");
			permissionid = $(this).data("permissionid");
			$(".clicked").removeClass("libgcl");
			$(this).addClass("libgcl");

			proleid = $(this).closest(".rolestyle").data("roleid");
		});

		$("#deleterp").on("click", function() {
			var confirm_ = confirm("您确认要删除?");
			if (confirm_) {
				$.post("drelationship", {
					permissionid : permissionid,
					proleid : proleid
				}, function(data) {
				 	if(data=="ok") {
						 $("#drolepermission .libgcl").remove();
					 }else {
						 alert("数据库操作失败");
				 	}
				});
				$("#deleterp").attr("disabled", "disabled");
			}
		});
	</script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
