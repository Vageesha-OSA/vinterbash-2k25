package com.vinterbash.web.model;

public class eventsRequest {
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getActiveEvent() {
		return activeEvent;
	}
	public void setActiveEvent(String activeEvent) {
		this.activeEvent = activeEvent;
	}
	private String schoolName;
	private String activeEvent;
	
}
