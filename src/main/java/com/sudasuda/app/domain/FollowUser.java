package com.sudasuda.app.domain;

import java.util.Date;

public class FollowUser {

	private int id;
	private int userId;
	private String followUserName;
	private Date dateFollowed;
	private int enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFollowUserName() {
		return followUserName;
	}

	public void setFollowUserName(String followUserName) {
		this.followUserName = followUserName;
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
