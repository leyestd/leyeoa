package tool;

import java.util.HashMap;
import java.util.Set;

import frontend.javabean.Workflow;
import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;

public class CheckPermission {

	public CheckPermission() {

	}

	public static boolean doCheckPermisson(Workflow workflow, int accountId,HashMap<Integer, RbacAccount> rbac,HashMap<Integer, RbacRole> roles) {
		
		boolean check = false;
		// 获得此工作流中的流程内容4,6,7,8,54
		String roleflow = workflow.getRoleflow();
		// 取得流程的第一位
		int flow = Integer.valueOf(roleflow.split(",")[0]);
		// 如果表单为角色流
		if (workflow.getCustom().equals("f")) {
			try {
				// 如果我有此角色
				if (rbac.get(accountId).getRole().contains(roles.get(flow).getName())) {
					
					// 我和表单发起人是不是在同一个部门，如果不在，那我是不是在发起人部门的上层部门
					check = doCheckDepartmentalRelationship(defaultRoleId, accountid,roleName, rbac, roles);
					
					//表单发起人的部门中或上层部门中，是不否有人可以签，如果是，那我不可以签
					if (check == false) {
						check = doCheckSomeOneCan(defaultRoleId, accountid,roles);
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				check = false;
			}
		} else {
			//如果是用户自定义流，看用户ID是否等于流中的ID，如果相等，那我可以等
			if (accountId == flow) {
				check = true;
			}
		}
		if (check == true) {
			workflow.append(String.valueOf(rs.getInt("id")) + ",");
		}
		return true;
	}

	public static boolean doCheckDepartmentalRelationship() ｛
	
	｝
	
	public static boolean doCheckDepartmentalRelationship() ｛
	
	｝
	// 表单 6 4 7 8
	// 如果我有角色6

	// 如果我在此表单发起人的所有层次列表中出现，我可以签

	// 如果我没在表单发起人的层次列表中出现，看是否有人出现，如果有别人出现些角色，说明些单为别人签，如果没此单为别的部门审核 ，我可以签
	public static boolean doCheckPermisson(int advancedRoleId, int accountId,
			String roleName, HashMap<Integer, RbacAccount> rbac,
			HashMap<Integer, RbacRole> roles) {
		boolean check = true;

		HashMap<Integer, String> userList = roles.get(advancedRoleId).getUser();
		Set<Integer> userskey = userList.keySet();
		for (Integer key : userskey) {
			if (rbac.get(key).getRole().contains(roleName)) {
				return false;
			}
		}

		// 上层角色ID
		advancedRoleId = D_Role_Hierarchy.doSelect(advancedRoleId);
		if (advancedRoleId != 0) {
			check = doCheckPermisson(advancedRoleId, accountId, roleName, rbac,
					roles);
		}
		return check;
	}

}
