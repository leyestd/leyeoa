package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rbac.javabean.AccountPermissionRole;
import rbac.javabean.Permission;
import database.ConnectionPool;
import database.DBUtil;

public class D_Permission {
	public static int doCreate(String name,String alias,String pid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    int GeneratedId=0;
	    String query = "INSERT INTO permission (name,alias,pid) VALUES (?,?,?)";
	    try {
	    	ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	    	ps.setString(1, name);
	    	ps.setString(2, alias);
	    	ps.setInt(3, Integer.valueOf(pid));
	    	ps.executeUpdate();
	    	rs=ps.getGeneratedKeys();
	    	if(rs.next()){
	    		GeneratedId=rs.getInt(1);
	    	}
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {
	    	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	    return GeneratedId;
	}	
	
	//查询所有控制器
	public static ArrayList<Permission> doSelectAllController() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    ArrayList<Permission> permissions=new ArrayList<Permission>();
	    Permission p=null;
	    String query = "SELECT id,name,alias,pid FROM permission WHERE pid=0";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	 
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		p=new Permission();
	    		p.setId(rs.getInt("id"));
	    		p.setName(rs.getString("name"));
	    		p.setAlias(rs.getString("alias"));
	    		p.setPid(rs.getInt("pid"));
	    		permissions.add(p);
	    	}
	    	return permissions;
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
	
	//查询所有控制器
	public static ArrayList<Permission> doSelectAllControllerActions() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    ArrayList<Permission> permissions=new ArrayList<Permission>();
	    Permission p=null;
	    String query = "SELECT id,name,alias,pid FROM permission";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	 
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		p=new Permission();
	    		p.setId(rs.getInt("id"));
	    		p.setName(rs.getString("name"));
	    		p.setAlias(rs.getString("alias"));
	    		p.setPid(rs.getInt("pid"));
	    		permissions.add(p);
	    	}
	    	return permissions;
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
	
	//查询此操作有没了下层
		public static int doSelectChild(int pid) {
			ConnectionPool pool = ConnectionPool.getInstance();
		    Connection connection = pool.getConnection();
		    PreparedStatement ps=null;
		    ResultSet rs=null;
		    
		    String query = "SELECT id FROM permission WHERE pid=?";
		    int id=0;
		    try {
		    	ps = connection.prepareStatement(query);
		    	ps.setInt(1, pid);
		    	rs=ps.executeQuery();
		    	if(rs.next()) {
		    		id=rs.getInt("id");
		    	}
		    	return id;
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
	
		//删除部门
		public static int doDelete(int id) {
		    ConnectionPool pool = ConnectionPool.getInstance();
		    Connection connection = pool.getConnection();
		    PreparedStatement ps=null;
		    int count=0;
		    String query = "DELETE FROM permission WHERE id=?";
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
		
	public static AccountPermissionRole doSelect(String name) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    AccountPermissionRole role=null;
	    String query = "SELECT id,name,alias FROM permission WHERE name=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		role=new AccountPermissionRole();
	    		role.setId(Integer.valueOf(rs.getInt("id")));
	    		role.setName(rs.getString("name"));
	    		role.setAlias(rs.getString("alias"));
	    	}
	    	return role;
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
	
	public static int doUpdate(String name,String alias,String oldName) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE permission SET name =?,alias=? WHERE name=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, alias);
	    	ps.setString(3,oldName);
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
	    ArrayList<AccountPermissionRole> permissions=new ArrayList<AccountPermissionRole>();
	    AccountPermissionRole permission;
	    String query = "SELECT id,name,alias FROM permission";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		permission=new AccountPermissionRole();;
	    		permission.setId(Integer.valueOf(rs.getInt("id")));
	    		permission.setName(rs.getString("name"));
	    		permission.setAlias(rs.getString("alias"));
	    		permissions.add(permission);
	    	}
	    	return permissions;
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
