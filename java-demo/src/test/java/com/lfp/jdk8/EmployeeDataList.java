package com.lfp.jdk8;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ${林锋鹏}
 * @Title: EmployeeDataList
 * @ProjectName springboot-demo
 * @Description: TODO
 * @date 2021/8/18 22:39
 */
public class EmployeeDataList {
    public static ArrayList<Employee> list = new ArrayList<>();

    public static List<Employee> getEmployees(){

        list.add(new Employee("南京",1));
        list.add(new Employee("西京",2));
        list.add(new Employee("北京1",2));
        list.add(new Employee("东京",3));
        list.add(new Employee("北3京",45));
        list.add(new Employee("4北京",12));
        list.add(new Employee("4北京",12));
        list.add(new Employee("4北京",12));
        list.add(new Employee("4北京",12));
        list.add(new Employee("4北京",12));
        return list;
    }

}
