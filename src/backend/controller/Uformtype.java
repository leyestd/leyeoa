package backend.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Formtype;
import backend.inputcheck.CheckFormtype;

/**
 * Servlet implementation class Uformtype
 */
public class Uformtype extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String formtypeId = request.getParameter("formtypeId");

		String checked = CheckFormtype.doCheckNull(name);

		if (checked.equals("ok")) {
			name=name.trim();
			checked = CheckFormtype.doMatch(name);
		}

		ArrayList<String> formtype=D_Formtype.doSelect(formtypeId);

		if (checked.equals("ok")) {

			int count = D_Formtype.doUpdate(name, Integer.valueOf(formtypeId));
			if (count == 0) {
				checked = "数据库操作失败";
			}else {
				response.sendRedirect("uformtype?formtypeId=" + formtypeId
						+ "&checked=success");
				return;
			}
		}

		request.setAttribute("formtype", formtype);
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/uformtype.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
