package com.vinterbash.web.dao;
import java.util.List;
import java.util.Map;

import com.vinterbash.web.model.EventDTO;
import com.vinterbash.web.model.EventWithTeamsDTO;
import com.vinterbash.web.model.ParticipantDTO;
import com.vinterbash.web.model.ParticipantEventDTO;
import com.vinterbash.web.model.TeamRegistrationRequest;
import com.vinterbash.web.model.eventsRequest;

public interface VinterbashDao {
	String getSchoolIdIfValid(String schoolName, String password);
    List<EventDTO> getAllEvents();
    Map<String, Object> getRegistrationSummary(String schoolId);
    void registerTeam(TeamRegistrationRequest request);
	List<ParticipantEventDTO> getEventParticipantMap(String schoolId);
	public void updateParticipantNames(List<ParticipantDTO> participants);
	public EventWithTeamsDTO getEventDetails(eventsRequest request);
}
