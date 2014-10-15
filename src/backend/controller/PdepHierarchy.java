package backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Department;
import rbac.javabean.Department;

/**
 * Servlet implementation class PdepHierarchy
 */
public class PdepHierarchy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		ArrayList<Department> departments=D_Department.doSelectAllDepartment();
		for(Department dep : departments) {
			if(dep.getPid()==0) {
				sb.append("<ul>");
				doRecursion(sb,dep.getPid(),departments,dep);
				sb.append("</ul>");
			}	
		}
		String hierarchytree=sb.toString();
		request.setAttribute("hierarchytree",hierarchytree);
		String url = "/WEB-INF/backend/pdephierarchy.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
	
	public static void doRecursion(StringBuilder sb,int id,ArrayList<Department> departments,Department dep) {
		
		sb.append("<li data-depid=\""+id+"\">"+dep.getAlias());
		for(Department d : departments)
		if(d.getPid()==dep.getId()) {
			sb.append("<ul>");
			doRecursion(sb,d.getPid(),departments,d);	
			sb.append("</ul>");
		}
		sb.append("</li>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
