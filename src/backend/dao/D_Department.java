package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rbac.javabean.Department;
import database.ConnectionPool;
import database.DBUtil;

//创建部门，使用父ID
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
	
	//显示所有部门
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
	
	//查询此部门有没了类
	public static int doSelectChild(int pid) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    String query = "SELECT id FROM department WHERE pid=?";
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
	    String query = "DELETE FROM department WHERE id=?";
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
	
	//查询部门信息
	public static Department doSelect(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    Department dep=null;
	    String query = "SELECT id,name,alias,pid FROM department WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, id);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		dep=new Department();
	    		dep.setId(rs.getInt("id"));
	    		dep.setName(rs.getString("name"));
	    		dep.setAlias(rs.getString("alias"));
	    		dep.setPid(rs.getInt("pid"));
	    	}
	    	return dep;
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
	
	//更新部门
	public static int doUpdate(String name,String alias,String id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE department SET name =?,alias=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, alias);
	    	ps.setInt(3,Integer.valueOf(id));
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

	//更新部门层次
	public static int doUpdateHierarchy(String depid,String pdepid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE department SET pid=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(pdepid));
	    	ps.setInt(2, Integer.valueOf(depid));
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
	
	//改为无上层
	public static int doDeleteHierarchy(String depid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE department SET pid=0 WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(depid));
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
