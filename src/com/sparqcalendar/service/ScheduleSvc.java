package com.sparqcalendar.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;

import org.apache.http.util.TextUtils;

import com.sparqcalendar.Schedule;
import com.sparqcalendar.SparqError;
import com.sparqcalendar.util.Constants;
import com.sparqcalendar.util.DBConnection;
import com.sparqcalendar.util.JsonTransformer;
import com.sun.org.apache.bcel.internal.generic.DCONST;

@Path("/schedule")
public class ScheduleSvc {
	
	@Path("/{userID}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSchedule(
		 @PathParam("userID") String userID,
		 @QueryParam("date") String date) {
		String response = null;
		Connection con = null;
		Statement stmt = null;
		Schedule schedule = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			CallableStatement cs = con.prepareCall("{call GetFullSchedule(?,?,?,?)}");
			stmt = cs;
			cs.setString("UserID", userID);
			if( TextUtils.isEmpty(date) )
				cs.setNull("Today", java.sql.Types.TIMESTAMP);
			else
				cs.setTimestamp("Today",new Timestamp(Long.parseLong(date)));
			cs.registerOutParameter("Success", java.sql.Types.BIT);
			cs.registerOutParameter("ErrorID", java.sql.Types.SMALLINT);
			
			if( cs.execute() ) {
				schedule = Schedule.getScheduleFromDatabase(cs);
				
				response = JsonTransformer.toJson(schedule);
			}					
			
		} catch (Exception e) {
			response = JsonTransformer.toJson(new SparqError(e.getMessage()));
		} finally {
			DBConnection.closeConnection(con);
			DBConnection.closeStatement(stmt);
		}
		
		
		return response;
	}
	
	@GET
	@Path("/version/{version}")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkVersion(
			@PathParam("version") String versionStr,
			@QueryParam("scheduleID") String scheduleID) {
		String response = "{\"version\":\"%s\"}";
		Connection con = null;
		PreparedStatement ps = null;
		
		long version = Long.parseLong(versionStr);
		if( TextUtils.isEmpty(scheduleID) ) { 
			if(  version == Constants.VERSION ) {
				response = String.format(response,"current");
			} else {
				response = String.format(response,"old");
			}
		} else {
			try {
				con = DBConnection.getDBConnection();
				ps = con.prepareStatement("SELECT Version FROM Schedules "
																					+ " WHERE PK_ScheduleID = ?"
																					+ " LIMIT 1");
				ps.setLong(1,Long.parseLong(scheduleID));
				
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					int versionID = rs.getInt("Version");
					if( versionID == version ) {
						response = String.format(response,"current");
					} else {
						response = String.format(response,"old");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBConnection.closeStatement(ps);
				DBConnection.closeConnection(con);
			}
		}
		
		
		return response;
	}
}
