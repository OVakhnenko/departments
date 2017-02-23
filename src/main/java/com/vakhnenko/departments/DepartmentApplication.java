package com.vakhnenko.departments;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.dao.EmployeeDAO;

public class DepartmentApplication {
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void run() throws InterruptedException {
        while (true) {
            System.out.println("Type command:");
            Thread.sleep(5000);
        }
    }
}
