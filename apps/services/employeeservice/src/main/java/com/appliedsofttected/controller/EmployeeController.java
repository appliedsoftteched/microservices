package com.appliedsofttected.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appliedsofttected.service.EmployeeServiceImpl;
import com.appliedsofttected.service.exception.ResourceNotFoundException;
import com.appliedsofttected.service.model.Employee;
import com.appliedsofttected.service.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	
	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	// get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeServiceImpl.getAllEmployees();
	}		
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeServiceImpl.createEmployee(employee);
		//return employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeServiceImpl.getEmployeeById(id);
		if(employee==null) {
			throw new ResourceNotFoundException("Employee not exist with id :" + id);
		} else {
			return ResponseEntity.ok(employee);	
		}
		
	}
	
	// update employee rest api
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeServiceImpl.getEmployeeById(id);
		if(employee==null) {
			throw new ResourceNotFoundException("Employee not exist with id :" + id);	
		}
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeServiceImpl.updateEmployee(id,employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeServiceImpl.getEmployeeById(id);
		if(employee==null) {
			throw new ResourceNotFoundException("Employee not exist with id :" + id);	
		}
		
		employeeServiceImpl.deleteEmployee(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
