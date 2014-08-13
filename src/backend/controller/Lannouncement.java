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

import tool.Pagination;
import backend.dao.D_Announcement;

/**
 * Servlet implementation class Lannouncement
 */
public class Lannouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String announcementId=request.getParameter("announcementid");
		if(announcementId!=null) {
			int count=D_Announcement.doDelete(Integer.valueOf(announcementId));
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

		Pagination page = new Pagination(pageNumber, 2, "announcement"," ORDER BY id DESC");

		if (page.getTotal() != 0) {
			String[] columns = { "id", "name","createtime"};
			List<ArrayList<Object>> rows = page.getRows(columns);
			request.setAttribute("rows", rows);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("countPage", page.getCountPage());
		}

		String url = "/WEB-INF/backend/lannouncement.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
