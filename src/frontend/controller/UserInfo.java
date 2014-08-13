package frontend.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.dao.D_AccountInfo;
import frontend.inputcheck.CheckAccountInfo;
import rbac.RbacInitialize;
import rbac.javabean.Account;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import security.BCrypt;

/**
 * Servlet implementation class UserInfo
 */
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String fullname = request.getParameter("fullname");

		int accountid = (Integer) request.getSession().getAttribute("id");
		Account user = D_AccountInfo.doSelect(accountid);
		
		if (password != null) {
			if (password.trim().equals("")) {
				password = user.getPassword();
			}
		}

		String checked = CheckAccountInfo
				.doCheckNull(password, fullname, email);
		
		if (checked.equals("ok")) {
			password = password.trim();
			email = email.trim();
			fullname = fullname.trim();
			checked = CheckAccountInfo.doMatch(email, fullname);
		}

		if (checked.equals("ok")) {
			String hashed;

			if (!password.equals(user.getPassword())) {
				hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			} else {
				hashed = user.getPassword();
			}
			int count = D_AccountInfo.doUpdate(hashed, email, fullname, accountid);
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

				response.sendRedirect("userInfo?checked=success");
				return;
			}
		}

		request.setAttribute("checked", checked);
		request.setAttribute("userinfo", user);
		String url = "/WEB-INF/frontend/userinfo.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
