package com.vakhnenko.departments.entity.employee;

public class Developer extends Employee {

    public Developer() {
    }

    public Developer(String name, String type, int age, String department, String language) {
        super(name, type, age, department, language, "");
    }
}
