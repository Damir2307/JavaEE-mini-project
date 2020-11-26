package main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.User;
import main.formbean.CommentForm;
import main.formbean.IdForm;



public class DeleteHomeCommentAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = new FormBeanFactory<>(IdForm.class);

    private CommentDAO commentDAO;
    private UserDAO	userDAO;
    private PostDAO postDAO;

    public DeleteHomeCommentAction(Model model) {
        commentDAO = model.getCommentDAO();
        userDAO = model.getUserDAO();
        postDAO = model.getPostDAO();
    }

    public String getName() {
        return "hcommentdelete.do";
    }

    public String performPost(HttpServletRequest request) {
        IdForm form = formBeanFactory.create(request);

        if (form.hasValidationErrors()) {
            return "error.jsp";
        }

        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            commentDAO.delete(form.getIdAsInt());
            request.setAttribute("user", user);
            request.setAttribute("posts", postDAO.getPosts(user.getUserName()));
            request.setAttribute("form", new CommentForm());
            request.setAttribute("comments",commentDAO.getComments());
            request.setAttribute("users", userDAO.getUsers());
            return "home.jsp";

        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
