package com.sudasuda.app.service;

import java.util.ArrayList;
import java.util.List;

import com.sudasuda.app.dao.LinkDAO;
import com.sudasuda.app.domain.Link;
import com.sudasuda.app.domain.User;
import com.sudasuda.app.vo.NameCountVO;

public class LinkService {

	LinkDAO linkDAO = new LinkDAO();

	public List<Link> getLinks(int currUser, String language, String category, String country) {
		List<Link> links = linkDAO
				.getCurrentLinks(currUser, language, category, country);

		return links;
	}

	public List<Link> getNewLinks(int currUser, String language, String category, String country) {
		List<Link> links = linkDAO.getNewLinks(currUser, language, category, country);

		return links;
	}

	public List<Link> getExpiredLinks(int currUser, String language,
			String category, String country) {
		List<Link> links = linkDAO
				.getExpiredLinks(currUser, language, category, country);

		return links;
	}

	public void voteUpLink(int linkid, int userid) {
		linkDAO.voteUpLink(linkid, userid);
	}

	public void addLink(String url, String title, String userId,
			String language, String category, String country, String media_type, String[] tags) {
		linkDAO.addLink(url, title, userId, language, category, country, media_type, tags);
	}

	public boolean isLinkPresent(String url) {
		return linkDAO.isLinkPresent(url);
	}
	
	public List<NameCountVO> getCategoryLinkCount(int userId, int type, String cond) {
		return linkDAO.getCategoryLinkCount(userId,type,cond);
	}

	public List<Link> getSubmittedByLinks(int submittedBy) {
		List<Link> links = linkDAO.getSubmittedByLinks(submittedBy);

		return links;
	}

	public Link getLink(int linkId) {
		return linkDAO.getLink(linkId);
	}

	public List<Link> getDomainLinks(String domain) {
		return linkDAO.getDomainLinks(domain);
	}

	public void addSpam(int linkId, User user) {
		linkDAO.addSpam(linkId, user);
	}
	
	public void applyTag(String tagName, int linkId) {
		linkDAO.applyTag(tagName, linkId);
	}
	
	public List<Link> getMyLinks(int userId, String lang, String category, String country)
	{
		return linkDAO.getMyLinks(userId, lang, category, country);
	}

	public List<NameCountVO> getMyTags(int userId) {
		return linkDAO.getMyTags(userId);
	}
	
	public List<String> getRecentSubmitters() {
		return linkDAO.getRecentlySubmittedUsers();
	}
}
