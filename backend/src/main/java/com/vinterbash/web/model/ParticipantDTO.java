package com.vinterbash.web.model;
public class ParticipantDTO {
    private String participantId;
    private String participantName;
	public ParticipantDTO(String participantId, String participantName) {
		super();
		this.participantId = participantId;
		this.participantName = participantName;
	}
	public ParticipantDTO() {
		super();
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getParticipantName() {
		return participantName;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
}
