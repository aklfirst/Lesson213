package com.example.lesson213;

import com.example.lesson213.domain.Employee;
import com.example.lesson213.exceptions.EmployeeNotFoundException;
import com.example.lesson213.service.DepartmentServiceImpl;
import com.example.lesson213.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.apache.commons.lang3.StringUtils.mid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class DepartmentServiceImplTest {

    @Mock

    private EmployeeServiceImpl employeeService;

    @InjectMocks

    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void beforeEach() {

        List<Employee> employees = List.of(
                new Employee("Гарии", "Каспаров", 1, 90_000),
                new Employee("Андрей", "Елькин", 1, 190_000),
                new Employee("Василий", "Палкин", 2, 110_000),
                new Employee("Иван", "Иванов", 2, 120_000),
                new Employee("Ивандва", "Второйиванов", 2, 125_000)
        );


        when(employeeService.getEmployees()).thenReturn(employees);
        }

     public static Stream<Arguments> employeeLowestPaidParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Гарии", "Каспаров", 1, 90_000)),
                Arguments.of(2, new Employee("Василий", "Палкин", 2, 110_000))
        );
    }

    public static Stream<Arguments> employeeHighestPaidParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Андрей", "Елькин", 1, 190_000)),
                Arguments.of(2, new Employee("Ивандва", "Второйиванов", 2, 125_000))
        );
    }

    public static Stream<Arguments> employeesSpecifiedListParams() {
        return Stream.of(
                Arguments.of(1, List.of(
                        new Employee("Гарии", "Каспаров", 1, 90_000),
                        new Employee("Андрей", "Елькин", 1, 190_000)
                )),
                Arguments.of(2, List.of(
                        new Employee("Василий", "Палкин", 2, 110_000),
                        new Employee("Иван", "Иванов", 2, 120_000),
                        new Employee("Ивандва", "Второйиванов", 2, 125_000)
                )),
                Arguments.of(5, Collections.emptyList())
        );

    }


    @ParameterizedTest
    @MethodSource("employeeHighestPaidParams")
    public void highestPaidEmployeeTest(int department, Employee expected) {
       assertThat(OptionalToString(departmentService.maxSalary(department))).isEqualTo(expected.toString());
    }

    @ParameterizedTest
    @MethodSource("employeeLowestPaidParams")
    public void lowestPaidEmployeeTest(int department, Employee expected) {
        assertThat(OptionalToString(departmentService.minSalary(department))).isEqualTo(expected.toString());
    }

    @ParameterizedTest
    @MethodSource("employeesSpecifiedListParams")
    public void employeesSpecifiedListTest(int department, List<Employee> expected) {
        assertThat(departmentService.printByDepartment(department)).containsExactlyElementsOf(expected);
    }

    @Test
    public void testOnErrors() {

    assertThatExceptionOfType(EmployeeNotFoundException.class)
            .isThrownBy(() -> departmentService.maxSalary(5));

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.maxSalary(4));

}

    public String OptionalToString(Optional optional) {
        return mid(optional.stream().collect(Collectors.toList()).toString(),
                1,
                optional.stream().collect(Collectors.toList()).toString().length() - 2);
    }


}
