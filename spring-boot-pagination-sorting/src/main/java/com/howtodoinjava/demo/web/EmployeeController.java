package com.howtodoinjava.demo.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.demo.exception.RecordNotFoundException;
import com.howtodoinjava.demo.model.EmployeeEntity;
import com.howtodoinjava.demo.service.EmployeeService;
import com.opencsv.CSVReader;

@RestController
@RequestMapping("/transaction")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	private static final String FILE_NAME = "src/main/resources/Bank_transactions.csv";
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private EmployeeEntity entity = null;
	CSVReader reader = null;
	
	@GetMapping("/import")
	public String import_data() {
		try {
			service.importDate();
			return "Success";
		} catch (Exception e) {
			return "Failed";
		}
	}


	@GetMapping
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		List<EmployeeEntity> list = service.getAllEmployees(pageNo, pageSize, sortBy);
		return new ResponseEntity<List<EmployeeEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		EmployeeEntity entity = service.getEmployeeById(id);

		return new ResponseEntity<EmployeeEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeEntity> createOrUpdateEmployee(@RequestBody EmployeeEntity employee)
			throws RecordNotFoundException {
		EmployeeEntity updated = service.createOrUpdateEmployee(employee);
		return new ResponseEntity<EmployeeEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		return HttpStatus.OK;
	}

}