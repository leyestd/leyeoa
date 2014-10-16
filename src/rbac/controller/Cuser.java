package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import rbac.RbacInitialize;
import rbac.dao.D_Account;
import rbac.dao.D_Role;
import rbac.inputcheck.CheckAccount;
import rbac.javabean.Account;
import rbac.javabean.AccountPermissionRole;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import security.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import backend.javabean.Department;

/**
 * Servlet implementation class cuser
 */
public class Cuser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Account user = new Account();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setFullname(request.getParameter("fullname"));
		String default_roleid = request.getParameter("default_roleid");
		String enable = request.getParameter("enabled");
		String default_depid = request.getParameter("default_depid");

		if (default_roleid == null) {
			ArrayList<AccountPermissionRole> dbroles = D_Role.doSelectAll();
			request.setAttribute("dbroles", dbroles);
			ArrayList<Department> departments=D_Department.doSelectAllDepartment();
			request.setAttribute("departments", departments);

			String url = "/WEB-INF/rbac/cuser.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			
			if (enable == null)
				enable = "0";

			String checked = CheckAccount.doCheckNull(user, enable,
					default_roleid,default_depid);

			if (checked.equals("ok")) {
				enable = enable.trim();
				checked = CheckAccount.doMatch(user, enable, default_roleid,default_depid);
			}
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();

			if (checked.equals("ok")) {

				int enabled = Integer.valueOf(enable);

				String hashed = BCrypt.hashpw(user.getPassword(),
						BCrypt.gensalt());
				int count = D_Account.doCreate(user.getUsername(), hashed,
						user.getEmail(), user.getFullname(), enabled,
						Integer.valueOf(default_roleid),Integer.valueOf(default_depid));
				
				if (count != 0) {
					synchronized (getServletContext()) {
						HashMap<Integer, RbacAccount> rbac = RbacInitialize.doRbacUserInit();
						HashMap<Integer, RbacRole> roles = RbacInitialize.doRbacRoleInit();
						HashMap<Integer,ArrayList<String>> actions=RbacInitialize.doRbacActionInit();
						getServletContext().setAttribute("actions", actions);	
						getServletContext().setAttribute("rbac", rbac);
						getServletContext().setAttribute("roles", roles);
					}

					out.print("ok");
					return;
				} else {
					checked = "数据库操作失败";
				}
			}
			out.print(checked);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
