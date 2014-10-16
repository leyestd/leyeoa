/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rbac;


import rbac.javabean.RbacAccount;

/**
 *
 * @author Administrator
 */
public class Check {
    public static boolean hasRole(String role,RbacAccount user) {   
        return (user.getRole().indexOf(role)!=-1);
    }
    
//    public static boolean isPermitted(String permission,RbacAccount user) {
//        return (user.getPermission().indexOf(permission)!=-1);
//    }
}
