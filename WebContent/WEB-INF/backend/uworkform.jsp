<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="backend.javabean.Workform,java.util.ArrayList,backend.javabean.Defaultflow,backend.javabean.Formtype"%>

<div class="container">
	<script src="../fckeditor/ckeditor.js"></script>
	<%
		Workform form=(Workform)request.getAttribute("workform");
			ArrayList<Defaultflow> activeflows=(ArrayList<Defaultflow>)request.getAttribute("activeflows");
			ArrayList<Formtype> formtype=(ArrayList<Formtype>)request.getAttribute("formtype");
	%>


	<div id="editform">
		<h2 class="sub-header">修改单据</h2>
		<form action="uworkform" method="post" class="form-horizontal"
			role="form">
			<div class="form-group">
				<div class="col-md-6">
					<input name="workformName" value="<%=form.getName()%>" type="text"
						class="form-control" placeholder="表单名称"> <input
						name="workformId" value="<%=form.getId()%>" type="hidden">
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
				<div class="col-md-6">
					<select name="formtypeId" class="form-control">
						<%
							for(Formtype type : formtype) {
						%>
						<option value="<%=type.getId()%>"<%=(form.getType_id()==type.getId())?" selected=\"selected\"":""%>><%=type.getName()%></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="col-md-6"></div>
			</div>

			<div class="form-group">
				<div class="col-md-10">
					<textarea class="ckeditor" name="editor1"><%=form.getContent()%></textarea>
				</div>
				<div class="col-md-2">
					<button type="submit" class="btn btn-warning">提交</button>
					&nbsp;
					<button id="updateform" type="button" class="btn btn-danger">流程</button>
				</div>
			</div>

		</form>
	</div>

	<div id="editflow">
		<h2 class="sub-header">修改流程</h2>
		<div class="row">
			<div class="col-md-4">
				<h4>已有流程</h4>
				<div id="activeflows" class="divcss">				
					<ol>
						<%
							for(Defaultflow flow :  activeflows) {
						%>
						<li data-flowid=<%=flow.getId()%>><%=flow.getName()%></li>
						<%
							}
						%>
					</ol>
				</div>
			</div>

			<div class="col-md-2">
				<h4>&nbsp;</h4>
				 <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span> <span
					class="glyphicon glyphicon-minus"></span>
					<span class="glyphicon glyphicon-hand-right"></span>
			</div>

			<div class="col-md-4">
				<h4>已删流程</h4>
				<div id="deleteflows" class="divcss">
					<ol>

					</ol>
				</div>
			</div>
			<div class="col-md-2">
				<button id="updateflow" type="button" class="btn btn-danger">表单</button>
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
	$("#editflow").hide();
	$("#updateform").on("click", function() {
		$("#editform").hide();
		$("#editflow").show();

	});
	$("#updateflow").on("click", function() {
		$("#editform").show();
		$("#editflow").hide();

	});

	$("#activeflows").on("click", "li", function() {
		var flowid = $(this).data("flowid");
		var formli = $(this);
		confirm_ = confirm("您确认要删除?");
		if (confirm_) {
			$.post("uworkflow", {
				flowId : flowid
			}, function(data) {
				if (data == "ok") {
					formli.appendTo( "#deleteflows ol" );
				} else {
					alert(data);
				}
			});
		}
	});

</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>

