package main.formbean;

import java.util.Date;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

public class Post extends FormBean {
	private String post;
	
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public Date getDate() {
    	Date date = new Date();
    	return date;
    }
	 public void validate() {
	        super.validate();
	        if (hasValidationErrors()) {
	            return;
	        }
	 }
}
