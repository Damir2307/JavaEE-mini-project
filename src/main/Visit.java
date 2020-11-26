package main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.User;
import main.formbean.CommentForm;
import main.formbean.Post;



public class Visit extends Action {


    private PostDAO postDAO;
    private UserDAO	userDAO;
    private CommentDAO commentDAO;

    public Visit(Model model) {
        postDAO = model.getPostDAO();
        userDAO = model.getUserDAO();
        commentDAO = model.getCommentDAO();
    }

    public String getName() {
        return "visit.do";
    }

    public String performPost(HttpServletRequest request) {
       
        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String un = request.getParameter("UName");
            String ufn = request.getParameter("UFName");
            String uln = request.getParameter("ULName");
            if((user.getUserName()).equals(un)) {
            	return "home.do";
            }
            
            request.setAttribute("UName", un);
            request.setAttribute("UFName", ufn);
            request.setAttribute("ULName", uln);
            request.setAttribute("posts", postDAO.getPosts(un));
            request.setAttribute("form", new CommentForm());
            request.setAttribute("comments", commentDAO.getComments());
            request.setAttribute("users", userDAO.getUsers());
            return "visitorpage.jsp";

        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
