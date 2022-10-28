package com.example.lesson213;

import com.example.lesson213.service.DepartmentService;
import com.example.lesson213.service.DepartmentServiceImpl;
import com.example.lesson213.service.EmployeeService;
import com.example.lesson213.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.lesson213.exceptions.EmployeeNotFoundException;
import com.example.lesson213.exceptions.EmployeeAlreadyAddedException;
import com.example.lesson213.exceptions.EmployeeIncorrectEntryException;
import com.example.lesson213.domain.Employee;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.mid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServiceImplTest {

    private final EmployeeService out = new EmployeeServiceImpl();
    private final DepartmentService out1 = new DepartmentServiceImpl();

       @BeforeEach
    public void BeforeEach () {
        out.getEmployees().forEach(employee -> out.remove(employee.getShortName()));
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("А_Клепов", "Александр", "Клепов", 5, 200_000),
                Arguments.of("Д_Фролов", "Дмитрий", "Фролов",  2, 250_000),
                Arguments.of("Д_Волосевич", "Дмитрий", "Волосевич", 5, 50_000),
                Arguments.of("А_Карпов", "Анатолий", "Карпов",  3, 60_000));
    }


        @ParameterizedTest
        @MethodSource("params")

        public void firstNegativeTestOnAddPrintFind (String key, String firstName, String lastName,int department, float salary){
            Employee expected = new Employee (firstName, lastName, department, salary);
            assertThat(out.getEmployees()).isEmpty();

            out.add(key, firstName, lastName, department, salary);
            assertThat(out.getEmployees())
                    .hasSize(1)
                    .containsExactly(expected);
            assertTrue(out.find(key).isPresent());
            assertEquals(expected.toString(),OptionalToString(out.find(key)));

            assertThatExceptionOfType(EmployeeNotFoundException.class)
                    .isThrownBy(() -> out.find(lastName));

            assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                    .isThrownBy(() -> out.add(key, firstName, lastName, department, salary));
        }


    @Test
    public void secondNegativeTestOnExceptions() {
        assertThatExceptionOfType(EmployeeIncorrectEntryException.class)
                .isThrownBy(() -> out.add("АКлепов", "Александр", "Клепов", 5, 200_000));

        assertThatExceptionOfType(EmployeeIncorrectEntryException.class)
                .isThrownBy(() -> out.add("Д_Фролов", "Дмитрий!", "Фролов",  2, 250_000));

        assertThatExceptionOfType(EmployeeIncorrectEntryException.class)
                .isThrownBy(() -> out.add("Д_Фролов", "Дмитрий", "Фролов@",  2, 250_000));

        assertThatExceptionOfType(EmployeeIncorrectEntryException.class)
                .isThrownBy(() -> out.add("Д_Фролов", "Дмитрий", null,  2, 250_000));

    }

    @ParameterizedTest
    @MethodSource("params")
    public void thirdNegativeTestOnRemove(String key, String firstName, String lastName, int department, float salary) {
        assertThat(out.getEmployees()).isEmpty();
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.remove("А_Клепов"));

        Employee expected = new Employee (firstName, lastName, department, salary);
        out.add(key, firstName, lastName, department, salary);
        assertThat(out.getEmployees())
                .hasSize(1)
                .containsExactly(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.remove("А_Клепофф"));

        assertThat(OptionalToString(out.remove(key))).isEqualTo(expected.toString());
        assertThat(out.getEmployees())
                .hasSize(0)
                .doesNotContain(expected);
        }


    public String OptionalToString(Optional optional) {
        return mid(optional.stream().collect(Collectors.toList()).toString(),
                1,
                optional.stream().collect(Collectors.toList()).toString().length() - 2);
    }

    }
