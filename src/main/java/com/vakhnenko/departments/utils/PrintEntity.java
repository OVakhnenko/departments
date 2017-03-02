package com.vakhnenko.departments.utils;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.department.Department;
import com.vakhnenko.departments.entity.employee.*;

import java.util.*;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.*;

/**
 * Created for practice on 22.02.2017 20:35
 */
public class PrintEntity {

    public static void printAllDepartments(List<Department> departments) {
        for (Department department : departments) {
            logger.info(department.getName());
        }
    }

    public static void printAllEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            printEmployee(employee, NOT_USE_BR);
        }
    }

    public static void printAllEmployeeGrid(List<Employee> employees) {
        String tmpString;

        tmpString = makeStringLength("Department Name", 26) +
                makeStringLength("Employee Name", 26) +
                makeStringLength("Type", 20) +
                makeStringLength("Age", 20);
        logger.info(tmpString);

        for (Employee employee : employees) {
            tmpString = makeStringLength(employee.getDepartment(), 26) +
                    makeStringLength(employee.getName(), 26) +
                    makeStringLength(employee.getType(), 20) +
                    makeStringLength(Integer.toString(employee.getAge()), 20);
            logger.info(tmpString);
        }
    }

    public static void printEmployee(Employee employee, boolean use_br) {
        String type = employee.getType();
        boolean isManager = type.equals(EMPLOYEE_MANAGER_TYPE);

        if (use_br) {
            logger.info("Name " + employee.getName());
            logger.info("ID " + employee.getEmployeeID());
            logger.info("Age " + employee.getAge());
            logger.info("Dep " + employee.getDepartment());

            if (isManager) {
                logger.info("Type (M) - MANAGER");
                logger.info("Meth " + employee.getMethodology());
            } else {
                logger.info("Type (D) - DEVELOPER ");
                logger.info("Lang " + employee.getLanguage());
            }
        } else {
            String tmpString = "Name " + employee.getName() + " ID " + employee.getID() +
                    " Age " + employee.getAge() + " Dep " + employee.getDepartment();
            if (isManager) {
                tmpString += " Type (M) - MANAGER Meth " + employee.getMethodology();
            } else {
                tmpString += " Type (D) - DEVELOPER  Lang " + employee.getLanguage();
            }
            logger.info(tmpString);
        }
    }
}
