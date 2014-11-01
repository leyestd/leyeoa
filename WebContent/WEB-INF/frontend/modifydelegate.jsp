<%@ include file="/WEB-INF/jspf/frontend/head.jsp"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.HashMap,rbac.javabean.RbacRole,java.util.Set,frontend.javabean.Delegate" %>

	<div class="container">
		<h2 class="sub-header">委托用户</h2>
		<div class="row">
			<div class="col-md-4">

				<div class="divcss" id="roleuser">
					<ul class="list-unstyled">
					<%
						Delegate delegate=(Delegate)request.getAttribute("delegate");
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
				            	  <li class="clicked" data-userid="<%=userkey%>"><%=users.get(userkey)%></li>
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
			<div class="col-md-8">
				<button id="CreateDelegate" type="button" class="btn btn-danger">委托</button>
				<span class="glyphicon glyphicon-resize-horizontal"></span>
				<%	
					String enabled="想启用";
					String name="无";
					if(delegate!=null) {
						if(delegate.getEnabled()==1) {
							enabled="想禁用";
						}
						name=delegate.getName();
					}
				%>
				
				<button id="Account" type="button" class="btn btn-default"><%=name %></button>
				<span class="glyphicon glyphicon-resize-horizontal"></span>
				<button id="UpdateDelegate" type="button" class="btn btn-danger"><%=enabled %></button>
				<hr>
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
$("#CreateDelegate").attr("disabled", "disabled");
$("#roleuser > ul > li:odd").hide();
$("#roleuser > ul > li:even").on("click", function() {
	$("#roleuser > ul > li:odd").hide();
	$(this).next().show();

});
var userid,accountName;
var status=<%=(delegate!=null)?delegate.getEnabled():"0" %>;
var temp=0;

$("#roleuser li li").on("click", function() {
	$("#CreateDelegate").removeAttr("disabled");
	userid = $(this).data("userid");
	accountName = $(this).text();
	$("#roleuser li li").removeClass("libgcl");
	$(this).addClass("libgcl");
});

$("#CreateDelegate").on("click", function() {
		$.post("modifydelegate", {
			userid : userid,
			status : status
		}, function(data) {
		 	if(data=="ok") {
		 		$("#Account").text(accountName);
				$("#CreateDelegate").attr("disabled", "disabled");
				status=1;
				$("#UpdateDelegate").text("想禁用");
			 }else {
				 alert("修改委托失败");
		 	}
		});	
});

$("#UpdateDelegate").on("click", function() {
	if(status==1) {
		temp=0;
	}else {
		temp=1;
	}
	$.post("modifydelegate", {
		status : temp
	}, function(data) {
	 	if(data=="ok") {
	 		if(temp==0) {
	 			status=0;
	 			$("#UpdateDelegate").text("想启用");
	 		}else {
	 			status=1;
	 			$("#UpdateDelegate").text("想禁用");
	 		}
		 }else {
			 alert("修改委托失败");
	 	}
	});	
});
</script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
