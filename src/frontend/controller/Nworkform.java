package frontend.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.dao.D_Form_Type;
import frontend.javabean.Typeform;

/**
 * Servlet implementation class Nworkflow
 */
public class Nworkform extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<Integer,Typeform> typeforms=D_Form_Type.doSelectAll();
		
		
		request.setAttribute("typeforms", typeforms);
		
		
		String url = "/WEB-INF/frontend/nworkform.jsp";
		RequestDispatcher dispatcher = getServletContext()
			.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
