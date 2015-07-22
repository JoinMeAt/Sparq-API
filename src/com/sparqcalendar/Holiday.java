package com.sparqcalendar;

public class Holiday {
	String name;
	String date;
	public Holiday() {}
	public Holiday(String name, String date) {
		super();
		this.name = name;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
