package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import backend.javabean.Department;
import rbac.inputcheck.CheckDepartment;

public class Cdepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String alias = request.getParameter("alias");
		String pid=request.getParameter("pid");

		if (name == null || alias == null) {
			String url = "/WEB-INF/backend/cdepartment.jsp";
			ArrayList<Department> departments=D_Department.doSelectAllDepartment();
			request.setAttribute("departments", departments);
			
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {

			String checked = CheckDepartment.doCheckNull(name, alias);
			
			if (checked.equals("ok")) {
				name = name.trim();
				alias = alias.trim();

				checked = CheckDepartment.doMatch(name, alias);
			}
			
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			
			if (checked.equals("ok")) {
				int GeneratedId=0;
				synchronized (getServletContext()) {
					GeneratedId = D_Department.doCreate(name, alias,Integer.valueOf(pid ));
				}
				if (GeneratedId != 0) {
					out.print("ok"+GeneratedId);
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
