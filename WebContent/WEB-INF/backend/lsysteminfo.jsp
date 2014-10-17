<%@ include file="/WEB-INF/jspf/backend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,java.util.List"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">

		<div class="col-md-12">

			<h2 class="sub-header">系统信息</h2>
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr class="info">
							<th>序号</th>
							<th>名称</th>
							<th>内容</th>
						</tr>
					</thead>
					<tbody>
					<%Integer systemInfo[]=(Integer[])request.getAttribute("systemInfo"); %>
					<%String infoName[]=(String[])request.getAttribute("infoName"); %>	
					<%String problemInfo[]=(String[])request.getAttribute("problemInfo");%>
					<% if( systemInfo.length==0 )  {%>
						<tr>
							<td class="text-center" colspan="3">没有相关记录</td>
						</tr>
					<%} else {
							for( int i=0;i<systemInfo.length;i++) {
					%>
						<tr class="active">
							<td><%=i %></td>
							<td><%=infoName[i] %></td>
							<td><%=systemInfo[i] %></td>
						</tr>				
						<%	}
						} %>
						<tr class="danger">
							<td>8</td>
							<td><%=infoName[8] %></td>
							<td>ID&nbsp;[<%=problemInfo[0] %>]</td>
						</tr>
						<tr class="danger">
							<td>9</td>
							<td><%=infoName[9] %></td>
							<td>ID&nbsp;[<%=problemInfo[1] %>]</td>
						</tr>
						
						<tr class="warning">
							<td>10</td>
							<td>角色(DepartmentForm) </td>
							<td>显示当前默认角色中的单据</td>
						</tr>
						<tr class="warning">
							<td>11</td>
							<td>操作(DepartmentFormDisplay)</td>
							<td>显示部门单据</td>
						</tr>
						<tr class="warning">
							<td>12</td>
							<td>角色 (Approval)</td>
							<td>审批的用户</td>
						</tr>
						<tr class="warning">
							<td>13</td>
							<td>操作(ApprovalWorkflow)</td>
							<td>审批单据</td>
						</tr>
						<tr class="warning">
							<td>14</td>
							<td>角色(SeparatedEmployees)</td>
							<td>系统离职员工</td>
						</tr>
						<tr class="warning">
							<td>15</td>
							<td>操作(CannotLogIn)</td>
							<td>不能登入</td>
						</tr>						
						<tr class="warning">
							<td>16</td>
							<td>操作(ApplyFor)</td>
							<td>申请单据,使rbac关联查询生效</td>
						</tr>
					</tbody>
				</table>
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

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>