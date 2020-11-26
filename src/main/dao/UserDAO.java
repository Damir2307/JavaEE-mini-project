package main.dao;



import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import main.databean.User;


public class UserDAO extends GenericDAO<User> {
	public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(User.class, tableName, cp);
	}
	public User[] getUsers() throws RollbackException {

		// Calls GenericDAO's match() method.
		// This no match constraint arguments, match returns all the Item beans
		User[] items = match();
		return items;
	}
}
