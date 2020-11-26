package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.PostBean;
import main.databean.User;
import main.formbean.Post;


public class PostAction extends Action {
	private FormBeanFactory<Post> formBeanFactory = new FormBeanFactory<>(Post.class);
	
	private PostDAO postDAO;
	private UserDAO userDAO;
	private CommentDAO commentDAO;
    public PostAction(Model model) {
        postDAO = model.getPostDAO();
        userDAO = model.getUserDAO();
        commentDAO = model.getCommentDAO();
    }

    public String getName() {
        return "post.do";
    }
    
    public String performPost(HttpServletRequest request) {
        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            Post form = formBeanFactory.create(request);
            if (form.hasValidationErrors()) {
                request.setAttribute("form", form);
                request.setAttribute("posts", postDAO.getPosts(user.getUserName()));
                return "home.jsp";
            }
            
            PostBean bean = new PostBean();
            bean.setContent(form.getPost());
            bean.setUserName(((User) request.getSession().getAttribute("user")).getUserName());
            bean.setDate(form.getDate());
            postDAO.addToTop(bean);
            request.setAttribute("comments",commentDAO.getComments());
            request.setAttribute("form", new Post());
            request.setAttribute("posts", postDAO.getPosts(user.getUserName()));
            request.setAttribute("users", userDAO.getUsers());

            return "home.jsp";

        } catch (RollbackException e) {
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
    }
}
