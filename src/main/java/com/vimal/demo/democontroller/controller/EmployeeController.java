package com.vimal.demo.democontroller.controller;

import com.vimal.demo.democontroller.model.Employee;
import com.vimal.demo.democontroller.repository.EmployeeRepository;
import com.vimal.demo.democontroller.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employee/create")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
    @GetMapping("/employee/get")
    public ResponseEntity<List<Employee>> getEmployees(){
        List<Employee> list = this.employeeService.getAllEmployee();
        if(list.size() <= 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //return ResponseEntity.of(Optional.of(list));
        return  ResponseEntity.status(HttpStatus.FOUND).body(list);
    }
    @GetMapping("/employee/get/{id}")
    public Employee getEmployee(@PathVariable Long id){
        if(employeeRepository.findById(id).isPresent())
            return employeeRepository.findById(id).get();
        else
            return null;
    }
    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id){
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
            if(employeeRepository.findById(id).isPresent())
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            else
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/employee/update/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        return employeeService.updateEmployee(id,employee);
    }
}
