package main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.User;
import main.formbean.Post;

public class Blog extends Action {
    private PostDAO PostDAO;
    private UserDAO userDAO;
    private CommentDAO commentDAO;

    public Blog(Model model) {
        PostDAO = model.getPostDAO();
        userDAO = model.getUserDAO();
        commentDAO = model.getCommentDAO();
    }

    public String getName() {
        return "home.do";
    }
    
    public String performGet(HttpServletRequest request) {
        return performPost(request);
    }

    public String performPost(HttpServletRequest request) {
        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
 
            request.setAttribute("user", user);
            request.setAttribute("comments",commentDAO.getComments());
            request.setAttribute("posts", PostDAO.getPosts(user.getUserName()));
            request.setAttribute("form",  new Post());
            request.setAttribute("users", userDAO.getUsers());
            return ("home.jsp");
        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
    }
}
