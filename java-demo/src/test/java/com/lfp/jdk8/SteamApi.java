package com.lfp.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ${林锋鹏}
 * @Title: SteamApi
 * @ProjectName springboot-demo
 * @Description:
 * 1 、创建Steam 关注的是对数据的运算 与cpu打交道、
 * 集合关注的是数据的存储，与内存打交道
 *
 * 2、自己不会存储元素
 *     不会改变源对象。相反，他们会返回一个持有结果的新stream.
 *     操作是延迟执行的。这意味着他们会等到需要结果的时候才执行
 *
 * 3、执行流程
 *  一 实例化steam
 *  二 一系列的中间操作
 *  三 终止操作
 *
 *
 * @date 2021/8/18 22:12
 */
public class SteamApi {


    //创建steam方式1 ：通过集合
    @Test
    public void test1(){
//       default Stream<E> stream ():返回一个顺序流
        List<Employee> employees = EmployeeDataList.getEmployees();
        Optional<Employee> first = employees.stream().findFirst();
        Employee employee = first.get();
        System.out.println(employee);
        Stream<Employee> stream = employees.stream();
//       default Stream<E> parallelStream() 返回一个并行流
        Stream<Employee> employeeStream = employees.parallelStream();

    }

    //创建Stream方式二 、通过数组
    //static<T> Stream<T>stream(Tarray):返回一个流
    @Test
    public void test2(){
        int[] arr =new int[]{1,2,3};
        IntStream stream = Arrays.stream(arr);
        Employee[] employees = {new Employee("1", 2), new Employee("2", 5)};
        Stream<Employee> stream1 = Arrays.stream(employees);
    }

    //创建Stream方式三 、通过Stream Of
    //static<T> Stream<T>stream(Tarray):返回一个流
    @Test
    public void test3(){
        Stream<Integer> integerStream = Stream.of(1, 2, 4);

    }
    //创建Stream方式四 、创建无限流
    //static<T> Stream<T>stream(Tarray):返回一个流
    @Test
    public void test4(){
       //迭代
        // public static<T> Stream<T>iterate(final T seed, final UnaryOperator<T> f)
        //遍历前10个偶数
        Stream.iterate(0,t->t+2).limit(10).forEach(System.out::println);

        // 生成
        //public static<T> Stream<T>generate(Supplier<T> s)
        Stream.generate(Math::random).limit(1).forEach(System.out::println);

    }


}
