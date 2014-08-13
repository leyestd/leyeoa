<%@ include file="/WEB-INF/jspf/backend/head.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set" %>   

<div class="container">
	<!-- Example row of columns -->


	<h2 class="sub-header">新建角色</h2>
  <div class="row">
   <div class="col-md-6">
	<form action="crole" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="rolename" class="col-md-2 control-label">角色</label>
			<div class="col-md-8">
				<input name="name" type="text" class="form-control" id="rolename"
					placeholder="角色">
			</div>
			<div class="col-md-2"></div>
		</div>
		
		
		<div class="form-group">
			<label for="rolealias" class="col-md-2 control-label">别名</label>
			<div class="col-md-8">
				<input  name="alias" type="text" class="form-control" id="rolealias"
					placeholder="别名">
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<button id="createrole" type="button" class="btn btn-danger">添加</button>
			</div>
		</div>
	</form>
   </div>
   <div class="col-md-6">
			<div class="divcss" id="rolelist">
				<ol>
<%
						HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)application.getAttribute("roles");
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

	var advanced_roleid=0;
	$("#rolelist li").on("click", function() {
		advanced_roleid=$(this).data("roleid");
		
		$("#rolelist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});
	
	$("#createrole").on("click", function() {
		var rolename=$("#rolename").val();
		var rolealias=$("#rolealias").val();
		
		 $.post( "crole", { advanced_roleid : advanced_roleid,
			 				name : rolename ,
			 				alias : rolealias ,
		 }, function( data ) {
	            if(data=="ok") {
	            	alert("添加角色成功");
	            	$("#rolename").val("");
	            	$("#rolealias").val("");
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