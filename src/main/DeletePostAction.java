package main;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.CommentBean;
import main.databean.PostBean;
import main.databean.User;
import main.formbean.IdForm;
import main.formbean.Post;



public class DeletePostAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = new FormBeanFactory<>(IdForm.class);

    private PostDAO postDAO;
    private CommentDAO commentDAO;
    private UserDAO	userDAO;

    public DeletePostAction(Model model) {
        postDAO = model.getPostDAO();
        userDAO = model.getUserDAO();
        commentDAO = model.getCommentDAO();
    }

    public String getName() {
        return "postdelete.do";
    }

    public String performPost(HttpServletRequest request) {
        IdForm form = formBeanFactory.create(request);

        if (form.hasValidationErrors()) {
            return "error.jsp";
        }

        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            CommentBean[] comments = commentDAO.match(MatchArg.equals("postId",form.getIdAsInt()));
            for(CommentBean i:comments) {
            	commentDAO.delete(i.getId());
            }
            postDAO.delete(form.getIdAsInt());
            request.setAttribute("comments",commentDAO.getComments());
            request.setAttribute("posts", postDAO.getPosts(user.getUserName()));
            request.setAttribute("form", new Post());
            request.setAttribute("users", userDAO.getUsers());

            return "home.jsp";

        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
