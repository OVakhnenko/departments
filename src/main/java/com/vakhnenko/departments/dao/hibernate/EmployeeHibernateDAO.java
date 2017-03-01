package com.vakhnenko.departments.dao.hibernate;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.entity.employee.Employee;
import org.hibernate.SessionFactory;

/**
 * Created for departments on 01.03.2017 9:34.
 */
public class EmployeeHibernateDAO extends EmployeeDAO<Employee> {
    private SessionFactory sessionFactory;

    public EmployeeHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
