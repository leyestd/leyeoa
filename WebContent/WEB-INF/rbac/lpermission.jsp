<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">

		<div class="col-md-12">

			<h2 class="sub-header">操作列表</h2>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>id</th>
							<th>操作</th>
							<th>别名</th>
							<th>上层</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<%
							List<ArrayList<Object>> rows;
											rows = (List<ArrayList<Object>>) request.getAttribute("rows");
											if (rows == null) {
						%>
						<tr>
							<td class="text-center" colspan="4">没有相关记录</td>
						</tr>
						<%
							} else {

												for (List<Object> row : rows) {
						%>
						<tr>
							<%
								for (Object column : row) {
							%>

							<td><%=(column==null)?"控制器":column.toString()%></td>
							<%
								}
							%>
							<td><a href="upermission?permissionName=<%=row.get(1)%>"><span
									class="glyphicon glyphicon-pencil"></span></a> &nbsp; <a
								data-permissionid="<%=row.get(0)%>" href="#"><span
									class="glyphicon glyphicon-remove"></span></a></td>
						</tr>

						<%
							}
											}
						%>

					</tbody>
				</table>
			</div>
			<%
				if (rows != null) {
						int pageNumber = (Integer) request.getAttribute("pageNumber");
						int countPage = (Integer) request.getAttribute("countPage");
			%>
			<div class="text-center">
				<ul class="pagination">

					<%
						if (pageNumber > 1) {
					%>
					<li><a href="lpermission?pageNumber=<%=pageNumber - 1%>">&laquo;</a></li>
					<%
						} else {
					%>
					<li class="disabled"><a href="#">&laquo;</a></li>
					<%
						}
					%>

					<%
						if (countPage <= 5) {
											for (int i = 1; i <= countPage; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="lpermission?pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
										} else if (pageNumber + 4 >= countPage) {
											for (int i = countPage - 4; i <= countPage; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="lpermission?pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
										} else {
											for (int i = pageNumber; i <= pageNumber + 4; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="lpermission?pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
										}
					%>


					<%
						if (pageNumber < countPage) {
					%>
					<li><a href="lpermission?pageNumber=<%=pageNumber + 1%>">&raquo;</a></li>
					<%
						} else {
					%>
					<li class="disabled"><a href="#">&raquo;</a></li>
					<%
						}
					%>


				</ul>
			</div>
			<%
				}
			%>

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
	$(".glyphicon-remove").parent().on("click", function() {
		var confirm_ = confirm("您确认要删除?");
		if (confirm_) {
			var permissionid = $(this).data("permissionid");
			var pparent = $(this).parent().parent();

			$.post("dpermission", {
				permissionId : permissionid
			}, function(data) {
				if (data == "ok") {
					pparent.remove();
				} else {
					alert("删除失败，您有关系存在");
				}
			});
		}
	});
</script>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>