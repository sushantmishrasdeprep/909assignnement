package com.assignement.EmployeeManagmentSystem.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignement.EmployeeManagmentSystem.models.Employee;
import com.assignement.EmployeeManagmentSystem.models.EmployeeDto;
import com.assignement.EmployeeManagmentSystem.services.EmployeesRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeesController {
    
    @Autowired
    private EmployeesRepository repo;
    
    @GetMapping({"", "/"})
    public String showEmployeeList(Model model) {
        List<Employee> employees = repo.findAll();
        model.addAttribute("employees", employees);
        return "employees/index";
    }
    
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
        return "employees/CreateEmployee";
    }
    
    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute EmployeeDto employeeDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "employees/CreateEmployee";
        }
        
        Date createdAt = new Date();
        
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setJob_role(employeeDto.getJob_role());
        employee.setSalary(employeeDto.getSalary());
        employee.setDescription(employeeDto.getDescription());
        employee.setCreated_at(createdAt);
        
        repo.save(employee);
        
        return "redirect:/employees";
    }
    
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Employee employee = repo.findById(id).get();
            model.addAttribute("employee", employee);
            
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setName(employee.getName());
            employeeDto.setDesignation(employee.getDesignation());
            employeeDto.setJob_role(employee.getJob_role());
            employeeDto.setSalary(employee.getSalary());
            employeeDto.setDescription(employee.getDescription());
            
            model.addAttribute("employeeDto", employeeDto);
            
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/employees";
        }
        
        return "employees/EditEmployee";
    }
    
    @PostMapping("/edit")
    public String updateEmployee(Model model, @RequestParam int id,
            @Valid @ModelAttribute EmployeeDto employeeDto,
            BindingResult result) {
        try {
            Employee employee = repo.findById(id).get();
            model.addAttribute("employee", employee);
            
            if(result.hasErrors()) {
                return "employees/EditEmployee";
            }
            
            employee.setName(employeeDto.getName());
            employee.setDesignation(employeeDto.getDesignation());
            employee.setJob_role(employeeDto.getJob_role());
            employee.setSalary(employeeDto.getSalary());
            employee.setDescription(employeeDto.getDescription());
            
            repo.save(employee);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        
        return "redirect:/employees";
    }
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Employee employee = repo.findById(id).get();
            repo.delete(employee);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        
        return "redirect:/employees";
    }
}
