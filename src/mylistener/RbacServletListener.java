/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mylistener;


import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import rbac.RbacInitialize;
import rbac.javabean.RbacRole;
import rbac.javabean.RbacAccount;
/**
 * Web application lifecycle listener.
 *
 * @author Administrator
 */
public class RbacServletListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        
        HashMap<Integer,RbacAccount> rbac=RbacInitialize.doRbacUserInit();
        HashMap<Integer,RbacRole> roles=RbacInitialize.doRbacRoleInit();
        
        sce.getServletContext().setAttribute("rbac", rbac);
        sce.getServletContext().setAttribute("roles", roles);
        
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("系统已关闭"); 
    }

}
