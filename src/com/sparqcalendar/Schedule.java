package com.sparqcalendar;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.util.TextUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sparqcalendar.util.Constants;

public class Schedule {
	long userID;
	long scheduleID;
	long schoolID;
	int type;
	int grade;
	int version;
	String schoolName;
	String timezone;
	String startDate;
	String stopDate;
	ArrayList<ClassMeeting> meetings;
	ArrayList<Holiday> holidays;
	ArrayList<RotationalDay> days;
	
	public Schedule() {
		meetings = new ArrayList<ClassMeeting>();
		holidays = new ArrayList<Holiday>();
		days = new ArrayList<RotationalDay>();
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public ArrayList<ClassMeeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<ClassMeeting> meetings) {
		this.meetings = meetings;
	}
	
	public void addMeeting(ClassMeeting meeting) {
		if( this.meetings == null )
			this.meetings = new ArrayList<ClassMeeting>();
		this.meetings.add(meeting);
	}

	public ArrayList<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(ArrayList<Holiday> holidays) {
		this.holidays= holidays;
	}
	
	public void addHoliday(Holiday holiday) {
		if( this.holidays == null )
			this.holidays = new ArrayList<Holiday>();
		this.holidays.add(holiday);
	}
	
	public void addDay(RotationalDay day) {
		if( this.days == null )
			this.days = new ArrayList<RotationalDay>();
		this.days.add(day);
	}
	
	public ArrayList<RotationalDay> getDays() {
		return days;
	}

	public void setDays(ArrayList<RotationalDay> days) {
		this.days = days;
	}

	public long getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(long scheduleID) {
		this.scheduleID = scheduleID;
	}

	public long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(long schoolID) {
		this.schoolID = schoolID;
	}

	public static Schedule getScheduleFromDatabase(CallableStatement cs) throws SQLException, UnsupportedOperationException {
		Schedule schedule = new Schedule();

		ResultSet rs = cs.getResultSet();
		
		// get Schedule data
		rs.next();
		schedule.setUserID(rs.getLong("PK_UserID"));
		schedule.setScheduleID(rs.getLong("ScheduleID"));
		schedule.setSchoolID(rs.getLong("SchoolID"));
		schedule.setType(rs.getInt("Type"));
		schedule.setGrade(rs.getInt("Grade"));
		schedule.setSchoolName(rs.getString("Name"));
		schedule.setTimezone(rs.getString("TimeZone"));
		schedule.setStartDate(rs.getString("StartDate"));
		schedule.setStopDate(rs.getString("StopDate"));
		schedule.setVersion(rs.getInt("Version"));
		rs.close();
		
		if( !cs.getMoreResults() )
			throw new UnsupportedOperationException("No class meetings found");
		
		// get Class Meetings
		rs = cs.getResultSet();
		while( rs.next() ) {
			ClassMeeting cm = new ClassMeeting();

			cm.setPeriod(rs.getInt("Period"));
			cm.setSubject(rs.getString("Subject"));
			cm.setGrade(rs.getInt("Grade"));
			cm.setRoom(rs.getString("Room"));
			cm.setTeacherName(rs.getString("LastName"));
			cm.setTeacherEmail(rs.getString("Email"));
			cm.setStartTime(rs.getString("Start"));
			cm.setStopTime(rs.getString("Stop"));
			cm.setDay(rs.getInt("Day"));
			cm.setDayName(rs.getString("Name"));
			cm.setSection(rs.getInt("Section"));
			cm.setIcon(rs.getString("Icon"));
			
			schedule.addMeeting(cm);
		}
		rs.close();
		
		if( !cs.getMoreResults() )
			throw new UnsupportedOperationException("No days found");
		
		
		// get Rotational Days
		rs = cs.getResultSet();
		while( rs.next() ) {
			RotationalDay rd = new RotationalDay();

			rd.setName(rs.getString("Name"));
			rd.setNumber(rs.getInt("Number"));
			
			schedule.addDay(rd);
		}
		rs.close();
		
		// Get Holidays
		if( cs.getMoreResults() ) {
			rs = cs.getResultSet();
			DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd");
			while( rs.next() ) {
				String name = rs.getString("Name");
				DateTime start = dtf.parseDateTime(rs.getString("StartDate"));
				DateTime stop = dtf.parseDateTime(rs.getString("StopDate"));
				while( !start.isAfter(stop) ){
					if( start.dayOfWeek().get() < DateTimeConstants.SATURDAY ) {
						Holiday h = new Holiday();
						
						h.setName(name);
						h.setDate(dtf.print(start));
						
						schedule.addHoliday(h);
					}
					
					start = start.plusDays(1);
				}
			}
		}
		rs.close();
		
		return schedule;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
