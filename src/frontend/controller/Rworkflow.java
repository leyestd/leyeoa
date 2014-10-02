package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.dao.D_Workflow;
import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import tool.Pagination;

/**
 * Servlet implementation class Rworkflow
 */
public class Rworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int accountid=(Integer)request.getSession().getAttribute("id");
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		
		boolean approval=false;
		int approvalRoleId=0;
		
		//如果用户有approval角色
		if(rbac.get(accountid).getRole().contains("Approval")) {
			approval=true;
		}else {
			Set<Integer> roleskey = roles.keySet();
			for (Integer key : roleskey) {
				//可审批角色ID存起来备用
				if(roles.get(key).getName().equals("Approval"))	{
					approvalRoleId=key;
					break;
				}
			}
			//可审批角色下的角色内的用户是否有当前用户
			if(approvalRoleId != 0) {
				ArrayList<Integer> basicRole=D_Role_Hierarchy.doSelectAdvanced(approvalRoleId);
				for(int id : basicRole) {
					if(roles.get(id).getUser().containsKey(accountid)) {
						approval=true;
						break;
					}
				}
			}
		}
		
		if(approval==true) {
			String readyFor=D_Workflow.doSelectReady(accountid, rbac, roles);
		
			System.out.println(readyFor);
		
			if(readyFor!=null) {
				readyFor=" WHERE id IN ("+readyFor+")";
			
				int pageNumber;
				if (request.getParameter("pageNumber") == null) {
					pageNumber = 1;
				} else {
					pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
				}

				Pagination page = new Pagination(pageNumber, 2, "workflow",readyFor);

				if (page.getTotal() != 0) {
					String[] columns = { "id", "name","account_id","createtime"};
					List<ArrayList<Object>> rows = page.getRows(columns);
					request.setAttribute("rows", rows);
					request.setAttribute("pageNumber", pageNumber);
					request.setAttribute("countPage", page.getCountPage());
				}
			}
		}else {
			request.setAttribute("approval", "denied");
		}
		
		String url = "/WEB-INF/frontend/rworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
