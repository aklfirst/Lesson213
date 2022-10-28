package com.example.lesson213.service;

import com.example.lesson213.domain.Employee;
import com.example.lesson213.exceptions.EmployeeAlreadyAddedException;
import com.example.lesson213.exceptions.EmployeeNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {


    List<Employee> getEmployees();

    public Optional<Employee> remove(String key) throws EmployeeNotFoundException;

    public Employee add(String key, String firstName, String lastName, int department, float salary) throws EmployeeAlreadyAddedException;
    public Optional<Employee> find(String key) throws EmployeeNotFoundException;

}