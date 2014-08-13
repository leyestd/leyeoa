<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">
	<h2 class="sub-header">新建公告</h2>
	<script src="../fckeditor/ckeditor.js"></script>

	<form action="cannouncement" method="post" class="form-horizontal"
		role="form">
		<div class="form-group">
			<div class="col-md-6">
				<input name="announcementName" type="text" class="form-control"
					placeholder="表单名称">
			</div>
			<div class="col-md-6">
				<h5>
					<%
						String checked = "";

						if (request.getParameter("checked") != null) {
							checked = "添加成功";
						} else {
							if (request.getAttribute("checked") != null)
								checked = (String) request.getAttribute("checked");
						}
						out.print(checked);
					%>
				</h5>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-11">
				<textarea class="ckeditor" name="editor1"></textarea>
			</div>
			<div class="col-md-1">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
		</div>
	</form>


	<hr>

	<footer>
		<p>&copy; Company 2014</p>
	</footer>
</div>
<!-- /container -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="../bootstrap/js/jquery-1.11.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
