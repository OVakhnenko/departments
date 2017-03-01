package com.vakhnenko.departments.utils;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.vakhnenko.departments.entity.department.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Constants.*;

/**
 * Created for departments on 01.03.2017 9:06.
 */
public abstract class ConnectionUtilHibernate {
    private static SessionFactory sessionFactory;

    static {
        makeSessionFactory();
        createDBIfNotExists();
    }

    private static void makeSessionFactory() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            logger.error("Hibernate create session factory error! Connection is not established", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static boolean createHibernateDB(String createStatement, String tableName, String className) {
        boolean result = false;
        Session session = null;

        try {
            session = sessionFactory.openSession();
        } catch (Exception e) {
            logger.error("Hibernate open session error! Connection is not established", e);
            return false;
        }
        try {
            List<Department> developers = session.createQuery("FROM " + className).list();
            logger.info("Table \"" + tableName + "\" is exists.");
            result = true;
        } catch (Exception e) {
            session.createSQLQuery(createStatement).executeUpdate();
            logger.info("Table \"" + tableName + "\" is created.");
            result = true;
        } finally {
            session.close();
        }
        return result;
    }

    private static void createDBIfNotExists() {
        if (!(createHibernateDB(CREATE_DB_DEPARTMENT_IF_NOT_EXISTS, DEPARTMENT_TABLE_NAME, "Department")) ||
                (!createHibernateDB(CREATE_DB_EMPLOYEE_IF_NOT_EXISTS, EMPLOYEE_TABLE_NAME, "Employee"))) {
            logger.error("Hibernate error! Tables not created!");
            System.exit(DB_CREATE_ERROR_EXIT_CODE);
        }
    }
}
