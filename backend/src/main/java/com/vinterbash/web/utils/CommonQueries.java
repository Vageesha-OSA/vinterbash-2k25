package com.vinterbash.web.utils;

public class CommonQueries {
	public static final String INSERT_SCHOOL = "INSERT INTO schools (school_id, school_name) VALUES (:schoolId, (Select school_name from schools where school_Id = :schoolId)) ON CONFLICT (school_id) DO NOTHING";

	public static final String INSERT_TEAM = "INSERT INTO teams (team_id, event_id, school_id, team_name) VALUES (:teamId, :eventId, :schoolId, :teamName)";

	public static final String INSERT_PARTICIPANT = "INSERT INTO participants (participant_id, team_id, participant_name) VALUES (:participantId, :teamId, :participantName)";

	public static final String GET_PARTICIPANTS_BY_SCHOOL =
		    "SELECT e.event_id, e.event_name, " +
		    "t.team_id, t.team_name, " +
		    "p.participant_id, p.participant_name " +
		    "FROM teams t " +
		    "LEFT JOIN participants p ON t.team_id = p.team_id " +
		    "INNER JOIN events e ON t.event_id = e.event_id " +
		    "INNER JOIN schools s ON t.school_id = s.school_id " +
		    "WHERE s.school_name = :schoolName AND e.event_name = :eventName " +
		    "ORDER BY t.team_id, p.participant_id";


	public static String VALIDATE_USER = "SELECT schools.password,Users.school_id,school_name FROM Users,Schools WHERE user_id = :userId and Users.school_id= Schools.school_id";
	public static final String GET_SCHOOL_EVENT_REGISTRATION_STATUS = """
			    SELECT
			        s.school_id,
			        s.school_name,
			        e.event_id,
			        e.max_teams_per_school,
			        COUNT(t.team_id) AS registered_teams
			    FROM schools s
			    CROSS JOIN events e
			    LEFT JOIN teams t ON t.school_id = s.school_id AND t.event_id = e.event_id
			    WHERE s.school_id = :schoolId
			    GROUP BY s.school_id, s.school_name, e.event_id, e.max_teams_per_school
			""";

	public static final String GET_PARTICIPANTS_AND_EVENTS_BY_SCHOOL = """
			SELECT
			    p.participant_name AS participantName,
			    e.event_name AS eventName
			FROM
			    participants p
			JOIN
			    teams t ON p.team_id = t.team_id
			JOIN
			    events e ON t.event_id = e.event_id
			JOIN
			    schools s ON t.school_id = s.school_id
			WHERE
			    s.school_name = :schoolName

					        """;

	public static final String VALIDATE_SCHOOL = "SELECT school_id FROM schools WHERE school_name = :schoolName AND password = :password";

	public static final String GET_ALL_EVENTS = "SELECT event_name AS eventName, event_id AS eventId FROM events";

	public static final String UPDATE_PARTICIPANT_NAME = "UPDATE participants SET participant_name = :participantName WHERE participant_id = :participantId";
}
