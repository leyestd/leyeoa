<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="backend.javabean.Department" %>
<%@ page import="java.util.ArrayList" %>

<div class="container">
	<!-- Example row of columns -->
<%
Department dep=(Department)request.getAttribute("dep");
%>

	<h2 class="sub-header">修改部门</h2>


	<form action="udepartment" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="DepartmentName" class="col-md-1 control-label">部门</label>
			<div class="col-md-4">
				<input name="newName" value="<%=dep.getName() %>" type="text" class="form-control" id="DepartmentName"
					placeholder="部门">
				<input type="hidden" name="departmentId" value="<%=dep.getId() %>">
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
				<input  name="alias" value="<%=dep.getAlias() %>"type="text" class="form-control" id="alias"
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
			Department pdep=(Department)request.getAttribute("pdep");
			%>
			<div class="udivcss" id="pdep">
				<ol>	
					<%if (pdep != null) { %>
						<li data-pedid="<%=pdep.getId() %>"><%=pdep.getAlias() %></li>
					<% }%>
				</ol>
				
			</div>
		</div>

		<div class="col-md-1">
			<h4>&nbsp;</h4>
			<button id="updatePdep" type="button" class="btn btn-default">&lt;&lt;&lt;</button>
		</div>

		<div class="col-md-3">
			<h4>所有部门</h4>
			<div class="divcss" id="departmentlist">
				<ol>
					<%
						ArrayList<Department> deps=(ArrayList<Department>)request.getAttribute("departments");
								for(Department d : deps) {
					%>
						<li data-departmentid="<%=d.getId()%>"><%=d.getAlias()%></li>
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
	$("#updatePdep").attr("disabled", "disabled");

	var depid="<%=dep.getId() %>";
	var pdepid;
	var clickedDep;
	var deleteDep;
	
	$("#departmentlist li").on("click", function() {
		pdepid = $(this).data("departmentid");
		$("#updatePdep").removeAttr("disabled");

		$("#departmentlist li").removeClass("libgcl");
		$(this).addClass("libgcl");
		clickedDep=$(this).clone().removeClass("libgcl");
	});

	$("#updatePdep").on("click", function() {
		$.post("udephierarchy", {
			depid : depid,
			pdepid: pdepid,
		}, function(data) {
			if (data == "ok") {
				$("#pdep li").remove();
				clickedDep.appendTo($("#pdep ol"));
				alert("高层关系修改成功");
			} else {
				alert("修改失败");
			}
		});
	});
	
	$("#pdep").on("click", "li", function() {
		deleteDep=$(this);
		var confirm_ = confirm("您确认要删除?");
		if (confirm_) {
			$.post("udephierarchy", {
				depid : depid,
			}, function(data) {
				if (data == "ok") {
					deleteDep.remove();
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