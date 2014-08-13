package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Defaultflow;
import backend.dao.D_Workform;
import tool.Pagination;

/**
 * Servlet implementation class Lworkflow
 */
public class Lworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flowid=request.getParameter("flowid");
		if(flowid!=null) {
			int count=D_Defaultflow.doDelete(Integer.valueOf(flowid));
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			if (count!=0) {			
				out.print("ok");
			} else {
				out.print("no");
			}
			return;
		}
		
		int pageNumber;
		if (request.getParameter("pageNumber") == null) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
		}

		Pagination page = new Pagination(pageNumber, 2, "defaultflow","");

		if (page.getTotal() != 0) {
			String[] columns = { "id", "name", "participate" ,"workform_id"};
			List<ArrayList<Object>> rows = page.getRows(columns);
			for(ArrayList<Object> row : rows) {
				if(row.get(3)!= null ) {
					String workformId=row.get(3).toString();
					String workformName=D_Workform.doSelect(workformId).getName();
					row.set(3,workformName);
				}else {
					row.set(3, "空");
				}
			}
			
			request.setAttribute("rows", rows);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("countPage", page.getCountPage());
		}

		String url = "/WEB-INF/backend/lworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
