package com.vakhnenko.departments.dao.hibernate;

import com.vakhnenko.departments.dao.DepartmentDAO;
import com.vakhnenko.departments.entity.department.Department;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Constants.*;
import static com.vakhnenko.departments.utils.Strings.swq;

/**
 * Created for departments on 01.03.2017 9:30.
 */
public class DepartmentHibernateDAO extends DepartmentDAO {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public DepartmentHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public void create(String name) {
        if (exists(name)) {
            logger.warn("Error! Department " + name + " already exists!");
        } else {
            if (openSession()) {
                try {
                    transaction = session.beginTransaction();
                    Department department = new Department(name);
                    int departmentID = Integer.valueOf(String.valueOf(session.save(department))).intValue(); //serializable to int
                    transaction.commit();
                } catch (Exception e) {
                    logger.error("Hibernate save error! " + e.getMessage());
                } finally {
                    closeSession();
                }
            }
        }
    }

    @Override
    public void delete(String name) {
        int departmentID = getID(name);

        if (openSession()) {
            try {
                Department department = session.get(Department.class, departmentID);
                session.delete(department);
                transaction.commit();
                logger.warn("Department " + name + " deleted!");
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return;
            } finally {
                closeSession();
            }
        }
    }

    @Override
    public boolean exists(String name) {
        if (openSession()) {
            try {
                setList(session.createQuery("from Department").list());
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return false;
            } finally {
                closeSession();
            }
        }
        return super.exists(name);
    }

    private int getID(String name) {
        exists(name);
        return search(name).getDepartmentID();
    }

    @Override
    public List<Department> getAll() {
        List<Department> result = null;

        if (openSession()) {
            try {
                result = session.createQuery("from Department").list();
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return result;
            } finally {
                closeSession();
            }
        }
        return result;
    }

    private boolean openSession() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
        } catch (Exception e) {
            logger.error("Hibernate open session error! " + e.getMessage());
            return false;
        }
        return true;
    }

    private void closeSession() {
        session.close();
    }

    @Override
    public void done() {
        if ((sessionFactory != null) && (!sessionFactory.isClosed())) {
            try {
                sessionFactory.close();
            } catch (Exception e) {
                logger.error("Hibernate session factory close error! " + e.getMessage());
            }
        }
    }
}
