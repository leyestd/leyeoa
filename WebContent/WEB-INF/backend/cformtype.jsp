<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container">
	<!-- Example row of columns -->


	<h2 class="sub-header">新建表单类型</h2>


	<form action="cformtype" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="formtype" class="col-md-2 control-label">名称</label>
			<div class="col-md-4">
				<input name="name" type="text" class="form-control" id="formtype"
					placeholder="名称">
			</div>
			<div class="col-md-6">
			<h5>
			<% 
			String checked="";
			
			if(request.getParameter("checked")!=null) {
				checked="添加成功";
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
			<div class="col-md-offset-2 col-md-10">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
		</div>
	</form>


	<%@ include file="/WEB-INF/jspf/backend/footer.jsp"%>