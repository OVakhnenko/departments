package com.vakhnenko.departments.dao.file;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.entity.employee.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 10.02.2017 9:36
 */
public class EmployeeFileDAO<T extends Employee> extends EmployeeDAO {
    private FileWriter writer;

    public EmployeeFileDAO(FileWriter writer) {
        this.writer = writer;
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        Employee employee = search(employeeName);

        if (employee != null) {
            if (age > 0) employee.setAge(age);
            if (!departmentName.equals("")) employee.setDepartment(departmentName);
            if (!methodology.equals("")) employee.setMethodology(methodology);
            if (!language.equals("")) employee.setLanguage(language);
        } else {
            logger.warn("Error! Employee " + employeeName + " not founf!");
        }
    }

    public boolean save() {
        boolean result = false;
        String type;
        List<Employee> employees = getAll();

        if (employees.size() == 0) {
            result = true;
        } else {
            for (Employee employee : employees) {
                try {
                    writer.write(CREATE_COMMAND + " " + EMPLOYEE_KEY + " " + NAME_EMPLOYEE_KEY + " " + employee.getName() + " ");
                    writer.write(TYPE_EMPLOYEE_KEY + " " + (type = employee.getType()) + " ");
                    writer.write(AGE_EMPLOYEE_KEY + " " + employee.getAge() + " ");
                    writer.write(DEPARTMENT_EMPLOYEE_KEY + " " + employee.getDepartment() + " ");

                    if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                        writer.write(METHODOLOGY_EMPLOYEE_KEY + " " + employee.getMethodology() + " ");
                    } else {
                        writer.write(LANGUAGE_EMPLOYEE_KEY + " " + employee.getLanguage() + " ");
                    }
                    writer.write("\n");
                    writer.flush();
                    result = true;
                } catch (IOException e) {
                    logger.error("Write error!");
                    result = false;
                }
            }
        }
        return result;
    }

    public void done() {
        try {
            writer.close();
        } catch (IOException e) {
            logger.error("Close file error!");
        }
    }
}
