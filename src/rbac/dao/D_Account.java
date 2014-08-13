package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rbac.javabean.Account;
import rbac.javabean.AccountPermissionRole;
import database.ConnectionPool;
import database.DBUtil;

public class D_Account {

	public static int doCreate(String username,String password,String email,String fullname,int enabled,int default_roleid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    PreparedStatement ps2=null;
	    ResultSet rs=null;
	    int count=0;
	    String query = "INSERT INTO account (username,password,fullname,email,enabled,default_roleid) VALUES (?,?,?,?,?,?)";
	    String query2 = "INSERT INTO account_role (account_id,role_id) VALUES (?,?)";
	    try {
	    	connection.setAutoCommit(false);
	    	ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	    	ps.setString(1, username);
	    	ps.setString(2, password);
	    	ps.setString(3,fullname);
	    	ps.setString(4, email);
	    	ps.setInt(5, enabled);
	    	ps.setInt(6, default_roleid);
	    	ps.executeUpdate();
	    	rs=ps.getGeneratedKeys();
	    	if(rs.next()){
	    		count=rs.getInt(1);
	    	}
	    	ps2 = connection.prepareStatement(query2);
	    	ps2.setInt(1, count);
	    	ps2.setInt(2, default_roleid);
	    	count=ps2.executeUpdate();
	    	connection.commit();
	    	connection.setAutoCommit(true);
	    }
	    catch(SQLException e)
	    {	
	    	try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return 0;
			}
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {	
	    	DBUtil.closeResultSet(rs);
	    	DBUtil.closePreparedStatement(ps);
	    	DBUtil.closePreparedStatement(ps2);
	        pool.freeConnection(connection);
	    }
	    return count;
	}
	
	public static int doUpdate(String username) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE account SET enabled=0 WHERE username=?";
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
	
	public static Account doSelect(String username) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Account user=new Account();
	    String query = "SELECT id,username, password, email, fullname, enabled FROM account WHERE username=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1,username);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		user.setId(rs.getInt("id"));
	    		user.setUsername(rs.getString("username"));
	    		user.setFullname(rs.getString("fullname"));
	    		user.setEmail(rs.getString("email"));
	    		user.setPassword(rs.getString("password"));
	    		user.setEnable(rs.getInt("enabled"));
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
	
	public static int doUpdate(String username,String password,String email,String fullname,int enabled,String oldUsername) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE account SET username =?,password=?,fullname=?,email=?,enabled=? WHERE username=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, username);
	    	ps.setString(2, password);
	    	ps.setString(3,fullname);
	    	ps.setString(4, email);
	    	ps.setInt(5, enabled);
	    	ps.setString(6, oldUsername);
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
	
	public static ArrayList<AccountPermissionRole> doSelectAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    ArrayList<AccountPermissionRole> users=new ArrayList<AccountPermissionRole>();
	    AccountPermissionRole user;
	    String query = "SELECT id,username,fullname FROM account WHERE enabled=1";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		user=new AccountPermissionRole();;
	    		user.setId(Integer.valueOf(rs.getInt("id")));
	    		user.setName(rs.getString("username"));
	    		user.setAlias(rs.getString("fullname"));
	    		users.add(user);
	    	}
	    	return users;
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
}
