package com.sudasuda.app.service;

import java.util.List;

import com.sudasuda.app.dao.UserDAO;
import com.sudasuda.app.domain.User;

public class UserService {

	UserDAO userDAO = new UserDAO();

	public boolean authenticate(String username, String password) {
		User user = userDAO.getUser(username);

		if (user.getPassword() != null && password != null
				&& password.equals(user.getPassword()))
			return true;

		return false;

	}

	public User getUser(String username) {
		User user = userDAO.getUser(username);
		return user;
	}

	public void addUser(String username, String email, String password) {
		userDAO.addUser(username, email, password);
	}

	public List<User> getTopPostersForDomain(String domain) {
		return userDAO.getTopPostersForDomain(domain);
	}

	public User findUUID(String uuid) {
		return userDAO.findUUID(uuid);
	}

	public void saveUUID(String uuid, String username) {
		userDAO.saveUUID(uuid, username);
	}

	public void deleteUUID(String username) {
		userDAO.deleteUUID(username);
	}
	
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

}
