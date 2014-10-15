package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rbac.dao.D_Permission;

/**
 * Servlet implementation class Dpermission
 */
public class Dpermission extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String permissionId = request.getParameter("permissionId");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		String status = "";

		int count = 0;
		int cid = 0;

		// 判断此ID有没有下层操作

		cid = D_Permission.doSelectChild(Integer.valueOf(permissionId));
		if (cid == 0) {
			count = D_Permission.doDelete(Integer.valueOf(permissionId));
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