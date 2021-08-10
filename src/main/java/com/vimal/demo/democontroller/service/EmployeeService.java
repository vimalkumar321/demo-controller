package com.vimal.demo.democontroller.service;

import com.vimal.demo.democontroller.model.Employee;
import com.vimal.demo.democontroller.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee(){
        return (List<Employee>)employeeRepository.findAll();
    }
    public Optional<Employee> getEmployeeById(Long id){
        Optional<Employee> employee = null;
        try{
            employee = this.employeeRepository.findById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return employee;
    }
    public ResponseEntity<Employee> addEmployee(Employee employee){
        Employee addedEmployee = this.employeeRepository.save(employee);
        if(employeeRepository.findById(employee.getId()).isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    public ResponseEntity<Object> deleteEmployee(Long id){
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
            if(employeeRepository.findById(id).isPresent())
                return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
            else
                return ResponseEntity.ok().body("Successfully deleted specified record");
        }
        else
            return ResponseEntity.unprocessableEntity().body("No Records Found");
    }
    public ResponseEntity<Object> updateEmployee(Long id, Employee employee){
        if(employeeRepository.findById(id).isPresent()) {
            Employee emp = employeeRepository.findById(id).get();
            emp.setName(employee.getName());
            emp.setDob(employee.getDob());
            emp.setEmail(employee.getEmail());
            emp.setPassword(employee.getPassword());
            Employee savedEmployee = employeeRepository.save(emp);
            if (employeeRepository.findById(savedEmployee.getId()).isPresent())
                return ResponseEntity.ok().body("Successfully updated Employee");
            else
                return ResponseEntity.unprocessableEntity().body("Failed to update specified employee");
        }
        else
            return ResponseEntity.unprocessableEntity().body("The specified employee is not found");
    }
}
