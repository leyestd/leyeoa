package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import rbac.javabean.RbacRole;
import database.ConnectionPool;
import database.DBUtil;

public class D_Role_Permission {
	public static int doCreate(String roleid,String permissionid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "INSERT INTO role_permission (role_id,permission_id) VALUES (?,?)";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(roleid));
	    	ps.setInt(2, Integer.valueOf(permissionid));
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
	
	public static int doDelete(String roleid,String permissionid) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM role_permission WHERE role_id=? and permission_id=?";
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, Integer.valueOf(roleid));
	    	ps.setInt(2, Integer.valueOf(permissionid));    	
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
	
	public static int doDeleteRoleOrPermission(int id,String tableName) {
	    ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    int count=0;
	    String query = "DELETE FROM "+tableName+" WHERE id=?";

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
	
	public static HashMap<Integer,RbacRole> doSelectRolePermission() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacRole> roles = new HashMap<Integer , RbacRole>(); 
        
        String query = 
                "SELECT role.id,role.name,role.alias,permission.id,permission.alias FROM role " +
                        "inner join role_permission on role.id=role_permission.role_id " +
                        "inner join permission on role_permission.permission_id=permission.id " +
                        "order by role.id";
        try
        {   

            
            HashMap<Integer,String> permission=new HashMap<Integer,String>();        
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            int temp=0;
            RbacRole role=new RbacRole();
            
            while(rs.next()){
                if(temp==0) { 
                    temp=rs.getInt("role.id");
                }else if(temp!=rs.getInt("role.id")) {
                    role.setPermission(permission);
                    roles.put(temp, role);
                    temp=rs.getInt("role.id");
                    role=new RbacRole();
                    permission=new HashMap<Integer,String>();
                }
                permission.put(rs.getInt("permission.id"), rs.getString("permission.alias"));
                role.setName(rs.getString("role.name"));
                role.setAlias(rs.getString("role.alias"));
            }
            if(temp!=0) {
                    role.setPermission(permission);
                    roles.put(temp, role);
            }
            
        }
        catch(SQLException e)
        {   
            e.printStackTrace();
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
 
        return roles;
    }
	
	public static int doSelectHasRelationship(int id) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    PreparedStatement ps2=null;
	    ResultSet rs2=null;
	    
	    int count=0;
	    String query="SELECT COUNT(*) as c FROM workflow WHERE roleflow REGEXP '^([[:digit:]]+,)*" + id + "(,[[:digit:]]+)*$'";
		String query2="SELECT COUNT(*) as c FROM defaultflow WHERE participate REGEXP '^([[:digit:]]+,)*" + id + "(,[[:digit:]]+)*$'";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		count=rs.getInt("c");
	    	}
	    	ps2 = connection.prepareStatement(query2);
	    	rs2=ps2.executeQuery();
	    	if(rs2.next()) {
	    		count+=rs2.getInt("c");
	    	}
	    	return count;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        DBUtil.closeResultSet(rs2);
	        DBUtil.closePreparedStatement(ps2);
	        pool.freeConnection(connection);
	    }
	}
}
