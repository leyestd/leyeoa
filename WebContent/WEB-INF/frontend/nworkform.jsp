<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.HashMap,frontend.javabean.Typeform,java.util.Set"%>
<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h2>申请表单</h2>
		<hr>
		<div class="col-md-3">

			<div class="list-group">
				<%
					HashMap<Integer,Typeform> typeforms=(HashMap<Integer,Typeform>)request.getAttribute("typeforms");
					Set<Integer> typekey=typeforms.keySet();
					for(int key : typekey) {
				%>
				<a href="#" data-typeid="<%=key%>" class="list-group-item"><%=typeforms.get(key).getTypename()%></a>
				<%
					}
				%>

			</div>


		</div>
		<div class="col-md-9">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>名称</th>
						<th>申请</th>
					</tr>
				</thead>
				<tbody>
				
		<%
		for(int key : typekey) { 
			HashMap<Integer, String> workforms=typeforms.get(key).getWorkforms();
			Set<Integer> formkey=workforms.keySet();
			for(int fkey : formkey) {
		%>		
					<tr data-typeid="<%=key %>">
						<td><%=fkey %></td>
						<td><%=workforms.get(fkey) %></td>

						<td><a href="nworkflow?formid=<%=fkey %>"><span class="glyphicon glyphicon-pencil">.....</span></a>
						</td>
					</tr>
					
		<% } } %>

				</tbody>
			</table>
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
	$(".list-group a:first-child").addClass("active");
	var typeid = $(".list-group a:first-child").data("typeid");
	$("tbody tr").hide();
	$("tr[data-typeid=\""+typeid+"\"]").show();
	$(".list-group a").on("click", function() {
		var type_id=$(this).data("typeid");
		$("tbody tr").hide();
		$("tr[data-typeid=\""+type_id+"\"]").show();
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	});
</script>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>