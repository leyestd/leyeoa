package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import database.ConnectionPool;
import database.DBUtil;
import frontend.javabean.Workflow;

public class D_DetailMyApplication {
	public static Workflow doSelectDetail(int accountid,int flowid) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Workflow workflow=null;
		String query = "SELECT id,name,roleflow,accountflow,content,account_id,createtime,custom,status FROM workflow WHERE account_id=? and id=?";
		
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, accountid );
			ps.setInt(2, flowid);
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
					workflow.setStatus(rs.getInt("status"));
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
}
