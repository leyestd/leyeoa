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

import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import tool.Pagination;

/**
 * Servlet implementation class Rdepartmentflow
 */
public class Rdepartmentflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int accountid = (Integer) request.getSession().getAttribute("id");
		HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>) getServletContext().getAttribute("roles");

		boolean approval = false;
		int approvalRoleId = 0;

		// 可审批用户都可查询本部已审批单据
		// 如果我有approval角色

		if (rbac.get(accountid).getRole().contains("Approval")) {
			approval = true;
		} else {
			Set<Integer> roleskey = roles.keySet();
			for (Integer key : roleskey) {
				if (roles.get(key).getName().equals("Approval")) {
					approvalRoleId = key;
					break;
				}
			}
			if (approvalRoleId != 0) {
				ArrayList<Integer> basicRole = D_Role_Hierarchy
						.doSelectAdvanced(approvalRoleId);
				for (int id : basicRole) {
					if (roles.get(id).getUser().containsKey(accountid)) {
						approval = true;
						break;
					}
				}
			}
		}

		if (approval == false) {
			if (rbac.get(accountid).getRole().contains("DepartmentForm")) {
				approval = true;
			}
		}

		if (approval == true) {
			int default_roleid = rbac.get(accountid).getDefault_roleid();

			int pageNumber;
			if (request.getParameter("pageNumber") == null) {
				pageNumber = 1;
			} else {
				pageNumber = Integer
						.valueOf(request.getParameter("pageNumber"));
			}

			String condition = " WHERE roleflow REGEXP '^"
					+ default_roleid
					+ "(,[[:digit:]]+)*$' AND status != 0 AND custom = 'f' ORDER BY id DESC";
			Pagination page = new Pagination(pageNumber, 10, "workflow",
					condition);

			if (page.getTotal() != 0) {
				String[] columns = { "id", "name", "account_id", "createtime",
						"status" };
				List<ArrayList<Object>> rows = page.getRows(columns);
				request.setAttribute("rows", rows);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("countPage", page.getCountPage());
			}
		} else {
			request.setAttribute("approval", "denied");
		}

		String url = "/WEB-INF/frontend/rdepartmentflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
