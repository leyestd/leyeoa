package rbac.inputcheck;

import rbac.javabean.Account;
import security.RegexUtil;

public class CheckAccount {
	public static String doMatch(Account user,String enable,String default_roleid) {
		String value="ok";
		
		if(!RegexUtil.isEmail(user.getEmail())) {
			return "邮箱格式有误";
		}
		
		if(!RegexUtil.isName(user.getUsername())) {
			return "用户名格式有误";
		}
		
		if(!RegexUtil.isFullname(user.getFullname())) {
			return "全名格式有误";
		}
	    
		if(!enable.equals("1") && !enable.equals("0")) {
			return "启用按钮格式有误";
		}
		if(!RegexUtil.isDigital(default_roleid)) {
			return "默认角色格式有误";
		}
		
	    return value;
	    
	}
	
	public static String doCheckNull(Account user,String enable,String default_roleid) {
		String value="ok";
		if(default_roleid == null || user.getUsername()== null || user.getPassword() == null || user.getEmail()==null || user.getFullname()==null) {
			return "";
		}else {
			user.setUsername(user.getUsername().trim());
			user.setPassword(user.getPassword().trim());
			user.setEmail(user.getEmail().trim());
			user.setFullname(user.getFullname().trim());
			enable=enable.trim();
			
			if(user.getUsername().length()<6 || user.getUsername().length()>25 ) {
				return "用户名长度不符";
			}else if(user.getEmail().length()<5 || user.getEmail().length()>80){
				return "邮箱长度不符";
			}else if(user.getPassword().length()<8 || user.getPassword().length()>60) {
				return "密码长度不符";
			}else if(user.getFullname().length()<2 || user.getFullname().length()>40) {
				return "全名长度不符";
			}else if(enable.length()!=1) {
				return "启用按钮长度不符";
			}
		}
		
		return value;
	}
	public static String doMatch(Account user,String enable) {
		String value="ok";
		
		if(!RegexUtil.isEmail(user.getEmail())) {
			return "邮箱格式有误";
		}
		
		if(!RegexUtil.isName(user.getUsername())) {
			return "用户名格式有误";
		}
		
		if(!RegexUtil.isFullname(user.getFullname())) {
			return "全名格式有误";
		}
	    
		if(!enable.equals("1") && !enable.equals("0")) {
			return "启用按钮格式有误";
		}
		
	    return value;
	    
	}
	
	public static String doCheckNull(Account user,String enable) {
		String value="ok";
		if(user.getUsername()== null || user.getPassword() == null || user.getEmail()==null || user.getFullname()==null) {
			return "";
		}else {
			user.setUsername(user.getUsername().trim());
			user.setPassword(user.getPassword().trim());
			user.setEmail(user.getEmail().trim());
			user.setFullname(user.getFullname().trim());
			enable=enable.trim();
			
			if(user.getUsername().length()<6 || user.getUsername().length()>25 ) {
				return "用户名长度不符";
			}else if(user.getEmail().length()<5 || user.getEmail().length()>80){
				return "邮箱长度不符";
			}else if(user.getPassword().length()<8 || user.getPassword().length()>60) {
				return "密码长度不符";
			}else if(user.getFullname().length()<2 || user.getFullname().length()>40) {
				return "全名长度不符";
			}else if(enable.length()!=1) {
				return "启用按钮长度不符";
			}
		}
		
		return value;
	}
}
