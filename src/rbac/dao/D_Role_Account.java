package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConnectionPool;
import database.DBUtil;

public class D_Role_Account {
	public static int doCreate(String roleid,String userid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO account_role (account_id,role_id) VALUES (?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(userid));
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
	
	public static int doDelete(String roleid,String userid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM account_role WHERE role_id=? and account_id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(roleid));
	    	ps.setInt(2, Integer.valueOf(userid));    	
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
