<%@ include file="/WEB-INF/jspf/backend/head.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,rbac.javabean.Department" %>   

<div class="container">
	<!-- Example row of columns -->


	<h2 class="sub-header">新建部门</h2>
  <div class="row">
   <div class="col-md-6">
	<form action="cdepartment" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="departmentname" class="col-md-2 control-label">部门</label>
			<div class="col-md-8">
				<input name="name" type="text" class="form-control" id="departmentname"
					placeholder="部门">
			</div>
			<div class="col-md-2"></div>
		</div>
		
		
		<div class="form-group">
			<label for="departmentalias" class="col-md-2 control-label">别名</label>
			<div class="col-md-8">
				<input  name="alias" type="text" class="form-control" id="departmentalias"
					placeholder="别名">
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<button id="createdepartment" type="button" class="btn btn-danger">添加</button>
			</div>
		</div>
	</form>
   </div>
   <div class="col-md-6">
			<div class="divcss" id="departmentlist">
				<h4>上层部门</h4>
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
	$("#departmentlist").on("click", "li", function() {
		pid=$(this).data("departmentid");
		
		$("#departmentlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
	});
	
	$("#createdepartment").on("click", function() {
		var DepartmentName=$("#departmentname").val();
		var DepartmentAlias=$("#departmentalias").val();
		
		 $.post( "cdepartment", { pid : pid,
			 				name : DepartmentName ,
			 				alias : DepartmentAlias ,
		 }, function( data ) {
	            if(data.substr(0, 2)=="ok") {
	            	alert("添加部门成功");
	            	$("#departmentlist ol").append('<li data-departmentid="'+data.substr(2)+'">'+DepartmentAlias+'</li>');
	            	$("#departmentname").val("");
	            	$("#departmentalias").val("");
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