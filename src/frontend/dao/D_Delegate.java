package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionPool;
import database.DBUtil;
import frontend.javabean.Delegate;

public class D_Delegate {
	public static Delegate doSelect(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Delegate delegate=null;
	    String query = "SELECT delegate.id,account_id,delegate_id,delegate.enabled,fullname FROM delegate INNER JOIN account on delegate.delegate_id=account.id  WHERE account_id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1,id);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		delegate=new Delegate();
	    		delegate.setId(rs.getInt("id"));
	    		delegate.setAccountId(rs.getInt("account_id"));
	    		delegate.setDelegateId(rs.getInt("delegate_id"));
	    		delegate.setEnabled(rs.getInt("enabled"));
	    		delegate.setName(rs.getString("fullname"));
	    	}
	    	return delegate;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	}
	
	public static ArrayList<Delegate> doSelectDelegate(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Delegate delegate=null;
	    ArrayList<Delegate> DelegateList=new ArrayList<Delegate>();
	    String query = "SELECT delegate.id,account_id,delegate_id,delegate.enabled,fullname FROM delegate INNER JOIN account on delegate.delegate_id=account.id  WHERE delegate_id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1,id);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		delegate=new Delegate();
	    		delegate.setId(rs.getInt("id"));
	    		delegate.setAccountId(rs.getInt("account_id"));
	    		delegate.setDelegateId(rs.getInt("delegate_id"));
	    		delegate.setEnabled(rs.getInt("enabled"));
	    		delegate.setName(rs.getString("fullname"));
	    		DelegateList.add(delegate);
	    	}
	    	return DelegateList;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	}
	
	public static int doDelete(int id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM delegate WHERE account_id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, id);
	    	count=ps.executeUpdate();
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	    return count;

	}
	
	public static int doCreate(int accountId,int delegateId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		String query = "INSERT INTO delegate (account_id,delegate_id,enabled) VALUES (?,?,1)";
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, accountId);
			ps.setInt(2, delegateId);
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
	
	public static int doUpdate(int status,int accountId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		int count = 0;
		String query = "UPDATE delegate SET enabled=? WHERE account_id=?";
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, accountId);
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
