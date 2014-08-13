package frontend.inputcheck;

import security.RegexUtil;

public class CheckQuery {
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
	
	public static String doCheckNull(String myYear,String myMonth) {
		String value="ok";
		if(myYear == null || myMonth==null) {
			return "";
		}else {
			myYear=myYear.trim();
			myMonth=myMonth.trim();
			
			if(myMonth.length()!=2 && myMonth.length()!=1){
				return "月份长度不符";
			}else if(myYear.length()!=4) {
				return "年份长度不符";
			}
		}
		
		return value;
	}
}
