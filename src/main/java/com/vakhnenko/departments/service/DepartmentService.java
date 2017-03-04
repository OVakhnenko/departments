package com.vakhnenko.departments.service;

import com.vakhnenko.departments.dao.*;
import com.vakhnenko.departments.dao.file.*;
import com.vakhnenko.departments.dao.db.*;
import com.vakhnenko.departments.dao.hibernate.DepartmentHibernateDAO;
import com.vakhnenko.departments.dao.hibernate.EmployeeHibernateDAO;
import com.vakhnenko.departments.entity.department.*;
import com.vakhnenko.departments.entity.employee.*;
import com.vakhnenko.departments.entity.*;
import com.vakhnenko.departments.utils.*;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for practice on 09.02.2017 21:07
 */
public class DepartmentService {
    // file
    //private DepartmentDAO departmentDAO = new DepartmentFileDAO(ConnectionUtilFile.getFileConnectionWriter());
    //private EmployeeDAO<Employee> employeeDAO = new EmployeeFileDAO(ConnectionUtilFile.getFileConnectionWriter());
    // db
    //private DepartmentDAO departmentDAO = new DepartmentDbDAO(ConnectionUtilJDBC.getDBConnection());
    //private EmployeeDAO<Employee> employeeDAO = new EmployeeDbDAO(ConnectionUtilJDBC.getDBConnection());
    // hibernate
    private DepartmentDAO departmentDAO = new DepartmentHibernateDAO(ConnectionUtilHibernate.getSessionFactory());
    private EmployeeDAO employeeDAO = new EmployeeHibernateDAO(ConnectionUtilHibernate.getSessionFactory());

    public DepartmentService() throws SQLException {
    }

    public void createDepartment(String name) {
        if (departmentDAO.exists(name)) {
            logger.warn("Error! Department " + name + " already exists!");
        } else {
            departmentDAO.create(name);
        }
    }

    public void createManager(String employeeName, String type, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            logger.warn("Error! Manager " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Employee(employeeName, type, age, departmentName, "", methodology));
        }
    }

    public void createDeveloper(String employeeName, String type, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            logger.warn("Error! Developer " + employeeName + " already exists!");
        } else {
            employeeDAO.add(new Employee(employeeName, type, age, departmentName, language, ""));
        }
    }

    public void updateManager(String employeeName, int age, String departmentName, String methodology) {
        if (employeeExists(employeeName)) {
            employeeDAO.update(employeeName, age, departmentName, methodology, "");
        } else {
            logger.warn("Error! Employee " + employeeName + " not found!");
        }
    }

    public void updateDeveloper(String employeeName, int age, String departmentName, String language) {
        if (employeeExists(employeeName)) {
            employeeDAO.update(employeeName, age, departmentName, "", language);
        } else {
            logger.warn("Error! Employee " + employeeName + " not found!");
        }
    }

    public void removeDepartment(String name) {
        if (departmentExists(name)) {
            departmentDAO.delete(name);
        } else {
            logger.warn("Error! Department " + name + " not found!");
        }
    }

    public void removeEmployee(String name) {
        if (employeeExists(name)) {
            employeeDAO.delete(name);
        } else {
            logger.warn("Error! Employee " + name + " not found!");
        }
    }

    public boolean departmentExists(String departmentName) {
        return departmentDAO.exists(departmentName);
    }

    public boolean employeeExists(String employeeName) {
        return employeeDAO.exists(employeeName);
    }

    public String getTypeEmployee(String employeeName) {
        String result;

        if (employeeExists(employeeName)) {
            result = employeeDAO.getType(employeeName);
        } else {
            result = "";
        }

        return result;
    }

    public void saveToFile() {
        if (departmentDAO.save()) {
            if (employeeDAO.save()) {
                logger.info("All data saved successfully");
            }
        } else {
            logger.warn("Data not been saved!");
        }
    }

    public List<String> readFromFile() throws IOException {
        return departmentDAO.read();
    }

    public void openEntityWithName(String employeeName) {
        if (employeeExists(employeeName)) {
            PrintEntity.printEmployee(employeeDAO.getByName(employeeName), USE_BR);
        } else {
            logger.warn(employeeDAO.getEntityStatus() + " \"" + employeeName + "\" not found!");
        }
    }

    public void printAllDepartments() {
        logger.info("Departmnents:");
        PrintEntity.printAllDepartments(departmentDAO.getAll());
    }

    public void printAllEmployee(String department) {
        logger.info("Employees of departmnent " + department + ":");
        PrintEntity.printAllEmployee(employeeDAO.getAll(department));
    }

    public void printEmployee(String employeeName, boolean use_br) {
        PrintEntity.printEmployee(employeeDAO.getByName(employeeName), use_br);
    }

    public void printAllEmployeeGrid() {
        PrintEntity.printAllEmployeeGrid(employeeDAO.getAll());
    }

    public void printSearchedEmployeeAge(String departmentName, int age) {
        PrintEntity.printAllEmployeeGrid(employeeDAO.getAll(departmentName, age));
    }

    public void printTopEmployee(String type) {
        String department = "";
        int max = 0;
        int tmp;

        List<Department> departments = departmentDAO.getAll();
        if (departments.size() == 0) {
            logger.warn("Error! No departments!");
            return;
        }

        for (Department dep : departments) {
            tmp = employeeDAO.getMaxEmployees(dep.getName(), type);
            if (tmp > max) {
                max = tmp;
                department = dep.getName();
            }
        }

        if (max > 0) {
            logger.info("Department " + department + " has " + max + ((type.equals("D")) ? " developers" : " managers"));
        } else {
            logger.info("Department's is not have any " + ((type.equals("D")) ? " developers" : " managers"));
        }
    }

    public void done() {
        departmentDAO.done();
        employeeDAO.done();
        logger.info("Bye!");
    }
}
