package com.sparqcalendar.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;

import com.sparqcalendar.Schedule;
import com.sparqcalendar.util.DBConnection;
import com.sparqcalendar.util.JsonTransformer;

@Path("/user")
public class UserSvc {

	@GET
	@Path("")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(
			@QueryParam("email") String email,
			@QueryParam("password") String password ) {
		String response = null;
		Connection con = null;
		Statement stmt = null;
		Schedule schedule = null;
		
		try {			
			con = DBConnection.getDBConnection();

			stmt = con.prepareCall("{call VerifyPassword(?,?,?,?,?)}");
			CallableStatement cs = (CallableStatement) stmt;
			cs.setString("Email", email);
			cs.setString("Password", password);
			cs.registerOutParameter("UserID", java.sql.Types.BIGINT);
			cs.registerOutParameter("Success", java.sql.Types.BIT);
			cs.registerOutParameter("ErrorID", java.sql.Types.INTEGER);
			
			if( cs.execute() ) {
				schedule = Schedule.getScheduleFromDatabase(cs);
				
				response = JsonTransformer.toJson(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException uoe ) {
			uoe.printStackTrace();
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return response;
	}
}
