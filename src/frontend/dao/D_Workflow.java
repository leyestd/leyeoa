package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Set;

import rbac.dao.D_Role_Hierarchy;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import database.ConnectionPool;
import database.DBUtil;
import frontend.javabean.Workflow;

public class D_Workflow {
	public static int doCreate(String name, String roleflow, String content,
			int account_id, String custom) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		String query = "INSERT INTO workflow (name,roleflow,content,account_id,custom) VALUES (?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setString(2, roleflow);
			ps.setString(3, content);
			ps.setInt(4, Integer.valueOf(account_id));
			ps.setString(5, custom);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return count;
	}


	// 表单 6 4 7 8
	// 如果我有角色6
	
	//如果我在此表单发起人的所有层次列表中出现，我可以签
	public static boolean doCheckPermisson(int advancedRoleId,int accountId, HashMap<Integer, RbacRole> roles) {
		boolean check=false;
		
		HashMap<Integer, String> userList=roles.get(advancedRoleId).getUser();
		Set<Integer> userskey=userList.keySet();
		for(Integer key : userskey) {
			if( key == accountId ) {
				return true;
			}
		}	
		//上层角色ID
		advancedRoleId=D_Role_Hierarchy.doSelect(advancedRoleId);
		if( advancedRoleId != 0) {
			check=doCheckPermisson(advancedRoleId,accountId,roles);
		}
		return check;
	}
	
	//如果我没在表单发起人的层次列表中出现，看是否有人出现，如果有别人出现些角色，说明些单为别人签，如果没此单为别的部门审核 ，我可以签
	public static boolean doCheckPermisson(int advancedRoleId,int accountId,String roleName,HashMap<Integer, RbacAccount> rbac, HashMap<Integer, RbacRole> roles) {
		boolean check=true;
		
		HashMap<Integer, String> userList=roles.get(advancedRoleId).getUser();
		Set<Integer> userskey=userList.keySet();
		for(Integer key : userskey) {
			if(rbac.get(key).getRole().contains(roleName)){
				return false;
			}
		}
		
		//上层角色ID
		advancedRoleId=D_Role_Hierarchy.doSelect(advancedRoleId);
		if( advancedRoleId != 0) {
			check=doCheckPermisson(advancedRoleId,accountId,roleName,rbac,roles);
		}
		return check;
	}
	
	public static String doSelectReady(int accountid,
			HashMap<Integer, RbacAccount> rbac, HashMap<Integer, RbacRole> roles) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String readyFor = null;
		String query = "SELECT id,name,roleflow,account_id,createtime,custom FROM workflow WHERE status=0";
		StringBuffer workflow = new StringBuffer();

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				boolean check = false;
				String roleflow = rs.getString("roleflow");
				int flow = Integer.valueOf(roleflow.split(",")[0]);

				// 如果表单为角色流
				if (rs.getString("custom").equals("f")) {
					try {
						//如果我有此角色
						if (rbac.get(accountid).getRole().contains(roles.get(flow).getName())) {
							
							// 我的ID的默认角色
							int defaultRoleId=rbac.get(rs.getInt("account_id")).getDefault_roleid();
							//流的角色名
							String roleName=roles.get(flow).getName();
							//递归检查
							check=doCheckPermisson(defaultRoleId,accountid,roleName,rbac,roles);
							if(check==false) {
								check=doCheckPermisson(defaultRoleId,accountid,roles);
							}

						}
					} catch (NullPointerException e) {
						e.printStackTrace();
						check = false;
					}
				} else {
					if(accountid==flow) {
						check=true;
					}
				}

				if (check == true) {
					workflow.append(String.valueOf(rs.getInt("id")) + ",");
				}

			}

			if (workflow.length() != 0) {
				readyFor = workflow.toString().substring(0,
						workflow.length() - 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return readyFor;
	}

	public static Workflow doSelectDetail(int accountid,int flowid,
			HashMap<Integer, RbacAccount> rbac, HashMap<Integer, RbacRole> roles) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Workflow workflow=null;
		String query = "SELECT id,name,roleflow,accountflow,content,account_id,createtime,custom FROM workflow WHERE status=0 and id=?";
		

		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, flowid);
			rs = ps.executeQuery();
			if (rs.next()) {
				boolean check = false;
				String roleflow = rs.getString("roleflow");
				int flow = Integer.valueOf(roleflow.split(",")[0]);

				// 如果表单为角色流
				if (rs.getString("custom").equals("f")) {
					try {
						//如果我有此角色
						if (rbac.get(accountid).getRole().contains(roles.get(flow).getName())) {
							
							// 我的ID的默认角色
							int defaultRoleId=rbac.get(rs.getInt("account_id")).getDefault_roleid();
							//流的角色名
							String roleName=roles.get(flow).getName();
							//递归检查
							check=doCheckPermisson(defaultRoleId,accountid,roleName,rbac,roles);
							if(check==false) {
								check=doCheckPermisson(defaultRoleId,accountid,roles);
							}
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
						check = false;
					}
				} else {
					if(accountid==flow) {
						check=true;
					}
				}

				if (check == true) {
					workflow=new Workflow();
					workflow.setId(rs.getInt("id"));
					workflow.setName(rs.getString("name"));
					workflow.setRoleflow(rs.getString("roleflow"));
					workflow.setAccountflow(rs.getString("accountflow"));
					workflow.setContent(rs.getString("content"));
					workflow.setAccount_id(rs.getInt("account_id"));
					SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					rs.getDate("createtime");
					workflow.setCreatetime(f.format(rs.getDate("createtime")));
				}

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return workflow;
	}
	
	public static int doModifyWorkflow(String flowid, String content,String accountflow,String roleflow,int status) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		String query = "UPDATE workflow SET accountflow=?,content=?,roleflow=?,status=? WHERE id=?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, accountflow);
			ps.setString(2, content);
			ps.setString(3, roleflow);
			ps.setInt(4, status);
			ps.setInt(5, Integer.valueOf(flowid));
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return count;
	}
}
