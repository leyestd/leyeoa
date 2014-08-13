package backend.inputcheck;

import security.RegexUtil;

public class CheckWorkform {
	public static String doMatch(String name) {
		String value="ok";	
		
		if(!RegexUtil.isFullname(name)) {
			return "表单名称格式有误";
		}	
		
	    return value;
	    
	}
	
	public static String doCheckNull(String content,String name) {
		String value="ok";
		
		if(content== null || name == null) {
			return "";
		}else {
			content=content.trim();
			name=name.trim();
			
			if(content.length()< 10 || content.length() > 50000 ) {
				return "表单长度不符";
			}else if(name.length()<2 || name.length()>25){
				return "表单名称长度不符";
			}
		}
		
		return value;
	}
}
