package com.vakhnenko.departments.dao;

import com.vakhnenko.departments.entity.department.Department;

import static com.vakhnenko.departments.App.logger;

public abstract class DepartmentDAO extends EntityDAO<Department> {

    public DepartmentDAO() {
        setEntityStatus("Department");
    }

    public void create(String name) {
        if (search(name) != null) {
            logger.warn("Department  \"" + name + "\" already exists");
        } else {
            add(new Department(name));
        }
    }

    public Department getByName(String name) {
        return search(name);
    }

    public void done() {
    }

    public boolean save() {
        return false;
    }
}
