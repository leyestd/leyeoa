package tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import login.javabean.Limit;
import database.ConnectionPool;
import database.DBUtil;
import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import security.BCrypt;

public class Test {

	public static void main(String[] args) {
//		String mflow[]="e,4".split(",");
//	System.out.println(mflow[1]);
	
//	String[] str = {"abc", "bcd", "def"};
//	StringBuffer sb = new StringBuffer();
//	for(int i = 0; i < str.length; i++){
//	 sb. append(str[i]);
//	}
	//HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
	//HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
//	if(rbac.get(key).getRole().contains(roles.get(Integer.valueOf(roleid)).getName())) {
//		System.out.println(userList.get(key));
//	}else {
//		HashMap<Integer,String> user=roles.get(Integer.valueOf(roleid)).getUser();
//		Set<Integer> userkey=user.keySet();
//		for(int k : userkey) {
//			System.out.println(user.get(k)+"外部");
//		}
//		
//	}
		//.matches("^45(,\\d+)*$")
//	int default_roleid=rbac.get(accountid).getDefault_roleid();WHERE name REGEXP '^45(,\\d+)*$';
//	//String defaultPermission=roles.get(default_roleid).getPermission().get(key)
//	HashMap<Integer,String> userList=roles.get(default_roleid).getUser();
//	Set<Integer> userskey=userList.keySet();
//	
//	String before;
//	
//	HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)getServletContext().getAttribute("rbac");
//	HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>)getServletContext().getAttribute("roles");
//	
//	String mflow[]=mWorkflow.split(",");
//	for(String roleid : mflow) {
//		//System.out.println(role);
//		for(int key : userskey) {
//			
//		}
//		
//		幽幽幽幽幽幽幽幽幽幽幽幽幽幽

//表单 6 4 7 8 
//
//
//如果我有角色6  
//
//判断   我的默认角色是束与表单表起人一样，如果一样，让我可以签
//
//else   如果不一样，判断此表单的用户的默认角色中的所有用户是否有此角色，如果有，我没权限签，如果没有，我有权限签（外部门签单）
////		
////		
////		
////	}
//	ArrayList<String> fwef=new ArrayList<String>();
//	fwef.add("wef");
//	fwef.add("grege");
//	fwef.add("ghtrhr");
//	fwef.contains(null);	
String aa="3";
	String a[]=aa.split(",");
	System.out.println(a[0]);
	System.out.println(a.length);
	String hashed = BCrypt.hashpw("12345678",
			BCrypt.gensalt());
	System.out.println(hashed);
	}
}
