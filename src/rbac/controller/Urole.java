package rbac.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.RbacInitialize;
import rbac.dao.D_Role;
import rbac.dao.D_Role_Hierarchy;
import rbac.inputcheck.CheckRole;
import rbac.javabean.AccountPermissionRole;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;

/**
 * Servlet implementation class Urole
 */
public class Urole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String newName = request.getParameter("newName");
		String rolename = request.getParameter("roleName");
		String alias = request.getParameter("alias");

		String checked = CheckRole.doCheckNull(newName, alias);

		if (checked.equals("ok")) {
			newName = newName.trim();
			alias = alias.trim();

			checked = CheckRole.doMatch(newName, alias);
		}

		AccountPermissionRole role =D_Role.doSelect(rolename);
		int advanced_role=D_Role_Hierarchy.doSelect(role.getId());

		if (checked.equals("ok")) {

			int count = D_Role.doUpdate(newName, alias, rolename);
			if (count == 0) {
				checked = "数据库操作失败";
			} else {
				synchronized (getServletContext().getAttribute("rbac")) {
					HashMap<Integer, RbacAccount> rbac = RbacInitialize
							.doRbacUserInit();
					HashMap<Integer, RbacRole> roles = RbacInitialize
							.doRbacRoleInit();

					getServletContext().setAttribute("rbac", rbac);
					getServletContext().setAttribute("roles", roles);
				}

				response.sendRedirect("urole?roleName=" + newName
						+ "&checked=success");
				return;
			}
		}
		
		if (advanced_role!=0) {
			request.setAttribute("advancedrole",advanced_role);
		}
		request.setAttribute("role", role);
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/rbac/urole.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
