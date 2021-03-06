package com.vakhnenko.departments.dao.db;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.entity.employee.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.ConnectionUtilJDBC.insertIntoDB;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.swq;

/**
 * Created for practice on 10.02.2017 10:21
 */
public class EmployeeDbDAO<T extends Employee> extends EmployeeDAO {
    private Connection dbConnection;
    private Statement statement;

    public EmployeeDbDAO(Connection dbConnection) throws SQLException {
        this.dbConnection = dbConnection;
        this.statement = dbConnection.createStatement();
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public List<String> read() {
        return null;
    }

    @Override
    public void add(Employee employee) {
        String type = employee.getType();

        if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
            insertIntoDB(statement, INSERT_INTO_DB_MANAGER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(employee.getMethodology()) + CLOSING_STRUCTURE);
        } else {
            insertIntoDB(statement, INSERT_INTO_DB_DEVELOPER
                    + swq(employee.getName()) + ","
                    + swq(Integer.toString(employee.getAge())) + ","
                    + swq(type) + ","
                    + swq(employee.getDepartment()) + ","
                    + swq(employee.getLanguage()) + CLOSING_STRUCTURE);
        }
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        if (age > 0) {
            updateEmployee(" age = " + age, swq(employeeName));
        }
        if (!departmentName.equals("")) {
            updateEmployee(" department_name = " + swq(departmentName), swq(employeeName));
        }
        if (!methodology.equals("")) {
            updateEmployee(" methodology = " + swq(methodology), swq(employeeName));
        }
        if (!language.equals("")) {
            updateEmployee(" language = " + swq(language), swq(employeeName));
        }
    }

    private void updateEmployee(String updateQuery, String employeeName) {
        String query = UPDATE_EMPLOYEE_DB_EMPLOYEE + updateQuery + WHERE_NAME_IS_EQUAL + employeeName;
        try {
            statement.execute(query);
        } catch (SQLException e) {
            logger.error("MySQL error! " + query);
        }
    }

    @Override
    public void delete(String name) {
        String query = DELETE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
    }

    @Override
    public String getType(String employeeName) {
        String query = SELECT_TYPE_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(employeeName);
        String result = "";

        if (!exists(employeeName)) {
            logger.warn("Error! Employee " + employeeName + " not found!");
        } else {
            try {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    result = rs.getString("type");
                }
            } catch (SQLException e) {
                logger.error("MySQL query error! " + query);
            }
        }
        return result;
    }

    @Override
    public List<Employee> getAll() {
        String name;
        String query = SELECT_ALL_FROM_DB_EMPLOYEE;
        List<Employee> result = new ArrayList<>();
        List<String> names = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName : names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public List<Employee> getAll(String departmentName) {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL + swq(departmentName);
        List<Employee> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String name;

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName : names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public List<Employee> getAll(String departmentName, int age) {
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_AGE_IS_EQUAL + age + " AND "
                + DEPARTMENT_IS_EQUAL + swq(departmentName);
        List<Employee> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String name;

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("name");
                names.add(name);
            }
            for (String employeeName : names) {
                result.add(getByName(employeeName));
            }
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public T getByName(String name) {
        int age;
        String query = SELECT_ALL_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);
        String type = "";
        String department;
        String methodology;
        String language;
        T result = null;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                type = rs.getString("type");
            age = rs.getInt("age");
            department = rs.getString("department_name");
            if (type.equals(EMPLOYEE_MANAGER_TYPE)) {
                methodology = rs.getString("methodology");
                result = (T) (new Employee(name, type, age, department, "", methodology));
            } else {
                language = rs.getString("language");
                result = (T) (new Employee(name, type, age, department, language, ""));
            }
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public boolean exists(String name) {
        String query = SELECT_NAME_FROM_DB_EMPLOYEE + WHERE_NAME_IS_EQUAL + swq(name);
        boolean result = false;

        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next())
                result = true;
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public int getMaxEmployees(String departmentName, String type) {
        int result = 0;

        String query = SELECT_COUNT_FROM_DB_EMPLOYEE + WHERE_DEPARTMENT_NAME_IS_EQUAL
                + swq(departmentName) + " AND " + TYPE_IS_EQUAL + swq(type);
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                result = rs.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("MySQL query error! " + query);
        }
        return result;
    }

    @Override
    public void done() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("MySQL error! DB statement not close!");
            }
        }
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                logger.error("MySQL error! DB connection not close!");
            }
        }
    }
}
