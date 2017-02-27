package com.vakhnenko.departments;

import org.apache.log4j.Logger;

public class App {
    public static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        DepartmentsApplication departments = new DepartmentsApplication();

        try {
            departments.run();
        } catch (Exception e) {
            logger.error("Application error ", e);
        } finally {
            departments.done();
        }
    }
}
