package com.vakhnenko.departments.entity.employee;

import com.vakhnenko.departments.entity.Entity;

public class Employee extends Entity {
    private static int employeeID;
    private int age;
    private String type;
    private String department;
    private int department_id;
    private String language;
    private String methodology;

    public Employee() {
    }

    public Employee(String name, String type, int age, String department, String language, String methodology) {
        super(name);
        this.age = age;
        this.type = type;
        this.department = department;
        this.language = language;
        this.methodology = methodology;
    }

    @Override
    public int getUniqeID() {
        return employeeID++;
    }

    public void setEmployeeID(int employeeID) {
        Employee.employeeID = employeeID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    public String getMethodology() {
        return methodology;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
