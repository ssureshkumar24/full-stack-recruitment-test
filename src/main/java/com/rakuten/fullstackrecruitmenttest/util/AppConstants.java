package com.rakuten.fullstackrecruitmenttest.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rakuten.fullstackrecruitmenttest.model.Employee;


public interface AppConstants {
	String CSV_MIME_TYPE = "text/csv";
	String CSV_EXCEL_MIME_TYPE = "application/vnd.ms-excel";
	
    int MAX_FILE_SIZE = 1024;
    
    String NAME_REGEX = "^[a-zA-Z]+$";
	String DEPT_REGEX = "^[a-zA-Z0-9 -_*]+$";
	String SALARY_REGEX = "^[0-9]+$";
	
	String COMMA_DELIMITER = ",";
	String NEW_LINE_SEPARATOR = "\n";
    String FILE_HEADER = "Name,Department,Designation,Salary,Joining Date";
	
	SimpleDateFormat JOINING_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	List<String> VALID_DESIGNATIONS = Collections.unmodifiableList(new ArrayList<String>() {
		{
			add("Developer");add("Senior Developer");add("Manager");
			add("Team Lead");add("VP");add("CEO");
		}
	});
	
	static Map<Long, Employee> EMPLOYEES = new HashMap<Long, Employee>();
	
	static Map<Long, Employee> INVALID_EMPLOYEES = new HashMap<Long, Employee>();
	
}
