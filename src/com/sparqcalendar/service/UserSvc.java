package com.sparqcalendar.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.util.TextUtils;

import com.sparqcalendar.Schedule;
import com.sparqcalendar.SparqError;
import com.sparqcalendar.util.Constants;
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

			stmt = con.prepareCall("{call VerifyPasswordCondensed(?,?,?,?,?)}");
			CallableStatement cs = (CallableStatement) stmt;
			cs.setString("Email", email);
			cs.setString("Password", password);
			cs.registerOutParameter("UserID", java.sql.Types.BIGINT);
			cs.registerOutParameter("Success", java.sql.Types.BIT);
			cs.registerOutParameter("ErrorID", java.sql.Types.INTEGER);
			
			if( cs.execute() ) {
				schedule = Schedule.getScheduleFromDatabase(cs);
				
				response = JsonTransformer.toJson(schedule).replaceAll("'", "\\\\'"); // unicode for \' 
			} else {
				if( cs.getBoolean("Success") ) {
					return "false";
				} else {
					int errorID = cs.getInt("ErrorID"); 
					if( errorID  == 6 ) {
						return JsonTransformer.toJson(new SparqError("Invalid email or password."));
					} else if( errorID == 12 ) {
						return JsonTransformer.toJson(new SparqError("Reset password to recover this account."));
					}
				}
			}
		} catch (Exception e) {
			response = JsonTransformer.toJson(new SparqError(e.getMessage()));
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return response;
	}
	
	@POST
	@Path("")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerUser( 
			@FormParam("email") String email,
			@FormParam("password") String password ) {
		String response = null;
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			stmt = con.prepareCall("{call RegisterUser(?,?,?,?)}");
			CallableStatement cs = (CallableStatement) stmt;
			cs.setString("Email", email);
			cs.setString("Password", password);
			cs.registerOutParameter("Success", java.sql.Types.BIT);
			cs.registerOutParameter("ErrorID", java.sql.Types.SMALLINT);
			
			if( cs.execute() ) {
				con.commit();
				Schedule schedule = Schedule.getScheduleFromDatabase(cs);
				
				response = JsonTransformer.toJson(schedule);
			} else {
				int error = cs.getInt("ErrorID");
				if( error == 7 ) {
					return JsonTransformer.toJson(new SparqError("Email already exists, try recovering your password."));
				} else if( error == 13 ) {
					return JsonTransformer.toJson(new SparqError("Email doesn't exist. Talk to your administrator"));
				}
			}
			
		} catch (Exception e) {
			response = JsonTransformer.toJson(new SparqError(e.getMessage()));
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(con);
		}
		
		return response;
	}
	
	@SuppressWarnings("resource")
	@GET
	@Path("/reset/password")
	@Produces(MediaType.TEXT_PLAIN)
	public String resetPassword(
			@QueryParam("email") String email ) {
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			while( true ) {
				String code = RandomStringUtils.randomAlphanumeric(Constants.RESET_CODE_LENGTH);
				stmt = con.prepareCall("{call ResetPassword(?,?,?,?)}");
				CallableStatement cs = (CallableStatement) stmt;
				cs.setString("Email", email);
				cs.setString("Code", code);
				cs.registerOutParameter("Success", java.sql.Types.BIT);
				cs.registerOutParameter("ErrorID", java.sql.Types.SMALLINT);
				cs.executeUpdate();
				
				if( cs.getBoolean("Success") ) {
					con.commit();
					return code;
				} else {
					if( cs.getInt("ErrorID") == 10 ) { // code not unique, try another one
						DBConnection.closeStatement(stmt);
						continue;
					} else {
						return JsonTransformer.toJson(new SparqError("Unable to complete your request"));
					}
				}
			}
		} catch (Exception e) {
			return JsonTransformer.toJson(new SparqError("Unable to complete your request"));
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(con);
		}
	}
	
	@POST
	@Path("/update/password")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePassword(
			@FormParam("email") String email,
			@FormParam("code") String code,
			@FormParam("password") String password) {

		String response = null;
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = DBConnection.getDBConnection();
			
			stmt = con.prepareCall("{call UpdatePassword(?,?,?,?,?)}");
			CallableStatement cs = (CallableStatement) stmt;
			cs.setString("Email", email);
			cs.setString("Password", password);
			cs.setString("Code",code);
			cs.registerOutParameter("Success", java.sql.Types.BIT);
			cs.registerOutParameter("ErrorID", java.sql.Types.SMALLINT);
			cs.executeUpdate();
			if( cs.getBoolean("Success") ) {
				con.commit();				
				return "true";
			} else {
				return JsonTransformer.toJson(new SparqError("User with this email already exists.\nTry recovering your password."));
			}
			
		} catch (Exception e) {
			response = JsonTransformer.toJson(new SparqError(e.getMessage()));
		} finally {
			DBConnection.closeStatement(stmt);
			DBConnection.closeConnection(con);
		}
		
		return response;
	}

}
