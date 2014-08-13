package backend.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Formtype;
import backend.dao.D_Defaultflow;
import backend.dao.D_Workform;
import backend.inputcheck.CheckWorkform;
import backend.javabean.Formtype;
import backend.javabean.Defaultflow;
import backend.javabean.Workform;

/**
 * Servlet implementation class Uworkform
 */
public class Uworkform extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String workformId = request.getParameter("workformId");
		
		String name = request.getParameter("workformName");
		String content = request.getParameter("editor1");
		String formtypeId=request.getParameter("formtypeId");
		String checked = CheckWorkform.doCheckNull(content, name);

		if (checked.equals("ok")) {
			name = name.trim();
			content = content.trim();

			checked = CheckWorkform.doMatch(name);
		}

		if (checked.equals("ok")) {

			int count = D_Workform.doUpdate(content, name,Integer.valueOf(workformId),Integer.valueOf(formtypeId));
			if (count != 0) {
				response.sendRedirect("uworkform?checked=success&workformId="+workformId);
				return;
			} else {
				checked = "数据库操作失败";
			}
		}
		
		ArrayList<Formtype> formtype=D_Formtype.doSelectAll();
		Workform form = D_Workform.doSelect(workformId);
		//ArrayList<Defaultflow> workflows=D_Defaultflow.doSelectAll();
		ArrayList<Defaultflow> activeflows=D_Defaultflow.doSelectActive(workformId);
		

		request.setAttribute("workform", form);
		//request.setAttribute("workflows", workflows);
		request.setAttribute("activeflows", activeflows);
		request.setAttribute("formtype", formtype);
		
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/uworkform.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
