<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,rbac.javabean.Permission"%>
<div class="container">
	<!-- Example row of columns -->

	<h2 class="sub-header">新建操作</h2>

	<div class="row">
		<div class="col-md-6">
			<form action="cpermission" method="post" class="form-horizontal"
				role="form">
				<div class="form-group">
					<label for="inputName" class="col-md-2 control-label">操作</label>
					<div class="col-md-8">
						<input name="name" type="text" class="form-control"
							id="inputName" placeholder="操作">
					</div>
					<div class="col-md-2"></div>
				</div>

				<div class="form-group">
					<label for="inputAlias" class="col-md-2 control-label">别名</label>
					<div class="col-md-8">
						<input name="alias" type="text" class="form-control"
							id="inputAlias" placeholder="别名">
					</div>
					<div class="col-md-2"></div>
				</div>

				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button id="createPermission" type="button" class="btn btn-warning">提交</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-6">
			<div class="divcss" id="permissionlist">
				<h4>控制器</h4>
				<ol>
					<%
						ArrayList<Permission> permissions=(ArrayList<Permission>)request.getAttribute("permissions");
								for(Permission p : permissions) {
					%>
						<li data-permissionid="<%=p.getId()%>"><%=p.getAlias()%></li>
					<%
						}
					%>
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

	var pid=0;
	$("#permissionlist").on("click", "li", function() {
		pid=$(this).data("permissionid");
		
		$("#permissionlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});
	
	$("#createPermission").on("click", function() {
		var permissionName=$("#inputName").val();
		var permissionAlias=$("#inputAlias").val();
		
		 $.post( "cpermission", { pid : pid,
			 				name : permissionName ,
			 				alias : permissionAlias ,
		 }, function( data ) {
	            if(data=="ok") {
	            	alert("添加操作成功");
	            	$("#inputName").val("");
	            	$("#inputAlias").val("");
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