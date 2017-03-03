package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public abstract class EmployeeDAO extends EntityDAO<Employee> {
    public EmployeeDAO() {
        setEntityStatus("Employee");
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
    }

    public String getType(String employeeName) {
        return ((Employee) search(employeeName)).getType();
    }

    public Employee getByName(String name) {
        return search(name);
    }

    public List<Employee> getAll(String departmentName) {
        List<Employee> result = new ArrayList<>();

        for (Employee list : getAll()) {
            if ((list).getDepartment().equals(departmentName)) {
                result.add(list);
            }
        }
        return result;
    }

    public List<Employee> getAll(String departmentName, int age) {
        List<Employee> result = new ArrayList<>();

        for (Employee list : getAll()) {
            if ((list).getDepartment().equals(departmentName) && ((list).getAge() == age)) {
                result.add(list);
            }
        }
        return result;
    }

    public int getMaxEmployees(String departmentName, String type) {
        return 0;
    }

    public void done() {
    }
}
