package com.vinterbash.web.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinterbash.web.dao.VinterbashDao;
import com.vinterbash.web.model.EventDTO;
import com.vinterbash.web.model.EventTeamDTO;
import com.vinterbash.web.model.EventWithTeamsDTO;
import com.vinterbash.web.model.ParticipantDTO;
import com.vinterbash.web.model.ParticipantEventDTO;
import com.vinterbash.web.model.TeamRegistrationRequest;
import com.vinterbash.web.model.ValidateRequest;
import com.vinterbash.web.model.ValidateResponse;
import com.vinterbash.web.model.eventsRequest;
import com.vinterbash.web.service.VinterbashService;

@Service
public class VinterbashServiceImpl implements VinterbashService {
	VinterbashDao vinterbashDao;

	@Autowired
	public VinterbashServiceImpl(VinterbashDao vinterbashDao) {
		this.vinterbashDao = vinterbashDao;
	}
	
	@Override
	public ValidateResponse validateSchool(ValidateRequest request) throws Exception {
		String schoolId = vinterbashDao.getSchoolIdIfValid(request.getSchoolName(), request.getPassword());
		if (schoolId == null) {
			return null;
		}

		List<EventDTO> events = vinterbashDao.getAllEvents();

		ValidateResponse response = new ValidateResponse();
		response.setSchoolId(schoolId);
		response.setSchoolName(request.getSchoolName());
		response.setEvents(events);
		return response;
	}
	
	@Override
	public Map<String, Object> getSchoolEventStats(String schoolId) {
		return vinterbashDao.getRegistrationSummary(schoolId);
	}

	@Override
	public void register(TeamRegistrationRequest request) {
		vinterbashDao.registerTeam(request);
	}

	public EventWithTeamsDTO getEventDetails(eventsRequest request) {
		EventWithTeamsDTO service = vinterbashDao.getEventDetails(request);
		if(service.getTeams()==null) {
			service.setEventName(request.getActiveEvent());
			service.setTeams(new ArrayList<>());
		}
		return service;
	}



	@Override
	public List<ParticipantEventDTO> getEventParticipantMap(String schoolName) {
		return vinterbashDao.getEventParticipantMap(schoolName);
	}
	
	@Override
	public void updateParticipants(List<ParticipantDTO> participants) {
	    vinterbashDao.updateParticipantNames(participants);
	}

}
