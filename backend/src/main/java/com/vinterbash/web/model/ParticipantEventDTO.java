package com.vinterbash.web.model;
public class ParticipantEventDTO {
    public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	private String participantName;
    private String eventName;

    public ParticipantEventDTO(String participantName, String eventName) {
        this.participantName = participantName;
        this.eventName = eventName;
    }

   
}
