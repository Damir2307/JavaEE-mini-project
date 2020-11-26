package main.formbean;

import java.util.Date;

import org.formbeanfactory.FieldOrder;
import org.formbeanfactory.FormBean;
import org.formbeanfactory.InputType;

public class CommentForm extends FormBean {
	private String comment;
	private String id;
	
	public String getId() {
		return id;
	}
	@InputType("hidden")
	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}
	
	@InputType("text")
	public void setComment(String comment) {
		this.comment = comment;
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
