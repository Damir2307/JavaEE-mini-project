package main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.formbeanfactory.FormBeanFactory;
import org.genericdao.RollbackException;

import main.dao.CommentDAO;
import main.dao.Model;
import main.dao.PostDAO;
import main.dao.UserDAO;
import main.databean.CommentBean;
import main.databean.User;
import main.formbean.CommentForm;
import main.formbean.IdForm;



public class CommentAction extends Action {
	private FormBeanFactory<CommentForm> formBeanFactory = new FormBeanFactory<>(CommentForm.class);
	private CommentDAO commentDAO;
	private UserDAO userDAO;
	private PostDAO postDAO;
	
    public CommentAction(Model model) {
        commentDAO = model.getCommentDAO();
        userDAO = model.getUserDAO();
        postDAO = model.getPostDAO();
    }

    public String getName() {
        return "comment.do";
    }
    public String performGet(HttpServletRequest request) {
    	return performPost(request);
    }
    
    public String performPost(HttpServletRequest request) {
        try {
        	HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            CommentForm form = formBeanFactory.create(request);
            if (form.hasValidationErrors()) {
                request.setAttribute("form", form);
                request.setAttribute("comments", commentDAO.getComments());
                return "visitorpage.jsp";
            }
            
            CommentBean bean = new CommentBean();
            bean.setContent(form.getComment());
            bean.setUserName(((User) request.getSession().getAttribute("user")).getUserName());
            bean.setDate(form.getDate());
            bean.setPostId(Integer.parseInt(form.getId()));
            commentDAO.addToTop(bean);
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
            request.setAttribute("error",e.getMessage());
            return "error.jsp";
        }
    }
}
