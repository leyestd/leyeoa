package login.javabean;


public class Limit {
	private int id;
	private int number;
	private String ipaddress;
	private long createtime;
	private String username;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public long  getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long   createtime) {
		this.createtime = createtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Limit() {
		
	}
}
