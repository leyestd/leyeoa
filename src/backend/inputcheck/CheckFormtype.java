package backend.inputcheck;

import security.RegexUtil;

public class CheckFormtype {
	public static String doMatch(String name) {
		String value="ok";
		
		if(!RegexUtil.isFullname(name)) {
			return "表单类型名称格式有误";
		}		
	    return value;
	    
	}
	
	public static String doCheckNull(String name) {
		String value="ok";
		
		if(name == null) {
			return "";
		}else {
			name=name.trim();
			
			if(name.length()<2 || name.length()>25){
				return "表单类型名称长度不符";
			}
		}
		
		return value;
	}
}
