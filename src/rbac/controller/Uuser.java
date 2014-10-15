package rbac.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import rbac.RbacInitialize;
import rbac.dao.D_Account;
import rbac.dao.D_Role;
import rbac.inputcheck.CheckAccount;
import rbac.javabean.Account;
import rbac.javabean.AccountPermissionRole;
import rbac.javabean.Department;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import security.BCrypt;

/**
 * Servlet implementation class Uuser
 */
public class Uuser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<AccountPermissionRole> dbroles = D_Role.doSelectAll();
		request.setAttribute("dbroles", dbroles);
		
		Account user = new Account();
		user.setUsername(request.getParameter("newUsername"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setFullname(request.getParameter("fullname"));

		String enable = request.getParameter("enabled");
		String username = request.getParameter("username");

		if (enable == null)
			enable = "0";

		Account oldUser = D_Account.doSelect(username);
		
		if (user.getPassword() != null) {
			if (user.getPassword().trim().equals("")) {
				user.setPassword(oldUser.getPassword());
			}
		}

		String checked = CheckAccount.doCheckNull(user, enable);

		if (checked.equals("ok")) {
			enable = enable.trim();
			checked = CheckAccount.doMatch(user, enable);
		}

		if (checked.equals("ok")) {

			int enabled = Integer.valueOf(enable);
			String hashed ;
			
			if( !user.getPassword().equals(oldUser.getPassword())) {
				hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			}else {
				hashed =user.getPassword();
			}
			int count = D_Account.doUpdate(user.getUsername(), hashed,
					user.getEmail(), user.getFullname(), enabled, username);
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

				response.sendRedirect("uuser?username=" + user.getUsername()
						+ "&checked=success");
				return;
			}
		}
		
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		ArrayList<String> accountRoles=rbac.get(oldUser.getId()).getRole();
		ArrayList<AccountPermissionRole> ownedRole=new ArrayList<AccountPermissionRole>();
		AccountPermissionRole role=null;
		
		for(String roleName : accountRoles) {
			role=D_Role.doSelect(roleName);
			ownedRole.add( role);
		}
			
		request.setAttribute("ownedRole", ownedRole);
		request.setAttribute("checked", checked);
		request.setAttribute("user", oldUser);
		
		ArrayList<Department> departments=D_Department.doSelectAllDepartment();
		request.setAttribute("departments", departments);
		
		String url = "/WEB-INF/rbac/uuser.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
