package rbac.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Permission;
import rbac.inputcheck.CheckPermission;

public class Cpermission extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");

		String checked=CheckPermission.doCheckNull(name, alias);
		
		if(checked.equals("ok")) {
			name=name.trim();
			alias=alias.trim();
			
			checked=CheckPermission.doMatch(name, alias);
		}
		
		if (checked.equals("ok")) {
						
			int count = D_Permission.doCreate(name,alias);
			if(count!=0) {
				response.sendRedirect("cpermission?checked=success");
				return; 
			}else {
				checked="数据库操作失败";
			}
		}
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/rbac/cpermission.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
