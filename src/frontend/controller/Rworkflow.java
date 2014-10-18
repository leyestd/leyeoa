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
import frontend.javabean.Workflow;
import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import tool.Pagination;
import tool.CheckPermission;
/**
 * Servlet implementation class Rworkflow
 */
public class Rworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		int accountId = (Integer) request.getSession().getAttribute("id");
		HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>) getServletContext().getAttribute("roles");

		//查询所有待审的表单
		ArrayList<Workflow> workflows= D_Workflow.doSelectReady();

		System.out.println(workflows.size());
		StringBuilder readyFor=new StringBuilder();
		boolean check=false;
		
		for ( Workflow workflow : workflows) { 
			check=CheckPermission.doCheckPermisson(workflow, accountId, rbac,roles);
		}
		
		
		/*
		if (readyFor != null) {
			readyFor = " WHERE id IN (" + readyFor + ")";

			int pageNumber;
			if (request.getParameter("pageNumber") == null) {
				pageNumber = 1;
			} else {
				pageNumber = Integer
						.valueOf(request.getParameter("pageNumber"));
			}

			Pagination page = new Pagination(pageNumber, 2, "workflow",
					readyFor);

			if (page.getTotal() != 0) {
				String[] columns = { "id", "name", "account_id", "createtime" };
				List<ArrayList<Object>> rows = page.getRows(columns);
				request.setAttribute("rows", rows);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("countPage", page.getCountPage());
			}
		}

		String url = "/WEB-INF/frontend/rworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	*/
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
