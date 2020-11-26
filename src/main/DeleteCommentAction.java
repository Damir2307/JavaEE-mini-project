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



public class DeleteCommentAction extends Action {
    private FormBeanFactory<IdForm> formBeanFactory = new FormBeanFactory<>(IdForm.class);

    private CommentDAO commentDAO;
    private UserDAO	userDAO;
    private PostDAO postDAO;

    public DeleteCommentAction(Model model) {
        commentDAO = model.getCommentDAO();
        userDAO = model.getUserDAO();
        postDAO = model.getPostDAO();
    }

    public String getName() {
        return "commentdelete.do";
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
            String un = request.getParameter("UName");
            String ufn = request.getParameter("UFName");
            String uln = request.getParameter("ULName");
            request.setAttribute("UName", un);
            request.setAttribute("UFName", ufn);
            request.setAttribute("ULName", uln);
            request.setAttribute("posts", postDAO.getPosts(un));
            request.setAttribute("form", new CommentForm());
            request.setAttribute("comments",commentDAO.getComments());
            request.setAttribute("users", userDAO.getUsers());
            return "visitorpage.jsp";

        } catch (RollbackException e) {
            request.setAttribute("error", e.getMessage());
            return "error.jsp";
        }
    }
}
