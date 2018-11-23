package com.rakuten.fullstackrecruitmenttest.model;

/**
 * Created by Suresh Kumar S on 17-Nov-2018.
 */

public class Employee {
	private Long id;
	private String name;
	private String department;
	private String designation;
	private String salary;
	private String joiningDate;
	private boolean isErrorRecord;

	public Employee() {

	}

	public Employee(Long id, String name, String department, String designation, String salary, String joiningDate, boolean isErrorRecord) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.designation = designation;
		this.salary = salary;
		this.joiningDate = joiningDate;
		this.isErrorRecord = isErrorRecord;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public boolean isErrorRecord() {
		return isErrorRecord;
	}

	public void setErrorRecord(boolean isErrorRecord) {
		this.isErrorRecord = isErrorRecord;
	}
}