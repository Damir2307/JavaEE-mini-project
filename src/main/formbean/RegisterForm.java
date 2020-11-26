package main.formbean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;
import org.formbeanfactory.Label;

@FieldOrder("userName,fName,lName,pass1,pass2")
public class RegisterForm extends FormBean {
	private String userName;
	private String pass2;
    private String pass1;
    private String fName;
    private String lName;
    private static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    
    public String getUserName() {
		return userName;
	}
    @Label("Email")
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass2() {
		return pass2;
	}
	@InputType("password")
	@Label("Confirm password")
	public void setPass2(String pass2) {
		this.pass2 = pass2;
	}
	public String getPass1() {
		return pass1;
	}
	@InputType("password")
	@Label("Password")
	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}
	public String getfName() {
		return fName;
	}
	@Label("First Name")
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	@Label("Last Name")
	public void setlName(String lName) {
		this.lName = lName;
	}

    public void validate() {
        super.validate();
        if (hasValidationErrors()) {
            return;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        if(!matcher.matches()) {
        	this.addFormError("Invalid email");
        }
        if(!pass1.equals(pass2)) {
        	this.addFormError("Passwords are not same");
        }
        
        if (userName.matches(".*[<>\"].*")) {
            this.addFieldError("userName", "May not contain angle brackets or quotes");
        }
    }

}