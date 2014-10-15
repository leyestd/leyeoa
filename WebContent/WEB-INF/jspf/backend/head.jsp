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
        <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../css/jumbotron.css" rel="stylesheet">

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
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"backend/lsysteminfo" %>">系统信息</a></li>
                                <li><a href="<%=basePath+"backend/lloginlimit" %>">锁定管理</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">查询 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"backend/queryuser" %>">用户查询</a></li>
                                <li><a href="<%=basePath+"backend/queryworkflow" %>">工作流查询</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">公告管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"backend/cannouncement" %>">添加公告</a></li>
                                <li><a href="<%=basePath+"backend/lannouncement" %>">列出公告</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">流程管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"backend/cworkflow" %>">添加流程</a></li>
                                <li><a href="<%=basePath+"backend/lworkflow" %>">列出流程</a></li>
                            </ul>
                        </li>
                         <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">表单管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                            	<li><a href="<%=basePath+"backend/cformtype" %>">添加类型</a></li>
                            	<li><a href="<%=basePath+"backend/lformtype" %>">列出类型</a></li>
                                <li><a href="<%=basePath+"backend/cworkform" %>">添加表单</a></li>
                                <li><a href="<%=basePath+"backend/lworkform" %>">列出表单</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">部门管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"backend/cdepartment" %>">添加部门</a></li>
                                <li><a href="<%=basePath+"backend/ldepartment" %>">列出部门</a></li>
                                <li><a href="<%=basePath+"backend/pdephierarchy" %>">部门层次</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">权限管理 <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=basePath+"rbac/cuser" %>">添加用户</a></li>
                                <li><a href="<%=basePath+"rbac/luser" %>">列出用户</a></li>
                                <li class="divider"></li>
                                <li><a href="<%=basePath+"rbac/crole" %>">添加角色</a></li>
                                <li><a href="<%=basePath+"rbac/lrole" %>">列出角色</a></li>
                                <li class="divider"></li>
                                <li><a href="<%=basePath+"rbac/cpermission" %>">添加操作</a></li>
                                <li><a href="<%=basePath+"rbac/lpermission" %>">列出操作</a></li>
                                <li><a href="<%=basePath+"rbac/prolehierarchytree" %>">列出层次</a></li>
                                <li class="divider"></li>
                                <li><a href="<%=basePath+"rbac/crelationship" %>">添加关系</a></li>
                                <li><a href="<%=basePath+"rbac/drelationship" %>">删除关系</a></li>
                            </ul>
                        </li>
                    </ul>

                    <form action="<%=basePath+"userLogin" %>" method="post" class="navbar-form navbar-right" role="form">  
                        <button type="submit" class="btn btn-success">登出</button>
                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </div>