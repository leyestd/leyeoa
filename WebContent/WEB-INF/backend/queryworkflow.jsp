<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.Calendar,java.util.ArrayList,java.util.List,java.util.HashMap,rbac.javabean.RbacAccount"%>
<%@ page
	import=	"java.util.Set,rbac.javabean.RbacRole" %>

<div class="container">
	<!-- Example row of columns -->

	<h2 class="sub-header">工作流查询</h2>

 <div class="row">
  <div class="col-md-6">
	<form action="queryworkflow" method="post" class="form-horizontal" role="form">
	
		<div class="form-group">
			<label for="mqueryid" class="col-md-4 control-label">ID号</label>
			<div class="col-md-8">
				<input name="queryid" type="text" class="form-control" id="mqueryrole" placeholder="ID">	
			</div>
		</div>
	
		<div class="form-group">
			<label for="mquerytype" class="col-md-4 control-label">流程</label>
			<div class="col-md-8" id="mquerytype">
				<select id="flowtype" name="querytype" class="form-control">
					<option value="general">角色流</option>
					<option value="custom">自定义流</option>
				</select>
			</div>
		</div>
	
		<div class="form-group">
			<label for="mquerytype" class="col-md-4 control-label">状态</label>
			<div class="col-md-8" id="mquerytype">
				<select name="status" class="form-control">
					<option value="0">未完成</option>
					<option value="1">已完成</option>
					<option value="2">已拒绝</option>
					<option value="3">全部</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-4"></div>
			<div class="col-md-8">
				<select name="year" class="form-control">
				<%
				Calendar calendar = Calendar.getInstance();
					int year=calendar.get(Calendar.YEAR);
				
				%>
					<option value="<%=year %>"><%=year %>年</option>
					<option value="<%=--year %>"><%=year %>年</option>
					<option value="<%=--year %>"><%=year %>年</option>
					<option value="<%=--year %>"><%=year %>年</option>
					<option value="<%=--year %>"><%=year %>年</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-4"></div>
			<div class="col-md-8">
				<select name="month" class="form-control">
					<option value="1">01月</option>
					<option value="2">02月</option>
					<option value="3">03月</option>
					<option value="4">04月</option>
					<option value="5">05月</option>
					<option value="6">06月</option>
					<option value="7">07月</option>
					<option value="8">08月</option>
					<option value="9">09月</option>
					<option value="10">10月</option>
					<option value="11">11月</option>
					<option value="12">12月</option>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-4"></div>
			<div class="col-md-8" id="mquerytype">
				<select name="tense" class="form-control">
					<option value="before">之前</option>
					<option value="after">之后</option>
					<option value="current">当月</option>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-4"></div>
			
			<div class="col-md-8">
				<button type="submit" class="btn btn-warning">查询</button>
			</div>
		</div>

	</form>
  </div>
  
  <div class="col-md-1">
  			 <span class="glyphicon glyphicon-hand-left"></span>
  			 <span class="glyphicon glyphicon-minus"></span> 
  			 <span class="glyphicon glyphicon-minus"></span>
  </div>
  
  <div class="col-md-5">
  	<div class="qdivcss" id="droleuser">
		<ul class="list-unstyled">
					<%
						HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)application.getAttribute("roles");
								HashMap<Integer,String> users;
								Set<Integer> roleskey=roles.keySet();
								for(Integer key : roleskey) {
					%>
						<li data-roleid="<%=key%>"><%=roles.get(key).getAlias()%></li>
						<li>
							<ul>
							<%
								users=(HashMap<Integer,String>)roles.get(key).getUser();
									              Set<Integer> userskey=users.keySet();
									              for(Integer userkey : userskey) {
							%>
				            	  <li data-userid="<%=userkey%>"><%=users.get(userkey)%></li>
				           <%
				           	}
				           %>

							</ul>

						</li>
					<%
						}
					%>
						
					</ul>
	</div>
  </div>
 </div>

