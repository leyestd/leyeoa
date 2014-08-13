package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.inputcheck.CheckQuery;
import tool.Pagination;

/**
 * Servlet implementation class Fquery
 */
public class Fquery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String myMonth = request.getParameter("month");
		String myYear = request.getParameter("year");
		String myType = request.getParameter("mytype");

		int accountid = (Integer) request.getSession().getAttribute("id");

		String checked = CheckQuery.doCheckNull(myYear, myMonth);

		if (checked.equals("ok")) {
			myYear = myYear.trim();
			myMonth = myMonth.trim();
			checked = CheckQuery.doMatch(myYear, myMonth);
		}

		if (checked.equals("ok")) {

			int pageNumber;
			if (request.getParameter("pageNumber") == null) {
				pageNumber = 1;
			} else {
				pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			}

			String condition;

			if (myType.equals("myapplication")) {
				condition = " WHERE account_id=" + accountid
						+ " and YEAR(createtime)=" + myYear
						+ " and MONTH(createtime)=" + myMonth
						+ " ORDER BY id DESC";
				request.setAttribute("mytype", "detailmyapplication");
			} else {
				condition = " WHERE accountflow REGEXP '^" + accountid
						+ "(,[[:digit:]]+)*$' AND status != 0"
						+ " and YEAR(createtime)=" + myYear
						+ " and MONTH(createtime)=" + myMonth
						+ " ORDER BY id DESC";
				request.setAttribute("mytype", "detailfinishedflow");
			}

			Pagination page = new Pagination(pageNumber, 1, "workflow",condition);

			if (page.getTotal() != 0) {
				String[] columns = { "id", "name", "account_id", "createtime",
						"status" };
				List<ArrayList<Object>> rows = page.getRows(columns);
				request.setAttribute("rows", rows);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("countPage", page.getCountPage());
				request.setAttribute("myYear", myYear);
				request.setAttribute("myMonth", myMonth);
				request.setAttribute("pType", myType);
			}
		}

		request.setAttribute("checked",checked);
		String url = "/WEB-INF/frontend/fquery.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
