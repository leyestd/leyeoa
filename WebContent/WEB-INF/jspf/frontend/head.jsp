<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>LeyeOA</title>

        <!-- Bootstrap -->
        <link href="<%=basePath %>bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=basePath %>css/jumbotron.css" rel="stylesheet">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<%=basePath %>">LeyeOA</a>
                </div>

                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="<%=basePath+"frontend/rworkflow" %>">审批</a></li>
                        <li><a href="<%=basePath+"frontend/nworkform" %>">申请</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">内容<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"frontend/fquery" %>">查询</a></li>
                                <li><a href="<%=basePath+"frontend/rdepartmentflow" %>">本部门</a></li>
                                <li><a href="<%=basePath+"frontend/rfinishedflow" %>">已审批</a></li>
                                <li><a href="<%=basePath+"frontend/rmyapplication" %>">已申请</a></li>
                                <li class="divider"></li>
                                <li><a href="<%=basePath+"frontend/rannouncement" %>">公告</a></li>
                                <li><a href="<%=basePath+"backend/lsysteminfo" %>">管理</a></li>
                            </ul>
                        </li>
                    </ul>

                      
                <%
                     String checkLogin="登入";
                     if(request.getAttribute("checkLogin")!=null) {
                        checkLogin=(String)request.getAttribute("checkLogin");
                     }
                     if(session.getAttribute("id")!=null) {
                        checkLogin="登出";
                %>   
                      <ul class="nav navbar-nav navbar-right">
        				<li><a href="<%=basePath+"frontend/userInfo" %>">个人信息</a></li>
        				<li><a href="<%=basePath+"userLogin" %>">登出</a></li>
        			  </ul>
                 <%       
                     } else {
                 %>
                        <form action="<%=basePath+"userLogin" %>" method="post" class="navbar-form navbar-right" role="form">
                        	<div class="form-group">
                            	<input name="username" type="text" placeholder="Username" class="form-control">
                        	</div>
                        	<div class="form-group">
                            	<input name="password" type="password" placeholder="Password" class="form-control">
                        	</div>
                        	<button type="submit" class="btn btn-success"><%=checkLogin %></button>
                        </form>
                 <%
                      }
                 %>             
                </div><!--/.navbar-collapse -->
            </div>
        </div>
        