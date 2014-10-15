package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;

/**
 * Servlet implementation class UroleHierarchy
 */
public class UdepHierarchy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String depid=request.getParameter("depid");
		String pdepid=request.getParameter("pdepid");

	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/plain; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		int count=0;
		String status="no";
		
		if(pdepid !=null) {
			synchronized (getServletContext()) {
				count=D_Department.doUpdateHierarchy(depid, pdepid);
			}
			if(count != 0) {
				status="ok";
			}
		}else {
			synchronized (getServletContext()) {
				count=D_Department.doDeleteHierarchy(depid);
			}
			if(count != 0) {
				status="ok";
			}
		}

		out.print(status);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}