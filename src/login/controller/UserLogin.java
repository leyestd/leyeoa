package login.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rbac.javabean.RbacAccount;
import login.dao.D_LoginLimit;
import login.dao.D_Login;
import login.javabean.Limit;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String checkLogin = "重试";
		
		//取得ip
		String ipaddress;
		if (request.getHeader("x-forwarded-for") == null) {
			ipaddress = request.getRemoteAddr();
		} else {
			ipaddress = request.getHeader("x-forwarded-for");
		}
		
		if (username != null && password != null) {
			int id = D_Login.doCheckPassword(username, password);
			Limit limit = D_LoginLimit.doSelectLimit(username);

			if (id != 0) {
				//把锁定用户重新激活
				D_LoginLimit.doDeleteAll();
				limit = D_LoginLimit.doSelectLimit(username);
				
				//如果限制数据库中没有我
				if(limit == null) {
					session.setAttribute("id", id);
					response.sendRedirect("frontend/nworkform");
					getServletContext().log("IP: "+ipaddress+" 登入成功: "+username);
					return;
				}else if (limit.getNumber() < 5) {
					//如果小于5次
					session.setAttribute("id", id);
					response.sendRedirect("frontend/nworkform");
					D_LoginLimit.doDelete(username);  //清除我的密码填写错误次数
					getServletContext().log("IP: "+ipaddress+" 登入成功: "+username);
					return;
				}else {
					checkLogin = "已锁定";
				}
			} else {

				if (limit != null) {
					D_LoginLimit.doUpdate(username);
					checkLogin = "重试（" + (4 - limit.getNumber()) + "）";
					if (limit.getNumber() >= 4) {
						checkLogin = "已锁定";
					}
				} else {
					D_LoginLimit.doCreate(ipaddress, username, 1);
				}

			}
		} else {
			session.invalidate();
			checkLogin = "登入";
		}
		
		if(username != null) {
			getServletContext().log("IP: "+ipaddress+" 登入失败: "+username+" 状态: "+checkLogin);
		}
		
		request.setAttribute("checkLogin", checkLogin);
		String url = "/index.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
