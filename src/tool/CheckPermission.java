package tool;

import java.util.ArrayList;
import java.util.HashMap;
import backend.dao.D_Department;
import frontend.javabean.Workflow;
import rbac.dao.D_Account;
import rbac.javabean.Account;
import rbac.javabean.RbacAccount;

public class CheckPermission {

	public CheckPermission() {

	}

	public static boolean doCheckPermisson(Workflow workflow, int accountId,HashMap<Integer, RbacAccount> rbac) {
		
		boolean check = false;
		// 获得此工作流中的流程内容4,6,7,8,54
		String roleflow = workflow.getRoleflow();
		// 取得流程的第一位
		int flowRoleId = Integer.valueOf(roleflow.split(",")[0]);
		// 如果表单为角色流
		if (workflow.getCustom().equals("f")) {
			try {
				// 如果我有此角色
				if (rbac.get(accountId).getRole().contains(flowRoleId)) {
					
					//表单发起人的信息
					 Account workflowAccount=D_Account.doSelect(workflow.getAccount_id());
					// 我和表单发起人是不是在同一个部门，如果不在，那我是不是在发起人部门的上层部门
					check = doCheckDepartmentalRelationship(accountId,workflow,workflowAccount);
					
					//表单发起人的部门中或上层部门中，是不否有人可以签，如果是，那我不可以签
					if (check == false) {
						check = doCheckSomeOneCan(flowRoleId, workflow,workflowAccount,rbac);
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				check = false;
			}
		} else {
			//如果是用户自定义流，看用户ID是否等于流中的ID，如果相等，那我可以等
			if (accountId == flowRoleId) {
				check = true;
			}
		}

		return check;
	}

	public static boolean doCheckDepartmentalRelationship(int accountId,Workflow workflow,Account workflowAccount) {
		//取得用户和发起人的信息
		 Account user=D_Account.doSelect(accountId);
			 
		 //如果部门相同，我可以签
		 if(user.getDepid() == workflowAccount.getDepid()) {
			 return true;
		 }else {
			 return doCheckDepartemtnHierarchy(user.getDepid(),workflowAccount.getDepid()); 
		 }
	}
	
	//递归发起人的上层上层的部门ID和用户是否一样
	public static boolean doCheckDepartemtnHierarchy(int id,int pid) {
		pid = D_Department.doSelect(pid).getPid();
		if(id == pid) {
			return true;
		} else if (pid == 0 ) {
			return false;
		} else {
			return doCheckDepartemtnHierarchy(id,pid);
		}
	}
	
	public static boolean doCheckSomeOneCan(int flowRoleId, Workflow workflow,Account workflowAccount,HashMap<Integer, RbacAccount> rbac) {
		return doCheckDepartmentSomeonCan( flowRoleId,workflowAccount.getDepid(),rbac);
		
	}
	
	public static boolean doCheckDepartmentSomeonCan(int flowRoleId,int pid,HashMap<Integer, RbacAccount> rbac) {
		ArrayList<Integer> accounts=D_Department.doSelectDepartmentAccount(pid);
		for(int accountId : accounts) {
			if(rbac.get(accountId).getRole().contains(flowRoleId)) {
				return false;
			}
		}
		pid = D_Department.doSelect(pid).getPid();
		if(pid == 0) {
			return true;
		}else {
			return doCheckDepartmentSomeonCan(flowRoleId,pid,rbac);
		}
		
	}
}
