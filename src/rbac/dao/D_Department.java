package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rbac.javabean.AccountPermissionRole;
import rbac.javabean.Department;
import database.ConnectionPool;
import database.DBUtil;

public class D_Department {
	public static int doCreate(String name,String alias,int pid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    int GeneratedId=0;
	    String query = "INSERT INTO department (name,alias,pid) VALUES (?,?,?)";
	    try {
	    	ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	    	ps.setString(1, name);
	    	ps.setString(2, alias);
	    	ps.setInt(3, pid);
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
	
	public static ArrayList<Department> doSelectAllDepartment() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    ArrayList<Department> departments=new ArrayList<Department>();
	    Department dep;
	    String query = "SELECT id,name,alias,pid FROM department";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		dep=new Department();;
	    		dep.setId(Integer.valueOf(rs.getInt("id")));
	    		dep.setName(rs.getString("name"));
	    		dep.setAlias(rs.getString("alias"));
	    		dep.setPid(rs.getInt("pid"));
	    		departments.add(dep);
	    	}
	    	return departments;
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
	
	
	
	public static int doCreateHierarchy(String name,String alias,int advanced_roleid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    PreparedStatement ps2=null;
	    ResultSet rs=null;
	    int count=0;
	    String query = "INSERT INTO role (name,alias) VALUES (?,?)";
	    String query2 = "INSERT INTO role_hierarchy (advanced_role,basic_role) VALUES (?,?)";
	    try {
	    	connection.setAutoCommit(false);
	    	ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	    	ps.setString(1, name);
	    	ps.setString(2, alias);
	    	ps.executeUpdate();
	    	rs=ps.getGeneratedKeys();
	    	if(rs.next()){
	    		count=rs.getInt(1);
	    	}
	    	ps2 = connection.prepareStatement(query2);
	    	ps2.setInt(1, advanced_roleid);
	    	ps2.setInt(2, count);
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
	
	public static AccountPermissionRole doSelect(String name) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    AccountPermissionRole role=null;
	    String query = "SELECT id,name,alias FROM role WHERE name=?";
	    
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
	    String query = "UPDATE role SET name =?,alias=? WHERE name=?";
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
	    ArrayList<AccountPermissionRole> roles=new ArrayList<AccountPermissionRole>();
	    AccountPermissionRole role;
	    String query = "SELECT id,name,alias FROM role";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		role=new AccountPermissionRole();;
	    		role.setId(Integer.valueOf(rs.getInt("id")));
	    		role.setName(rs.getString("name"));
	    		role.setAlias(rs.getString("alias"));
	    		roles.add(role);
	    	}
	    	return roles;
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
	
	public static int doUpdateDefault(int userid,int roleid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE account SET default_roleid=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1,  roleid);
	    	ps.setInt(2, userid);
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
