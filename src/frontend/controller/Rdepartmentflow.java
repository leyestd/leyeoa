package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import rbac.javabean.RbacAccount;
import tool.Pagination;

/**
 * Servlet implementation class Rdepartmentflow
 */
public class Rdepartmentflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int accountid = (Integer) request.getSession().getAttribute("id");
		HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
		int depId=rbac.get(accountid).getDepartmentId();
		// 可审批用户都可查询本部已审批单据

		int pageNumber;
		if (request.getParameter("pageNumber") == null) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
		}
 
		String condition = " WHERE roleflow REGEXP '^"+ depId + "(,[[:digit:]]+)*$' AND status !=0 AND custom = 'f' ORDER BY id DESC";
		Pagination page = new Pagination(pageNumber, 10, "workflow", condition);

		if (page.getTotal() != 0) {
			String[] columns = { "id", "name", "account_id", "createtime","status" };
			List<ArrayList<Object>> rows = page.getRows(columns);
			request.setAttribute("rows", rows);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("countPage", page.getCountPage());
		}

		String url = "/WEB-INF/frontend/rdepartmentflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
