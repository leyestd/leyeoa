package backend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_SystemInfo;
import rbac.dao.D_Role_Permission;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;

/**
 * Servlet implementation class Lsysteminfo
 */
public class Lsysteminfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int rbacAccountCount=0;
		int rbacRoleCount=0;
		
		int dbAccountCount=0;
		int dbRoleCount=0;
		
		int workflowCount=0;
		int finishedWorkflowCount=0;
		int rejectedWorkflowCount=0;
		int unfinishedWorkflowCount=0;
		String rbacRolekey="";
		String rbacAccountkey="";
		/*
		 *   系统关键字  Departmentform （部门单据） 可查当前默认角色部门中的单据  对应操作 DepartmentFormDisplay 	显示部门单据
		 * 	  系统关键字    Approval 可审批的用户  对应操作 ApprovalWorkflow  审批流程 
		 * 	  系统管理员	administrator 默认存在    对应操作   Admin 	管理后台 
		 * 	  系统离职员工 SeparatedEmployees	 对应操作  不能登入
		 * 	 ApplyFor 	申请单据  给所有没有其它操作的角色  让关联查询生效
		 */
		
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		HashMap<Integer, RbacRole> rolesPermission = (HashMap<Integer, RbacRole>) D_Role_Permission.doSelectRolePermission();
		
		String problemInfo[]=new String[3];

		Set<Integer> roleskey=roles.keySet();
		for(Integer key : roleskey) {
			rbacRolekey+=key+",";
		}
		if(!rbacRolekey.equals("")){
			rbacRolekey= " WHERE id NOT IN ("+rbacRolekey.substring(0, rbacRolekey.length()-1)+")";
		}
		problemInfo[0]=D_SystemInfo.problemRoleAccount(rbacRolekey);  //未在系统中的角色-用户
		
		roleskey=rolesPermission.keySet();
		rbacRolekey="";
		for(Integer key : roleskey) {
			rbacRolekey+=key+",";
		}
		if(!rbacRolekey.equals("")){
			rbacRolekey= " WHERE id NOT IN ("+rbacRolekey.substring(0, rbacRolekey.length()-1)+")";
		}
		problemInfo[1]=D_SystemInfo.problemRolePermission(rbacRolekey);  //未在系统中的角色-操作
		
		
		Set<Integer> accountkey=rbac.keySet();
		for(Integer key : accountkey) {
			rbacAccountkey+=key+",";
		}
		if(!rbacAccountkey.equals("")){
			rbacAccountkey=" WHERE id NOT IN ("+rbacAccountkey.substring(0, rbacAccountkey.length()-1)+")";
		}
		problemInfo[2]=D_SystemInfo.problemRbacAccount(rbacAccountkey);
		
		
		rbacAccountCount=rbac.size();
		rbacRoleCount=roles.size();
		dbAccountCount=D_SystemInfo.doCountAccount();
		dbRoleCount=D_SystemInfo.doCountRole();
		workflowCount=D_SystemInfo.doCountWorkflow();
		finishedWorkflowCount=D_SystemInfo.doCountFinishedWorkflow();
		rejectedWorkflowCount=D_SystemInfo.doCountRejectedWorkflow();
		unfinishedWorkflowCount=D_SystemInfo.doCountUnfinishedWorkflow();
		
		Integer systemInfo[]=new Integer[8];
		systemInfo[0]=rbacAccountCount;
		systemInfo[1]=rbacRoleCount;
		systemInfo[2]=dbAccountCount;
		systemInfo[3]=dbRoleCount;
		systemInfo[4]=workflowCount;
		systemInfo[5]=finishedWorkflowCount;
		systemInfo[6]=rejectedWorkflowCount;
		systemInfo[7]=unfinishedWorkflowCount;
		

		
		String infoName[]=new String[11];
		infoName[0]="系统用户";
		infoName[1]="系统角色";
		infoName[2]="数据库用户";
		infoName[3]="数据库角色";
		infoName[4]="工作流总数";
		infoName[5]="完成的工作流";
		infoName[6]="拒绝的工作流";
		infoName[7]="未完成的工作流";
		infoName[8]="未在系统中的角色-用户";
		infoName[9]="未在系统中的角色-操作";
		infoName[10]="未在系统中的用户";
			
		
		request.setAttribute("problemInfo", problemInfo);
		request.setAttribute("systemInfo", systemInfo);
		request.setAttribute("infoName", infoName);
		
		String url = "/WEB-INF/backend/lsysteminfo.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
