package frontend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import frontend.dao.D_DetailMyApplication;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Detailmyapplication
 */
public class Detailmyapplication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		
		String flowid=request.getParameter("flowid");
		int accountid=(Integer)request.getSession().getAttribute("id");
		
		Workflow workflow=null;
		
		if(flowid != null) {
			workflow=D_DetailMyApplication.doSelectDetail(accountid, Integer.valueOf(flowid));	
		}
		
		LinkedHashMap<String,String> finishInfo=null;
		if(workflow != null) {
			if(workflow.getAccountflow()!=null) {
				String accountFlow[]=workflow.getAccountflow().split(",");
				finishInfo=new LinkedHashMap<String,String>();
				
				for(String account : accountFlow) {
					String defaultRole=roles.get(rbac.get(Integer.valueOf(account)).getDefault_roleid()).getAlias();
					String accountName=rbac.get(Integer.valueOf(account)).getFullname();
					finishInfo.put(account, defaultRole+"-"+accountName);
				}
			}	
		}else {
			response.sendRedirect("rmyapplication");
			return;
		}
		
		String accountDefaultRole=roles.get(rbac.get(Integer.valueOf(workflow.getAccount_id())).getDefault_roleid()).getAlias();
		String accountName=rbac.get(Integer.valueOf(workflow.getAccount_id())).getFullname();
		
		String accountInfo=accountDefaultRole+"-"+accountName;
		
		request.setAttribute("accountInfo", accountInfo);
		request.setAttribute("finishInfo", finishInfo);
		request.setAttribute("content", workflow.getContent());
		request.setAttribute("status", workflow.getStatus());
		System.out.println(workflow.getStatus());
		String url = "/WEB-INF/frontend/detailmyapplication.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
