<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.Account"%>
<%@ page
	import="rbac.javabean.AccountPermissionRole,java.util.ArrayList"%>

<div class="container">
	<!-- Example row of columns -->

	<%
		Account user = (Account) request.getAttribute("userinfo");
	%>
	<h2 class="sub-header">用户信息</h2>

	<form action="userInfo" method="post" class="form-horizontal"
		role="form">

		<div class="form-group">
			<label for="password" class="col-md-2 control-label">密码</label>
			<div class="col-md-6">
				<input name="password" type="password" class="form-control"
					id="password" placeholder="密码">
			</div>
			<div class="col-md-4">
				<h5>
					<%
						String checked = "";

						if (request.getParameter("checked") != null) {
							checked = "修改成功";
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
			<label for="fullname" class="col-md-2 control-label">全名</label>
			<div class="col-md-6">
				<input name="fullname" value="<%=user.getFullname()%>" type="text"
					class="form-control" id="fullname" placeholder="全名">
			</div>
			<div class="col-md-4"></div>
		</div>

		<div class="form-group">
			<label for="email" class="col-md-2 control-label">邮箱</label>
			<div class="col-md-6">
				<input name="email" type="text" value="<%=user.getEmail()%>"
					class="form-control" id="email" placeholder="邮箱">
			</div>
			<div class="col-md-4"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<button type="submit" class="btn btn-warning">修改</button>
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
