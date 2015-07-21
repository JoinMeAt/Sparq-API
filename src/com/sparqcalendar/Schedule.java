package com.sparqcalendar;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.util.TextUtils;

public class Schedule {
	long userID;
	int type;
	int grade;
	String schoolName;
	String timezone;
	String startDate;
	String stopDate;
	ArrayList<Period> periods;
	ArrayList<RotationalDay> days;
	ArrayList<Section> sections;
	ArrayList<ClassMeeting> meetings;
	ArrayList<Holiday> holidays;
	
	public Schedule() {
		periods = new ArrayList<Period>();
		days = new ArrayList<RotationalDay>();
		sections = new ArrayList<Section>();
		meetings = new ArrayList<ClassMeeting>();
		holidays = new ArrayList<Holiday>();
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

	public ArrayList<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(ArrayList<Period> periods) {
		this.periods = periods;
	}
	
	public void addPeriod(Period period) {
		if( this.periods == null )
			this.periods = new ArrayList<Period>();
		this.periods.add(period);
	}

	public ArrayList<RotationalDay> getDays() {
		return days;
	}

	public void setDays(ArrayList<RotationalDay> days) {
		this.days = days;
	}
	
	public void addDay(RotationalDay day) {
		if( this.days == null )
			this.days = new ArrayList<RotationalDay>();
		this.days.add(day);
	}

	public ArrayList<Section> getSection() {
		return sections;
	}

	public void setSection(ArrayList<Section> classes) {
		this.sections = classes;
	}
	
	public void addSection(Section c) {
		if( this.sections == null )
			this.sections = new ArrayList<Section>();
		this.sections.add(c);
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
	
	public static Schedule getScheduleFromDatabase(CallableStatement cs) throws SQLException, UnsupportedOperationException {
		Schedule schedule = new Schedule();

		ResultSet rs = cs.getResultSet();
		
		// get Schedule data
		rs.next();
		schedule.setUserID(rs.getLong("PK_UserID"));
		schedule.setType(rs.getInt("Type"));
		schedule.setGrade(rs.getInt("Grade"));
		schedule.setSchoolName(rs.getString("Name"));
		schedule.setTimezone(rs.getString("TimeZone"));
		schedule.setStartDate(rs.getString("StartDate"));
		schedule.setStopDate(rs.getString("StopDate"));
		rs.close();
		
		if( !cs.getMoreResults() ) 
			throw new UnsupportedOperationException("No rotational days found");
		
		// get Rotational Days
		rs = cs.getResultSet();
		while( rs.next() ) {
			schedule.addDay(
					new RotationalDay(
							rs.getLong("PK_DayID"), 
							rs.getString("Name")));
		}		

		if( !cs.getMoreResults() ) 
			throw new UnsupportedOperationException("No periods found");
		
		// get Periods
		rs = cs.getResultSet();
		while( rs.next() ) {
			Period p = new Period();
			
			p.setId(rs.getInt("PK_PeriodID"));
			p.setNumber(rs.getInt("Number"));
			p.setStartTime(rs.getString("Start"));
			p.setStopTime(rs.getString("Stop"));
			
			schedule.addPeriod(p);
		}		

		if( !cs.getMoreResults() ) 
			throw new UnsupportedOperationException("No sections found");
		
		// get Sections
		rs = cs.getResultSet();
		while( rs.next() ) {
			Section s = new Section();
			
			s.setId(rs.getLong("PK_SectionID"));
			s.setSubject(rs.getString("Subject"));
			s.setSection(rs.getString("Section"));
			s.setRoom(rs.getString("Room"));
			s.setGrade(rs.getInt("Grade"));
			String first = rs.getString("FirstName");
			if( !TextUtils.isEmpty(first) ) {
				s.setTeacherName(
						first
						+ " " +
						rs.getString("LastName"));
			}
			s.setTeacherEmail(rs.getString("Email"));
			
			schedule.addSection(s);
		}
		
		if( !cs.getMoreResults() )
			throw new UnsupportedOperationException("No class meetings found");
		
		// get Class Meetings
		rs = cs.getResultSet();
		while( rs.next() ) {
			ClassMeeting cm = new ClassMeeting();
			
			cm.setDayID(rs.getLong("FK_DayID"));
			cm.setPeriodID(rs.getLong("FK_PeriodID"));
			cm.setSectionID(rs.getLong("FK_SectionID"));
			
			schedule.addMeeting(cm);
		}
		
		if( cs.getMoreResults() ) {
			rs = cs.getResultSet();
			while( rs.next() ) {
				Holiday h = new Holiday();
				
				h.setName(rs.getString("Name"));
				h.setStartDate(rs.getString("StartDate"));
				h.setStopDate(rs.getString("StopDate"));
				
				schedule.addHoliday(h);
			}
		}
		
		return schedule;
	}
}
