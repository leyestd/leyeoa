package frontend.inputcheck;

import security.RegexUtil;

public class CheckAccountInfo {
		public static String doMatch(String email,String fullname) {
			String value="ok";
			
			if(!RegexUtil.isEmail(email)) {
				return "邮箱格式有误";
			}
			
			if(!RegexUtil.isFullname(fullname)) {
				return "全名格式有误";
			}
		    return value;
		    
		}
		
		public static String doCheckNull(String password,String fullname,String email ) {
			String value="ok";
			if(password == null || email==null || fullname==null) {
				return "";
			}else {
				password=password.trim();
				email=email.trim();
				fullname=fullname.trim();
				
				if(email.length()<5 || email.length()>80){
					return "邮箱长度不符";
				}else if(password.length()<8 || password.length()>60) {
					return "密码长度不符";
				}else if(fullname.length()<2 || fullname.length()>40) {
					return "全名长度不符";
				}
			}
			
			return value;
		}
}
