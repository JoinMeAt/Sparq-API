package com.sparqcalendar;

public class RotationalDay {
	public long id;
	public int number;
	public String name;
	
	public RotationalDay() {}
	
	public RotationalDay(int number, String name) {
		this.number = number;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
