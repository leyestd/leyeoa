package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionPool;
import database.DBUtil;

public class D_Role_Hierarchy {
	public static int doSelect(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    String query = "SELECT advanced_role FROM role_hierarchy WHERE basic_role=?";
	    int advanced_role=0;
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, id);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		advanced_role=rs.getInt("advanced_role");
	    	}
	    	return advanced_role;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	}
	
	public static ArrayList<Integer> doSelectAdvanced(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    String query = "SELECT basic_role FROM role_hierarchy WHERE advanced_role=?";
	    ArrayList<Integer> basic_role=null;
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, id);
	    	rs=ps.executeQuery();
	    	basic_role=new ArrayList<Integer>();
	    	while(rs.next()) {
	    		int c=rs.getInt("basic_role");
	    		basic_role.add(c);
	    	}
	    	return basic_role;
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
	
	
	public static int doUpdate(String roleid,String advancedid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=doDelete(roleid); 
	    String query = "INSERT INTO role_hierarchy (advanced_role,basic_role) VALUES (?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(advancedid));
	    	ps.setInt(2, Integer.valueOf(roleid));
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
	
	public static int doDelete(String roleid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM role_hierarchy WHERE basic_role=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(roleid));  	
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
