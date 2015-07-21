package com.sparqcalendar;

public class ClassMeeting {
	long sectionID;
	long periodID;
	long dayID;
	
	public ClassMeeting() {}
	
	public ClassMeeting(long sectionID, long periodID, long dayID) {
		super();
		this.sectionID = sectionID;
		this.periodID = periodID;
		this.dayID = dayID;
	}
	
	public long getSectionID() {
		return sectionID;
	}
	
	public void setSectionID(long sectionID) {
		this.sectionID = sectionID;
	}
	
	public long getPeriodID() {
		return periodID;
	}
	
	public void setPeriodID(long periodID) {
		this.periodID = periodID;
	}
	
	public long getDayID() {
		return dayID;
	}
	
	public void setDayID(long dayID) {
		this.dayID = dayID;
	}
}
