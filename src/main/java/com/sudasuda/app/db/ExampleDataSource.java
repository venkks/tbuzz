package com.sudasuda.app.db;

/**
 * import java.io.PrintWriter; import java.sql.Connection; import
 * java.sql.DriverManager; import java.sql.SQLException; import
 * java.sql.SQLFeatureNotSupportedException; import java.util.logging.Logger;
 * 
 * import javax.sql.DataSource;
 * 
 * public class ExampleDataSource implements DataSource {
 * 
 * private String url; private String username; private String password;
 * 
 * @Override public PrintWriter getLogWriter() throws SQLException { // TODO
 *           Auto-generated method stub return null; }
 * @Override public int getLoginTimeout() throws SQLException { // TODO
 *           Auto-generated method stub return 0; }
 * @Override public Logger getParentLogger() throws
 *           SQLFeatureNotSupportedException { // TODO Auto-generated method
 *           stub return null; }
 * @Override public void setLogWriter(PrintWriter out) throws SQLException { //
 *           TODO Auto-generated method stub
 * 
 *           }
 * @Override public void setLoginTimeout(int seconds) throws SQLException { //
 *           TODO Auto-generated method stub
 * 
 *           }
 * @Override public boolean isWrapperFor(Class<?> iface) throws SQLException {
 *           // TODO Auto-generated method stub return false; }
 * @Override public <T> T unwrap(Class<T> iface) throws SQLException { // TODO
 *           Auto-generated method stub return null; }
 * @Override public Connection getConnection() throws SQLException {
 * 
 *           Connection conn =
 *           DriverManager.getConnection(getUrl(),getUsername(),getPassword());
 * 
 *           return conn; }
 * @Override public Connection getConnection(String username, String password)
 *           throws SQLException { // TODO Auto-generated method stub return
 *           null; }
 * 
 *           public String getUrl() { return url; }
 * 
 *           public void setUrl(String url) { this.url = url; }
 * 
 *           public String getUsername() { return username; }
 * 
 *           public void setUsername(String username) { this.username =
 *           username; }
 * 
 *           public String getPassword() { return password; }
 * 
 *           public void setPassword(String password) { this.password =
 *           password; }
 * 
 *           }
 **/
