package com.sudasuda.app.service;

import java.util.List;

import com.sudasuda.app.dao.CommentDAO;
import com.sudasuda.app.domain.Comment;
import com.sudasuda.app.domain.User;

public class CommentService {

	CommentDAO commentDAO = new CommentDAO();

	public List<Comment> getComments(int linkId, User user) {
		return commentDAO.getComments(linkId, user);
	}

	public void addComment(int userId, int linkId, String text) {
		commentDAO.addComment(userId, linkId, text);
	}
	
	public void voteUpComment(int commentId, int userid) {
		commentDAO.voteUpComment(commentId, userid);
	}

}
