package com.sparqcalendar;

public class Section {
	long id;
	String subject;
	String section;
	String room;
	int grade;
	String teacherName;
	String teacherEmail;
	
	public Section() {}
	
	public Section(long id, String subject, String section, String room,
			int grade, String teacherName, String teacherEmail) {
		super();
		this.id = id;
		this.subject = subject;
		this.section = section;
		this.room = room;
		this.grade = grade;
		this.teacherName = teacherName;
		this.teacherEmail = teacherEmail;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}
}
