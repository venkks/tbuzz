package com.sudasuda.app.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBConnection {

	private static DataSource dataSource;

	public static DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource datasource) {
		this.dataSource = datasource;
	}

	/**
	 * public static DataSource getDataSource1() { ExampleDataSource ds = new
	 * ExampleDataSource();
	 * 
	 * String db_url =
	 * "jdbc:mysql://sudasuda.cblmxj25qyje.us-west-2.rds.amazonaws.com:3306/leaderread?useUnicode=true&characterEncoding=UTF-8"
	 * ; // String db_url = "jdbc:mysql://localhost:3306/leaderread"; String
	 * db_username = "venkk"; // String db_username="root"; String
	 * db_password="Karumbairam1"; //String db_password="venkk23";
	 * 
	 * try { Properties prop = new Properties(); //
	 * ds.setDriverClassName("com.mysql.jdbc.Driver"); ds.setUrl(db_url);
	 * ds.setUsername(db_username); ds.setPassword(db_password); // conn =
	 * DriverManager.getConnection(,"venkk","Karumbairam1"); // conn =
	 * DriverManager.getConnection(,"root","venkk23"); // stmt =
	 * conn.createStatement(); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * return ds; }
	 * 
	 * public static DataSource getDataSource() { ExampleDataSource ds = new
	 * ExampleDataSource();
	 * 
	 * // String db_url =
	 * "jdbc:mysql://sudasuda.cblmxj25qyje.us-west-2.rds.amazonaws.com:3306/leaderread?useUnicode=true&characterEncoding=UTF-8"
	 * ; String db_url = "jdbc:mysql://localhost:3306/leaderread"; // String
	 * db_username = "venkk"; String db_username="root"; // String
	 * db_password="Karumbairam1"; String db_password="venkk23";
	 * 
	 * try { Properties prop = new Properties(); //
	 * ds.setDriverClassName("com.mysql.jdbc.Driver"); ds.setUrl(db_url);
	 * ds.setUsername(db_username); ds.setPassword(db_password); // conn =
	 * DriverManager.getConnection(,"venkk","Karumbairam1"); // conn =
	 * DriverManager.getConnection(,"root","venkk23"); // stmt =
	 * conn.createStatement(); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * return ds; }
	 */

}
