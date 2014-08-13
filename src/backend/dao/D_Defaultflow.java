package backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import backend.javabean.Defaultflow;
import database.ConnectionPool;
import database.DBUtil;

public class D_Defaultflow {
	public static int doCreate(String mWorkflow,String wName,String formId) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO defaultflow (name,participate,workform_id) VALUES (?,?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, wName);
	    	ps.setString(2, mWorkflow);
	    	ps.setInt(3, Integer.valueOf(formId));
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
	
	public static int doDelete(int flowid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM defaultflow WHERE id=?";

	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, flowid);  	
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
	
	public static Defaultflow doSelect(String flowid) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    Defaultflow flow=null;
	    String query = "SELECT id,name,participate FROM defaultflow WHERE id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(flowid));
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		flow=new Defaultflow();
	    		flow.setId(rs.getInt("id"));
	    		flow.setName(rs.getString("name"));
	    		flow.setParticipate(rs.getString("participate"));;
	    	}
	    	return flow;
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
	
	public static ArrayList<Defaultflow> doSelectActive(String workformId) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    ArrayList<Defaultflow> workflows=new ArrayList<Defaultflow>();
	    Defaultflow flow;
	    String query = "SELECT id,name,participate FROM defaultflow WHERE workform_id=?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(workformId));
	    	rs=ps.executeQuery();
	    	while(rs.next()) {
	    		flow=new Defaultflow();
	    		flow.setId(rs.getInt("id"));
	    		flow.setName(rs.getString("name"));
	    		flow.setParticipate(rs.getString("participate"));
	    		workflows.add(flow);
	    	}
	    	return workflows;
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
	
	public static int doUpdate(String name,String participate,String flowid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "UPDATE defaultflow SET name =? ,participate=? WHERE id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setString(1, name);
	    	ps.setString(2,participate);
	    	ps.setInt(3, Integer.valueOf(flowid));
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
