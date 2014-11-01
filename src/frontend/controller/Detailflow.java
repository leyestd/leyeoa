package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import tool.CheckPermission;
import frontend.dao.D_Delegate;
import frontend.dao.D_Workflow;
import frontend.javabean.Delegate;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Detailflow
 */
public class Detailflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		
		String flowid = request.getParameter("flowid");
		int accountid = (Integer) request.getSession().getAttribute("id");
		
		//委托我的所有用户
		ArrayList<Delegate> DelegateList = D_Delegate.doSelectDelegate(accountid);
		//检测是不有权限
		Workflow workflow = null;
		boolean check=false;
		
		if (flowid != null) {	
			workflow = D_Workflow.doSelectDetail(Integer.valueOf(flowid));
		}
		
		if(workflow!=null) {
			check=CheckPermission.doCheckPermisson(workflow,accountid, rbac);
			if(!check) {
				for (Delegate delegate : DelegateList) {
					check=CheckPermission.doCheckPermisson(workflow, delegate.getAccountId(), rbac);
					if(check) break;
				}
			}
		}

		ArrayList<String> finishInfo=null;
		if (check) {
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
		} else {
			response.sendRedirect("rworkflow");
			return;
		}

		String accountInfo=D_Department.doSelect(rbac.get(workflow.getAccount_id()).getDepartmentId()).getAlias()+
				"-"+roles.get(rbac.get(workflow.getAccount_id()).getDefault_roleid()).getAlias()+
				"-"+rbac.get(workflow.getAccount_id()).getFullname();
		request.setAttribute("accountInfo", accountInfo);
		
		request.setAttribute("finishInfo", finishInfo);
		request.setAttribute("content", workflow.getContent());
		request.setAttribute("flowid", flowid);

		String url = "/WEB-INF/frontend/detailflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
