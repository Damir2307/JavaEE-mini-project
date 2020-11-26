package main.databean;


import org.genericdao.PrimaryKey;
@PrimaryKey("userName")
public class User {
    private String userName;	
    private int id;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String password;
    private String lastName;
	private String firstName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}