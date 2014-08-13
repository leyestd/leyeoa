package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backend.javabean.Announcement;
import database.ConnectionPool;
import database.DBUtil;

public class D_Announcement {
	public static int doCreate(String content,String name) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO announcement (name,content) VALUES (?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, content);
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
	
	public static int doDelete(int id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM announcement WHERE id=?";

	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1,id);  	
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
	
	public static Announcement doSelect(String id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    Announcement announcement=null;
	    String query = "SELECT id,name,content FROM announcement WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(id));
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		announcement=new Announcement();
	    		announcement.setId(rs.getInt("id"));
	    		announcement.setName(rs.getString("name"));
	    		announcement.setContent(rs.getString("content"));
	    	}
	    	return announcement;
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
	
	public static int doUpdate(String content,String name,int id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE announcement SET name =?,content=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, content);
	    	ps.setInt(3, id);
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
