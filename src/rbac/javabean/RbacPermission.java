package rbac.javabean;

import java.util.ArrayList;
import java.util.HashMap;

public class RbacPermission {
    private int id;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private String alias;
	
	private HashMap<String, ArrayList<Permission>> permissions;

	public RbacPermission() {
	}

	public HashMap<String, ArrayList<Permission>> getPermission() {
		return permissions;
	}

	public void setPermission(HashMap<String, ArrayList<Permission>> permissions) {
		this.permissions = permissions;
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
}
