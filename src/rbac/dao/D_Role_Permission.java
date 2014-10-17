package rbac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import rbac.javabean.Permission;
import rbac.javabean.RbacPermission;
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
	
	public static HashMap<Integer,RbacPermission> doSelectRolePermission() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacPermission> roles = new HashMap<Integer , RbacPermission>(); 
        
        String query = 
                "SELECT role.id,role.name,role.alias,permission.id,permission.alias,p.id,p.alias FROM role " +
                        "inner join role_permission on role.id=role_permission.role_id " +
                        "inner join permission on role_permission.permission_id=permission.id " +
                        "inner join permission as p on permission.pid=p.id "+
                        "order by role.id,p.id";
        try
        {         
        	
        	
            //每个角色对应的信息和操作
            RbacPermission role=new RbacPermission();
            
        	//控制器下所有的操作
        	HashMap<String,ArrayList<Permission>> ControllerAction=new HashMap<String,ArrayList<Permission>>();
        	
        	//操作列表
        	ArrayList<Permission> pers=new ArrayList<Permission>();
        	
        	//每个操作的信息
            Permission permission=null;   
            
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            int temp=0;
            String tempPalias="";
            String tempRoleAlias="";
            
            while(rs.next()){
                if(temp==0) { 
                    temp=rs.getInt("role.id");
                    tempRoleAlias=rs.getString("role.alias");
                }else if(temp!=rs.getInt("role.id")) {
                	ControllerAction.put(tempPalias, pers);
                	role.setAlias(tempRoleAlias);
                    role.setPermission(ControllerAction);
                    roles.put(temp, role);
                    temp=rs.getInt("role.id");
                    tempRoleAlias=rs.getString("role.alias");
                    role=new RbacPermission();
                    pers=new ArrayList<Permission>();    
                	ControllerAction=new HashMap<String,ArrayList<Permission>>();
                	tempPalias="";
                }
                
                if(tempPalias.equals("")) {
                	tempPalias=rs.getString("p.alias");
                }else if(!tempPalias.equals(rs.getString("p.alias"))) {
                	ControllerAction.put(tempPalias, pers);
                	pers=new ArrayList<Permission>(); 
                	tempPalias=rs.getString("p.alias");
                	
                }
                permission=new Permission();
                permission.setId(rs.getInt("permission.id"));
                permission.setAlias(rs.getString("permission.alias"));
                pers.add(permission);
                
            }
            if(temp!=0) {
            	    role.setAlias(tempRoleAlias);
            		ControllerAction.put(tempPalias, pers);
                    role.setPermission(ControllerAction);
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
