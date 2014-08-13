package backend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Announcement;
import backend.inputcheck.CheckWorkform;


/**
 * Servlet implementation class Cannouncement
 */
public class Cannouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("announcementName");
		String content=request.getParameter("editor1");
		
		String checked=CheckWorkform.doCheckNull(content, name);
		
		if(checked.equals("ok")) {
			name=name.trim();
			content=content.trim();
			
			checked=CheckWorkform.doMatch(name);
		}
		
		if (checked.equals("ok")) {
						
			int count = D_Announcement.doCreate(content, name);
			if(count!=0) {
				response.sendRedirect("cannouncement?checked=success");
				return; 
			}else {
				checked="数据库操作失败";
			}
		}

		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/cannouncement.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
