package com.vakhnenko.departments.entity.department;

import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.entity.employee.Employee;

import java.util.Set;

public class Department extends Entity {
    private static int departmentID;
    private Set<Employee> employees;

    public Department() {
    }

    public Department(String name) {
        super(name);
    }

    @Override
    public int getUniqeID() {
        return departmentID++;
    }

    public void setDepartmentID(int departmentID) {
        Department.departmentID = departmentID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public Set getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
