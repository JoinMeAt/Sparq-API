package com.sparqcalendar;

public class Period {
	long id;
	int number;
	String startTime;
	String stopTime;
	
	public Period() {}
	
	public Period(long id, int number, String startTime, String stopTime) {
		this.id = id;
		this.number = number;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	
	
}
