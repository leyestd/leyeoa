package rbac.javabean;

import java.util.ArrayList;

public class RbacAccount {
	private int default_roleid;

	public int getDefault_roleid() {
		return default_roleid;
	}

	public void setDefault_roleid(int default_roleid) {
		this.default_roleid = default_roleid;
	}

	private String fullname;
	private ArrayList<String> role;
	private ArrayList<String> permission;

	public RbacAccount() {
	}

	public ArrayList<String> getRole() {
		return role;
	}

	public void setRole(ArrayList<String> role) {
		this.role = role;
	}

	public ArrayList<String> getPermission() {
		return permission;
	}

	public void setPermission(ArrayList<String> permission) {
		this.permission = permission;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
