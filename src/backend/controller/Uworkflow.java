package backend.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.dao.D_Defaultflow;
import backend.javabean.Defaultflow;

/**
 * Servlet implementation class Dactiveform
 */
public class Uworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flowId=request.getParameter("flowId");
		String updateFlowId =request.getParameter("updateflowId");
		String mWorkflow=request.getParameter("mWorkflow");
		String wName=request.getParameter("wName");
		String mflowid=request.getParameter("mflowid");
		
		if(flowId != null) {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8"); 
			PrintWriter out = response.getWriter();
			int count=D_Defaultflow.doDelete(Integer.valueOf(flowId));
			if(count != 0) {
				out.print("ok");
			}else{
				out.print("数据库操作失败");
			}
		}else if(mWorkflow != null && wName != null && mflowid != null) {
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("text/plain; charset=utf-8");
			PrintWriter out = response.getWriter();
			int count=D_Defaultflow.doUpdate(wName,mWorkflow,mflowid);
			if(count != 0) {
				out.print("ok");
			}else{
				out.print("数据库操作失败");
			}	
		}else if(updateFlowId != null ){
			Defaultflow flow=D_Defaultflow.doSelect(updateFlowId);
			request.setAttribute("flow", flow);
			String url = "/WEB-INF/backend/uworkflow.jsp";
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		
 	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
