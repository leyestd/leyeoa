package frontend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Announcement;
import backend.javabean.Announcement;

/**
 * Servlet implementation class Nannouncement
 */
public class Nannouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String announcementId = request.getParameter("announcementId");
		Announcement announcement =  D_Announcement.doSelect(announcementId);

		request.setAttribute("announcement", announcement);

		String url = "/WEB-INF/frontend/nannouncement.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
