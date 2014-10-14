package rbac.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacRole;

/**
 * Servlet implementation class ProleHierarchyTree
 */
public class ProleHierarchyTree extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<Integer,RbacRole> roles=(HashMap<Integer,RbacRole>)getServletContext().getAttribute("roles");
		Set<Integer> roleskey=roles.keySet();
		StringBuffer sb = new StringBuffer();
		 
		for(Integer key : roleskey) {
			if(D_Role_Hierarchy.doSelect(key)==0) {
				sb.append("<ul>");
				doRecursion(sb,key,roles);
				sb.append("</ul>");
			}
		}
	
		String hierarchytree=sb.toString();
		request.setAttribute("hierarchytree",hierarchytree);
		String url = "/WEB-INF/rbac/prolehierarchytree.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	
	}
	
	public static void doRecursion(StringBuffer sb,int id,HashMap<Integer,RbacRole> roles) {
		
		sb.append("<li data-roleid=\""+id+"\">"+roles.get(id).getAlias());
		ArrayList<Integer> basic_role=D_Role_Hierarchy.doSelectAdvanced(id);
		if(basic_role.size()!= 0) {
			sb.append("<ul>");
			for( int roleid : basic_role) {	
				doRecursion(sb,roleid,roles);
			}
			sb.append("</ul>");
		}
		sb.append("</li>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
