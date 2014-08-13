package frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rbac.javabean.Account;
import database.ConnectionPool;
import database.DBUtil;

public class D_AccountInfo {
	public static Account doSelect(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Account user=new Account();
	    String query = "SELECT id, username, password,email, fullname FROM account WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1,id);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		user.setUsername(rs.getString("username"));
	    		user.setFullname(rs.getString("fullname"));
	    		user.setEmail(rs.getString("email"));
	    		user.setPassword(rs.getString("password"));
	    	}
	    	return user;
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
	
	public static int doUpdate(String password,String email,String fullname,int id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE account SET password=?,fullname=?,email=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, password);
	    	ps.setString(2,fullname);
	    	ps.setString(3, email);
	    	ps.setInt(4, id);
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
}
