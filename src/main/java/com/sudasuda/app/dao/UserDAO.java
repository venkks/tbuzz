package com.sudasuda.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sudasuda.app.db.DBConnection;
import com.sudasuda.app.domain.Link;
import com.sudasuda.app.domain.User;

public class UserDAO {

	public User getUser(String username) {

		User user = new User();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from user where username='" + username + "'";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("idUser"));
				user.setPassword(rs.getString("password"));
				user.setUserName(rs.getString("username"));
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
		return user;
	}

	public User getUserByEmail(String email) {

		User user = new User();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from user where lower(email)='" + email.toLowerCase() + "'";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("idUser"));
				user.setPassword(rs.getString("password"));
				user.setUserName(rs.getString("username"));
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
		return user;
	}

	
	public void addUser(String username, String email, String password) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;

		String SQL = "insert into user values (0,'" + username + "','" + email
				+ "','" + password + "')";
		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			stmt.executeUpdate(SQL);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<User> getTopPostersForDomain(String domain) {

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<User> users = new ArrayList<User>();

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select distinct l.userId,u.* from link l, user u where l.userId=u.idUser and domain='"
					+ domain + "' order by votes desc limit 0,5";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("idUser"));
				// user.setPassword(rs.getString("password"));
				user.setUserName(rs.getString("username"));
				users.add(user);
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

	public User findUUID1(String uuid) {
		User user = null;

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			String SQL = "select * from user where uuid='" + uuid + "'";
			System.out.println(SQL);

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				user = new User();
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("idUser"));
				user.setPassword(rs.getString("password"));
				user.setUserName(rs.getString("username"));
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

		return user;
	}

	public User findUUID(String uuid) {
		User user = null;

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			// conn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/leaderread",
			// "root", "venkk23");
			stmt = conn.createStatement();

			String SQL = "select *,b.* from user_uuid a, user b where a.	uuid='"
					+ uuid + "' and a.username=b.username";
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				user = new User();
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("idUser"));
				user.setPassword(rs.getString("password"));
				user.setUserName(rs.getString("username"));
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
		return user;
	}

	public void saveUUID(String uuid, String username) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String SQL = "update user set uuid='" + uuid
					+ "',last_updated=now() where username='" + username + "'";

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			if (findUUID(uuid) == null)
				SQL = "insert into user_uuid values('" + uuid + "','"
						+ username + "',now())";
			stmt.executeUpdate(SQL);
			System.out.println("SQL: " + SQL);
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

	public void deleteUUID(String username) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "delete from user_uuid where username='" + username
					+ "'";
			System.out.println("SQL:" + SQL);
			stmt.executeUpdate(SQL);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
