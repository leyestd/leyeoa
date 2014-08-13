package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.ConnectionPool;
import database.DBUtil;

public class D_SystemInfo {

	public static int doCountAccount() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int dbAccountCount = 0;

		String query = "SELECT count(*) AS c FROM account";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				dbAccountCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return dbAccountCount;
	}

	public static int doCountRole() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int dbRoleCount = 0;

		String query = "SELECT count(*) AS c FROM role";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				dbRoleCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return dbRoleCount;
	}

	public static int doCountWorkflow() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int workflowCount = 0;

		String query = "SELECT count(*) AS c FROM workflow";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				workflowCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return workflowCount;
	}

	public static int doCountFinishedWorkflow() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int finishedWorkflowCount = 0;

		String query = "SELECT count(*) AS c FROM workflow WHERE STATUS=1";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				finishedWorkflowCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return finishedWorkflowCount;
	}
	
	public static int doCountRejectedWorkflow() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rejectedWorkflowCount = 0;

		String query = "SELECT count(*) AS c FROM workflow WHERE STATUS=2";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				rejectedWorkflowCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return rejectedWorkflowCount;
	}
	
	public static int doCountUnfinishedWorkflow() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int unfinishedWorkflowCount = 0;

		String query = "SELECT count(*) AS c FROM workflow WHERE STATUS=0";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next()) {
				unfinishedWorkflowCount = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		return unfinishedWorkflowCount;
	}
	
	public static String problemRoleAccount(String condition) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String problemInfo ="";
		
		String query = "SELECT id FROM role"+condition;
		System.out.println(query);
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				problemInfo+=rs.getInt("id")+",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		if(!problemInfo.equals("")) {
			problemInfo=problemInfo.substring(0, problemInfo.length()-1);
		}
		return problemInfo;
	}
	
	public static String problemRolePermission(String condition) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String problemInfo ="";
		
		String query = "SELECT id FROM role"+condition;
		System.out.println(query);
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				problemInfo+=rs.getInt("id")+",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		if(!problemInfo.equals("")) {
			problemInfo=problemInfo.substring(0, problemInfo.length()-1);
		}
		return problemInfo;
	}
	
	public static String problemRbacAccount(String condition) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String problemInfo ="";
		
		String query = "SELECT id FROM account"+condition;
		System.out.println(query);
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				problemInfo+=rs.getInt("id")+",";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
		if(!problemInfo.equals("")) {
			problemInfo=problemInfo.substring(0, problemInfo.length()-1);
		}
		return problemInfo;
	}
	
	
}
