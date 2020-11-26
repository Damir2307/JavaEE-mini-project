package main.formbean;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;
@FieldOrder("userName,password")
public class LoginForm extends FormBean {
	private String userName;
    private String password;
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	@InputType("password")
	public void setPassword(String password) {
		this.password = password;
	}
	public void validate() {
        super.validate();
        if (hasValidationErrors()) {
            return;
        }
        if (userName.matches(".*[<>\"].*")) {
            this.addFieldError("userName", "May not contain angle brackets or quotes");
        }
    }
}