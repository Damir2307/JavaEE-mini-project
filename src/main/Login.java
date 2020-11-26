package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.RollbackException;

import main.dao.Model;
import main.dao.UserDAO;
import main.databean.User;
import main.formbean.LoginForm;


public class Login extends Action {
    private FormBeanFactory<LoginForm> formBeanFactory = new FormBeanFactory<>(LoginForm.class);

    private UserDAO userDAO;

    public Login(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "index.do";
    }

    public String performGet(HttpServletRequest request) throws RollbackException {
        // If user is already logged in, redirect to todolist.do
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "home.do";
        }

        // Otherwise, just display the login page.
        request.setAttribute("form", new LoginForm());
        request.setAttribute("users", userDAO.getUsers());
        
        return "index.jsp";
    }

    public String performPost(HttpServletRequest request) {
        // If user is already logged in, redirect to todolist.do
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return "home.do";
        }

        try {
            LoginForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);

            // Any validation errors?
            if (form.hasValidationErrors()) {
                return "index.jsp";
            }

            // Look up the user
            User user = userDAO.read(form.getUserName());

            if (user == null) {
                form.addFieldError("userName", "User Name not found");
                return "index.jsp";
            }

            // Check the password
            if (!user.getPassword().equals(form.getPassword())) {
                form.addFieldError("password", "Incorrect password");
                return "index.jsp";
            }

            // Attach (this copy of) the user bean to the session
            session.setAttribute("user", user);
            
            session.setAttribute("userFN", user.getFirstName());
            session.setAttribute("userLN", user.getLastName());
            session.setAttribute("userUN", user.getUserName());

            // If redirectTo is null, redirect to the "todolist" action
            return "home.do";
        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
