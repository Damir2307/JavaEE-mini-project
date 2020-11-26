package main.dao;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import main.databean.CommentBean;



public class CommentDAO extends GenericDAO<CommentBean>{
	public CommentDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(CommentBean.class, tableName, cp);
	}
	
	public void addToTop(CommentBean content) throws RollbackException {
		try {
			Transaction.begin();

			// Get item at top of list
			CommentBean[] a = match(MatchArg.min("position"));

			CommentBean topBean;
			if (a.length == 0) {
				topBean = null;
			} else {
				topBean = a[0];
			}

			int newPos;
			if (topBean == null) {
				// List is empty...just add it with position = 1
				newPos = 1;
			} else {
				// Create the new item with position one less than the top
				// bean's position
				newPos = topBean.getPosition() - 1;
			}

			content.setPosition(newPos);

			// Create a new ItemBean in the database with the next id number
			// Note that GenericDAO.create() will use auto-increment when
			// the primary key field is an int or a long.
			create(content);

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	public CommentBean[] getComments() throws RollbackException {

		// Calls GenericDAO's match() method.
		// This no match constraint arguments, match returns all the Item beans
		CommentBean[] posts = match();
		
		Arrays.sort(posts, (CommentBean i1, CommentBean i2) -> i1.getPosition() - i2.getPosition());

		return posts;
	}
}
