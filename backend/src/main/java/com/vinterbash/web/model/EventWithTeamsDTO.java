package com.vinterbash.web.model;

import java.util.List;

public class EventWithTeamsDTO  {
    private String eventId;
    private String eventName;
    public List<EventTeamDTO> getTeams() {
		return teams;
	}

	public void setTeams(List<EventTeamDTO> teams) {
		this.teams = teams;
	}

	private List<EventTeamDTO> teams;

    // Getters and setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


}
