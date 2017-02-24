package com.vakhnenko.departments;

import org.apache.log4j.Logger;

public class App {
    public static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        DepartmentsApplication departments = new DepartmentsApplication();

        try {
            //logger.info("departments run");
            departments.run();
        } catch (Exception e) {
            logger.error("departments error ", e);
            System.out.println("Application error! " + e.getMessage());
        } finally {
            //logger.info("departments done");
            departments.done();
        }
    }
}