<%if(!request.getAttribute("checked").equals("")) {%>
	<hr>
	<div class="row">
		<div class="col-md-12">
			<h2 class="sub-header">查询结果</h2>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>id</th>
							<th>名称</th>
							<th>申请人</th>
							<th>时间</th>
							<th>状态</th>
							<th>#</th>
						</tr>
					</thead>
					<tbody>
						<%
							HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext()
									.getAttribute("rbac");
							List<ArrayList<Object>> rows;
							rows = (List<ArrayList<Object>>) request.getAttribute("rows");
							if (rows == null) {
						%>
						<tr>
							<td class="text-center" colspan="6">没有相关记录</td>
						</tr>
						<%
							} else {

								for (List<Object> row : rows) {
						%>
						<tr <%=row.get(4).toString().equals("2") ? " class=\"danger\"" : ""%>>
							<td><%=row.get(0).toString()%></td>
							<td><%=row.get(1).toString()%></td>
							<td><%=rbac.get(Integer.valueOf(row.get(2).toString()))
							.getFullname()%></td>
							<td><%=row.get(3).toString()
							.substring(0, row.get(3).toString().length() - 2)%></td>
							<td>
								<%
									if (row.get(4).toString().equals("0")) {
												out.print("未完成");
											} else if (row.get(4).toString().equals("1")) {
												out.print("已完成");
											} else {
												out.print("拒绝");
											}
								%>
							</td>
							<td><a
								href="qdetailworkflow?flowid=<%=row.get(0).toString()%>"><span
									class="glyphicon glyphicon-pencil">.....</span></a></td>
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
					<li><a href="queryworkflow?year=<%=request.getAttribute("year") %>&month=<%=request.getAttribute("month") %>&tense=<%=request.getAttribute("tense") %>&status=<%=request.getAttribute("status")%>&querytype=<%=request.getAttribute("querytype")%>&queryid=<%=request.getAttribute("queryid")%>&pageNumber=<%=pageNumber - 1%>">&laquo;</a></li>
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
						href="queryworkflow?year=<%=request.getAttribute("year") %>&month=<%=request.getAttribute("month") %>&tense=<%=request.getAttribute("tense") %>&status=<%=request.getAttribute("status")%>&querytype=<%=request.getAttribute("querytype")%>&queryid=<%=request.getAttribute("queryid")%>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							} else if (pageNumber + 4 >= countPage) {
								for (int i = countPage - 4; i <= countPage; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="queryworkflow?year=<%=request.getAttribute("year") %>&month=<%=request.getAttribute("month") %>&tense=<%=request.getAttribute("tense") %>&status=<%=request.getAttribute("status")%>&querytype=<%=request.getAttribute("querytype")%>&queryid=<%=request.getAttribute("queryid")%>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							} else {
								for (int i = pageNumber; i <= pageNumber + 4; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="queryworkflow?year=<%=request.getAttribute("year") %>&month=<%=request.getAttribute("month") %>&tense=<%=request.getAttribute("tense") %>&status=<%=request.getAttribute("status")%>&querytype=<%=request.getAttribute("querytype")%>&queryid=<%=request.getAttribute("queryid")%>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							}
					%>


					<%
						if (pageNumber < countPage) {
					%>
					<li><a href="queryworkflow?year=<%=request.getAttribute("year") %>&month=<%=request.getAttribute("month") %>&tense=<%=request.getAttribute("tense") %>&status=<%=request.getAttribute("status")%>&querytype=<%=request.getAttribute("querytype")%>&queryid=<%=request.getAttribute("queryid")%>&pageNumber=<%=pageNumber + 1%>">&raquo;</a></li>
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
<%} %>

	<hr>

	<footer>
		<p>&copy; Company 2014</p>
	</footer>
</div>
<!-- /container -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<script src="../bootstrap/js/jquery-1.11.1.js"></script>

<script>
	$("#droleuser > ul > li:odd").hide();
	
	$("#droleuser > ul > li:even").on("click", function() {
		$("#flowtype option[value='custom']").removeProp("selected");
		$("#flowtype option[value='general']").prop("selected",true);
		$("#droleuser > ul > li:odd").hide();
		$(this).next().show();
		$("#droleuser > ul > li:even").removeClass("libgcl");
		$("#droleuser li li").removeClass("libgcl");
		$(this).addClass("libgcl");
		$("#mqueryrole").val($(this).data("roleid"));
	});
	
	$("#droleuser li li").on("click", function() {

		$("#flowtype option[value='general']").removeProp("selected");
		$("#flowtype option[value='custom']").prop("selected", true);
		$("#droleuser li li").removeClass("libgcl");
		$("#droleuser > ul > li:even").removeClass("libgcl");
		$(this).addClass("libgcl");
		$("#mqueryrole").val($(this).data("userid"));
	});

</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>

