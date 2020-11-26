package main.formbean;

import org.formbeanfactory.FormBean;

public class DeleteForm extends FormBean{
	private String id;
	private String visitor;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVisitor() {
		return visitor;
	}
	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}
	public int getIdAsInt() {
        // The call validate() to ensures that errors will be detected before
        //  NullPointerException or NumberFormatException are thrown!
        return Integer.parseInt(id);
    }
	public void validate() {
        super.validate();

        if (hasValidationErrors()) {
            return;
        }

        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            this.addFormError("Id is not an integer");
        }
    }
	
}
