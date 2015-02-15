package com.sudasuda.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sudasuda.app.db.DBConnection;
import com.sudasuda.app.domain.FollowUser;

public class FollowUserDAO {

	public List<FollowUser> getFollowUsers(int userId) {
		List<FollowUser> users = new ArrayList<FollowUser>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select (select username from user u where u.idUser="
					+ userId
					+ ") as username,f.* from follow_user f where f.enabled=1 and f.userId="
					+ userId + "";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				FollowUser followUser = new FollowUser();
				followUser.setUserId(rs.getInt("userId"));
				followUser.setFollowUserName(rs.getString("followUserName"));
				followUser.setDateFollowed(rs.getTimestamp("date_followed"));
				followUser.setEnabled(rs.getInt("enabled"));
				users.add(followUser);

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

		return users;
	}

	public boolean isAlreadyPresent(int userId, String followUserName) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "SELECT * from follow_user where followUserName='"
					+ followUserName + "' and userId=" + userId;
			System.out.println(SQL);
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

	public boolean isFollowAllowed(int userId, String followUserName) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "SELECT * from follow_user where followUserName='"
					+ followUserName + "' and userId=" + userId
					+ " and enabled=1";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			if (rs.next())
				return false;
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
		return true;
	}

	public void addFollowUser(int userId, String followUserName) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO follow_user VALUES(0," + userId + ",'"
					+ followUserName + "',now(),1)";
			if (!isAlreadyPresent(userId, followUserName)) {
				System.out.println("SQL:" + sql);
				stmt.executeUpdate(sql);
			}

			else {
				String SQL = "update follow_user set enabled=1 where userId="
						+ userId + " and followUserName='" + followUserName
						+ "'";
				System.out.println("SQL:" + SQL);
				stmt.executeUpdate(SQL);
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

	}

	public void removeFollowUser(int userId, String followUserName) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "update follow_user set enabled=0 where userId="
					+ userId + " and followUserName='" + followUserName + "'";
			System.out.println(SQL);
			stmt.executeUpdate(SQL);
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

}
