package backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.inputcheck.CheckQuery;
import tool.Pagination;

/**
 * Servlet implementation class Queryuser
 */
public class Queryuser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String queryname = request.getParameter("queryname");
		String queryType = request.getParameter("querytype");

		String checked = CheckQuery.doCheckNull(queryType);

		if (checked.equals("ok")) {
			checked = CheckQuery.doCheckFullname(queryname);
		}

		if (checked.equals("ok")) {
			if (queryType.equals("username")) {
				checked = CheckQuery.doMatchUsername(queryname);	
			} else if (queryType.equals("fullname")) {
				checked = CheckQuery.doMatchFullname(queryname);
			} else if(queryType.equals("userid")) {
				checked= CheckQuery.doMatchUserid(queryname);
			}
		}

		if (checked.equals("ok")) {

			int pageNumber;
			if (request.getParameter("pageNumber") == null) {
				pageNumber = 1;
			} else {
				pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			}

			String condition;

			if (queryType.equals("username")) {
				condition = " WHERE username LIKE '%" + queryname + "%'"
						+ " ORDER BY id DESC";
			} else if (queryType.equals("fullname")) {
				condition = " WHERE fullname LIKE '%" + queryname + "%'"
						+ " ORDER BY id DESC";		
			} else  {
				condition = " WHERE id LIKE '%" + queryname + "%'"
						+ " ORDER BY id DESC";	
			}
			
			Pagination page = new Pagination(pageNumber, 2, "account",condition);

			if (page.getTotal() != 0) {
				String[] columns = { "id", "username", "fullname", "email","enabled" ,"default_roleid"};
				List<ArrayList<Object>> rows = page.getRows(columns);
				request.setAttribute("rows", rows);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("countPage", page.getCountPage());
				request.setAttribute("queryname", queryname);
				request.setAttribute("querytype", queryType);
			}
		}

		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/queryuser.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
