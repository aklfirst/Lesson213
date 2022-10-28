package com.example.lesson213.service;

import com.example.lesson213.domain.Employee;
import com.example.lesson213.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private EmployeeService employeeService;

    public DepartmentServiceImpl() {
        this.employeeService = employeeService;
    }
    @Override
    public Optional<Employee> minSalary(int department) {
        return Optional.ofNullable(employeeService.getEmployees().stream()
                .filter(e -> e.getDepartment() == (department))
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник в департаменте или департамент отсутствует")));
    }

    @Override
    public Optional<Employee> maxSalary(int department) {
        return Optional.ofNullable(employeeService.getEmployees().stream()
                .filter(e -> e.getDepartment() == (department))
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник в департаменте или департамент отсутствует")));
    }

    @Override
    public List<Employee> printAllEmployees() {
        return employeeService.getEmployees().stream()
                .sorted(Comparator.comparingInt(Employee::getDepartment))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> printByDepartment(int department) {
        return employeeService.getEmployees().stream()
                .filter(e->e.getDepartment() == (department))
                .collect(Collectors.toList());
    }
}
