package frontend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import tool.CheckPermission;
import frontend.dao.D_Delegate;
import frontend.dao.D_Workflow;
import frontend.javabean.Delegate;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Countworkflow
 * 统计待签工作表单
 */
public class Countworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int count = 0;
		if (request.getParameter("id") != null) {
			int accountId =	Integer.valueOf( request.getParameter("id"));
			//System.out.println(accountId);
			ArrayList<Delegate> DelegateList = D_Delegate.doSelectDelegate(accountId);
			ArrayList<Workflow> workflows = D_Workflow.doSelectReady();
			HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
			boolean check = false;

			for (Workflow workflow : workflows) {
				check = CheckPermission.doCheckPermisson(workflow, accountId,
						rbac);
				if (check) {
					count++;
				} else if (DelegateList != null) {
					for (Delegate delegate : DelegateList) {
						check = CheckPermission.doCheckPermisson(workflow,
								delegate.getAccountId(), rbac);
						if (check) {
							count++;
							break;
						}
					}
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(count);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
