package backend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Announcement;
import backend.inputcheck.CheckWorkform;
import backend.javabean.Announcement;


/**
 * Servlet implementation class Uannouncement
 */
public class Uannouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String announcementId = request.getParameter("announcementId");

		String name = request.getParameter("announcementName");
		String content = request.getParameter("editor1");
		String checked = CheckWorkform.doCheckNull(content, name);

		if (checked.equals("ok")) {
			name = name.trim();
			content = content.trim();

			checked = CheckWorkform.doMatch(name);
		}

		if (checked.equals("ok")) {

			int count = D_Announcement.doUpdate(content, name,Integer.valueOf(announcementId));
			if (count != 0) {
				response.sendRedirect("uannouncement?checked=success&announcementId="
						+ announcementId);
				return;
			} else {
				checked = "数据库操作失败";
			}
		}

		Announcement announcement =  D_Announcement.doSelect(announcementId);

		request.setAttribute("announcement", announcement);

		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/uannouncement.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
