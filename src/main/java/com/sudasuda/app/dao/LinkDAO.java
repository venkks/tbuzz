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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sudasuda.app.controller.HomeController;
import com.sudasuda.app.db.DBConnection;
import com.sudasuda.app.domain.Link;
import com.sudasuda.app.domain.User;
import com.sudasuda.app.vo.NameCountVO;

public class LinkDAO {

	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(LinkDAO.class);

	public void addLink(String url, String title, String userid,
			String language, String category, String country,
			String media_type, String tags[]) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			URI uri = new URI(url);
			String domain = uri.getHost();
			domain = domain.startsWith("www.") ? domain.substring(4) : domain;

			System.out
					.println("title==>" + new String(title.getBytes("UTF-8")));
			title = title.replace("'", " ");
			String SQL = "insert into link values (0," + userid + ",'" + url
					+ "','" + title + "',now(),0,0,'" + domain + "',0,'"
					+ language + "','" + category + "','" + country + "','"
					+ media_type + "')";
			System.out.println("SQL:" + SQL);
			stmt.executeUpdate(SQL);

			Link link = getLink(url);

			if (link.getLinkId() > 0) {
				for (String tag : tags) {
					applyTag(tag, link.getLinkId());
				}
				voteUpLink(link.getLinkId(), Integer.valueOf(userid));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public List<Link> getCurrentLinks(int currUser, String lang,
			String category, String country) {
		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			// String SQL =
			// "select *,b.username from link a, user b where a.userId=b.idUser order by a.votes desc, a.date_created";
			String SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.* from link a left join link_user c on a.idlink=c.linkId and c.userId="
					+ currUser + " order by a.votes desc, a.date_created";

			if (lang == null || category == null || country == null)
				return links;

			// No filter scenario

			if (lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v,"
						+ " (select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by Language

			if (!lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v"
						+ ",(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId"
						+ " where language='"
						+ lang.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by category

			if (lang.equalsIgnoreCase("all") && country.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*,(select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v, "
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where category='"
						+ category.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by country

			if (lang.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v,"
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where country='"
						+ country.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by language and category

			if (!lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct * from (  select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v, "
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where language='"
						+ lang.trim()
						+ "' and category='"
						+ category.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by language and country

			if (!lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v,"
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where language='"
						+ lang.trim()
						+ "' and country='"
						+ country.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by category and country

			if (lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v,"
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where category='"
						+ category.trim()
						+ "' and country='"
						+ country.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			// Filter by language, category and country

			if (!lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct * from ( select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, (select userId from link_user lu where lu.linkId=a.idlink and lu.userId = "+currUser+" ) as voted,  (a.votes-1)/pow(timestampdiff(HOUR, a.date_created, now())+2,1.8) as v,"
						+ "(select count(distinct byUserId) from comment where linkid=a.idlink) as activists,"
						+ "timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId "
						+ " where language='"
						+ lang.trim()
						+ "' and category='"
						+ category.trim()
						+ "' and country='"
						+ country.trim()
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) < 100000 ) as mq order by v desc";

			System.out.println(SQL);

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setSubmitedBy(rs.getString("username"));
				link.setVoted((rs.getString("voted") != null));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setNoOfComments(rs.getInt("comments"));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
				link.setActivists(rs.getInt("activists"));
				links.add(link);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return links;
	}

	public List<Link> getNewLinks(int currUser, String language,
			String category, String country) {
		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			// String SQL =
			// "select *,b.username from link a, user b where a.userId=b.idUser order by a.votes desc, a.date_created";
			String SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted from link a left join link_user c on a.idlink=c.linkId and c.userId="
					+ currUser + " order by a.votes desc, a.date_created";

			if (language == null || category == null || country == null)
				return links;

			// No filter scenario

			if (language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Language only filter

			if (!language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and language='"
						+ language
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Category filter

			if (language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and category='"
						+ category
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Country filter

			if (language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and country='"
						+ country
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Language, Category and Country filter

			if (!language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and category='"
						+ category
						+ "' and language='"
						+ language
						+ "' and country='"
						+ country
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Category and Language filter

			if (!language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and category='"
						+ category
						+ "' and language='"
						+ language
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Category and country filter

			if (language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and category='"
						+ category
						+ "' and country='"
						+ country
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			// Language and Country

			if (!language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and language='"
						+ language
						+ "' and country='"
						+ country
						+ "' and votes=1 and timestampdiff(HOUR, a.date_created, now()) <= 100 order by a.date_created desc";

			System.out.println(SQL);

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setSubmitedBy(rs.getString("username"));
				link.setVoted((rs.getString("voted") != null));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setNoOfComments(rs.getInt("comments"));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
				links.add(link);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return links;
	}

	public List<Link> getExpiredLinks(int currUser, String language,
			String category, String country) {
		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			// String SQL =
			// "select *,b.username from link a, user b where a.userId=b.idUser order by a.votes desc, a.date_created";
			String SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username, (select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted from link a left join link_user c on a.idlink=c.linkId and c.userId="
					+ currUser + " order by a.votes desc, a.date_created";

			if (language == null || category == null || country == null)
				return links;

			// No filter return everything

			if (language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by language

			if (!language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where language='"
						+ language
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by category

			if (language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where category='"
						+ category
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by country

			if (language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where country='"
						+ country
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by country and language

			if (!language.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where country='"
						+ country
						+ "' and language='"
						+ language
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by country and category

			if (language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where country='"
						+ country
						+ "' and category='"
						+ category
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by language and category

			if (!language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where language='"
						+ language
						+ "' and category='"
						+ category
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			// Filter by language, category and country

			if (!language.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all"))
				SQL = "select distinct (select b.username from user b where a.userId=b.idUser) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags, a.*, c.userId as voted, a.votes as v,timestampdiff(HOUR, a.date_created, now()) as hours from link a left join link_user c on a.idlink=c.linkId and c.userId="
						+ currUser
						+ " where language='"
						+ language
						+ "' and category='"
						+ category
						+ "' and country='"
						+ country
						+ "' and spam < 4 and timestampdiff(HOUR, a.date_created, now()) > 100000 order by a.date_created desc limit 0,25";

			System.out.println(SQL);

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setSubmitedBy(rs.getString("username"));
				link.setVoted((rs.getString("voted") != null));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setNoOfComments(rs.getInt("comments"));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				links.add(link);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return links;
	}

	public Link getLink(int linkId) {
		Link link = new Link();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select (select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=a.idlink and t.tag_id=lt.tag_id) as tags,a.* from link a where a.idlink="
					+ linkId;

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setNoOfComments(rs.getInt("comments"));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return link;
	}

	public void voteUpLink(int linkid, int userid) {
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			String SQL1 = "update link set votes = votes+1 where idlink= ?";
			String SQL2 = "insert into link_user values (0,?,?,now())";

			System.out.println("SQL:" + SQL1);
			System.out.println("SQL:" + SQL2);

			if (!isVotedAlready(userid, linkid)) {
				pstmt1 = conn.prepareStatement(SQL1);

				pstmt1.setInt(1, linkid);
				pstmt1.executeUpdate();

				pstmt2 = conn.prepareStatement(SQL2);
				pstmt2.setInt(1, linkid);
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

	public boolean isVotedAlready(int userId, int linkId) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from link_user where userId=" + userId
					+ " and linkId=" + linkId;
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

	public boolean isLinkPresent(String url) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from link where url='" + url + "'";
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

	public int isTagPresent(String tagName) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from tag where tag_name='" + tagName + "'";
			rs = stmt.executeQuery(SQL);

			if (rs.next())
				return rs.getInt("tag_id");

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
		return -1;
	}

	public Link getLink(String url) {
		Link link = new Link();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from link where url='" + url + "'";
			rs = stmt.executeQuery(SQL);

			if (rs.next()) {
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setNoOfComments(rs.getInt("comments"));
				link.setDomain(rs.getString("domain"));
				link.setLanguage(rs.getString("language"));
				link.setCategory(rs.getString("category"));
				link.setCountry(rs.getString("country"));
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

		return link;
	}
	
	public List<String> getRecentlySubmittedUsers() {
		List<String> usernames = new ArrayList<String>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select distinct * from  ( select  (select username from user u where u.iduser=l.userid) as username from link l  order by l.date_created desc  ) as b limit 0,5 ";
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				usernames.add(rs.getString("username"));
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

		return usernames;
	}

	public List<Link> getSubmittedByLinks(int submittedBy) {
		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			String SQL = "select *, (select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=idlink and t.tag_id=lt.tag_id) as tags,timestampdiff(HOUR, date_created, now()) as hours from link where userId=?"
					+ " order by date_created desc limit 0,25";
			logger.info("Submitted by SQL:" + SQL);

			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, submittedBy);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				// link.setSubmitedBy(rs.getString("username"));
				// link.setVoted((rs.getString("voted")!=null));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				link.setNoOfComments(rs.getInt("comments"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
				links.add(link);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return links;
	}

	public List<NameCountVO> getCategoryLinkCount(int userId, int type, String cond) {
		List<NameCountVO> links = new ArrayList<NameCountVO>();

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			// by tag:
			// select t.tag_name,count(t.tag_name) as cnt from link_tag lt, tag
			// t, link l where l.idlink=lt. and lt.tag_id=t.tag_id group
			// by t.tag_name order by cnt desc limit 0,5,

			String SQL = "select concat(category, ' (', group_concat(distinct l.country separator ','),')' ) as category, count(distinct l.idlink) as count from link l, link_user lu where l.idlink=lu.linkId and lu.userId="
					+ userId + " and spam < 4 group by category";

			if ( userId > 0 && type == 1 )
				SQL = "select concat(domain,' (', group_concat(distinct language separator ','),')' ) as domain, count(*) as count from link l, link_user lu  where l.idlink=lu.linkId and lu.userId="+userId+" and spam < 4  group by domain order by count desc limit 0,10";
			 
			if ( userId > 0 && type == 3 )
				SQL = "select category, count(*) as count from link l, link_user lu where l.idlink=lu.linkId and lu.userId="+userId+" and domain='"+cond+"' and spam < 4 group by category order by count desc";
			
			// For homepage userId will be -1

			if (userId == -1)
				SQL = "select category, count(*) from link where spam < 4 and timestampdiff(HOUR, date_created, now()) < 500 group by category";

			// For breakdown by language (homepage)

			if (userId == -2)
				SQL = "select language, count(*) from link where spam < 4 and timestampdiff(HOUR, date_created, now()) < 500 group by language";

			// Breakdown by country for homepage

			if (userId == -3)
				SQL = "select country, count(*) from link where spam < 4 and timestampdiff(HOUR, date_created, now()) < 500 group by country";

			if (userId == -4)
				SQL = "select domain, count(*) from link where spam < 4 and timestampdiff(HOUR, date_created, now()) < 500 group by domain";
			
			logger.info("SQL:" + SQL);

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				NameCountVO countVO = new NameCountVO();
				countVO.setCategory(rs.getString(1)); // category column
				countVO.setCount(rs.getInt(2));
				links.add(countVO);
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
		return links;
	}

	public List<Link> getDomainLinks(String domain) {

		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			String SQL = "select (select username from user where user.idUser=l.userId) as username, (select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l where domain = ?"
					+ " order by date_created desc limit 0,25";
			logger.info("Domain Links SQL: " + SQL);

			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, domain);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setSubmitedBy(rs.getString("username"));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setNoOfComments(rs.getInt("comments"));
				link.setCountry(rs.getString("country"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
				links.add(link);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return links;

	}

	@SuppressWarnings("resource")
	public List<Link> getMyLinks(int userId, String lang, String category,
			String country) {

		List<Link> links = new ArrayList<Link>();

		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			String SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and l.idlink=lu.linkId order by l.date_created desc limit 0,20";
			logger.info("My Links SQL: " + SQL);

			if (lang == null || category == null || country == null)
				return links;

			if (lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and l.idlink=lu.linkId order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				
			}
			// Filter by Language

			if (!lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.language=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, lang);
			}

			// Filter by category

			if (lang.equalsIgnoreCase("all") && country.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.category=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, category);

			}

			// Filter by country

			if (lang.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.country=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, country);

			}

			// Filter by language and category

			if (!lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.language=? and l.category=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, lang);
				pstmt.setString(3, category);
			}

			// Filter by language and country

			if (!lang.equalsIgnoreCase("all")
					&& category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.language=? and l.country=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, lang);
				pstmt.setString(3, country);
			}
			
			// Filter by category and country

			if (lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.category=? and l.country=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, category);
				pstmt.setString(3, country);
			}

			// Filter by language, category and country
			
			if (!lang.equalsIgnoreCase("all")
					&& !category.equalsIgnoreCase("all")
					&& !country.equalsIgnoreCase("all")) {
				SQL = "select distinct (select username from user where idUser=l.userId) as username,(select group_concat(tag_name separator ',') from tag t, link_tag lt where lt.link_id=l.idlink and t.tag_id=lt.tag_id) as tags,l.*,timestampdiff(HOUR, l.date_created, now()) as hours from link l, link_user lu where lu.userId=? and lu.linkId=l.idlink and l.languge=? and l.category=? and l.country=? order by l.date_created desc limit 0,20";
				pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, userId);
				pstmt.setString(2, lang);
				pstmt.setString(3, category);
				pstmt.setString(4, country);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Link link = new Link();
				link.setUrl(rs.getString("url"));
				link.setTitle(rs.getString("title"));
				link.setLinkId(rs.getInt("idlink"));
				link.setVotes(rs.getInt("votes"));
				link.setSubmitedBy(rs.getString("username"));
				link.setHoursElapsed((rs.getLong("hours")));
				link.setDomain(rs.getString("domain"));
				link.setCategory(rs.getString("category"));
				link.setLanguage(rs.getString("language"));
				link.setCountry(rs.getString("country"));
				link.setTags(rs.getString("tags"));
				link.setSpam(rs.getInt("spam"));
				link.setVoted(true);
				links.add(link);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return links;

	}

	public List<NameCountVO> getMyTags(int userId) {

		List<NameCountVO> tags = new ArrayList<NameCountVO>();

		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			String SQL = "select t.*, count(lt.tag_id) as tag_count from link_tag lt, link_user lu, tag t where lt.link_id = lu.linkId and t.tag_id=lt.tag_id and lu.userId=? group by lt.tag_id order by tag_count desc";
			logger.info("My Links SQL: " + SQL);

			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NameCountVO countVO = new NameCountVO();
				countVO.setCategory(rs.getString("tag_name"));
				countVO.setCount(rs.getInt("tag_count"));
				tags.add(countVO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tags;

	}

	public boolean isSpam(int linkId, int userId) {
		boolean spam = false;

		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ds = DBConnection.getDataSource();
			conn = ds.getConnection();
			stmt = conn.createStatement();

			String SQL = "select * from spam where linkId=" + linkId
					+ " and userId=" + userId;
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

		return spam;

	}

	public void addSpam(int linkId, User user) {
		DataSource ds = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			if (!isSpam(linkId, user.getUserId())) {
				ds = DBConnection.getDataSource();
				conn = ds.getConnection();
				stmt = conn.createStatement();

				String SQL = "insert into spam values(" + user.getUserId()
						+ "," + linkId + ",'" + user.getUserName() + "')";
				String SQL1 = "update link set spam=spam+1 where idlink="
						+ linkId;
				stmt.addBatch(SQL);
				stmt.addBatch(SQL1);
				stmt.executeBatch();
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

	public void applyTag(String tagName, int linkId) {
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs3 = null;

		if (tagName == null || tagName.equalsIgnoreCase("null")
				|| tagName.trim().length() == 0)
			return;

		try {

			ds = DBConnection.getDataSource();
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			String SQL1 = "insert into tag values(0,?)";
			String SQL2 = "insert into link_tag values (0,?,?)";
			String SQL3 = "select * from tag where tag_name = ?";

			logger.info("SQL1:" + SQL1);
			logger.info("SQL2:" + SQL2);
			logger.info("SQL3:" + SQL3);

			int tagId = isTagPresent(tagName);

			if (tagId == -1) {
				pstmt1 = conn.prepareStatement(SQL1);
				pstmt1.setString(1, tagName);
				pstmt1.executeUpdate();

				pstmt3 = conn.prepareStatement(SQL3);
				pstmt3.setString(1, tagName);
				rs3 = pstmt3.executeQuery();

				if (rs3.next()) {
					tagId = rs3.getInt("tag_id");
				}

			}

			if (tagId != -1) {
				pstmt2 = conn.prepareStatement(SQL2);
				pstmt2.setInt(1, linkId);
				pstmt2.setInt(2, tagId);
				pstmt2.executeUpdate();
			}
			conn.commit();

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
				if (pstmt3 != null)
					pstmt3.close();
				if (rs3 != null)
					rs3.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
