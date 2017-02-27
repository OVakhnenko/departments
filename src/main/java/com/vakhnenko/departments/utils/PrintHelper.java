package com.vakhnenko.departments.utils;

import static com.vakhnenko.departments.App.logger;
import static com.vakhnenko.departments.utils.Arrays.*;

public class PrintHelper {

    public static void printHelpCommandsList() {
        logger.info("commanrds list:");
        logger.info("");
    }

    public static void printHelpDepartment() {
        logger.info("type \"create -d department_name\" for create department \"department_name\"");
        logger.info("type \"rm -d department_name\" for remove department \"department_name\"");
        logger.info("type \"departments\" for print list of all departments");
        logger.info("");
    }

    public static void printHelpEmployee() {
        logger.info("type \"create -e -n employee_name -t m -a age -m methodology\" for for create manager of");
        logger.info("type \"create -e -n employee_name -t d -a age -l language \" for for create developer");
        logger.info("type \"rm -e employee_name\" for remove employee \"employee_name\"");
        logger.info("type \"open -d department_name\" for watch department details");
        logger.info("type \"open -e employee_name\" for watch employee details");
        logger.info("");
    }

    public static void printHelpReadSave() {
        logger.info("type \"save\" for save data to file");
        logger.info("type \"read\" for read data from file");
        logger.info("");
    }

    public static void printHelpExit() {
        logger.info("type \"help\" for commands list");
        logger.info("type \"exit\" for exit");
        logger.info("");
    }

    public static void printSyntaxError(String[] commands) {
        logger.warn("Syntax Error! - \"" + getAllArrayStrings(commands) + "\" type \"help\" for commands list");
    }

    public static void printFirstScreen() {
        logger.info("Departments and Employees");
        logger.info("Type \"help\" for commands list or type \"exit\" for exit");
        logger.info("");
    }

    public static void printHelpSomething() {
        logger.info("Type \"all\" for print list of all departments and employees");
        logger.info("Type \"search -e -a age_to_search -d department\" for search employees");
        logger.info("Type \"top -d -t type_of_employee\"  for search employees");
        logger.info("");
    }
}
