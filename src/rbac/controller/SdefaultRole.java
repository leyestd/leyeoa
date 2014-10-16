package rbac.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.RbacInitialize;
import rbac.dao.D_Role;
import rbac.dao.D_Role_Account;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;

/**
 * Servlet implementation class SetDefaultRole
 */
public class SdefaultRole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String roleid = request.getParameter("roleid");

		if (userid != null && roleid != null) {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			int count1 = 0;
			int count2 = 0;
			String status="0";

			count1 = D_Role_Account.doCreate(roleid, userid);
			
			HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
			synchronized (roles) {
				if(roles.get(Integer.valueOf(roleid)).getUser().containsKey(Integer.valueOf(userid))) {
					count2 = D_Role.doUpdateDefault(Integer.valueOf(userid),Integer.valueOf(roleid));
				}
			}
			if (count1 != 0 && count2 == 0) {
				status ="1";
			}else if (count1 == 0 && count2 != 0) {
				status= "2";
			}else if(count1 !=0 && count2 !=0) {
				status="3";
			}
			
			if(count1 != 0 || count2!=0) {
				synchronized (getServletContext()) {
					HashMap<Integer, RbacAccount> rbac = RbacInitialize.doRbacUserInit();
					roles = RbacInitialize.doRbacRoleInit();
					HashMap<Integer,ArrayList<String>> actions=RbacInitialize.doRbacActionInit();
					getServletContext().setAttribute("actions", actions);	
					getServletContext().setAttribute("rbac", rbac);
					getServletContext().setAttribute("roles", roles);
				}
				
			}
			
			out.print(status);
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

}
