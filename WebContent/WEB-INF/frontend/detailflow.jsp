<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.LinkedHashMap,java.util.Set" %>
<%
	String accountInfo=(String)request.getAttribute("accountInfo");
	LinkedHashMap<String,String> finishInfo=(LinkedHashMap<String,String>)request.getAttribute("finishInfo");
	
%>
<div class="container">
	<h2 class="sub-header">单据状态</h2>
	<script src="../ckeditor/ckeditor.js"></script>

	<button type="button" class="btn btn-default">发起人：<%=accountInfo %></button>
	<button type="button" class="btn btn-primary">已签人员：</button>
	<%
	if(finishInfo==null) {
	%>
		<button type="button" class="btn btn-success">无</button>
	<%
	}else {
		Set<String> accountkey = finishInfo.keySet();
		for (String key : accountkey) {
		%>
			<button type="button" class="btn btn-success"><%=finishInfo.get(key) %></button>
	<% 
		}
	}
	%>
	<hr>

	<form action="modifyflow" method="post" class="form" role="form">

		<div class="row">
			<div class="col-md-11">
				<textarea class="ckeditor" name="editor1">
				<%=request.getAttribute("content") %>
      			</textarea>
			</div>

			<div class="col-md-1">
				<div class="radio">
					<label> <input type="radio" name="decision"
						id="optionsRadios1" value="agree" checked> 同意
					</label>
				</div>

				<div class="radio">
					<label> <input type="radio" name="decision"
						id="optionsRadios2" value="reject"> 拒绝
					</label>
				</div>

				<hr>
				<input name="flowid" value="<%=request.getAttribute("flowid") %>" type="hidden">
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
