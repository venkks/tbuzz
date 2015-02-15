package com.sudasuda.app.service;

import java.util.List;

import com.sudasuda.app.dao.FollowDomainDAO;
import com.sudasuda.app.domain.FollowDomain;

public class FollowDomainService {

	private FollowDomainDAO followDomainDAO = new FollowDomainDAO();

	public List<FollowDomain> getFollowDomains(int userId) {
		return followDomainDAO.getFollowDomains(userId);
	}

	public void addFollowDomain(String domain, int userId) {
		followDomainDAO.addFollowDomain(domain, userId);
	}

	public boolean isFollowAllowed(String domain, int userId) {
		return followDomainDAO.isFollowAllowed(domain, userId);
	}

	public void removeFollowDomain(String domain, int userId) {
		followDomainDAO.removeFollowDomain(domain, userId);
	}

}
