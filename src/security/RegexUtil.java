package security;

import org.apache.commons.validator.routines.EmailValidator;

public class RegexUtil {

	public static boolean isEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) {
			return false;
		}
		return true;
	}

	public static boolean isName(String name) {
		if (!name.matches("^[\\w]+$")) {
			return false;
		}
		return true;
	}

	public static boolean isFullname(String fullname) {
		if (!fullname.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$")) {
			return false;
		}
		return true;
	}

	public static boolean isWorkflow(String workflow) {
		if (!workflow.matches("^(\\d+,)*\\d+$")) {
			return false;
		}
		return true;
	}
	
	public static boolean isDigital(String digital) {
		if (!digital.matches("^\\d+$")) {
			return false;
		}
		return true;
	}
}