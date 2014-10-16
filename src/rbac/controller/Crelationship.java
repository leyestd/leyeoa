package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.RbacInitialize;
import rbac.dao.D_Account;
import rbac.dao.D_Permission;
import rbac.dao.D_Role;
import rbac.dao.D_Role_Account;
import rbac.dao.D_Role_Permission;
import rbac.javabean.AccountPermissionRole;
import rbac.javabean.Permission;
import rbac.javabean.RbacRole;
import rbac.javabean.RbacAccount;

/**
 * Servlet implementation class crelationship
 */
public class Crelationship extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String roleid = request.getParameter("roleid");
		String userid = request.getParameter("userid");
		String permissionid = request.getParameter("permissionid");

		if (roleid == null) {

			ArrayList<AccountPermissionRole> users = D_Account.doSelectAll();

			ArrayList<Permission> DBpermissions=D_Permission.doSelectAllControllerActions();
			HashMap<Permission,ArrayList<Permission>> ControllerActions=new HashMap<Permission,ArrayList<Permission>>();
			ArrayList<Permission> pers=null;
			
			for(Permission per : DBpermissions) {
				
				if(per.getPid()==0) {
					//System.out.println(per.getAlias());
					pers=doSelectActions(DBpermissions,per.getId());
					if(pers.size()>0) {
						ControllerActions.put(per,pers);
					}
				}
			}
				System.out.println(DBpermissions.size());
			ArrayList<AccountPermissionRole> roles = D_Role.doSelectAll();
			request.setAttribute("users", users);
			request.setAttribute("ControllerActions", ControllerActions);
			request.setAttribute("roles", roles);

			String url = "/WEB-INF/rbac/crelationship.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);

		} else {
			
			int count = 0;
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			if (userid != null) {
				count = D_Role_Account.doCreate(roleid, userid);
			} else if (permissionid != null) {
				count = D_Role_Permission.doCreate(roleid, permissionid);
			}

			if (count != 0) {
				synchronized (getServletContext().getAttribute("rbac")) {
					HashMap<Integer, RbacAccount> rbac = RbacInitialize
							.doRbacUserInit();
					HashMap<Integer, RbacRole> roles = RbacInitialize
							.doRbacRoleInit();

					getServletContext().setAttribute("rbac", rbac);
					getServletContext().setAttribute("roles", roles);
				}
				out.print("ok");
			} else {
				out.print("no");
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	
	protected ArrayList<Permission> doSelectActions(ArrayList<Permission> DBpermissions,int id) {
		ArrayList<Permission> pers=new ArrayList<Permission>();
		for(Permission per : DBpermissions) {
			
			if(per.getPid() == id) {
				pers.add(per);	
			}  
		}
		return pers;	
	}

}
