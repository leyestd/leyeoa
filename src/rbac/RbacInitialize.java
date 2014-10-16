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
public class RbacInitialize {
    public static HashMap<Integer,RbacAccount> doRbacUserInit() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacAccount> rbac = new HashMap<Integer , RbacAccount>(); 
        
        String query = 
                "SELECT account.id,account.fullname,account.default_roleid,role.name FROM account " +
                        "inner join account_role on account.id= account_role.account_id " +
                        "inner join role on account_role.role_id=role.id order by account.id";
        try
        {   
            ArrayList<String> role=new ArrayList<String>();
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
                        role=new ArrayList<String>();
                        u=new RbacAccount();
                }
               //System.out.println(rs.getInt("account.id")+"    "+ rs.getString("role.name")+ "  "+rs.getString("permission.name"));
                if(role.indexOf(rs.getString("role.name"))==-1){
                     role.add(rs.getString("role.name"));   
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
    
    public static HashMap<Integer,RbacRole> doRbacRoleInit() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<Integer , RbacRole> roles = new HashMap<Integer , RbacRole>(); 
        
        String query = 
                "SELECT role.id,role.name,role.alias,account.id,account.fullname FROM role " +
                        "inner join account_role on role.id= account_role.role_id " +
                        "inner join account on account_role.account_id=account.id " +
                        "order by role.id";
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
    
}
