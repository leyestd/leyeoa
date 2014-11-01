package frontend.javabean;

public class Delegate {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getDelegateId() {
		return delegateId;
	}
	public void setDelegateId(int delegateId) {
		this.delegateId = delegateId;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	int id;
	int accountId;
	int delegateId;
	int enabled;
	String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
