package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Defaultflow;
import backend.dao.D_Workform;
import backend.inputcheck.CheckWorkflow;
import backend.javabean.Workform;


/**
 * Servlet implementation class cworkflow
 */
public class Cworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mWorkflow=request.getParameter("mWorkflow");
		String wName=request.getParameter("wName");
		String formId=request.getParameter("formid");
		
		String checked = CheckWorkflow.doCheckNull(mWorkflow, wName,formId);

		if (checked.equals("ok")) {
			checked = CheckWorkflow.doMatch(mWorkflow, wName,formId);
		}	
		
		if(checked.equals("ok")) {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8");  
			PrintWriter out = response.getWriter();
			mWorkflow=mWorkflow.trim();
			wName=wName.trim();
			int count = D_Defaultflow.doCreate(mWorkflow,wName,formId);
			if(count!=0) {			
				out.print("ok");
			}else {
				out.print("数据库操作失败");
			}
			
		}else if(checked.equals("")){
			
			ArrayList<Workform> formList=D_Workform.doSelectAll();
			request.setAttribute("formList", formList);
			String url = "/WEB-INF/backend/cworkflow.jsp";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}else {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			out.print(checked);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
