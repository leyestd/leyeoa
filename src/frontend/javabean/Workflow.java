package frontend.javabean;

public class Workflow {
	String custom;
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getRoleflow() {
		return roleflow;
	}

	public void setRoleflow(String roleflow) {
		this.roleflow = roleflow;
	}

	public String getAccountflow() {
		return accountflow;
	}

	public void setAccountflow(String accountflow) {
		this.accountflow = accountflow;
	}

	private int id;
	private String name;
	private String roleflow;
	private String accountflow;
	private String content;
	private int status;
	private int account_id;
	private	String createtime;
	
	public Workflow() {
	}
	
}
