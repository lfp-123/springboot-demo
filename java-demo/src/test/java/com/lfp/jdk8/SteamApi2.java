package com.lfp.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ${林锋鹏}
 * @Title: SteamApi2
 * @ProjectName springboot-demo
 * @Description: 中间操作
 * @date 2021/8/18 23:04
 */
public class SteamApi2 {



    //筛选与切片
    @Test
    public void test1(){
        List<Employee> employees = EmployeeDataList.getEmployees();
        //filter(Predicate p)—接收Lambda ,从流中排除某些元素。
        Stream<Employee> stream = employees.stream(); //获取流
        //练习 找出名字是南京的信息
        stream.filter(e ->e.getName().equals("南京")).forEach(System.out::println); //释放流
        //limit(n)—载断流，使其元素不超过给定数量。
        System.out.println("******************");
        employees.stream().limit(1).forEach(System.out::println);
        // skip(n)—跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n 个，则返回一个空流。与limit互补
        System.out.println("******************");
        employees.stream().skip(10).forEach(System.out::println);
        //distinct()—筛选，通过流所生成元素的hashCode(）和equals()去除重复元素
        System.out.println("******************");
        employees.stream().distinct().forEach(System.out::println);

    }

    //映射
    @Test
    public void test2(){
        //map(Function f)接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会应用到每一个元素，并将其映射到一个元素上
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(str -> str.toLowerCase()).forEach(System.out::println);
        list.stream().map(String::toLowerCase).forEach(System.out::println);
        //练习 获取年龄大于5的
        List<Employee> employees = EmployeeDataList.getEmployees();
        employees.stream().map(Employee::getAge).filter(integer -> integer > 5).forEach(System.out::println);
        employees.stream().filter(employee -> employee.getAge() > 5).forEach(System.out::println);
        //flatNap(Function f)接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
        Stream<Stream<Character>> stream = list.stream().map(SteamApi2::fromStringToStream);
        stream.forEach(s->{
            s.forEach(System.out::println);
        });

    }

    public static Stream<Character> fromStringToStream(String str){
        ArrayList<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    //归约
    public void test3(){

    }
}
