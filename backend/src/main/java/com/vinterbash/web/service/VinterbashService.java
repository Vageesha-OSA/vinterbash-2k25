package com.vinterbash.web.service;
import java.util.List;
import java.util.Map;

import com.vinterbash.web.model.EventWithTeamsDTO;
import com.vinterbash.web.model.ParticipantDTO;
import com.vinterbash.web.model.ParticipantEventDTO;
import com.vinterbash.web.model.TeamRegistrationRequest;
import com.vinterbash.web.model.ValidateRequest;
import com.vinterbash.web.model.ValidateResponse;
import com.vinterbash.web.model.eventsRequest;



public interface VinterbashService {
    ValidateResponse validateSchool(ValidateRequest request) throws Exception;
    Map<String, Object> getSchoolEventStats(String schoolId);
    void register(TeamRegistrationRequest request);
    public EventWithTeamsDTO getEventDetails(eventsRequest request);
    List<ParticipantEventDTO> getEventParticipantMap(String schoolId);
    public void updateParticipants(List<ParticipantDTO> participants);
}



