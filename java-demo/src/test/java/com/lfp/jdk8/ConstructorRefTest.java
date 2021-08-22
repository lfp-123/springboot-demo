package com.lfp.jdk8;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author ${林锋鹏}
 * @Title: ConstructorRefTest
 * @ProjectName springboot-demo
 * @Description: 构造器引用
 * 数组引用
 *
 * @date 2021/8/18 21:38
 */
public class ConstructorRefTest {
    //构造器引用
    //Supplier 中的T get()

    @Test
    public void test1(){
        Supplier<Employee> supplier =  new Supplier<Employee>() {
            @Override
            public Employee get() {
                return new Employee();
            }
        };
        System.out.println("*******************");
        Supplier<Employee> sup = ()-> new Employee();
        System.out.println("*******************");
        Supplier<Employee> sup2 = Employee::new;
    }

    //Function 中的apply(T t)
    @Test
    public void Test2(){
        Function<Integer,Employee> fun = id ->new Employee();
        Employee apply = fun.apply(111111);
        System.out.println(apply.getAge());
        System.out.println("*******************");
        Function<Integer, Employee> runnable = Employee::new;
        Employee apply1 = runnable.apply(11111);
        System.out.println(apply1.getAge());
    }


    //数组引用
    //Function 中的 R apply(T t)
    @Test
    public void Test3(){
        Function<Integer,String[]> func1 = length -> new String[length];
        String[] apply = func1.apply(5);
        System.out.println("*********************");
        Function<Integer,String[]> func2 = String[] ::new;
        String[] apply1 = func2.apply(5);

    }




}
