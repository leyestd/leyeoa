package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.RbacInitialize;
import rbac.dao.D_Role_Account;
import rbac.dao.D_Role_Permission;
import rbac.javabean.RbacRole;
import rbac.javabean.RbacAccount;


public class Drelationship extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String roleid = request.getParameter("roleid");
		String proleid = request.getParameter("proleid");
		String userid = request.getParameter("userid");
		String permissionid = request.getParameter("permissionid");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		HashMap<Integer, RbacRole> rolesPermission = (HashMap<Integer, RbacRole>) D_Role_Permission.doSelectRolePermission();
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		if (roleid == null && proleid==null) {
			request.setAttribute("rolesPermission", rolesPermission);

			String url = "/WEB-INF/rbac/drelationship.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			int count = 0;
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			
			if (roleid != null) {
				//默认相关不能删除,删除用户把些用户默认角色改为其它
				synchronized (roles) {
					int default_roleid = rbac.get(Integer.valueOf(userid)).getDefault_roleid();
					if(default_roleid != Integer.valueOf(roleid)) {
						count = D_Role_Account.doDelete(roleid,userid);
					}
				}
			}else if (proleid != null) {
					count = D_Role_Permission.doDelete(proleid, permissionid);
			}
			
			if (count != 0) {
				synchronized (getServletContext().getAttribute("rbac")) {
					rbac = RbacInitialize.doRbacUserInit();
					roles = RbacInitialize.doRbacRoleInit();

					getServletContext().setAttribute("rbac", rbac);
					getServletContext().setAttribute("roles", roles);
				}
		        
				out.print("ok");
			} else {
				out.print("no");
			}
			
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
