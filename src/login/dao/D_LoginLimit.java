package login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import login.javabean.Limit;
import database.ConnectionPool;
import database.DBUtil;

public class D_LoginLimit {
	public static int doCreate(String ipaddress,String username,int number) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO loginlimit (ipaddress,username,number) VALUES (?,?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1,  ipaddress);
	    	ps.setString(2, username);
	    	ps.setInt(3, number);
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
	
	public static Limit doSelectLimit (String username) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Limit limit=null;
	    
	    String query = "SELECT id,ipaddress,number,createtime FROM loginlimit WHERE username=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, username);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		limit=new Limit();
	    		limit.setId(rs.getInt("id"));
	    		limit.setIpaddress(rs.getString("ipaddress"));
	    		limit.setNumber(rs.getInt("number"));
	    		limit.setCreatetime(rs.getTimestamp("createtime").getTime());
	    	}
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
		
		return limit;
	}
	
	public static int doDelete(String username) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM loginlimit WHERE username=?";

	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, username);  	
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
	
	public static int doUpdate(String username) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE loginlimit SET number = number + 1 WHERE username = ?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, username);
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
	
	public static int doDeleteAll() {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM loginlimit WHERE TIMESTAMPDIFF(MINUTE,createtime,current_timestamp()) > 25";

	    try {
	    	ps = connection.prepareStatement(query);	
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
