package backend.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import backend.javabean.Department;
import rbac.inputcheck.CheckRole;
/**
 * Servlet implementation class Udepartment
 */
public class Udepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String newName = request.getParameter("newName");
		String DepartmentId= request.getParameter("departmentId");
		String alias = request.getParameter("alias");

		String checked = CheckRole.doCheckNull(newName, alias);

		if (checked.equals("ok")) {
			newName = newName.trim();
			alias = alias.trim();

			checked = CheckRole.doMatch(newName, alias);
		}

		Department dep =D_Department.doSelect(Integer.valueOf(DepartmentId));
		Department pdep=D_Department.doSelect(dep.getPid());

		if (checked.equals("ok")) {

			int count = D_Department.doUpdate(newName, alias,DepartmentId);
			if (count == 0) {
				checked = "数据库操作失败";
			} else {
				response.sendRedirect("udepartment?departmentId=" + DepartmentId
						+ "&checked=success");
				return;
			}
		}
		
		if (pdep != null) {
			request.setAttribute("pdep",pdep);
		}
		
		ArrayList<Department> departments=D_Department.doSelectAllDepartment();
		request.setAttribute("departments", departments);
		request.setAttribute("dep", dep);
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/udepartment.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
