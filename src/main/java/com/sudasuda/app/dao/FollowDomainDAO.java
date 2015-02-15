package com.sudasuda.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sudasuda.app.db.DBConnection;
import com.sudasuda.app.domain.Comment;
import com.sudasuda.app.domain.FollowDomain;

public class FollowDomainDAO {

	public List<FollowDomain> getFollowDomains(int userId) {
		List<FollowDomain> domains = new ArrayList<FollowDomain>();

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
					+ ") as username,f.* from follow_domain f where f.enabled=1 and f.userId="
					+ userId + "";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				FollowDomain domain = new FollowDomain();
				domain.setDomain(rs.getString("domain"));
				domain.setId(rs.getInt("idFollow"));
				domain.setEnabled(rs.getInt("enabled"));
				domain.setDateFollowed(rs.getTimestamp("date_followed"));
				domain.setUserId(rs.getInt("userId"));
				domains.add(domain);
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

		return domains;
	}

	public void addFollowDomain(String domain, int userId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO follow_domain VALUES(0,'" + domain + "',"
					+ userId + ",now(),1)";
			if (!isAlreadyPresent(domain, userId)) {
				System.out.println("SQL:" + sql);
				stmt.executeUpdate(sql);
			}

			else {
				String SQL = "update follow_domain set enabled=1 where userId="
						+ userId + " and domain='" + domain + "'";
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

	public boolean isFollowAllowed(String domain, int userId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "SELECT * from follow_domain where domain='" + domain
					+ "' and userId=" + userId + " and enabled=1";
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

	public boolean isAlreadyPresent(String domain, int userId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "SELECT * from follow_domain where domain='" + domain
					+ "' and userId=" + userId;
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

	public void removeFollowDomain(String domain, int userId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "update follow_domain set enabled=0 where userId="
					+ userId + " and domain='" + domain + "'";
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
