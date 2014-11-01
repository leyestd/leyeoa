package frontend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import frontend.dao.D_Delegate;
import frontend.javabean.Delegate;

/**
 * Servlet implementation class Modifydelegate
 */
public class Modifydelegate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int accountid = (Integer) request.getSession().getAttribute("id");
		String delegateId=request.getParameter("userid");
		String status=request.getParameter("status");
		int count=0;
		
		if(status != null) {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			if(delegateId!=null) {
				if(D_Delegate.doSelect(accountid) != null ) {
					D_Delegate.doDelete(accountid);
				}
				count=D_Delegate.doCreate(accountid, Integer.valueOf(delegateId));
				if(count != 0) {
					out.print("ok");
				}else {
					out.print("error");
				}
			}else {
				count=D_Delegate.doUpdate(Integer.valueOf(status),accountid);
				if(count != 0) {
					out.print("ok");
				}else {
					out.print("error");
				}
			}
			return;
		}
		
		Delegate delegate=D_Delegate.doSelect(accountid);
		if(delegate!=null) {
			request.setAttribute("delegate", delegate);
		}
		String url = "/WEB-INF/frontend/modifydelegate.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
