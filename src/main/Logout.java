package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import main.dao.Model;
import main.formbean.LoginForm;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class Logout extends Action {

    public Logout(Model model) {
    }

    public String getName() {
        return "logout.do";
    }

    public String performPost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);

        request.setAttribute("form", new LoginForm());
        return "index.jsp";
    }
}
