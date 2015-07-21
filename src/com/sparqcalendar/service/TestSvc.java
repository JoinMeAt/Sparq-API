package com.sparqcalendar.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class TestSvc {

	@Path("/")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSchedule() {
		String response = null;
		
		response = "{\"studentID\":\"1\",\"school\":\"Good Shepherd Episcopal School\",\"grade\":5,\"startOfSchedule\":\"10/8\",\"endOfSchedule\":\"11/20\",\"holidays\":[\"10/12\"],\"days\":[{\"name\":\"C\",\"id\":1},{\"name\":\"O\",\"id\":2},{\"name\":\"U\",\"id\":3},{\"name\":\"G\",\"id\":4},{\"name\":\"A\",\"id\":5},{\"name\":\"R\",\"id\":6}],\"periods\":[{\"number\":1,\"id\":1,\"startTime\":\"7:55\",\"duration\":45},{\"number\":2,\"id\":2,\"startTime\":\"8:40\",\"duration\":70},{\"number\":3,\"id\":3,\"startTime\":\"9:50\",\"duration\":40},{\"number\":4,\"id\":4,\"startTime\":\"10:30\",\"duration\":70},{\"number\":5,\"id\":5,\"startTime\":\"11:40\",\"duration\":45},{\"number\":6,\"id\":6,\"startTime\":\"12:25\",\"duration\":45},{\"number\":7,\"id\":7,\"startTime\":\"13:10\",\"duration\":70},{\"number\":8,\"id\":8,\"startTime\":\"14:20\",\"duration\":70}],\"classes\":[{\"id\":1,\"subject\":\"American History\",\"room\":\"A201\",\"teacher\":\"Abraham1\",\"section\":1},{\"id\":2,\"subject\":\"Earth Science\",\"room\":\"A202\",\"teacher\":\"Abraham2\",\"section\":1},{\"id\":3,\"subject\":\"Math - Fractions\",\"room\":\"A203\",\"teacher\":\"Abraham3\",\"section\":2},{\"id\":4,\"subject\":\"Spanish\",\"room\":\"A204\",\"teacher\":\"Abraham4\",\"section\":3},{\"id\":5,\"subject\":\"English\",\"room\":\"A205\",\"teacher\":\"Abraham5\",\"section\":2},{\"id\":6,\"subject\":\"Music\",\"room\":\"A206\",\"teacher\":\"Abraham6\",\"section\":1},{\"id\":7,\"subject\":\"Chapel, Advisory, Community\",\"room\":\"A201\",\"teacher\":\"Abraham7\",\"section\":1},{\"id\":8,\"subject\":\"Break\",\"room\":\"A302\",\"teacher\":\"Abraham8\",\"section\":1},{\"id\":9,\"subject\":\"Physical Education\",\"room\":\"A303\",\"teacher\":\"Abraham9\",\"section\":1},{\"id\":10,\"subject\":\"Lunch\",\"room\":\"A304\",\"teacher\":\"Abraham0\",\"section:1\"}],\"links\":[{\"day\":1,\"period\":2,\"class\":1},{\"day\":1,\"period\":4,\"class\":2},{\"day\":1,\"period\":7,\"class\":3},{\"day\":1,\"period\":8,\"class\":4},{\"day\":1,\"period\":1,\"class\":7},{\"day\":1,\"period\":3,\"class\":8},{\"day\":1,\"period\":5,\"class\":9},{\"day\":1,\"period\":6,\"class\":10},{\"day\":2,\"period\":2,\"class\":3},{\"day\":2,\"period\":4,\"class\":5},{\"day\":2,\"period\":7,\"class\":6},{\"day\":2,\"period\":8,\"class\":1},{\"day\":2,\"period\":1,\"class\":7},{\"day\":2,\"period\":3,\"class\":8},{\"day\":2,\"period\":5,\"class\":9},{\"day\":2,\"period\":6,\"class\":10},{\"day\":3,\"period\":2,\"class\":2},{\"day\":3,\"period\":4,\"class\":5},{\"day\":3,\"period\":7,\"class\":6},{\"day\":3,\"period\":8,\"class\":4},{\"day\":3,\"period\":1,\"class\":7},{\"day\":3,\"period\":3,\"class\":8},{\"day\":3,\"period\":5,\"class\":9},{\"day\":3,\"period\":6,\"class\":10},{\"day\":4,\"period\":2,\"class\":4},{\"day\":4,\"period\":4,\"class\":1},{\"day\":4,\"period\":7,\"class\":2},{\"day\":4,\"period\":8,\"class\":6},{\"day\":4,\"period\":1,\"class\":7},{\"day\":4,\"period\":3,\"class\":8},{\"day\":4,\"period\":5,\"class\":9},{\"day\":4,\"period\":6,\"class\":10},{\"day\":5,\"period\":2,\"class\":5},{\"day\":5,\"period\":4,\"class\":3},{\"day\":5,\"period\":7,\"class\":4},{\"day\":5,\"period\":8,\"class\":6},{\"day\":5,\"period\":1,\"class\":7},{\"day\":5,\"period\":3,\"class\":8},{\"day\":5,\"period\":5,\"class\":9},{\"day\":5,\"period\":6,\"class\":10},{\"day\":6,\"period\":2,\"class\":1},{\"day\":6,\"period\":4,\"class\":3},{\"day\":6,\"period\":7,\"class\":2},{\"day\":6,\"period\":8,\"class\":5},{\"day\":6,\"period\":1,\"class\":7},{\"day\":6,\"period\":3,\"class\":8},{\"day\":6,\"period\":5,\"class\":9},{\"day\":6,\"period\":6,\"class\":10}]}";
		
		
		return response;
	}
}
