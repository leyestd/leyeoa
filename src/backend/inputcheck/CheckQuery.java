package backend.inputcheck;

import security.RegexUtil;

public class CheckQuery {
	
	public static String doCheckNull(String queryType) {
		String value="ok";
		if(queryType == null) {
			return "";
		}else {
			 queryType= queryType.trim();
			
			if(queryType.length()< 1 ) {
				return "查询类型长度不符";
			}
		}
		
		return value;
	}
	
	
	public static String doCheckUsername(String  username) {
		String value="ok";
		if(username == null) {
			return "";
		}else {
			username=username.trim();
			
			if(username.length()< 1 ) {
				return "用户名长度不符";
			}
		}
		
		return value;
	}
	
	public static String doMatchUsername(String  username) {
		String value="ok";
		
		
		if(!RegexUtil.isName(username)) {
			return "用户名格式有误";
		}
		
	    return value;
	    
	}
	
	public static String doCheckFullname(String fullname) {
		String value="ok";
		if(fullname == null) {
			return "";
		}else {
			fullname=fullname.trim();
			
			if(fullname.length()< 1 ) {
				return "全名长度不符";
			}
		}
		
		return value;
	}
	
	public static String doMatchFullname(String fullname) {
		String value="ok";
		
		if(!RegexUtil.isFullname(fullname)) {
			return "全名格式有误";
		}		
	    return value;
	    
	}
	
	public static String doCheckNull(String queryType,String status,String year,String month,String tense) {
		String value="ok";
		if(queryType == null || status==null || year==null || month==null || tense==null) {
			return "";
		}else {
			 queryType= queryType.trim();
			 status=status.trim();
			 year=year.trim();
			 month= month.trim();
			 tense=tense.trim();
			
			if(queryType.length()< 5 || queryType.length()>7 ) {
				return "查询类型长度不符";
			}else if(status.length()!=1) {
				return "状态 长度不符";
			}else if(month.length()!=2 && month.length()!=1){
				return "月份长度不符";
			}else if(year.length()!=4) {
				return "年份长度不符";
			}else if(tense.length() < 5 || tense.length() > 7) {
				return "时态长度不符";
		
			}	
		}
		
		return value;
	}
	
	public static String doMatch(String myYear,String myMonth) {
		String value="ok";
		
		if(!RegexUtil.isDigital(myYear)) {
			return "年份格式有误";
		}
		
		if(!RegexUtil.isDigital(myMonth)) {
			return "月份格式有误";
		}
	    return value;
	    
	}
	
	public static String doMatchUserid(String userid) {
		String value="ok";
		
		if(!RegexUtil.isDigital(userid)) {
			return "ID格式有误";
		}

	    return value;
	    
	}
}
