/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rbac;

import database.ConnectionPool;
import database.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import rbac.javabean.RbacRole;
import rbac.javabean.RbacAccount;

/**
 *
 * @author Administrator
 */

/*
  SELECT role.id ,concat(controller.name,'/',action.name) FROM role INNER JOIN role_permission on role.id = role_permission.role_id 
					   INNER JOIN permission AS action ON role_permission.permission_id = action.id
					   INNER JOIN permission AS controller ON action.pid = controller.id ORDER BY role.id
 */

//取得用户对应信息
public class RbacInitialize {
    public static HashMap<Integer,RbacAccount> doRbacUserInit() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacAccount> rbac = new HashMap<Integer , RbacAccount>(); 
        
        String query = 
                "SELECT account.id,account.fullname,account.default_roleid,role.id FROM account " +
                        "INNER JOIN account_role ON account.id= account_role.account_id " +
                        "INNER JOIN role ON account_role.role_id=role.id ORDER BY account.id";
        try
        {   
            ArrayList<Integer> role=new ArrayList<Integer>();
            int temp=0;
            RbacAccount u=new RbacAccount();
            
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                if(temp==0) {
                    temp=rs.getInt("account.id");            
                }
                else if(temp != rs.getInt("account.id")) 
                {       
                        u.setRole(role);
                        rbac.put(temp, u);
                        temp=rs.getInt("account.id");
                        role=new ArrayList<Integer>();
                        u=new RbacAccount();
                }
               //System.out.println(rs.getInt("account.id")+"    "+ rs.getString("role.name")+ "  "+rs.getString("permission.name"));
                if(role.indexOf(rs.getInt("role.id"))==-1){
                     role.add(rs.getInt("role.id"));   
                }
                
                u.setFullname(rs.getString("account.fullname"));
                u.setDefault_roleid(rs.getInt("default_roleid"));
               // System.out.println(rs.getInt("account.id")+ "  | "+rs.getString("role.name")+" | "+rs.getString("permission.name"));
            }
            if(temp!=0) {                  
                   u.setRole(role);
                   rbac.put(temp, u);
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
        return rbac;
    }
   
//取得角色下所有用户    
    public static HashMap<Integer,RbacRole> doRbacRoleInit() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacRole> roles = new HashMap<Integer , RbacRole>(); 
        
        String query = 
                "SELECT role.id,role.name,role.alias,account.id,account.fullname FROM role " +
                        "INNER JOIN account_role ON role.id= account_role.role_id " +
                        "INNER JOIN account ON account_role.account_id=account.id " +
                        "ORDER BY role.id";
        try
        {       
            HashMap<Integer,String> user=new HashMap<Integer,String>();        
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            int temp=0;
            RbacRole role=new RbacRole();
            
            while(rs.next()){
                if(temp==0) { 
                    temp=rs.getInt("role.id");
                }else if(temp!=rs.getInt("role.id")) {
                    role.setUser(user);
                    roles.put(temp, role);
                    temp=rs.getInt("role.id");
                    role=new RbacRole();
                    user=new HashMap<Integer,String>();
                }
                user.put(rs.getInt("account.id"), rs.getString("account.fullname"));
                role.setName(rs.getString("role.name"));
                role.setAlias(rs.getString("role.alias"));
            }
            if(temp!=0) {
                    role.setUser(user);
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

//取得角色所有可执行的动作
    public static HashMap<Integer,ArrayList<String>> doRbacActionInit() {
    	ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer,ArrayList<String>> actions = new  HashMap<Integer,ArrayList<String>>(); 
        
        String query = 
                "SELECT role.id ,concat(controller.name,'/',action.name) as action FROM role" +
                     " INNER JOIN role_permission on role.id = role_permission.role_id"+ 
					 " INNER JOIN permission AS action ON role_permission.permission_id = action.id" +
					 " INNER JOIN permission AS controller ON action.pid = controller.id ORDER BY role.id";
        try
        {       
        	ArrayList<String> action = new ArrayList<String>();        
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            int temp=0;
            
            while(rs.next()){
                if(temp==0) { 
                    temp=rs.getInt("role.id");
                }else if(temp!=rs.getInt("role.id")) {
                    actions.put(temp, action);
                    temp=rs.getInt("role.id");
                    action = new ArrayList<String>();
                }
                action.add(rs.getString("action"));
            }
            if(temp!=0) {
            	actions.put(temp, action);
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
 
        return actions;
    }
}
