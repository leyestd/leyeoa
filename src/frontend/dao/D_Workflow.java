package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


//查询所有待处理的工作流	
	public static ArrayList<Workflow> doSelectReady() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT id,name,roleflow,accountflow,account_id,createtime,custom FROM workflow WHERE status=0";

		Workflow workflow=null;
		ArrayList<Workflow> workflows=new ArrayList<Workflow>();
		
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				workflow=new Workflow();
				workflow.setId(rs.getInt("id"));
				workflow.setName(rs.getString("name"));
				workflow.setRoleflow(rs.getString("roleflow"));
				workflow.setAccountflow(rs.getString("accountflow"));
				//workflow.setContent(rs.getString("content"));
				workflow.setAccount_id(rs.getInt("account_id"));
				SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				rs.getDate("createtime");
				workflow.setCreatetime(f.format(rs.getDate("createtime")));
				workflow.setCustom(rs.getString("custom"));			
				workflows.add(workflow);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return workflows;
	}

	//工作流详细内容
	public static Workflow doSelectDetail( int flowid ) {
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
					workflow.setCustom(rs.getString("custom"));	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
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
