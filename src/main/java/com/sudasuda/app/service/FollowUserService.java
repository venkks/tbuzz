package com.sudasuda.app.service;

import java.util.List;

import com.sudasuda.app.dao.FollowUserDAO;
import com.sudasuda.app.domain.FollowUser;

public class FollowUserService {
	private FollowUserDAO followUserDAO = new FollowUserDAO();

	public List<FollowUser> getFollowUsers(int userId) {
		return followUserDAO.getFollowUsers(userId);
	}

	public boolean isAlreadyPresent(int userId, String followUserName) {
		return followUserDAO.isAlreadyPresent(userId, followUserName);
	}

	public boolean isFollowAllowed(int userId, String followUserName) {
		return followUserDAO.isFollowAllowed(userId, followUserName);
	}

	public void addFollowUser(int userId, String followUserName) {
		followUserDAO.addFollowUser(userId, followUserName);
	}

	public void removeFollowUser(int userId, String followUserName) {
		followUserDAO.removeFollowUser(userId, followUserName);
	}
}
