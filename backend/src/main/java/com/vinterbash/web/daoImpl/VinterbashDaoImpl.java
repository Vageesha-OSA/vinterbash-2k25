package com.vinterbash.web.daoImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.vinterbash.web.dao.VinterbashDao;
import com.vinterbash.web.model.EventDTO;
import com.vinterbash.web.model.EventTeamDTO;
import com.vinterbash.web.model.EventWithTeamsDTO;
import com.vinterbash.web.model.ParticipantDTO;
import com.vinterbash.web.model.ParticipantEventDTO;
import com.vinterbash.web.model.TeamRegistrationRequest;
import com.vinterbash.web.model.eventsRequest;
import com.vinterbash.web.utils.CommonQueries;

@Repository
public class VinterbashDaoImpl implements VinterbashDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	    public VinterbashDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	    @Override
	    public String getSchoolIdIfValid(String schoolName, String password) {
	        String sql = CommonQueries.VALIDATE_SCHOOL;
	        Map<String, Object> params = Map.of("schoolName", schoolName, "password", password);
	        try {
	            return jdbcTemplate.queryForObject(sql, params, String.class);
	        } catch (EmptyResultDataAccessException e) {
	            return null;
	        }
	    }

	    @Override
	    public List<EventDTO> getAllEvents() {
	        String sql = CommonQueries.GET_ALL_EVENTS;
	        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventDTO.class));
	    }
	    
	    @Override
	    public Map<String, Object> getRegistrationSummary(String schoolId) {
	        Map<String, Object> result = new LinkedHashMap<>();

	        SqlParameterSource params = new MapSqlParameterSource("schoolId", schoolId);

	        List<Map<String, Object>> rows = jdbcTemplate.queryForList(CommonQueries.GET_SCHOOL_EVENT_REGISTRATION_STATUS, params);

	        if (rows.isEmpty()) return null;

	        int fully = 0, partially = 0, none = 0;
	        String schoolName = null;

	        for (Map<String, Object> row : rows) {
	            int registered = ((Number) row.get("registered_teams")).intValue();
	            int max = ((Number) row.get("max_teams_per_school")).intValue();

	            if (registered == 0) {
	                none++;
	            } else if (registered < max) {
	                partially++;
	            } else {
	                fully++;
	            }

	            schoolName = (String) row.get("school_name");
	        }

	        result.put("schoolId", schoolId);
	        result.put("schoolName", schoolName);
	        result.put("fullyRegistered", fully);
	        result.put("partiallyRegistered", partially);
	        result.put("notRegistered", none);
	        return result;
	    }
	    
	    @Override
	    public void registerTeam(TeamRegistrationRequest request) {
	        Map<String, Object> schoolParams = new HashMap<>();
	        schoolParams.put("schoolId", request.getSchoolId());
	        jdbcTemplate.update(CommonQueries.INSERT_SCHOOL, schoolParams);

	        Map<String, Object> teamParams = new HashMap<>();
	        teamParams.put("teamId", request.getTeamId());
	        teamParams.put("eventId", request.getEventId());
	        teamParams.put("schoolId", request.getSchoolId());
	        teamParams.put("teamName",request.getSchoolId()+request.getTeamId());
	        jdbcTemplate.update(CommonQueries.INSERT_TEAM, teamParams);

	        for (ParticipantDTO participant : request.getParticipants()) {
	            Map<String, Object> participantParams = new HashMap<>();
	            participantParams.put("participantId", participant.getParticipantId());
	            participantParams.put("teamId", request.getTeamId());
	            participantParams.put("participantName", participant.getParticipantName());
	            jdbcTemplate.update(CommonQueries.INSERT_PARTICIPANT, participantParams);
	        }
	    }

	    


    
	    @Override
	    public List<ParticipantEventDTO> getEventParticipantMap(String schoolName) {
	        Map<String, Object> params = Map.of("schoolName", schoolName);

	        return jdbcTemplate.query(
	            CommonQueries.GET_PARTICIPANTS_AND_EVENTS_BY_SCHOOL,
	            params,
	            (rs, rowNum) -> new ParticipantEventDTO(
	                rs.getString("participantName"),
	                rs.getString("eventName")
	            )
	        );
	    }
	    
	    @Override
	    public void updateParticipantNames(List<ParticipantDTO> participants) {
	        for (ParticipantDTO p : participants) {
	            Map<String, Object> params = Map.of(
	                "participantId", p.getParticipantId(),
	                "participantName", p.getParticipantName()
	            );
	            jdbcTemplate.update(CommonQueries.UPDATE_PARTICIPANT_NAME, params);
	        }
	    }
	    
	    public EventWithTeamsDTO getEventDetails(eventsRequest request) {
	        String sql = """
	            SELECT 
	                e.event_id,
	                e.event_name,
	                t.team_id,
	                t.team_name,
	                p.participant_id,
	                p.participant_name
	            FROM schools s
	            JOIN teams t ON s.school_id = t.school_id
	            JOIN events e ON t.event_id = e.event_id
	            JOIN participants p ON t.team_id = p.team_id
	            WHERE s.school_name = :schoolName AND e.event_name = :eventName
	            ORDER BY t.team_id, p.participant_id
	        """;

	        MapSqlParameterSource params = new MapSqlParameterSource()
	            .addValue("schoolName", request.getSchoolName())
	            .addValue("eventName", request.getActiveEvent());

	        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);

	        EventWithTeamsDTO event = new EventWithTeamsDTO();
	        Map<String, EventTeamDTO> teamMap = new LinkedHashMap<>();

	        for (Map<String, Object> row : rows) {
	            if (event.getTeams() == null) {
	                event.setEventId((String) row.get("event_id"));
	                event.setEventName((String) row.get("event_name"));
	                event.setTeams(new ArrayList<>());
	            }

	            String teamId = (String) row.get("team_id");
	            String teamName = (String) row.get("team_name");
	            String participantId = (String) row.get("participant_id");
	            String participantName = (String) row.get("participant_name");

	            EventTeamDTO team = teamMap.computeIfAbsent(teamId, id -> {
	                EventTeamDTO t = new EventTeamDTO();
	                t.setTeamId(teamId);
	                t.setTeamName(teamName);
	                t.setParticipants(new ArrayList<>());
	                event.getTeams().add(t);
	                return t;
	            });

	            team.getParticipants().add(new ParticipantDTO(participantId, participantName));
	        }

	        return event;
	    }

	    
	    
	    
	    
	    



}
