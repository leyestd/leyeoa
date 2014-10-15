package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Permission;
import rbac.inputcheck.CheckPermission;
import rbac.javabean.Permission;

public class Cpermission extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String pid = request.getParameter("pid");

		if (pid == null) {
			ArrayList<Permission> permissions = D_Permission
					.doSelectAllController();
			request.setAttribute("permissions", permissions);

			String url = "/WEB-INF/rbac/cpermission.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			String checked = CheckPermission.doCheckNull(name, alias, pid);

			if (checked.equals("ok")) {
				name = name.trim();
				alias = alias.trim();

				checked = CheckPermission.doMatch(name, alias, pid);
			}
			
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			
			if (checked.equals("ok")) {

				int count = D_Permission.doCreate(name, alias, pid);
				if (count != 0) {
					checked = "ok";
				} else {
					checked = "数据库操作失败";
				}
			}
			out.print(checked);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
