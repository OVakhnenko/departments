package com.vakhnenko.departments.entity.employee;

public class Manager extends Employee {

    public Manager() {
    }

    public Manager(String name, String type, int age, String department, String methodology) {
        super(name, type, age, department, "", methodology);
    }
}
