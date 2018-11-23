package com.rakuten.fullstackrecruitmenttest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rakuten.fullstackrecruitmenttest.exception.FileStorageException;
import com.rakuten.fullstackrecruitmenttest.model.Employee;
import com.rakuten.fullstackrecruitmenttest.service.EmployeeService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/test")
	public String testMethod() {
		return "test";
	}
	
	@PostMapping("/fileupload")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			employeeService.parseCSVFile(file);
		} catch(FileStorageException fse) {
			return fse.getMessage();
		}
		return "File uploaded successfully.";
	}
	
	@GetMapping("/employees")
    public List<Employee> getAllEmployees() {
    	return employeeService.getAllEmployees();
    }
	

	@PutMapping("/employees")
    public String updateEmployee(@RequestBody Employee emp) {
    	return employeeService.updateEmployee(emp);
    }
    
    @GetMapping("/downloadErrorEntries")
    public ResponseEntity<Resource> downloadErrorEntries() {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "errorRecords.csv" + "\"")
                .body(new ByteArrayResource(employeeService.getAllErrorEmployees()));
    }
}
