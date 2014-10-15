package rbac.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.RbacInitialize;
import rbac.dao.D_Permission;
import rbac.inputcheck.CheckPermission;
import rbac.javabean.AccountPermissionRole;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;

/**
 * Servlet implementation class Upermission
 */
public class Upermission extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newName = request.getParameter("newName");
		String permissionName = request.getParameter("permissionName");
		String alias = request.getParameter("alias");
		String pid=request.getParameter("pid");

		String checked=CheckPermission.doCheckNull(newName, alias,pid);
		
		if(checked.equals("ok")) {
			newName=newName.trim();
			alias=alias.trim();
			
			checked=CheckPermission.doMatch(newName, alias,pid);
		}
		
		AccountPermissionRole permission=D_Permission.doSelect(permissionName);
		
		if (checked.equals("ok")) {
						
			int count = D_Permission.doUpdate(newName,alias,permissionName);
			if(count==0) {
				checked="数据库操作失败";
			}else {
				synchronized (getServletContext().getAttribute("rbac")) {
					HashMap<Integer, RbacAccount> rbac = RbacInitialize
							.doRbacUserInit();
					HashMap<Integer, RbacRole> roles = RbacInitialize
							.doRbacRoleInit();

					getServletContext().setAttribute("rbac", rbac);
					getServletContext().setAttribute("roles", roles);
				}
				response.sendRedirect("upermission?permissionName="+newName+"&checked=success");
				return;
			}
		}
		
		request.setAttribute("permission", permission);
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/rbac/upermission.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
