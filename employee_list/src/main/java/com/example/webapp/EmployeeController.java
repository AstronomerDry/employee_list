package com.example.webapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    //requesting employee repository from application
    @Autowired
    private EmployeeRepository employeeRepo;

    //new route to display available employees
    @GetMapping({"", "employees"})
    public String getEmployees(Model model) {
        var employees = employeeRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("employees", employees); //list of employees will be made available to the page using the name 'employee'
        return "employees/index";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
        return "employees/create";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute EmployeeDto employeeDto, BindingResult result) {
        if (employeeRepo.findByEmail(employeeDto.getEmail()) != null) {
            result.addError(new FieldError("employeeDto", "email", employeeDto.getEmail(), false, null, null, "Email address is already used"));
        }

        if (result.hasErrors()) {
            return "employees/create";
        }

        Employee employee = new Employee();
        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setAddress(employeeDto.getAddress());
        employee.setStatus(employeeDto.getStatus());
        employee.setSalary(employeeDto.getSalary());
        employee.setCreatedAt(new Date());

        employeeRepo.save(employee);

        return "redirect:/employees";
    }

    @GetMapping("/edit")
    public String editEmployee(Model model, @RequestParam String id) {
        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) {
            return "redirect:/employees";
        }
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirst_name(employee.getFirst_name());
        employeeDto.setLast_name(employee.getLast_name());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setStatus(employee.getStatus());

        model.addAttribute("employee",employee);
        model.addAttribute("employeeDto",employeeDto);
        return "employees/edit";
    }

    @PostMapping("/edit")
    public String updateEmployee(@RequestParam String id, @Valid @ModelAttribute EmployeeDto employeeDto, BindingResult result,Model model) {
        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) {
            return "redirect:/employees";
        }

        if (result.hasErrors()) {
            model.addAttribute("employee",employee); //edit
            return "employees/edit";
        }

        // Update the employee details
        employee.setFirst_name(employeeDto.getFirst_name());
        employee.setLast_name(employeeDto.getLast_name());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setAddress(employeeDto.getAddress());
        employee.setStatus(employeeDto.getStatus());
        employee.setSalary(employeeDto.getSalary());

        employeeRepo.save(employee);

        return "redirect:/employees"; // Redirect to employee list
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam String id) {
        Employee employee=employeeRepo.findById(id).orElse(null);
        if (employee!=null) {
            employeeRepo.delete(employee);
        }
        return "redirect:/employees";
    }


}

