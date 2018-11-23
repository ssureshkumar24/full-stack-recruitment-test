package com.rakuten.fullstackrecruitmenttest.service;

import java.io.StringReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.rakuten.fullstackrecruitmenttest.exception.FileStorageException;
import com.rakuten.fullstackrecruitmenttest.model.Employee;
import com.rakuten.fullstackrecruitmenttest.util.AppConstants;

@Service
public class EmployeeService {

	public boolean parseCSVFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			if(file.isEmpty()){
				throw new FileStorageException("Sorry! File cannot be empty " + fileName);
			}

			if(!AppConstants.CSV_MIME_TYPE.equalsIgnoreCase(file.getContentType()) && !AppConstants.CSV_EXCEL_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
				throw new FileStorageException("Sorry! Only CSV files can be uploaded " + fileName);
			}

			CSVReader csvReader = new CSVReaderBuilder(new StringReader(new String(file.getBytes())))
					.withSkipLines(1)
					.build();

			String[] nextRecord;
			Long empId = 1L;
			while ((nextRecord = csvReader.readNext()) != null) {
				String empName 	= nextRecord[0];
				String department = nextRecord[1];
				String designation = nextRecord[2];
				String salary = nextRecord[3];
				String joiningDate = nextRecord[4];
				Employee emp = new Employee(empId, empName, department, designation, salary, joiningDate, false);
				if(!validateRecords(emp)) {
					emp.setErrorRecord(true);
					AppConstants.INVALID_EMPLOYEES.put(empId, emp);
				}
				AppConstants.EMPLOYEES.put(empId, emp);
				empId = empId + 1;
			}
		} catch (Exception ex) {
			throw new FileStorageException("Could not read file " + fileName + ". Please try again!", ex);
		}
		return true;
	}


	private boolean validateRecords(Employee emp) {
		String empName = emp.getName();
		if(!doValidation(empName, AppConstants.NAME_REGEX)) {
			return false;
		}
		String department = emp.getDepartment();
		if(!doValidation(department, AppConstants.DEPT_REGEX)) {
			return false;
		}
		String designation = emp.getDesignation();
		if(!AppConstants.VALID_DESIGNATIONS.contains(designation)) {
			return false;
		}
		String salary = emp.getSalary();
		if(!doValidation(String.valueOf(salary), AppConstants.SALARY_REGEX)) {
			return false;
		}
		String joiningDate = emp.getJoiningDate();
		if(!isValidDateFormat(joiningDate)) {
			return false;
		}
		return true;
	}

	public static boolean isValidDateFormat(String value) {
		Date date = null;
		try {
			date = AppConstants.JOINING_DATE_FORMAT.parse(value);
			if (!value.equals(AppConstants.JOINING_DATE_FORMAT.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	public static boolean doValidation(String txt, String regx) {
		Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}

	public String updateEmployee(Employee emp) {
		Employee actual = AppConstants.EMPLOYEES.get(emp.getId());

		if(!validateRecords(emp)) {
			return "Validation failed.";
		} 
		actual.setName(emp.getName());
		actual.setDepartment(emp.getDepartment());
		actual.setDesignation(emp.getDesignation());
		actual.setSalary(emp.getSalary());
		actual.setJoiningDate(emp.getJoiningDate());
		actual.setErrorRecord(false);
		
		AppConstants.EMPLOYEES.put(emp.getId(), actual);
		AppConstants.INVALID_EMPLOYEES.remove(emp.getId());
		return "Updated";
	}

	public byte[] getAllErrorEmployees() {
		List<Employee> employees = AppConstants.INVALID_EMPLOYEES.values().stream()
				.collect(Collectors.toList());
		StringBuilder builder = new StringBuilder();
		builder.append(AppConstants.FILE_HEADER);
		builder.append(AppConstants.NEW_LINE_SEPARATOR);

		for(Employee e : employees) {
			builder.append(e.getName());
			builder.append(AppConstants.COMMA_DELIMITER);
			builder.append(e.getDepartment());
			builder.append(AppConstants.COMMA_DELIMITER);
			builder.append(e.getDesignation());
			builder.append(AppConstants.COMMA_DELIMITER);
			builder.append(e.getSalary());
			builder.append(AppConstants.COMMA_DELIMITER);
			builder.append(e.getJoiningDate());
			builder.append(AppConstants.NEW_LINE_SEPARATOR);
		}
		return builder.toString().getBytes();
	}

	public List<Employee> getAllEmployees() {
		return AppConstants.EMPLOYEES.values().stream()
				.collect(Collectors.toList());
	}
}