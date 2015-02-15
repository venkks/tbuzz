package com.sudasuda.app.dao;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sudasuda.app.db.DBConnection;
import com.sudasuda.app.domain.Comment;
import com.sudasuda.app.domain.User;

public class CommentDAO {

	public List<Comment> getComments(int linkId, User user) {
		List<Comment> comments = new ArrayList<Comment>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select (select username from user u where u.idUser=c.byUserId) as username,c.*, uc.user_id from comment c left outer join comment_user uc on uc.user_id = "+user.getUserId()+" and uc.comment_id=c.idcomment where c.linkId="
					+ linkId + "";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("idcomment"));
				comment.setByUserId(rs.getInt("byUserId"));
				comment.setDate_created(rs.getDate("date_created"));
				comment.setText(rs.getString("text"));
				comment.setLinkId(rs.getInt("linkId"));
				comment.setByUser(rs.getString("username"));
				comment.setVotes(rs.getInt("votes"));
				comment.setVoted((rs.getString("user_id") != null));
				comments.add(comment);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return comments;
	}

	public void addComment(int userId, int linkId, String text) {

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "insert into comment values (0,'" + text + "',"
					+ userId + ",now()," + linkId + ", 1)";
			String SQL1 = "update link set comments=comments+1 where idlink="
					+ linkId;
			
			System.out.println("SQL:" + SQL);
			stmt.addBatch(SQL);
			stmt.addBatch(SQL1);
			stmt.executeBatch();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void voteUpComment(int commentId, int userid) {
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			String SQL1 = "update comment set votes = votes+1 where idcomment= ? and byUserId != "+userid;
			String SQL2 = "insert into comment_user values (0,?,?,now())";

			System.out.println("SQL:" + SQL1);
			System.out.println("SQL:" + SQL2);

			if (!isVotedAlready(userid, commentId)) {
				pstmt1 = conn.prepareStatement(SQL1);

				pstmt1.setInt(1, commentId);
				pstmt1.executeUpdate();

				pstmt2 = conn.prepareStatement(SQL2);
				pstmt2.setInt(1, commentId);
				pstmt2.setInt(2, userid);
				pstmt2.executeUpdate();

				conn.commit();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isVotedAlready(int userId, int commentId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from comment_user where user_id=" + userId
					+ " and comment_id=" + commentId;
			rs = stmt.executeQuery(SQL);

			if (rs.next())
				return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
