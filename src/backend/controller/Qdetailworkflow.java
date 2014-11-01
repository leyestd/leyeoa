package backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import backend.dao.D_Department;
import backend.dao.D_Workflow;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Qdetailworkflow
 */
public class Qdetailworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String flowid=request.getParameter("flowid");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		Workflow workflow=null;
		
		if(flowid != null) {
			workflow=D_Workflow.doSelectQdetail(Integer.valueOf(flowid));	
		}
		
		ArrayList<String> finishInfo=null;
		if(workflow != null) {
			if (workflow.getAccountflow() != null) {
				String accountFlow[] = workflow.getAccountflow().split(",");
				finishInfo=new ArrayList<String>();
				String accountInfo=null;
				
				for(String account : accountFlow) {
					 //如果是委托的
					if(account.contains("-")) {
						String AccountIdInfo[]=account.split("-");
						//委托人和经办人信息
						int userId=Integer.valueOf(AccountIdInfo[0]);
						int delegateId=Integer.valueOf(AccountIdInfo[1]);
						
						accountInfo=D_Department.doSelect(rbac.get(userId).getDepartmentId()).getAlias()+
						"-"+roles.get(rbac.get(userId).getDefault_roleid()).getAlias()+
						"-"+rbac.get(userId).getFullname()
						+"-->"+
						D_Department.doSelect(rbac.get(delegateId).getDepartmentId()).getAlias()+
						"-"+roles.get(rbac.get(delegateId).getDefault_roleid()).getAlias()+
						"-"+rbac.get(delegateId).getFullname();
						
						finishInfo.add(accountInfo);
					}else {
						int userId=Integer.valueOf(account);
						accountInfo=D_Department.doSelect(rbac.get(userId).getDepartmentId()).getAlias()+
								"-"+roles.get(rbac.get(userId).getDefault_roleid()).getAlias()+
								"-"+rbac.get(userId).getFullname();
						finishInfo.add(accountInfo);
					}
				}
			}
		}else {
			response.sendRedirect("queryworkflow");
			return;
		}
		
		String accountInfo=D_Department.doSelect(rbac.get(workflow.getAccount_id()).getDepartmentId()).getAlias()+
				"-"+roles.get(rbac.get(workflow.getAccount_id()).getDefault_roleid()).getAlias()+
				"-"+rbac.get(workflow.getAccount_id()).getFullname();
		request.setAttribute("accountInfo", accountInfo);
		
		request.setAttribute("finishInfo", finishInfo);
		request.setAttribute("content", workflow.getContent());
		request.setAttribute("status", workflow.getStatus());
		
		String url = "/WEB-INF/backend/qdetailworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
