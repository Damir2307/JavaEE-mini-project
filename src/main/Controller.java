package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import main.dao.Model;
import main.databean.User;


public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());

        Action.add(new PostAction(model));
        Action.add(new Blog(model));
        Action.add(new Login(model));
        Action.add(new Logout(model));
        Action.add(new Register(model));
        Action.add(new DeletePostAction(model));
        Action.add(new Visit(model));
        Action.add(new CommentAction(model));
        Action.add(new DeleteCommentAction(model));
        Action.add(new DeleteHomeCommentAction(model));

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = null;
		try {
			nextPage = performTheAction(request);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) throws RollbackException {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        User user = (User) session.getAttribute("user");
        String action = getActionName(servletPath);

        if (user != null) {
            // Let the logged in user run his chosen action
            return Action.perform(action, request);
        }
        
        // If the user hasn't logged in, login is the only option
        // Note in this example, register is in the login action.
        if (action.equals("index.do")) {
            return Action.perform("index.do", request);
        }
        if (action.equals("register.do")) {
            return Action.perform("register.do", request);
        }
        
        // The not-logged user is trying to execute an action other
        // than login.  This is usually due to his session timing
        // out but he could be fooling around.  Let's give him a
        // stale session error message.  Another option would be
        // to send him to login.jsp, instead.
        return "controller-stale-session.jsp";
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
            d.forward(request, response);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
