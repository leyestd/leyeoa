package frontend.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import frontend.dao.D_Workflow;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Modifyflow
 */
public class Modifyflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
		
		String flowid=request.getParameter("flowid");
		
		int accountid=(Integer)request.getSession().getAttribute("id");
		Workflow workflow=null;
		int count=0;
		
		//检测是不有权限
		if(flowid != null) {
			workflow=D_Workflow.doSelectDetail(accountid, Integer.valueOf(flowid), rbac, roles);	
		}
		
		System.out.println(workflow.getName());
		
		String decision=request.getParameter("decision");
		String content=request.getParameter("editor1");
		
		
		if(decision!= null && content !=null && workflow != null) {
			if(decision.equals("agree") || decision.equals("reject")) {
								
				String accountflow;
				String roleflow;
				String roleflowArray[];
				int status=0;	//0 没变 1成功完成  2拒绝
				
				StringBuffer sb = new StringBuffer();
				
				if(workflow.getAccountflow()==null) {
					accountflow=String.valueOf(accountid);
				}else{
					accountflow=String.valueOf(accountid)+","+workflow.getAccountflow();
				}
					
				roleflowArray=workflow.getRoleflow().split(",");
				if(roleflowArray.length == 1) {
					status = 1;
					roleflow=roleflowArray[0];
					//roleflow=""; 保留最后一位角色，为单据存放部门
				}else {
					for(int i = 1; i < roleflowArray.length; i++){
						 sb.append(roleflowArray[i]+",");
					}
					roleflow=sb.toString();
					roleflow=roleflow.substring(0, roleflow.length()-1);
				}
				
				if(decision.equals("reject")) {
					status = 2;
				}
				
				count=D_Workflow.doModifyWorkflow(flowid, content, accountflow, roleflow, status);	
			}
			if(count==1) {
				response.sendRedirect("rworkflow?status=success");
			}
			return;
		}

		response.sendRedirect("rworkflow?status=failure");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
