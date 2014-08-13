package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.inputcheck.CheckRole;
import rbac.dao.D_Role;

public class Crole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String advanced_roleid=request.getParameter("advanced_roleid");

		if (name == null || alias == null) {
			String url = "/WEB-INF/rbac/crole.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {

			String checked = CheckRole.doCheckNull(name, alias);
			
			if (checked.equals("ok")) {
				name = name.trim();
				alias = alias.trim();

				checked = CheckRole.doMatch(name, alias);
			}
			
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			
			if (checked.equals("ok")) {
				int count=0;
				if(advanced_roleid .equals("0")) {
					count = D_Role.doCreate(name, alias);
				}else {
					count = D_Role.doCreateHierarchy(name, alias,Integer.valueOf(advanced_roleid ));
				}
				if (count != 0) {
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
