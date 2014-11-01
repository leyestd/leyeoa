package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import tool.CheckPermission;
import frontend.dao.D_Delegate;
import frontend.dao.D_Workflow;
import frontend.javabean.Delegate;
import frontend.javabean.Workflow;

/**
 * Servlet implementation class Modifyflow
 */
public class Modifyflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
		
		String flowid=request.getParameter("flowid");
		
		int accountid=(Integer)request.getSession().getAttribute("id");
		
		//委托我的所有用户
		ArrayList<Delegate> DelegateList = D_Delegate.doSelectDelegate(accountid);
		Workflow workflow=null;
		int count=0;
		
		//检测是不有权限
		boolean check=false;
		int delegateAccountId = accountid;
		
		if (flowid != null) {	
			workflow = D_Workflow.doSelectDetail(Integer.valueOf(flowid));
		}
		if(workflow!=null) {
			check=CheckPermission.doCheckPermisson(workflow,accountid, rbac);
			if(!check) {
				for (Delegate delegate : DelegateList) {
					check=CheckPermission.doCheckPermisson(workflow, delegate.getAccountId(), rbac);
					if(check) {
						delegateAccountId=delegate.getAccountId();
						break;
					}
				}
			}
		}

		String decision=request.getParameter("decision");
		String content=request.getParameter("editor1");
		
		
		if(decision!= null && content !=null && workflow != null && check) {
			if(decision.equals("agree") || decision.equals("reject")) {
								
				String accountflow;
				String roleflow;
				String roleflowArray[];
				int status=0;	//0 没变 1成功完成  2拒绝
				
				StringBuilder sb = new StringBuilder();
				
				if(workflow.getAccountflow()==null) {
					if(delegateAccountId != accountid) {
						accountflow=delegateAccountId+"-"+String.valueOf(accountid);
					}else {
						accountflow=String.valueOf(accountid);
					}
				}else{
					if(delegateAccountId != accountid) {
						accountflow=delegateAccountId+"-"+String.valueOf(accountid)+","+workflow.getAccountflow();
					}else {
						accountflow=String.valueOf(accountid)+","+workflow.getAccountflow();
					}
				}
					
				roleflowArray=workflow.getRoleflow().split(",");
				if(roleflowArray.length == 1) {
					status = 1;
					roleflow=String.valueOf(rbac.get(accountid).getDepartmentId());
					//roleflow=""; 保留最后一位角色，为单据存放部门
				} else {
					for(int i = 1; i < roleflowArray.length; i++){
						 sb.append(roleflowArray[i]+",");
					}
					roleflow=sb.toString();
					roleflow=roleflow.substring(0, roleflow.length()-1);
				}
				
				if(decision.equals("reject")) {
					status = 2;
					roleflow=String.valueOf(rbac.get(accountid).getDepartmentId());
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
