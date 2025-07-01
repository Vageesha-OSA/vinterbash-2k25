package com.vinterbash.web.model;
import java.util.List;

public class TeamRegistrationRequest {
    private String schoolId;
    private String eventId;
    private String teamId;
    private List<ParticipantDTO> participants;
    
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public List<ParticipantDTO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantDTO> participants) {
		this.participants = participants;
	}
	
	
}
