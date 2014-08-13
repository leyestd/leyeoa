<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.Calendar,java.util.ArrayList,java.util.List,java.util.HashMap,rbac.javabean.RbacAccount"%>

<div class="container">
	<!-- Example row of columns -->

	<h2 class="sub-header">按月查询</h2>

	<form action="fquery" method="post" class="form-horizontal" role="form">
		<div class="form-group">
			<label for="querytype" class="col-md-2 control-label">类型</label>
			<div class="col-md-4" id="querytype">
				<select name="mytype" class="form-control">
					<option value="myapplication">申请</option>
					<option value="finishedflow">审批</option>
				</select>
			</div>
			<div class="col-md-6">
				<button type="submit" class="btn btn-warning">提交</button>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-2"></div>
			<div class="col-md-4">
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
			<div class="col-md-6"></div>
		</div>

		<div class="form-group">
			<div class="col-md-2"></div>
			<div class="col-md-4">
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
			<div class="col-md-6"></div>
		</div>

	</form>

<%if(!request.getAttribute("checked").equals("")) {%>
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
								href="<%=request.getAttribute("mytype") %>?flowid=<%=row.get(0).toString()%>"><span
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
					<li><a href="fquery?mytype=<%=request.getAttribute("pType") %>&year=<%=request.getAttribute("myYear") %>&month=<%=request.getAttribute("myMonth") %>&pageNumber=<%=pageNumber - 1%>">&laquo;</a></li>
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
						href="fquery?mytype=<%=request.getAttribute("pType") %>&year=<%=request.getAttribute("myYear") %>&month=<%=request.getAttribute("myMonth") %>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							} else if (pageNumber + 4 >= countPage) {
								for (int i = countPage - 4; i <= countPage; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="fquery?mytype=<%=request.getAttribute("pType") %>&year=<%=request.getAttribute("myYear") %>&month=<%=request.getAttribute("myMonth") %>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							} else {
								for (int i = pageNumber; i <= pageNumber + 4; i++) {
					%>
					<li <%=(i == pageNumber) ? " class=\"active\"" : ""%>><a
						href="fquery?mytype=<%=request.getAttribute("pType") %>&year=<%=request.getAttribute("myYear") %>&month=<%=request.getAttribute("myMonth") %>&pageNumber=<%=i%>"><%=i%></a></li>
					<%
						}
							}
					%>


					<%
						if (pageNumber < countPage) {
					%>
					<li><a href="fquery?mytype=<%=request.getAttribute("pType") %>&year=<%=request.getAttribute("myYear") %>&month=<%=request.getAttribute("myMonth") %>&pageNumber=<%=pageNumber + 1%>">&raquo;</a></li>
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
	<%@ include file="/WEB-INF/jspf/frontend/footer.jsp"%>