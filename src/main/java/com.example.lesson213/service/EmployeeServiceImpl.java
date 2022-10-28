package com.example.lesson213.service;

import com.example.lesson213.domain.Employee;
import com.example.lesson213.exceptions.EmployeeAlreadyAddedException;
import com.example.lesson213.exceptions.EmployeeIncorrectEntryException;
import com.example.lesson213.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service

public class EmployeeServiceImpl implements EmployeeService {


    public Map<String, Employee> employees = new HashMap<>(Map.of(
            "А_Клепов", new Employee("Александр", "Клепов", 5, 200_000),
            "Д_Фролов", new Employee("Дмитрий", "Фролов",  2, 250_000),
            "Д_Волосевич", new Employee("Дмитрий", "Волосевич", 5, 50_000),
            "А_Карпов", new Employee("Анатолий", "Карпов",  3, 60_000),
            "Г_Каспаров", new Employee("Гарии", "Каспаров", 1, 90_000),
            "А_Елькин", new Employee("Андрей", "Елькин", 1, 190_000),
            "В_Палкин", new Employee("Василий", "Палкин", 2, 110_000),
            "И_Иванов", new Employee("Иван", "Иванов",  2, 120_000),
            "И_Второйиванов", new Employee("Ивандва", "Второйиванов", 2, 125_000),
            "И_Третийиванов", new Employee("Ивантри", "Третийиванов",  3, 130_000)
    ));

    @Override
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Optional<Employee> remove(String key) throws EmployeeNotFoundException {
        Optional<Employee> forRemove = find(key);
        employees.remove(key);
        return forRemove;
    }


    @Override
    public Employee add(String key, String firstName, String lastName, int department, float salary) throws EmployeeNotFoundException  {
        checkUpperCase(firstName);
        checkUpperCase(lastName);
        firstName = firstName.trim();
        lastName = lastName.trim();
        checkKey(key,firstName,lastName);
        Employee employeeToAdd = new Employee(firstName,lastName,department,salary);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже есть в базе!");
        }
        employees.put(key,employeeToAdd);
        return employeeToAdd;
    }


    @Override
    public Optional<Employee> find(String key) {
        return Optional.ofNullable(employees.values().stream()
                .filter(e -> e.getShortName().equals(key))
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("Такой сотрудник не найдем!")));
    }

    public static void checkKey(String key, String firstName, String lastName) {
        if (!key.equals(firstName.charAt(0) + "_" + lastName)) {
            throw new EmployeeIncorrectEntryException("Формат ключа - Первая буква Имени, затем символ '_' и Фамилия с заглавной буквы!");
        }
    }

    public static void checkUpperCase(String name)  {
        if (!isAlpha(name)||isEmpty(name)) {
            throw new EmployeeIncorrectEntryException("Имя и Фамилия должны начинаться с заглавной буквы и не должны содержать ничего кроме букв!");
        }
    }

}




