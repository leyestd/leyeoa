package login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import security.BCrypt;
import database.ConnectionPool;
import database.DBUtil;

public class D_Login {
	public static int doCheckPassword(String username,String password) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    String query = "SELECT id , password FROM account WHERE username=? and enabled=1";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, username);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		if (BCrypt.checkpw(password, rs.getString("password"))) {
	    			return rs.getInt("id");
	    		}
	    	}
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
		
		return 0;
	}
	
	
	
	
}
