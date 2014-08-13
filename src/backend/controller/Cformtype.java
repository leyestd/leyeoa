package backend.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Formtype;
import backend.inputcheck.CheckFormtype;

/**
 * Servlet implementation class Cfromtype
 */
public class Cformtype extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name=request.getParameter("name");
		String checked=CheckFormtype.doCheckNull(name);
		
		if(checked.equals("ok")) {
			name=name.trim();
			checked=CheckFormtype.doMatch(name);
		}
		
		if (checked.equals("ok")) {
			
			int count = D_Formtype.doCreate(name);
			if(count!=0) {
				response.sendRedirect("cformtype?checked=success");
				return; 
			}else {
				checked="数据库操作失败";
			}
		}
		request.setAttribute("checked", checked);
		
		String url = "/WEB-INF/backend/cformtype.jsp";
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
