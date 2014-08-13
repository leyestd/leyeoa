package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import backend.javabean.Workform;
import database.ConnectionPool;
import database.DBUtil;

public class D_Workform {
	public static int doCreate(String workform,String name,int type_id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO workform (name,content,type_id) VALUES (?,?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, workform);
	    	ps.setInt(3, type_id);
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
	
	public static int doDelete(int workformId) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM workform WHERE id=?";

	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, workformId);  	
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
	
	public static Workform doSelect(String formid) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	  	Workform form=null;
	    String query = "SELECT id,name,content,type_id FROM workform WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(formid));
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		form=new Workform();
	    		form.setId(rs.getInt("id"));
	    		form.setName(rs.getString("name"));
	    		form.setContent(rs.getString("content"));
	    		form.setType_id(rs.getInt("type_id"));
	    	}
	    	return form;
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
	
	public static int doUpdate(String content,String name,int formid,int type_id) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE workform SET name =?,content=? ,type_id=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2, content);
	    	ps.setInt(3, type_id);
	    	ps.setInt(4,formid);
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
	
	public static int doUpdateFromId(int formid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE defaultflow SET workform_id=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setNull(1, Types.INTEGER);;
	    	ps.setInt(2, formid);
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
	
	public static int doUpdateFromId(int workform_id,int formid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE defaultflow SET workform_id=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, workform_id);;
	    	ps.setInt(2, formid);
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
	
	public static ArrayList<Workform> doSelectAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    ArrayList<Workform> formList=new ArrayList<Workform>();
	  	Workform form=null;
	    String query = "SELECT id,name FROM workform";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		form=new Workform();
	    		form.setId(rs.getInt("id"));
	    		form.setName(rs.getString("name"));
	    		formList.add(form);
	    	}
	    	return formList;
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
