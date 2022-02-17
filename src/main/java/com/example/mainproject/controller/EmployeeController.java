package com.example.mainproject.controller;

import com.example.mainproject.EmployeeException.EmployeeNotFoundException;
import com.example.mainproject.model.Employee;
import com.example.mainproject.model.EmployeeList;
import com.example.mainproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository repo;
    @Autowired
    Employee e;
    @Autowired
    EmployeeList employeeList;

   //1. Add New Employee to System (Emp ID, Name, Salary, Department
    @GetMapping("/Employee")
    public List<Employee> getAllEmployees(){
        if(repo.findAll().isEmpty()){
            throw new EmployeeNotFoundException("No employees found");
        }
        return repo.findAll();
    }

    @GetMapping("/Employee/{id}")
    public Employee getEmployee(@PathVariable int id){
        return repo.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Employee id:"+id+" not found"));
    }
// 2) Display Salary of Single employee

    @GetMapping("/Employee/salary/{id}")
    public int getEmployeeSalary(@PathVariable int id){
        return repo.findById(id).get().getSalary();
    }
     //  @GetMapping("/Employee/Name/{id}")
     //  public String getEmployeeName(@PathVariable String Name){
     //      return repo.findById(Integer.valueOf(Name)).get().getName();
     // }

    @PutMapping("/Employee/add")
    public Employee addEmployee(Employee employee){
        repo.save(employee);
        return employee;
    }

     // @PutMapping("/Employee/update/{id}")
    // public Employee updateEmployee(@PathVariable int id) {

    //      return repo.findByIdUpdate(id);
    //  }


         //   3.Update salary of Employee

    @PutMapping("/Employee/update/{id}")
     public Employee updateEmployee(@PathVariable int id, Employee emp) {
        if (repo.existsById(id)){
            repo.getById(id).setSalary(emp.getSalary());
            repo.save(repo.getById(id));
        }
          return repo.findByIdUpdate(id);
     }

    @DeleteMapping("/Employee/delete/{id}")
    public String deleteEmployee(@PathVariable int id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Employee deleted";
        }
        else throw new EmployeeNotFoundException("Employee id:"+id+" not found");
    }


// 4) Display Employees with particular Department in Ascending order (EMPLOYEE ID)
    @GetMapping("/Employee/sort/{dept}")
    public List<Employee> getEmployees(@PathVariable int dept){
        return repo.findByDept(dept);
    }

}