package frontend.javabean;


import java.util.HashMap;


public class Typeform {

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	private String typename;
	
	private HashMap<Integer,String> workforms; 
	
	public HashMap<Integer, String> getWorkforms() {
		return workforms;
	}

	public void setWorkforms(HashMap<Integer, String> workforms) {
		this.workforms = workforms;
	}

	public Typeform() {
	}
	
	
}
