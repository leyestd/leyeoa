<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List"%>

<link rel="stylesheet" href="../jstree/themes/default/style.min.css" />

<div class="container">
	<!-- Example row of columns -->
	<div class="row">

		<div class="col-md-12">

			<h2 class="sub-header">部门层次</h2>
			<div id="rbactree">
			<%
				String tt=(String)request.getAttribute("hierarchytree");
				out.print(tt);
			%>
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

<script src="../jstree/jstree.min.js"></script>

<script>
	$("#rbactree").jstree();	
</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>