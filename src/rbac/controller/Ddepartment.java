package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rbac.dao.D_Department;

/**
 * Servlet implementation class Ddepartment
 */
public class Ddepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String depid = request.getParameter("depid");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		String status = "";

		int count = 0;
		int cid = 0;
		
		//判断此ID有没有下层部门
		synchronized (getServletContext()) {
			cid = D_Department.doSelectChild(Integer.valueOf(depid));
			if (cid == 0) {
				count = D_Department.doDelete(Integer.valueOf(depid));
			}
		}
		
		if (count != 0) {
			status = "ok";
		}

		out.print(status);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}