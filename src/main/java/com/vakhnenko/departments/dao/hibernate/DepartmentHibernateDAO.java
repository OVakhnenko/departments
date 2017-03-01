package com.vakhnenko.departments.dao.hibernate;

import com.vakhnenko.departments.dao.DepartmentDAO;
import org.hibernate.SessionFactory;

/**
 * Created for departments on 01.03.2017 9:30.
 */
public class DepartmentHibernateDAO extends DepartmentDAO {
    private SessionFactory sessionFactory;

    public DepartmentHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
