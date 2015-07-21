package com.sparqcalendar;

public class Holiday {
	String name;
	String startDate;
	String stopDate;
	public Holiday() {}
	public Holiday(String name, String startDate, String stopDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.stopDate = stopDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSTopDate() {
		return stopDate;
	}
	public void setStopDate(String StopDate) {
		this.stopDate = StopDate;
	}
}
