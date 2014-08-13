package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Account;

/**
 * Servlet implementation class Duser
 */
public class Duser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("uname");
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/plain; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		String status="";
		
		if(username!=null) {
			int count=D_Account.doUpdate(username);
			if(count!=0) {
				status="ok";
			}else {
				status="no";
			}
		}
		out.print(status);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
