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
        if (use_br) {
            logger.info("Name " + employee.getName());
            logger.info("ID " + employee.getID());
            logger.info("Age " + employee.getAge());
            logger.info("Dep " + employee.getDepartment());

            if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Manager")) {
                logger.info("Type (" + employee.getType() + ") - MANAGER");
                logger.info("Meth " + ((Manager) employee).getMethodology());
            } else if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Developer")) {
                logger.info("Type (" + employee.getType() + ") - DEVELOPER ");
                logger.info("Lang " + ((Developer) employee).getLanguage());
            }
        } else {
            String tmpString = "Name " + employee.getName() + " ID " + employee.getID() +
                    " Age " + employee.getAge() + " Dep " + employee.getDepartment();
            if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Manager")) {
                tmpString += " Type (" + employee.getType() + ") - MANAGER Meth " + ((Manager) employee).getMethodology();
            } else if (employee.getClass().getName().equals("com.vakhnenko.departments.entity.employee.Developer")) {
                tmpString += " Type (" + employee.getType() + ") - DEVELOPER  Lang " + ((Developer) employee).getLanguage();
            }
            logger.info(tmpString);
        }
    }
}
