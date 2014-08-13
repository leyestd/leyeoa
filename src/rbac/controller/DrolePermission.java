package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.dao.D_Role_Permission;


/**
 * Servlet implementation class DroleUserpermission
 */
public class DrolePermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tableName=request.getParameter("tableName");
		String uid=request.getParameter("uname");
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("text/plain; charset=utf-8");  
		PrintWriter out = response.getWriter();
		String status="";
		if(tableName != null && uid != null) {
			if(!tableName.equals("role") && !tableName.equals("permission")) {
				status="";
			}else {
				int count=0;
				int relationshipCount=0;
				if(tableName.equals("role")) {
					relationshipCount=D_Role_Permission.doSelectHasRelationship(Integer.valueOf(uid));	
					if(relationshipCount == 0) {
						count=D_Role_Permission.doDeleteRoleOrPermission(Integer.valueOf(uid), tableName);
					}
				}else if(tableName.equals("permission")){
					count=D_Role_Permission.doDeleteRoleOrPermission(Integer.valueOf(uid), tableName);
				}
				
				
				if(count!=0) {
					status="ok";
				}else {
					status="";
				}
			}
		}else {
			status="";
		}
		out.print(status);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
