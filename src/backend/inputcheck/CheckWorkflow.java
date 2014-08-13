package backend.inputcheck;

import security.RegexUtil;

public class CheckWorkflow {
	public static String doMatch(String mWorkflow,String wName,String formId) {
		String value="ok";
		
		if(!RegexUtil.isWorkflow(mWorkflow)) {
			return "工作流格式有误";
		}
		
		if(!RegexUtil.isFullname(wName)) {
			return "工作流名称格式有误";
		}	
		
		if(!RegexUtil.isDigital(formId)){
			return "表单ID格式有误";
		}
	    return value;
	    
	}
	
	public static String doCheckNull(String mWorkflow,String wName, String formId) {
		String value="ok";
		
		if(mWorkflow== null || wName == null ||  formId == null) {
			return "";
		}else {
			mWorkflow=mWorkflow.trim();
			wName=wName.trim();
			
			if(mWorkflow.length()< 1 || mWorkflow.length() > 255 ) {
				return "工作流长度不符";
			}else if(wName.length()<2 || wName.length()>25){
				return "工作流名称长度不符";
			}
		}
		
		return value;
	}
}
