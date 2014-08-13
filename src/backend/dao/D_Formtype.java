package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import backend.javabean.Formtype;
import database.ConnectionPool;
import database.DBUtil;

public class D_Formtype {
	public static int doCreate(String name) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO formtype (name) VALUES (?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
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
	
	
	public static int doDelete(int formtypeid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM formtype WHERE id=?";

	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, formtypeid);  	
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
	
	public static ArrayList<String> doSelect(String formtypeid) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    ArrayList<String> formtype=null;
	    String query = "SELECT id,name FROM formtype WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(formtypeid));
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		formtype=new ArrayList<String>();
	    		formtype.add(String.valueOf(rs.getInt("id")));
	    		formtype.add(rs.getString("name"));
	    	}
	    	return formtype;
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
	
	public static int doUpdate(String name,int formtypeid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE formtype SET name =? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setInt(2,formtypeid);
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
	
	public static ArrayList<Formtype> doSelectAll() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    ArrayList<Formtype> formtypes=new ArrayList<Formtype>();
	    Formtype formtype;
	    String query = "SELECT id,name FROM formtype";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		formtype=new Formtype();
	    		formtype.setId(rs.getInt("id"));
	    		formtype.setName(rs.getString("name"));
	    		formtypes.add(formtype);
	    	}
	    	return formtypes;
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
