package com.sudasuda.app.domain;

public class Link {

	private int linkId;

	private String url;
	private String title;
	private String submitedBy;
	private boolean voted;
	private long hoursElapsed;
	private int votes;
	private int noOfComments;
	private String domain;
	private String language;
	private String category;
	private String country;
	private String tags;
	private int spam;
	private int activists;

	public int getLinkId() {
		return linkId;
	}

	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getSubmitedBy() {
		return submitedBy;
	}

	public void setSubmitedBy(String submitedBy) {
		this.submitedBy = submitedBy;
	}

	public boolean isVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

	public long getHoursElapsed() {
		return hoursElapsed;
	}

	public void setHoursElapsed(long hoursElapsed) {
		this.hoursElapsed = hoursElapsed;
	}

	public int getNoOfComments() {
		return noOfComments;
	}

	public void setNoOfComments(int noOfComments) {
		this.noOfComments = noOfComments;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getSpam() {
		return spam;
	}

	public void setSpam(int spam) {
		this.spam = spam;
	}

	public int getActivists() {
		return activists;
	}

	public void setActivists(int activists) {
		this.activists = activists;
	}

}
