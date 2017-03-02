package com.vakhnenko.departments.dao.hibernate;

import com.vakhnenko.departments.dao.EmployeeDAO;
import com.vakhnenko.departments.entity.employee.Employee;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static com.vakhnenko.departments.App.logger;

/**
 * Created for departments on 01.03.2017 9:34.
 */
public class EmployeeHibernateDAO extends EmployeeDAO<Employee> {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public EmployeeHibernateDAO(SessionFactory sessionFactory) {
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
    public void add(Employee employee) {
        String name = employee.getName();

        if (exists(name)) {
            logger.warn("Error! Employee" + name + " already exists!");
        } else {
            if (openSession()) {
                try {
                    transaction = session.beginTransaction();
                    int employeeID = Integer.valueOf(String.valueOf(session.save(employee))).intValue(); //serializable to int
                    transaction.commit();
                } catch (Exception e) {
                    logger.error("Hibernate transaction error!");
                    return;
                } finally {
                    closeSession();
                }
            }
        }
    }

    public void update(String employeeName, int age, String departmentName, String methodology, String language) {
        int employeeID = getID(employeeName);

        if (openSession()) {
            try {
                Employee employee = session.get(Employee.class, employeeID);

                if (age > 0) employee.setAge(age);
                if (!departmentName.equals("")) employee.setDepartment(departmentName);
                if (!methodology.equals("")) employee.setMethodology(methodology);
                if (!language.equals("")) employee.setLanguage(language);
                session.update(employee);
                transaction.commit();
                logger.warn("Employee " + employeeName + " updated!");
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return;
            } finally {
                closeSession();
            }
        }
    }

    @Override
    public void delete(String name) {
        int employeeID = getID(name);

        if (openSession()) {
            try {
                Employee employee = session.get(Employee.class, employeeID);
                session.delete(employee);
                transaction.commit();
                logger.warn("Employee " + name + " deleted!");
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
                setList(session.createQuery("from Employee").list());
            } catch (Exception e) {
                logger.error("Hibernate query error!");
                return false;
            } finally {
                session.close();
            }
        }
        return super.exists(name);
    }

    private int getID(String name) {
        exists(name);
        return search(name).getEmployeeID();
    }

    @Override
    public Employee getByName(String name) {
        int employeeID = getID(name);
        Employee result = null;

        if (openSession()) {
            try {
                result = session.get(Employee.class, employeeID);
            } catch (Exception e) {
                logger.error("Hibernate query error!");
                return null;
            } finally {
                closeSession();
            }
        }
        return result;
    }

    @Override
    public String getType(String name) {
        int employeeID = getID(name);
        Employee result = null;

        if (openSession()) {
            try {
                result = session.get(Employee.class, employeeID);
            } catch (Exception e) {
                logger.error("Hibernate query error!");
                return null;
            } finally {
                closeSession();
            }
        }
        return result.getType();
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> result = null;

        if (openSession()) {
            try {
                result = session.createQuery("from Employee").list();
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return result;
            } finally {
                closeSession();
            }
        }
        return result;
    }

    @Override
    public List<Employee> getAll(String departmentName) {
        List<Employee> result = null;

        if (openSession()) {
            try {
                Query query = session.createQuery("from Employee where department_name = :paramDepartmentName");
                query.setParameter("paramDepartmentName", departmentName);
                result = query.list();
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return result;
            } finally {
                closeSession();
            }
        }
        return result;
    }

    @Override
    public List<Employee> getAll(String departmentName, int age) {
        List<Employee> result = null;

        if (openSession()) {
            try {
                Query query = session.createQuery("from Employee where " +
                        "department_name = :paramDepartmentName and " +
                        "age = :paramAge");
                query.setParameter("paramDepartmentName", departmentName);
                query.setParameter("paramAge", age);
                result = query.list();
            } catch (Exception e) {
                logger.error("Hibernate query error! " + e.getMessage());
                return result;
            } finally {
                closeSession();
            }
        }
        return result;
    }

    @Override
    public int getMaxEmployees(String departmentName, String type) {
        int result = 0;

        if (openSession()) {
            try {
                Query query = session.createQuery("select count(*) from Employee where " +
                        "department_name = :paramDepartmentName and " +
                        "type = :paramType");
                query.setParameter("paramDepartmentName", departmentName);
                query.setParameter("paramType", type);
                result = ((Long) query.uniqueResult()).intValue();
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
                logger.error("Hibernate session factory close error!");
            }
        }
    }
}
