package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Account;

/**
 * Servlet implementation class SdefaultDepartment
 */
public class SdefaultDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String depid = request.getParameter("depid");
		String userid = request.getParameter("userid");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		int count = 0;
		String status = "no";

		if (depid != null && userid != null) {
			count = D_Account.doSetDefaultDepartment(userid,depid);
			if (count != 0) {
				status = "ok";
			}
		}

		out.print(status);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
