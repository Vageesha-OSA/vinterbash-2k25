package com.vinterbash.web.controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinterbash.web.model.EventWithTeamsDTO;
import com.vinterbash.web.model.ParticipantEventDTO;
import com.vinterbash.web.model.TeamRegistrationRequest;
import com.vinterbash.web.model.ValidateRequest;
import com.vinterbash.web.model.ValidateResponse;
import com.vinterbash.web.model.eventsRequest;
import com.vinterbash.web.model.updateRequest;
import com.vinterbash.web.service.VinterbashService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/vinterbash")
public class VinterbashController {

	@Autowired
	private VinterbashService vinterbashService;

	    public VinterbashController(VinterbashService vinterbashService) {
	        this.vinterbashService = vinterbashService;
	    }
	    
	    @PostMapping("/validate")
	    public ResponseEntity<?> validateSchool(@RequestBody ValidateRequest request) throws Exception {
	        ValidateResponse response = vinterbashService.validateSchool(request);

	        if (response == null) {
	            Map<String, String> error = Map.of("error", "Invalid");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	        }

	        return ResponseEntity.ok(response);
	    }
	    
	    
	    @PostMapping("/registeredEvents")
	    public ResponseEntity<?> getRegisteredEvents(@RequestBody Map<String, String> body) {
	    	String schoolId = body.get("schoolId");
	    	Map<String, Object> response = vinterbashService.getSchoolEventStats(schoolId);
	        if (response == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("School ID not found or no events available.");
	        }
	        return ResponseEntity.ok(response);
	    }
	    

	    @PostMapping("/register")
	    public ResponseEntity<String> registerTeam(
	            @RequestBody TeamRegistrationRequest request) {
	    	vinterbashService.register(request);
	        return ResponseEntity.ok("Congratulations. You have successfully registered for event : " + request.getEventId());
	    }
	    
	    @PostMapping("/events")
	    public ResponseEntity<EventWithTeamsDTO> getEventDetails(@RequestBody eventsRequest request) {
	        EventWithTeamsDTO response = vinterbashService.getEventDetails(request);
	        return ResponseEntity.ok(response);
	    }



	    
	    @PostMapping("/eventParticipantMap")
	    public ResponseEntity<?> getEventParticipantMap(@RequestBody  Map<String, String> body) {
	        List<ParticipantEventDTO> result = vinterbashService.getEventParticipantMap(body.get("schoolName"));
	        Map<String,List<ParticipantEventDTO>> response = new LinkedHashMap<>();
	        response.put("participants",result);
	        if (result.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found with this Id : " + body.get("schoolName"));
	        }
	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/updateTeamParticipants")
	    public ResponseEntity<?> getEventParticipantMap(@RequestBody updateRequest request) {
	        vinterbashService.updateParticipants(request.getParticipants());
	        return ResponseEntity.ok("updated successfully");
	    }
	    
	    



}
