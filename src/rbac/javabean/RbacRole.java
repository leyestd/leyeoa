/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rbac.javabean;

import java.util.HashMap;


/**
 *
 * @author Administrator
 */
public class RbacRole {
    
    private HashMap<Integer,String> user;
    private String name;
    private String alias;
    private HashMap<Integer,String> permission;
    
	public RbacRole() {
    }
    
    public HashMap<Integer, String> getPermission() {
		return permission;
	}

	public void setPermission(HashMap<Integer, String> permission) {
		this.permission = permission;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public HashMap<Integer,String> getUser() {
        return user;
    }

    public void setUser(HashMap<Integer,String> user) {
        this.user = user;
    }
    
    
    
}
