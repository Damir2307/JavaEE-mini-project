package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;

import main.dao.Model;
import main.dao.UserDAO;
import main.databean.User;
import main.formbean.LoginForm;
import main.formbean.RegisterForm;


public class Register extends Action {
    private FormBeanFactory<RegisterForm> formBeanFactory = new FormBeanFactory<>(RegisterForm.class);

    private UserDAO userDAO;

    public Register(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "register.do";
    }

    public String performGet(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "home.do";
        }

        // Otherwise, just display the login page.
        request.setAttribute("form", new RegisterForm());
    
        return "register.jsp";
    }

    public String performPost(HttpServletRequest request) {
        // If user is already logged in, redirect to todolist.do
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "home.do";
        }

        try {
            RegisterForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            // Any validation errors?
            if (form.hasValidationErrors()) {
                return "register.jsp";
            }
                User newUser = new User();
                newUser.setUserName(form.getUserName());
                newUser.setPassword(form.getPass2());
                newUser.setLastName(form.getlName());
                newUser.setFirstName(form.getfName());
                try {
                    userDAO.create(newUser);

                    session.setAttribute("user", newUser);
                    session.setAttribute("userFN", newUser.getFirstName());
                    session.setAttribute("userUN", newUser.getUserName());
                    return ("home.do");
                } catch (DuplicateKeyException e) {
                    form.addFieldError("userName", "A user with this name already exists");
                    return "register.jsp";
                }
            
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
