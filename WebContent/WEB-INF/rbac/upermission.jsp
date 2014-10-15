<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rbac.javabean.AccountPermissionRole" %>

<div class="container">
	<!-- Example row of columns -->
<%
AccountPermissionRole permission=(AccountPermissionRole)request.getAttribute("permission");
%>

	<h2 class="sub-header">修改操作</h2>


	<form action="upermission" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="newName" class="col-md-2 control-label">操作</label>
			<div class="col-md-4">
				<input name="newName" value="<%=permission.getName() %>" type="text" class="form-control" id="newName"
					placeholder="操作">
				<input type="hidden" name="permissionName" value="<%=permission.getName() %>">
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
			<label for="alias" class="col-md-2 control-label">别名</label>
			<div class="col-md-4">
				<input  name="alias" value="<%=permission.getAlias() %>" type="text" class="form-control" id="alias"
					placeholder="别名">
			</div>
			<div class="col-md-6"></div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-2 col-md-10">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
		</div>
	</form>


	<%@ include file="/WEB-INF/jspf/backend/footer.jsp"%>