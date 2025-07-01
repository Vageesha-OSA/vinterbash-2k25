package com.vinterbash.web.model;

import java.util.ArrayList;
import java.util.List;

public class EventTeamDTO {
	private String teamId;
    private String teamName;
    private List<ParticipantDTO> participants = new ArrayList<>();
    public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public List<ParticipantDTO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantDTO> participants) {
		this.participants = participants;
	}
}
