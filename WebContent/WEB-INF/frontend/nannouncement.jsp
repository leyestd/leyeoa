<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="backend.javabean.Announcement" %>
<%
Announcement announcement=(Announcement)request.getAttribute("announcement");
%>
<div class="container">
	<h2 class="sub-header">公告明细</h2>
	<script src="../ckeditor/ckeditor.js"></script>

	<form class="form-horizontal"
		role="form">
		<div class="form-group">
			<div class="col-md-6">
				<input name="announcementName" value="<%=announcement.getName() %>" type="text" class="form-control"
					placeholder="表单名称">
			</div>
			<div class="col-md-6">
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-11">
				<textarea class="ckeditor" name="editor1"><%=announcement.getContent() %></textarea>
			</div>
			<div class="col-md-1">
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
