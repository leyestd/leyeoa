package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Role_Hierarchy;

/**
 * Servlet implementation class UroleHierarchy
 */
public class UroleHierarchy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roleid=request.getParameter("roleid");
		String advancedid=request.getParameter("advancedid");

	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/plain; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		int count=0;
		String status="no";
		
		if(advancedid !=null) {
			count=D_Role_Hierarchy.doUpdate(roleid, advancedid);
			if(count != 0) {
				status="ok";
			}
		}else {
			count=D_Role_Hierarchy.doDelete(roleid);
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
