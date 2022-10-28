package com.example.lesson213.service;

import com.example.lesson213.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<Employee> minSalary(int department);

    Optional<Employee> maxSalary(int department);

    List<Employee> printAllEmployees();

    List<Employee> printByDepartment(int department);
}
