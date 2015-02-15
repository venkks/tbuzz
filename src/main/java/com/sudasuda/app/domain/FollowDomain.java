package com.sudasuda.app.domain;

import java.util.Date;

public class FollowDomain {

	private int id;
	private String domain;
	private int userId;
	private Date dateFollowed;
	private int enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDateFollowed() {
		return dateFollowed;
	}

	public void setDateFollowed(Date dateFollowed) {
		this.dateFollowed = dateFollowed;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

}
